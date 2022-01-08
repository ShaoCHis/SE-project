<template>
  <div>
    <!--        面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/AdminHome' }">管理员首页</el-breadcrumb-item>
      <el-breadcrumb-item>班级管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-divider></el-divider>
    <!--        卡片-->
    <el-card class="box-card">
      <!--            搜索与添加-->
      <el-row :gutter="20">
        <el-col :span="7">
          <!--                    搜索取消时也会刷新搜索页面,搜索确定时,将携带query搜索特定内容的活动-->
          <el-input clearable @clear="getClassList" placeholder="请输入内容" v-model="query">
            <el-button slot="append" icon="el-icon-search" @click="getClassList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="showAddClass">创建班级</el-button>
        </el-col>
      </el-row>
      <!--            课程列表 只展示一些信息,详细信息可在详情查看-->
      <el-table :data="classList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="班级ID" prop="classId"></el-table-column>
        <el-table-column label="班级名称" prop="name"></el-table-column>
        <el-table-column label="教师ID" prop="teacher_id"></el-table-column>
        <el-table-column label="显示详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row.classId)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--                        增加学生按钮-->
            <el-button type="primary" @click="addStudent(scope.row.classId)"
                       icon="el-icon-edit"></el-button>
            <!--                        修改按钮-->
            <el-button type="primary" @click="showEditDialog(scope.row.classId)"
                       icon="el-icon-edit"></el-button>
            <!--                        删除按钮-->
            <el-button type="primary" @click="removeById(scope.row.classId)"
                       icon="el-icon-delete"></el-button>

          </template>
        </el-table-column>
      </el-table>

      <!--        添加课程对话框-->
      <el-dialog title="创建班级" :visible.sync="addDialogVisible"
                 width="630px" top="60px" center>
        <!--            内容主体区域 放置一个表单-->
        <!--绑定到addForm中，绑定验证规则对象addFormRules 表单校验项的引用为addFormRef-->
        <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px"
                 style="height:385px">
          <!-- prop属性指定验证规则-->
          <el-form-item label="班级名称:" prop="name">
            <!--v-model双向绑定-->
            <el-input style="width: 82%;" v-model="addForm.name"></el-input>
          </el-form-item>
          <el-form-item label="班级介绍:" prop="description">
            <el-input style="width: 82%;" type="textarea"
                      :autosize="{ minRows: 3, maxRows: 4}" v-model="addForm.description"></el-input>
          </el-form-item>
          <el-form-item label="任课教师:" prop="teacher">
            <el-input style="width: 82%;" v-model="addForm.teacher"></el-input>
          </el-form-item>
        </el-form>
        <!--            底部区域-->
        <span slot="footer" class="dialog-footer">
                <el-button style="margin-right:20px" @click="cancelAdd">取 消</el-button>
                <el-button style="margin-left:20px" type="primary" @click="addClass">确 定</el-button>
            </span>
      </el-dialog>

      <!--        展示课程对话框-->
      <el-dialog title="活动详情" :visible.sync="showDialogVisible"
                 width="630px" top="60px" center>
        <!--            展示内容主体区域 -->
        <el-form :model="showForm" label-width="150px" style="height:580px">
          <el-form-item label="班级名称:">
            <el-input style="width: 82%;" v-model="showForm.name" readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="班级介绍:">
            <el-input type="textarea"
                      :autosize="{ minRows: 3, maxRows: 4}" style="width: 82%;"
                      v-model="showForm.description"
                      readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="任课教师:">
            <el-input style="width: 82%;" v-model="showForm.teacher" readonly="true"></el-input>
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
          <el-form-item label="班级名称:" prop="name">
            <el-input style="width: 82%;" v-model="studentForm.name"></el-input>
          </el-form-item>
          <el-form-item label="学生ID:" prop="studentID">
            <el-input style="width: 82%;" v-model="studentForm.studentID"></el-input>
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
        name: "",
        description: "",
        teacher: ""
      },
      showForm: {},
      editForm: {},
      studentForm:{
        name:"",
        studentId: ""
      },
      //添加活动的校验规则
      addFormRules: {
        name: [
          {required: true, message: '请输入班级名称', trigger: 'blur'},
          {min: 2, max: 10, message: '课程名称必须在2-10字符之间', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入班级描述', trigger: 'blur'}
        ],
        teacher: [
          {required: true, message: '请输入任课教师', trigger: 'blur'}]
      }
    };
  },
  //一开始就显示活动列表
  created()
  {
    this.getClassList();
  },
  methods: {
    //获取活动列表
    async getClassList()
    {
      let result = await this.$http.post(this.$api.PrincipalGetActivitiesUrl,
          {
            query: this.query,
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            status: true
          });

      this.classList = result.data.data;

      console.log(this.classList);
      this.totalCount = parseInt(result.data.totalCount);
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

      this.addForm.name = "";
      this.addForm.description = "";
      this.addForm.teacher = "";
    },
    //点击确定按钮后,添加活动
    addClass()
    {
      this.$refs.addFormRef.validate(
          async valid =>
          {
            if (!valid) return;
            // let result = await this.$http.post(this.$api.PrincipalAddOneActivityUrl,
            //     {
            //       activityId: 0,
            //       name: this.addForm.name,
            //       teacher: this.addForm.teacher,
            //       description: this.addForm.description
            //     });

            //隐藏添加活动对话框
            this.addDialogVisible = false;
            await this.getClassList();
            this.$message.info("添加班级成功!");
          }
      );
    },
    //添加活动框里面的取消添加活动按钮触发的事件
    cancelAdd()
    {
      //隐藏添加活动对话框
      this.addDialogVisible = false;
      this.$message.info("取消添加班级!");
    },

    //根据班级ID,展示活动具体信息
    async showDialog(classId)
    {
      let result = await this.$http.post(this.$api.PrincipalGetOneActivityUrl + "/" + classId);
      this.showForm = result.data;
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
      this.$message.info("取消修改班级!");
    },
    //修改活动页面弹出后,会查询要修改的id所对应活动的内容
    async showEditDialog(classId)
    {
      let result = await this.$http.post(this.$api.PrincipalGetOneActivityUrl + "/" + classId);
      this.editForm = result.data;
      this.editDialogVisible = true;
    },
    // async addStudent()
    // {
    //   let that = this;
    //   if(that.isPresent==true)
    //   {
    //     axios.post(`//139.224.65.154:8080/takeclass/add?classid=` + that.classId +"&studentid="+that.studentId).then((res) => {
    //       if (res.data.success == true) {
    //         this.$message.success("添加成功！")
    //       }
    //       this.dialogVisible = false
    //     }).catch((res) => {
    //       console.log(res);
    //       that.$message.error("Time out!Please try again!");
    //     })
    //   }
    // },
    //实现具体的修改活动操作
    async editClass()
    {
      this.$refs.editFormRef.validate(
          async valid =>
          {
            if (!valid) return;
            console.log(this.editForm);
            await this.$http.post(this.$api.PrincipalUpdateOneActivityUrl + "/" + this.editForm.classId, this.editForm);
            //关闭对话框
            this.editDialogVisible = false;
            //    刷新数据列表
            this.getClassList();
            //    提示成功
            this.$message.success("更新班级成功!");
          }
      );
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
        await this.$http.post(this.$api.PrincipalDeleteOneActivityUrl + "/" + classId);
        this.$message.info("删除成功!");
        this.getClassList();
      }
    }
  }
}
</script>

<style scoped>
</style>

