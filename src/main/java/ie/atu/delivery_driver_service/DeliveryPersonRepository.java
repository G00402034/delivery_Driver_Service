package ie.atu.delivery_driver_service;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface DeliveryPersonRepository extends MongoRepository<DeliveryPerson, String> {
    Optional<DeliveryPerson> findFirstByIsAvailableTrue();
}

