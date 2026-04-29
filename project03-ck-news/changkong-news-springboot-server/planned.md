# 长空新闻 Spring Boot 后端架构设计

## 1. 总体目标

本项目后端建议按 Spring Boot 常见三层结构设计：

```text
Controller 层：接收 HTTP 请求，做参数绑定和基础校验，返回统一 Result
Service 层：处理业务逻辑，例如登录、注册、权限判断、分页查询
Mapper 层：通过 MyBatis-Plus 访问数据库
```

核心原则：

- Controller 不直接写 SQL，不直接处理复杂业务。
- Service 不关心 HTTP 细节，只处理业务。
- Mapper 只负责数据库操作，不写登录、权限、token 逻辑。
- 请求参数使用 DTO，响应数据使用 VO，数据库实体只对应表结构。

## 2. 推荐包结构

```text
com.ck.it
├── ApplicationMain.java
├── common
│   ├── Result.java
│   ├── ResultCodeEnum.java
│   └── GlobalExceptionHandler.java
├── config
│   ├── MyBatisPlusConfig.java
│   ├── JwtConfig.java
│   └── WebMvcConfig.java
├── controller
│   ├── AuthController.java
│   ├── UserController.java
│   ├── HeadlineController.java
│   └── TypeController.java
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── HeadlineCreateRequest.java
│   ├── HeadlineUpdateRequest.java
│   └── HeadlineQueryRequest.java
├── vo
│   ├── LoginVO.java
│   ├── UserInfoVO.java
│   ├── HeadlinePageVO.java
│   ├── HeadlineDetailVO.java
│   └── PageInfoVO.java
├── pojo
│   ├── NewsUser.java
│   ├── NewsHeadline.java
│   └── NewsType.java
├── service
│   ├── NewsUserService.java
│   ├── NewsHeadlineService.java
│   └── NewsTypeService.java
├── service.impl
│   ├── NewsUserServiceImpl.java
│   ├── NewsHeadlineServiceImpl.java
│   └── NewsTypeServiceImpl.java
├── mapper
│   ├── NewsUserMapper.java
│   ├── NewsHeadlineMapper.java
│   └── NewsTypeMapper.java
└── util
    ├── BcryptUtil.java
    └── JwtUtil.java
```

说明：

- `dto`：前端传给后端的数据，比如登录、注册、发布文章。
- `vo`：后端返回给前端的数据，比如新闻详情、分页列表。
- `pojo`：数据库实体类，只和表结构对应。
- `config`：放拦截器、MyBatis-Plus、JWT 配置等。

## 3. RESTful 接口设计

### 3.1 认证接口

登录、注册属于认证流程，建议单独放到 `AuthController`。

| 功能 | 请求方式 | 路径 | 参数位置 |
| --- | --- | --- | --- |
| 登录 | `POST` | `/api/auth/login` | JSON 请求体 |
| 注册 | `POST` | `/api/auth/register` | JSON 请求体 |
| 获取当前用户信息 | `GET` | `/api/users/me` | 请求头 token |

登录请求：

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "zhangsan",
  "password": "123456_"
}
```

登录响应：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "jwt-token-string"
  }
}
```

注册请求：

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "zhangsan",
  "password": "123456_",
  "nickName": "张三"
}
```

### 3.2 分类接口

| 功能 | 请求方式 | 路径 | 参数位置 |
| --- | --- | --- | --- |
| 查询全部分类 | `GET` | `/api/types` | 无 |

示例：

```http
GET /api/types
```

### 3.3 新闻接口

| 功能 | 请求方式 | 路径 | 参数位置 |
| --- | --- | --- | --- |
| 分页查询新闻 | `GET` | `/api/headlines` | query 参数 |
| 查询新闻详情 | `GET` | `/api/headlines/{hid}` | 路径参数 |
| 发布新闻 | `POST` | `/api/headlines` | JSON 请求体 + token |
| 修改新闻 | `PUT` | `/api/headlines/{hid}` | 路径参数 + JSON 请求体 + token |
| 删除新闻 | `DELETE` | `/api/headlines/{hid}` | 路径参数 + token |
| 查询我的新闻 | `GET` | `/api/users/me/headlines` | query 参数 + token |

分页查询：

```http
GET /api/headlines?keyWords=java&type=1&pageNum=1&pageSize=5
```

新闻详情：

```http
GET /api/headlines/12
```

发布新闻：

```http
POST /api/headlines
Authorization: Bearer jwt-token-string
Content-Type: application/json

{
  "title": "新闻标题",
  "article": "新闻正文",
  "type": 1
}
```

修改新闻：

```http
PUT /api/headlines/12
Authorization: Bearer jwt-token-string
Content-Type: application/json

