import { FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/PageLayout';
import { login } from '../api/authApi';
import { saveAuthSession } from '../utils/authStorage';

export default function LoginPage() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const submit = async (e: FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const response = await login({ username, password });
      saveAuthSession(response.token, response.user);
      navigate(response.user.role === 'OWNER' ? '/owner/dashboard' : '/dashboard');
    } catch (err) {
      setError((err as Error).message);
    }
  };

  return (
    <PageLayout title="Login">
      <form onSubmit={submit} className="form-grid">
        <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username" required />
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" required />
        <div className="button-row">
          <button type="submit">Login</button>
          <button type="button" onClick={() => navigate('/')}>Cancel</button>
        </div>
      </form>
      {error && <p className="error">{error}</p>}
    </PageLayout>
  );
}
