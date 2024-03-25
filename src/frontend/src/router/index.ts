import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: () => import("../pages/DashBoard.vue")
  },
  {
    path: "/registration",
    component: () => import("../components/Registration.vue")
  },
  {
    path: "/member-management",
    component: () => import("../components/MemberManagement.vue")
  },
  {
    path: "/login",
    component: () => import("../components/Login.vue")
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, _, next) => {
  const storedUser = JSON.parse(localStorage.getItem('user') || '{}');
  const isUserLoggedIn = !!storedUser.username;

  // Redirect user to dashboard if they are already logged in and trying to access login page.
  if (isUserLoggedIn && to.path === '/login') {
    next('/');
    return;
  }

  // Exclude paths that can be accessed by anyone.
  const excludePaths = ['/login', '/registration'];

  // Verify whether the user has necessary roles.
  const hasRequiredRole = storedUser.role === 'ADMIN' || storedUser.role === 'USER';

  if (isUserLoggedIn && hasRequiredRole) {
    // If user is logged in and has the required role, they can access any page.
    next();
  } else if (!excludePaths.includes(to.path)) {
    // If the user is not logged in and they are trying to access a page other than login or registration, redirect them to login page.
    next('/login');
  } else {
    // If the user is not logged in but they are trying to access login or registration page, allow them.
    next();
  }
});

export default router;