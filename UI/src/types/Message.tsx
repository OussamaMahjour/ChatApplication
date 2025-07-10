import { AttachementType } from "./Attachement";

type Message = {
    id?:string
    body:string;
    owner:string;
    seen:boolean;
    type:MessageType | AttachementType;
    sentAt:Date;
  
}

export enum  MessageType {
    VIDEO="VIDEO",
    IMAGE="IMAGE",
    AUDIO="AUDIO",
    DOCUMENT="DOCUMENT",
    TEXT="TEXT"
}
export default Message;