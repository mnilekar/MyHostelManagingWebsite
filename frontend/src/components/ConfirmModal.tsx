interface Props { open: boolean; title: string; description: string; onConfirm: () => void; onCancel: () => void; }
export default function ConfirmModal({ open, title, description, onConfirm, onCancel }: Props) {
  if (!open) return null;
  return <div className="modal-backdrop"><div className="modal-card"><h3>{title}</h3><p>{description}</p><div className="modal-actions"><button onClick={onConfirm}>OK</button><button className="secondary" onClick={onCancel}>Cancel</button></div></div></div>;
}
