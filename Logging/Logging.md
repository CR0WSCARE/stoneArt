# 后台管理开发日志

## <font color=green> <strong>day1</strong> </font>

### 工作内容


1. 设计后端数据库；
2. 编写后端post、get、delete等方法；


***

### 问题


1. 当类名和数据库表名不一致时，无法进行操作；
2. 当类内变量和字段名不一致时，操作错误；


***

### 解决


1. 使用<font color=yellow>@TableName(name = "tableName")</font>注解可以映射表名;
2. 使用<font color=yellow>@TableField(name = "tableField")</font>注解可以映射字段名;
3. 另外，每个类必须有<font color=yellow>@TableId()</font>注解来进行Id字段标注；


## <font color=green> <strong>day2</strong> </font>

### 工作内容


1. 完成/index主页面的前端编写；
2. 完成/login管理员登录页面编写；
3. 完成商品组件，将组件嵌入/index；
4. 配置页面路由；


***

### 问题


1. 路由配置后，打开网页url为localhost://8080;


***

### 解决


1. 在路由js里重定向


## <font color=green> <strong>day3</strong> </font>

### 工作内容


1. 将/index主页面拆分成多个组件和界面分别路由；
2. 将/login与原项目分离，将后台管理作为新项目；
3. 编写简单后端登录验证（未完成）；
4. 使用axios链接前后端；
5. 测试前后端连接性；


***

### 问题


1. 后端打开时，前端端口被占用无法启动；
2. 在前后端链接时，由于端口不一致导致的跨域访问错误
    ![跨域访问错误](images\跨域error.png "跨域error")


***

### 解决


1. 更改后端启动端口，在application.properties中添加配置：<code>server.port=8081</code></br>
2. 在要访问的controller前使用<font color=yellow>@CrossOrigin(origins = "http://localhost:8080")</font>
注解；
1. 将前台与后台结构分离有更好的扩展性，且更容易配置与使用；


## <font color=green> <strong>day4</strong> </font>

### 内容

1. 优化前端布局，配置全局request，使后期使用更方便,但考虑到安全性问题，仍推荐使用单个配置;
2. 配置后端全局跨域访问；
    <code>

        // 当前跨域请求最大有效时长。这里默认1天
        private static final long MAX_AGE = 24 * 60 * 60;

        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
            corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
            corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
            corsConfiguration.setMaxAge(MAX_AGE);
            source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
            return new CorsFilter(source);
        }
    </code>
    但考虑到安全性问题，仍采用单个配置;
3. 实现向后端传输数据，另外优化敲击回车时可触发登录按钮；
    <code>@submit.prevent="login"</code> <code>native-type="submit"</code>
