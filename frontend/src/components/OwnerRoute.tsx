import { Navigate, Outlet } from 'react-router-dom';
import { getAuthUser, isAuthenticated } from '../utils/authStorage';

export default function OwnerRoute() {
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }

  const user = getAuthUser();
  if (!user || user.role !== 'OWNER') {
    return <Navigate to="/dashboard" replace />;
  }

  return <Outlet />;
}
