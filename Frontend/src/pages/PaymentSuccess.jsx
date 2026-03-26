import React from "react";
import { useNavigate } from "react-router-dom";

function PaymentSuccess() {

    const navigate = useNavigate();

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            height: "80vh"
        }}>
            <h1>✅ Thanh toán thành công</h1>
            <p>Cảm ơn bạn đã đặt hàng!</p>

            <button
                onClick={() => navigate("/")}
                style={{
                    marginTop: "20px",
                    padding: "10px 20px",
                    background: "#ff4d6d",
                    color: "white",
                    border: "none",
                    borderRadius: "8px",
                    cursor: "pointer"
                }}
            >
                Về trang chủ
            </button>
        </div>
    );
}

export default PaymentSuccess;