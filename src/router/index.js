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
            }
        ]
    },
]

const router=new VueRouter({
    mode:"history",
    base:process.env.BASE_URL,
    routes
})

export default router