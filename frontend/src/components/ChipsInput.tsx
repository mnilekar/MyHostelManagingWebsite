import { useMemo, useState } from 'react';
import { Facility } from '../api/ownerHostelApi';

interface Props { facilities: Facility[]; selectedIds: number[]; onChange: (ids: number[]) => void }
export default function ChipsInput({ facilities, selectedIds, onChange }: Props) {
  const [query, setQuery] = useState('');
  const selected = facilities.filter(f => selectedIds.includes(f.facilityId));
  const suggestions = useMemo(() => facilities.filter(f => !selectedIds.includes(f.facilityId) && f.facilityName.toLowerCase().includes(query.toLowerCase())).slice(0,6), [facilities, selectedIds, query]);

  return <div className="chips-input">
    <div className="chips">{selected.map(s => <span key={s.facilityId} className="chip">{s.facilityName}<button onClick={() => onChange(selectedIds.filter(id => id!==s.facilityId))}>×</button></span>)}</div>
    <input value={query} onChange={e => setQuery(e.target.value)} placeholder="Add facilities" />
    {query && <div className="suggestions">{suggestions.map(s => <button type="button" key={s.facilityId} onClick={() => { onChange([...selectedIds, s.facilityId]); setQuery(''); }}>{s.facilityName}</button>)}</div>}
  </div>;
}
