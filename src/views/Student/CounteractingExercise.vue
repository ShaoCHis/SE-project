<template>
  <div>
    <h1>稍等，正在匹配中·······</h1>
    <el-dialog
        title="题目"
        :visible.sync="visible"
        width="60%"
        :before-close="handleClose">
      <el-table
          :data="titles"
          border
          height="270"
          style="width: 100%;font-weight: bolder;font-size: 15px;">
        <el-table-column
            fixed
            prop="title"
            label="题目"
            width="150">
        </el-table-column>
        <el-table-column
            prop="A"
            label="A选项"
            width="170">
        </el-table-column>
        <el-table-column
            prop="B"
            label="B选项"
            width="170">
        </el-table-column>
        <el-table-column
            prop="C"
            label="C选项"
            width="170">
        </el-table-column>
        <el-table-column
            prop="D"
            label="D选项"
            width="170">
        </el-table-column>
      </el-table>
      <el-input v-model="input" placeholder="请按照如下格式输入:A,B,C,D......"></el-input>
      <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="success" @click="submit">提交</el-button>
          </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      path:"ws://127.0.0.1:8888/webSocket",
      socket:"",
      id:"",
      visible:false,
      time:"",
      input:"",
      titles:[{
        title:"",
        A:"",
        B:"",
        C:"",
        D:""
      },
        {
          title:"",
          A:"",
          B:"",
          C:"",
          D:""
        },
        {
          title:"",
          A:"",
          B:"",
          C:"",
          D:""
        },
        {
          title:"",
          A:"",
          B:"",
          C:"",
          D:""
        }],
      title:[],
      A:[],
      B:[],
      C:[],
      D:[],
      answers:[],
      result:[],
      otherMark:"",
      mark:0,
      i:0,
    }
  },
  mounted () {
    // 初始化
    this.init()
  },
  methods: {
    submit(){
      let sig=this.input.split(',');
      let i=0;
      while (i<sig.length){
        this.result[i]=sig[i]
        i+=1;
      }
      let j=0;
      while(j<this.result.length){
        if(this.result[j]==this.answers[j])
          this.mark+=1;
        j+=1
      }
      this.socket.send("result:"+this.id+":"+this.mark)
      this.$message.warning("请稍等，您的对手还在做题中！")
    },
    handleClose(done) {
      this.$confirm('你确定要关闭吗？该操作将不会保存修改的内容！')
          .then(_ => {
            done();
            this.visible=false
            this.$message.error("保存失败！")
            console.log(_)
          })
          .catch(_ => {
            console.log(_)
          });
    },
    init: function () {
      if(typeof(WebSocket) === "undefined"){
        alert("您的浏览器不支持socket")
      }else{
        // 实例化socket
        this.socket = new WebSocket(this.path)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error
        // 监听socket消息
        this.socket.onmessage = this.getMessage
      }
    },
    open: function () {
      console.log("socket连接成功")
      this.socket.send("register:"+this.id)
    },
    error: function () {
      console.log("连接错误")
    },
    getMessage: function (msg) {
      console.log(msg.data)
      let str=msg.data;
      if(str.startsWith("result:")) {
        let a=str.split(':')
        this.otherMark=a[1];
        this.$alert(
            "对抗练习结果：您的得分为：" +
            this.mark+
            ",对手得分为："+this.otherMark,
            "对抗练习结果",
            {
              confirmButtonText: "确定",
            }
        )
      }
      if(str.startsWith("Ah")){
        this.mark=0;
        let segments = str.split(":");
        this.title[this.i]=segments[1];
        this.A[this.i]=segments[2];
        this.B[this.i]=segments[3];
        this.C[this.i]=segments[4];
        this.D[this.i]=segments[5];
        this.answers[this.i]=segments[6];
        this.i+=1;
      }
      if(this.i==4){
        let j=0;
        while(j<4) {
          this.titles[j].title=this.title[j];
          this.titles[j].A=this.A[j];
          this.titles[j].B=this.B[j];
          this.titles[j].C=this.C[j];
          this.titles[j].D=this.D[j];
          j+=1
        }
        this.visible=true;
      }
    },
    send: function () {
      this.socket.send()
    },
    close: function () {
      console.log("socket已经关闭")
    }
  },
  destroyed () {
    // 销毁监听
    this.socket.onclose = this.close
  },
  created() {
    this.id=this.$route.params.id;
    console.log(this.id)
  }
}
</script>

<style>

</style>