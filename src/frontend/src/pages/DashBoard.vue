<script setup lang="ts">
import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/vue/20/solid'
const people = [
  {
    title: '최근 로그인',
    name: 'Jane Cooper',
    content: '2024-04-03 (IP : 211.34.21.124)',
    role: 'Admin',
  },
]
const download = [
  {
    title: '최근 다운로드',
    name: 'Jane Cooper',
    content: '2024-04-03 (IP : 211.34.21.124)',
    role: 'Admin',
  },
]
const search = [
  {
    title: '최근 검색기록',
    name: 'Jane Cooper',
    content: '2024-04-03 (IP : 211.34.21.124)',
    role: 'Admin',
  },
]
const stats = [
  { id: 1, name: 'ListNames + ListData', value: '43,000' },
  { id: 2, name: 'ListNames', value: '41,000' },
  { id: 3, name: 'ListData', value: '40,000' },
]
const statuses = { Completed: 'text-green-400 bg-green-400/10', Error: 'text-rose-400 bg-rose-400/10' }
const activityItems = [
  {
    user: {
      name: 'Michael Foster',
    },
    commit: '2d89f0c8',
    branch: 'main',
    status: 'Completed',
    duration: '25s',
    date: '45 minutes ago',
    dateTime: '2023-01-23T11:00',
  },
  {
    user: {
      name: 'Michael Foster',
    },
    commit: '2d89f0c8',
    branch: 'main',
    status: 'Completed',
    duration: '25s',
    date: '45 minutes ago',
    dateTime: '2023-01-23T11:00',
  },
  {
    user: {
      name: 'Michael Foster',
    },
    commit: '2d89f0c8',
    branch: 'main',
    status: 'Completed',
    duration: '25s',
    date: '45 minutes ago',
    dateTime: '2023-01-23T11:00',
  },
  {
    user: {
      name: 'Michael Foster',
    },
    commit: '2d89f0c8',
    branch: 'main',
    status: 'Completed',
    duration: '25s',
    date: '45 minutes ago',
    dateTime: '2023-01-23T11:00',
  },
  {
    user: {
      name: 'Michael Foster',
    },
    commit: '2d89f0c8',
    branch: 'main',
    status: 'Completed',
    duration: '25s',
    date: '45 minutes ago',
    dateTime: '2023-01-23T11:00',
  },
]
</script>

<template>
  <div class="bg-white py-1 sm:py-2">
    <h2 class="px-4 py-4 text-base font-semibold leading-7 sm:px-6 lg:px-8">LoLi Data</h2>
    <div class="mx-auto max-w-7xl px-6 lg:px-8">
      <dl class="grid grid-cols-1 gap-x-8 gap-y-16 text-center lg:grid-cols-3">
        <div v-for="stat in stats" :key="stat.id" class="mx-auto flex max-w-xs flex-col gap-y-4">
          <dt class="text-base leading-7 text-gray-600">{{ stat.name }}</dt>
          <dd class="order-first text-3xl font-semibold tracking-tight text-gray-900 sm:text-5xl">{{ stat.value }}</dd>
        </div>
      </dl>
    </div>
  </div>
  <h2 class="px-4 py-4 text-base font-semibold leading-7 sm:px-6 lg:px-8">최근 기록</h2>
  <ul role="list" class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
    <li v-for="person in people" :key="person.title" class="col-span-1 divide-y divide-gray-200 rounded-lg bg-white shadow">
      <div class="flex w-full items-center justify-between space-x-6 p-6">
        <div class="flex-1 truncate">
          <h3 class="text-lg text-gray-800 font-bold py-3">{{ person.title }}</h3>
          <div class="flex items-center space-x-3">
            <h3 class="truncate text-sm font-medium text-gray-900">{{ person.name }}</h3>
            <span class="inline-flex flex-shrink-0 items-center rounded-full bg-green-50 px-1.5 py-0.5 text-xs font-medium text-green-700 ring-1 ring-inset ring-green-600/20">{{ person.role }}</span>
          </div>
          <p class="mt-1 truncate text-sm text-gray-500">{{ person.content }}</p>
        </div>
      </div>
    </li>
    <li v-for="dw in download" :key="dw.title" class="col-span-1 divide-y divide-gray-200 rounded-lg bg-white shadow">
      <div class="flex w-full items-center justify-between space-x-6 p-6">
        <div class="flex-1 truncate">
          <h3 class="text-lg text-gray-800 font-bold py-3">{{ dw.title }}</h3>
          <div class="flex items-center space-x-3">
            <h3 class="truncate text-sm font-medium text-gray-900">{{ dw.name }}</h3>
            <span class="inline-flex flex-shrink-0 items-center rounded-full bg-green-50 px-1.5 py-0.5 text-xs font-medium text-green-700 ring-1 ring-inset ring-green-600/20">{{ dw.role }}</span>
          </div>
          <p class="mt-1 truncate text-sm text-gray-500">{{ dw.content }}</p>
        </div>
      </div>
    </li>
    <li v-for="sh in search" :key="sh.title" class="col-span-1 divide-y divide-gray-200 rounded-lg bg-white shadow">
      <div class="flex w-full items-center justify-between space-x-6 p-6">
        <div class="flex-1 truncate">
          <h3 class="text-lg text-gray-800 font-bold py-3">{{ sh.title }}</h3>
          <div class="flex items-center space-x-3">
            <h3 class="truncate text-sm font-medium text-gray-900">{{ sh.name }}</h3>
            <span class="inline-flex flex-shrink-0 items-center rounded-full bg-green-50 px-1.5 py-0.5 text-xs font-medium text-green-700 ring-1 ring-inset ring-green-600/20">{{ sh.role }}</span>
          </div>
          <p class="mt-1 truncate text-sm text-gray-500">{{ sh.content }}</p>
        </div>
      </div>
    </li>
  </ul>

  <!-- Start of your new two-column layout -->
  <div class="grid grid-cols-1 2xl:grid-cols-2 gap-8 mt-8">  <!-- Use Tailwind's grid plugin for two columns -->
    <!-- Left Side -->
    <div>
      <div class="border-t border-gray-200/10 pt-8">
        <h2 class="px-4 text-base font-semibold leading-7 sm:px-6 lg:px-8">Today's History</h2>
        <table class="mt-6 w-full whitespace-nowrap text-left">
          <colgroup>
            <col>
            <col>
            <col>
            <col>
            <col>
          </colgroup>
          <thead class="border-b border-gray-200/5 text-sm leading-6">
          <tr>
            <th scope="col" class="py-2 pl-4 pr-8 font-semibold sm:pl-6 lg:pl-8">User</th>
            <th scope="col" class="hidden py-2 pl-0 pr-8 font-semibold sm:table-cell">Commit</th>
            <th scope="col" class="py-2 pl-0 pr-4 text-right font-semibold sm:pr-8 sm:text-left lg:pr-20">Status</th>
            <th scope="col" class="hidden py-2 pl-0 pr-8 font-semibold md:table-cell lg:pr-20">Duration</th>
            <th scope="col" class="hidden py-2 pl-0 pr-4 text-right font-semibold sm:table-cell sm:pr-6 lg:pr-8">Deployed at</th>
          </tr>
          </thead>
          <tbody class="divide-y divide-gray-200/5">
          <tr v-for="item in activityItems" :key="item.commit">
            <td class="py-4 pl-4 pr-8 sm:pl-6 lg:pl-8">
              <div class="flex items-center gap-x-2">
                <div class="truncate text-sm font-medium leading-6">{{ item.user.name }}</div>
              </div>
            </td>
            <td class="hidden py-4 pl-0 pr-4 sm:table-cell sm:pr-8">
              <div class="flex gap-x-2">
                <div class="font-mono text-sm leading-6 text-gray-400">{{ item.commit }}</div>
                <span class="inline-flex items-center rounded-md bg-gray-400/10 px-2 py-1 text-xs font-medium text-gray-400 ring-1 ring-inset ring-gray-400/20">{{ item.branch }}</span>
              </div>
            </td>
            <td class="py-4 pl-0 pr-4 text-sm leading-6 sm:pr-8 lg:pr-20">
              <div class="flex items-center justify-end gap-x-2 sm:justify-start">
                <time class="text-gray-400 sm:hidden" :datetime="item.dateTime">{{ item.date }}</time>
                <div :class="[statuses[item.status], 'flex-none rounded-fulltes p-1']">
                  <div class="h-1.5 w-1.5 rounded-full bg-current" />
                </div>
                <div class="hidden sm:block">{{ item.status }}</div>
              </div>
            </td>
            <td class="hidden py-4 pl-0 pr-8 text-sm leading-6 text-gray-400 md:table-cell lg:pr-20">{{ item.duration }}</td>
            <td class="hidden py-4 pl-0 pr-4 text-right text-sm leading-6 text-gray-400 sm:table-cell sm:pr-6 lg:pr-8">
              <time :datetime="item.dateTime">{{ item.date }}</time>
            </td>
          </tr>
          </tbody>
        </table>

        <div class="flex justify-center">
          <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
            <a href="#" class="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
              <span class="sr-only">Previous</span>
              <ChevronLeftIcon class="h-5 w-5" aria-hidden="true" />
            </a>
            <!-- Current: "z-10 bg-indigo-600 text-white focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600", Default: "text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:outline-offset-0" -->
            <a href="#" aria-current="page" class="relative z-10 inline-flex items-center bg-indigo-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">1</a>
            <a href="#" class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">2</a>
            <a href="#" class="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex">3</a>
            <span class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-700 ring-1 ring-inset ring-gray-300 focus:outline-offset-0">...</span>
            <a href="#" class="relative hidden items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 md:inline-flex">8</a>
            <a href="#" class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">9</a>
            <a href="#" class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">10</a>
            <a href="#" class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0">
              <span class="sr-only">Next</span>
              <ChevronRightIcon class="h-5 w-5" aria-hidden="true" />
            </a>
          </nav>
        </div>
      </div>
    </div>
    <!-- Right Side -->
    <div>
      <div class="bg-white">
        <div class="px-6 py-24 sm:px-6 sm:py-32 lg:px-8">
          <div class="mx-auto max-w-2xl text-center">
            <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Loli Document Download</h2>
            <p class="mx-auto mt-6 max-w-xl text-lg leading-8 text-gray-600">(List of Lists, XML Sturcture Changes, ChemADVISOR update Document)</p>
            <div class="mt-10 flex items-center justify-center gap-x-6">
              <a href="#" class="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Download</a>
              <a href="#" class="text-sm font-semibold leading-6 text-gray-900">Learn more <span aria-hidden="true">→</span></a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- End of your new two-column layout -->

</template>