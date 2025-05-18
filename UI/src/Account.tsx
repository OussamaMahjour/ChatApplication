import React from "react";
import Message from "./Message";




type Props = {
    name: string;
    func: CallableFunction
  };



function Account({name,func}:Props){
    
    
    
    return (<div className="w-full h-20 border-b pl-3 py-1 flex" onClick={(e)=>func(name)}>
            
            <div className="rounded-full h-full w-1/5 bg-black text-white text-5xl flex justify-center itms-center text-center">{name[0].toUpperCase()}</div>
            <h1 className="h-full w-4/5 flex pl-4 items-center text-2xl text-bold">{name.toUpperCase()}</h1>
    </div>)
}


export default Account;