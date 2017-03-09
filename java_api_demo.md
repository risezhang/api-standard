# JAVA Api Demo

[项目地址：src/api-demo](src/api-demo)

## 前置要求

1. JDK 1.8
2. Maven

## 介绍

这个 Api Demo 项目构建于 Spring Boot 最新的 GA 版本。它演示了如何通过 Spring Boot 以Restful API 的形式对外提供服务。

Demo 项目中演示的内容有：

1. 使用 Spring MVC 作为 controller 层
2. 使用 Spring Data JPA 作为持久层，实体的定义以及 Repository 层的构建
3. 向 controller 层提供服务的 Service 层的构建
4. 自定义业务异常类
5. 使用Swagger编写 API 文档并通过 Swagger 提供的 web 界面进行测试
6. 使用 H2 做为开发用数据库，以及使用 WEB 工具对期进行管理
6. 使用 Logback 作为日志记录工具

计划将添加的功能包括：

1. 用户身份的认证
2. 外部基于 Http 协议的服务的调用
3. 来自客户端的Request-Id的处理

## 运行

```
cd src/api-demo/
mvn clean package
java -jar ./target/api.demo-0.0.1-SNAPSHOT.jar
```

或者，如果有 make 的话

```
make package
make run
```

## 访问
默认运行于8080端口，运行后可以通过以下地址访问：

* [API文档以及在线调试工具](http://localhost:8080/swagger-ui.html)
* [H2数据库 WEB 控制台](http://localhost:8080/console)