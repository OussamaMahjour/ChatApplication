import { ReactElement } from "react"


type Props =   React.InputHTMLAttributes<HTMLInputElement> & {
   className?:string
}


const Card:React.FC<Props> = ({children,className,...rest}) =>{
    return <div className={`
    bg-background-light dark:bg-background-dark 
    flex flex-col 
    justify-center 
    shadow-md dark:shadow-secondary-dark  
    border  border-accent-light 
    rounded 
    ${className}
    `} {...rest}>
            {children}
    </div>
}

export default Card