import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: () => import("../pages/DashBoard.vue"),
    beforeEnter: (_, __, next) => {
      const storedUser = JSON.parse(localStorage.getItem('user') || '{}');
      const isUserLoggedIn: boolean = !!storedUser.username;
      const isUserAdmin: boolean = storedUser.role === 'ADMIN' || storedUser.role === 'USER';

      console.log('Before enter guard. isUserLoggedIn:', isUserLoggedIn, 'isUserAdmin:', isUserAdmin);

      if (isUserLoggedIn && isUserAdmin) {
        next();
      } else if (
        storedUser.role !== 'ADMIN' || storedUser !== 'USER'
      ) {
        next('/login');
      } else {
        next('/login')
      }
    }
  },
  {
    path: "/login",
    component: () => import("../components/Login.vue"),
    beforeEnter: (_, __, next) => {
      const storedUser = JSON.parse(localStorage.getItem('user') || '{}');
      const isUserLoggedIn: boolean = !!storedUser.username;
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

