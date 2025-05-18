import { useState,useEffect, useRef, ReactElement } from 'react'
import { Client, IFrame, IMessage } from '@stomp/stompjs';
import './App.css'
import Account from './Account'
import Conversation from './Conversation';

type Message = {
  source:string;
  text:string;
}

type ConversationType = {
  master:string;
  conversation:Message[]
}
function App() {

 
  const [friends,setFriends] = useState<string[]>([])
  const [user,setUser] = useState("")
  const [connected,setConnected] = useState(false)
  const [destination,setDestination] = useState("")
  const [message,setMessage] = useState("")
  const stompClientRef = useRef<Client | null>(null);
  const [currentConversationUser, setCurrentConversationUser] = useState<string | null>(null);
  const [conversations,setConversations] = useState<ConversationType[]>([])
  const [accounts,setAccounts] = useState<ReactElement[]>([])
  useEffect(() => {
    const client = new Client({
      brokerURL: 'ws://localhost:8080/message',
      onConnect: () => {
        setConnected(true);
        console.log('Connected to socket');

        client.subscribe(`/queue/${user}/chat`, (msg: IMessage) => {
          try {
            const parsed: Message = JSON.parse(msg.body);
            const source = parsed.source;
            if (!friends.includes(source)) {
              addAccount(source);
             
            }
            setConversations(prev =>
              prev.map(c =>
                c.master === source
                  ? { ...c, conversation: [...c.conversation, parsed] }
                  : c
              )
            );
            
            console.log('Received from:', parsed);
          } catch (err) {
            console.error('Failed to parse message:', err);
          }
        });
      },
      onDisconnect: () => {
        setConnected(false);
      },
      onStompError: (err) => {
        console.error('STOMP error:', err);
      }
    });

    stompClientRef.current = client;

    return () => {
      client.deactivate();
    };
  }, [user]);

    const addAccount = (name:string) => {
      setFriends(friends.concat(name));
      
      conversations.push({master:name,conversation:[]})
    };

    const changeConversation = (username: string) => {
      setCurrentConversationUser(username);
    };

    const sendMessage = (dest:string,text:string)=>{
      const msg: Message = { source: user, text };
      if (connected && stompClientRef.current) {
        stompClientRef.current.publish({
          destination: `/app/${dest}/chat`,
          body: JSON.stringify({
            source: user,
            text: text,
          }),
        });
        if (!friends.includes(dest)) {
          addAccount(dest);
         
        }
        setConversations(prev =>
          prev.map(c =>
            c.master === dest
              ? { ...c, conversation: [...c.conversation,msg] }
              : c
          )
        );
    
        setCurrentConversationUser(dest);
        console.log(`Sent message to /app/${dest}/chat`);
       }

    }
  return (
    <>
        <div className='w-full h-200 border flex '>
          <div className='w-1/3 h-full border-r'>
          <div onClick={(e)=>setCurrentConversationUser(null)} className="cursor-pointer flex items-center text-center text-white bg-[#333333] w-full h-10 border-b justify-center flex">
            <h1>New Message</h1>
          </div>
          {friends.map((e, index) => (
            <Account func={changeConversation} key={index} name={e} />
          ))}
             
          </div>
          <div className='w-2/3 flex-row relative'> 
                <div style={{display:connected?'none':'flex'}}  className='absolute z-100 bg-white flex justify-center items-center w-full h-full'>
                    <input className=' h-10 w-1/3 border' placeholder='Username' onChange={(e)=>setUser(e.target.value)}/>
                    <button className='cursor-pointer bg-black text-white h-10 border' onClick={()=>{
                      if (user.length > 0 && stompClientRef.current) {
                        stompClientRef.current.activate();
                      }
                      
                    }
                    }>Connect</button>
                </div>
                <div className='w-full h-1/20 border-b '><h1>status:    {connected?"online":"offline"}</h1></div>
                <div className='w-full h-19/20 container relative z-0'>
                        <div className='w-full h-full justify-center items-end flex'> 
                          <input onChange={(e)=>setDestination(e.target.value)} placeholder='To' className='border w-2/5 h-10'/>
                          <input onChange={(e)=>setMessage(e.target.value)} placeholder='Message' className='border w-3/5 h-10'/>
                          <button className='w-1/5 border bg-black text-white cursor-pointer h-10' 
                            onClick={()=>{
                                sendMessage(destination,message)
                            }}
                          >Send</button>
                          <div className='absolute h-full w-full ' style={{display:currentConversationUser==null?'none':'flex'}}>
                            
                          {currentConversationUser && (
                                <Conversation
                                  key={currentConversationUser}
                                  master={currentConversationUser}
                                  send={sendMessage}
                                  conversation={
                                    conversations.find(c => c.master === currentConversationUser)?.conversation || []
                                  }
                                />
                              )}
                            </div>
                        </div>
                        
                </div>
          </div>
        </div>
    </>
  )
}

export default App
