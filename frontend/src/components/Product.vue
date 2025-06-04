<template>
  <div class="product">
    <div class="product-image">
      <img 
        :src="imageUrl" 
        :alt="product.name"
      />
    </div>
    <div class="product-info">
      <h1>{{ product.name }}</h1>
      <p>{{ product.description }}</p>
      <p class="price">价格: {{ product.price }}</p>
      <button @click="goToPurchasePage">前去购买</button>
    </div>
  </div>
</template>

<script>

export default {
  name: "Product",
  props: {
    product: {
      type: Object,
      required: true
    },
    defaultImage: {
      type: String,
      default: require("@/assets/images/logo.png") // 默认图片路径
    }
  },
  computed: {
    imageUrl() {
      // 检查图片URL是否存在且有效
      if (this.product.image) {
        return `http://localhost:8000/productImage/${this.product.image}`;
      }
      return this.defaultImage
    }
  },
  methods: {
    goToPurchasePage() {
      // 这里添加跳转到购买页面的逻辑
      window.location.href = this.product.purchaseUrl || "https://mobile.yangkeduo.com/mall_page.html?mall_id=762651622"; // 使用产品的购买链接或默认链接
    }
  }
};
</script>

<style scoped>
.product {
  border: 1px solid #eee;
  padding: 16px;
  margin: 16px;
  width: calc(50% - 32px);
  box-sizing: border-box;
  display: flex;
  flex-direction: row; /* 改为横向排列 */
  align-items: flex-start; /* 顶部对齐 */
  transition: all 0.3s ease;
  gap: 16px; /* 添加间距 */
  background: rgba(255, 255, 255, 0.0); /* 添加半透明背景 */
  backdrop-filter: blur(5px); /* 添加毛玻璃效果 */
  border-radius: 8px; /* 添加圆角 */
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden; /* 防止内容溢出 */
}

.product:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.5);
  transform: translateY(-2px);
}

.product-image {
  width: 40%; /* 调整图片区域宽度 */
  flex-shrink: 0; /* 防止图片区域被压缩 */
}

.product-image img {
  width: 100%;
  height: 150px; /* 调整图片高度 */
  object-fit: cover;
  border-radius: 8px;
}

.product-info {
  width: 60%; /* 调整信息区域宽度 */
  text-align: left; /* 左对齐文本 */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 150px; /* 与图片高度保持一致 */
}

.product-info h1 {
  font-size: 2em;
  margin-bottom: 8px;
  text-align: center; /* 标题居中 */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #333;
}

.product-info p {
  margin: 4px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  height: 36px;
  font-size: 0.9em; /* 减小描述文字大小 */
  color: #666; /* 使描述文字颜色更浅 */
  line-height: 1.4;
}

.product-info .price {  /* 增加特异性 */
  font-size: 1.1em;
  color: #e53935 !important;  /* 添加 !important 确保样式生效 */
  font-weight: bold;
  margin: 8px 0;
  text-align: center;  /* 价格居中显示 */
}

button {
  width: 60%;
  align-self: center;
  padding: 6px 12px;
  background-color: #e53935;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-size: 0.9em;
}

button:hover {
  background-color: #c62828;
}
</style>