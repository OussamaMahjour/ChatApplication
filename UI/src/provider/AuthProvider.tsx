import {createContext,ReactNode, useContext, useEffect,useState } from "react";
import User from "../types/User";
import Exception from "../types/Exception";


type AuthContextType = {
  token: string | null;
  login: (user: string,password:string) => void;
  logout: () => void;
  isAuthenticated: boolean;
  user:User | null
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
            console.log("throwing exception")
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



  if (loading) return null;


  return (
    <AuthContext.Provider value={{ token, login, logout, isAuthenticated ,user}}>
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