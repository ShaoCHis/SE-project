import Vue from "vue"
import VueRouter from "vue-router"
import Login from "../views/LoginRegister/Login"

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const routes=[
    {
        path:'/',
        name:'login',
        component:Login
    },
    {
        path:'Register',
        name:'register',
        component: ()=>import('../views/LoginRegister/Register'),
    },
    {
        path:'StudentHome',
        name:'studentHome',
        component: ()=>import('../views/Student/Home'),
        children:[
            {
                path: 'UserInfo',
                name: "userInfo",
                component: ()=>import('../views/UserInfo')
            },
            {
                path:'ExperimentNotice',
                name:"experimentNotice",
                component:()=>import('../views/Student/ExperimentNotice')
            },
            {
                path:'ExperimentList',
                name:"experimentList",
                component:()=>import('../views/Student/ExperimentList')
            },
            {
                path:'ExperimentReport',
                name:"experimentReport",
                component:()=>import('../views/Student/ExperimentReport')
            },
            {
                path:'ExperimentAttendance',
                name:"experimentAttendance",
                component:()=>import('../views/Student/ExperimentAttendance')
            }
        ]
    },
];
const router = new VueRouter({
    history: true,
    routes : routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.length ===0) { //如果未匹配到路由
        from.name ? next({ name:from.name }) : next('/'); //如果上级也未匹配到路由则跳转登录页面，如果上级能匹配到则转上级路由
    } else {
        next(); //如果匹配到正确跳转
    }
});

export default router