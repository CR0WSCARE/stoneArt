import axios from "axios";

// 创建请求实例
const request = axios.create({
    baseURL: 'http://localhost:8000', // 默认值
    timeout: 30000
});

// 更新配置的函数
const updateConfig = async () => {
    try {
        const response = await axios.get('/config.json');
        if (response.data && response.data.serverUrl) {
            request.defaults.baseURL = response.data.serverUrl;
            if (response.data.timeout) {
                request.defaults.timeout = response.data.timeout;
            }
        }
    } catch (error) {
        console.error('配置文件加载失败:', error);
    }
};

// 初始化配置
updateConfig();

// 请求拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=UTF-8';
    let user = JSON.parse(localStorage.getItem("honey-user")||'{}');
    config.headers['token'] = user.token // 设置请求头
    return config;
},error=>{
    console.error('Request error:', error);
    return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(
    response=>{
        let res = response.data;

        // 如果响应是字符串，尝试解析为JSON
        if(typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // 直接返回响应数据，不进行额外解析
        return res;
    },
    error => {
        console.error('Response parse error:', error);
        return Promise.reject(error);
    }
)

export default request