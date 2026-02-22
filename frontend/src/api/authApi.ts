import { LoginResponse, RegisterEmployeeRequest, RegisterOwnerRequest, RegisterUserRequest } from '../types/auth';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

async function post<T>(path: string, payload: unknown): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });

  if (!response.ok) {
    const errorBody = await response.json().catch(() => ({}));
    throw new Error(errorBody.message ?? 'Request failed');
  }

  return response.json() as Promise<T>;
}

export function registerUser(payload: RegisterUserRequest) {
  return post<{ message: string; username: string }>('/api/auth/register/user', payload);
}

export function registerEmployee(payload: RegisterEmployeeRequest) {
  return post<{ message: string; username: string }>('/api/auth/register/employee', payload);
}

export function registerOwner(payload: RegisterOwnerRequest) {
  return post<{ message: string; username: string }>('/api/auth/register/owner', payload);
}

export function login(payload: { username: string; password: string }) {
  return post<LoginResponse>('/api/auth/login', payload);
}
