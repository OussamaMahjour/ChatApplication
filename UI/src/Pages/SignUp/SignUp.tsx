import { ReactElement, useState } from "react";
import {useNavigate } from "react-router-dom";
import User, { Gender } from "../../types/User";
import FormInput from "../../components/FormInput";
import Card from "../../components/Card";
import ThemeButton from "../../components/ThemeButton";
import ButtonInverse from "../../components/ButtonInverse";




function SignUp():ReactElement{

    const [user,setUser] = useState<User>({username:""})
    const [password,setPassword] = useState("")    
    

    const handleSignup = async () => {
        if (!user.username || !password) {
                alert("Please enter both username and password");
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/api/v1/auth/signup", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({...user,password:password}),
                });

                if (!response.ok) {
                    throw new Error("Signup failed");
                }               
                navigate("/login");
            } catch (error) {
                alert("Login failed: " + error);
            }
    };

    const navigate = useNavigate();

    return <Card className="min-w-1/4 w-120  max-h-400 h-3/4  gap-4 px-13">
                <Card className="  h-fit w-fit absolute top-10 left-10 ">
                    <ThemeButton />
               </Card> 
            <h1 className="h-30 mb-10 w-full text-center justify-center flex items-center font-bold text-2xl dark:text-text-dark text-text-light">
                    Signup 
            </h1>
            <div className="gap-4 flex flex-col w-full items-center flex-1">
                <div className="gap-4 flex">
                    <FormInput placeholder="First Name" id="firstname" className="w-1/2" onChange={(e)=>{setUser(user=>{
                        user.firstname=e.target.value
                        return user
                        }) }} />
                    <FormInput placeholder="Last Name" id="lastname" onChange={(e)=>{setUser(user=>{
                        user.lastname=e.target.value
                        return user
                        }) }} className="w-1/2"/>
                 </div>
                
                <FormInput placeholder="Email" id="email" type="email" onChange={(e)=>{setUser(user=>{
                    user.email=e.target.value
                    return user
                    }) }} className="w-full"/>
                
                
                <FormInput placeholder="Username" id="username" onChange={(e)=>{setUser(user=>{
                    user.username=e.target.value
                    return user
                    }) }} className=" w-full"/>
               
                <FormInput placeholder="BirthDate" id="birthdate" type="date" onChange={(e)=>{setUser(user=>{
                    if(e.target.valueAsDate)user.birthday=e.target.valueAsDate
                    return user
                    }) }} className=" w-full  "/>
                
                <select
                onChange={(e)=>{
                    setUser(user=>{
                            user.gender=e.target.value as Gender
                            return user
                        })
                }}
                className="p-3 focus:outline-hidden dark:border-border-dark dark:focus:border-border-light focus:border-border-dark w-full  border border-border-light rounded  text-text-light dark:text-text-dark"
                    >
                        <option value={Gender.MALE}>Male</option>
                        <option  value={Gender.FEMALE}>Female</option>
                    </select>

                <FormInput placeholder="Password" id="password" onChange={(e)=>{setPassword(e.target.value) }} className="w-full " />
                    <ButtonInverse onClick={handleSignup} className=" w-1/3 max-w-40  " >
                        SignUp
                    </ButtonInverse>
            </div>
            
    </Card>
}


export default SignUp;