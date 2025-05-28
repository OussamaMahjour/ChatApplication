import { createContext, Dispatch, ReactNode, SetStateAction, useContext, useEffect, useRef, useState } from "react";
import Alert from "../components/Alert";

type ThemeContext = {
    theme:string | null;
    toggleTheme:()=>void ;
    setAlert:Dispatch<SetStateAction<{ message: string; timeout: number; }>>
}

const ThemContext = createContext<ThemeContext | undefined>(undefined)
  

const ThemeProvider = ({ children }: { children: ReactNode })=>{
         const [theme,setTheme] = useState<string | null>(()=>localStorage.getItem("theme"));
         const [alertMessage,setAlert]  = useState<{message:string,timeout:number}>({message:"",timeout:0})
         
    
        const toggleTheme = ()=>{
            if(theme=="light"){
                setTheme(()=>{
                    localStorage.setItem("theme","dark")
                    return "dark"
                })
            }else{
                setTheme(()=>{
                    localStorage.setItem("theme","light")
                    return "light"
                })
            }
           
        }
        
        
        const alertRef = useRef<HTMLDivElement | null>(null);
        
        useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
            if (!entry.isIntersecting) {
                setAlert({message:"",timeout:0})
            } 
            });
        });
    
        if (alertRef.current) {
            observer.observe(alertRef.current);
        }
    
        return () => {
            if (alertRef.current) {
            observer.unobserve(alertRef.current);
            }
        };
        }, []);
       

    return <ThemContext.Provider value={{theme,toggleTheme,setAlert}}>
        
        <div className={`${theme}  h-full w-full relative `}>
            <Alert ref={alertRef} alert={alertMessage} ></Alert>
            {children}
        </div>
    </ThemContext.Provider>
}


export const useTheme = () =>{
    const themeContext = useContext(ThemContext);
      if (!themeContext) throw new Error('useTheme must be used within ThemeProvider');
      return themeContext;
}

export default  ThemeProvider