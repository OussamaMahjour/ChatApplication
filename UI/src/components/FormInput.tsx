import { ReactElement} from "react";

type Props =  React.InputHTMLAttributes<HTMLInputElement> & {
    className?:string;
    placeholder?:string;
    id:string;
    inClassName?:string;
    errorMessage?:string

}


const FormInput = ({id,className,placeholder,errorMessage,inClassName,...rest}:Props):ReactElement=>{
    
    
    return <div className={`${className}`}>
        <div className={`relative w-full`}>
            <input 
                    {...rest} 
                    aria-describedby={`${id}_error_help`}
                    id={id} className={`
                    block 
                    px-2.5 pb-2.5 pt-4  
                    text-text-light  dark:text-text-dark
                    bg-transparent
                    rounded
                    border-1 border-border-light dark:border-border-dark
                  dark:focus:border-border-light focus:border-border-dark
                    appearance-none         
                    focus:outline-none focus:ring-0 
                    peer
                    w-full
                    dark:invalid:border-red-400
                    invalid:border-red-400
                    ${inClassName}
                    `} 
                    placeholder=" " />
            
            
            <label htmlFor={id} className="
                    absolute 
                    text-secondary-light dark:text-secondary-light
                    duration-300 transform -translate-y-4 scale-75 
                    top-2 z-10 origin-[0] 
                    bg-background-light dark:bg-background-dark 
                    px-2 peer-focus:px-2 
                    peer-focus:text-secondary-light peer-focus:dark:text-secondary-light
                    peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2
                    peer-focus:top-1 peer-focus:scale-75 peer-focus:-translate-y-4 
                    rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto 
                    start-1
                    invalid:text-red-600 dark:invalid:text-red-500
                    "
                    >{placeholder}</label>
                
            </div>
            <p id={`${id}_error_help`}
                className="mt-2 px-2 text-xs text-red-600 dark:text-red-400 hidden invalid:inline">{errorMessage}</p>    
        </div>
   
}


export default FormInput;