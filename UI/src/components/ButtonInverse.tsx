
type Props = React.ButtonHTMLAttributes<HTMLButtonElement> & {
    className?:string;
}



const ButtonInverse:React.FC<Props> = ({children,className,...rest})=>{
    return <button className={`
        p-3 
        bg-text-light 
        text-background-light text-center 
        dark:text-background-dark dark:bg-text-dark flex 
        justify-center items-center font-bold  cursor-pointer rounded
        ${className}
    `} {...rest}>
            {children}
    </button>
}

export default ButtonInverse