<!--
author:ShaoCHi
date: 2021/11/26 5:13 下午
IDE:WebStorm
Tongji University
-->

<template>
  <div>
    <h1 style="font-size: 30px;margin-right: 65%">实验公告</h1>
    <div>
    <el-table
        :data="tableData"
        :row-class-name="tableRowClassName"
        border
        style="width: 100%;font-weight: bolder;font-size: 15px;row-class-name:tableRowClassName">
      <el-table-column
          fixed
          prop="date"
          label="日期"
          width="150">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="province"
          label="省份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="city"
          label="市区"
          width="120">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址"
          width="300">
      </el-table-column>
      <el-table-column
          prop="zip"
          label="邮编"
          width="120">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "ExperimentNotice",

  methods: {
    handleClick(row) {
      console.log(row);
    },
    tableRowClassName({row, rowIndex}) {
      if (rowIndex % 2 == 0) {
        return 'warning-row';
      } else {
        console.log(row)
        return 'success-row';
      }
    }
  },

  data() {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-04',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1517 弄',
        zip: 200333
      }, {
        date: '2016-05-01',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1519 弄',
        zip: 200333
      }, {
        date: '2016-05-03',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1516 弄',
        zip: 200333
      }],
      currentDate: new Date()
    }
  }
}
</script>

<style>
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