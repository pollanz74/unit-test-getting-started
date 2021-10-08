package io.github.pollanz74.mockito;

import java.math.BigDecimal;
import java.util.Date;
import java.util.StringJoiner;

public class OrderService {

    private final PaymentService paymentService;

    private final DeliveryService deliveryService;

    public OrderService(PaymentService paymentService, DeliveryService deliveryService) {
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
    }

    public boolean process(Order order) {
        if (!paymentService.process(order)) {
            return false;
        }
        order.setPaymentDone(true);
        return deliveryService.process(order);
    }

    public static class Order {

        private long orderId;

        private Date orderDate;

        private long customerId;

        private String customerName;

        private long productId;

        private String productName;

        private BigDecimal amount;

        private boolean paymentDone;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public boolean isPaymentDone() {
            return paymentDone;
        }

        public void setPaymentDone(boolean paymentDone) {
            this.paymentDone = paymentDone;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Date getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                    .add("orderId=" + orderId)
                    .add("orderDate=" + orderDate)
                    .add("customerId=" + customerId)
                    .add("customerName='" + customerName + "'")
                    .add("productId=" + productId)
                    .add("productName='" + productName + "'")
                    .add("amount=" + amount)
                    .add("paymentDone=" + paymentDone)
                    .toString();
        }

    }

}
