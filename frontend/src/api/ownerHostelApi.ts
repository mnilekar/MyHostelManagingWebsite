import { getToken } from '../utils/authStorage';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

export interface HostelSummary { hostelId: number; hostelName: string; cityName: string; stateName: string; floorsCount: number; updatedAt: string; }
export interface FloorRoom { floorNumber: number; roomsCount: number | ''; }
export interface HostelPayload {
  hostelName: string; gstNumber?: string; addressLine1: string; addressLine2?: string; areaLocality?: string;
  cityName: string; stateName: string; pincode?: string; countryName: string; floorsCount: number;
  floorRooms: Array<{ floorNumber: number; roomsCount: number }>; facilityIds: number[]; whyChoose?: string;
}
export interface HostelDetail extends Omit<HostelPayload, 'floorRooms'> { hostelId: number; floorRooms: Array<{ floorNumber: number; roomsCount: number }>; }
export interface Facility { facilityId: number; facilityName: string; }
export interface HostelExistence { exists: boolean; }

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const token = getToken();
  const response = await fetch(`${API_BASE}${path}`, {
    ...init,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...(init?.headers ?? {})
    }
  });
  if (!response.ok) {
    const err = await response.json().catch(() => ({}));
    throw new Error(err.message ?? 'Request failed');
  }
  if (response.status === 204) return null as T;
  return response.json() as Promise<T>;
}

export const ownerHostelApi = {
  listHostels: () => request<HostelSummary[]>('/api/owner/hostels'),
  hostelsExist: () => request<HostelExistence>('/api/owner/hostels/exists'),
  getHostel: (id: number) => request<HostelDetail>(`/api/owner/hostels/${id}`),
  createHostel: (payload: HostelPayload) => request<HostelDetail>('/api/owner/hostels', { method: 'POST', body: JSON.stringify(payload) }),
  updateHostel: (id: number, payload: HostelPayload) => request<HostelDetail>(`/api/owner/hostels/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
  deleteHostel: (id: number) => request<void>(`/api/owner/hostels/${id}`, { method: 'DELETE' }),
  listFacilities: () => request<Facility[]>('/api/owner/facilities')
};
