<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/AdministratorHome' }">管理员首页</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-divider></el-divider>
    <!-- 卡片 -->
    <el-card class="box-card">

      <!-- 列表 -->
      <el-table :data="studentList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="学号" prop="number"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="角色" prop="role"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary"
                       @click="showReplyDialog(scope.$index)">
              修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <br>
      <el-row :gutter="24">
        <el-col :span="13">
          <!-- 分页区域 -->
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNumber"
              :page-sizes="[1, 2, 5, 10]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalCount">
          </el-pagination>
        </el-col>
        <el-col :span='2'>
          <el-button type="primary"
                     @click="showEmptyReplyDialog()">
            新增
          </el-button>
        </el-col>
        <el-col :span='0'>
          <input
              class="input-file"
              type="file"
              @change="exportData"
              accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
          />
        </el-col>
        <el-col :span="3">
                    <span>
                        <el-button type="primary" icon="el-icon-upload" @click="freshBtnClick">批量新增</el-button>
                    </span>
        </el-col>
      </el-row>

    </el-card>
    <!-- 详情对话框 -->
    <el-dialog title="学生信息" :visible.sync="replyDialogVisible"
               width="50%" center>
      <!-- 展示内容主体区域 -->
      <el-form :model="this.replyForm" label-width="150px">
        <el-form-item label="姓名:">
          <el-input v-model="replyForm.name" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="学号:">
          <el-input v-model="replyForm.number" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="邮箱:">
          <el-input v-model="replyForm.email" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="角色:">
          <el-input v-model="replyForm.role" style="width: 82%;"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
                <!-- 取消按钮 -->
                <el-button @click="closeReplyDialog()">取 消</el-button>
        <!-- 提交按钮 -->
                <el-button type="primary" @click="submit()">提 交</el-button>
        <!-- 删除按钮 -->
                <el-button
                    v-if="!replyForm.isNew"
                    type="danger"
                    @click="deleteStudentMeta()">
                    删除
                </el-button>
            </span>
    </el-dialog>
    <!-- 批量操作出错对话框 -->
    <el-dialog title="批量操作出错" :visible.sync="errorDialogVisible"
               width="50%">
      <!-- 展示内容主体区域 -->
      <el-form :model="this.errorForm" label-width="150px">
        <el-form-item label="当前操作:">
          <el-input v-model="errorForm.op" readonly></el-input>
        </el-form-item>
        <el-form-item label="错误行号:">
          <el-input v-model="errorForm.row" readonly></el-input>
        </el-form-item>
        <el-form-item label="详情:">
          <el-input v-model="errorForm.notice" readonly></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
                <!-- 确认按钮 -->
                <el-button type="danger" @click="closeErrorDialog()">确 认</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>

