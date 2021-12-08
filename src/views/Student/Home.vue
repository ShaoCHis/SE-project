<!--
author:ShaoCHi
date: 2021/11/25 6:34 下午
IDE:WebStorm
Tongji University
-->
<template>
  <div style="height: 100%">
    <el-container>
      <el-header style="height: 6%;background-color:cornflowerblue">

        <span style="font-weight: bolder;color: black;font-size: 25px;position:absolute;left: 5%;margin-top: 3px">
          同济大学实验教学平台<i class="el-icon-loading" style="color:white"></i></span>

        <el-button @click="openOr" icon="el-icon-arrow-left"
                   style="font-size:120%;color:white;background-color: cornflowerblue;border: cornflowerblue;position:absolute;left: 0px"></el-button>

        <div style="position: absolute;right: 0px;margin-top: 6px">
          <user-controller></user-controller>
        </div>
      </el-header>
      <el-container>
        <el-aside
            style="background-color:RGBA(255,255,255,0.3);width: fit-content;border-radius: 25px;box-shadow: 0 3px 16px rgba(0, 0, 0, 0.5);height: 100%">
          <el-menu default-active="1" class="el-menu-vertical-demo" :collapse="isCollapse">
            <el-menu-item index="1" @click="goNotice" style="font-size: 20px;margin-top: 20px">
              <i class="el-icon-menu"></i>
              <span slot="title" style="font-weight: bolder">实验公告</span>
            </el-menu-item>
            <el-menu-item index="2" @click="goList" style="font-size: 20px;margin-top: 20px">
              <i class="el-icon-document"></i>
              <span slot="title" style="font-weight: bolder">实验列表</span>
            </el-menu-item>
            <el-menu-item index="3" @click=goReport style="font-size: 20px;margin-top: 20px">
              <i class="el-icon-setting"></i>
              <span slot="title" style="font-weight: bolder">实验报告</span>
            </el-menu-item>
          </el-menu>

        </el-aside>

        <el-main style="margin-left: 20px">
          <div>
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>


<script>
import userController from "@/components/userController";

export default {
  name: "StudentHome",

  components:{userController},

  data() {
    return {
      isCollapse: true,
    };
  },
  methods: {
    openOr() {
      this.isCollapse = !this.isCollapse;
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    goNotice(){
      this.$router.push({
        name:"experimentNotice"
      })
    },
    goList(){
      this.$router.push({
        name:"experimentList"
      })
    },
    goReport(){
      this.$router.push({
        name:"experimentReport"
      })
    }
  },
  mounted() {
    this.goNotice();
  },
  created() {
    this.goNotice();
  }
}
</script>

<style scoped>
.el-container {
  height: 100%;
  border: 0;
  top: 0;
  margin: 0;
}

.el-aside {
  height: 100%;
}

.el-main {
  /*line-height: 100vh;*/
  height: 100%;
  padding: 0;
}
</style>