<!--
author:ShaoCHi
date: 2021/11/25 2:15 下午
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
          <h1>激活</h1>
        </div>
        <el-form
            ref="loginFormRef"
            :model="RegisterForm"
            label-width="0px"
            class="login_form"
        >
          <!-- 用户名 -->
          <el-form-item prop="userid">
            <el-input
                v-model="RegisterForm.email"
                prefix-icon="el-icon-user"
                placeholder="请输入邮箱（账号邮箱）"
                clearable
            ></el-input>
          </el-form-item>
          <!--          验证码-->
          <el-form-item prop="userid">
            <div>
              <el-input
                  v-model="RegisterForm.identification"
                  prefix-icon="el-icon-search"
                  placeholder="请输入验证码"
                  style="width: 72%;border: 10px"
                  maxlength="4"
                  show-word-limit
                  clearable
              ></el-input>
              <el-button style="font-weight: bold;right: 0;" @click="getIdentification">获取验证码</el-button>
            </div>
          </el-form-item>
          <!-- 密码 -->
<!--          <el-form-item prop="password">-->
<!--            <el-input-->
<!--                v-model="RegisterForm.password"-->
<!--                prefix-icon="el-icon-lock"-->
<!--                type="password"-->
<!--                placeholder="请输入密码"-->
<!--                show-password-->
<!--                clearable-->
<!--            ></el-input>-->
<!--          </el-form-item>-->
          <!--          确认密码-->
<!--          <el-form-item prop="password">-->
<!--            <el-input-->
<!--                v-model="password"-->
<!--                prefix-icon="el-icon-lock"-->
<!--                type="password"-->
<!--                placeholder="请再次输入密码"-->
<!--                show-password-->
<!--                clearable-->
<!--            ></el-input>-->
<!--          </el-form-item>-->

          <!-- 按钮区域 -->
          <el-form-item class="btns">
            <el-button type="primary" @click="register" @keydown="register">激活</el-button>
            <el-button type="info" @click="resetLoginForm">重置</el-button>
            <el-button type="success" @click="show">帮助</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

axios.defaults.withCredentials = true;

export default {
  name: "Login",

  data() {
    return {
      password: "",
      RegisterForm: {
        email: "",
        identification: "",
      },
      loginRules: {
        username: [
          {required: true, trigger: "change",},
        ],
        password: [
          {required: true, trigger: "change"},
          // , validator: isPassword
        ],
      },
      identification: "",
      userId:""
    }
  },

  methods: {
    resetLoginForm() {
      // console.log(this)
      this.$refs.loginFormRef.resetFields();
    },

    async getIdentification() {
      let that=this;
      axios.get(`//localhost:8080/mails/sendMail?` + that.userId
      ).then((res => {
        if(res.data.data.success==true){
          this.$message.success(res.data.data.message)
        }
        else{
          this.$message.error("验证码发送失败！请重试！");
        }
        console.log(res)
      })).catch((res) => {
        console.log(res);
        this.$message.error("验证码发送失败！请重试！");
      })
    },

    async register() {
      axios.post(`//localhost:8080/mails/identify?code=`+this.RegisterForm.identification+"&userid="+this.userId).then((res => {
        console.log(res)
        if (res.data.success == true) {
          this.$message.success("激活成功！请登陆！");
          this.$router.push({
            name: "login"
          })
        } else {
          this.$message.success("用户已激活，请登录！");
          this.$router.push({
            name: "login"
          })
        }
      })).catch(res => {
        this.$message.error("该用户不存在，请联系管理员！");
        console.log(res)
      })
    },

    show() {
      this.$alert(
          "该系统只针对选课的师生开放，如果您未进行选课或授课，将不具有该系统的使用权限；激活成功后可以使用工号及密码进行登陆该系统。",
          "帮助",
          {
            confirmButtonText: "确定",
          }
      )
    },
  },
  created() {
    this.userId=this.$route.params.userId;
  }
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
  height: 350px;
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