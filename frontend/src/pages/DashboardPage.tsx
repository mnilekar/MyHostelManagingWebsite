import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/PageLayout';

export default function DashboardPage() {
  const navigate = useNavigate();

  return (
    <PageLayout title="Dashboard">
      <p>Wait for next things to come up</p>
      <button
        type="button"
        onClick={() => {
          sessionStorage.removeItem('authToken');
          navigate('/');
        }}
      >
        Logout
      </button>
    </PageLayout>
  );
}
