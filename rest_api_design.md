# Restful API 设计指南


## URI
### 1. 域名

应该尽量将API部署在专用域名之下。

```
https://api.example.com
```

如果确定API很简单，不会有进一步扩展，可以考虑放在主域名下。

```
https://example.org/api/
```

### 2. 版本

应将API的版本号放入URL。

```
https://api.example.com/v1/
```

相对于放入 header 中，URL 里的版本号更加直观。

### 3. 路径

路径又称终点，即endpoint，表示API的具体网址。

在RESTful架构中，每个网址代表一种资源（resource），网址中一般不能有动词，只能有名词。一般来说，每种资源都是一个"集合"（collection），API中的名词也应该使用复数。

举例来说，有一个API提公司的资源，这些资源可能包括部门和雇员，则它的路径应该设计成下面这样。

```
https://api.example.com/v1/departments
https://api.example.com/v1/employees
```

上面这些这个路劲用来表示资源的集合，对于资源集合中的单个资源，我们可以这样表示。

```
https://api.example.com/v1/departments/:id
https://api.example.com/v1/departments/:name
https://api.example.com/v1/employees/:id
```

有些资源还有嵌套关系。比如说，一位员工的手机号码，可以这么表示。

```
https://api.example.com/v1/employees/:id/phone-numbers
```

还有些情况，需要表示一个动作而非资源。那可以通过"/动作"或者"资源/actions/:action-name"来表示。

```
# 执行登录
https://api.example.com/v1/login
# 让一位员工吼叫
https://api.example.com/v1/employees/:id/actions/yell
```

总结一下，路劲的设计应遵循：

* URI 中应只使用小写字母，使用中横线“-”而非下划线“_”

* 资源分为集合与单个，尽量使用复数来表示资源，单个资源通过添加 id 或者 name 等来表示

* 一个资源可以有多个不同的URI对应，比如使用 id 和 name 来表示同一个资源

* 资源可以嵌套，通过类似目录路径的方式来表示，以体现它们之间的关系

* 当需要表示一个动作时，可以采用"/动作"或"资源/actions/:action-name"的方式

## 请求

### 1. HTTP方法

GET：获取资源

```
GET /departments
GET /departments/1
GET /departments/1/employees
```

POST：创建单个资源。POST一般向“资源集合”型uri发起

```
# 创建一个部门
POST /departments
# 为部门1创建一们雇员
POST /departments/1/employees
```

PUT和PATCH：都是用于更新资源。PUT用于更新一个资源的所有信息。要求客户端提供完整的更新后的资源。使用PATCH时则只负责部分更新，客户端提供要更新的那些字段。PUT/PATCH一般向“单个资源”型uri发起

```
# 更新部门1的信息
PUT /departments/1
# 更新部门1员工20的部分信息
PATCH /departments/1/employees/20
```

DELETE：删除资源

```
# 删除部门1下的所有雇员
DELETE /departments/1/employees
# 删除部门1
DELETE /departments/1
```

### 2. 安全性和幂等性

安全性：不会改变资源状态，可以理解为只读的

幂等性：执行1次和执行N次，对资源状态改变的效果是等价的

|.	|安全性	|幂等性|
|---|---|---|
|GET	|√	|√|
|POST|	×|	×|
|PUT|	×|	√|
|PATCH|	×|	√|
|DELETE|	×|	√|
安全性和幂等性均不保证反复请求能拿到相同的response。以 DELETE 为例，第一次DELETE返回200表示删除成功，第二次返回404提示资源不存在，这是允许的。

### 3. 复杂查询

查询可以捎带以下参数：

| 类型 | 示例 |
|----|-----|
|过滤条件|	?type=1&age=16|
|排序|	?sort=age,desc	|
|分页|	?limit=10&offset=3	|

### 4. 身份验证信息

对于有用户身份验证要求的资源，需要客户端在调用时携带用户的身份信息。如采用 access token 的方式，token 可以放在查询条件里：

```
GET /v1/departments?access_token=xxxxxxx HTTP/1.1
Host: api.example.org
Accept: application/json

```

或者是 header 中:

```
GET /v1/departments?access_token=xxxxxxx HTTP/1.1
Host: api.example.org
Accept: application/json
Authorization: Bearer xxxxx

```

### 5. 请求体

对于 POST/PUT/PATCH 这三种可以带有请求体的方法，只用以下三种请求体格式

 * 将数据以 JSON 的格式提交，即Content-Type: application/json

```
POST /login HTTP/1.1
Host: api.example.com
Content-Length: 31
Accept: text/html
Content-Type: application/json

{
    "username": "root",
    "password": "password"
}
```

