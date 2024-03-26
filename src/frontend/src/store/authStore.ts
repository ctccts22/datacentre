import { defineStore } from 'pinia';
import axios from '@/plugin/axios.ts';
import router from '@/router';
import * as authService from '@/service/authService.ts';
import {Member} from "../model/member.auth.model.ts"

export const useAuthStore = defineStore('auth', {
  state: () :{accessToken: string | null, user: Member} => ({
    user: JSON.parse(localStorage.getItem('user') || '{}') as Member,
    accessToken: null
  }),
  actions: {
    setUser(user: Member) {
      this.user = user;
    },
    setAccessToken(token: string | null) {
      this.accessToken = token;
      axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
    },
    async login(credentials: { usernameOrEmail: string, password: string }) {
      try {
        const response = await authService.login(credentials);

        if (response && response.accessToken) {
          this.setAccessToken(response.accessToken);

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
        const response = await authService.fetchCurrentUser();
        this.setUser(response.data);
      } catch (error) {
        console.error('Error fetching current user:', error);
        throw error;
      }
    },
    async logout() {
      try {
        if (this.accessToken) {
          await authService.logout(this.accessToken as string);
        }
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
        if (this.accessToken !== null) {
          const response = await authService.refreshToken(this.accessToken);
          this.setAccessToken(response.data.accessToken);
        }
      } catch (error) {
        console.error('Error during token refresh:', error);
        throw error;
      }
    },
    getters: {
      isAccessTokenExpired(): boolean {
        return authService.isAccessTokenExpired(this.accessToken);
      },
      isLogin(): boolean {
        if (!localStorage.getItem('user')) return false;
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        return user && user.usernameOrEmail !== '';
      }
    }
  },
});