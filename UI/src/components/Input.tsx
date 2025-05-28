import { forwardRef} from "react";

type Props =  React.InputHTMLAttributes<HTMLInputElement> & {
    className?:string;
    placeholder?:string;
    id:string;
    inClassName?:string;

}


const Input = forwardRef<HTMLInputElement,Props>(({id,className,placeholder,inClassName,...rest},ref)=>{
    
    return <input 
            ref={ref}
            {...rest} 
            id={id} 
            className={`
            p-3 
            
            focus:outline-hidden dark:border-border-dark dark:focus:border-border-light 
            focus:border-border-dark flex-1 h-full border  
            border-border-light rounded  
            text-text-light 
            dark:text-text-dark
            
            ${inClassName}
            `} 
            placeholder={placeholder} />


})


export default Input;