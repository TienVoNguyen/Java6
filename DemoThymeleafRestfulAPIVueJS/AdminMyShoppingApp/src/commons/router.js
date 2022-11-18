import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/User/Login";
import Register from "@/components/User/Register";

Vue.use(Router);

const router = new Router({
    mode: "history",
    routes: [
        {
            path: '/login',
            component: Login,
        },
        {
            path: '/autologin/:param',
            alias: "/payloadUser",
            component: () => import("@/components/User/AutoLogin")
        },
        {
            path: '/register',
            component: Register
        },
        {
            path: "/",
            alias: "/blog-list",
            name: "blog-list",
            component: () => import("../components/ProductListComponent")
        },
        {
            path: "/add",
            alias:"/product",
            name: "product",
            component: () => import("../components/ProductComponent")
        },
        {
            path: "/order",
            alias:"/order",
            name: "order",
            component: () => import("../components/Order/OrderComponent")
        }
    ]
});
router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/home', '/autologin'];
    // http://localhost:8080/autologin/589626994
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    // trying to access a restricted page + not logged in
    // redirect to login page
    if(to.path.startsWith("/autologin")) {
        next();
    } else if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});
export default router