export default {

  data()
  {
    return {
      //获取列表参数对象
      query: '',
      status: 'all',
      //当前的页码
      pageNumber: 1,
      //每页显示的条数
      pageSize: 5,
      quanbu:"全　部",
      isFresh: true,
      //查询到的当前页活动列表
      studentList: [
      ],
      //总条数,用于分页的显示
      totalCount: 0,
      //批复对话框显示状态
      replyDialogVisible: false,
      //批量操作出错对话框显示状态
      errorDialogVisible: false,
      //分类按钮状态
      buttonUsable: [true, true, true, true],
      //活动审核表单数据
      replyForm: {
        number: 0,
        name: "",
        email: "",
        role: "",
        isNew: false
      },
      ws: {},
      errorForm:{
        op: "",
        row: 0,
        notice: "",
      }
    }
  },
  created()
  {
    this.getStudentList('all');
  },
  methods: {
    //获取query_in关键字，status状态，第pageNumber_in页的列表
    async getStudentList(status_in = this.status, query_in = this.query, pageNumber_in = this.pageNumber)
    {
      this.status = status_in;
      this.query = query_in;
      this.pageNumber = pageNumber_in;
      let result = await this.$http.post(this.$api.AdminGetStudentMetaListUrl,
          {
            status: this.status,
            PageQO:{
              query: this.query,
              pageNumber: this.pageNumber,
              pageSize: this.pageSize,
            },
          });

      while(this.studentList.length > 0){
        this.studentList.pop();
      }
      for(let i = 0; i < result.data.data.length; i++){
        // console.log(result.data.data[i]);
        let metaItem = {
          number: result.data.data[i].number,
          name: result.data.data[i].name,
          email: result.data.data[i].email,
          role: result.data.data[i].role
        };
        this.studentList.push(metaItem);
      }
      // console.log(this.studentList);
      this.totalCount = parseInt(result.data.totalCount);
    },
    //监听pageSize改变的事件
    handleSizeChange(newSize)
    {
      this.pageSize = newSize;
      this.getStudentList(this.status, this.query);
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage)
    {
      this.pageNumber = newPage;
      this.getStudentList(this.status, this.query);
    },
    //弹出详情对话框
    async showReplyDialog(pos_in)
    {
      this.replyForm.number = this.studentList[pos_in].number;
      this.replyForm.name = this.studentList[pos_in].name;
      this.replyForm.email = this.studentList[pos_in].email;
      this.replyForm.role = this.studentList[pos_in].role;
      this.replyForm.isNew = false;
      this.replyDialogVisible = true;
    },
    //弹出空白详情对话框
    async showEmptyReplyDialog()
    {
      this.replyForm.number = 0;
      this.replyForm.name = "";
      this.replyForm.email = "";
      this.replyForm.role = "";
      this.replyForm.isNew = true;
      this.replyDialogVisible = true;
    },
    //关闭详情对话框
    closeReplyDialog()
    {
      this.replyDialogVisible = false;
    },
    closeErrorDialog(){
      this.errorDialogVisible = false;
    },
    //提交更新并关闭对话框
    async submit()
    {
      this.closeReplyDialog();
      if (this.replyForm.isNew){ return this.insertStudentMeta(); }
      let result = await this.$http.post(this.$api.AdminUpdateStudentMetaUrl,
          {
            number: this.replyForm.number,
            name: this.replyForm.name,
            email: this.replyForm.email,
            role: this.replyForm.role
          });
      this.getStudentList();
      return result.data.isSuccess;
    },
    //新增学生信息
    async insertStudentMeta(){
      let result = await this.$http.post(this.$api.AdminInsertStudentMetaUrl,
          {
            number: this.replyForm.number,
            name: this.replyForm.name,
            email: this.replyForm.email,
            role: this.replyForm.role
          });
      this.getStudentList();
      return result.data.isSuccess;
    },
    //删除学生信息
    async deleteStudentMeta(){
      console.log({
        number: this.replyForm.number
      });
      let result = await this.$http.post(this.$api.AdminDeleteStudentMetaUrl,
          {
            number: this.replyForm.number
          });
      this.getStudentList();
      this.replyDialogVisible = false;
      return result.data.isSuccess;
    },
    async importFresh(){
      console.log(this.ws);
      var irow;
      for(irow = 0; irow < this.ws.length; irow++){
        let questObject = {
          number: this.ws[irow].学号,
          name: this.ws[irow].姓名,
          email: this.ws[irow].邮箱,
          role: this.ws[irow].角色
        }
        let result = await this.$http.post(this.$api.AdminInsertStudentMetaUrl, questObject);
        console.log(result);
        if(result.data.isSuccess == false){
          this.errorForm.op = "批量新增";
          this.errorForm.row = irow;
          this.errorForm.notice = "已存在同学号学生，请刷新页面并修改错误学生信息";
          this.errorDialogVisible = true;
          this.getStudentList();
          return false;
        }

      }
      this.getStudentList();
      return true;

    },
    freshBtnClick(){
      this.isFresh = true;
      this.readBtnClick();
    },
    graduateBtnClick(){
      this.isFresh = false;
      this.readBtnClick();
    },
    readBtnClick() {
      // 点击事件
      document.querySelector(".input-file").click();
    },
  }
}
</script>

<style scoped>
.center{
  margin: auto 0;
  vertical-align: 50%;
}
.input-file {
  display: none;
}
</style>


