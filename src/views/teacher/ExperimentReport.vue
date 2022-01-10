<template>
  <div style="padding: 20px">
    <el-table :data="showTable"
              stripe
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'ascending' }"
    >
      <el-table-column label="学号" prop="stuNum" />
      <el-table-column label="姓名" prop="name" />
      <el-table-column
          label="实验名称"
          prop="experimentName"
          sortable
      />
      <el-table-column label="提交时间" prop="date" sortable />
      <el-table-column label="批改状态"
                       prop="state"
                       :filters="[
                        { text: '已批改', value: 1 },
                        { text: '待批改', value: 0 },
                        ]"
                       :filter-method="filterTag"
      >
        <!-- eslint-disable-next-line -->
        <template #default="scope">
          <div class="el-icon-check" style="margin-left: 15px" v-if="scope.row.state == 1"></div>
          <div class="el-icon-minus" style="margin-left: 15px" v-else-if="scope.row.state == 0"></div>
        </template>
      </el-table-column>

      <el-table-column align="right">
        <template #header>
          <el-input @keyup.enter.native="searchResource" v-model="keywords" size="small" placeholder="搜索"></el-input>
        </template>
        <template>

          <el-button size="medium" @click="goToRead">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        hide-on-single-page
        class="fy"
        background
        layout="prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :total="filterDataShow.length">
    </el-pagination></div>
</template>

<script>

import axios from "axios";

export default {
  // components: {
  //   readReport
  // },
  data() {
    return {
      total: 0,
      pageSize: 10,
      page: 1,
      currentPage: 1,
      tableData:[],
      keywords: '',
      filterDataShow: [],
    }
  },

  computed: {
    //showTable计算属性通过slice方法计算表格当前应显示的数据
    showTable() {
      return this.filterDataShow.slice(
          (this.currentPage - 1) * this.pageSize,
          this.currentPage * this.pageSize
      );
    }
  },

  mounted() {
    this.searchResource(); //在页面挂载后调用此方法，确保数据与页面发生了交互
  },
  watch: { //监听搜索框内容，当搜索框内容发生变化时调用searchResource方法
    keywords: {
      handler() {
        this.searchResource()
      }
    }
  },
  methods: {
    goToRead(){
      this.$router.push({
        name:"readReport"
      })
    },
    searchResource() {
      this.currentPage = 1; //将当前页设置为1，确保每次都是从第一页开始搜
      let filterKeywords = this.keywords.trim();
      let filterResource = this.tableData.filter(item => { //过滤全部数据
        if (item.name.includes(filterKeywords)||item.stuNum.includes(filterKeywords)
            ||item.experimentName.includes(filterKeywords)) {
          return item
        }
      })
      this.filterDataShow = filterResource; //将符合条件的内容赋给filterDataShow
    },
    filterTag(value, row) {
      return row.state === value
    },
    handleCurrentChange(val) {
      this.page=val;
      this.currentPage=val;
    },
    getReports()
    {
      let that = this;
      axios.post("//localhost:8080/classes/add?week=" + that.addForm.week + "&starttime=" + that.addForm.starttime + "&endtime=" + that.addForm.endtime + "&room=" + that.addForm.room + "&courseid=" + that.addForm.courseid+ "&teacherid=" + that.addForm.teacherid+"&reportrate="+that.addForm.reportrate+"&attendrate="+that.addForm.attendrate+"&cprate="+that.addForm.cprate).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        this.getClassList();
        console.log(res);
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加班级成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
  },
}
</script>
<style>
.fy{
  text-align:center;
  margin-top:30px;
}
</style>