{
  "title": "修改后的标题",
  "article": "修改后的正文",
  "type": 2
}
```

删除新闻：

```http
DELETE /api/headlines/12
Authorization: Bearer jwt-token-string
```

## 4. Controller 层参数传递规则

### 4.1 `@PathVariable`

用于路径中的资源 ID。

```java
@GetMapping("/api/headlines/{hid}")
public Result<HeadlineDetailVO> detail(@PathVariable Integer hid) {
    return Result.ok(headlineService.detail(hid));
}
```

适用场景：

- 查询某一条新闻：`/api/headlines/{hid}`
- 修改某一条新闻：`/api/headlines/{hid}`
- 删除某一条新闻：`/api/headlines/{hid}`

### 4.2 `@RequestParam`

用于查询条件、分页参数、排序参数。

```java
@GetMapping("/api/headlines")
public Result<PageInfoVO<HeadlinePageVO>> page(
        @RequestParam(defaultValue = "") String keyWords,
        @RequestParam(defaultValue = "0") Integer type,
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "5") Integer pageSize) {
    return Result.ok(headlineService.page(keyWords, type, pageNum, pageSize));
}
```

适用场景：

- `keyWords`
- `type`
- `pageNum`
- `pageSize`

### 4.3 `@RequestBody`

用于 JSON 请求体，适合登录、注册、新增、修改。

```java
@PostMapping("/api/auth/login")
public Result<LoginVO> login(@Valid @RequestBody LoginRequest request) {
    return Result.ok(userService.login(request));
}
```

适用场景：

- 登录：用户名、密码
- 注册：用户名、密码、昵称
- 发布新闻：标题、正文、分类
- 修改新闻：标题、正文、分类

### 4.4 `@RequestHeader`

用于读取请求头。实际项目中不建议每个 Controller 都手动解析 token，推荐用拦截器统一处理。

```java
@GetMapping("/api/users/me")
public Result<UserInfoVO> currentUser(@RequestHeader("Authorization") String authorization) {
    return Result.ok(userService.currentUser(authorization));
}
```

更推荐的方式：

```text
前端请求头携带 token
        ↓
JwtInterceptor 统一解析 token
        ↓
把 uid 放入 request attribute 或 ThreadLocal
        ↓
Controller / Service 直接获取当前登录用户 id
```

## 5. DTO 设计

### 5.1 登录 DTO

```java
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
```

说明：

- 前端登录表单传 `password` 即可。
- 数据库字段可以继续叫 `userPwd`，但接口层不一定要暴露这个数据库字段名。

### 5.2 注册 DTO

```java
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "昵称不能为空")
    private String nickName;
}
```

### 5.3 新闻分页查询 DTO

如果使用 `GET + @RequestParam`，可以不专门建 DTO。

如果想统一封装，也可以使用：

```java
public class HeadlineQueryRequest {
    private String keyWords;
    private Integer type = 0;
    private Integer pageNum = 1;
    private Integer pageSize = 5;
}
```

### 5.4 新闻新增 DTO

```java
public class HeadlineCreateRequest {
    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "正文不能为空")
    private String article;

    @NotNull(message = "新闻分类不能为空")
    private Integer type;
}
```

### 5.5 新闻修改 DTO

```java
public class HeadlineUpdateRequest {
    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "正文不能为空")
    private String article;

    @NotNull(message = "新闻分类不能为空")
    private Integer type;
}
```

## 6. VO 设计

### 6.1 登录返回 VO

```java
public class LoginVO {
    private String token;
}
```

### 6.2 当前用户 VO

```java
public class UserInfoVO {
    private Integer uid;
    private String username;
    private String nickName;
}
```

不要返回 `userPwd`。

### 6.3 新闻列表 VO

```java
public class HeadlinePageVO {
    private Integer hid;
    private String title;
    private Integer type;
    private Integer pageViews;
    private Long pastHours;
    private Integer publisher;
}
```

### 6.4 新闻详情 VO

```java
public class HeadlineDetailVO {
    private Integer hid;
    private String title;
    private String article;
    private Integer type;
    private String typeName;
    private Integer pageViews;
    private Long pastHours;
    private Integer publisher;
    private String author;
}
```

### 6.5 分页 VO

```java
public class PageInfoVO<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Long totalSize;
    private Long totalPage;
    private List<T> pageData;
}
```

## 7. 登录注册流程

### 7.1 登录流程

```text
1. 前端提交 username + password
2. Controller 使用 LoginRequest 接收参数
3. Service 根据 username 查询用户
4. 用户不存在：返回 USERNAME_ERROR
5. 用户存在：使用 BCrypt 校验密码
6. 密码错误：返回 PASSWORD_ERROR
7. 密码正确：生成 JWT token
8. 返回 token 给前端
9. 前端保存 token，后续请求放入 Authorization 请求头
```

### 7.2 注册流程

```text
1. 前端提交 username + password + nickName
2. Controller 使用 RegisterRequest 接收参数
3. Service 查询 username 是否已存在
4. 已存在：返回 USERNAME_USED
5. 不存在：使用 BCrypt 加密密码
6. 保存用户到 news_user 表
7. 返回注册成功
```

### 7.3 token 校验流程

```text
1. 前端请求需要登录的接口
2. 请求头携带 Authorization: Bearer token
3. JwtInterceptor 解析 token
4. token 缺失：返回 NOT_LOGIN
5. token 过期或非法：返回 TOKEN_INVALID
6. token 正常：取出 uid，放入当前请求上下文
7. Controller / Service 根据 uid 做业务处理
```

## 8. Service 层职责

### 8.1 `NewsUserService`

负责：

- 登录校验
- 注册用户
- 根据用户名查询用户
- 根据 token 中的 uid 查询当前用户信息

不负责：

- HTTP 参数绑定
- 直接读取请求头
- 直接返回前端页面

### 8.2 `NewsHeadlineService`

负责：

- 分页查询新闻
- 查询新闻详情
- 发布新闻
- 修改新闻
- 删除新闻
- 判断当前用户是否有权限修改或删除新闻

删除新闻时必须校验：

```text
当前登录 uid == 新闻 publisher
```

否则不能删除或修改。

### 8.3 `NewsTypeService`

负责：

- 查询全部新闻分类

分类通常是字典数据，接口可以保持简单。

## 9. Mapper 层设计

当前项目使用 MyBatis-Plus，可以优先使用 `BaseMapper` 提供的方法。

适合直接使用 MyBatis-Plus 的场景：

- 根据 id 查询
- 根据 username 查询
- 新增用户
- 新增新闻
- 修改新闻
- 逻辑删除新闻

需要自定义 SQL 的场景：

- 新闻分页列表需要联表、计算发布时间差
- 新闻详情需要联表查询分类名称和作者昵称
- 我的新闻列表需要按当前登录用户过滤

建议：

- 简单 CRUD 用 MyBatis-Plus。
- 复杂列表和详情查询写 XML。
- 不要在 Controller 拼 SQL。

## 10. 统一响应格式

建议统一使用：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

成功：

```java
return Result.ok(data);
```

失败：

```java
return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
```

建议错误码：

| code | 含义 |
| --- | --- |
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录 |
| 403 | 无权限 |
| 500 | 服务端错误 |
| 501 | 用户名错误 |
| 502 | 密码错误 |
| 503 | token 过期 |
| 504 | token 非法 |
| 505 | 注册失败 |
| 506 | 用户名已存在 |

注意：前后端要统一错误码含义，不能前端把 `503` 当密码错误，后端又把 `503` 当 token 过期。

## 11. 当前项目需要优先调整的点

### 11.1 Controller 路径

当前不建议继续使用：

```text
GET /user
POST /portal/findPage
POST /headline/deleteHeadline
```

推荐改为：

```text
POST   /api/auth/login
POST   /api/auth/register
GET    /api/headlines
GET    /api/headlines/{hid}
DELETE /api/headlines/{hid}
```

如果前端暂时不想大改，可以短期保留旧接口路径，但内部实现仍然按 DTO、Service、Mapper 分层写。

### 11.2 MyBatis-Plus 配置

需要确认：

```yaml
mybatis-plus:
  type-aliases-package: com.ck.it.pojo
  mapper-locations: classpath*:/com/ck/it/mapper/**/*.xml
