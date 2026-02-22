import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PopupMessage from '../components/PopupMessage';
import { clearAuthSession, getAuthUser } from '../utils/authStorage';

const ownerTabs = ['Create Hostel', 'My Employees', 'My'] as const;

export default function OwnerDashboardPage() {
  const navigate = useNavigate();
  const user = getAuthUser();
  const [activeTab, setActiveTab] = useState<(typeof ownerTabs)[number]>('Create Hostel');
  const [popupMessage, setPopupMessage] = useState('');
  const [showUserTooltip, setShowUserTooltip] = useState(false);

  const initials = user?.name
    .split(' ')
    .filter(Boolean)
    .map((part) => part[0])
    .join('')
    .slice(0, 2)
    .toUpperCase() || 'U';

  return (
    <main className="owner-dashboard">
      <header className="owner-dashboard-header">
        <h1>Welcome to MyHostelApp</h1>
        <div className="owner-header-actions">
          <div
            className="avatar-wrap"
            onMouseEnter={() => setShowUserTooltip(true)}
            onMouseLeave={() => setShowUserTooltip(false)}
          >
            <button
              type="button"
              className="user-avatar"
              onClick={() => setPopupMessage('It is currently being implemented')}
            >
              {initials}
            </button>
            {showUserTooltip && user && (
              <div className="profile-tooltip">
                <p><strong>Name:</strong> {user.name}</p>
                <p><strong>Username:</strong> {user.username}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Role:</strong> {user.role}</p>
              </div>
            )}
          </div>
          <button
            type="button"
            onClick={() => {
              clearAuthSession();
              navigate('/');
            }}
          >
            Logout
          </button>
        </div>
      </header>

      <section className="dashboard-banner-card">Dashboard and stats coming up</section>

      <aside className="owner-tabs">
        {ownerTabs.map((tab) => (
          <button
            type="button"
            key={tab}
            className={activeTab === tab ? 'owner-tab active' : 'owner-tab'}
            onClick={() => {
              setActiveTab(tab);
              if (tab === 'Create Hostel') {
                navigate('/owner/hostels/new');
                return;
              }

              setPopupMessage('Work in progress');
            }}
          >
            {tab}
          </button>
        ))}
      </aside>

      {popupMessage && <PopupMessage message={popupMessage} onClose={() => setPopupMessage('')} />}
    </main>
  );
}
