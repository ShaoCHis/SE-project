<template>
  <div style="padding: 20px">
    <el-table :data="tableData"
              stripe
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'ascending' }"
    >
      <el-table-column label="学号" prop="id" />
      <el-table-column label="实验报告名称" prop="title" />
      <el-table-column label="批改时间" prop="appraiseTime" />
      <el-table-column label="批改评语"
                       prop="conclusion"
      >
      </el-table-column>

      <el-table-column align="right">
        <template #header>
          <el-input @keyup.enter.native="searchResource" v-model="keywords" size="small" placeholder="搜索"></el-input>
        </template>
        <template slot-scope="index">
          <el-button size="medium" @click="goToRead(index.$index)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        hide-on-single-page
        class="fy"
        background
        layout="prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :total="filterDataShow.length">
    </el-pagination>
    <el-dialog
        title="实验报告"
        :visible.sync="dialogVisible"
        width="60%"
        :before-close="handleClose">
      <el-form :label-position="top" label-width="100px" :model="report">
        <el-form-item label="实验名称">
          <el-input v-model="report.title" type="textarea"
                    :autosize="{minRows:2,maxRows:3}" placeholder="Please input" clearable disabled></el-input>
        </el-form-item>
        <el-form-item label="实验内容">
          <el-input v-model="report.content" type="textarea"
                    :autosize="{ minRows: 6, maxRows: 7}" placeholder="Please input" clearable
                    label="report.content" disabled></el-input>
        </el-form-item>
        <el-form-item label="实验结论">
          <el-input v-model="report.conclusion" type="textarea"
                    :autosize="{ minRows: 6, maxRows: 7}" placeholder="Please input" :rows="2" clearable disabled></el-input>
        </el-form-item>
      </el-form>
      <el-input v-model="content" type="textarea"
                :autosize="{ minRows: 6, maxRows: 7}" placeholder="请输入您的评语" :rows="2" clearable></el-input>
      <el-input v-model="goals" placeholder="请输入你的打分"></el-input>
      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="success" @click="submit(report.id)">提交</el-button>
          </span>
    </el-dialog>
  </div>
</template>

<script>

import axios from "axios";

export default {
  // components: {
  //   readReport
  // },
  data() {
    return {
      total: 0,
      pageSize: 10,
      page: 1,
      currentPage: 1,
      tableData:[],
      TableData:[],
      keywords: '',
      dialogVisible:false,
      filterDataShow: [],
      report: {
        id:"",
        title: "",
        content: "",
        conclusion: ""
      },
      content:"",
      goals:"",
    }
  },

  computed: {
    //showTable计算属性通过slice方法计算表格当前应显示的数据
    showTable() {
      return this.filterDataShow.slice(
          (this.currentPage - 1) * this.pageSize,
          this.currentPage * this.pageSize
      );
    }
  },
  mounted() {
    this.searchResource(); //在页面挂载后调用此方法，确保数据与页面发生了交互
  },
  watch: { //监听搜索框内容，当搜索框内容发生变化时调用searchResource方法
    keywords: {
      handler() {
        this.searchResource()
      }
    }
  },
  methods: {
    goToRead(index){
      console.log(index)
      this.report.title=this.tableData[index].title
      this.report.content=this.tableData[index].content
      this.report.conclusion=this.tableData[index].conclusion
      this.report.id=this.tableData[index].eid;
      this.dialogVisible=true;
    },
    searchResource() {
      this.currentPage = 1; //将当前页设置为1，确保每次都是从第一页开始搜
      let filterKeywords = this.keywords.trim();
      let filterResource = this.tableData.filter(item => { //过滤全部数据
        if (item.name.includes(filterKeywords)||item.stuNum.includes(filterKeywords)
            ||item.experimentName.includes(filterKeywords)) {
          return item
        }
      })
      this.filterDataShow = filterResource; //将符合条件的内容赋给filterDataShow
    },
    filterTag(value, row) {
      return row.state === value
    },
    handleCurrentChange(val) {
      this.page=val;
      this.currentPage=val;
    },
    getReports()
    {
      let that = this;
      axios.post("//localhost:8080/classes/add?week=" + that.addForm.week + "&starttime=" + that.addForm.starttime + "&endtime=" + that.addForm.endtime + "&room=" + that.addForm.room + "&courseid=" + that.addForm.courseid+ "&teacherid=" + that.addForm.teacherid+"&reportrate="+that.addForm.reportrate+"&attendrate="+that.addForm.attendrate+"&cprate="+that.addForm.cprate).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        this.getClassList();
        console.log(res);
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    getAll(){
      axios.get(`//localhost:8080/reports/all`).then((res)=>{
        console.log(res)
        if(res.data.success==true){
          this.tableData=res.data.data;
          let i=0;
          console.log(this.tableData);
          console.log(this.tableData.length);
          let j=0;
          while(i<this.tableData.length)
          {
            if(this.tableData[i].eid===this.experimentid) {
              this.TableData[j] = this.tableData[i];
              j++;
            }
            i++;
          }
          console.log(this.TableData);
          this.TableData.length=j;
          this.tableData.length=j;
          this.tableData=this.TableData;
          while (i<this.tableData.length){
            console.log(this.tableData[i].appraiseTime);
            this.tableData[i].appraiseTime = new Date(
                +new Date(this.tableData[i].appraiseTime)+ 8 * 3600 * 1000
            )
                .toISOString()
                .replace(/T/g, " ")
                .replace(/\.[\d]{3}Z/, "");
            i+=1;
          }
          console.log(this.tableData)
        }
      })
    },
    submit(index){
      axios.post(`//localhost:8080/reports/appraisebyte?reportid=`+index+"&score="+this.goals+"&pingyu="+this.content+"&teacherid=1").then((res)=>{
        console.log(res)
        if(res.data.success==true){
          console.log(this.tableData)
          this.dialogVisible=false;
        }
      })
    }
  },
  created() {
    this.experimentid=this.$route.params.experimentid;
    console.log(this.experimentid);
    this.getAll();
  }
}
</script>
<style>
.fy{
  text-align:center;
  margin-top:30px;
}
</style>