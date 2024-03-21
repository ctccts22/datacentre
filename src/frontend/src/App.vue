<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import Navbar from '@/layout/Navbar.vue';
import Footer from '@/layout/Footer.vue';

const route = useRoute();

const showNavbarFooter = computed(() => route.path !== '/login');
</script>
<template>
  <div id="app">
    <Navbar v-if="showNavbarFooter" />
    <router-view />
    <Footer v-if="showNavbarFooter" />
  </div>
</template>
