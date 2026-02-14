import { FormEvent, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/PageLayout';
import { LOCATION_DATA } from '../constants/locationData';
import { registerOwner } from '../api/authApi';
import { PASSWORD_RULE, suggestUsername } from '../utils/formUtils';
import type { Gender } from '../types/auth';

export default function RegisterOwnerPage() {
  const navigate = useNavigate();
  const [sameAddress, setSameAddress] = useState(false);
  const [form, setForm] = useState({
    firstName: '', middleName: '', surname: '', gender: 'MALE', email: '', nationality: 'India', mobileNumber: '',
    stateName: 'Maharashtra', cityName: 'Pune', idProofType: 'AADHAR_CARD', idProofValue: '', permanentAddress: '',
    currentAddress: '', username: '', password: '', confirmPassword: ''
  });
  const [msg, setMsg] = useState('');
  const [error, setError] = useState('');
  const usernameSuggestion = useMemo(() => suggestUsername(form.firstName, form.surname), [form.firstName, form.surname]);

  const clear = () => {
    setForm({
      firstName: '', middleName: '', surname: '', gender: 'MALE', email: '', nationality: 'India', mobileNumber: '',
      stateName: 'Maharashtra', cityName: 'Pune', idProofType: 'AADHAR_CARD', idProofValue: '', permanentAddress: '',
      currentAddress: '', username: '', password: '', confirmPassword: ''
    });
    setSameAddress(false);
    setMsg('');
    setError('');
  };

  const submit = async (e: FormEvent) => {
    e.preventDefault();
    setError('');
    if (!PASSWORD_RULE.test(form.password)) return setError('Password policy failed.');
    if (form.password !== form.confirmPassword) return setError('Confirm password must match.');

    try {
      const response = await registerOwner({
        ...form,
        gender: form.gender as Gender,
        countryCode: LOCATION_DATA.India.countryCode,
        idProofType: form.idProofType as "AADHAR_CARD" | "VOTER_ID" | "PASSPORT",
        username: form.username || usernameSuggestion,
        currentAddress: sameAddress ? form.permanentAddress : form.currentAddress,
        idProofValue: form.idProofValue.replace(/\D/g, '')
      });
      setMsg(response.message);
      setTimeout(() => navigate('/'), 1200);
    } catch (err) {
      setError((err as Error).message);
    }
  };

  return (
    <PageLayout title="Register Your Hostel (Owner)">
      <form onSubmit={submit} className="form-grid">
        <input placeholder="Name" required value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} />
        <input placeholder="Middle name" value={form.middleName} onChange={(e) => setForm({ ...form, middleName: e.target.value })} />
        <input placeholder="Surname" value={form.surname} onChange={(e) => setForm({ ...form, surname: e.target.value })} />
        <input type="email" required placeholder="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
        <select value={form.gender} onChange={(e) => setForm({ ...form, gender: e.target.value as Gender })}><option value="MALE">Male</option><option value="FEMALE">Female</option><option value="NOT_PREFERRED">NotPreferred</option></select>
        <input value={LOCATION_DATA.India.countryCode} readOnly />
        <input required placeholder="Mobile" value={form.mobileNumber} onChange={(e) => setForm({ ...form, mobileNumber: e.target.value })} />
        <input value="Maharashtra" readOnly />
        <input value="Pune" readOnly />
        <select value={form.idProofType} onChange={(e) => setForm({ ...form, idProofType: e.target.value })}><option value="AADHAR_CARD">Aadhar Card</option><option value="VOTER_ID">VoterID</option><option value="PASSPORT">Passport</option></select>
        <input placeholder="ID Proof value" maxLength={10} value={form.idProofValue} onChange={(e) => setForm({ ...form, idProofValue: e.target.value.replace(/\D/g, '') })} />
        <input placeholder="Permanent Address" maxLength={60} required value={form.permanentAddress} onChange={(e) => setForm({ ...form, permanentAddress: e.target.value })} />
        <label><input type="checkbox" checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />Permanent Address same as Current Address</label>
        <input placeholder="Current Address" maxLength={60} required value={sameAddress ? form.permanentAddress : form.currentAddress} disabled={sameAddress} onChange={(e) => setForm({ ...form, currentAddress: e.target.value })} />
        <input placeholder={`Username (suggested: ${usernameSuggestion})`} maxLength={10} value={form.username} onChange={(e) => setForm({ ...form, username: e.target.value.replace(/[^A-Za-z0-9]/g, '') })} />
        <input type="password" required placeholder="Password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
        <input type="password" required placeholder="Confirm Password" value={form.confirmPassword} onChange={(e) => setForm({ ...form, confirmPassword: e.target.value })} />
        <div className="button-row"><button type="submit">Register</button><button type="button" onClick={clear}>Clear</button><button type="button" onClick={() => navigate('/')}>Cancel</button></div>
      </form>
      {msg && <p className="success">{msg}</p>}
      {error && <p className="error">{error}</p>}
    </PageLayout>
  );
}
