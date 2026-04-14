import type {RouteRecordRaw} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";

const routes: RouteRecordRaw[] = [
	{
		path: '/login',
		name: 'login',
		component: () => import("../pages/Login/index.vue"),
	}, {
		path: '/register',
		name: 'register',
		component: () => import("../pages/Register/index.vue"),
	}, {
		path: '/detail',
		name: 'detail',
		component: () => import("../pages/Detail/index.vue"),
	}, {
		path: '/addormodifynews',
		name: 'addormodifynews',
		component: () => import("../pages/addOrModifyNews/index.vue"),
	}, {
		path: '/headlinenews',
		name: 'headlinenews',
		component: () => import("../pages/HeadlineNews/index.vue")
	}, {
		path: '/',
		redirect: "/headlinenews"
	}
];


const router = createRouter({
	history: createWebHistory(),
	routes
});

export default router;
