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
        <el-form-item label="实验名称" prop="experimentName" style="width: 50%">
          <el-input v-model="form.experimentName"></el-input>
        </el-form-item>
        <el-form-item label="实验截至日期" required>
          <el-col :span="11">
            <el-form-item prop="releaseTime">
              <el-date-picker
                  v-model="form.releaseTime"
                  type="date"
                  placeholder="选择一个日期"
                  style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>

        <el-form-item label="实验要求" prop="need" style="width: 100%;">
          <el-input v-model="form.need" type="textarea"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">发布</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
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
        experimentName: [
          {
            required: true,
            message: '请输入实验名称',
            trigger: 'blur',
          },
        ],
        releaseTime: [
          {
            required: true,
            message: '请选择发布时间',
            trigger: 'blur',
          },
        ],
        need: [
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
    /*release() {
      request.post("/api/experiment/release", this.form).then(res => {
        this.imageUrl=''
        this.resetForm("ruleForm")
        console.log(res)
      })

      ElNotification({
        title: '成功',
        message: '实验项目已发布',
        type: 'success',
      })
    },*/

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.release()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    //上传图片
    handlefaceSuccess(res) {
      this.form.face = res.result
      this.imageUrl = this.form.face
      console.log(this.imageUrl)
    },

    //获取图片


    beforefaceUpload(file) {
      // 设置限定格式
      const img_mimetype = ['image/jpeg', 'image/jpg', 'image/png']
      const isJPG = img_mimetype.includes(file.type);
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传头像只能是图片格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false;
      }
      return isJPG && isLt2M;
    },
    // 文件上传
    submitUpload() {
      this.$refs.upload.submit();
    },
    submitSuccess(res){
      this.form.data=res.result
      console.log(this.form.data)
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
