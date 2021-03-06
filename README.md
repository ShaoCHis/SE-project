# SE-project
Tongji University Software Engineer Project
前端在front-end下提交
后端在back-end下提交

### 开发环境
```
jdk:1.8
IDE:IDEA
```

### 项目结构
```
src-------|main--------|java---------|------controller                     #控制类
   |           |                     |
   |           |                     |------error                          #错误 
   |           |                     |
   |           |                     |------model                          #实体类
   |           |                     |
   |           |                     |------repository                     #DAO层 
   |           |                     |
   |           |                     |------service                        #服务层
   |           |                     |
   |           |                     |------utils                          #工具类
   |           |                     |   
   |           |                     |------SEbackEndApplication           #项目入口
   |           |
   |           |-------|resources----|------static
   |                                 |   
   |                                 |------temlates
   |                                 |   
   |                                 |------application.properties
   |
   |
   |
   |
   |------|test--------|java
               |
               |-------|testHTTP             #HTTP测试
```

### 技术栈
```
front-end--------------------|vue
         |
         |-------------------|ElementUI

back-end---------------------|SpringBoot
        |
        |--------------------|Mysql
        |
        |--------------------|Maven

部署：centOS7
```

### 响应信息示例
```
"timestamp": "",        #时间戳
  "data": {}            #数据
  "success": true,      #成功与否
  "message": null,      #提示信息
  "code": null          #成功为null
```
