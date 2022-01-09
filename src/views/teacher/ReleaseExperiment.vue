<template>
  <el-row>

    <div class="exp">
      <el-form
          ref="ruleForm"
          label-width="120px"
          class="demo-ruleForm"
          label-position="left"
          :model="form"
          :rules="rules"
      >
        <el-form-item label="实验名称" prop="experimentname" style="width: 50%">
          <el-input v-model="form.experimentname"></el-input>
        </el-form-item>
        <el-form-item label="课程id" prop="courseid" style="width: 50%">
          <el-input v-model="form.courseid"></el-input>
        </el-form-item>
        <el-form-item label="实验要求" prop="intro" style="width: 100%;">
          <el-input v-model="form.intro" type="textarea"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">发布</el-button>
        </el-form-item>
      </el-form>
    </div>

<!--    <div class="files">
      <div style="text-align: center;color: cornflowerblue;margin-top: 10px">上传文件</div>
      <el-form
          ref="editFormRef">

        &lt;!&ndash;            文件上传&ndash;&gt;
        <el-form-item>
          <el-upload
              ref="upload"
              class="upload-demo"
              action="api/files/file"
              :on-success="submitSuccess"
              :auto-upload="false">
            <template #trigger>
              <el-button style="font-size: 12px;width: 80px;margin-top: 80px;height: 30px"
                         type="primary">文件
              </el-button>
            </template>
            <el-button
                style="width: 80px;height: 30px;font-size: 12px;margin-left: 20px"
                type="success"
                @click="submitUpload">上传
            </el-button>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>-->

  </el-row>
</template>

<script>
/*import {Plus} from '@element-plus/icons'
import request from "@/utils/request"
import { ElNotification } from 'element-plus'*/

import axios from "axios";

export default {
  components: {
    //图片
    //Plus,
  },
  data() {
    return {
      imageUrl: '',

      form: {
        experimentName: '',
        releaseTime: '',
        need: '',
        courseId: 2,
        teacherId: '',
        face:''
      },

      rules: {
        experimentname: [
          {
            required: true,
            message: '请输入实验名称',
            trigger: 'blur',
          },
        ],
        courseid: [
          {
            required: true,
            message: '请输入课程id',
            trigger: 'blur',
          },
        ],
        intro: [
          {
            required: true,
            message: '请描述一下实验要求',
            trigger: 'blur',
          },
        ],
      },
    }
  },

  methods: {
    submitForm()
    {
      let that = this;
      axios.post("//139.224.65.154:8080/experiments/add?courseid=" + that.form.courseid + "&experimentname" + that.form.experimentname + "&intro=" + that.form.intro).then((res) => {
        //隐藏添加公告对话框
        this.addDialogVisible = false;
        console.log(res);
        if(res.data.success!=true)
          return this.$message.error('增加失败！');
        this.$message.info("添加成功!");
      }).catch((res) => {
        console.log(res);
        that.$message.error("Time out!Please try again!");
      })
    },
  },
}
</script>

<style>
.files {
  height: 520px;
  margin-left: 20px;
  background-color: white;
  width: 220px;
  padding: 10px;
  border-radius: 10px;
  max-height: fit-content;
}

.exp {
  padding: 100px 50px 50px 50px;
  margin-left: 100px;
  background-color: white;
  width: 850px;
  border-radius: 10px;
}

.el-form-item__content {
  text-align: center;
}

.el-textarea__inner {
  height: 100px;

}

.avatar-uploader .el-upload {
  width: 162px;
  height: 162px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader {
  margin-top: 20px;
  border-color: #409eff;
  height: 162px;
}


.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.avatar-uploader-icon svg {
  margin-top: 74px; /* (178px - 28px) / 2 - 1px */
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>
