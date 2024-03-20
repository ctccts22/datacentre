<template>
  <div class="leading-normal tracking-normal" id="main-body">
      <Sidebar/>
      <Navbar/>
      <Home/>
      <Footer/>
  </div>
</template>

<script setup lang="ts">
import Sidebar from "../layout/Sidebar.vue";
import Navbar from "../layout/Navbar.vue";
import Home from "@/pages/Home.vue";


</script>