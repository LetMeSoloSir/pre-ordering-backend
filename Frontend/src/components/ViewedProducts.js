import React, { useEffect, useState } from "react";
import "./ViewedProducts.css";
import { productAPI } from "../services/api";
import { Link } from "react-router-dom";

function ViewedProducts() {

    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetchViewed();
    }, []);

    const fetchViewed = async () => {
        try {

            const data = await productAPI.getRecentViewed();

            setProducts(data || []);

        } catch (err) {
            console.error("Viewed error:", err);
        }
    };

    if (!products.length) return null;

    return (
        <div className="viewed-container">
            <h2>Sản phẩm đã xem</h2>

            <div className="viewed-list">
                {products.map((p) => (

                    <Link
                        to={`/products/${p.productId}`}
                        key={p.productId}
                        className="viewed-card"
                    >

                        <img
                            src={`http://localhost:8080/ordering/images/${p.avatarUrl}`}
                            alt={p.productName}
                        />

                        <div className="viewed-info">
                            <div className="name">{p.productName}</div>

                            <div className="price">
                                <span className="new-price">
                                    {formatPrice(p.productPrice)}
                                </span>

                                {p.discount > 0 && (
                                    <>
                                        <span className="old-price">
                                            {formatPrice(
                                                p.productPrice / (1 - p.discount / 100)
                                            )}
                                        </span>

                                        <span className="discount">
                                            -{p.discount}%
                                        </span>
                                    </>
                                )}
                            </div>
                        </div>

                    </Link>

                ))}
            </div>
        </div>
    );
}

function formatPrice(price) {
    return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND"
    }).format(price);
}

export default ViewedProducts;