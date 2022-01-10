<template>
  <div class="main">
    <el-row>
      <!--    公告板-->

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
    </el-row>
    <el-divider></el-divider>
    <el-row class="new_exp" style="color: #409EFF">
      <el-col :span="24" style="height: fit-content;margin-bottom: 20px">
        <el-card shadow="always" style="width: 200px;color: #409EFF;height: fit-content"> 最近实验</el-card>
      </el-col>
      <!--      最近实验-->
      <el-col :span="6">
        <el-card class="newExp"
                 :body-style="{ padding: '0px'}" style="width: 250px"
                 @click="experimentInformation(exp.experiment_id)">
          <img v-if="exp.face!==''"
               :src=exp.face
               class="image"
               style="height: 200px"/>
          <img v-else
               :src=imgNull
               class="image"
               style="height: 200px"/>
          <div style="padding: 14px">
            <span>{{ exp.experiment_name }}</span>
            <div class="bottom">
              <time class="time">截止日期：{{ exp.release_time }}</time>
              <el-button type="text" class="button" @click="experimentInformation(exp.experiment_id)">查看详情</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import axios from "axios";

axios.defaults.withCredentials = true;

export default {
  data() {

    return {
      //公告板
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

      //新发布的实验
      imgNull:"http://106.15.6.161:8081/images/ddddffffdefault.jpg",
      exp:{
        experiment_id:'',
        face: '',
        release_time: '',
        experiment_name: '',
        course_id: ''
      },
      latestExp: [],
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
    },
    experimentInformation(experimentId){
      this.$router.push({
        name:'experimentInformation',
        query: {experimentId: experimentId}
      })
    },

    //获取最新发布的实验
    getLatestExp() {
      /*request.get("/api/experiment/latestExp").then(res => {
        this.latestExp = res;
      })*/
    },
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
.main {
  height: max-content;
  background-color: white;
  border-radius: 8px;
  padding: 50px 50px 200px 50px
}



.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}



/*最近实验*/
.new_exp {
  height: 300px;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}
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