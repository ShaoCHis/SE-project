<template>
  <div>
    <!--        面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/TeacherHome' }">教师首页</el-breadcrumb-item>
      <el-breadcrumb-item>学生成绩查询</el-breadcrumb-item>
    </el-breadcrumb>

    <el-divider></el-divider>
    <!--        卡片-->
    <el-card class="box-card">
      <!--  添加-->
      <el-row :gutter="20">
        <el-input  placeholder="请输入班级id" v-model="classid">
          <el-button slot="append" icon="el-icon-search" @click="getGradeList"></el-button>
        </el-input>
      </el-row>
      <!--            公告列表 只展示一些公告信息,详细文本可在详情查看-->
      <el-table :data="examineCurData">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="学生姓名" prop="name"></el-table-column>
        <el-table-column label="成绩" prop="grade"></el-table-column>
        <el-table-column label="查看成绩详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row.studentID)">查看</el-button>
          </template>
        </el-table-column>

      </el-table>
      <br>

      <!--        展示成绩对话框-->
      <el-dialog title="成绩详情" :visible.sync="showDialogVisible"
                 width="630px" top="60px" center>
        <!--            展示内容主体区域 -->
        <el-form :model="showForm" label-width="150px" style="height:380px">
          <el-form-item label="学生姓名:" prop="name">
            <el-input style="width: 82%;" v-model="showForm.name"></el-input>
          </el-form-item>
          <el-form-item label="课程名称:" prop="className">
            <el-input style="width: 82%;" v-model="showForm.className"></el-input>
          </el-form-item>
          <el-form-item label="总成绩:" prop="grade">
            <el-input style="width: 82%;" v-model="showForm.grade"></el-input>
          </el-form-item>
          <el-form-item label="报告占比:" prop="reportRate">
            <el-input style="width: 82%;" v-model="showForm.reportRate"></el-input>
          </el-form-item>
          <el-form-item label="考勤占比:" prop="attendRate">
            <el-input style="width: 82%;" v-model="showForm.attendRate"></el-input>
          </el-form-item>
          <el-form-item label="报告得分:" prop="reportScore">
            <el-input style="width: 82%;" v-model="showForm.reportScore"></el-input>
          </el-form-item>
          <el-form-item label="考勤得分:" prop="attendScore">
            <el-input style="width: 82%;" v-model="showForm.attendScore"></el-input>
          </el-form-item>

        </el-form>
        <!--                底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
      </el-dialog>

      <!--            分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="[2, 5]"
          :page-size.sync="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="examineSearchData.length">
      </el-pagination>
    </el-card>

  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      //获取公告列表参数对象
      query: '',
      pageNumber: 1,
      //每页显示的条数
      currentPage:1,
      pageSize: 5,

      //查询到的当页成绩
      GradeList: [],
      //总条数,用于分页的显示
      totalCount: 0,
      //添加,修改,展示公告对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      classid:"",

      showForm: {
        /*name:"",
        className:"",
        grade:"",
        reportRate:"",
        attendRate:"",
        reportScore:"",
        attendScore:"",*/
      },
    }
  },
  computed:{
    examineSearchData(){
      return (this.GradeList.filter(data => !this.search ||
          data.studentId.toLowerCase().includes(this.search.toLowerCase())))
    },
    examineCurData:function(){
      return this.examineSearchData.slice((this.currentPage - 1) * this.pageSize,
          Math.min(this.currentPage * this.pageSize, this.examineSearchData.length));
    }
  },
  methods: {
    async getGradeList() {
      let that = this;
      axios.get(`//localhost:8080/grades/studentgrade/`+that.classid).then((res) => {
        console.log(res)
        if (res.data.success == true) {
          console.log(res)
          that.GradeList=res.data.data
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    showDialog(studentID)
    {

      let that = this;
      axios.get(`//localhost:8080/grades/new?studentid=` +studentID+"&classid="+that.classid).then((res) => {
        console.log(res)
        if (res.data.success == true) {
          console.log(res)
          that.showForm=res.data.data
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
      this.showDialogVisible = true;
    },
    //监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getGradeList();
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage) {
      this.pageNumber = newPage;
      this.getGradeList();
    },
    closeDialogVisible()
    {
      this.showDialogVisible = false;
    },
  }
}
</script>

<style scoped></style>

