<script setup lang="ts">
import {ArrowLongLeftIcon, ArrowLongRightIcon} from '@heroicons/vue/20/solid';
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from '@headlessui/vue'
import {CheckIcon, ChevronUpDownIcon} from '@heroicons/vue/20/solid'
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import {useMemberStore} from "@/store/memberStore.ts";
import {computed, onMounted, ref, watch} from "vue";

const membersStore = useMemberStore();

const pagination = computed(() => membersStore.pagination);
const memberList = computed(() => membersStore.memberList);
const memberSearch = computed(() => membersStore.memberCondition);
const totalPages = computed(() => membersStore.totalPages);
const roles = computed(() => membersStore.role);
const selectedRole = ref(roles.value[0]);
const status = computed(() => membersStore.status);
const selectedStatus = ref(status.value[0]);
const date = ref();

const updateHandler = async (username: string) => {
  console.log('updateHandler:', username);

}

const searchMembers = async () => {
  try {
    await membersStore.memberSearch();
  } catch (error) {
    console.error('Error fetching members:', error);
  }
}

const goToPage = async (page: number) => {
  if (page < 0 || page >= totalPages.value || page === pagination.value.page) {
    return;
  }
  pagination.value.page = page;
  await searchMembers();
}

onMounted(async () => {
  console.log("mount");
  await searchMembers();
});

watch(memberSearch, async (newVal, oldVal) => {
  if (newVal !== oldVal) {
    await searchMembers();
  }
}, {deep: true});

watch(selectedRole, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    memberSearch.value.role = newVal.value;
  }
});

watch(selectedStatus, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    memberSearch.value.status = newVal.value;
  }
});
watch(date, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    if (newVal) {
      memberSearch.value.rdateStart = getJavaLocalDateTimeString(newVal[0]);
      memberSearch.value.rdateEnd = getJavaLocalDateTimeString(newVal[1]);
    } else {
      memberSearch.value.rdateStart = '';
      memberSearch.value.rdateEnd = '';
    }
  }
});

const getJavaLocalDateTimeString = (jsDate: any) => {
  return jsDate.getUTCFullYear() + '-' +
    pad(jsDate.getUTCMonth() + 1) + '-' +
    pad(jsDate.getUTCDate()) + 'T' +
    pad(jsDate.getUTCHours()) + ':' +
    pad(jsDate.getUTCMinutes()) + ':' +
    pad(jsDate.getUTCSeconds());
}

const pad = (number: any) => {
  if (number < 10) {
    return '0' + number;
  }
  return number;
}
</script>

