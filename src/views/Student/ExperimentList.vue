<!--
author:ShaoCHi
date: 2021/12/8 9:14 AM
IDE:WebStorm
Tongji University
-->

<template>
  <div>
    <div>
      <div class="block" style="position: absolute;left: 20%;margin-top: 10%">
        <span style="font-size: 25px">成绩统计</span>
      </div>
<!--      <div class="block" id="chartLineBox" style="width: 60%;height: 60vh;left: 0%"></div>-->
    </div>
    <div>
      <el-table
          :data="experiments"
          :row-class-name="tableRowClassName"
          border
          height="750"
          style="width: 100%;font-weight: bolder;font-size: 15px;row-class-name:tableRowClassName">
        <el-table-column
            fixed
            prop="experimentId"
            label="序号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="name"
            label="实验名称"
            width="170">
        </el-table-column>
        <el-table-column
            prop="uploadTime"
            label="上传时间"
            width="250">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="200">
          <template slot-scope="index">
            <el-button @click="handleClick(index.$index)" type="text" size="small">查看实验内容</el-button>
            <el-button type="text" size="small" @click="deleteB(index.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import axios from "axios";

export default {
  name: "ExperimentList",
  data() {
    return {
      id: "",
      classId: "",
      experiments: [],
      courserId:"",
      meanScore:[],
      myScore:[],
      name:[],
    }
  },
  methods: {
    tableRowClassName({row, rowIndex}) {
      if (rowIndex % 2 == 0) {
        return 'warning-row';
      } else {
        console.log(row)
        return 'success-row';
      }
    },
    handleClick(row) {
      console.log(row);
      console.log(this.experiments[row])
      this.$alert(
          this.experiments[row].intro,
          "实验内容",
          {
            confirmButtonText: "确定",
          }
      )
    },
    async getExperiments() {
      let that = this;
      axios.get(`//localhost:8080/reports/getexperimentsofclass/` + that.classId).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          that.experiments = res.data.data;
        }
        for (let i in that.experiments) {
          //let delayTime = new Date(experiment.uploadTime).toJSON();
          that.experiments[i].uploadTime = new Date(
              +new Date(that.experiments[i].uploadTime) + 8 * 3600 * 1000
          )
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "");
          console.log(that.experiments[i].uploadTime)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async getExperimentsGradeMean(){
      let that = this;
      axios.get(`//localhost:8080/grades/experimentmeanscore/` + that.classId).then((res) => {
        console.log(res)
        if (res.data.success == true) {
          let i=0;
          while(i<res.data.data.length){
            this.meanScore[i]=res.data.data[i].meanScore;
          }
          console.log(res)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //得到课程Id
    async getCourse(){
      let that = this;
      axios.get(`//localhost:8080/classes/getcourse/` + that.classId).then((res) => {
        console.log(res)
        if (res.data.success == true) {
          that.courserId = res.data.data.courserId;
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //再把得到的成绩放进图表进行显示

    async getExperimentsGrade(){
      let that = this;
      axios.get(`//localhost:8080/reports/?studentid=`+that.id+"&courseid=" + that.courserId).then((res) => {
        console.log(res)
        if (res.data.success == true) {
          let i=0;
          while(i<res.data.data.length){
            this.myScore[i]=res.data.data[i].score;
            i+=1
          }
          let j=0;
          while (j<this.experiments.length){
            this.name[j]=this.experiments[j].score;
            j+=1
          }
          console.log(res)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async deleteB(index){
      let that = this;
      console.log(index)
      axios.delete(`//localhost:8080/experiments/`+this.experiments[index].experimentId).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          this.$message.success(res.data.data)
        }
        else {
          this.$message.error(res.data.message)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    }
  },
  mounted() {
    this.chartLine = echarts.init(document.getElementById('chartLineBox'));
    // 指定图表的配置项和数据
    let option = {
      tooltip: {              //设置tip提示
        trigger: 'axis'
      },

      legend: {               //设置区分（哪条线属于什么）
        data: ['平均成绩', '学生成绩']
      },
      color: ['#8AE09F', '#FA6F53'],       //设置区分（每条线是什么颜色，和 legend 一一对应）
      xAxis: {                //设置x轴
        type: 'category',
        boundaryGap: false,     //坐标轴两边不留白
        data: this.name,
        name: '实验',           //X轴 name
        nameTextStyle: {        //坐标轴名称的文字样式
          color: '#FA6F53',
          fontSize: 16,
          padding: [0, 0, 0, 20]
        },
        axisLine: {             //坐标轴轴线相关设置。
          lineStyle: {
            color: '#FA6F53',
          }
        }
      },
      yAxis: {
        name: '成绩',
        nameTextStyle: {
          color: '#FA6F53',
          fontSize: 16,
          padding: [0, 0, 10, 0]
        },
        axisLine: {
          lineStyle: {
            color: '#FA6F53',
          }
        },
        type: 'value'
      },
      series: [
        {
          name: '平均成绩',
          data: this.meanScore,
          type: 'line',               // 类型为折线图
          lineStyle: {                // 线条样式 => 必须使用normal属性
            normal: {
              color: '#8AE09F',
            }
          },
        },
        {
          name: '学生成绩',
          data: this.myScore,
          type: 'line',
          lineStyle: {
            normal: {
              color: '#FA6F53',
            }
          },
        }
      ]
    };

    // 使用刚指定的配置项和数据显示图表。
    this.chartLine.setOption(option);
  },
  created() {
    this.id = this.$route.params.id;
    this.classId = this.$route.params.classId;
    this.getCourse();
    this.getExperiments();
    // this.getExperimentsGradeMean();
    // this.getExperimentsGrade();
  },
  watch: {
    '$route.params.classId'() {
      this.classId = this.$route.params.classId;
      this.getExperiments()
    },
  },
}
</script>

<style scoped>
.el-table .warning-row {
  background: #9cc0ff;
}

.el-table .success-row {
  background: white;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.time {
  font-size: 13px;
  color: #999;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>