<template>
    <div class="home-container">
        <div class="table-container">
            <!-- 添加新增按钮 -->
            <div class="table-header">
                <el-button type="primary" @click="handleAdd">新增商品</el-button>
            </div>
            
            <el-table :data="products" style="width: 80%; margin: 0;" stripe:true>
                <el-table-column prop="id" label="ID" width="80" />
                <el-table-column prop="name" label="产品名称" width="180" />
                <el-table-column prop="price" label="价格" width="120" />
                <el-table-column prop="description" label="描述" show-overflow-tooltip />
                <el-table-column prop="image" label="图片名称" width="180" />
                <el-table-column prop="perchaseurl" label="商品链接" width="180" />
                <el-table-column label="操作" width="120" align="center">
                <template #default="scope">
                    <el-text
                    type="danger"
                    class="action-link"
                    @click="deleteProduct(scope.row.id)"
                    >删除</el-text>
                    <span class="action-separator"> | </span>
                    <el-text
                    type="primary"
                    class="action-link"
                    @click="updateProduct(scope.row.id)"
                    >编辑</el-text>
                </template>
                </el-table-column>
            </el-table>
        </div>
        
        <!-- 修改对话框标题为动态 -->
        <el-dialog 
            v-model="dialogVisible" 
            :title="editForm.id ? '编辑产品' : '新增产品'"
            width="50%"
        >
            <el-form 
                :model="editForm" 
                label-width="120px"
                :rules="rules"
                ref="editFormRef"
            >
                <el-form-item label="产品名称" prop="name">
                    <el-input v-model="editForm.name" />
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input 
                        v-model="editForm.description"
                        type="textarea"
                        :rows="3"
                    />
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input-number 
                        v-model="editForm.price"
                        :precision="2"
                        :step="0.1"
                        :min="0"
                    />
                </el-form-item>
                <el-form-item label="图片" prop="image">
                    <el-upload
                        class="upload-demo"
                        :headers="{token: user.token}"
                        :action="`http://localhost:8000/uploadImage`"
                        name="file"
                        :show-file-list="false"
                        :on-success="handleUploadSuccess"
                        :before-upload="beforeUpload"
                        :on-error="handleUploadError"
                    >
                        <el-button size="small" type="primary">点击上传图片</el-button>
                        <div v-if="editForm.image" class="uploaded-image">
                            已上传: {{ editForm.image }}
                        </div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="商品链接" prop="purchaseurl">
                    <el-input v-model="editForm.purchaseurl" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitForm">确定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElMessageBox, ElMessage } from 'element-plus'
import { serverUrl } from '@/utils/request'

export default {
    name: 'Products',
    data() {
        return {
            products: [],
            dialogVisible: false,
            editForm: {
                id: '',
                name: '',
                description: '',
                price: 0,
                purchaseurl: ''
            },
            user: JSON.parse(localStorage.getItem('honey-user')) || {},
            
            rules: {
                name: [
                    { required: true, message: '请输入产品名称', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: '请输入产品描述', trigger: 'blur' }
                ],
                price: [
                    { required: true, message: '请输入价格', trigger: 'blur' }
                ],
                purchaseurl: [
                    { required: false, message: '请输入商品链接', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        formatPrice(price) {
            return `￥${price.toFixed(2)}`
        },
        formatDate(date) {
            return new Date(date).toLocaleString('zh-CN')
        },
        deleteProduct(id) {
            ElMessageBox.confirm(
                '确认删除该产品吗？',
                '警告',
                {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }
            ).then(() => {
                this.$request.delete(`/product/${id}`)
                    .then(() => {
                        this.products = this.products.filter(product => product.id !== id);
                        this.$message.success('删除成功');
                        // 刷新页面数据
                        this.loadProducts();
                    })
                    .catch(error => {
                        this.$message.error('删除失败: ' + error.message);
                    });
            }).catch(() => {
                this.$message.info('已取消删除');
            });
        },
        updateProduct(id) {
            const product = this.products.find(p => p.id === id);
            this.editForm = { ...product };
            this.dialogVisible = true;
        },
        // 添加新增方法
        handleAdd() {
            this.editForm = {
                id: '',
                name: '',
                description: '',
                price: 0,
                purchaseurl: ''
            };
            this.dialogVisible = true;
        },
        // 修改提交表单方法以支持新增和编辑
        submitForm() {
            this.$refs.editFormRef.validate((valid) => {
                if (valid) {
                    const isEdit = !!this.editForm.id;
                    const request = isEdit 
                        ? this.$request.put('/product', this.editForm)
                        : this.$request.post('/product', this.editForm);
                        
                    request.then(response => {
                        if (response.code === 200) {
                            ElMessage.success(isEdit ? '更新成功' : '新增成功');
                            this.dialogVisible = false;
                            this.loadProducts();
                        } else {
                            ElMessage.error((isEdit ? '更新' : '新增') + '失败: ' + response);
                        }
                    }).catch(error => {
                        ElMessage.error((isEdit ? '更新' : '新增') + '失败: ' + error.message);
                    });
                }
            });
        },
        loadProducts() {
            this.$request.get('/products')
                .then(response => {
                    this.products = response;
                })
                .catch(error => {
                    this.$message.error('加载数据失败: ' + error.message);
                });
        },
        // 修改上传成功处理方法
        async handleUploadSuccess(response, file) {
            if (response.code === 200) {
                // 从路径中提取文件名
                const fileName = response.data.split('\\').pop();
                this.editForm.image = fileName;
                ElMessage.success('图片上传成功');
            } else {
                ElMessage.error(response.msg || '上传失败');
            }
        },

        beforeUpload(file) {
            const isImage = file.type.startsWith('image/');
            const isLt10M = file.size / 1024 / 1024 < 10;

            if (!isImage) {
                ElMessage.error('只能上传图片文件!');
                return false;
            }
            if (!isLt10M) {
                ElMessage.error('图片大小不能超过 10MB!');
                return false;
            }
            return true;
        },

        handleUploadError(error) {
            ElMessage.error('上传失败: ' + (error.message || '未知错误'));
        }
    },
    mounted(){
        this.loadProducts(); // 使用新方法加载数据
    }
}
</script>

<style scoped>
.home-container {
    padding: 20px;
}

.greeting {
    font-size: 24px;
    color: #333;
    margin: 20px;
}
.action-link {
  cursor: pointer;
}
.dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}
.table-container {
    width: 100%;
    margin: 0;
}

.table-header {
    margin: 0px 80%;
    display: flex;
    justify-content: flex-end;
}
.uploaded-image {
    margin-top: 10px;
    font-size: 14px;
    color: #606266;
}
</style>