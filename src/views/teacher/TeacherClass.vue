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
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="showAddClass">创建班级</el-button>
        </el-col>
      </el-row>
      <!--            class列表 只展示一些信息,详细信息可在详情查看-->
      <el-table :data="classList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="班级id" prop="classId"></el-table-column>
        <el-table-column label="班级教室" prop="room"></el-table-column>
        <el-table-column label="上课周数" prop="week"></el-table-column>
        <el-table-column label="显示详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--                        增加学生按钮-->
            <el-button type="primary" @click="showAddStudent(scope.row.classId)"
                       icon="el-icon-edit"></el-button>
            <!--                        删除按钮-->
            <el-button type="primary" @click="removeById(scope.row.classId)"
                       icon="el-icon-delete"></el-button>

          </template>
        </el-table-column>
      </el-table>

      <!--        添加class对话框-->
      <el-dialog title="创建班级" :visible.sync="addDialogVisible"
                 width="630px" top="60px" center>
        <!--            内容主体区域 放置一个表单-->
        <!--绑定到addForm中，绑定验证规则对象addFormRules 表单校验项的引用为addFormRef-->
        <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px"
                 style="height:385px">
          <!-- prop属性指定验证规则-->
          <el-form-item label="上课周数:" prop="week">
            <el-input style="width: 82%;" v-model="addForm.week"></el-input>
          </el-form-item>
          <el-form-item label="开始时间:" prop="starttime">
            <!--v-model双向绑定-->
            <el-input style="width: 82%;" v-model="addForm.starttime"></el-input>
          </el-form-item>
          <el-form-item label="结束时间:" prop="endtime">
            <el-input style="width: 82%;" v-model="addForm.endtime"></el-input>
          </el-form-item>
          <el-form-item label="上课教室:" prop="room">
            <el-input style="width: 82%;" v-model="addForm.room"></el-input>
          </el-form-item>
          <el-form-item label="课程id:" prop="courseid">
            <el-input style="width: 82%;" v-model="addForm.courseid"></el-input>
          </el-form-item>
          <el-form-item label="教师id:" prop="teacherid">
            <el-input style="width: 82%;" v-model="addForm.teacherid"></el-input>
          </el-form-item>
          <el-form-item label="报告占比:" prop="reportrate">
            <el-input style="width: 82%;" v-model="addForm.reportrate"></el-input>
          </el-form-item>
          <el-form-item label="考勤占比:" prop="attendrate">
            <el-input style="width: 82%;" v-model="addForm.attendrate"></el-input>
          </el-form-item>
          <el-form-item label="对抗练习占比:" prop="cprate">
            <el-input style="width: 82%;" v-model="addForm.cprate"></el-input>
          </el-form-item>
        </el-form>
        <!--            底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button style="margin-right:20px" @click="cancelAdd">取 消</el-button>
                <el-button style="margin-left:20px" type="primary" @click="addClass">确 定</el-button>
            </span>
      </el-dialog>

      <!--        展示class对话框-->
      <el-dialog title="班级详情" :visible.sync="showDialogVisible"
                 width="630px" top="60px" center>
        <!--            展示内容主体区域 -->
        <el-form :model="showForm" label-width="150px" style="height:580px">
          <el-form-item label="上课周数:" prop="week">
            <el-input style="width: 82%;" v-model="showForm.week"></el-input>
          </el-form-item>
          <el-form-item label="开始时间:" prop="startTime">
            <!--v-model双向绑定-->
            <el-input style="width: 82%;" v-model="showForm.startTime"></el-input>
          </el-form-item>
          <el-form-item label="结束时间:" prop="endTime">
            <el-input style="width: 82%;" v-model="showForm.endTime"></el-input>
          </el-form-item>
          <el-form-item label="上课教室:" prop="room">
            <el-input style="width: 82%;" v-model="showForm.room"></el-input>
          </el-form-item>
          <el-form-item label="报告占比:" prop="reportRate">
            <el-input style="width: 82%;" v-model="showForm.reportRate"></el-input>
          </el-form-item>
          <el-form-item label="考勤占比:" prop="attendRate">
            <el-input style="width: 82%;" v-model="showForm.attendRate"></el-input>
          </el-form-item>

        </el-form>
        <!--                底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
      </el-dialog>

      <!--        添加学生对话框-->
      <el-dialog title="学生添加" :visible.sync="addStudent"
                 width="630px" top="60px" center>
        <el-form :model="studentForm"  ref="editFormRef" label-width="150px" style="height:380px">
          <el-form-item label="班级id:" prop="classid">
            <el-input style="width: 82%;" v-model="studentForm.classid"></el-input>
          </el-form-item>
          <el-form-item label="学生ID:" prop="studentid">
            <el-input style="width: 82%;" v-model="studentForm.studentid"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
                    <el-button style="margin-right:20px" @click="cancelEdit">取 消</el-button>
                    <el-button style="margin-left:20px" type="primary" @click="editClass">确 定</el-button>
            </span>
      </el-dialog>
      <!--        修改班级对话框-->
      <el-dialog title="班级更改" :visible.sync="editDialogVisible"
                 width="630px" top="60px" center>
        <el-form :model="editForm" :rules="addFormRules" ref="editFormRef" label-width="150px" style="height:380px">
          <el-form-item label="班级名称:" prop="name">
            <el-input style="width: 82%;" v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="班级介绍:" prop="description">
            <el-input style="width: 82%;" type="textarea"
                      :autosize="{ minRows: 3, maxRows: 4}" v-model="editForm.description"></el-input>
          </el-form-item>
          <el-form-item label="任课教师:" prop="teacher">
            <el-input style="width: 82%;" v-model="editForm.teacher"></el-input>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
                    <el-button style="margin-right:20px" @click="cancelEdit">取 消</el-button>
                    <el-button style="margin-left:20px" type="primary" @click="editClass">确 定</el-button>
            </span>
      </el-dialog>
      <br>
      <!--            分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNumber"
          :page-sizes="[1, 2, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalCount">
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

      query: '',
      //当前的页码
      pageNumber: 1,
      //每页显示的条数
      pageSize: 5,
      //总条数,用于分页的显示
      totalCount: 0,

      //添加,修改,展示活动对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,
      addStudent: false,


      //添加活动表单数据
      addForm: {
        week:"",
        starttime: "",
        endtime: "",
        room: "",
        courseid:"",
        teacherid:"",
        reportrate:"",
        attendrate:"",
        cprate:"",
      },
      showForm: {},
      editForm: {},
      studentForm:{
        classid:"",
        studentid: ""
      },
      //添加活动的校验规则
      addFormRules: {
      }
    };
  },
  //一开始就显示活动列表
  created()
  {
    this.getClassList();
  },
  methods: {
    //获取列表
    async getClassList() {
      let that = this;
      axios.get(`//139.224.65.154:8080/classes`).then((res) => {
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
      this.getCourseList();
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage)
    {
      this.pageNumber = newPage;
      this.getCourseList();
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

      this.addForm.week = "";
      this.addForm.starttime="";
      this.addForm.endtime="";
      this.addForm.room="";
      this.addForm.courseid="";
      this.addForm.teacherid="";
      this.addForm.reportrate="";
      this.addForm.attendrate="";
      this.addForm.cprate="";
    },
    //点击确定按钮后,添加class
    addClass()
    {
      let that = this;
      axios.post("//139.224.65.154:8080/classes/add?week=" + that.addForm.week + "&starttime=" + that.addForm.starttime + "&endtime=" + that.addForm.endtime + "&room=" + that.addForm.room + "&courseid=" + that.addForm.courseid+ "&teacherid=" + that.addForm.teacherid+"&reportrate="+that.addForm.reportrate+"&attendrate="+that.addForm.attendrate+"&cprate="+that.addForm.cprate).then((res) => {
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
    },
    //取消修改活动
    cancelEdit()
    {
      this.editDialogVisible = false;
      this.$message.info("取消添加学生班级!");
    },
    showAddStudent(classid)
    {
      this.addStudent = true;
      this.studentForm.classid=classid;
      this.studentForm.studentid="";
    },
    //添加学生
    async editClass()
    {
      let that = this;
      axios.post("//139.224.65.154:8080/takeclass/add?classid=" + that.studentForm.classid + "&studentid=" + that.addForm.studentid ).then((res) => {
        //隐藏添加公告对话框
        this.addStudent= false;
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //根据ID删除对应信息
    async removeById(classId)
    {
      //    弹框提示
      let confirmResult = await this.$confirm('此操作将永久删除该班级, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      //    如果选择不删除
      if (confirmResult !== "confirm")
      {
        return this.$message.info("已经取消删除");
      } else
      {
        await axios.delete("//139.224.65.154:8080/classes" + "/" + classId).then((res)=>{
          console.log(res)
        });
        this.$message.info("删除成功!");
        this.getClassList();
      }
    }
  }
}
</script>

<style scoped>
</style>

