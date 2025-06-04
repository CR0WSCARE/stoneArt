import axios from "axios";

const request = axios.create({
    baseURL: 'http://localhost:8000',
    timeout: 30000
})

request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=UTF-8';
    return config;
}, error => {
    console.error('Request error:', error);
    return Promise.reject(error);
});

request.interceptors.response.use(
    response => {
        let res = response.data;
        
        // 如果响应是字符串，尝试解析为JSON
        if (typeof res === 'string') {
            try {
                res = JSON.parse(res);
            } catch (e) {
                console.error('JSON解析失败:', e);
                return res;
            }
        }
        
        // 直接返回响应数据，不进行额外解析
        return res;
    },
    error => {
        console.error('Response error:', error);
        return Promise.reject(error);
    }
)

export default request;