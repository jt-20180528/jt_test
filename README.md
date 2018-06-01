##一：dscp项目模块划分：

* basic-service:基本数据服务  端口：8081
* drug-service:药品服务  端口：8080
* qcc-service:企业服务  端口：8082
* common-service:公共服务 所有依赖的公共包，通用方法
* medicine-service:医学服务  端口：8083
* search-service:搜索服务  端口：8104
* dscp-ui:前端ui  端口：8090
* user-service:用户服务  端口：8086
* pharmacy-service:药学服务  端口：8084
* discovery-service：自动发现服务  端口：8761
* config-service：统一配置中心服务  端口：8085
* sleuth-service：健康检查链路追踪服务  端口：8087
* getway-service：统一网关服务  端口：8088

* 每个服务对应该的接口放在各自服务模块中

##二：开发前准备

* jdk1.8
* 代码规范检查使用阿里插件
   安装使用文档https://www.cnblogs.com/jiangbei/p/7668654.html





