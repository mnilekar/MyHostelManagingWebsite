import { Link, useLocation } from 'react-router-dom';
import { isAuthenticated } from '../utils/authStorage';

const guestTabs = [
  { label: 'Home', to: '/' },
  { label: 'Register', to: '/register' },
  { label: 'Login', to: '/login' }
];

const loggedInTabs = [{ label: 'Login', to: '/login' }];

export default function NavTabs() {
  const location = useLocation();
  const tabs = isAuthenticated() ? loggedInTabs : guestTabs;

  return (
    <nav className="nav-tabs">
      {tabs.map((tab) => (
        <Link key={tab.to} to={tab.to} className={location.pathname === tab.to ? 'active' : ''}>
          {tab.label}
        </Link>
      ))}
    </nav>
  );
}