* 将数据以表单的格式提交，即Content-Type: application/x-www-form-urlencoded 

```
POST /login HTTP/1.1
Host: api.example.com
Content-Length: 31
Accept: text/html
Content-Type: application/x-www-form-urlencoded

username=root&password=passowrd
```

* Content-Type: multipart/form-data; boundary=—-RANDOM_iAkdD3if4I5oO (表单有文件上传时的格式)

### 6. 为内省而提供 Request-Id

为每一个请求响应包含一个Request-Id头，并使用UUID作为该值。通过在客户端、服务器或任何支持的服务上记录该值，它能为我们提供一种机制来跟踪、诊断和调试请求。

## 响应


### 1. 响应体

#### 不要多余的包装

response 的 body 直接就是数据，不要做多余的包装。

比如，当创建一个 department 时，错误示例：

```
{
    "success":true,
    "data":{"id":1,"name":"信息技术部"},
}
```

正确示例：

```
{
    "id": 1,
    "name": "信息技术部"
}
```

#### 分页查询结果

分页查询的结果集需要进行包装，一般采用 paging 字段表示分页信息，采用 data 字段存放查询结果集。

```
{
    "paging":{"limit":10,"offset":0,"total":729},
    "data":[{},{},{}...]
}
```

#### 嵌套化外键关系

使用嵌套对象序列化外键关联，例如:

```
{
    "name": "Kobe Braynt",
    "department" : {
    	"id" : 1
    }
}
```

而不是：
```
{
    "name": "Kobe Braynt",
    "departmentId": 1
}
```

#### 各HTTP方法成功处理后的数据格式
|方法	|response 格式|
|----|----|
|GET|单个对象、集合|
|POST|	新增成功的对象|
|PUT/PATCH|	更新成功的对象|
|DELETE|空 (注: 并非空 JSON 对象“{}”)|

#### 时间格式的约定

时间字段将彩用长整形(即 UTC毫秒数)，客户端自己按需解析


#### 错误处理

发生错误时，不要给客户端2xx 的响应，而应使用对应的http 状态码。且应生成结构化的响体，指明含有能标识错误的代码（给客户端识别）以及错误的消息（给人看）：
```
{
  "timestamp": 1489073010721,
  "status": 404,
  "error": "Not Found",
  "exception": "com.risezhang.api.demo.exception.BookNotFound",
  "message": "No such book!",
  "path": "/v1/books/1"
}
```

### 2. 响应状态码

为每一次的响应返回合适的HTTP状态码。 好的响应应该使用如下的状态码:

#### 2xx: 请求正常处理并返回

* 200 OK: GET请求成功，及DELETE或PATCH同步请求完成，或者PUT同步更新一个已存在的资源
* 201 Created: POST 同步请求完成，或者PUT同步创建一个新的资源
* 202 Accepted: POST，PUT，DELETE，或PATCH请求接收，将被异步处理。响应 body 中应该告诉客户端去哪里查看任务的状态


#### 4xx: 客户端发送的请求有错误
* 400 Bad request: 客户端发送的请求有错误，比如参数校验失败，导致服务端无法处理
* 401 Unauthorized: 未经验证的用户，常见于未登录，或者身份信息无效
* 403 Forbidden: 与401的区别是用户已经认证，但是没有权限访问
* 404 Not Found: 客户端请求的资源不存在。有时为了隐藏细节，在应返回403的情况下 API 也会返回404

#### 5xx: 服务器内部错误
* 500 Internal Server Error: 所有末由 API 端处理的非业务类的异常，都应由统一的异常拦截器进行处理，并返回500错误

## 参考文档

* [Heroku Api reference](https://devcenter.heroku.com/articles/platform-api-reference)
* [HTTP Api design](https://geemus.gitbooks.io/http-api-design/content/en/responses/return-appropriate-status-codes.html)
* [跟着 Github 学习 Restful HTTP API 设计](http://cizixs.com/2016/12/12/restful-api-design-guide)
* [Restful API 的设计规范](https://novoland.github.io/%E8%AE%BE%E8%AE%A1/2015/08/17/Restful%20API%20%E7%9A%84%E8%AE%BE%E8%AE%A1%E8%A7%84%E8%8C%83.html)
* [Microsoft API guidelines](https://github.com/Microsoft/api-guidelines/blob/master/Guidelines.md)
* [Instagram restful API](https://www.instagram.com/developer/endpoints/likes/)
* [ypertext Transfer Protocol (HTTP/1.1)](https://tools.ietf.org/html/rfc7231)