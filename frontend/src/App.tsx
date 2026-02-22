import { Navigate, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import RegisterChoicePage from './pages/RegisterChoicePage';
import RegisterUserPage from './pages/RegisterUserPage';
import RegisterEmployeePage from './pages/RegisterEmployeePage';
import RegisterOwnerPage from './pages/RegisterOwnerPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import ProtectedRoute from './components/ProtectedRoute';
import OwnerRoute from './components/OwnerRoute';
import OwnerDashboardPage from './pages/OwnerDashboardPage';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/register" element={<RegisterChoicePage />} />
      <Route path="/register/user" element={<RegisterUserPage />} />
      <Route path="/register/employee" element={<RegisterEmployeePage />} />
      <Route path="/register/owner" element={<RegisterOwnerPage />} />
      <Route path="/login" element={<LoginPage />} />

      <Route element={<ProtectedRoute />}>
        <Route path="/dashboard" element={<DashboardPage />} />
      </Route>

      <Route element={<OwnerRoute />}>
        <Route path="/owner/dashboard" element={<OwnerDashboardPage />} />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
