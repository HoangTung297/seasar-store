import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axiosClient from '../api/axiosClient';
import './Home.css';
import logo from '../assets/logo.png';

const Home = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            const response = await axiosClient.get('/products/page?page=0&size=12');
            setProducts(response.data.content || []);
            setLoading(false);
        } catch (error) {
            console.error("Lỗi khi tải sản phẩm:", error);
            setLoading(false);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const formatPrice = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    return (
        <div className="home-container">
            {/* 1. Navbar */}
            <header className="navbar">
                <div className="nav-logo">
                    <Link to="/">
                        <img src={logo} alt="Seasar Store" />
                    </Link>
                </div>

                <nav className="nav-menu">
                    <Link to="/" className="nav-item active">Trang Chủ</Link>
                    <Link to="/products" className="nav-item">Sản Phẩm</Link>
                    <Link to="/shoes" className="nav-item">Giày Bóng Rổ</Link>
                    <Link to="/accessories" className="nav-item">Phụ Kiện</Link>
                    <Link to="/about" className="nav-item">Giới Thiệu</Link>
                </nav>

                <div className="nav-links">
                    <button className="cart-btn" onClick={() => navigate('/cart')}>
                        <span className="cart-icon">🛒</span> Giỏ hàng (0)
                    </button>
                    <button className="logout-btn" onClick={handleLogout}>Đăng xuất</button>
                </div>
            </header>

            {/* 2. Hero Banner Khổ lớn */}
            <section className="hero-banner">
                <div className="hero-content">
                    <h1>Bứt Phá Mọi Giới Hạn</h1>
                    <p>Khám phá bộ sưu tập thời trang và phụ kiện bóng rổ đỉnh cao nhất mùa giải. Thiết kế để bạn tỏa sáng trên mọi mặt sân.</p>
                    <button className="shop-now-btn">Mua Sắm Ngay</button>
                </div>
            </section>

            {/* 3. Ưu điểm Dịch vụ (Features) */}
            <section className="features-section">
                <div className="feature-box">
                    <span className="feature-icon">🏀</span>
                    <h3>Chính Hãng 100%</h3>
                    <p>Cam kết chất lượng đỉnh cao từ các thương hiệu hàng đầu.</p>
                </div>
                <div className="feature-box">
                    <span className="feature-icon">⚡</span>
                    <h3>Giao Hàng Tốc Độ</h3>
                    <p>Nhận hàng chỉ trong 24h đối với khu vực nội thành.</p>
                </div>
                <div className="feature-box">
                    <span className="feature-icon">🛡️</span>
                    <h3>Đổi Trả Dễ Dàng</h3>
                    <p>Hỗ trợ đổi size và mẫu miễn phí trong vòng 7 ngày.</p>
                </div>
            </section>

            {/* 4. Giới thiệu Cửa hàng (About Us) */}
            <section className="about-section">
                <div className="about-image">
                    <img src="https://images.unsplash.com/photo-1542652694-40abf526446e?q=80&w=2070&auto=format&fit=crop" alt="Về Seasar Store" />
                </div>
                <div className="about-content">
                    <h2>Về Seasar Store</h2>
                    <div className="divider"></div>
                    <p>
                        Đam mê bóng rổ chảy trong huyết quản. Seasar Store được thành lập với sứ mệnh mang đến những trang bị tốt nhất cho cộng đồng baller Việt Nam.
                    </p>
                    <p>
                        Dù bạn là một tay ném phong trào dạo chơi trên sân bê tông mỗi chiều, hay một vận động viên thi đấu chuyên nghiệp, chúng tôi hiểu rõ bạn cần gì để đôi chân luôn nhẹ nhàng và những cú ném luôn chuẩn xác. Hãy để Seasar đồng hành cùng đam mê của bạn!
                    </p>
                </div>
            </section>

            {/* 5. Khu vực Sản phẩm */}
            <main className="product-section">
                <div className="section-header">
                    <h2>Hàng Mới Lên Kệ</h2>
                    <p>Những item đang được các baller săn lùng nhiều nhất</p>
                </div>

                {loading ? (
                    <div className="loading">
                        <div className="spinner"></div>
                        <p>Đang tải dữ liệu...</p>
                    </div>
                ) : (
                    <div className="product-grid">
                        {products.map((product) => (
                            <div className="product-card" key={product.id}>

                                {/* Link bao bọc ảnh và thông tin để chuyển sang trang chi tiết */}
                                <Link to={`/product/${product.id}`} style={{textDecoration: 'none', color: 'inherit'}}>
                                    <div className="product-image-container">
                                        <img
                                            src={product.images && product.images.length > 0 ? product.images[0] : 'https://images.unsplash.com/photo-1574722872669-760af2080205?q=80&w=1964&auto=format&fit=crop'}
                                            alt={product.name}
                                            className="product-image"
                                        />
                                        <div className="category-badge">MỚI</div>
                                    </div>

                                    <div className="product-info" style={{ paddingBottom: '10px' }}>
                                        <h3 className="product-name">{product.name}</h3>
                                        <div className="product-price-row">
                                            <span className="product-price">{formatPrice(product.basePrice)}</span>
                                        </div>
                                    </div>
                                </Link>

                                {/* Nút thêm giỏ hàng được tách ra ngoài Link */}
                                <div style={{ padding: '0 25px 25px 25px' }}>
                                    <button
                                        className="add-to-cart-btn"
                                        onClick={(e) => {
                                            e.preventDefault(); // Ngăn sự kiện click lan truyền
                                            alert('Tính năng giỏ hàng sẽ được triển khai ở bước sau!');
                                        }}
                                    >
                                        Thêm vào giỏ
                                    </button>
                                </div>

                            </div>
                        ))}
                    </div>
                )}
            </main>

            {/* 6. Footer */}
            <footer className="footer">
                <div className="footer-content">
                    <div className="footer-column brand-column">
                        <img src={logo} alt="Logo" className="footer-logo" />
                        <p>Hệ thống phân phối giày và phụ kiện bóng rổ chính hãng hàng đầu Việt Nam. Tự hào đồng hành cùng đam mê của bạn trên mọi mặt sân.</p>
                        <div className="social-icons">
                            <span className="social-icon">📘</span>
                            <span className="social-icon">📸</span>
                            <span className="social-icon">🎵</span>
                            <span className="social-icon">▶️</span>
                        </div>
                    </div>

                    <div className="footer-column">
                        <h4>Về Chúng Tôi</h4>
                        <ul>
                            <li><Link to="/about">Câu chuyện thương hiệu</Link></li>
                            <li><Link to="/stores">Hệ thống cửa hàng</Link></li>
                            <li><Link to="/careers">Tuyển dụng</Link></li>
                            <li><Link to="/contact">Liên hệ</Link></li>
                        </ul>
                    </div>

                    <div className="footer-column">
                        <h4>Hỗ Trợ Khách Hàng</h4>
                        <ul>
                            <li><Link to="/policy/shipping">Chính sách vận chuyển</Link></li>
                            <li><Link to="/policy/returns">Chính sách đổi trả</Link></li>
                            <li><Link to="/policy/warranty">Chính sách bảo hành</Link></li>
                            <li><Link to="/faq">Câu hỏi thường gặp</Link></li>
                        </ul>
                    </div>

                    <div className="footer-column contact-column">
                        <h4>Thông Tin Liên Hệ</h4>
                        <p>📍 Đại học Xây dựng Hà Nội</p>
                        <p>📞 Hotline: <strong>0912.345.678</strong></p>
                        <p>✉️ Email: cskh@seasarstore.com</p>
                        <p>⏰ Mở cửa: 8:00 - 22:00 (Cả tuần)</p>
                    </div>
                </div>
                <div className="footer-bottom">
                    <div className="footer-bottom-content">
                        <p>&copy; 2026 Seasar Store. All rights reserved.</p>
                        <div className="payment-methods">
                            <span>💳 Visa</span>
                            <span>💳 MasterCard</span>
                            <span>💵 VNPay</span>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    );
};

export default Home;