import { ReactElement, ReactNode } from "react";
import { useAuth } from "./AuthProvider";
import { Navigate } from "react-router-dom";



const PublicRoute = ({ children }:{ children: ReactNode}) => {
  const { isAuthenticated } = useAuth();
  return !isAuthenticated  ? children : <Navigate to="/" replace />;
};


export default PublicRoute;