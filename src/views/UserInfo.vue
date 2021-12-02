<!--
author:ShaoCHi
date: 2021/11/26 1:46 下午
IDE:WebStorm
Tongji University
-->

<template>
  <div>
    <el-descriptions title="用户信息" :column="1" border style="width:70%;margin-left:10%;margin-top: 5%;font-size:20px;font-weight: bolder" class="info">
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          学号姓名
        </template>
        {{info.studentId+info.name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          邮箱
        </template>
        {{info.email}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          年级
        </template>
        {{info.grade}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          专业
        </template>
        <el-tag>{{info.major}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          课程名称
        </template>
        江苏省苏州市吴中区吴中大道 1188 号
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: "UserInfo",

  data(){
    return{
      info:{
        name:"沈韬",
        studentId:"1952395",
        email:"1952395@tongji.edu.cn",
        grade:"大三",
        major:"软件工程"
      }
    }
  },
}
</script>

<style scoped>

</style>