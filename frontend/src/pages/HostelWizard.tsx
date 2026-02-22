import { useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ChipsInput from '../components/ChipsInput';
import Stepper from '../components/Stepper';
import { ownerHostelApi, Facility } from '../api/ownerHostelApi';

const initialState = { hostelName:'', gstNumber:'', addressLine1:'', addressLine2:'', areaLocality:'', cityName:'', stateName:'', pincode:'', countryName:'India', floorsCount:1, floorRooms:[{floorNumber:1, roomsCount:'' as number | ''}], facilityIds:[] as number[], whyChoose:'' };

export default function HostelWizard() {
  const { id } = useParams();
  const editMode = Boolean(id);
  const [step, setStep] = useState(1);
  const [saving, setSaving] = useState(false);
  const [facilities, setFacilities] = useState<Facility[]>([]);
  const [state, setState] = useState(initialState);
  const navigate = useNavigate();

  useEffect(() => { ownerHostelApi.listFacilities().then(setFacilities); }, []);
  useEffect(() => { if (editMode && id) { ownerHostelApi.getHostel(Number(id)).then(d => setState({ ...state, ...d, floorRooms: d.floorRooms.map(f => ({ ...f })) })); } }, [editMode, id]);

  const step1Valid = state.hostelName && state.addressLine1 && state.cityName && state.stateName;
  const step2Valid = state.floorsCount >= 1 && state.floorRooms.length === state.floorsCount && state.floorRooms.every(r => Number(r.roomsCount) > 0);
  const counter = useMemo(() => `${state.whyChoose.length}/2000`, [state.whyChoose]);

  const resizeFloors = (count: number) => {
    const rooms = Array.from({ length: count }, (_, i) => state.floorRooms[i] ?? { floorNumber: i + 1, roomsCount: '' });
    setState(s => ({ ...s, floorsCount: count, floorRooms: rooms.map((r, idx) => ({ ...r, floorNumber: idx + 1 })) }));
  };

  const save = async () => {
    setSaving(true);
    const payload = { ...state, floorRooms: state.floorRooms.map(r => ({ floorNumber: r.floorNumber, roomsCount: Number(r.roomsCount) })), whyChoose: state.whyChoose || undefined };
    if (editMode && id) await ownerHostelApi.updateHostel(Number(id), payload);
    else await ownerHostelApi.createHostel(payload);
    navigate('/owner/hostels');
  };

  return <div className="wizard-wrap"><h1>{editMode ? 'Edit Hostel' : 'Register Hostel'}</h1><Stepper current={step} />
  {step===1 && <div className="panel form-grid">
    <input placeholder="Hostel Name*" value={state.hostelName} onChange={e => setState({ ...state, hostelName:e.target.value })} />
    <input placeholder="Owner GST Number" value={state.gstNumber} onChange={e => setState({ ...state, gstNumber:e.target.value })} />
    <input placeholder="Address Line 1*" value={state.addressLine1} onChange={e => setState({ ...state, addressLine1:e.target.value })} />
    <input placeholder="Address Line 2" value={state.addressLine2} onChange={e => setState({ ...state, addressLine2:e.target.value })} />
    <input placeholder="Area/Locality" value={state.areaLocality} onChange={e => setState({ ...state, areaLocality:e.target.value })} />
    <input placeholder="State*" value={state.stateName} onChange={e => setState({ ...state, stateName:e.target.value })} />
    <input placeholder="City*" value={state.cityName} onChange={e => setState({ ...state, cityName:e.target.value })} />
    <input placeholder="Pincode" value={state.pincode} onChange={e => setState({ ...state, pincode:e.target.value })} />
    <input value={state.countryName} readOnly />
  </div>}

  {step===2 && <div className="panel"><label>Floors Count</label><input type="number" min={1} max={50} value={state.floorsCount} onChange={e => resizeFloors(Math.max(1, Math.min(50, Number(e.target.value || 1))))} />
    <table><thead><tr><th>Floor</th><th>Number of rooms</th></tr></thead><tbody>{state.floorRooms.map((r, idx) => <tr key={idx}><td>{r.floorNumber}</td><td><input type="number" min={1} value={r.roomsCount} onChange={e => setState(s => ({ ...s, floorRooms: s.floorRooms.map((fr, i) => i===idx ? { ...fr, roomsCount: e.target.value === '' ? '' : Number(e.target.value) } : fr) }))} /></td></tr>)}</tbody></table>
  </div>}

  {step===3 && <div className="panel"><label>Facilities</label><ChipsInput facilities={facilities} selectedIds={state.facilityIds} onChange={ids => setState({ ...state, facilityIds: ids })} />
    <label>Why choose hostel</label><textarea maxLength={2000} value={state.whyChoose} onChange={e => setState({ ...state, whyChoose:e.target.value })} />
    <div className="counter">{counter}</div>
  </div>}

  <div className="actions"><button className="secondary" onClick={() => navigate('/login')}>Cancel</button>{step>1 && <button className="secondary" onClick={() => setStep(step-1)}>Back</button>}{step<3 && <button disabled={(step===1 && !step1Valid) || (step===2 && !step2Valid)} onClick={() => setStep(step+1)}>Next</button>}{step===3 && <button disabled={saving} onClick={save}>{saving ? 'Saving...' : 'Save'}</button>}</div>
  </div>;
}
