<template>
  <div>
    <!--        面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/TeacherHome' }">教师首页</el-breadcrumb-item>
      <el-breadcrumb-item>班级管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-divider></el-divider>
    <!--        卡片-->
    <el-card class="box-card">
      <!--            搜索与添加-->
      <!--            class列表 只展示一些信息,详细信息可在详情查看-->
      <el-table :data="examineCurData">
        <el-table-column type="index" ></el-table-column>
        <el-table-column label="班级id" prop="classId"></el-table-column>
        <el-table-column label="班级教室" prop="room"></el-table-column>
        <el-table-column label="上课周数" prop="week"></el-table-column>
        <el-table-column label="显示详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row)" size="small">查看详情</el-button>
          </template>
        </el-table-column>
          <el-table-column label="查看所属课程">
            <template slot-scope="scope">
              <el-button type="primary" @click="showCourse(scope.row.classId)" size="small">查看所属课程</el-button>
            </template>
        </el-table-column>
        <el-table-column label="查看实验">
          <template slot-scope="scope">
            <el-button type="primary" @click="showReport(scope.row.classId)" size="small">查看实验</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--        展示class对话框-->
      <el-dialog title="班级详情" :visible.sync="showDialogVisible"
                 width="630px" top="60px" center>
        <!--            展示内容主体区域 -->
        <el-form :model="showForm" label-width="150px" style="height:510px">
          <el-form-item label="上课周数:" prop="week">
            <el-input style="width: 82%;" v-model="showForm.week" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="开始时间:" prop="startTime">
            <!--v-model双向绑定-->
            <el-input style="width: 82%;" v-model="showForm.startTime" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="结束时间:" prop="endTime">
            <el-input style="width: 82%;" v-model="showForm.endTime" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="上课教室:" prop="room">
            <el-input style="width: 82%;" v-model="showForm.room" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="报告占比:" prop="reportRate">
            <el-input style="width: 82%;" v-model="showForm.reportRate" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="考勤占比:" prop="attendRate">
            <el-input style="width: 82%;" v-model="showForm.attendRate" :disabled="true"></el-input>
          </el-form-item>

        </el-form>
        <!--                底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
      </el-dialog>

      <el-dialog title="课程详情" :visible.sync="showVisible"
                 width="630px" top="60px" center>
        <!--            展示内容主体区域 -->
        <el-form :model="showForm" label-width="150px" style="height:380px">
          <el-form-item label="课程名称:">
            <el-input style="width: 82%;" v-model="showForm.name" readonly="true" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="课程介绍:">
            <el-input type="textarea"
                      :autosize="{ minRows: 3, maxRows: 4}" style="width: 82%;"
                      v-model="showForm.intro"
                      readonly="true" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="课程id:">
            <el-input style="width: 82%;" v-model="showForm.course_id" readonly="true" :disabled="true"></el-input>
          </el-form-item>


        </el-form>
        <!--                底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
      </el-dialog>


      <br>
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
  data()
  {
    return {
      //获取活动列表参数对象
      //查询到的当页活动
      classList: [],

      courseList:[],

      query: '',
      //当前的页码
      pageNumber: 1,
      //每页显示的条数
      currentPage:1,
      pageSize: 5,

      //添加,修改,展示活动对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      showVisible:false,

      showForm: {},
      editForm: {},
    };
  },
  //一开始就显示活动列表
  created()
  {
    this.id=this.$route.params.id;
    this.userId=this.$route.params.userId;
    console.log(this.id);
    console.log(this.userId);
    this.getClassList();
  },
  computed:{
    examineSearchData(){
      return (this.classList.filter(data => !this.search ||
          data.classId.toLowerCase().includes(this.search.toLowerCase())))
    },
    examineCurData:function(){
      return this.examineSearchData.slice((this.currentPage - 1) * this.pageSize,
          Math.min(this.currentPage * this.pageSize, this.examineSearchData.length));
    }
  },
  methods: {
    //获取列表
    async getClassList() {
      let that = this;
      axios.get(`//localhost:8080/reports/getclassesofyourself/`+ this.id).then((res) => {
        console.log(res)
        if (res.data.success == true) {
          console.log(res)
          that.classList=res.data.data
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //监听pageSize改变的事件
    handleSizeChange(newSize)
    {
      this.pageSize = newSize;
      this.getClassList();
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage)
    {
      this.pageNumber = newPage;
      this.getClassList();
    },
    //添加活动
    showAddClass()
    {
      this.addDialogVisible = true;
      //清空表单的校验项
      this.$nextTick(() =>
      {
        this.$refs.addFormRef.resetFields();
      });
    },
    //添加活动框里面的取消添加活动按钮触发的事件
    cancelAdd()
    {
      //隐藏添加活动对话框
      this.addDialogVisible = false;
      this.$message.info("取消添加班级!");
    },

    //根据班级ID,展示活动具体信息
    async showDialog(data)
    {
      this.showForm = data;
      this.showDialogVisible = true;
    },
    //显示活动详情页面按确定后的触发事件
    closeDialogVisible()
    {
      this.showDialogVisible = false;
      this.showVisible=false;
    },
    showCourse(classId)
    {
      this.showVisible=true;
      let that = this;
      axios.get(`//localhost:8080/classes/getcourse/`+classId).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          that.showForm=res.data.data;
          that.courseList=res.data.data;
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    showReport(classId)
    {
      console.log(classId);
       console.log(this.courseList.course_id);
      this.$router.push({
        name: "classExperiment",
        params: {
          classid: classId,
          courseid:this.courseList.course_id,
        }
      })
    }
  }
}
</script>

<style scoped>
</style>

