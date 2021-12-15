<!--
author:ShaoCHi
date: 2021/12/14 11:04 AM
IDE:WebStorm
Tongji University
-->

<template>
  <div>
    <h1>
      <span>课程签到处（点击进行签到）</span>
    </h1>
    <div class="block" @click="attendance">
      <el-image :src="imgSrc"></el-image>
    </div>
    <el-dialog
        title="签到"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-input placeholder="请输入签到码" v-model="code"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">签到</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ExperimentAttendance",
  data() {
    return {
      id: "",
      classId: "",
      code: "",
      imgSrc: "http://img.yao51.com/jiankangtuku/qwwwweqsrx.jpeg",
      dialogVisible: false
    }
  },
  methods: {
    attendance() {
      console.log("attendance")
      this.dialogVisible=true
    },
    async submit(){
      let that=this;
      axios.post(`//139.224.65.154:8080/attendances/attend?`+"studentid="+that.id+"&classid="+that.classId+"&code="+that.code).then((res) => {
        if (res.data.success == true) {
          this.$message.success("签到成功！")
          that.dialogVisible=false
        }
      }).catch((res) => {
        console.log(res);
        this.$message.error(res.data.message)
      })
    },
    handleClose(done) {
      this.$confirm('你确定要关闭吗？')
          .then(_ => {
            console.log(_)
            done();
          })
          .catch(_ => {
            console.log(_)
          });
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.classId = this.$route.params.classId;
  }
}
</script>

<style scoped>
.block {
  display: inline-block;
}
</style>