import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from '@/App.vue';
import "./assets/css/index.css";
import router from '@/router';

const pinia = createPinia();

createApp(App).use(pinia).use(router).mount('#app');