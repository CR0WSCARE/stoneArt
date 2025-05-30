<template>
    <h1 class="gradient-title">永信石雕</h1>
    <TabPanel :tabs="tabs" v-model="currentTab">
        <div v-if="currentTab === 0">
            <p>欢迎来到永信石雕的官方网站！</p>
        </div>
        <div v-if="currentTab === 1">
            <p>永信石雕专注于传统石雕艺术的传承与创新。</p>
        </div>
        <div v-if="currentTab === 2">
            <p>这里展示我们的精品石雕作品。</p>
            <div class="products-container">
                <Product v-for="product in products" :key="product.id" :product="product" />
            </div>
        </div>
        <div v-if="currentTab === 3">
            <p>如需了解更多信息，请联系我们。</p>
            <p>拼多多店铺：<a href="https://mobile.yangkeduo.com/mall_page.html?mall_id=762651622" target="_blank">永信石雕</a></p>
        </div>
    </TabPanel>
</template>

<script>
import TabPanel from '@/components/TabPanel.vue'
import Product from '@/components/Product.vue'

export default {
    name: 'Index',
    components: {
        TabPanel,
        Product
    },
    data() {
        return {
            currentTab: 0,
            tabs: [
                { title: '首页' },
                { title: '关于' },
                { title: '产品' },
                { title: '联系我们' }
            ],
            products: [
                {
                    id: 1,
                    name: '精品石雕',
                    description: '精美的手工石雕作品',
                    price: '￥999',
                    image: require('@/assets/logo.png'),
                    purchaseUrl: 'https://mobile.yangkeduo.com/mall_page.html?mall_id=762651622'
                },
                {
                    id: 2,
                    name: '传统石雕',
                    description: '传承千年的传统石雕艺术',
                    price: '￥1999',
                    image: require('@/assets/logo.png'),
                    purchaseUrl: 'https://www.baidu.com'
                },
                {
                    id: 3,
                    name: '现代石雕',
                    description: '结合现代艺术的创新石雕',
                    price: '￥2999',
                    image: require('@/assets/logo.png'),
                    purchaseUrl: 'https://mobile.yangkeduo.com/mall_page.html?mall_id=762651622'
                }
            ]
        }
    }
}
</script>

<style scoped>
.products-container {
    display: flex;
    flex-wrap: wrap;
    margin: -16px;
    overflow: hidden;
}
.gradient-title {
    color: #2193b0;
    font-size: 2.5em;
    text-align: center;
    margin-bottom: 20px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}
</style>