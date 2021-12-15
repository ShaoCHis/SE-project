import router from './router'
import store from './store'
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权

const whiteList = ['/login','/register'] // 不重定向白名单
router.beforeEach((to, from, next) => {

    //如果未匹配到路由

    if (to.matched.length ===0) {

        //如果上级也未匹配到路由则跳转登录页面，如果上级能匹配到则转上级路由

        from.name ? next({ name:from.name }) : next('/');

    } else {

        next(); //如果匹配到正确跳转

    }

});
router.beforeEach((to, from, next) => {
    if (getToken()) {
        if (to.path === '/login') {
            next({ path: '/' })// if current page is dashboard will not trigger	afterEach hook, so manually handle it
        } else {
            if (store.getters.roles.length === 0) {
                store.dispatch('GetInfo').then(res => { // 拉取用户信息
                    next()
                    console.log(res)
                }).catch((err) => {
                    store.dispatch('FedLogOut').then(() => {
                        Message.error(err || '认证失败，请重新登录')
                        next({ path: '/' })
                    })
                })
            } else {
                next()
            }
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next(`/login`) // 否则全部重定向到登录页
        }
    }
})