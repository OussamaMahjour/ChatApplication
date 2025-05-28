// src/routes/PrivateRoute.tsx
import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';
import { ReactNode } from 'react';

export const PrivateRoute = ({ children }:{ children: ReactNode }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated  ? children : <Navigate to="/login" replace />;
};
