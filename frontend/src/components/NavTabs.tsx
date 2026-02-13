import { Link, useLocation } from 'react-router-dom';

const tabs = [
  { label: 'Home', to: '/' },
  { label: 'Register', to: '/register' },
  { label: 'Login', to: '/login' }
];

export default function NavTabs() {
  const location = useLocation();

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