<template>
  <div class="px-4 sm:px-6 lg:px-8">
    <div class="sm:flex sm:items-center">
      <div class="sm:flex-auto">
        <h1 class="text-base font-semibold leading-6 text-gray-900">Users</h1>
        <p class="mt-2 text-sm text-gray-700">A list of all the users in your account including their name, title, email
          and role.</p>
      </div>
      <div class="mt-4 sm:ml-16 sm:mt-0 sm:flex-none">
        <button type="button" @click="searchMembers"
                class="block rounded-md bg-indigo-600 px-3 py-2 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          검색
        </button>
      </div>
    </div>
    <div class="px-4 sm:px-6 lg:px-8">
      <div class="mt-10 grid grid-cols-1 gap-x-4 gap-y-3 sm:grid-cols-6">
        <div class="sm:col-span-3">
          <label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">이름</label>
          <div class="mt-2">
            <input type="text" v-model="memberSearch.name"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
          </div>
        </div>

        <div class="sm:col-span-3">
          <Listbox as="div" v-model="selectedRole">
            <ListboxLabel class="block text-sm font-medium leading-6 text-gray-900">권한</ListboxLabel>
            <div class="relative mt-2">
              <ListboxButton
                class="relative w-full cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-600 sm:text-sm sm:leading-6">
                <span class="block truncate">{{ selectedRole.label }}</span>
                <span class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
            <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true"/>
          </span>
              </ListboxButton>
              <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                          leave-to-class="opacity-0">
                <ListboxOptions
                  class="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                  <ListboxOption as="template" v-for="role in roles" :key="role.value" :value="role"
                                 v-slot="{ active, selected }">
                    <li
                      :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                      <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{
                          role.label
                        }}</span>
                      <span v-if="selected"
                            :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                  <CheckIcon class="h-5 w-5" aria-hidden="true"/>
                </span>
                    </li>
                  </ListboxOption>
                </ListboxOptions>
              </transition>
            </div>
          </Listbox>
        </div>

        <div class="sm:col-span-3">
          <Listbox as="div" v-model="selectedStatus">
            <ListboxLabel class="block text-sm font-medium leading-6 text-gray-900">상태</ListboxLabel>
            <div class="relative mt-2">
              <ListboxButton
                class="relative w-full cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-600 sm:text-sm sm:leading-6">
                <span class="block truncate">{{ selectedStatus.label }}</span>
                <span class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
            <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true"/>
          </span>
              </ListboxButton>
              <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                          leave-to-class="opacity-0">
                <ListboxOptions
                  class="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                  <ListboxOption as="template" v-for="st in status" :key="st.value" :value="st"
                                 v-slot="{ active, selected }">
                    <li
                      :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                      <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{
                          st.label
                        }}</span>
                      <span v-if="selected"
                            :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                  <CheckIcon class="h-5 w-5" aria-hidden="true"/>
                </span>
                    </li>
                  </ListboxOption>
                </ListboxOptions>
              </transition>
            </div>
          </Listbox>
        </div>
        <div class="sm:col-span-3">
          <label for="last-name" class="block text-sm font-medium leading-6 text-gray-900">가입날짜</label>
          <div class="mt-2">
            <VueDatePicker v-model="date" range multi-calendars
                           class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
          </div>
        </div>
      </div>
    </div>
    <div class="-mx-4 mt-8 sm:-mx-0">
      <table class="min-w-full divide-y divide-gray-300">
        <thead>
        <tr>
          <th scope="col" class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0">아이디</th>
          <th scope="col" class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell">이메일
          </th>
          <th scope="col" class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell">이름</th>
          <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">권한</th>
          <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">상태</th>
          <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">가입날짜</th>
          <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">수정날짜</th>
          <th scope="col" class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900">수정</th>
        </tr>
        </thead>
        <tbody class="divide-y divide-gray-200 bg-white">
        <tr v-for="member in memberList" :key="member.username">
          <td class="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-0">{{
              member.username
            }}
          </td>
          <td class="hidden whitespace-nowrap px-3 py-4 text-sm text-gray-500 sm:table-cell">{{ member.email }}</td>
          <td class="hidden whitespace-nowrap px-3 py-4 text-sm text-gray-500 lg:table-cell">{{ member.name }}</td>
          <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{{ member.role }}</td>
          <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{{ member.status }}</td>
          <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{{ member.rdate }}</td>
          <td class="whitespace-nowrap px-3 py-4 text-sm text-gray-500">{{ member.ldate }}</td>
          <td class="whitespace-nowrap py-4 pl-3 pr-4 text-sm font-medium sm:pr-0">
            <button type="button" @click="updateHandler(member.username)" class="text-indigo-600 hover:text-indigo-900">
              Edit
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <!-- Pagination section -->
    <nav class="flex items-center justify-between border-t border-gray-200 px-4 sm:px-0 mt-8">
      <div class="-mt-px flex w-0 flex-1">
        <button @click.prevent="goToPage(pagination.page - 1)"
                :class="{ 'text-gray-500': pagination.page === 0, 'text-indigo-600 hover:text-indigo-900': pagination.page !== 0 }"
                class="inline-flex items-center border-t-2 border-transparent pr-1 pt-4 text-sm font-medium">
          <ArrowLongLeftIcon class="mr-3 h-5 w-5" aria-hidden="true"/>
          이전
        </button>
      </div>

      <div class="hidden md:-mt-px md:flex">
        <template v-for="page in totalPages">
          <button @click.prevent="goToPage(page -1)"
                  :class="{ 'border-indigo-500 text-indigo-600': pagination.page === page - 1, 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300': pagination.page !== page - 1 }"
                  class="inline-flex items-center border-t-2 border-transparent px-4 pt-4 text-sm font-medium">
            {{ page }}
          </button>
        </template>
      </div>

      <div class="-mt-px flex w-0 flex-1 justify-end">
        <button @click.prevent="goToPage(pagination.page + 1)"
                :class="{ 'text-gray-500': pagination.page === totalPages -1 , 'text-indigo-600 hover:text-indigo-900': pagination.page !== totalPages -1 }"
                class="inline-flex items-center border-t-2 border-transparent pl-1 pt-4 text-sm font-medium">
          다음
          <ArrowLongRightIcon class="ml-3 h-5 w-5" aria-hidden="true"/>
        </button>
      </div>
    </nav>
  </div>
</template>
