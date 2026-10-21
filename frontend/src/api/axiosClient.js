import axios from 'axios';

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080/api', // Trỏ thẳng vào Backend Spring Boot
    headers: {
        'Content-Type': 'application/json',
    },
});

// Tự động đính kèm Token vào request trước khi gửi lên Server
axiosClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token'); // Lấy JWT từ LocalStorage của trình duyệt
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosClient;