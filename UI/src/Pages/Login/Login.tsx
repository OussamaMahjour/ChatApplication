import { ReactElement, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../provider/AuthProvider";
import FormInput from "../../components/FormInput";
import Card from "../../components/Card";
import ThemeButton from "../../components/ThemeButton";
import ButtonInverse from "../../components/ButtonInverse";
import Exception, { ExceptionType } from "../../types/Exception";
import { useTheme } from "../../provider/ThemeProvider";





function Login():ReactElement{

    const [username,setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const {login} = useAuth();
    const [error,setError] = useState<string>()
    const {setAlert} = useTheme()


  
    const navigate = useNavigate();
   
    const handleLogin = async () => {
        if ((username.length!=0) || (password.length!=0)) {
            try{
                await login(username,password)
                navigate("/chat")
            }catch(e: unknown){
                if(e instanceof Exception){
                    if(e.type == ExceptionType.BAD_CREDENTIAL){
                        if (!username || !password) {
                    
                    }else{
                        setAlert({message:"Unable to Login! Try Again Later.",timeout:2000})
                    }
                    
                }else {
                    console.error("Unknown error during login:", e);
                }
            }}
        }else{
                setAlert({message:"Both Usename and Password should be set",timeout:2000})
        }
        
        
    };



    return <Card className="min-w-1/4 w-100 max-h-300 h-2/3  gap-4 px-13">
                <Card className="  h-fit w-fit absolute top-10 left-10 ">
                    <ThemeButton className="w-10 "/>
                </Card> 
            <h1 className="h-30 mb-10 w-full text-center justify-center flex items-center font-bold text-2xl dark:text-text-dark text-text-light">
                    Login
            </h1>
            <div className={`gap-4 flex flex-col w-full items-center flex-1 ${error?"invalid":""}`}>
                <FormInput id="username" placeholder="Username" errorMessage={error} className="w-full"  onChange={(e)=>{setUsername(e.target.value)}} ></FormInput>
                 
                 <FormInput id="password" placeholder="Password"   errorMessage={error} type="password" className="w-full"  onChange={(e) => setPassword(e.target.value)} ></FormInput>
                    <ButtonInverse onClick={handleLogin} className="p-3 w-1/3 max-w-40      " >
                        Connect
                    </ButtonInverse>
                <h1 className="dark:text-text-dark text-text-light">don't have an account?  <a className="text-[#6db7f0] hover:underline cursor-pointer" onClick={()=>navigate("/signup")}>Signup</a></h1>
            </div>
            
    </Card>
}


export default Login;