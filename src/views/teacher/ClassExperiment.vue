<template>
  <div>
    <!--        卡片-->
    <el-card class="box-card">
      <!--            列表-->
      <el-table :data="examineCurData">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="实验id" prop="experimentId"></el-table-column>
        <el-table-column label="实验名称" prop="name"></el-table-column>
        <el-table-column label="实验介绍" prop="intro"></el-table-column>
        <el-table-column label="发布时间" prop="uploadTime"></el-table-column>
        <el-table-column label="删除实验">
          <template slot-scope="scope">
            <el-button type="primary" @click="removeById(scope.row.experimentId)" icon="el-icon-delete"></el-button>
          </template>
        </el-table-column>
        <el-table-column label="查看实验报告">
          <template slot-scope="scope">
            <el-button type="primary" @click="readreport(scope.row.experimentId)">查看实验报告</el-button>
          </template>
        </el-table-column>
      </el-table>
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
      currentPage: 1,
      pageSize: 5,

      //查询到的当页公告
      ExperimentList: [],

      //添加,修改,展示公告对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      showDialogVisible: false,

      courseid: "",
    }
  },
  //一开始就显示公告列表
  created() {
    this.classid=this.$route.params.classid;
    this.courseid=this.$route.params.courseid;
    console.log(this.classid);
    console.log(this.courseid);
    this.getExperimentList();
  },
  computed: {
    examineSearchData() {
      return (this.ExperimentList.filter(data => !this.search ||
          data.experimentId.toLowerCase().includes(this.search.toLowerCase())))
    },
    examineCurData: function () {
      return this.examineSearchData.slice((this.currentPage - 1) * this.pageSize,
          Math.min(this.currentPage * this.pageSize, this.examineSearchData.length));
    }
  },
  methods: {
    async getExperimentList() {
      let that = this;
      axios.get(`//localhost:8080/reports/getexperimentsofclass/` + this.classid).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          that.ExperimentList = res.data.data;
          console.log(that.ExperimentList);
          let i = 0;
          while (i < that.ExperimentList.length) {
            that.ExperimentList[i].uploadTime = new Date(
                +new Date(that.ExperimentList[i].uploadTime) + 8 * 3600 * 1000
            )
                .toISOString()
                .replace(/T/g, " ")
                .replace(/\.[\d]{3}Z/, "");
            i += 1;
          }
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    //监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.getExperimentList();
    },
    //监听pageNum改变的事件
    handleCurrentChange(newPage) {
      this.pageNumber = newPage;
      this.getExperimentList();
    },
    async removeById(experimentId) {
      //    弹框提示
      let confirmResult = await this.$confirm('此操作将永久删除该实验, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);
      //    如果选择不删除
      if (confirmResult !== "confirm") {
        return this.$message.info("已经取消删除");
      } else {
        await axios.delete("//localhost:8080/experiments" + "/" + experimentId).then((res) => {
          console.log(res)
        });
        this.$message.info("删除成功!");
        this.getExperimentList();
      }
    },
    async readreport(experimentId) {
      console.log(experimentId);
      this.$router.push({
        name: "classReport",
        params: {
          experimentid: experimentId

        }
      })

    },
  }
}
</script>

<style scoped></style>

