<template>
  <div class="page-container">
    <p>这里展示我们的精品石雕作品。</p>
    <div class="products-container">
      <Product v-for="product in products" :key="product.id" :product="product" />
    </div>
  </div>
</template>

<script>
import Product from '@/components/Product.vue'
import axios from 'axios';

export default {
  name: 'Products',
  components: {
    Product
  },
  data() {
    return {
      products: []
    }
  },
  mounted(){
    axios.get('http://localhost:8081/products')
        .then(res=>{
            console.log(res.data);
        })
  }
}
</script>