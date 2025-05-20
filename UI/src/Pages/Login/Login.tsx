import { ReactElement, useState } from "react";
import { Link, useNavigate } from "react-router-dom";



type Props= {
    theme:string;
    setTheme:CallableFunction;
    setUser:CallableFunction;
}


function Login({theme,setTheme,setUser}:Props):ReactElement{

    const [username,setUsername] = useState<string>("")

    const toggleTheme = ()=>{
        if(theme=="light"){
            setTheme("dark")
        }else{
            setTheme("light")
        }
    }

    const navigate = useNavigate();

    return <div className="min-w-1/4 w-100 rounded max-h-300 h-2/3  shadow-md dark:shadow-secondary-dark  border  border-accent-light flex bg-background-light dark:bg-background-dark flex-col items-center justify-around gap-4 px-13">
             <button onClick={toggleTheme} className="aspect-square w-10 absolute top-10 left-10 bg-background-light dark:bg-background-dark  text-center text-text-light dark:text-text-dark flex justify-center items-center font-bold text-xl cursor-pointer rounded hover:dark:bg-accent-dark hover:bg-accent-light">
                    <i className={`fa-regular ${theme=="light"?"fa-sun":"fa-moon"}`}></i>
            </button>
            <h1 className="h-30 mb-10 w-full text-center justify-center flex items-center font-bold text-2xl dark:text-text-dark text-text-light">
                    Login
            </h1>
            <div className="gap-4 flex flex-col w-full items-center flex-1">
                <input placeholder="Username" onChange={(e)=>{setUsername(e.target.value)}} className="p-3 focus:outline-hidden dark:border-border-dark dark:focus:border-border-light focus:border-border-dark w-full  border border-border-light rounded  text-text-light dark:text-text-dark"/>
                <input placeholder="Password" className="p-3 focus:outline-hidden dark:border-border-dark dark:focus:border-border-light focus:border-border-dark w-full  border border-border-light rounded  text-text-light dark:text-text-dark" />
                    <button onClick={()=>{
                        if(username.length !=0){
                            setUser({username:username})
                            navigate("/chat")
                        }else{
                            alert("insert a username first")
                        }
                        }} className="p-3 w-1/3 max-w-40   bg-text-light text-background-light text-center   dark:text-background-dark dark:bg-text-dark flex justify-center items-center font-bold  cursor-pointer rounded " >
                    SignUp
                    </button>
            </div>
            
    </div>
}


export default Login;