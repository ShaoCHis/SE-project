<!--
author:ShaoCHi
date: 2021/11/26 5:13 下午
IDE:WebStorm
Tongji University
-->

<template>
  <div>
    <div>
      <h1 style="font-size: 25px;margin-right: 65%">{{courserInfo.name}}</h1>
      <p style="font-size:20px;margin-right: 60%">{{courserInfo.intro}}</p>
    </div>
    <h2 style="font-size: 25px;margin-right: 65%">实验公告</h2>
    <div class="infinite-list-wrapper" style="overflow:auto;height: 30vh;width: 100%">
      <ul
          class="list"
          v-infinite-scroll="load"
          infinite-scroll-disabled="disabled">
        <li v-for="i in systemNotices" class="list-item" :key=i.noticeId>
          {{ i.category + "!" + i.title + ":" + i.content }}
          <br>
          <span style="font-size: 15px;align:right">{{ i.uploadTime }}</span>
        </li>
      </ul>
      <p v-if="loading">Loading...</p>
      <p v-if="noMoreSystem">No more</p>
    </div>
    <h2 style="font-size: 25px;margin-right: 65%">教师公告</h2>
    <div class="infinite-list-wrapper" style="overflow:auto;height: 30vh">
      <ul
          class="list"
          v-infinite-scroll="load"
          infinite-scroll-disabled="disabled">
        <li v-for="i in classNotices" class="newList-item" :key=i.noticeId>
          {{ i.category + "!" + i.title + ":" + i.content }}
          <br>
          <span style="font-size: 15px;align:right">{{ i.uploadTime }}</span>
        </li>
      </ul>
      <p v-if="loading">Loading...</p>
      <p v-if="noMoreClass">No more</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

axios.defaults.withCredentials = true;

export default {
  name: "ExperimentNotice",

  data() {
    return {
      id: "",
      classId: "",
      //公告显示的条数
      classCount: 10,
      systemCount: 10,
      //加载状态
      loading: false,
      systemNotices: [],
      classNotices: [],
      courserInfo:{},
    }
  },
  computed: {
    noMoreClass() {
      return this.count >= this.classNotices.length;
    },
    disabledClass() {
      return this.loading || this.noMoreClass
    },
    noMoreSystem() {
      return this.count >= this.systemNotices.length;
    },
    disabledSystem() {
      return this.loading || this.noMoreSystem
    },
  },
  methods: {
    load() {
      this.loading = true
      setTimeout(() => {
        this.count += 2
        this.loading = false
      }, 2000)
    },
    async getSystemNotices() {
      let that = this;
      axios.get(`//139.224.65.154:8080/sysnotices`).then((res) => {
        if (res.data.success == true) {
          that.systemNotices = res.data.data;
        }
        for (let i in that.systemNotices) {
          //let delayTime = new Date(experiment.uploadTime).toJSON();
          that.systemNotices[i].uploadTime = new Date(
              +new Date(that.systemNotices[i].uploadTime) + 8 * 3600 * 1000
          )
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "");
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async getClassNotices() {
      let that = this;
      axios.get(`//139.224.65.154:8080/classnotices/` + that.classId).then((res) => {
        if (res.data.success == true) {
          that.classNotices = res.data.data;
        }
        for (let i in that.classNotices) {
          //let delayTime = new Date(experiment.uploadTime).toJSON();
          that.classNotices[i].uploadTime = new Date(
              +new Date(that.classNotices[i].uploadTime) + 8 * 3600 * 1000
          )
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "");
        }
        console.log(res.data.data)
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async getCourse(){
      let that = this;
      axios.get(`//139.224.65.154:8080/classes/getcourse/` + that.classId).then((res) => {
        if (res.data.success == true) {
          that.courserInfo = res.data.data;
          console.log(res)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    }
  },

  mounted() {
    this.id = this.$route.params.id;
    this.classId = this.$route.params.classId;
    this.getClassNotices();
    this.getSystemNotices();
    this.getCourse();
  },
  watch: {
    '$route.params.classId'() {
      this.classId = this.$route.params.classId;
      this.getClassNotices()
      this.getCourse();
    },
  },
  created() {
    this.id = this.$route.params.id;
    this.classId = this.$route.params.classId;
    this.getClassNotices();
    this.getSystemNotices();
    this.getCourse();
  }
}
</script>

<style>
.block {
  display: inline-block;
}

.list {
  list-style: none;
  padding: 0;
  margin: 1px;
}

.list-item {
  list-style: none;
  padding: 0;
  margin: 4px;
  padding: 2px;
  width: 100%;
  background-color: #f0f9eb;
  font-size: 20px;
  float: left;
}
.newList-item {
  list-style: none;
  padding: 0;
  margin: 4px;
  padding: 2px;
  width: 100%;
  background-color: oldlace;
  font-size: 25px;
  float: left;
}
</style>