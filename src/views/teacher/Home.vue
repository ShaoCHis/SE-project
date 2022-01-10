<template>
  <!--    页面布局-->
  <el-container class="home-container">
    <!--        头部区域-->
    <el-header>
      <div class="out-button">
        <el-dropdown >
                    <span class="el-dropdown-link" style="margin-right: 15px">
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item class="navigation-text" id="username-div">{{name}}</el-dropdown-item>
            <el-dropdown-item class="navigation-text" @click.native="systemNotice">
              <el-badge value="new" class="item" :hidden="showDot">系统公告</el-badge>
            </el-dropdown-item>
            <el-dropdown-item class="navigation-text" id="exit-div" divided @click.native="logout">退出
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <!--        页面主体-->
    <el-container>
      <!--            侧边栏-->
      <el-aside width="150px">
        <el-menu router
                 background-color="rgb(239,239,239)" text-color="rgb(43,43,43)" active-text-color="#409EFF">
          <el-menu-item index="HomePage" @click="goHome">
            <i class="el-icon-s-home"></i>
            教师首页
          </el-menu-item>
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i>实验管理</template>
            <el-menu-item index="ReleaseExperiment" @click="goRelease">发布实验</el-menu-item>
            <el-menu-item index="SearchExperiment" @click="goSearchExperiment">实验查询</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-s-check"></i>成绩管理</template>
            <el-menu-item index="ExperimentReport" @click="goExReport">实验报告批改</el-menu-item>
            <el-menu-item index="StudentGrade" @click="goStudentGrade">学生成绩查询</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-s-check"></i>公告管理</template>
            <el-menu-item index="ExperimentReport" @click="goTeacherNotice">教师公告发布</el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-s-check"></i>课程管理</template>
            <el-menu-item index="TeacherCourse" @click="goTeacherCourse">课程创建</el-menu-item>
            <el-menu-item index="TeacherClass" @click="goTeacherClass">班级创建</el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"><i class="el-icon-s-check"></i>考勤管理</template>
            <el-menu-item index="ExperimentReport" @click="goTeacherAttendances">教师设置考勤</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--            右侧内容主体-->
      <el-main style="padding-left: 35px">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data()
  {
    return {
      showDot: false,
      name: window.sessionStorage.getItem('name'),
    }
  },

  mounted()
  {
    this.$nextTick(() =>
    {
      setInterval(this.CurrentTime, 1000);
    })
  },
  methods: {
    CurrentTime()
    {
      let loginTime = window.sessionStorage.getItem('loginTime');
      if (loginTime!=null && new Date().getTime() - loginTime > 7200000)//2*60*60*1000 两小时
      {
        this.$message.success("太久未登录,强制退出!");
        this.logout();
      }
    },
    //退出按钮
    logout()
    {
      window.sessionStorage.clear();
      this.$router.push("/");
    },
    goHome()
    {
      this.$router.push({
        name: "homePage",
      })
    },
    goRelease()
    {
      this.$router.push({
        name:"releaseExperiment",
      })
    },
    goExReport()
    {
      this.$router.push({
        name:"DoexperimentReport",
      })
    },
    goTeacherNotice()
    {
      this.$router.push({
        name:"teacherNotice",
      })
    },
    goTeacherCourse()
    {
      this.$router.push({
        name:"teacherCourse",
      })
    },
    goTeacherClass()
    {
      this.$router.push({
        name:"teacherClass",
      })
    },
    goStudentGrade()
    {
      this.$router.push({
        name:"studentGrade",
      })
    },
    goSearchExperiment()
    {
      this.$router.push({
        name:"searchExperiment",
      })
    },
    goTeacherAttendances() {
      this.$router.push({
        name: "teacherAttendance",
      })
    }
  }
};
</script>

<style scoped>
.home-container {
  height: 100%;
}
#username-div {
  font-size: 20px;
  font-weight: bold;
  font-family: 楷体;
}

#exit-div {
  text-align: center;
  font-weight: bold;
}

.el-header {
  background-color: rgb(43, 43, 43);
  display: flex;
  justify-content: space-between;
  margin-left: 0;
  padding-left: 0;

}

.el-aside {
  background-color: rgb(239, 239, 239);
}

.el-main {
  background-color: rgb(255, 255, 255);
}

.out-button {
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-icon-arrow-down {
  font-size: 12px;
}

</style>
