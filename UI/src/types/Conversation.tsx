import Contact from "./Contact";
import Message from "./Message";

type Conversation = {
    owner:Contact;
    messages:Message[];
}


export default Conversation;