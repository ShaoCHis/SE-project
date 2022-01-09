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
    }
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

</style>