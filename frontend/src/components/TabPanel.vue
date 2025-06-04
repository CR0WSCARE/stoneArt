<template>
    <div class="tabs-container">
        <div class="tabs-header">
            <div 
                v-for="(tab, index) in tabs" 
                :key="index"
                :class="['tab-item', { active: modelValue === index }]"
                @click="$emit('update:modelValue', index)"
            >
                {{ tab.title }}
            </div>
        </div>
        <div class="tab-content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: 'TabPanel',
    props: {
        tabs: {
            type: Array,
            required: true
        },
        modelValue: {
            type: Number,
            required: true
        }
    },
    emits: ['update:modelValue']
}
</script>

<style scoped>
.tabs-container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
}

.tabs-header {
    display: flex;
    border-bottom: 1px solid #ddd;
    margin-bottom: 20px;
}

.tab-item {
    padding: 10px 20px;
    cursor: pointer;
    border-bottom: 2px solid transparent;
    transition: all 0.3s ease;
}

.tab-item:hover {
    color: #666;
}

.tab-item.active {
    border-bottom: 2px solid #2c3e50;
    color: #2c3e50;
}

.tab-content {
    padding: 20px;
}
</style>