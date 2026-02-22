import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/PageLayout';
import { clearAuthSession } from '../utils/authStorage';

export default function DashboardPage() {
  const navigate = useNavigate();

  return (
    <PageLayout title="Dashboard">
      <p>Wait for next things to come up</p>
      <button
        type="button"
        onClick={() => {
          clearAuthSession();
          navigate('/');
        }}
      >
        Logout
      </button>
    </PageLayout>
  );
}
