<script setup lang="ts">
import {reactive} from 'vue';
import {useAuthStore} from '@/store/authStore.ts';
import {Login} from '@/model/login.model';
import router from '@/router';

const credentials = reactive<Login>({
  usernameOrEmail: '',
  password: ''
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
  <div class="flex min-h-full flex-1 flex-col justify-center py-12 sm:px-6 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-md">
      <img class="mx-auto h-10 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
           alt="Your Company"/>
      <h2 class="mt-6 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">로그인</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-[480px]">
      <div class="bg-white px-6 py-12 shadow sm:rounded-lg sm:px-12">
        <form class="space-y-6" @submit.prevent="loginHandler">
          <div>
            <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
            <div class="mt-2">
              <input id="id"
                     name="id"
                     v-model="credentials.usernameOrEmail"
                     required
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
            </div>
          </div>

          <div>
            <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Password</label>
            <div class="mt-2">
              <input id="password"
                     name="password"
                     v-model="credentials.password"
                     type="password"
                     required
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
            </div>
          </div>

          <div class="flex items-center justify-between">
            <div class="text-sm leading-6">
              <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500">비밀번호 찾기</a>
            </div>
          </div>

          <div>
            <button type="submit"
                    class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
              로그인
            </button>
          </div>
        </form>

      </div>

      <p class="mt-10 text-center text-sm text-gray-500">
        <router-link :to="{ path: '/registration' }" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">회원가입</router-link>
      </p>
    </div>
  </div>
  <div class="bg-white py-10 sm:py-10">
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <div class="mx-auto max-w-2xl lg:max-w-none">
        <h2 class="text-lg font-semibold leading-8 text-gray-900">Trusted by the world’s most innovative teams</h2>
        <div class="mx-auto mt-10 grid grid-cols-4 items-start gap-x-8 gap-y-10 sm:grid-cols-6 sm:gap-x-10 lg:mx-0 lg:grid-cols-5">
          <img class="col-span-2 max-h-12 w-full object-contain object-left lg:col-span-1" src="https://tailwindui.com/img/logos/transistor-logo-gray-900.svg" alt="Transistor" width="158" height="48" />
          <img class="col-span-2 max-h-12 w-full object-contain object-left lg:col-span-1" src="https://tailwindui.com/img/logos/reform-logo-gray-900.svg" alt="Reform" width="158" height="48" />
          <img class="col-span-2 max-h-12 w-full object-contain object-left lg:col-span-1" src="https://tailwindui.com/img/logos/tuple-logo-gray-900.svg" alt="Tuple" width="158" height="48" />
          <img class="col-span-2 max-h-12 w-full object-contain object-left lg:col-span-1" src="https://tailwindui.com/img/logos/savvycal-logo-gray-900.svg" alt="SavvyCal" width="158" height="48" />
          <img class="col-span-2 max-h-12 w-full object-contain object-left lg:col-span-1" src="https://tailwindui.com/img/logos/statamic-logo-gray-900.svg" alt="Statamic" width="158" height="48" />
        </div>
      </div>
    </div>
  </div>
</template>