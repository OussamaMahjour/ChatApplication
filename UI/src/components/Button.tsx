
type Props = React.ButtonHTMLAttributes<HTMLButtonElement> & {
    className?:string;
}



const Button:React.FC<Props> = ({children,className,...rest})=>{
    return <button className={`
        p-3
        bg-background-light dark:bg-background-dark  
        text-center text-text-light 
        dark:text-text-dark 
        flex justify-center items-center 
        cursor-pointer rounded 
        hover:dark:bg-accent-dark 
        hover:bg-accent-light
        ${className}
    `} {...rest}>
            {children}
    </button>
}

export default Button