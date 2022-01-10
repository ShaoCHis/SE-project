<template>
  <div>
  <div class="head">
    <el-row>
      <el-col :span="12">
        <el-image v-if="experiment.face===''" :src=imageNULL style="height: 300px"></el-image>
        <el-image v-else :src=experiment.face style="height: 300px"></el-image>
      </el-col>
      <el-col :span="12">
        <el-card class="title">
          <template #header>
            <div class="card-header" style="font-size: 25px">
              <span>{{ experiment.experimentName }}</span>
            </div>
          </template>
          <div class="item">截止日期：{{ experiment.releaseTime }}</div>

          <el-row>
            <el-col class="item" :span="4">资料下载：</el-col>
            <el-col class="item" :span="7">
              <el-button type="warning"
                         size=small
                         style="width: 80px;"
                         @click="downloadTableVisible = true">
                资料列表
              </el-button>
              <el-dialog v-model="downloadTableVisible" title="资料列表">
                <el-checkbox-group v-model="checkedFiles">
                  <el-checkbox
                      :label="document.url"
                      border
                      size="medium"
                  >{{ document.documentName }}
                  </el-checkbox>
                </el-checkbox-group>
                <el-divider></el-divider>
                <el-row>
                  <el-col style="text-align: right">
                    <el-button @click="this.downloadFiles(this.checkedFiles)"
                               style="background: rgba(174, 233, 230, 0.3);font-size: 16px">下载
                    </el-button>
                  </el-col>
                </el-row>
              </el-dialog>
            </el-col>

            <el-col :span="1"></el-col>
            <el-col class="item" :span="4">资料上传：</el-col>
            <el-col class="item" :span="8" v-if="permission>0">
              <el-button type="primary"
                         size=small
                         style="width: 80px;"
                         @click="uploadTableVisible = true">
                上传列表
              </el-button>
              <el-dialog v-model="uploadTableVisible" title="资料列表">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    :on-success="uploadFile"
                    action="/api/files/file"
                    :auto-upload="false"
                >
                  <template #trigger>
                    <el-button size="small" type="primary" style="width: 80px">选择</el-button>
                  </template>

                  <el-divider direction="vertical" style="margin:20px"></el-divider>

                  <el-button
                      style="width: 80px"
                      size="small"
                      type="success"
                      @click="submitUpload"
                  >上传
                  </el-button>
                </el-upload>
                <el-divider></el-divider>
              </el-dialog>

            </el-col>
            <el-col class="item" :span="7" style="color: orangered" v-else>暂无权限</el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <div class="content">
    <el-tabs class="comment" type="border-card">
      <el-tab-pane label="实验内容">
        <div class="need" style="margin: 10px;
        background-color: #F5F8F4;
        height: fit-content;
        min-height: 150px;
        text-align: left;
        padding: 50px">
          {{ experiment.need }}
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
  </div>
</template>

<script>
//import request from "../../utils/request";

export default {
  data() {
    return {
      downloadTableVisible: false,
      uploadTableVisible: false,
      checkedFiles: [],
      textarea1: '',
      //imageNULL: "http://106.15.6.161:8081/images/ddddffffdefault.jpg",
      permission: window.sessionStorage.getItem("permission"),
      experiment: {
        experimentId: '',
        experimentName: '',
        releaseTime: '',
        need: '',
        face: '',
        data: '',
        teacherId: '',
        courseId: ''
      },
      document: {
        url: '',
        documentName: '',
        experimentId: '',
      },
      comments: [],
      documents: []
    }
  },

  beforeRouteEnter: (to, from, next) => {
    next(vm => {
      vm.experiment.experimentId = vm.$route.query.experimentId
      //vm.getExperiment()
      //vm.getComment()
      vm.downloadFile()
    })
  },

  methods: {
    //获取实验信息
    /*getExperiment() {
      request.get('/api/experiment/experimentInformation/' + this.experiment.experimentId).then(res => {
        this.experiment = res.result
      })
    },*/

    //文件上传
    submitUpload() {
      this.$refs.upload.submit()
    },
    //上传成功
    uploadFile(res, file) {
      this.document.url = res.result
      this.document.experimentId = this.experiment.experimentId
      this.document.documentName = file.name
      this.addFile()
    },
    //添加到数据库
    addFile() {
      /*request.post('/api/document/uploadFiles', this.document).then(res => {
      })*/
    },
    //下载文件
    downloadFile() {
      /*request.get('/api/document/downloadFiles/' + this.experiment.experimentId).then(res => {
        this.documents = res.result
      })*/
    },
    //批量下载
    downloadFiles(checkedFiles) {
      for (let index in checkedFiles) {
        window.open(checkedFiles[index])
      }
    },
  }
}
</script>

<style scoped>
.head,
.content {
  margin: 20px;
  background: white;
  padding: 60px;
  border-radius: 10px;
}

.content {
  min-height: calc(100vh - 580px);
}

.need {
  -webkit-transform: translateY(-2px) translateX(-2px);
  -ms-transform: translateY(-2px) translateY(-2px);
  -webkit-box-shadow: 3px 3px 6px #999;
  box-shadow: 3px 3px 6px #999;
  -webkit-transition: all .5s ease-out;
  transition: all .5s ease-out;
}

.title .card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title .item {
  margin-bottom: 25px;
  line-height: 32px;
  text-align: left;
  font-size: 16px;
}

.edit {
  height: 55px;
  width: 180px;
  font-size: 20px;
}

.comment {
  min-height: 500px;
  height: fit-content;
}

.star:hover {
  cursor: pointer;
}

:deep(.el-card__header) {
  background-color: #F5F8F4;
}

:deep(.el-dialog) {
  width: 500px;
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-height: calc(100% - 200px);
  max-width: calc(100% - 30px);
}

:deep(.el-dialog__header) {
  background: rgba(174, 233, 230, 0.3);
  height: 60px;
  padding-top: 10px;
  padding-bottom: 10px;
  line-height: 40px;
}

:deep(.el-dialog .el-dialog__body) {
  flex: 1;
  overflow: auto;
}

:deep(.el-tabs__item) {
  font-size: 18px;
  height: 50px;
  line-height: 50px;
}

:deep(.el-tabs__nav-scroll) {
  height: 50px;
}

:deep(.el-tabs__item.is-active) {
  border-bottom: #15cbf3 2px solid;
}

.upload-demo {
  padding-left: 17%;
}

</style>
