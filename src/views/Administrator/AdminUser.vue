<template>
  <div>
    <!--        面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/AdminHome' }">管理员首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-divider></el-divider>
    <!--        卡片-->
    <el-card class="box-card">
      <!--  添加-->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="showAddUser">添加用户</el-button>
        </el-col>
      </el-row>
      <!--            学生列表 只展示一些学生信息,详细文本可在详情查看-->
      <el-table :data="UserList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="角色" prop="roleId"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="显示详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--                        修改按钮-->
            <!--            <el-button type="primary" @click="showEditDialog(scope.row.AnnouncementId)"
                                   icon="el-icon-edit"></el-button>-->
            <!--                        删除按钮-->
            <el-button type="primary" @click="removeById(scope.row.userId)"
                       icon="el-icon-delete"></el-button>

          </template>
        </el-table-column>

      </el-table>
      <br>
      <!--            分页区域-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="[2, 5, 10]"
          :page-size.sync="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="examineSearchData.length">
      </el-pagination>
    </el-card>

    <!--        展示系统公告对话框-->
    <el-dialog ref="showFormRef" :visible.sync="showDialogVisible"
               width="50%">
      <!--            展示内容主体区域 -->
      <div slot="title">
        <h1>
          {{ addForm.name }}
        </h1>
        <div>
          {{ addForm.password }}
        </div>
      </div>
      {{ addForm.email }}
      <!--            底部区域-->
      <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
    </el-dialog>


    <!--        添加用户对话框-->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible"
               width="50%" center>
      <!--            内容主体区域 放置一个表单-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px">
        <el-form-item label="用户姓名:" prop="name">
          <el-input v-model="addForm.name" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="用户密码:" prop="password">
          <el-input v-model="addForm.password" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="用户角色:" prop="roleId">
          <el-input v-model="addForm.roleid" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="用户邮箱:" prop="email">
          <el-input v-model="addForm.email" style="width: 82%;"></el-input>
        </el-form-item>
      </el-form>
      <!--            底部区域-->
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancelAdd" style="margin-right: 20px;">取 消</el-button>
    <el-button type="primary" @click="addUser">确 定</el-button>
  </span>
    </el-dialog>

    <!--    &lt;!&ndash;        修改公告对话框&ndash;&gt;
        <el-dialog title="修改公告" :visible.sync="editDialogVisible"
                   width="50%" center>
          <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px">
            <el-form-item label="公告标题:" prop="title">
              <el-input v-model="addForm.title" style="width: 82%;"></el-input>
            </el-form-item>
            <el-form-item label="公告内容:" prop="content">
              <el-input
                  type="textarea"
                  :rows="7"
                  v-model="addForm.content" style="width: 82%;">
              </el-input>
            </el-form-item>
          </el-form>

          <span slot="footer" class="dialog-footer">
                        <el-button @click="cancelEdit" style="margin-right: 20px;">取 消</el-button>
                        <el-button type="primary" @click="editAnnouncement">确 定</el-button>
                </span>
        </el-dialog>-->
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      //获取公告列表参数对象
      query: '',
      //当前的页码
      pageNumber: 1,
      //每页显示的条数
      currentPage:1,
      pageSize: 10,

      //查询到的当页公告
      UserList: [],
      //总条数,用于分页的显示
      totalCount: 0,
      //添加,修改,展示公告对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      //添加公告表单数据
      addForm: {
        name:"",
        password: "",
        roleid: "",
        email:"",
      },
      //添加公告的校验规则
      addFormRules: {}
    }
  },
  //一开始就显示公告列表
  created() {
    this.getUserList();
  },
  computed:{
    examineSearchData(){
      return (this.UserList.filter(data => !this.search ||
          data.userId.toLowerCase().includes(this.search.toLowerCase())))
    },
    examineCurData:function(){
      return this.examineSearchData.slice((this.currentPage - 1) * this.pageSize,
          Math.min(this.currentPage * this.pageSize, this.examineSearchData.length));
    }
  },
  methods: {
    async getUserList() {
      let that = this;
      axios.get(`//localhost:8080/users`).then((res) => {

          console.log(res);
          that.UserList=res.data.data;
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getUserList();
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage) {
      this.pageNumber = newPage;
      this.getUserList();
    },

    showAddUser() {

      this.addDialogVisible = true;
      //清空表单的校验项
      this.$nextTick(() => {
        this.$refs.addFormRef.resetFields();
      });

      this.addForm.name="";
      this.addForm.password = "";
      this.addForm.roleid="";
      this.addForm.email = "";
    },

    //点击确定按钮后,添加公告
    addUser() {
      let that = this;
      axios.post("//localhost:8080/users/register?name=" + that.addForm.name + "&password=" + that.addForm.password + "&roleid="+that.addForm.roleid +"&email=" + that.addForm.email ).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        this.getUserList();
        console.log(res)
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加用户成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //添加公告框里面的取消添加公告按钮触发的事件
    cancelAdd() {
      //隐藏添加公告对话框
      this.addDialogVisible = false;
      this.$message.info("取消添加用户!");
    },

    async showDialog(data){
      console.log(data);
      this.addForm = data;
      this.showDialogVisible = true;
    },
    //显示公告详情页面按确定后的触发事件
    closeDialogVisible() {
      this.showDialogVisible = false;
    },
    cancelEdit() {
      this.editDialogVisible = false;
      this.$message.info("取消修改!");
    },
    //修改公告页面弹出后,会查询要修改的id所对应公告的内容
    /*async showEditDialog(AnnouncementId) {
      let result = await this.$http.post(this.$api.AdminGetOneAnnouncement + "/" + AnnouncementId);
      this.addForm = result.data;
      this.editDialogVisible = true;
    },*/
    //根据ID删除对应信息
    async removeById(userId) {
      console.log(userId);
      //    弹框提示
      let confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      //    如果选择不删除
      if (confirmResult !== "confirm") {
        return this.$message.info("已经取消删除");
      }else {
        await axios.delete("//localhost:8080/users" + "/" + userId).then((res)=>{
          console.log(res)
        });
        this.$message.info("删除成功!");
        await this.getUserList();
      }
    }
  }
}
</script>

<style scoped></style>

