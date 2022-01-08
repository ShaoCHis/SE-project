<template>
  <div style="margin: 10px;background: white;padding:20px;border-radius: 10px" >
    <el-form ref="form" label-position="top" :model="form">
      <el-row :gutter="20">
        <el-col :span="6">
      <div style="width:300px">
        <el-form-item label="实验名称" prop="name">
          <el-input v-model="form.name" :disabled="true"></el-input>
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
        <el-form-item>
          <el-row>
            <el-col :span="8">
              <span class="demonstration">实验报告完整性</span>
              <el-rate v-model="gradeOne" allow-half />
            </el-col>
            <el-col :span="8">
              <span class="demonstration">实验结果正确性</span>
              <el-rate v-model="gradeTwo" allow-half />
            </el-col>
            <el-col :span="8">
              <span class="demonstration">实验总结</span>
              <el-rate v-model="gradeThree" allow-half />
            </el-col>
          </el-row>
        </el-form-item>
      </div>

      <el-form-item label="实验报告评价" prop="name">
        <el-input type="textarea" v-model="form.comment" rows="10"></el-input>
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
        <el-button>     取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data(){
    return {
      gradeOne:'',
      gradeTwo:'',
      gradeThree:'',
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