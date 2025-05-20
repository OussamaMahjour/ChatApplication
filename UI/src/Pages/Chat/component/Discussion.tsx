import { ReactElement } from "react";
import Conversation from "../../../types/Conversation";

type Props = {
    conversation:Conversation | null
}
function Discussion({conversation}:Props):ReactElement{
    return <div className="flex-1 w-full p-3 flex flex-col gap-3">
        {
            conversation?.messages.map((e)=>(
                e.isMine?
                <div className="transition-none w-full flex gap-4 justify-end items-center hover:[&_p]:visible">
                   
                    <p className="text-sm text-secondary-light dark:text-accent-light  invisible transition-none">
                        {e.sentDate.toLocaleTimeString('en-US', {
                        hour: 'numeric',
                        minute: '2-digit',
                        hour12: true,
                        })}
                    </p> 
                    <h1 className="p-3  rounded-lg  text-accent-light dark:text-accent-dark bg-text-light dark:bg-text-dark">
                        {e.body}
                    </h1>
                </div>:
                <div className="transition-normal duration-0 hover:[&_p]:visible w-full flex justify-start items-center gap-4 d-none">
                    <h1 className="p-3 rounded-lg bg-accent-light text-text-light dark:bg-accent-dark dark:text-text-dark ">
                        {e.body}
                    </h1>
                    <p className="text-sm  text-secondary-light dark:text-accent-light invisible transition-normal duration-0">
                        {e.sentDate.toLocaleTimeString('en-US', {
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