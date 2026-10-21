import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axiosClient from '../api/axiosClient';
import './ProductDetail.css';
import logo from '../assets/logo.png'; // Tái sử dụng logo cho Navbar đơn giản

const ProductDetail = () => {
    const { id } = useParams(); // Lấy ID sản phẩm từ trên thanh URL
    const navigate = useNavigate();

    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [mainImage, setMainImage] = useState(''); // Ảnh đang hiển thị to

    // Lưu trạng thái người dùng chọn size/màu
    const [selectedSize, setSelectedSize] = useState('');
    const [selectedColor, setSelectedColor] = useState('');

    useEffect(() => {
        fetchProductDetail();
    }, [id]);

    const fetchProductDetail = async () => {
        try {
            const response = await axiosClient.get(`/products/${id}`);
            const data = response.data;
            setProduct(data);

            // Set ảnh mặc định là ảnh đầu tiên trong mảng images
            if (data.images && data.images.length > 0) {
                setMainImage(data.images[0]);
            }
            setLoading(false);
        } catch (error) {
            console.error("Lỗi khi tải chi tiết sản phẩm:", error);
            setLoading(false);
        }
    };

    const formatPrice = (price) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    if (loading) return <div className="loading-detail">Đang tải thông tin sản phẩm...</div>;
    if (!product) return <div className="loading-detail">Không tìm thấy sản phẩm!</div>;

    // Lọc ra danh sách Size và Màu độc nhất từ mảng variants để render nút bấm
    const availableSizes = [...new Set(product.variants?.map(v => v.size))];
    const availableColors = [...new Set(product.variants?.map(v => v.color))];

    return (
        <div className="product-detail-page">
            {/* Thanh Navbar thu gọn */}
            <header className="detail-navbar">
                <img src={logo} alt="Logo" className="detail-logo" onClick={() => navigate('/')} style={{cursor: 'pointer'}} />
                <button className="back-btn" onClick={() => navigate('/')}>🔙 Quay lại Cửa hàng</button>
            </header>

            <div className="detail-container">
                {/* Cột trái: Thư viện Ảnh */}
                <div className="detail-gallery">
                    <div className="main-image-box">
                        <img src={mainImage || 'https://via.placeholder.com/600'} alt={product.name} />
                    </div>
                    <div className="thumbnail-list">
                        {product.images?.map((imgUrl, index) => (
                            <img
                                key={index}
                                src={imgUrl}
                                alt={`Thumb ${index}`}
                                className={`thumbnail ${mainImage === imgUrl ? 'active-thumb' : ''}`}
                                onClick={() => setMainImage(imgUrl)} // Bấm vào ảnh nhỏ -> Đổi ảnh to
                            />
                        ))}
                    </div>
                </div>

                {/* Cột phải: Thông tin & Đặt hàng */}
                <div className="detail-info">
                    <span className="detail-category">Mã SP: {product.id}</span>
                    <h1 className="detail-name">{product.name}</h1>
                    <p className="detail-price">{formatPrice(product.basePrice)}</p>

                    <div className="detail-description">
                        <p>{product.description}</p>
                    </div>

                    <div className="variant-section">
                        {/* Chọn Màu sắc */}
                        <div className="variant-group">
                            <h4>Màu sắc: {selectedColor || '(Chưa chọn)'}</h4>
                            <div className="variant-options">
                                {availableColors.map(color => (
                                    <button
                                        key={color}
                                        className={`variant-btn ${selectedColor === color ? 'selected' : ''}`}
                                        onClick={() => setSelectedColor(color)}
                                    >
                                        {color}
                                    </button>
                                ))}
                            </div>
                        </div>

                        {/* Chọn Kích cỡ */}
                        <div className="variant-group">
                            <h4>Kích cỡ (Size): {selectedSize || '(Chưa chọn)'}</h4>
                            <div className="variant-options">
                                {availableSizes.map(size => (
                                    <button
                                        key={size}
                                        className={`variant-btn size-btn ${selectedSize === size ? 'selected' : ''}`}
                                        onClick={() => setSelectedSize(size)}
                                    >
                                        {size}
                                    </button>
                                ))}
                            </div>
                        </div>
                    </div>

                    <div className="action-buttons">
                        <button className="add-cart-massive-btn">
                            🛒 THÊM VÀO GIỎ HÀNG
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductDetail;