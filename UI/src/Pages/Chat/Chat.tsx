import { ReactElement, useEffect, useRef, useState } from "react";
import Account from "./component/Account";
import Conversation from "../../types/Conversation";
import Contact from "../../types/Contact";
import Discussion from "./component/Discussion";
import User from "../../types/User";
import { useNavigate } from "react-router-dom";
import { Client, IMessage } from "@stomp/stompjs";
import Message from "../../types/Message";
import notificationSound from '../../assets/notification.mp3';


type Props= {
    theme:string;
    setTheme:CallableFunction;
    user?:User;
}



function Chat({theme,setTheme,user}:Props):ReactElement{
    const navigate = useNavigate();
    const stompClientRef = useRef<Client | null>(null);
    const [connected,setConnected] = useState(false)
    const [conversations,setConversations] = useState<Conversation[]>([])
    const [currentContact,setCurrentContact] = useState<Contact>()
    const [popup,setPopup] = useState<ReactElement|null>(null)
    const destinationRef = useRef<HTMLInputElement>(null);
    const [unseenConvo,setUnseenConvo] = useState(0)
    const messageRef = useRef<HTMLInputElement>(null)


    useEffect(()=>{
        if(user==undefined || user.username.length == 0){
            navigate("/login")
        }else{
            const client = new Client({
                  brokerURL: 'ws://localhost:8080/chatapp',
                  onConnect: ()=>{
                    client.subscribe(`/queue/${user.username}/chat`, (msg: IMessage) => {
                            try {
                                const raw:{destination:string,message:Message}= JSON.parse(msg.body);
                                raw.message.sentDate = new Date(raw.message.sentDate)
                                raw.message.isMine = false
                                raw.message.isSeen = false
                                registerMessage(raw.destination,raw.message)
                                console.log('Received from:', raw.destination);
                                console.log(raw)
                                
                                setUnseenConvo(prev => {
                                    const newCount = prev + 1;
                                    const audio = new Audio(notificationSound);
                                    audio.play()
                                    updateTitle(newCount);
                                    return newCount;
                                });
                                
                                
                            } catch (err) {
                                console.error('Failed to parse message:', err);
                            }
                            });
                            setConnected(true);
                            console.log("Connected as "+user.username)
                        },
                  onDisconnect: () => {
                    console.log("Disconnect as "+user.username)
                    setConnected(false);
                  },
                  onStompError: (err) => {
                    console.error('STOMP error:', err);
                  }
                });
                stompClientRef.current = client;
                client.activate()
                return () => {
                client.deactivate();
                };
                
            }
        
    },[])

    
    const updateTitle = (count: number) => {
        
        document.title = `${count > 0 ? `(${count})` : ''} ChatApp`;
    };
    


    const registerMessage = (sender:string,message:Message)=>{
        setConversations(prev => {
            const found = prev.find(conv => conv.owner.name === sender);
            if (found) {
                return prev.map(conv =>
                    conv.owner.name === sender
                        ? { ...conv, messages: [...conv.messages, message] }
                        : conv
                );
            } else {
                const contact: Contact = {
                    name: sender,
                    isOnline: false,
                    lastOnline: new Date(),
                    profile: "https://preview.redd.it/3fc3wd5xwf171.png?width=640&crop=smart&auto=webp&s=5becec3ee5ab15c5654fab0ad847cfae54f208a0"
                };
                return [...prev, { owner: contact, messages: [message] }];
            }
        });           
    }
    const toggleTheme = ()=>{
        if(theme=="light"){
            setTheme("dark")
        }else{
            setTheme("light")
        }
    }

    const setActiveContact = (contact: Contact) => {
        setConversations(prev => {
            return prev.map(conv =>
                conv.owner.name === contact.name
                    ? {
                        ...conv,
                        messages: conv.messages.map(message => ({
                            ...message,
                            isSeen: true
                        }))
                    }
                    : conv
            );
        });
        
        setUnseenConvo(prev => {
            const newCount = prev > 0 ? prev - 1 : 0;
            updateTitle(newCount);
            return newCount;
        });
        setCurrentContact(contact);
    };

    const sendMessage = (dest:string,message:Message)=>{
        
        if (connected && stompClientRef.current!=null ) {
            
            stompClientRef.current.publish({
            destination: `/app/${dest}/chat`,
           
            body: JSON.stringify({destination:user?.username,message:message}),
            });
            registerMessage(dest,message)                                          
        }
       
                      
    
    }

    return <div className="h-full w-full  rounded shadow-md dark:shadow-secondary-dark  dark:border-border-dark  border-border-light flex bg-background-light dark:bg-background-dark ">
        {
            popup==null?
            <></>:
            <div className="absolute h-full w-full bg-[#00000033] top-0 left-0 flex justify-center items-center">
                {popup}
            </div>
            
        }
        {/*contacts container*/}
        <div className="h-full max-w-100 w-1/3 border-r dark:border-border-dark border-border-light flex-col flex">
            <div className="h-20 border-b  dark:border-border-dark border-border-light  w-full flex px-4 py-3 ">
                <h1 className="h-full w-5/7 text-center flex items-center font-bold text-xl dark:text-text-dark text-text-light">
                    Chats
                </h1>
                <button onClick={toggleTheme} className="h-full w-1/7 text-center text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light">
                    <i className={`fa-regular ${theme=="light"?"fa-sun":"fa-moon"}`}></i>
                </button>
                <button className="h-full w-1/7 text-center  text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light" 
                    onClick={()=>setPopup(
                        <div id="popup" className="min-w-1/4 w-100 rounded  shadow-md dark:shadow-secondary-dark  border  border-accent-light flex bg-background-light dark:bg-background-dark flex-col items-center justify-around gap-4 " > 
                                
                            <div className=" w-full flex p-2  justify-end cursor-pointer ">
                                <i onClick={()=>setPopup(null)} className="fa-solid fa-xmark"></i>
                            </div>
                            <div className="h-20 w-full flex p-3 py-4 gap-1  ">
                                <input ref={destinationRef} id="destination"placeholder="Username" className="p-3 focus:outline-hidden dark:border-border-dark dark:focus:border-border-light focus:border-border-dark flex-1 h-full border border-border-light rounded  text-text-light dark:text-text-dark"/>
                            </div>
                            <div className="h-20 w-full flex p-3 py-4 gap-1 border-t dark:border-border-dark border-border-light ">
                                <button className="h-full aspect-square  text-center  text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light" >
                                    <i className="fa-solid fa-paperclip"></i>
                                </button>
                                <input id="message" ref={messageRef} placeholder="Write something..."  className="p-3 focus:outline-hidden dark:border-border-dark dark:focus:border-border-light focus:border-border-dark flex-1 h-full border border-border-light rounded  text-text-light dark:text-text-dark"/>
                                <button className="h-full aspect-square bg-text-light text-background-light text-center   dark:text-background-dark dark:bg-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded " 
                                    onClick={(e)=>{
                                        const destination = destinationRef.current?.value || "";
                                        const message = messageRef.current?.value || "";
                                        if (destination.length !== 0 && message.length !== 0) {
                                            console.log("Call before message send ")
                                            console.log(message,destination)
                                          sendMessage(destination, {
                                            body: message,
                                            isMine: true,
                                            isSeen: true,
                                            sentDate: new Date(),
                                          });
                                        }
                                        setPopup(null)
                                       
                                    }}
                                >
                                    <i className="fa-solid fa-paper-plane "></i>
                                </button>
                            </div>
                        </div>
                    )}
                >
                    <i className="fa-solid fa-plus"></i>
                </button>

            </div>
            {/*Accounts list*/}
            <div className="w-full h-full flex-1 overflow-auto p-3">
                {
                    conversations.map((e,index)=>(
                        <Account 

                            key={index} 
                            contact={e.owner}
                            lastMessage={e.messages[(e.messages.length>0?e.messages.length-1:0)]?.body} 
                            lastMessageTime={e.messages[(e.messages.length>0?e.messages.length-1:0)]?.sentDate} 
                            nbrOfUnSeenMessages={e.messages.filter(e=>!e.isSeen).length}
                            setCurrentContact={setActiveContact}
                            isActive={currentContact==e.owner?true:false}
                            />
                    ))
                }
     
            </div>
        </div>

        {/* conversation container */}
        <div className="h-full w-2/3 flex-1 flex flex-col">
        {   (currentContact!=undefined)?
            /* conversations header */
            <>
            <div className="h-20 border-b w-full  dark:border-border-dark border-border-light flex gap-3 p-3 ">
                <img className="h-full aspect-square rounded-full " src={currentContact?.profile}></img>
                <div className="h-full w-8/10  justify-center flex flex-col  ">
                    <h1 className="w-full flex text-lg font-bold  text-text-light dark:text-text-dark">{currentContact?.name}</h1>
                    <p className="w-full text-secondary-light text-sm">{currentContact?.isOnline?"online":`last seen: ${currentContact?.lastOnline?.toLocaleDateString()}`}</p>
                </div>
                <button className="h-full aspect-square text-center  text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light">
                    <i className="fa-solid fa-phone"></i>
                </button>
                <button className="h-full aspect-square  text-center  text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light" >
                    <i className="fa-solid fa-video"></i>
                </button>
                <button className="h-full aspect-square  text-center  text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light" >
                    <i className="fa-solid fa-ellipsis-vertical"></i>
                </button>
            </div>
            {/* conversation body */}
            <Discussion key={0} conversation={
                conversations.filter((e=>e.owner.name==currentContact?.name))[0]
                }/>

            <div className="h-20 w-full flex p-3 py-4 gap-1 border-t dark:border-border-dark border-border-light ">
                <button className="h-full aspect-square  text-center  text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light" 
                    

                    
                >
                    <i className="fa-solid fa-paperclip"></i>
                </button>
                <input ref={messageRef} placeholder="Write something..." className="p-3 focus:outline-hidden dark:border-border-dark dark:focus:border-border-light focus:border-border-dark flex-1 h-full border border-border-light rounded  text-text-light dark:text-text-dark"/>
                <button className="h-full aspect-square bg-text-light text-background-light text-center   dark:text-background-dark dark:bg-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded " 
                    onClick={()=>{
                            const message = messageRef.current?.value || "";
                            if (currentContact.name.length !== 0 && message.length !== 0) {
                                sendMessage(currentContact.name, {
                                    body: message,
                                    isMine: true,
                                    isSeen: true,
                                    sentDate: new Date(),
                                });
                            }
                           
                        }}
                >
                    <i className="fa-solid fa-paper-plane "></i>
                </button>
            </div>
            </>:
            <div className="flex h-full w-full bg-accent-light dark:bg-accent-dark justify-center items-center ">
                    <i className="fa-regular fa-message  text-center text-9xl text-secondary-light "></i>
            </div>
            }
        </div>
        
    </div>
}


export default Chat;