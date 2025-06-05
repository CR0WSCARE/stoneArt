<template>
    <div class="admin-layout">
        <header class="admin-header">
            <h1>后台管理</h1>
        </header>
        <div class="admin-content">
            <div class="sidebar">
                <router-link 
                    to="/home/admin1" 
                    class="link" 
                    active-class="active"
                >永信石雕产品管理</router-link>
            </div>
            <div class="main-content">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AdminLayout',
    data() {
        return {
        };
    },
    mounted() {
        // 添加关闭窗口事件监听
        window.addEventListener('beforeunload', this.handleBeforeUnload);
    },
    unmounted() {
        // 移除事件监听
        window.removeEventListener('beforeunload', this.handleBeforeUnload);
        this.clearLoginStatus();
    },
    methods: {
        handleBeforeUnload() {
            this.clearLoginStatus();
        },
        clearLoginStatus() {
            localStorage.removeItem('honey-user');
        }
    }
};
</script>

<style scoped>
.admin-layout {
    display: flex;
    flex-direction: column;
    height: 100vh;
}

.admin-header {
    background-color: #f5f5f5;
    padding: 1rem;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.admin-content {
    display: flex;
    flex: 1;
    overflow: hidden;
}

.sidebar {
    width: 200px;
    background-color: #fff;
    padding: 1rem;
    border-right: 1px solid #eee;
}

.main-content {
    flex: 1;
    padding: 1rem;
    overflow-y: auto;
}

.link {
    text-decoration: none;
    color: #333;
    font-size: 16px;
    padding: 8px 16px;
    border-radius: 4px;
    transition: all 0.3s;
    display: block;
    border-left: 3px solid transparent;
}

.link:hover {
    background-color: #f0f0f0;
    color: #000;
}

.link.active {
    background-color: #e6f7ff;
    color: #1890ff;
    border-left-color: #1890ff;
    font-weight: 500;
}
</style>