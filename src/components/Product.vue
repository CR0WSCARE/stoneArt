<template>
  <div class="product">
    <div class="product-image">
      <img :src="product.image" :alt="product.name" v-if="product.image">
    </div>
    <div class="product-info">
      <h1>{{ product.name }}</h1>
      <p>{{ product.description }}</p>
      <p class="price">价格: {{ product.price }}</p>
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
  }
};
</script>

<style scoped>
.product {
  border: 1px solid #eee;
  padding: 16px;
  margin: 16px 0;
  display: flex;
  align-items: start;
  gap: 20px;
}

.product-image {
  flex: 0 0 300px;
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

.price {
  font-size: 1.2em;
  color: #e53935;
  font-weight: bold;
}
</style>