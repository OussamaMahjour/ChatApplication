import { ReactElement, useEffect, useRef } from "react";
import Conversation from "../../../types/Conversation";
import User from "../../../types/User";

type Props = {
    conversation:Conversation | null
    user:User
}
function Discussion({conversation,user}:Props):ReactElement{
    
    const lastMessageRef = useRef<HTMLDivElement | null>(null);    
    
    //Setup the focus to the last message so it's appears in the converstion
    useEffect(() => {
        if (lastMessageRef.current) {
        lastMessageRef.current.focus();
        setTimeout(() => {
        lastMessageRef.current?.blur();
        }, 100);
        }
    }, [conversation]);


    return <div  className="flex-1 w-full p-3 flex flex-col gap-3 overflow-y-auto scr">
        {
            conversation?.messages.map((e,index)=>(
                e.owner==user.username?
                <div className="transition-none w-full flex gap-4 justify-end items-center hover:[&_p]:visible">
                   
                    <p className="text-sm text-secondary-light dark:text-accent-light  invisible transition-none">
                        {e.sentAt.toLocaleTimeString('en-US', {
                        hour: 'numeric',
                        minute: '2-digit',
                        hour12: true,
                        })}
                    </p> 
                    <h1 tabIndex={-1} ref={index === conversation.messages.length - 1 ? lastMessageRef : null} className="p-3  rounded-lg  text-accent-light dark:text-accent-dark bg-text-light dark:bg-text-dark">
                        {e.body}
                    </h1>
                </div>:
                <div className="transition-normal duration-0 hover:[&_p]:visible w-full flex justify-start items-center gap-4 d-none">
                    <h1 tabIndex={-1} ref={index === conversation.messages.length - 1 ? lastMessageRef : null} className="p-3 rounded-lg bg-accent-light text-text-light dark:bg-accent-dark dark:text-text-dark ">
                        {e.body}
                    </h1>
                    <p className="text-sm  text-secondary-light dark:text-accent-light invisible transition-normal duration-0">
                        {e.sentAt.toLocaleTimeString('en-US', {
                        hour: 'numeric',
                        minute: '2-digit',
                        hour12: true,
                        })}
                    </p> 
                </div>
            ))
        }
    </div>
}

export default Discussion;