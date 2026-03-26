import React, { useEffect, useState } from "react";
import { cartAPI } from "../services/cartAPI";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CheckoutPage() {
    const [cartItems, setCartItems] = useState([]);
    const [address, setAddress] = useState("");
    const [paymentMethod, setPaymentMethod] = useState("COD");

    const userId = "3244a14a-179d-11f1-9d7a-94758d634a48";
    const navigate = useNavigate();

    useEffect(() => {
        loadCart();
    }, []);

    const loadCart = async () => {
        try {
            const res = await cartAPI.getCart(userId);
            setCartItems(res.data.data || []);
        } catch (err) {
            console.error("Load cart error:", err.response?.data || err.message);
        }
    };

    const totalPrice = cartItems.reduce(
        (sum, item) => sum + item.unitPrice * item.quantity,
        0
    );

    const createOrder = async () => {
        if (!address) {
            alert("Vui lòng nhập địa chỉ");
            return;
        }

        try {
            const requestData = {
                userId: userId, // ✅ thêm lại
                shippingAddress: address,
                paymentMethod: paymentMethod,
                items: cartItems.map((item) => ({
                    productId: item.productId,
                    quantity: item.quantity
                }))
            };

            console.log("Sending create order request:", requestData);

            // Tạo order
            const res = await axios.post(
                "http://localhost:8080/ordering/api/orders",
                requestData,
                { headers: { "Content-Type": "application/json" } }
            );
            console.log("Order response:", res.data);

            const orderId = res.data.data?.id; // ✅ dùng id
            if (!orderId) {
                alert("Không lấy được orderId");
                return;
            }

            // Nếu VNPay
            // Nếu VNPay
            if (paymentMethod === "VNPAY") {

                console.log("Redirecting to VNPay...");

                const paymentRes = await axios.post(
                    `http://localhost:8080/ordering/api/payments/vnpay/${orderId}`
                );

                console.log("VNPay response:", paymentRes.data);

                const redirectUrl = paymentRes.data.data?.redirectUrl;

                if (!redirectUrl) {
                    alert("Không nhận được link VNPay!");
                    return;
                }

                // 🔥 QUAN TRỌNG: clear cart TRƯỚC
                await cartAPI.clearCart(userId);
                window.dispatchEvent(new Event("cartUpdated"));

                // 🔥 rồi mới redirect
                window.location.href = redirectUrl;

            } else {

                // COD
                await cartAPI.clearCart(userId);
                window.dispatchEvent(new Event("cartUpdated"));

                navigate("/payment-success");
            }
        } catch (err) {
            console.error("Create order error full:", err);
            console.error("Response data:", err.response?.data);
            console.error("Response status:", err.response?.status);
            alert("Tạo đơn hàng thất bại. Kiểm tra console.");
        }
    };

    return (
        <div style={{ maxWidth: "1000px", margin: "auto", padding: "20px" }}>
            <h2 style={{ marginBottom: "20px" }}>Thanh toán đơn hàng</h2>

            {/* DANH SÁCH SẢN PHẨM */}
            <div
                style={{
                    background: "#fff",
                    padding: "20px",
                    borderRadius: "10px",
                    marginBottom: "20px",
                    boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                }}
            >
                <h3 style={{ marginBottom: "15px" }}>Sản phẩm trong giỏ</h3>
                {cartItems.map((item) => (
                    <div
                        key={item.productId}
                        style={{
                            display: "flex",
                            alignItems: "center",
                            borderBottom: "1px solid #eee",
                            padding: "12px 0",
                            gap: "15px",
                        }}
                    >
                        <img
                            src={`http://localhost:8080/ordering/images/${item.imageUrl}`}
                            alt={item.productName}
                            style={{
                                width: "70px",
                                height: "70px",
                                objectFit: "cover",
                                borderRadius: "8px",
                            }}
                        />
                        <div style={{ flex: 1 }}>
                            <div style={{ fontWeight: "bold" }}>{item.productName}</div>
                            <div style={{ color: "#666" }}>Số lượng: {item.quantity}</div>
                        </div>
                        <div style={{ fontWeight: "bold" }}>
                            {(item.unitPrice * item.quantity).toLocaleString()}đ
                        </div>
                    </div>
                ))}
            </div>

            {/* TỔNG TIỀN */}
            <div
                style={{
                    background: "#fff",
                    padding: "20px",
                    borderRadius: "10px",
                    marginBottom: "20px",
                    boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                }}
            >
                <h3>Tổng tiền: {totalPrice.toLocaleString()}đ</h3>
            </div>

            {/* ĐỊA CHỈ */}
            <div
                style={{
                    background: "#fff",
                    padding: "20px",
                    borderRadius: "10px",
                    marginBottom: "20px",
                    boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                }}
            >
                <h3>Địa chỉ giao hàng</h3>
                <input
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    placeholder="Nhập địa chỉ giao hàng"
                    style={{ width: "100%", padding: "10px", marginTop: "10px" }}
                />
            </div>

            {/* THANH TOÁN */}
            <div
                style={{
                    background: "#fff",
                    padding: "20px",
                    borderRadius: "10px",
                    boxShadow: "0 2px 10px rgba(0,0,0,0.05)",
                }}
            >
                <h3>Phương thức thanh toán</h3>
                <select
                    value={paymentMethod}
                    onChange={(e) => setPaymentMethod(e.target.value)}
                    style={{ width: "100%", padding: "10px", marginTop: "10px" }}
                >
                    <option value="COD">Thanh toán khi nhận hàng</option>
                    <option value="VNPAY">Thanh toán VNPay</option>
                </select>

                <button
                    onClick={createOrder}
                    style={{
                        marginTop: "20px",
                        width: "100%",
                        padding: "12px",
                        background: "#ff4d6d",
                        border: "none",
                        color: "white",
                        fontSize: "16px",
                        borderRadius: "8px",
                        cursor: "pointer",
                    }}
                >
                    Đặt hàng
                </button>
            </div>
        </div>
    );
}

export default CheckoutPage;