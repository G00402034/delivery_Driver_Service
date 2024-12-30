package ie.atu.delivery_driver_service;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "deliveries")

public class Delivery {
        @Id
        private String deliveryId;
        private String orderId;
        private String deliveryAddress;
        private String deliveryPersonId;
        private String deliveryStatus; // e.g., "Assigned", "In Transit", "Delivered"
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public String getDeliveryId() {
                return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
                this.deliveryId = deliveryId;
        }

        public String getOrderId() {
                return orderId;
        }

        public void setOrderId(String orderId) {
                this.orderId = orderId;
        }

        public String getDeliveryAddress() {
                return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
                this.deliveryAddress = deliveryAddress;
        }

        public String getDeliveryPersonId() {
                return deliveryPersonId;
        }

        public void setDeliveryPersonId(String deliveryPersonId) {
                this.deliveryPersonId = deliveryPersonId;
        }

        public String getDeliveryStatus() {
                return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
                this.deliveryStatus = deliveryStatus;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }
}
