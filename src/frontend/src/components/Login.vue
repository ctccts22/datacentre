<script setup lang="ts">
import { reactive } from 'vue';
import { useAuthStore } from '@/store/authStore.ts';
import { Login } from '@/model/login.model';
import router from '@/router';

const credentials = reactive<Login>({
  id: '',
  passwd: ''
});
const authStore = useAuthStore();
const loginHandler = async () => {
  try {
    await authStore.login(credentials);
    await router.push('/');
  } catch (error) {
    console.error('Error during login:', error);
  }
};

</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8">
      <div>
        <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
          Brams
        </h2>
      </div>
      <form class="mt-8 space-y-6" @submit.prevent="loginHandler">
        <div class="rounded-md shadow-sm -space-y-px">
          <div>
            <label for="id" class="sr-only">아이디:</label>
            <input
              id="id"
              v-model="credentials.id"
              required
              class="appearance-none rounded-none relative block w-full py-2 px-3 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="아이디"
            />
          </div>
          <div>
            <label for="password" class="sr-only">비밀번호:</label>
            <input
              id="password"
              v-model="credentials.passwd"
              type="password"
              required
              class="appearance-none rounded-none relative block w-full py-2 px-3 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="비밀번호"
            />
          </div>
        </div>
        <div>
          <button
            type="submit"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            로그인
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>

</style>