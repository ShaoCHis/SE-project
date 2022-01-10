<template>
  <div>

    <!--        卡片-->
    <el-card class="box-card">
      <!--  添加-->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="showAddAttendance">添加考勤</el-button>
        </el-col>
      </el-row>

    </el-card>

    <!--        添加公告对话框-->
    <el-dialog title="添加考勤" :visible.sync="addDialogVisible"
               width="50%" center>
      <!--            内容主体区域 放置一个表单-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px">
        <el-form-item label="班级id:" prop="title">
          <el-input v-model="addForm.classid" style="width: 82%;"></el-input>
        </el-form-item>
        <el-form-item label="考勤码:" prop="title">
          <el-input v-model="addForm.code" style="width: 82%;"></el-input>
        </el-form-item>
      </el-form>
      <!--            底部区域-->
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancelAdd" style="margin-right: 20px;">取 消</el-button>
    <el-button type="primary" @click="addAttendance">确 定</el-button>
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
      pageSize: 5,

      //总条数,用于分页的显示
      totalCount: 0,
      //添加,修改,展示公告对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      //添加公告表单数据
      addForm: {
        classid:"",
        code: "",
      },
      //添加公告的校验规则
      addFormRules: {}
    }
  },
  //一开始就显示公告列表
  methods: {

    showAddAttendance() {

      this.addDialogVisible = true;
      //清空表单的校验项
      this.$nextTick(() => {
        this.$refs.addFormRef.resetFields();
      });

      this.addForm.classid="";
      this.addForm.code = "";
    },

    //点击确定按钮后,添加公告
    addAttendance() {
      let that = this;
      axios.post("//localhost:8080/attendances/set?classid=" + that.addForm.classid + "&code=" + that.addForm.code).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        console.log(res);
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加考勤成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //添加公告框里面的取消添加公告按钮触发的事件
    cancelAdd() {
      //隐藏添加公告对话框
      this.addDialogVisible = false;
      this.$message.info("取消添加考勤!");
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
    //修改公告页面弹出后,会查询要修改的id所对应公告的内容
    /*async showEditDialog(AnnouncementId) {
      let result = await this.$http.post(this.$api.AdminGetOneAnnouncement + "/" + AnnouncementId);
      this.addForm = result.data;
      this.editDialogVisible = true;
    },*/
    //根据ID删除对应信息
    async removeById(noticeId) {
      console.log(noticeId);
      //    弹框提示
      let confirmResult = await this.$confirm('此操作将永久删除该公告, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      //    如果选择不删除
      if (confirmResult !== "confirm") {
        return this.$message.info("已经取消删除");
      }else {
        await axios.delete("//localhost:8080/classnotices" + "/" + noticeId).then((res)=>{
          console.log(res)
        });
        this.$message.info("删除成功!");
        await this.getAnnouncementList();
      }
    }
  }
}
</script>

<style scoped></style>

