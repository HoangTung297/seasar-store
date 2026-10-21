import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosClient from '../api/axiosClient';
import './Login.css';
import logo from '../assets/logo.png';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');

        try {
            // Gọi API đến Spring Boot
            const response = await axiosClient.post('/auth/login', {
                email: email,
                password: password
            });

            // Nếu thành công, lưu token vào LocalStorage
            localStorage.setItem('token', response.data.token);

            // Chuyển hướng về trang chủ
            navigate('/');
        } catch (err) {
            // Xử lý lỗi (sai pass, email không tồn tại)
            setError(err.response?.data || 'Đăng nhập thất bại. Vui lòng thử lại!');
        }
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <div className="login-header">
                    <img src={logo} alt="Seasar Store Logo" className="login-logo" />
                    <h2>Đăng Nhập</h2>
                </div>

                {error && <div className="error-message">{error}</div>}

                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <label>Email</label>
                        <input
                            type="email"
                            placeholder="Nhập email của bạn"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label>Mật khẩu</label>
                        <input
                            type="password"
                            placeholder="Nhập mật khẩu"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="login-btn">ĐĂNG NHẬP</button>
                </form>
            </div>
        </div>
    );
};

export default Login;