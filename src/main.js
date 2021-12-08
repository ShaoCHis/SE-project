import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from "element-ui"
import 'element-ui/lib/theme-chalk/index.css'
import axios from "axios"
import infiniteScroll from 'vue-infinite-scroll'


axios.defaults.withCredentials = true;

Vue.use(ElementUI,infiniteScroll)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')