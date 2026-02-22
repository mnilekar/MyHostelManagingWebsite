import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ConfirmModal from '../components/ConfirmModal';
import HostelCard from '../components/HostelCard';
import { ownerHostelApi, HostelSummary } from '../api/ownerHostelApi';

export default function OwnerHostelsPage() {
  const navigate = useNavigate();
  const [hostels, setHostels] = useState<HostelSummary[]>([]);
  const [deleteId, setDeleteId] = useState<number | null>(null);

  const load = async () => {
    const data = await ownerHostelApi.listHostels();
    if (data.length === 0) {
      navigate('/owner/hostels/new', { replace: true });
      return;
    }
    setHostels(data);
  };

  useEffect(() => { load(); }, []);

  return <div className="owner-page"><h1>My Hostels</h1><div className="hostel-grid">
    <button className="create-card" onClick={() => navigate('/owner/hostels/new')}>＋<span>Create Hostel</span></button>
    {hostels.map(h => <HostelCard key={h.hostelId} hostel={h} onEdit={() => navigate(`/owner/hostels/${h.hostelId}/edit`)} onDelete={() => setDeleteId(h.hostelId)} />)}
  </div>
  <ConfirmModal open={deleteId!==null} title="Do you want to delete this hostel?" description="Once you delete, all details will be deleted." onCancel={() => setDeleteId(null)} onConfirm={async () => { if (deleteId) await ownerHostelApi.deleteHostel(deleteId); setDeleteId(null); await load(); }} />
  </div>;
}
