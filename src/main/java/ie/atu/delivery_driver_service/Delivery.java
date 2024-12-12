package ie.atu.delivery_driver_service;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "deliveries")
@Data
public class Delivery {

        @Id
        private String deliveryId;
        private String orderId;
        private String deliveryPersonId;
        private String deliveryAddress;
        private String deliveryStatus;
        private String estimatedDeliveryTime;

}
