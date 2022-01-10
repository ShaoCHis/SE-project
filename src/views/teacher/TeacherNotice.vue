<template>
  <div>

    <!--        卡片-->
    <el-card class="box-card">
      <!--  添加-->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="showAddAnnouncement">添加公告</el-button>
        </el-col>
      </el-row>
      <!--            公告列表 只展示一些公告信息,详细文本可在详情查看-->
      <el-table :data="AnnouncementList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="种类" prop="category"></el-table-column>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="显示详情">
          <template slot-scope="scope">
            <el-button type="primary" @click="showDialog(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="removeById(scope.row.noticeId)"
                       icon="el-icon-delete"></el-button>

          </template>
        </el-table-column>

      </el-table>
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

    <!--        展示系统公告对话框-->
    <el-dialog ref="showFormRef" :visible.sync="showDialogVisible"
               width="50%">
      <!--            展示内容主体区域 -->
      <div slot="title">
        <h1>
          {{ addForm.category }}
        </h1>
        <div>
          {{ addForm.title }}
        </div>
      </div>
      {{ addForm.content }}
      <!--            底部区域-->
      <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeDialogVisible">确 定</el-button>
            </span>
    </el-dialog>


    <!--        添加公告对话框-->
    <el-dialog title="添加公告" :visible.sync="addDialogVisible"
               width="50%" center>
      <!--            内容主体区域 放置一个表单-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="150px">
        <el-form-item label="公告类型:" prop="title">
          <el-input v-model="addForm.category" style="width: 82%;"></el-input>
        </el-form-item>
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
        <el-form-item label="班级id:" prop="classid">
          <el-input v-model="addForm.classid" style="width: 82%;"></el-input>
        </el-form-item>
      </el-form>
      <!--            底部区域-->
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancelAdd" style="margin-right: 20px;">取 消</el-button>
    <el-button type="primary" @click="addAnnouncement">确 定</el-button>
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

      //查询到的当页公告
      AnnouncementList: [],
      //总条数,用于分页的显示
      totalCount: 0,
      //添加,修改,展示公告对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      //添加公告表单数据
      addForm: {
        category:"",
        title: "",
        content: "",
        classid:"",
      },
      //添加公告的校验规则
      addFormRules: {}
    }
  },
  //一开始就显示公告列表
  created() {
    this.getAnnouncementList();
  },
  methods: {
    async getAnnouncementList() {
      let that = this;
      axios.get(`//localhost:8080/classnotices`).then((res) => {

        console.log(res);
        if (res.data.success == true) {
          console.log(res)
          that.AnnouncementList=res.data.data
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getAnnouncementList();
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage) {
      this.pageNumber = newPage;
      this.getAnnouncementList();
    },

    showAddAnnouncement() {

      this.addDialogVisible = true;
      //清空表单的校验项
      this.$nextTick(() => {
        this.$refs.addFormRef.resetFields();
      });

      this.addForm.category="";
      this.addForm.title = "";
      this.addForm.content = "";
      this.addForm.classid="";
    },

    //点击确定按钮后,添加公告
    addAnnouncement() {
      let that = this;
      axios.post("//localhost:8080/classnotices/add?category=" + that.addForm.category + "&title=" + that.addForm.title + "&content=" + that.addForm.content + "&classid=" + that.addForm.classid).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        console.log(res);
        this.getAnnouncementList();
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加公告成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //添加公告框里面的取消添加公告按钮触发的事件
    cancelAdd() {
      //隐藏添加公告对话框
      this.addDialogVisible = false;
      this.$message.info("取消添加公告!");
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
      this.$message.info("取消修改公告!");
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

