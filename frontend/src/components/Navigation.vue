<template>
  <div class="nav-wrapper">
    <h1 class="main-title" style="color: red; font-family: 'KaiTi', 'STCaiyun', cursive;">永信石雕</h1>
    <div class="nav-container">
      <router-link to="/home" class="nav-item">首页</router-link>
      <router-link to="/about" class="nav-item">关于</router-link>
      <router-link to="/products" class="nav-item">产品</router-link>
      <router-link to="/contact" class="nav-item">联系我们</router-link>
    </div>
  </div>
</template>

<style scoped>
.nav-wrapper {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(5px);
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
}

.main-title {
  text-align: center;
  color: #2193b0;
  font-size: 2.5em;
  margin-bottom: 20px;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
}

.nav-container {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.nav-item {
  text-decoration: none;
  color: #333;
  font-size: 16px;
  padding: 8px 16px;
  border-radius: 4px;
  transition: all 0.3s;
}

.nav-item:hover {
  color: #2193b0;
  background: #f5f5f5;
}

.router-link-active {
  color: #2193b0;
  font-weight: bold;
}
</style>