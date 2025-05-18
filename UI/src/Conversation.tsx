import { useState } from "react";
import Message from "./Message";

type ConversationType = {
    master:string | any;
    conversation:Message[] | any;
    send:CallableFunction
  }
function Conversation({master,conversation,send}:ConversationType){
    const [message,setMessage] = useState("")
    


    return <div className="h-full w-full bg-white z-99">
            <h1 className="w-full h-1/15 border-b">{master}</h1>
            <div className="w-full h-13/15 p-3">
                {
                    conversation.map((e,index)=>(
                        e?.source==master?
                        <div className="flex justify-start items-end pt-1">
                            <h1 className="h-full p-2 bg-black text-white  rounded-full">
                                {e.text}
                            </h1>
                        </div>
                        :
                        <div className="flex justify-end items-center pt-1">
                            <h1 className="h-full p-2 bg-[#999999] text-black rounded-full">
                                {e.text}
                            </h1>
                        </div>
                    ))
                            

                
                }
            </div>
            <div className="h-1/15  w-full">
                <input className="h-full w-4/5 border" placeholder="Message..." onChange={(e)=>{setMessage(e.target.value)}}/>
                <button className="h-full bg-black text-white w-1/5 cursor-pointer" onClick={(e)=>send(master,message)}>Send</button>
            </div>
    </div>
}



export default Conversation