import { defineStore } from 'pinia';
import axios from '@/plugin/axios.ts';
import router from '@/router';
import { Member } from '@/model/member.model';
import jwt_decode from 'vue-jwt-decode';

export const useAuthStore = defineStore('auth', {
  state: (): {
    user: Member,
    accessToken: string | null
  } => ({
    user: new Member(),
    accessToken: null
  }),
  actions: {
    setUser(user: Member) {
      this.user = user;
    },
    setAccessToken(token: string | null) {
      this.accessToken = token;
    },
    async login(credentials: { usernameOrEmail: string, password: string }) {
      try {
        const response = await axios.post('/auth/login', credentials);
        console.log('Login response:', response.data);
        if (response.data && response.data.accessToken) {
          const token = response.data.accessToken;
          this.setAccessToken(token);
          axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          await this.fetchCurrentUser();

          if (this.getters.isAccessTokenExpired()) {
            await this.refreshToken();
          }

          localStorage.setItem('user', JSON.stringify(this.user));
        } else {
          console.error('Error during login:');
        }
      } catch (error) {
        console.error('Error during login:', error);
        throw error;
      }
    },
    async fetchCurrentUser() {
      try {
        const response = await axios.get('/auth/info');
        console.log('fetchCurrentUser response:', response.data);
        this.setUser(response.data);
      } catch (error) {
        console.error('Error fetching current user:', error);
        throw error;
      }
    },
    async logout() {
      try {
        await axios.post('/auth/logout', null, {
          params: {
            refreshToken: this.accessToken
          }
        });
        this.setUser(new Member());
        this.setAccessToken(null);
        localStorage.clear();
        await router.push('/');
      } catch (error) {
        console.error('Error during logout:', error);
        throw error;
      }
    },
    async refreshToken() {
      try {
        const response = await axios.post('/auth/refresh', null, {
          params: {
            refreshToken: this.accessToken
          }
        });
        this.setAccessToken(response.data.accessToken);
      } catch (error) {
        console.error('Error during token refresh:', error);
        throw error;
      }
    },
    getters: {
      isAccessTokenExpired(): boolean {
        if (!this.accessToken) return true;
        const accessTokenExp = jwt_decode(this.accessToken).exp;
        return Date.now() >= accessTokenExp * 1000;
      },
      isLogin(): boolean {
        if (!localStorage.getItem('user')) return false;
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        return user && user.usernameOrEmail !== '';
      }
    }
  },
});
