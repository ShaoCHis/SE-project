<template>
  <div>
    <!--        面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/TeacherHome' }">教师首页</el-breadcrumb-item>
      <el-breadcrumb-item>课程管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-divider></el-divider>
    <!--        卡片-->
    <el-card class="box-card">
      <!--        添加-->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="showAddCourse">添加课程</el-button>
        </el-col>
      </el-row>
      <!--            课程列表 只展示一些信息,详细信息可在详情查看-->
      <el-table :data="courseList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="课程名称" prop="name"></el-table-column>
        <el-table-column label="课程简介" prop="intro"></el-table-column>
        <el-table-column label="显示详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--                        修改按钮-->
            <!--            <el-button type="primary" @click="showEditDialog(scope.row.courseId)"
                                   icon="el-icon-edit"></el-button>-->
            <!--                        删除按钮-->
            <el-button type="primary" @click="removeById(scope.row.course_id)"
                       icon="el-icon-delete"></el-button>

          </template>
        </el-table-column>
      </el-table>

      <!--        添加课程对话框-->
      <el-dialog title="添加课程" :visible.sync="addDialogVisible"
                 width="630px" top="60px" center>
        <!--            内容主体区域 放置一个表单-->
        <!--绑定到addForm中，绑定验证规则对象addFormRules 表单校验项的引用为addFormRef-->
        <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px"
                 style="height:385px">
          <!-- prop属性指定验证规则-->
          <el-form-item label="课程名称:" prop="name">
            <!--v-model双向绑定-->
            <el-input style="width: 82%;" v-model="addForm.coursename"></el-input>
          </el-form-item>
          <el-form-item label="课程介绍:" prop="intro">
            <el-input style="width: 82%;" type="textarea"
                      :autosize="{ minRows: 3, maxRows: 4}" v-model="addForm.intro"></el-input>
          </el-form-item>
        </el-form>
        <!--            底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button style="margin-right:20px" @click="cancelAdd">取 消</el-button>
                <el-button style="margin-left:20px" type="primary" @click="addCourse">确 定</el-button>
            </span>
      </el-dialog>

      <!--        展示课程对话框-->
      <el-dialog title="课程详情" :visible.sync="showDialogVisible"
                 width="630px" top="60px" center>
        <!--            展示内容主体区域 -->
        <el-form :model="showForm" label-width="150px" style="height:580px">
          <el-form-item label="课程名称:">
            <el-input style="width: 82%;" v-model="showForm.name" readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="课程介绍:">
            <el-input type="textarea"
                      :autosize="{ minRows: 3, maxRows: 4}" style="width: 82%;"
                      v-model="showForm.intro"
                      readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="课程id:">
            <el-input style="width: 82%;" v-model="showForm.course_id" readonly="true"></el-input>
          </el-form-item>


        </el-form>
        <!--                底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
      </el-dialog>

      <!--        修改课程对话框-->
      <!--      <el-dialog title="修改课程" :visible.sync="editDialogVisible"
                       width="630px" top="60px" center>
              <el-form :model="editForm" :rules="addFormRules" ref="editFormRef" label-width="150px" style="height:380px">
                <el-form-item label="课程名称:" prop="name">
                  <el-input style="width: 82%;" v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="课程介绍:" prop="description">
                  <el-input style="width: 82%;" type="textarea"
                            :autosize="{ minRows: 3, maxRows: 4}" v-model="editForm.description"></el-input>
                </el-form-item>
                <el-form-item label="责任教师:" prop="teacher">
                  <el-input style="width: 82%;" v-model="editForm.teacher"></el-input>
                </el-form-item>
              </el-form>

              <span slot="footer" class="dialog-footer">
                          <el-button style="margin-right:20px" @click="cancelEdit">取 消</el-button>
                          <el-button style="margin-left:20px" type="primary" @click="editCourse">确 定</el-button>
                  </span>
            </el-dialog>-->
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
      courseList: [],

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

      //添加活动表单数据
      addForm: {
        coursename: "",
        intro: "",
        administratorid: ""
      },
      showForm: {},
      editForm: {},
      //添加活动的校验规则
      addFormRules: {
        coursename: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
          {min: 2, max: 10, message: '课程名称必须在2-10字符之间', trigger: 'blur'}
        ],
        intro: [
          {required: true, message: '请输入课程描述', trigger: 'blur'}
        ]
      }
    };
  },
  //一开始就显示活动列表
  created()
  {
    this.getCourseList();
  },
  methods: {
    //获取活动列表
    async getCourseList()
    {
      let that = this;
      axios.get(`//localhost:8080/courses`).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          that.courseList=res.data.data
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
    showAddCourse()
    {
      this.addDialogVisible = true;
      //清空表单的校验项
      this.$nextTick(() =>
      {
        this.$refs.addFormRef.resetFields();
      });

      this.addForm.coursename = "";
      this.addForm.intro = "";
    },
    //点击确定按钮后,添加课程
    addCourse()
    {
      let that = this;
      axios.post("//localhost:8080/courses/add?coursename=" + that.addForm.coursename + "&intro=" + that.addForm.intro + "&administratorid=" + 2 ).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        this.getCourseList();
        console.log(res);
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加成功!");
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
      this.$message.info("取消添加课程!");
    },

    //根据活动ID,展示活动具体信息
    async showDialog(data)
    {
      console.log(data);
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
      this.$message.info("取消修改课程!");
    },
    //修改活动页面弹出后,会查询要修改的id所对应活动的内容
    /*async showEditDialog(courseId)
    {
      let result = await this.$http.post(this.$api.PrincipalGetOneActivityUrl + "/" + courseId);
      this.editForm = result.data;
      this.editDialogVisible = true;
    },*/
    //实现具体的修改活动操作
    /*async editCourse()
    {
      this.$refs.editFormRef.validate(
          async valid =>
          {
            if (!valid) return;
            console.log(this.editForm);
            await this.$http.post(this.$api.PrincipalUpdateOneActivityUrl + "/" + this.editForm.courseId, this.editForm);
            //关闭对话框
            this.editDialogVisible = false;
            //    刷新数据列表
            this.getCourseList();
            //    提示成功
            this.$message.success("更新课程成功!");
          }
      );
    },*/
    //根据ID删除对应信息
    async removeById(course_id)
    {
      //    弹框提示
      let confirmResult = await this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
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
        await axios.delete("//localhost:8080/courses" + "/" + course_id).then((res)=>{
          console.log(res)
        });
        this.$message.info("删除成功!");
        this.getCourseList();
      }
    }
  }
}
</script>

<style scoped>
</style>

