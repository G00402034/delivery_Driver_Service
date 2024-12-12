package ie.atu.delivery_driver_service;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    List<Delivery> findByDeliveryPersonId(String deliveryPersonId);
    List<Delivery> findByDeliveryStatus(String deliveryStatus);
}
