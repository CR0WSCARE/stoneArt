<template>
  <div class="product">
    <div class="product-image">
      <img :src="product.image" :alt="product.name" v-if="product.image">
    </div>
    <div class="product-info">
      <h1>{{ product.name }}</h1>
      <p>{{ product.description }}</p>
      <p class="price">价格: {{ product.price }}</p>
        <button @click="goToPurchasePage">立即购买</button>
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
    }
  },
  methods: {
    goToPurchasePage() {
      // 这里添加跳转到购买页面的逻辑
      window.location.href = this.product.purchaseUrl || "#"; // 使用产品的购买链接或默认链接
    }
  }
};
</script>

<style scoped>
.product {
  border: 1px solid #eee;
  padding: 16px;
  margin: 16px;
  display: flex;
  align-items: start;
  gap: 20px;
  width: calc(50% - 32px); /* 减去左右margin的值 */
  box-sizing: border-box;
  float: left;
}

.product-image {
  flex: 0 0 150px; /* 减小图片容器的宽度 */
}

.product-image img {
  width: 100%;
  height: auto;
  border-radius: 8px;
  object-fit: cover;
}

.product-info {
  flex: 1;
  text-align: left;
}

.product-info h1 {
  font-size: 1.2em; /* 减小标题大小 */
  margin-bottom: 8px;
}

.price {
  font-size: 1.1em;
  color: #e53935;
  font-weight: bold;
  margin-top: 8px;
}

button {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #e53935;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #c62828;
}
</style>