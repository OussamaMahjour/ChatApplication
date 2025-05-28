import { useEffect, useState ,forwardRef} from "react";
import { } from "react";


type Props = {
    alert:{message:string,timeout:number}
}


const Alert = forwardRef<HTMLDivElement,Props>(({alert},ref)=>{
    const [show,setShow] = useState(false)

      useEffect(()=>{
            if(alert.message.length!=0){
                setShow(true);
                setTimeout(() => {
                    setShow(false);
                }, alert.timeout);
            }
                
        },[alert])
    
    return <div ref={ref} id="alert-2" 
        className= {`
        flex 
        absolute 
        z-100 
        left-1/2
        center
        -top-16 
        items-center 
        p-4 mb-4 
        text-red-800 
        rounded-lg 
        bg-red-50 
        dark:bg-gray-800 
        dark:text-red-400
        border border-red-300 
        dark:border-red-800
        transform -translate-x-1/2 
        transition-transform duration-500
        ${!show? 'translate-y-0' : 'translate-y-20'}
        `} 
        role="alert">
            
            <svg className="shrink-0 w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
            </svg>
            <span className="sr-only">Info</span>
            <div className="ms-3 text-sm font-medium">
                {alert.message}
            </div>
            </div>
})




export default Alert;