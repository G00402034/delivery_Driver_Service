package ie.atu.delivery_driver_service;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "deliveries")

public class Delivery {

        @Id
        private String deliveryId;
        private String orderId;
        private String deliveryPersonId;
        private String deliveryAddress;
        private String deliveryStatus;
        private String estimatedDeliveryTime;

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

        public String getDeliveryPersonId() {
                return deliveryPersonId;
        }

        public void setDeliveryPersonId(String deliveryPersonId) {
                this.deliveryPersonId = deliveryPersonId;
        }

        public String getDeliveryAddress() {
                return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
                this.deliveryAddress = deliveryAddress;
        }

        public String getDeliveryStatus() {
                return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
                this.deliveryStatus = deliveryStatus;
        }

        public String getEstimatedDeliveryTime() {
                return estimatedDeliveryTime;
        }

        public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
                this.estimatedDeliveryTime = estimatedDeliveryTime;
        }
}
