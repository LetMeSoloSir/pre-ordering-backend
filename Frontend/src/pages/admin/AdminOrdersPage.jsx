import React, { useEffect, useState } from "react";
import axios from "axios";

function AdminOrdersPage() {

    const [orders, setOrders] = useState([]);

    useEffect(() => {
        loadOrders();
    }, []);

    const loadOrders = async () => {

        try {

            const auth = JSON.parse(localStorage.getItem("auth"));
            const token = auth?.accessToken || auth?.access_token;

            console.log("Token:", token);

            const res = await axios.get(
                "http://localhost:8080/ordering/api/admin/orders",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            console.log("Orders:", res.data);

            setOrders(res.data.data || res.data || []);

        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Quản lý đơn hàng</h2>

            <table border="1" width="100%" style={{ marginTop: "20px" }}>
                <thead>
                <tr>
                    <th>Order Number</th>
                    <th>User</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Payment</th>
                    <th>Address</th>
                </tr>
                </thead>

                <tbody>
                {orders.map((o) => (
                    <tr key={o.id}>
                        <td>{o.orderNumber}</td>
                        <td>{o.userId?.fullName || o.userId?.email}</td>
                        <td>{o.totalAmount?.toLocaleString()}đ</td>
                        <td>{o.status}</td>
                        <td>{o.paymentMethod}</td>
                        <td>{o.shippingAddress}</td>
                    </tr>
                ))}
                </tbody>

            </table>
        </div>
    );
}

export default AdminOrdersPage;