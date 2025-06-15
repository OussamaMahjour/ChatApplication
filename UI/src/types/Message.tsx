type Message = {
    id?:string
    body:string;
    owner:string;
    seen:boolean;
    MessageType:string;
    sentAt:Date;
  
}

export default Message;