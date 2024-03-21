<script setup lang="ts">
import {Disclosure, DisclosureButton, DisclosurePanel, Menu, MenuButton, MenuItem, MenuItems} from '@headlessui/vue'
import {Bars3Icon, BellIcon, XMarkIcon} from '@heroicons/vue/24/outline'
import {ChevronDownIcon} from '@heroicons/vue/20/solid'
import {useAuthStore} from '@/store/authStore.ts';
import {useRouter} from 'vue-router';

const user = {
  name: 'Tom Cook',
  email: 'tom@example.com',
}
const navigation = [
  {name: 'ListNames/ListData', href: '#', current: true},
  {name: 'ListNames', href: '#', current: false},
  {name: 'ListData', href: '#', current: false},
  {name: 'Others', href: '#', current: false},
  {name: 'History', href: '#', current: false},
]
const authStore = useAuthStore();
const router = useRouter();
const logoutHandler = async () => {
  try {
    await authStore.logout();
    console.log('Logout successful');
    if (authStore.accessToken === null) {
      await router.push("/login");
    }
  } catch (error) {
    console.error('Error during logout:', error);
    throw new Error('로그아웃 실패');
  }
};
</script>

<template>
  <div class="min-h-full">
    <Disclosure as="nav" class="bg-indigo-600" v-slot="{ open }">
      <div class="mx-auto max-w-10xl px-4 sm:px-6 lg:px-8">
        <div class="flex h-16 items-center justify-between">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <img class="h-8 w-8" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=300"
                   alt="Your Company"/>
            </div>
            <div class="hidden md:block">
              <div class="ml-10 flex items-baseline space-x-4">
                <a v-for="item in navigation" :key="item.name" :href="item.href"
                   :class="[item.current ? 'bg-indigo-700 text-white' : 'text-white hover:bg-indigo-500 hover:bg-opacity-75', 'rounded-md px-3 py-2 text-sm font-medium']"
                   :aria-current="item.current ? 'page' : undefined">{{ item.name }}</a>
              </div>
            </div>
          </div>

          <div class="hidden md:block">
            <div class="ml-4 flex items-center md:ml-6">
              <div class="text-white hover:bg-indigo-500">뉴로라인즈님 환영합니다.</div>

              <!-- Profile dropdown -->
              <Menu as="div" class="relative ml-3">
                <div>
                  <MenuButton
                    class="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                    설정
                    <ChevronDownIcon class="-mr-1 h-5 w-5 text-gray-400" aria-hidden="true"/>
                  </MenuButton>
                </div>
                <transition enter-active-class="transition ease-out duration-100"
                            enter-from-class="transform opacity-0 scale-95"
                            enter-to-class="transform opacity-100 scale-100"
                            leave-active-class="transition ease-in duration-75"
                            leave-from-class="transform opacity-100 scale-100"
                            leave-to-class="transform opacity-0 scale-95">
                  <MenuItems
                    class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <MenuItem v-slot="{ active }">
                      <a @click="logoutHandler"
                         :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']">
                        로그아웃
                      </a>
                    </MenuItem>
                  </MenuItems>
                </transition>
              </Menu>
            </div>
          </div>
          <div class="-mr-2 flex md:hidden">
            <!-- Mobile menu button -->
            <DisclosureButton
              class="relative inline-flex items-center justify-center rounded-md bg-indigo-600 p-2 text-indigo-200 hover:bg-indigo-500 hover:bg-opacity-75 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-indigo-600">
              <span class="absolute -inset-0.5"/>
              <span class="sr-only">Open main menu</span>
              <Bars3Icon v-if="!open" class="block h-6 w-6" aria-hidden="true"/>
              <XMarkIcon v-else class="block h-6 w-6" aria-hidden="true"/>
            </DisclosureButton>
          </div>
        </div>
      </div>

      <DisclosurePanel class="md:hidden">
        <div class="space-y-1 px-2 pb-3 pt-2 sm:px-3">
          <DisclosureButton v-for="item in navigation" :key="item.name" as="a" :href="item.href"
                            :class="[item.current ? 'bg-indigo-700 text-white' : 'text-white hover:bg-indigo-500 hover:bg-opacity-75', 'block rounded-md px-3 py-2 text-base font-medium']"
                            :aria-current="item.current ? 'page' : undefined">{{ item.name }}
          </DisclosureButton>
        </div>
        <div class="border-t border-indigo-700 pb-3 pt-4">
          <div class="flex items-center px-3">
            <div class="ml-3">
              <div class="text-base font-medium text-white">{{ user.name }}</div>
              <div class="text-sm font-medium text-indigo-300">{{ user.email }}</div>
            </div>
            <button type="button"
                    class="relative ml-auto flex-shrink-0 rounded-full bg-indigo-600 p-1 text-indigo-200 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-indigo-600">
              <span class="absolute -inset-1.5"/>
              <span class="sr-only">View notifications</span>
              <BellIcon class="h-6 w-6" aria-hidden="true"/>
            </button>
          </div>
          <div class="mt-3 space-y-1 px-2">
            <DisclosureButton
              class="block rounded-md px-3 py-2 text-base font-medium text-white hover:bg-indigo-500 hover:bg-opacity-75"
              @click="logoutHandler">
              로그아웃
            </DisclosureButton>
          </div>
        </div>
      </DisclosurePanel>
    </Disclosure>

    <header class="bg-white shadow-sm">
      <div class="mx-auto max-w-10xl px-4 py-4 sm:px-6 lg:px-8 border-b border-gray-200">
        <h1 class="text-lg font-semibold leading-6 text-gray-900">Dashboard</h1>
      </div>
    </header>
  </div>
</template>