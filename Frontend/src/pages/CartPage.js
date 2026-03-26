import React, { useEffect, useState } from "react";
import { cartAPI } from "../services/cartAPI";
import "../css/cart.css";
import { Link } from "react-router-dom";


function CartPage() {

    const [cartItems, setCartItems] = useState([]);

    const userId = "3244a14a-179d-11f1-9d7a-94758d634a48";


    useEffect(() => {
        loadCart();
    }, []);

    const loadCart = async () => {
        try {
            const res = await cartAPI.getCart(userId);
            setCartItems(res.data.data || []);
        } catch (err) {
            console.error(err);
        }
    };

    const removeItem = async (productId) => {
        await cartAPI.removeCart(userId, productId);
        window.dispatchEvent(new Event("cartUpdated"));
        loadCart();
    };




    const updateQuantity = async (productId, quantity) => {
        if (quantity < 1) return;
        await cartAPI.updateCart(userId, productId, quantity);
        window.dispatchEvent(new Event("cartUpdated"));
        loadCart();
    };

    const totalPrice = cartItems.reduce(
        (sum, item) => sum + item.unitPrice * item.quantity,
        0
    );

    return (

        <div className="cart-page">

            <h1 className="cart-title">
                Giỏ hàng <span>{cartItems.length}</span>
            </h1>

            <div className="cart-wrapper">

                {/* LEFT SIDE */}
                <div className="cart-list">

                    {cartItems.map(item => (

                        <div className="cart-item" key={item.productId}>

                            <img
                                src={`http://localhost:8080/ordering/images/${item.imageUrl}`}
                                alt={item.productName}
                            />

                            <div className="cart-info">
                                <h4>{item.productName}</h4>

                                <div className="cart-quantity">

                                    <button
                                        onClick={() =>
                                            updateQuantity(item.productId, item.quantity - 1)
                                        }
                                    >
                                        -
                                    </button>

                                    <span>{item.quantity}</span>

                                    <button
                                        onClick={() =>
                                            updateQuantity(item.productId, item.quantity + 1)
                                        }
                                    >
                                        +
                                    </button>

                                </div>
                            </div>

                            <div className="cart-price">
                                {(item.unitPrice * item.quantity).toLocaleString()}đ
                            </div>

                            <button
                                className="remove-btn"
                                onClick={() => removeItem(item.productId)}
                            >
                                ✕
                            </button>

                        </div>

                    ))}

                    <div className="cart-total-row">
                        <span>Tổng giá tiền các sản phẩm</span>
                        <span>{totalPrice.toLocaleString()}đ</span>
                    </div>

                </div>

                {/* RIGHT SIDE */}
                <div className="cart-summary">

                    <h3>Tóm tắt đơn hàng</h3>

                    <div className="summary-row">
                        <span>Thành tiền</span>
                        <span>{totalPrice.toLocaleString()}đ</span>
                    </div>

                    <div className="summary-row">
                        <span>Vận chuyển</span>
                        <span>Liên hệ phí vận chuyển sau</span>
                    </div>

                    <div className="coupon">
                        <input placeholder="Mã giảm giá" />
                        <button>Sử dụng</button>
                    </div>

                    <div className="summary-total">
                        <span>Tổng cộng</span>
                        <span>{totalPrice.toLocaleString()}đ</span>
                    </div>

                    <div className="summary-buttons">

                        <Link to="/products" className="continue-btn">
                            Mua thêm hoa
                        </Link>

                        <Link to="/checkout" className="checkout-btn">
                            🛒 Thanh toán
                        </Link>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default CartPage;