```

当前实体包是 `com.ck.it.pojo`，XML 也在 `resources/com/ck/it/mapper` 下，配置要和实际路径一致。

### 11.3 JWT 配置

`application-jwt.yaml` 可以保留：

```yaml
jwt:
  secret-key: changkong-news-secret-key-please-change-to-long-random-string
  expire-millis: 604800000
  issuer: changkong-news
```

注意：

- 真实生产环境不要把真实密钥直接提交到 Git。
- `JwtProperties` 里需要定义 `secretKey`、`expireMillis`、`issuer` 字段。
- 启动类或配置类需要启用 `@ConfigurationProperties`。

### 11.4 密码字段

数据库字段可以叫 `user_pwd`，实体类可以叫 `userPwd`，但是前端接口建议统一传：

```json
{
  "username": "zhangsan",
  "password": "123456_"
}
```

然后 Service 内部再转换成 `NewsUser.userPwd`。

这样接口语义更清楚，也避免把数据库字段命名暴露给前端。

## 12. 推荐落地顺序

第一步：整理基础公共类

```text
Result
ResultCodeEnum
GlobalExceptionHandler
PageInfoVO
```

第二步：整理配置

```text
MyBatis-Plus 配置
JWT 配置
跨域配置
登录拦截器配置
```

第三步：完成用户模块

```text
LoginRequest
RegisterRequest
LoginVO
UserInfoVO
AuthController
UserController
NewsUserService
```

第四步：完成分类模块

```text
TypeController
NewsTypeService
```

第五步：完成新闻模块

```text
HeadlineController
HeadlineCreateRequest
HeadlineUpdateRequest
HeadlinePageVO
HeadlineDetailVO
NewsHeadlineService
NewsHeadlineMapper.xml
```

第六步：再同步前端请求路径。

前端当前使用 `/api` 作为 baseURL，所以后端接口如果统一以 `/api` 开头，要注意不要和 nginx 或 Vite 代理重复拼接。常见做法是：

```text
前端 axios baseURL: /api
后端 Controller 路径: /auth/login、/headlines
最终请求: /api/auth/login、/api/headlines
```

或者：

```text
前端 axios baseURL: /
后端 Controller 路径: /api/auth/login、/api/headlines
最终请求: /api/auth/login、/api/headlines
```

两种方式选一种即可，不要两边都重复加 `/api`。
