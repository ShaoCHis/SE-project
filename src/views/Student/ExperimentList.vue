<!--
author:ShaoCHi
date: 2021/12/8 9:14 AM
IDE:WebStorm
Tongji University
-->

<template>
  <div>
    <div>
    <div class="block" style="position: absolute;left: 10%;margin-top: 10%">
      <span style="font-size: 25px">成绩统计</span>
    </div>
    <div class="block" id="chartLineBox" style="width: 60%;height: 60vh;left: 0%"></div>
    </div>
    <div class="infinite-list-wrapper" style="overflow:auto;height: 35vh">
      <ul
          class="list"
          v-infinite-scroll="load"
          infinite-scroll-disabled="disabled">
        <li v-for="i in count" class="list-item" :key=i>{{ i }}</li>
      </ul>
      <p v-if="loading">Loading...</p>
      <p v-if="noMore">No more</p>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "ExperimentList",
  data() {
    return {
      count: 10,
      loading: false
    }
  },
  computed: {
    noMore() {
      return this.count >= 20
    },
    disabled() {
      return this.loading || this.noMore
    }
  },
  methods: {
    load() {
      this.loading = true
      setTimeout(() => {
        this.count += 2
        this.loading = false
      }, 2000)
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
        data: ['放大镜', '2019-2-1', '2019-3-1', '2019-4-1', '2019-5-1', '2019-6-1', '2019-7-1',],
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
          data: [220, 232, 201, 234, 290, 230, 220],
          type: 'line',               // 类型为折线图
          lineStyle: {                // 线条样式 => 必须使用normal属性
            normal: {
              color: '#8AE09F',
            }
          },
        },
        {
          name: '学生成绩',
          data: [120, 200, 150, 80, 70, 110, 130],
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
}
</script>

<style scoped>
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
  font-size: 25px;
  float: left;
}
</style>