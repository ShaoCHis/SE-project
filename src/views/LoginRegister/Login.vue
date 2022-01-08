<!--
author:ShaoCHi
date: 2021/11/18 8:37 上午
IDE:WebStorm
Tongji University
-->

<template>
  <div class="background">
    <div class="login_container">
      <div class="title">
        <h1>同济大学实验教学管理系统</h1>
      </div>
      <div class="login_box">
        <div class="tip">
          <h1>登录</h1>
        </div>
        <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            label-width="0px"
            class="login_form"
        >
          <!-- 用户名 -->
          <el-form-item prop="userid">
            <el-input
                v-model="loginForm.email"
                prefix-icon="el-icon-user"
                placeholder="请输入邮箱"
            ></el-input>
          </el-form-item>
          <!-- 密码 -->
          <el-form-item prop="password">
            <el-input
                v-model="loginForm.password"
                prefix-icon="el-icon-lock"
                type="password"
                placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <!--          验证码区域-->
          <el-form-item>
            <div class="form-group" style="display: flex;">
              <div>
                <el-input ref="identify" style="width:100%" type="text" id="code" v-model="code" class="code"
                          placeholder="请输入您的验证码" @keydown.enter.native="login"/>
              </div>
              <div @click="refreshCode">
                <!--验证码组件-->
                <SIdentify :identifyCode="identifyCode"></SIdentify>
              </div>
            </div>
          </el-form-item>
          <!-- 按钮区域 -->
          <el-form-item class="btns">
            <el-button type="primary" @click="login">登录</el-button>
            <el-button type="info" @click="resetLoginForm">重置</el-button>
            <el-button type="text" @click="register"
            >
              <div><p style="font-size:15px">激活账号</p></div>
            </el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {validateEMail, isPassword} from "@/utils/validator";
import SIdentify from "@/components/identify";

axios.defaults.withCredentials = true;

export default {
  name: "Login",
  components: {SIdentify},
  data() {
    return {
      loginForm: {
        email: "",
        password: "",
      },
      loginRules: {
        email: [
          {required: true,trigger: "change",validator:validateEMail},
        ],
        password: [
          { required: true, trigger: "blur", min: 1, message: "密码不能为空" },
          {
            required: true,trigger: "blur",validator:isPassword
          }
        ],
      },
      identifyCodes: "1234567890",
      identifyCode: "",
      code: "",//text框输入的验证码
    }
  },

  methods: {
    resetLoginForm() {
      // console.log(this)
      this.$refs.loginFormRef.resetFields();
      this.$refs.identify.clear();
    },

    //根据返回码决定身份进行页面的跳转
    async login() {
      this.$router.push({
        name:"teacherHome"
      })
      // if(this.code!=this.identifyCode){
      //   this.$message.error("验证码错误！请重新输入！")
      //   this.refreshCode();
      //   return
      // }
      // let that = this;
      // // let url = "106.14.69.227:18080/api/Login/session";
      // // let data = {"name": that.loginForm.email, "password": that.loginForm.password};
      // axios.post(`//139.224.65.154:8080/users/login?`+"name="+that.loginForm.email+"&password="+that.loginForm.password).then((res) => {
      //   if (res.data.success == true) {
      //
      //     //后续修改为角色判断字段
      //
      //     if(res.data.data.roleId==4) {
      //       this.$router.push({
      //         name: "studentHome",
      //         params: {
      //           id: res.data.data.specialId
      //         }
      //       })
      //     }
      //     this.$message.success("Login Success!Welcome!")
      //   }
      //   console.log(res)
      // }).catch((res) => {
      //   console.log(res);
      //   that.$message.error("Login Failed!Please try again!");
      // })
    },

    register() {
      this.$router.push({
        name: "register",
      });
    },

    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },

    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },

    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode +=
            this.randomNum(0, o.length)
        ;
      }
      console.log(this.identifyCode);
    },
  },
  created() {
    this.refreshCode();
  },
}
</script>

<style lang="less" scoped>
.background {
  background: url(../../assets/pictures/login.jpg);
  background-size: 100% 100%;
  height: 100%;
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
}

.title {
  color: cornflowerblue;
  font-size: 20px;
  font-weight: bold;
  text-align: left;
  margin-bottom: 0px;
}

.tip {
  color: cornflowerblue;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 30px;
}

.login_container {
  background-color: RGBA(255, 255, 255, 0.2);
  background-size: 100%, 100%;
  height: 100%;
  width: 100%;
  position: fixed;
}

.login_box {
  width: 450px;
  height: 380px;
  background-color: RGBA(255, 255, 255, 0.5);
  border-radius: 25px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  box-shadow: 0 3px 16px rgba(0, 0, 0, 0.5);

  .avatar_box {
    height: 110px;
    width: 110px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 5px;
    box-shadow: 0 0 2px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: RGB(255, 255, 255);

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}

.login_form {
  position: relative;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: center;
}
</style>