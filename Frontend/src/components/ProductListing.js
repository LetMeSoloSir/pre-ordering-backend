import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { productAPI } from '../services/api';
import './ProductListing.css';
import { cartAPI } from "../services/cartAPI";

function ProductListing() {

    // ===== FILTER =====
    const [filters, setFilters] = useState({
        keyword: "",
        categoryId: "",
        minPrice: "",
        maxPrice: "",
        sortBy: "productName",
        direction: "ASC"
    });

    const [isFiltering, setIsFiltering] = useState(false);

    // ===== DATA =====
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // ===== PAGINATION =====
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);

    // ===== BUILD REQUEST =====
    const buildFilterRequest = () => {
        return {
            keyword: filters.keyword || null,
            categoryId: filters.categoryId || null,
            minPrice: filters.minPrice ? Number(filters.minPrice) : null,
            maxPrice: filters.maxPrice ? Number(filters.maxPrice) : null,
            sortBy: filters.sortBy,
            direction: filters.direction,
            page: currentPage,
            size: 21
        };
    };

    // ===== FETCH NORMAL =====
    const fetchProducts = async () => {
        try {
            setLoading(true);
            setError(null);

            const response = await productAPI.getProducts(currentPage, 21, 'productName');

            setProducts(response.content || []);
            setTotalPages(response.totalPages || 0);
            setTotalElements(response.totalElements || 0);

        } catch (err) {
            setError('Failed to load products. Please try again later.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    // ===== FETCH FILTER =====
    const fetchFilteredProducts = async () => {
        try {
            setLoading(true);

            const response = await productAPI.filterProducts(buildFilterRequest());

            setProducts(response.content || []);
            setTotalPages(response.totalPages || 0);
            setTotalElements(response.totalElements || 0);

        } catch (err) {
            console.error("Filter error:", err);
        } finally {
            setLoading(false);
        }
    };

    // ===== APPLY FILTER =====
    const handleApplyFilter = () => {
        setIsFiltering(true);
        setCurrentPage(0);
    };

    // ===== RESET FILTER =====
    const handleResetFilter = () => {
        setFilters({
            keyword: "",
            categoryId: "",
            minPrice: "",
            maxPrice: "",
            sortBy: "productName",
            direction: "ASC"
        });
        setIsFiltering(false);
        setCurrentPage(0);
    };

    // ===== USE EFFECT =====
    useEffect(() => {
        if (isFiltering) {
            fetchFilteredProducts();
        } else {
            fetchProducts();
        }
    }, [currentPage, isFiltering]);

    // ===== ADD TO CART =====
    const handleAddToCart = async (product) => {
        try {
            const userId = "3244a14a-179d-11f1-9d7a-94758d634a48";

            await cartAPI.addToCart({
                userId: userId,
                productId: product.productId,
                productName: product.productName,
                unitPrice: product.productPrice,
                quantity: 1,
                imageUrl: product.avatarUrl
            });
            window.dispatchEvent(new Event("cartUpdated"));

            alert("Added to cart successfully!");
        } catch (error) {
            console.error("Add to cart error:", error);
        }
    };

    // ===== PAGINATION =====
    const handlePreviousPage = () => {
        if (currentPage > 0) setCurrentPage(currentPage - 1);
    };

    const handleNextPage = () => {
        if (currentPage < totalPages - 1) setCurrentPage(currentPage + 1);
    };

    // ===== UI =====
    if (loading) return <div className="loading">Loading products...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="product-listing">

            <h1>All Products</h1>

            <p className="product-count">
                Showing {products.length} of {totalElements} products
            </p>

            <div className="product-page">

                {/* FILTER SIDEBAR */}
                <div className="filter-sidebar">

                    <h3>🌸 Flower Filter</h3>

                    {/* SEARCH */}
                    <div className="filter-section">
                        <label>🔍 Search Flower</label>
                        <input
                            type="text"
                            placeholder="Rose, Tulip..."
                            onChange={(e) =>
                                setFilters({ ...filters, keyword: e.target.value })
                            }
                        />
                    </div>

                    {/* CATEGORY */}
                    <div className="filter-section">
                        <label>🌼 Flower Type</label>
                        <select
                            onChange={(e) =>
                                setFilters({ ...filters, categoryId: e.target.value })
                            }
                        >
                            <option value="">All Flowers</option>
                            <option value="1">Roses</option>
                            <option value="2">Tulips</option>
                            <option value="3">Sunflowers</option>
                            <option value="4">Bouquet</option>
                        </select>
                    </div>

                    {/* PRICE */}
                    <div className="filter-section">
                        <label>💐 Price Range</label>
                        <div className="filter-price">
                            <input
                                type="number"
                                placeholder="Min"
                                onChange={(e) =>
                                    setFilters({ ...filters, minPrice: e.target.value })
                                }
                            />
                            <input
                                type="number"
                                placeholder="Max"
                                onChange={(e) =>
                                    setFilters({ ...filters, maxPrice: e.target.value })
                                }
                            />
                        </div>
                    </div>

                    {/* SORT */}
                    <div className="filter-section">
                        <label>✨ Sort By</label>
                        <select
                            onChange={(e) => {
                                const value = e.target.value;

                                if (value === "priceAsc") {
                                    setFilters({ ...filters, sortBy: "productPrice", direction: "ASC" });
                                } else if (value === "priceDesc") {
                                    setFilters({ ...filters, sortBy: "productPrice", direction: "DESC" });
                                } else {
                                    setFilters({ ...filters, sortBy: "createdAt", direction: "DESC" });
                                }
                            }}
                        >
                            <option value="newest">Newest</option>
                            <option value="priceAsc">Price Low → High</option>
                            <option value="priceDesc">Price High → Low</option>
                        </select>
                    </div>

                    <button className="btn-filter" onClick={handleApplyFilter}>
                        🌷 Apply Filter
                    </button>

                    <button className="btn-filter" onClick={handleResetFilter}>
                        Reset
                    </button>

                </div>

                {/* PRODUCT CONTENT */}
                <div className="product-content">

                    <div className="product-grid">

                        {products.map((product) => (

                            <div key={product.productId} className="product-card">

                                <div className="product-image">

                                    <img
                                        src={`http://localhost:8080/ordering/images/${product.avatarUrl}`}
                                        alt={product.productName}
                                    />

                                    <div className="product-overlay">
                                        <button
                                            className="btn-cart"
                                            onClick={() => handleAddToCart(product)}
                                        >
                                            🛒
                                        </button>
                                    </div>

                                    {product.discount > 0 && (
                                        <span className="badge-sale">
                                            -{product.discount}%
                                        </span>
                                    )}

                                </div>

                                <div className="product-info">

                                    <h3 className="product-name">
                                        {product.productName}
                                    </h3>

                                    <div className="product-price">
                                        {product.productPrice
                                            ? new Intl.NumberFormat("vi-VN", {
                                                style: "currency",
                                                currency: "VND"
                                            }).format(product.productPrice)
                                            : "0 đ"}
                                    </div>

                                    <Link
                                        to={`/products/${product.productId}`}
                                        className="btn-view"
                                    >
                                        View Product
                                    </Link>

                                </div>

                            </div>

                        ))}

                    </div>

                    {/* PAGINATION */}
                    {totalPages > 1 && (
                        <div className="pagination">

                            <button
                                onClick={handlePreviousPage}
                                disabled={currentPage === 0}
                            >
                                Previous
                            </button>

                            <span className="page-info">
                                Page {currentPage + 1} of {totalPages}
                            </span>

                            <button
                                onClick={handleNextPage}
                                disabled={currentPage >= totalPages - 1}
                            >
                                Next
                            </button>

                        </div>
                    )}

                    {products.length === 0 && (
                        <div className="no-products">
                            <p>No products found.</p>
                        </div>
                    )}

                </div>

            </div>

        </div>
    );
}

export default ProductListing;