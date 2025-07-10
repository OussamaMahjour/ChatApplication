import { Client } from "@stomp/stompjs";
import { useEffect, useRef, useState } from "react";
import { useAuth } from "../../../provider/AuthProvider";
import Peer from 'simple-peer-light';



const VideoCall = ({target}:{target:String})=>{
    const [stompClient, setStompClient] = useState<any>(null);
    const [stream, setStream] = useState<any>(null);
    const peerRef = useRef<any>();
    const myVideo = useRef<any>();
    const remoteVideo = useRef<any>();
    const {token,user} = useAuth()
    if(!user ) return null;
    useEffect(()=>{

            const client = new Client({
                  brokerURL: `ws://localhost:8080/api/v1/chat?token=${token}`,
                  onConnect: ()=>{
                    console.log('Connected to signaling server');
                    client.subscribe(`/queue/${user.username}/call/video`, async ({ body }) => {
                      const data = JSON.parse(body);
                      console.log("Received signal:", data);
                      console.log("peerRef.current:", peerRef.current);

                      // If peer already exists, just signal it
                      if (peerRef.current) {
                        peerRef.current.signal(data.signal);
                        return;
                      }

                      // Step 1: Get local stream if not already
                      let localStream = stream;
                      if (!localStream) {
                        localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
                        setStream(localStream);
                        if (myVideo.current) {
                          myVideo.current.srcObject = localStream;
                        }
                      }

                      // Step 2: Create peer with initiator: false
                      const peer = new Peer({
                        initiator: false,
                        trickle: false,
                        stream: localStream,
                      });

                      peer.on('signal', (signal) => {
                        console.log("Sending return signal to caller...");
                        stompClient.publish({
                          destination: `/app/${user.username}/call/video`,
                          body: JSON.stringify({
                            target: data.from, // Send back to caller
                            from: user.username,
                            signal: signal
                          }),
                        });
                      });

                      peer.on('stream', remoteStream => {
                        console.log("Received remote stream!");
                        if (remoteVideo.current) {
                          remoteVideo.current.srcObject = remoteStream;
                        }
                      });

                      // Step 3: Feed the initial offer signal to peer
                      peer.signal(data.signal);

                      peerRef.current = peer;
                    });
                        },
                
                });
                client.activate()
                setStompClient(client)      
    },[])
    const callPeer = async () => {
        if (!stompClient || !stompClient.connected) {
          console.warn("Stomp client not ready");
          return;
        }
        const localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
        setStream(localStream);
        if (myVideo.current) {
          myVideo.current.srcObject = localStream;
        }
        console.log("STOMP connected:", stompClient?.connected);
        console.log("Local stream ready:", localStream);
        const peer = new Peer({
        initiator: true,
        trickle: false,
        stream: localStream,
        });

        peer.on('signal', signal => {
          console.log("getting signal")
          console.log({ target: target, from: user.username, signal })
          console.log(JSON.stringify(signal))

        stompClient.publish({
            destination: `/app/${target}/call/video`,
            body: JSON.stringify({ target: target, from: user.username, signal:JSON.stringify(signal) }),
        });
        });

        peer.on('stream', remoteStream => {
          console.log("getting stream")
          console.log(remoteStream)
        remoteVideo.current.srcObject = remoteStream;
        });

        peerRef.current = peer;
        
    };



    return (
    <div className="bg-background-light aspect-video w-40 z-100">
      <button onClick={callPeer}>Start Call</button>
      <video ref={myVideo} autoPlay muted />
      <video ref={remoteVideo} autoPlay />
    </div>
  );
}

export default VideoCall