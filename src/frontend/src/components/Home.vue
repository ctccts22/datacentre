<script setup lang="ts">
import {useAuthStore} from '@/store/authStore.ts';
import {useRouter} from 'vue-router'; // Import useRouter
// import Swal from 'sweetalert2';

const authStore = useAuthStore();
const router = useRouter();
const logoutHandler = async () => {
  try {
    await authStore.logout();
    console.log('Logout successful');
    if (authStore.accessToken === null) {
      // await Swal.fire('로그아웃 성공');
      await router.push("/login");
    }
  } catch (error) {
    console.error('Error during logout:', error);
    throw new Error('로그아웃 실패');
  }
};
</script>

<template>
  <div class="flex flex-col items-center justify-center h-screen">
    <h1 class="text-3xl font-bold mb-8">Welcome to Brams</h1>

    <button v-if="authStore.getters.isLogin()"
            @click="logoutHandler"
            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
      Logout
    </button>

    <router-link v-else
                 :to="{ path: '/login' }"
                 class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
      Login
    </router-link>
  </div>
</template>

<style scoped>

</style>