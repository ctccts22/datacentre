import axios from "@/plugin/axios.ts";
import jwt_decode from 'vue-jwt-decode';

export async function login(credentials: { usernameOrEmail: string, password: string }) {
  const response = await axios.post('/auth/login', credentials);
  return response.data;
}

export async function fetchCurrentUser() {
  return await axios.get('/auth/info');
}

export async function logout(refreshToken: string) {
  return await axios.post('/auth/logout', null, {
    params: {
      refreshToken: refreshToken
    }
  });
}

export async function refreshToken(refreshToken: string) {
  return await axios.post('/auth/refresh', null, {
    params: {
      refreshToken: refreshToken
    }
  });
}

export function isAccessTokenExpired(accessToken: string | null): boolean {
  if (!accessToken) return true;
  const accessTokenExp = jwt_decode(accessToken).exp;
  return Date.now() >= accessTokenExp * 1000;
}