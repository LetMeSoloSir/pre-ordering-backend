import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { FaShoppingCart } from "react-icons/fa";
import { getAuth, clearAuth } from "../services/auth";
import { FaUserCircle, FaChevronDown } from "react-icons/fa";
import "./Navigation.css";
import { cartAPI } from "../services/cartAPI";

function Navigation() {

    const [auth, setAuth] = useState(null);
    const [open, setOpen] = useState(false);
    const [cartCount, setCartCount] = useState(0);
    const fetchCartCount = async () => {
        try {
            const userId = "3244a14a-179d-11f1-9d7a-94758d634a48";

            const res = await cartAPI.getCart(userId);

            const cartItems = res.data.data || [];

            const total = cartItems.length;

            setCartCount(total);

        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {

        const user = getAuth();
        setAuth(user);

        // 👉 load lần đầu
        fetchCartCount();

        // 👉 lắng nghe khi add cart
        window.addEventListener("cartUpdated", fetchCartCount);

        return () => {
            window.removeEventListener("cartUpdated", fetchCartCount);
        };

    }, []);

    function handleLogout() {
        clearAuth();
        window.location.href = "/";
    }

    return (
        <div className="navigation">

            {/* TOP BAR */}
            <div className="nav-top">
                <div className="nav-hotline">
                    HOTLINE: 1900 633 045 | 0865 160 360
                </div>

                {/* CHƯA LOGIN */}
                {!auth && (
                    <div className="nav-auth">

                        <Link to="/login" className="login-btn">
                            Đăng nhập
                        </Link>

                        <Link to="/register" className="register-btn">
                            Đăng ký
                        </Link>

                    </div>
                )}

                {/* ĐÃ LOGIN */}
                {auth && (
                    <div className="nav-user">
                        <div
                            className="nav-username"
                            onClick={() => setOpen(!open)}
                        >
                            <FaUserCircle className="user-icon" />

                            <span className="username-text">
        {auth.user?.username}
    </span>

                            <FaChevronDown className="dropdown-icon" />
                        </div>

                        {open && (
                            <div className="dropdown-menu">

                                <Link to="/profile">
                                    Profile
                                </Link>

                                <div
                                    className="logout"
                                    onClick={handleLogout}
                                >
                                    Logout
                                </div>

                            </div>
                        )}

                    </div>
                )}
            </div>

            {/* HEADER */}
            <div className="nav-header">

                <div className="nav-logo">
                    <Link to="/">FLOWER<span>CORNER</span></Link>
                </div>

                <div className="nav-right">

                    <div className="nav-menu">
                        <Link to="/">Trang chủ</Link>
                        <Link to="/products">Sản phẩm</Link>
                    </div>

                    <div className="nav-cart">
                        <Link to="/cart">
                            <FaShoppingCart />

                            {cartCount > 0 && (
                                <span className="cart-badge">
                {cartCount}
            </span>
                            )}

                        </Link>
                    </div>

                </div>

            </div>

        </div>
    );
}

export default Navigation;