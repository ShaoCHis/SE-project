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
          <el-menu-item index="AdminHome" @click="goHome">
            <i class="el-icon-s-home"></i>
            管理员首页
          </el-menu-item>
          <el-menu-item index="AdminNotice" @click="goNotice">
            <i class="el-icon-s-shop"></i>
            系统公告管理
          </el-menu-item>
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-menu"></i>人员管理</template>
            <el-menu-item index="AdminUser" @click="goUser">用户管理</el-menu-item>
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
    systemNotice()
    {
      this.showDot = true;
      this.$router.push("/AdminNotice")
    },
    goHome()
    {
      this.$router.push({
        name: "adminHomePage",
      })
    },
    goNotice()
    {
      this.$router.push({
        name: "adminNotice",
      })
    },
    goStu()
    {
      this.$router.push({
        name:"adminStu",
      })
    },
    goTeacher()
    {
      this.$router.push({
        name:"adminTeacher",
      })
    },
    goCourse()
    {
      this.$router.push({
        name:"adminCourse",
      })
    },
    goClass()
    {
      this.$router.push({
        name:"adminClass",
      })
    },
    goUser()
    {
      this.$router.push({
            name:"adminUser",
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
