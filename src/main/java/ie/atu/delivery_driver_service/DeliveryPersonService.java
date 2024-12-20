package ie.atu.delivery_driver_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    // Add a new delivery person
    public DeliveryPerson addDeliveryPerson(DeliveryPerson deliveryPerson) {

        if (deliveryPerson.getName() == null || deliveryPerson.getName().isEmpty()) {
            throw new IllegalArgumentException("Delivery person name cannot be empty");
        }
        if (deliveryPerson.getPhoneNumber() == null || deliveryPerson.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        deliveryPerson.setAvailable(true); // Set default availability to true
        return deliveryPersonRepository.save(deliveryPerson);
    }
}

