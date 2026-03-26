import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Homepage.css";

import { FaCartPlus } from "react-icons/fa";

import bannerImage from "../assets/banner-flower.png";

function Homepage() {

    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/ordering/api/product/list?page=0&size=18")
            .then(res => res.json())
            .then(data => {
                setProducts(data.data.content);
            })
            .catch(err => console.error(err));
    }, []);

    return (
        <div className="homepage">

            {/* CATEGORY BAR */}
            <div className="category-bar">
                <Link to="/products?category=hoa-sinh-nhat">Hoa Sinh Nhật</Link>
                <Link to="/products?category=hoa-khai-truong">Hoa Khai Trương</Link>
                <Link to="/products?category=hoa-cuoi">Hoa Cưới</Link>
                <Link to="/products?category=hoa-tinh-yeu">Hoa Tình Yêu</Link>
                <Link to="/products?category=hoa-chia-buon">Hoa Chia Buồn</Link>
            </div>

            {/* HERO */}
            <div
                className="hero"
                style={{ backgroundImage: `url(${bannerImage})` }}
            >
                <div className="hero-overlay">
                    <div className="hero-content">
                        <Link to="/products" className="btn-hero">
                            Xem Ngay →
                        </Link>
                    </div>
                </div>
            </div>

            {/* FEATURES */}
            <div className="features">

                <div className="feature-card">
                    <h3>🌸 Sản phẩm đa dạng</h3>
                    <p>Hơn 1000 mẫu hoa thiết kế sang trọng</p>
                </div>

                <div className="feature-card">
                    <h3>🚚 Giao nhanh 2H</h3>
                    <p>Giao hàng nhanh trong nội thành</p>
                </div>

                <div className="feature-card">
                    <h3>💖 Dịch vụ tận tâm</h3>
                    <p>Hỗ trợ khách hàng 24/7</p>
                </div>

            </div>

            {/* PRODUCT LIST */}
            <div className="product-section">

                <h2>Sản phẩm nổi bật</h2>

                <div className="product-grid">

                    {products.map(product => (

                        <div key={product.productId} className="product-card">

                            <img
                                src={`http://localhost:8080/ordering/images/${product.avatarUrl}`}
                                alt={product.productName}
                            />

                            <h3>{product.productName}</h3>

                            <p>{product.productDescription}</p>

                            <p className="price">
                                {Number(product.productPrice).toLocaleString()} đ
                            </p>

                        </div>

                    ))}

                </div>

            </div>

            {/* ABOUT */}
            <div className="about-section">

                <div className="about-container">

                    {/* IMAGE */}
                    <div className="about-image">
                        <img
                            src="https://images.unsplash.com/photo-1526047932273-341f2a7631f9"
                            alt="Flower Shop"
                        />
                    </div>

                    {/* TEXT */}
                    <div className="about-text">

                        <span className="about-badge">FLOWER SHOP</span>

                        <h2>
                            Nghệ thuật từ <span>hoa tươi</span>
                        </h2>

                        <p>
                            FlowerCorner là shop hoa tươi chuyên cung cấp các mẫu hoa
                            sinh nhật, hoa khai trương, hoa cưới và hoa tình yêu
                            được thiết kế tinh tế bởi các florist chuyên nghiệp.
                        </p>

                        <p>
                            Với nhiều năm kinh nghiệm trong lĩnh vực hoa tươi,
                            chúng tôi luôn cam kết mang đến những bó hoa
                            đẹp nhất và ý nghĩa nhất cho khách hàng.
                        </p>

                        <p>
                            Tất cả hoa đều được nhập trực tiếp từ các nhà vườn
                            tại Đà Lạt và được bảo quản cẩn thận trước khi
                            giao đến tay khách hàng.
                        </p>

                        {/* STATS */}
                        <div className="about-stats">

                            <div className="stat">
                                <h3>1000+</h3>
                                <p>Mẫu hoa</p>
                            </div>

                            <div className="stat">
                                <h3>5000+</h3>
                                <p>Khách hàng</p>
                            </div>

                            <div className="stat">
                                <h3>10+</h3>
                                <p>Năm kinh nghiệm</p>
                            </div>

                        </div>

                    </div>

                </div>

            </div>


            {/* PARTNER */}
            <div className="partner-section">

                <h2 className="partner-title">Nhà phân phối</h2>

                <div className="partner-grid">

                    <div className="partner-card">
                        <img
                            src="https://images.unsplash.com/photo-1490750967868-88aa4486c946"
                            alt="Dalat Flower Farm"
                        />
                        <p>Dalat Flower Farm</p>
                    </div>

                    <div className="partner-card">
                        <img
                            src="https://images.unsplash.com/photo-1501004318641-b39e6451bec6"
                            alt="Saigon Fresh Flower"
                        />
                        <p>Saigon Fresh Flower</p>
                    </div>

                    <div className="partner-card">
                        <img
                            src="https://images.unsplash.com/photo-1509042239860-f550ce710b93"
                            alt="Hanoi Flower Garden"
                        />
                        <p>Hanoi Flower Garden</p>
                    </div>

                    <div className="partner-card">
                        <img
                            src="https://images.unsplash.com/photo-1492724441997-5dc865305da7"
                            alt="Vietnam Flower Market"
                        />
                        <p>Vietnam Flower Market</p>
                    </div>

                </div>

            </div>


            {/* SERVICE */}
            <div className="service-section">

                <div className="service-item">
                    <span>🌸</span>
                    <h3>Hoa tươi 100%</h3>
                    <p>Hoa được nhập mới mỗi ngày.</p>
                </div>

                <div className="service-item">
                    <span>🚚</span>
                    <h3>Giao hàng nhanh</h3>
                    <p>Giao trong 2 giờ nội thành.</p>
                </div>

                <div className="service-item">
                    <span>💳</span>
                    <h3>Thanh toán tiện lợi</h3>
                    <p>Hỗ trợ nhiều phương thức thanh toán.</p>
                </div>

            </div>


            {/* FOOTER */}
            <footer className="footer">

                <div className="footer-grid">

                    <div className="footer-about">
                        <h3>FLOWER CORNER</h3>
                        <p>
                            Chuyên cung cấp hoa tươi cho mọi dịp:
                            sinh nhật, khai trương, cưới hỏi.
                        </p>
                    </div>

                    <div>
                        <h4>Liên hệ</h4>
                        <p>Hotline: 1900 633 045</p>
                        <p>Email: support@flowercorner.vn</p>
                        <p>Hà Nội, Việt Nam</p>
                    </div>

                    <div>
                        <h4>Danh mục</h4>
                        <p>Hoa sinh nhật</p>
                        <p>Hoa khai trương</p>
                        <p>Hoa cưới</p>
                        <p>Hoa tình yêu</p>
                    </div>

                    <div>
                        <h4>Theo dõi chúng tôi</h4>
                        <p>Facebook</p>
                        <p>Instagram</p>
                        <p>TikTok</p>
                    </div>

                </div>

                <div className="footer-bottom">
                    © 2026 FlowerCorner. All rights reserved.
                </div>

            </footer>

        </div>
    );
}

export default Homepage;