import { ReactElement, useState } from "react";
import Chat from "./Pages/Chat/Chat";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./Pages/Login/Login";
import User from "./types/User";




function  App():ReactElement {

    const [theme,setTheme] = useState("light")
    const [user,setUser] = useState<User>()


   
    return <>
        <div className={`h-screen w-screen relative p-4 bg-primary-light dark:bg-primary-dark *:transition *:duration-300 *:ease-in-out ${theme} flex justify-center items-center` }> 
           <Router> 
                <Routes>
                    <Route path="/chat" element={ <Chat user={user} setTheme={setTheme} theme={theme} />}></Route>
                    <Route path="/" element={ <h1>hello</h1>}></Route>
                    <Route path="/login" element={<Login setUser={setUser} setTheme={setTheme} theme={theme}/>} ></Route>
                </Routes>
            </Router>
        </div>
    </>

}

export default App