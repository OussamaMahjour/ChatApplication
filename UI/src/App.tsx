import { ReactElement} from "react";
import Chat from "./Pages/Chat/Chat";
import { createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./Pages/Login/Login";

import SignUp from "./Pages/SignUp/SignUp";
import { PrivateRoute } from "./provider/PrivateRoute";
import Home from "./Pages/Home/home";
import PublicRoute from "./provider/PublicRoute";
import Test from "./Pages/Test";





function  App():ReactElement {


    const publicRountes = [
        {
            path:"/about",
            element:<>about</>
        },
        {
            path:"/test",
            element:<Test></Test>
        }
    ]

    const authenticatedRoutesOnly = [
        {
            path:"/",
            element:<PrivateRoute><Home/></PrivateRoute>
        },
        {
            path:"/chat",
            element:<PrivateRoute><Chat /></PrivateRoute>
        }
        

    ]
    const noneAuthenticatedRoutes = [
        {
            path:"/login",
            element:<PublicRoute><Login /></PublicRoute>
        },
        {
            path:"/signup",
            element:<PublicRoute><SignUp /></PublicRoute>
        }
    ]
    const router = createBrowserRouter([
        ...publicRountes,
        ...noneAuthenticatedRoutes,
        ...authenticatedRoutesOnly
    ])

   
    return <> 
    
            <div className={`h-screen w-screen relative p-4 bg-primary-light dark:bg-primary-dark *:transition *:duration-300 *:ease-in-out  flex justify-center items-center` }>    
                
                    <RouterProvider router={router} />
            
            </div> 
        
    </>

}

export default App