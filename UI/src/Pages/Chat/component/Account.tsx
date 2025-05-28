import { ReactElement } from "react";
import Contact from "../../../types/Contact";

type Props =  {
    isActive?:boolean; 
    contact:Contact
    lastMessage:string;
    lastMessageTime:Date;
    nbrOfUnSeenMessages:number;
    setCurrentContact:CallableFunction;
}


function Account({isActive,contact,lastMessage,lastMessageTime,nbrOfUnSeenMessages,setCurrentContact}:Props):ReactElement{
    return <div className={`w-full h-20 p-4    rounded hover:bg-accent-light hover:dark:bg-accent-dark cursor-pointer flex items-center gap-2 ${isActive?"dark:bg-accent-dark bg-accent-light":""}`}
            onClick={()=>setCurrentContact(contact)}
    >
        <img className="h-full aspect-square rounded-full " src={contact.profile}></img>
        <div className="h-full w-7/10 ">
                <div className="flex justify-between items-center h-1/2">
                    <h1 className="h-full text-center  text-text-light dark:text-text-dark ">{contact.name}</h1>
                    <h1 className="h-full text-center text-sm text-secondary-light">
                        {
                        lastMessageTime?.toLocaleTimeString('en-US', {
                        hour: 'numeric',
                        minute: '2-digit',
                        hour12: true,
                        })}</h1>
                </div>
                <p className="text-sm text-secondary-light  max-h-1/2 line-clamp-1">{lastMessage}</p>
        </div>
        {
            nbrOfUnSeenMessages!=0?
        <div className="h-full w-1/10 flex justify-end">
            <h1 className=" aspect-square h-7 rounded-full bg-text-light  text-background-light dark:bg-text-dark dark:text-background-dark text-xs flex items-center justify-center text-center">{nbrOfUnSeenMessages}</h1>
        </div>:
        <></>
        }   
    </div>
}



export default Account;