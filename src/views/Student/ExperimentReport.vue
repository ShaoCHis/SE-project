<!--
author:ShaoCHi
date: 2021/12/8 10:12 AM
IDE:WebStorm
Tongji University
-->

<template>
  <div style="height: 100%">
    <el-container>
      <el-main>
        <div style="height: 50px">
          <el-row :span="24" style="top: 5px">
            <el-col span="6">
              <el-button type="success" @click="download">下载资料</el-button>
            </el-col>
            <el-col span="6">
              <el-button style="background-color: #9cc0ff" @click="this.feedbackVisible=true">提交反馈</el-button>
            </el-col>
          </el-row>
        </div>
        <div class="block" style="width: 100%">
          <el-timeline style="margin-top: 5%">
            <el-timeline-item
                v-for="(experiment, index) in experiments"
                :key="index"
                :type="type"
                :color="color"
                :size="size"
                :timestamp="experiment.uploadTime" placement="top">
              <el-card style="height: 10vh">
                <span style="font-size: 20px;align-content: center">{{ experiment.name }}</span>
                <el-button style="position:absolute;right: 20%" type="success"
                           @click="complete(experiment.experimentId,index)">填写
                </el-button>
                <el-button style="position:absolute;right: 5%" type="primary"
                           @click="checkReport(experiment.experimentId)">查看反馈以及得分
                </el-button>
                <br>
                <span style="position: absolute;right: 100px;margin-top: 12px">截止时间：{{
                    new Date(
                        +new Date(experiment.uploadTime) + 8 * 3600 * 1000 + 7 * 24 * 3600 * 1000
                    )
                        .toISOString()
                        .replace(/T/g, " ")
                        .replace(/\.[\d]{3}Z/, "")
                  }}</span>
              </el-card>
              <!--        {{activity.content}}-->
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-main>
    </el-container>
    <div>
      <el-dialog
          title="实验报告"
          :visible.sync="dialogVisible"
          width="60%"
          :before-close="handleClose">
        <el-form :label-position="top" label-width="100px" :model="report">
          <el-form-item label="实验名称">
            <el-input v-model="report.title" type="textarea"
                      :autosize="{minRows:2,maxRows:3}" placeholder="Please input" clearable></el-input>
          </el-form-item>
          <el-form-item label="实验内容">
            <el-input v-model="report.content" type="textarea"
                      :autosize="{ minRows: 6, maxRows: 7}" placeholder="Please input" clearable
                      label="report.content"></el-input>
          </el-form-item>
          <el-form-item label="实验结论">
            <el-input v-model="report.conclusion" type="textarea"
                      :autosize="{ minRows: 6, maxRows: 7}" placeholder="Please input" :rows="2" clearable></el-input>
          </el-form-item>
        </el-form>
        <br>
        <span>{{ "上次修改时间：" + updateTime }}</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="success" @click="submit">提交</el-button>
          </span>
      </el-dialog>
      <el-dialog
          title="实验反馈"
          :visible.sync="checkVisible"
          width="60%"
          :before-close="handleClose">
        <el-descriptions title="用户信息" :column="1" border style="width:70%;margin-left:10%;margin-top: 5%;font-size:20px;font-weight: bolder" class="info">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-files"></i>
              实验报告名
            </template>
            {{report.title}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-scissors"></i>
              实验得分
            </template>
            {{report.score}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              评语
            </template>
            {{report.ping_yu}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              批改者
            </template>
            <el-tag>{{report.teacher_or_assistant}}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              批改时间
            </template>
            {{report.appraise_time}}
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </div>
    <div>
      <el-dialog
          title="提交课程反馈"
          :visible.sync="feedbackVisible"
          width="60%"
          :before-close="handleClose">
        <el-input v-model="feedbackContent" type="textarea"
                  :autosize="{minRows:2,maxRows:3}" placeholder="Please input" clearable>
        </el-input>
        <span slot="footer" class="dialog-footer">
            <el-button @click="feedbackVisible = false">取消</el-button>
            <el-button type="success" @click="feedback">提交</el-button>
          </span>
      </el-dialog>
      <el-dialog
          title="实验反馈"
          :visible.sync="checkVisible"
          width="60%"
          :before-close="handleClose">
        <el-descriptions title="用户信息" :column="1" border style="width:70%;margin-left:10%;margin-top: 5%;font-size:20px;font-weight: bolder" class="info">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-files"></i>
              实验报告名
            </template>
            {{report.title}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-scissors"></i>
              实验得分
            </template>
            {{report.score}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              评语
            </template>
            {{report.ping_yu}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              批改者
            </template>
            <el-tag>{{report.teacher_or_assistant}}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              批改时间
            </template>
            {{report.appraise_time}}
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
      <el-dialog
          title="文件列表"
          :visible.sync="fileVisible"
          width="60%"
          :before-close="handleClose">
        <el-table
            :data="fileList"
            border
            height="270"
            style="width: 100%;font-weight: bolder;font-size: 15px;">
          <el-table-column
              fixed
              prop="fileName"
              label="文件名称"
              width="150">
          </el-table-column>
          <el-table-column
              prop="type"
              label="type"
              width="170">
          </el-table-column>
          <el-table-column
              prop="uploadTime"
              label="上传时间"
              width="250">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
              width="200">
            <template slot-scope="index">
              <el-button @click="handleClick(index.$index)" type="text" size="small">下载</el-button>
              <el-button type="text" size="small" disabled>编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ExperimentReport",
  data() {
    return {
      id: "",
      classId: "",

      //实验报告所需实验Id
      dialogVisible: false,
      feedbackVisible:false,
      checkVisible:false,
      fileVisible:false,
      experimentId: "",

      color: '#0bbd87',
      size: "large",
      type: 'primary',

      //是否已经提交过实验报告，未提交则添加，已提交则修改
      isPresent: false,
      //已提交则会拥有reportId
      reportId: "",
      //修改时间
      updateTime: "null",

      experiments: [],
      courseId:"",

      feedbackContent:"",

      report: {
        title: "",
        content: "",
        conclusion: ""
      },
      fileList:[]

    }
  },
  methods: {
    handleClick(row) {
      console.log(row);
      console.log(this.fileList[row])
      let that=this;
      let formData = {
        "courseId":this.courseId,
        "fileName":this.fileList[row].fileName
      }
      axios.post(`//localhost:8080/coursefiles/downloadFile`,formData).then((res) => {
        console.log(res)
        this.fileVisible=false;
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async getAllFiles(){
      let that=this;
      axios.get(`//localhost:8080/coursefiles/`+this.courseId).then((res) => {
        console.log(res)
        if (res.data.success == false) {
          this.fileList=res.data.data;
          console.log("file"+res.data)
        }
        this.fileVisible=true;
        console.log(res)
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async complete(value, index) {
      console.log("索引：" + value, index)
      //先调get进行判断数据库是否已经存在了实验报告
      console.log(this.experiments)
      let nowDate = new Date();
      let endDate = new Date(
          +new Date(this.experiments[index].uploadTime) + 8 * 3600 * 1000 + 7 * 24 * 3600 * 1000
      )
      console.log(nowDate < endDate)
      if (nowDate > endDate) {
        this.$message.error("已超过实验报告提交时间，无法再次进行操作！");
        return
      }
      let that = this;
      that.experimentId = value;
      axios.get(`//localhost:8080/reports/get?experimentid=` + value + "&studentid=" + that.id).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          if (res.data.data == null) {
            that.report = {
              title: "",
              content: "",
              conclusion: ""
            };
            that.updateTime = "null";
            this.$message.warning("你还未提交过实验报告，请尽快提交！");
            that.isPresent = false;
          } else {
            this.$message.warning("你已提交过实验报告，在截止前你可以选择对其进行修改");
            that.isPresent = true;
            that.reportId = res.data.data.reportId;
            that.report = res.data.data;
            if (that.report.updateTime == null) {
              that.updateTime = new Date(
                  +new Date(that.report.submitTime)
              )
                  .toISOString()
                  .replace(/T/g, " ")
                  .replace(/\.[\d]{3}Z/, "");
            } else {
              that.updateTime = new Date(
                  +new Date(that.report.updateTime)
              )
                  .toISOString()
                  .replace(/T/g, " ")
                  .replace(/\.[\d]{3}Z/, "");
            }
            console.log(that.report)
          }
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
      this.dialogVisible = true
    },
    async checkReport(value) {
      let that = this;
      that.experimentId = value;
      axios.get(`//localhost:8080/reports/get?experimentid=` + value + "&studentid=" + that.id).then((res) => {
        if (res.data.success == true) {
          console.log(res);
          that.report = res.data.data;
          that.report.appraise_time = new Date(+new Date(that.report.appraise_time))
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "");
          that.checkVisible=true;
        }else {
          this.$message.warning("老师还未评阅，您无法查看！")
        }
        console.log(that.report)
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    handleClose(done) {
      this.$confirm('你确定要关闭吗？该操作将不会保存修改的内容！')
          .then(_ => {
            done();
            this.$message.error("保存失败！")
            console.log(_)
          })
          .catch(_ => {
            console.log(_)
          });
    }
    ,
    async submit() {
      let that = this;
      if (that.isPresent == true) {
        axios.post(`//localhost:8080/reports/alter?reportid=` + that.reportId + "&title=" + that.report.title + "&content=" + that.report.content + "&conclusion=" + that.report.conclusion).then((res) => {
          console.log(res)
          if (res.data.success == true) {
            this.$message.success("修改成功！")
          }
          this.dialogVisible = false
        }).catch((res) => {
          console.log(res);
          that.$message.error("Time out!Please try again!");
        })
      } else {
        axios.post(`//localhost:8080/reports/submit?experimentid=` + that.experimentId + "&studentid=" + that.id + "&title=" + that.report.title + "&content=" + that.report.content + "&conclusion=" + that.report.conclusion).then((res) => {
          console.log(res)
          if (res.data.success == true) {
            this.$message.success("提交成功！")
          }
          this.dialogVisible = false
        }).catch((res) => {
          console.log(res);
          that.$message.error("Time out!Please try again!");
        })
      }
    }
    ,
    async getExperiments() {
      let that = this;
      axios.get(`//localhost:8080/reports/getexperimentsofclass/` + that.classId).then((res) => {
        if (res.data.success == true) {
          console.log(res)
          that.experiments = res.data.data;
        }
        for (let i in that.experiments) {
          //UTC时间格式转yyyy cc:dd:mm
          that.experiments[i].uploadTime = new Date(
              +new Date(that.experiments[i].uploadTime) + 8 * 3600 * 1000
          )
              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "");
          console.log(that.experiments[i].uploadTime)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async download() {
      await this.getAllFiles();
      console.log("1");
    },
    async feedback(){
      let that = this;
      axios.get(`//localhost:8080/feedback/add?studentid=` + that.id+"&classid="+that.classId+"&feedback="+that.feedbackContent).
      then((res) => {
        if (res.data.success == true) {
          console.log(res)
          that.experiments = res.data.data;
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
    async getCourse(){
      let that = this;
      axios.get(`//localhost:8080/classes/getcourse/` + that.classId).then((res) => {
        if (res.data.success == true) {
          that.courseId = res.data.data.course_id;
          console.log(this.courseId)
          console.log(res)
        }
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.classId = this.$route.params.classId;
    this.getExperiments();
    this.getCourse()
  }
}
</script>

<style scoped>
.el-container {
  height: 100%;
  border: 0;
  top: 0;
  margin: 0;
}

.el-aside {
  height: 100%;
}

.el-main {
  /*line-height: 100vh;*/
  height: 100%;
  padding: 0;
}
</style>