import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { productAPI } from '../services/api';
import './ProductDetail.css';
import { cartAPI } from "../services/cartAPI";
import ViewedProducts from "../components/ViewedProducts";

function ProductDetail() {
  // Get product ID from URL parameters
  const { id } = useParams();
  const navigate = useNavigate();


  // State to store product data
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
    const [quantity, setQuantity] = useState(1);
  const [error, setError] = useState(null);

    const handleAddToCart = async () => {
        try {
            const userId = "3244a14a-179d-11f1-9d7a-94758d634a48";

            await cartAPI.addToCart({
                userId: userId,
                productId: product.productId,
                productName: product.productName,
                unitPrice: product.productPrice,
                quantity: quantity,
                imageUrl: product.avatarUrl
            });

            window.dispatchEvent(new Event("cartUpdated"));

            alert("Added to cart!");

        } catch (err) {
            console.error(err);
        }
    };



  /**
   * Fetch product details when component mounts
   */
  useEffect(() => {
      fetchProductDetail();
  }, [id]);

  /**
   * Function to fetch product details from API
   */
  const fetchProductDetail = async () => {
    try {
      setLoading(true);
      setError(null);

      // Call API to get product details
      const productData = await productAPI.getProductDetail(id);
      setProduct(productData);
    } catch (err) {
      setError('Failed to load product details. Please try again.');
      console.error('Error fetching product:', err);
    } finally {
      setLoading(false);
    }
  };

    const handleIncrease = () => {
        if (quantity < product.unitsInStock) {
            setQuantity(quantity + 1);
        }
    };

    const handleDecrease = () => {
        if (quantity > 1) {
            setQuantity(quantity - 1);
        }
    };



  // Show loading state
  if (loading) {
    return <div className="loading">Loading product details...</div>;
  }

  // Show error state
  if (error) {
    return (
      <div className="product-detail">
        <div className="error">{error}</div>
        <button onClick={() => navigate('/products')}>Back to Products</button>
      </div>
    );
  }

  // Show message if product not found
  if (!product) {
    return (
      <div className="product-detail">
        <div className="error">Product not found</div>
        <button onClick={() => navigate('/products')}>Back to Products</button>
      </div>
    );
  }

  // Calculate discounted price if discount exists
  const originalPrice = product.productPrice || 0;
  const discountAmount = product.discount ? (originalPrice * product.discount / 100) : 0;
  const finalPrice = originalPrice - discountAmount;

  return (
      <>
    <div className="product-detail">
      <button onClick={() => navigate('/products')} className="btn-back">
        ← Back to Products
      </button>

      <div className="product-detail-content">
        {/* Product Image */}
        <div className="product-detail-image">
          {product.avatarUrl ? (
              <img
                  src={`http://localhost:8080/ordering/images/${product.avatarUrl}`}
                  alt={product.productName}
              />
          ) : (
            <div className="no-image-large">No Image Available</div>
          )}
        </div>

        {/* Product Information */}
        <div className="product-detail-info">
          <h1>{product.productName}</h1>

          <div className="product-price-detail">
            {product.discount && product.discount > 0 ? (
              <>
               <span className="original-price">
  {new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND"
  }).format(originalPrice)}
</span>

                  <span className="final-price">
  {new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND"
  }).format(finalPrice)}
</span>
                <span className="discount-badge">{product.discount}% OFF</span>
              </>
            ) : (
              <span className="final-price">${originalPrice.toFixed(2)}</span>
            )}
          </div>

          <div className="product-description-full">
            <h3>Description</h3>
            <p>{product.productDescription || 'No description available'}</p>
          </div>

          <div className="product-specs">
              <div className="product-actions">

                  <div className="quantity-wrapper">
                      <span>Quantity</span>

                      <div className="quantity-box">
                          <button onClick={handleDecrease}>-</button>

                          <input value={quantity} readOnly />

                          <button onClick={handleIncrease}>+</button>
                      </div>

                      <span className="stock-note">
            {product.unitsInStock} available
        </span>
                  </div>

                  <div className="action-buttons">
                      <button className="btn-add-cart" onClick={handleAddToCart}>
                          🛒 Thêm vào giỏ
                      </button>

                      <button
                          className="btn-buy-now"
                          onClick={async () => {
                              await handleAddToCart();
                              navigate("/cart");
                          }}
                      >
                          ⚡ Mua ngay
                      </button>
                  </div>

              </div>

            <div className="spec-item">
              <strong>Status:</strong> {product.status || 'Available'}
            </div>
            {product.unitsOnOrder !== undefined && (
              <div className="spec-item">
                <strong>On Order:</strong> {product.unitsOnOrder}
              </div>
            )}
          </div>

        </div>
      </div>
    </div>
          <ViewedProducts />
      </>
  );
}

export default ProductDetail;
