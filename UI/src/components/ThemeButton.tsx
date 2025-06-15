import { useTheme } from "../provider/ThemeProvider"
import Button from "./Button"


type Props = {
    className?:string
}

const ThemeButton:React.FC<Props> = ({className}:Props)=>{

    const {theme,toggleTheme} = useTheme()
    
    return <Button onClick={toggleTheme} className={`aspect-square ${className}`}>
             <i className={`fa-regular ${theme=="light"?"fa-sun":"fa-moon"}`}></i>
        </Button>

}

export default ThemeButton;