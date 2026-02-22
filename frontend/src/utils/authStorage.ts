import { AuthUser } from '../types/auth';

const AUTH_TOKEN_KEY = 'authToken';
const AUTH_USER_KEY = 'authUser';

export function saveAuthSession(token: string, user: AuthUser) {
  sessionStorage.setItem(AUTH_TOKEN_KEY, token);
  sessionStorage.setItem(AUTH_USER_KEY, JSON.stringify(user));
}

export function clearAuthSession() {
  sessionStorage.removeItem(AUTH_TOKEN_KEY);
  sessionStorage.removeItem(AUTH_USER_KEY);
}

export function getToken() {
  return sessionStorage.getItem(AUTH_TOKEN_KEY);
}

export function getAuthUser(): AuthUser | null {
  const raw = sessionStorage.getItem(AUTH_USER_KEY);
  if (!raw) {
    return null;
  }

  try {
    return JSON.parse(raw) as AuthUser;
  } catch {
    clearAuthSession();
    return null;
  }
}

export function isAuthenticated() {
  return Boolean(getToken());
}
