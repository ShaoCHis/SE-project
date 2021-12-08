<!--
author:ShaoCHi
date: 2021/12/8 10:12 AM
IDE:WebStorm
Tongji University
-->

<template>
  <div class="block" style="width: 100%">
    <el-timeline style="margin-top: 5%">
      <el-timeline-item
          v-for="(activity, index) in activities"
          :key="index"
          :icon="activity.icon"
          :type="activity.type"
          :color="activity.color"
          :size="activity.size"
          :timestamp="activity.timestamp" placement="top">
        <el-card style="height: 10vh">
          {{activity.content}}
          <el-button style="left: 20px">填写</el-button>
        </el-card>
<!--        {{activity.content}}-->
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
export default {
  name: "ExperimentReport",
  data(){
    return{
      activities: [{
        content: 'Custom icon',
        timestamp: '2018-04-12 20:46',
        size: 'large',
        type: 'primary',
        icon: 'el-icon-more'
      }, {
        content: 'Custom color',
        timestamp: '2018-04-03 20:46',
        color: '#0bbd87'
      }, {
        content: 'Custom size',
        timestamp: '2018-04-03 20:46',
        size: 'large'
      }, {
        content: 'Default node',
        timestamp: '2018-04-03 20:46'
      }]
    }
  }
}
</script>

<style scoped>

</style>