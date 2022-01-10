<template>
  <div class="main">
    <el-row>
      <!--    公告板-->
      <div class="board">
        <el-tabs label="系统公告" v-model="SysactiveName" @tab-click="handleClick1">
          <el-tab-pane label="系统公告一" name="first">公告一</el-tab-pane>
          <el-tab-pane label="系统公告二" name="second">公告二</el-tab-pane>
          <el-tab-pane label="系统公告三" name="third">公告三</el-tab-pane>
          <el-tab-pane label="系统公告四" name="fourth">公告四</el-tab-pane>
        </el-tabs>
      </div>
      <div class="board">
        <el-tabs label="教师公告" v-model="TeaactiveName" @tab-click="handleClick2">
          <el-tab-pane label="教师公告一" name="first">公告一</el-tab-pane>
          <el-tab-pane label="教师公告二" name="second">公告二</el-tab-pane>
          <el-tab-pane label="教师公告三" name="third">公告三</el-tab-pane>
          <el-tab-pane label="教师公告四" name="fourth">公告四</el-tab-pane>
        </el-tabs>
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

export default {
  data() {

    return {
      //公告板
      message1: "公告一",
      SysactiveName: 'first',
      TeaactiveName:'first',

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
  methods: {
    handleClick1 (tab, event) {
      console.log(tab, event)
    },
    handleClick2 (tab, event) {
      console.log(tab, event)
    },
    //实验详情
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

/*公告板*/
.board {
  background-color: #d3dce6;
  float: left;
  margin-left: 50px;
  padding: 10px;
  height: 200px;
  width: 40%;
  border-radius: 10px;
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

</style>