4. 利用element-plus实现规则；
    <code>

        rules: {
            username: [
                { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
                { required: true, message: '请输入密码', trigger: 'blur' },
            ],
            captcha: [
                { validator: validateCaptcha, required: true, message: '请输入验证码', trigger: 'blur' }
            ]
        }
    </code>
5. 实现验证码校验；
    <code>

        //验证码校验
        const validateCaptcha = (rule, value, callback) => {
            if(value === '') {
                callback(new Error('请输入验证码'));
            } else if(value!= this.captcha) {
                callback(new Error('验证码错误'));
            } else {
                callback();
            }
        }

        this.$refs.loginForm.validate(valid => {
            if (valid) {
                this.$request.post('/admin/login', this.loginForm).then(res => {
                    console.log(res)
                })
            }
        });

    </code>
6. 主页跳转，显示登陆错误信息，修改验证码刷新机制，修改前后端端口号；
   在vue.config.js中加入
   <code>

        devServer: {
            port: 7000,
            host: '0.0.0.0',
            https: false,
        }
   </code>
   
***

### 问题

1. 配置全局request时，访问数据正常但是返回数据为空
2. 当输入验证码时，会立即显示验证码不正确，且不会再变化，直到正确提交表单；

***

### 解决

1. 在解析时分析返回类型并转换
    <code>

        // 如果响应是字符串，尝试解析为JSON
        if(typeof res === 'string') {
            try {
                res = JSON.parse(res);
            } catch (e) {
                console.error('JSON解析失败:', e);
                return res;
            }
        }

        // 直接返回响应数据，不进行额外解析
        return res;
    </code>
2. 对验证码输入框状态进行管理，每次按登录按钮时进行刷新状态，并在下次输入时清空状态；
   <code>

        <el-form-item label="验证码" prop="captcha">
            <div style="display: flex;height: 32px; width: 210px;">
                <el-input 
                    style="flex: 1;" 
                    v-model="loginForm.captcha"
                    @input="handleCaptchaInput">
                </el-input>
                <div style="flex: 1;">
                    <valid-code @update:value="getCode"/>
                </div>
            </div>
        </el-form-item>


        getCode(code) {
            this.captcha = code;
            this.loginForm.captcha = ''; // 清空输入框
            this.$refs.loginForm.clearValidate('captcha'); // 清除验证码的验证状态
        },
   </code>

## <font color=green> <strong>day5</strong> </font>

### 内容

1. 引用JWT做验证；
2. 创建tokenUtil工具类用于生成token；
3. 创建interceptor拦截器，配置拦截器，取消登录界面拦截；
4. 修改Exception异常；
5. 修改userService来获取登录token；
6. 登陆后存储数据到“honey-user”
7. 若后台没有token即输入url直接进入主页将失败并返回登录界面
   <code>   if(res.code === '401') router.push('/login')    </code>
8. 通过自定义注解@AuthAccess来解除拦截

### 问题

1. 实现校验后，前端登录但是仍然显示“401”；
2. 返回‘401’时，还是可以通过输入url直接访问主页
3. 当关闭管理员页面时，由于登录信息残留，之后便可以通过url输入直接进入
4. 有些方法需要9000可以访问而7000无法访问

### 解决

1. login前端界面登录校验成功后获取后端token数据并储存，
    <code>localStorage.setItem("honey-user",JSON.stringify(res.data))</code>，之后将数据传入request并取出token字段并赋值给请求头部以便后端进行校验。
    <code>

        request.interceptors.request.use(config => {
        config.headers['Content-Type'] = 'application/json;charset=UTF-8';
        let user = JSON.parse(localStorage.getItem("honey-user")||'{}');
        config.headers['token'] = user.token // 设置请求头
        return config;})
    
    </code>
2. 在路由中配置前置护卫
   <code>

        // 添加全局前置守卫
        router.beforeEach((to, from, next) => {
        const user = localStorage.getItem('honey-user')
        
        // 如果访问的不是登录页且没有登录信息
        if (to.path !== '/login' && !user) {
            next('/login')
        } else {
            next()
        }
        })
   </code>
3. 通过
   <code>
   
        unmounted() {
            localStorage.removeItem('honey-user')
        }
   </code>
   来完成对数据的销毁
4. 将类级@CrossOrigin注解转为方法级

## <font color=green> <strong>day6</strong> </font>

### 内容
1. 解决跨域访问问题，重启全局跨域配置；
2. 为了拥有可扩展性，将后台管理拆分
3. 完成管理页面删除商品功能
4. 添加商品更新和新增功能
5. 前端实现上传商品图片
   
### 问题
1. 关于后端get方法，前端7000端口可以访问，而9000端口被阻拦；
2. 编写前端页面时，当点击某个网站的后台时，路由进入新的页面而不是直接在当前页面渲染
3. 请求删除商品时报错<code>java.sql.SQLSyntaxErrorException: Unknown column 'token' in 'field list'</code>
4. 上传图片时出现401
5. 前端无法访问本地文件
6. 每次打开页面只能上传一次图片

### 解决
1. @CrossOrigins注解产生跨域冲突，使用全局配置可解决；
2. 添加子路由而非直接路由
   <code>

        children: [  // 添加子路由配置
            {
                path: 'admin1',  // 注意这里不需要加 '/home'
                name: 'yongxin',
                component: () => import('@/views/admin1.vue'),
            }
        ]
   </code>
3. 原因是user类中定义了token字段，在验证时Mybatisplus会查询数据库user表，但表中并没有user字段，因此报错，只需在token变量前加上@TableField(exist = false)注解就可以不对token进行数据库映射
4. 在上传文件时加上头部token
5. 将文件储存到后端并使用相对路径
6. 将前端上传的<code>:limit:"1"</code>限制删除即可