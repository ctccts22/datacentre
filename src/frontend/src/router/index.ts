import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: () => import("../pages/Dashboard.vue"),
  },
  {
    path: "/login",
    component: () => import("../components/Login.vue"),
    beforeEnter: (_, __, next) => {
      const storedUser = JSON.parse(localStorage.getItem('user') || '{}');
      const isUserLoggedIn: boolean = !!storedUser.id;
      console.log('Before enter guard. isUserLoggedIn:', isUserLoggedIn);
      if (isUserLoggedIn) {
        next('/');
      } else {
        next();
      }
    }
  },
];

export default createRouter({
  history: createWebHistory(),
  routes,
});

