import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/PageLayout';

export default function RegisterChoicePage() {
  const navigate = useNavigate();
  return (
    <PageLayout title="Register">
      <fieldset>
        <legend>Select Registration Type</legend>
        <label>
          <input type="radio" name="registerType" onChange={() => navigate('/register/user')} /> Register As User
        </label>
        <label>
          <input type="radio" name="registerType" onChange={() => navigate('/register/employee')} /> Register As
          Employee
        </label>
        <label>
          <input type="radio" name="registerType" onChange={() => navigate('/register/owner')} /> Register Your
          Hostel (Owner)
        </label>
      </fieldset>
      <button type="button" onClick={() => navigate('/')}>Home</button>
    </PageLayout>
  );
}
