<!--
author:ShaoCHi
date: 2021/11/26 4:33 下午
IDE:WebStorm
Tongji University
-->

<template>
  <el-dropdown @command="handleCommand" ref="jump">
            <span class="el-dropdown-link" style="color: white;font-size: 20px;width: fit-content">
              <i class="el-icon-user-solid"></i>
              个人中心<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item icon="el-icon-user-solid" command="User">个人信息</el-dropdown-item>
      <el-dropdown-item icon="el-icon-remove" command="Login">登出</el-dropdown-item>
      <el-dropdown-item icon="el-icon-info" command="About">关于</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  name: "userInfo",

  methods:{
    handleCommand(command) {
      if (command == "User") {
        console.log("111")
        this.$router.push({
          name: "userInfo"
        })
      } else if (command == "Login") {
        console.log("Login")
        this.$router.push(({
          name: "login"
        }))
      } else if (command == "About") {
        this.$alert(
            "实验教学系统v1.0;" +
            "\n" +
            "遇到问题请联系：xxxxx@126.com",
            "关于",
            {
              confirmButtonText: "确定",
            }
        )
      }
    }
  }
}
</script>

<style scoped>

</style>