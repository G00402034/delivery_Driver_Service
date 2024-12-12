package ie.atu.delivery_driver_service;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "delivery_persons")
@Data
public class DeliveryPerson {
    @Id
    private String deliveryPersonId;
    private String name;
    private String phoneNumber;
    private boolean isAvailable;
}
