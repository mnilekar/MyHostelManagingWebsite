import { HostelSummary } from '../api/ownerHostelApi';
interface Props { hostel: HostelSummary; onEdit: () => void; onDelete: () => void }
export default function HostelCard({ hostel, onEdit, onDelete }: Props) {
  return <div className="hostel-card"><div className="thumb">🏨</div><div className="title" title={hostel.hostelName}>{hostel.hostelName}</div><div className="overlay"><button onClick={onEdit}>Edit</button><button className="secondary" onClick={onDelete}>Delete</button></div></div>;
}
