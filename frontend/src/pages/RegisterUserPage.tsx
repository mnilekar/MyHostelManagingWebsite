import { FormEvent, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/PageLayout';
import { LOCATION_DATA } from '../constants/locationData';
import { registerUser } from '../api/authApi';
import { PASSWORD_RULE, suggestUsername } from '../utils/formUtils';
import type { Gender } from '../types/auth';

const defaultNationality = 'India';
const defaultState = 'Maharashtra';
const defaultCity = 'Pune';

export default function RegisterUserPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    firstName: '',
    middleName: '',
    surname: '',
    gender: 'MALE',
    email: '',
    nationality: defaultNationality,
    mobileNumber: '',
    stateName: defaultState,
    cityName: defaultCity,
    idProofType: 'AADHAR_CARD',
    idProofValue: '',
    username: '',
    password: '',
    confirmPassword: ''
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const countryCode = LOCATION_DATA[form.nationality]?.countryCode ?? '+00';
  const states = Object.keys(LOCATION_DATA[form.nationality]?.states ?? { Other: ['NA'] });
  const cities = LOCATION_DATA[form.nationality]?.states[form.stateName] ?? ['NA'];
  const isIndia = form.nationality === 'India';

  const usernameSuggestion = useMemo(() => suggestUsername(form.firstName, form.surname), [form.firstName, form.surname]);

  const applyDefaults = () => {
    setForm({
      firstName: '', middleName: '', surname: '', gender: 'MALE', email: '', nationality: defaultNationality, mobileNumber: '',
      stateName: defaultState, cityName: defaultCity, idProofType: 'AADHAR_CARD', idProofValue: '', username: '',
      password: '', confirmPassword: ''
    });
    setError('');
    setMessage('');
  };

  const submit = async (e: FormEvent) => {
    e.preventDefault();
    setError('');
    if (!PASSWORD_RULE.test(form.password)) return setError('Password policy failed.');
    if (form.password !== form.confirmPassword) return setError('Confirm password must match.');

    try {
      const payload = {
        firstName: form.firstName,
        middleName: form.middleName,
        surname: form.surname,
        gender: form.gender as Gender,
        email: form.email,
        nationality: form.nationality,
        countryCode,
        mobileNumber: form.mobileNumber,
        stateName: form.stateName,
        cityName: form.cityName,
        idProofType: (isIndia ? form.idProofType : 'PASSPORT') as 'AADHAR_CARD' | 'VOTER_ID' | 'PASSPORT',
        idProofValue: form.idProofValue,
        username: form.username || usernameSuggestion,
        password: form.password,
        confirmPassword: form.confirmPassword
      };
      const response = await registerUser(payload);
      setMessage(response.message);
      setTimeout(() => navigate('/'), 1200);
    } catch (err) {
      setError((err as Error).message);
    }
  };

  return (
    <PageLayout title="Register As User">
      <form onSubmit={submit} className="form-grid">
        <input placeholder="Name" required value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} />
        <input placeholder="Surname" value={form.surname} onChange={(e) => setForm({ ...form, surname: e.target.value })} />
        <select value={form.gender} onChange={(e) => setForm({ ...form, gender: e.target.value as Gender })}>
          <option value="MALE">Male</option>
          <option value="FEMALE">Female</option>
          <option value="NOT_PREFERRED">Not Preferred</option>
        </select>
        <input type="email" placeholder="Email" required value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
        <select value={form.nationality} onChange={(e) => setForm({ ...form, nationality: e.target.value, stateName: defaultState, cityName: defaultCity })}>
          {Object.keys(LOCATION_DATA).map((c) => <option key={c}>{c}</option>)}
        </select>
        <input value={countryCode} readOnly />
        <input placeholder="Mobile" required value={form.mobileNumber} onChange={(e) => setForm({ ...form, mobileNumber: e.target.value })} />
        <select value={form.stateName} onChange={(e) => setForm({ ...form, stateName: e.target.value, cityName: (LOCATION_DATA[form.nationality]?.states[e.target.value] ?? [''])[0] })}>{states.map((s) => <option key={s}>{s}</option>)}</select>
        <select value={form.cityName} onChange={(e) => setForm({ ...form, cityName: e.target.value })}>{cities.map((c) => <option key={c}>{c}</option>)}</select>
        {isIndia ? (
          <select value={form.idProofType} onChange={(e) => setForm({ ...form, idProofType: e.target.value })}>
            <option value="AADHAR_CARD">Aadhar Card</option>
            <option value="VOTER_ID">VoterID</option>
            <option value="PASSPORT">Passport</option>
          </select>
        ) : <input value="Passport" readOnly />}
        <input placeholder="ID Proof Value" maxLength={10} value={form.idProofValue} onChange={(e) => setForm({ ...form, idProofValue: isIndia ? e.target.value.replace(/\D/g, '') : e.target.value })} />
        <input placeholder={`Username (suggested: ${usernameSuggestion})`} maxLength={10} value={form.username} onChange={(e) => setForm({ ...form, username: e.target.value.replace(/[^A-Za-z0-9]/g, '') })} />
        <input type="password" placeholder="Password" required value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
        <input type="password" placeholder="Confirm Password" required value={form.confirmPassword} onChange={(e) => setForm({ ...form, confirmPassword: e.target.value })} />
        <div className="button-row">
          <button type="submit">Register</button>
          <button type="button" onClick={applyDefaults}>Clear</button>
          <button type="button" onClick={() => navigate('/')}>Cancel</button>
        </div>
      </form>
      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}
    </PageLayout>
  );
}
