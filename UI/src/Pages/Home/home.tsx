import { ReactElement, useEffect} from "react";
import { useNavigate } from "react-router-dom";



function  Home():ReactElement {
    const navigate = useNavigate()
    useEffect(()=>{
        navigate("/chat")
    },[])
    
    return <>
    </>
}


export default Home;