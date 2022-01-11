<template>
  <div>

    <!--        卡片-->
    <el-card class="box-card">
      <!--  添加-->
      <el-row :gutter="20">
        <el-input  placeholder="请输入班级id" v-model="classid">
          <el-button slot="append" icon="el-icon-search" @click="getGradeList"></el-button>
        </el-input>
      </el-row>
      <!--            公告列表 只展示一些公告信息,详细文本可在详情查看-->
      <el-table :data="GradeList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="学生姓名" prop="name"></el-table-column>
        <el-table-column label="成绩" prop="grade"></el-table-column>

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
      GradeList: [],
      //总条数,用于分页的显示
      totalCount: 0,
      //添加,修改,展示公告对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      classid:"",
    }
  },
  //一开始就显示公告列表

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

  }
}
</script>

<style scoped></style>

