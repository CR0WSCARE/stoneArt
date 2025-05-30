# 🛒 商品展示系统 - Spring Boot + Vue + MyBatis 项目方案

---

## 🏗️ 系统架构图
┌────────────────────────────┐
│ 前端 Vue                    │
│                            │
│ 商品列表页、详情页、搜索栏   │
│ Element UI、Axios          │
└──────────┬─────────────────┘
│ RESTful API
┌──────────▼─────────────────┐
│ 后端 Spring Boot            │
│                            │
│ Controller：接口处理        │
│ Service：业务逻辑 │
│ Mapper：MyBatis数据库操作 │
│ Entity：Product、Category │
└──────────┬─────────────────┘
│
┌──────────▼────────┐
│ 数据库 MySQL │
│ product 表、category 表 │
└─────────────────────┘

pgsql
复制
编辑

---

## 🛢️ 数据库建表SQL
```sql
CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    category_id INT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
🗂️ 最小骨架结构
css
复制
编辑
product-display-system/
├── backend/ （Spring Boot）
│   ├── src/main/java/com/example/productdisplay/
│   │   ├── controller/
│   │   │   └── ProductController.java
│   │   ├── service/
│   │   │   └── ProductService.java
│   │   ├── mapper/
│   │   │   └── ProductMapper.java
│   │   ├── entity/
│   │   │   ├── Product.java
│   │   │   └── Category.java
│   │   └── ProductDisplayApplication.java
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── mapper/ProductMapper.xml
│   └── pom.xml
├── frontend/ （Vue 3 + Vite）
│   ├── src/
│   │   ├── views/
│   │   │   ├── ProductList.vue
│   │   │   └── ProductDetail.vue
│   │   ├── components/
│   │   │   └── ProductCard.vue
│   │   ├── api/
│   │   │   └── product.js
│   │   ├── router/
│   │   │   └── index.js
│   │   └── App.vue
│   └── vite.config.js
└── README.md
🎨 前端页面原型图
css
复制
编辑
┌────────────────────────────────────────────┐
│                  首页                       │
├────────────────────────────────────────────┤
│ 分类选择： [全部] [数码] [服装]             │
│ 搜索框： [请输入关键字] [搜索按钮]          │
├────────────────────────────────────────────┤
│ [商品卡片1]  [商品卡片2]  [商品卡片3] ...   │
│ （图片+标题+价格）                          │
│                                            │
│ 分页：[上一页] [1] [2] [3] [下一页]         │
└────────────────────────────────────────────┘

┌────────────────────────────────────────────┐
│              商品详情页                     │
├────────────────────────────────────────────┤
│ 商品图片                                    │
│ 商品名称                                    │
│ 价格                                        │
│ 描述                                        │
│ 返回按钮                                    │
└────────────────────────────────────────────┘
🚀 开发步骤建议
搭建 Spring Boot 项目，创建 MyBatis 接口，设计数据库表

实现 RESTful 接口，使用 Postman 测试

搭建 Vue 项目，设计商品列表页面，用 Axios 请求后端接口

添加商品详情页和分类筛选功能

（可选）扩展后台管理功能（商品CRUD）

（可选）分页功能、图片上传、简单登录控制

🎁 可选扩展
图片上传（本地或OSS）

登录功能（JWT，简单验证）

后台管理系统（独立路由）