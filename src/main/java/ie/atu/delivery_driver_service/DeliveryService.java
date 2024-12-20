package ie.atu.delivery_driver_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    // Assign a delivery person to an order
    public Delivery assignDelivery(String orderId, String deliveryAddress) {
        Optional<DeliveryPerson> deliveryPersonOptional = deliveryPersonRepository.findFirstByIsAvailableTrue();
        if (deliveryPersonOptional.isPresent()) {
            DeliveryPerson deliveryPerson = deliveryPersonOptional.get();
            deliveryPerson.setAvailable(false); // Mark as unavailable
            deliveryPersonRepository.save(deliveryPerson);

            Delivery delivery = new Delivery();
            delivery.setOrderId(orderId);
            delivery.setDeliveryAddress(deliveryAddress);
            delivery.setDeliveryPersonId(deliveryPerson.getDeliveryPersonId());
            delivery.setDeliveryStatus("Assigned");
            delivery.setEstimatedDeliveryTime("45 minutes");
            return deliveryRepository.save(delivery);
        }
        throw new RuntimeException("No available delivery personnel");
    }

    // Update the status of a delivery
    public Delivery updateDeliveryStatus(String deliveryId, String status) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryId);
        if (deliveryOptional.isPresent()) {
            Delivery delivery = deliveryOptional.get();
            delivery.setDeliveryStatus(status);
            deliveryRepository.save(delivery);

            // If the delivery is completed, mark the delivery person as available
            if ("Delivered".equalsIgnoreCase(status)) {
                Optional<DeliveryPerson> deliveryPersonOptional =
                        deliveryPersonRepository.findById(delivery.getDeliveryPersonId());
                deliveryPersonOptional.ifPresent(deliveryPerson -> {
                    deliveryPerson.setAvailable(true);
                    deliveryPersonRepository.save(deliveryPerson);
                });
            }

            return delivery;
        }
        throw new RuntimeException("Delivery not found");
    }

    // Track delivery status
    public Delivery trackDelivery(String deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }
}

