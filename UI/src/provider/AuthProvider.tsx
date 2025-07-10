import {createContext,ReactNode, useContext, useEffect,useRef,useState } from "react";
import User from "../types/User";
import Exception from "../types/Exception";
import gsap from "gsap";


type AuthContextType = {
  token: string | null;
  login: (user: string,password:string) => void;
  logout: () => void;
  isAuthenticated: boolean;
  user:User | null;
  openSetting:()=>void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);


const AuthProvider = ({ children }: { children: ReactNode })=>{

  const [token, setToken] = useState<string | null>(() => localStorage.getItem('token'));
  const [user,setUser] = useState<User | null>(null)
  const [isAuthenticated,setIsAuthenticated] = useState(false)
  const [loading, setLoading] = useState(true);

    useEffect(() => {
      const initialize = async () => {
        if (token != null) {
          await getUser(token);
        }
        setLoading(false); 
      };
      initialize();
    }, []);
    
    
    

    const getUser = async (token:string)=>{
          try {
         const response = await fetch(`http://localhost:8080/api/v1/users`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
                });
                if (!response.ok) throw new Error(`Status: ${response.status}`);
                const userData:User = await response.json();
                setUser(userData)
                setIsAuthenticated(true)
            } catch (err) {
                console.error("Failed to fetch user:", err);
            }
            
    }

  const login = async (username:string,password:string) => {
      if (!username || !password) {
            alert("Please enter both username and password");
            return;
      }
    
        const response = await fetch("http://localhost:8080/api/v1/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ "username":username, "password":password }),
        });

        if (!response.ok){
            const exception:Exception = await response.json()
            console.log("throwing exception",exception)
            throw new Exception(exception.error,exception.type);
        }

        const data = await response.json();
        localStorage.setItem("token", data.token);
        setToken(data.token)
        const userResponse = await fetch(`http://localhost:8080/api/v1/users/${username}`,{
            headers:{
                Authorization:`Bearer ${data.token}`,
            }
        })
        const userData = await userResponse.json()
        localStorage.setItem("username",userData.username)
        setUser(userData);
        setIsAuthenticated(true)
           
        
  };

  
  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
    setIsAuthenticated(false)
    window.location.href = "/login";
  };
  
  const settingContainer = useRef<HTMLDivElement | null>(null)
  const settingRef = useRef<HTMLDivElement | null>(null)

  
  const openSetting = () =>{
      if(!settingContainer.current || !settingRef.current)return
      let ts = gsap.timeline()
      ts.to(settingContainer.current,{opacity:100,duration:0,onComplete:()=>{
        settingContainer.current?.classList.add("absolute")
        settingContainer.current?.classList.remove("hidden")
      }})
      .fromTo(settingRef.current,{right:-gsap.getProperty(settingRef.current,"width")},{right:0,duration:0.4,ease:"power2.in"})
      console.log("opening")
  }
  const closeSetting = () =>{
    if(!settingContainer.current || !settingRef.current)return
      let ts = gsap.timeline()
      ts.fromTo(settingRef.current,{right:0},{right:-gsap.getProperty(settingRef.current,"width"),duration:0.2,ease:"power2.out"})
      
      .to(settingContainer.current,{opacity:0,duration:0,onComplete:()=>{
        settingContainer.current?.classList.add("hidden")
        settingContainer.current?.classList.remove("absolute")

      }})
      
  }

  const inputFile = useRef<HTMLInputElement|null>(null)
  const changeProfile = async (file:File)=>{
    const formData = new FormData();
    formData.append("context", "profile");
    formData.append("medias", file);
    let response  = await fetch(`http://localhost:8080/api/v1/users/${user?.username}/profile`,{
        method: "PUT",
        body:formData,
        headers: {
              Authorization: `Bearer ${token}`,
          }
      })
    let body = await response.json()
    setUser(body)
  }

  const deleteAccount = async ()=>{
    let response = await fetch("http://localhost:8080/api/v1/users",{
      method:"DELETE",
      headers: {
              Authorization: `Bearer ${token}`,
          }
    })
    if(response.status == 200){
      logout()
    }
  }

  if (loading) return null;


  return (
    <AuthContext.Provider value={{ token, login, logout, isAuthenticated ,user,openSetting}}>
      {user?
      <div className="w-screen h-screen opacity-100 absolute hidden z-100 " ref={settingContainer}>
          <div className="w-full h-full fixed bg-background-dark dark:bg-background-light z-100 opacity-40 " onClick={closeSetting}>

          </div>
          <div ref={settingRef} className="w-2/5 min-w-100 h-full bg-background-light dark:bg-background-dark opacity-100 z-101  absolute flex flex-col gap-5 pb-3">
                <div className="w-full h-15"></div>
                <div className="w-full flex justify-center items-center">
                    <div className="w-40 aspect-square rounded-full  relative overflow-hidden">
                          <input onChange={(e)=>{
                              if(!e.target.files)return
                              let file = e.target.files[0]
                              changeProfile(file);

                           }}  type='file' id='file' ref={inputFile} style={{display: 'none'}}/>
                          <img src={"http://localhost:8080/api/v1/media/"+user?.profilePicture} className="p-2 w-full aspect-square" alt="Profile" />
                          <div className="w-full h-full opacity-0 hover:opacity-30 flex absolute top-0 left-0 bg-accent-dark   justify-center items-center text-2xl cursor-pointer"
                            onClick={()=>{
                                inputFile.current?.click()
                            }}
                            >
                              <i className="fa-solid fa-camera text-text-dark"></i>
                          </div>
                    </div>
                    
                </div>
                <div className="flex-1">
                  
                </div>
                <div className="w-full  flex justify-center items-center text-red-500 gap-3 cursor-pointer " onClick={deleteAccount}>
                      <i className="fa-solid fa-trash"></i>
                      <h1>Delete Account</h1>
                </div>
                <div className="w-full  flex justify-center items-center text-red-500 gap-3 cursor-pointer " onClick={logout}>
                      
                    <button className="h-full   "  >
                        <i className="fa-solid bottom-0 fa-right-from-bracket rotate-180 "></i>
                    </button>
                    <h1 className="">Log Out</h1> 
                    
                    
                </div>
          </div>
      </div>
      :<></>
      }
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within AuthProvider');
  return context;
};

export default AuthProvider;