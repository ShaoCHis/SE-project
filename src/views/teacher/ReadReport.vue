<template>
  <div style="margin: 10px;background: white;padding:20px;border-radius: 10px" >
    <el-form ref="form" label-position="top" :model="form">
      <el-row :gutter="20">
        <el-col :span="6">
      <div style="width:300px">
        <el-form-item label="实验名称" prop="title">
          <el-input v-model="form.title" :disabled="true"></el-input>
        </el-form-item>
      </div>
        </el-col>
        <el-col :span="6">
      <div style="width:200px">
        <el-form-item label="授课老师" prop="teacher">
          <el-select v-model="form.teacher" placeholder="请选择教师" :disabled="true">
            <el-option label="黄杰" value="huangjie"></el-option>
            <el-option label="黄杰！" value="huangjie!"></el-option>
          </el-select>
        </el-form-item>
      </div>
        </el-col>
        <el-col :span="6">
      <div style="width: 300px">
        <el-form-item label="实验时间" prop="date">
          <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.date"
              style="width: 100%;"
              :disabled="true"
          ></el-date-picker>
        </el-form-item>
      </div>
        </el-col>
      </el-row>

      <el-form-item label="实验内容">
        <el-input :disabled="true"></el-input>
      </el-form-item>


      <el-form-item label="心得体会">
        <el-input :disabled="true"></el-input>
      </el-form-item>

      <el-divider content-position="left"></el-divider>
      <div>
        <el-form-item label="实验报告得分" prop="score">
          <el-input type="textarea" v-model="form.score" ></el-input>
        </el-form-item>
      </div>

      <el-form-item label="实验报告评价" prop="pingyu">
        <el-input type="textarea" v-model="form.pingyu" rows="10"></el-input>
      </el-form-item>

      <el-form-item>
        <el-popconfirm
            confirmButtonText="确认"
            cancelButtonText="再想想"
            icon="el-icon-info"
            iconColor="yellow"
            title="提交后无法编辑，确认提交吗？"
        >
          <template #reference>
            <el-button type="primary" @click="onSubmit">确认完成</el-button>
          </template>
        </el-popconfirm>
        <el-button>    取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data(){
    return {
      form: {
        name: '',
        teacher: '',
        date: '',
        domains: [
          {
            value: '',
          },
        ],
        princ: '',
        appearance:'',
        content: '',
        sum:'',
        comment:'',
        score:"",
        pingyu:"",
      },
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!')
    },
  }

}
</script>

<style scoped>
.el-textarea__inner{
  height: 300px;
}
</style>