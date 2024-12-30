package ie.atu.delivery_driver_service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Assign a delivery person to an order
    public Delivery assignDelivery(String orderId, String deliveryAddress) {
        Optional<DeliveryPerson> availablePerson = deliveryPersonRepository.findFirstByIsAvailableTrue();
        if (availablePerson.isPresent()) {
            DeliveryPerson deliveryPerson = availablePerson.get();
            deliveryPerson.setAvailable(false); // Mark as unavailable
            deliveryPersonRepository.save(deliveryPerson);

            Delivery delivery = new Delivery();
            delivery.setOrderId(orderId);
            delivery.setDeliveryAddress(deliveryAddress);
            delivery.setDeliveryPersonId(deliveryPerson.getDeliveryPersonId());
            delivery.setDeliveryStatus("Assigned");
            delivery.setCreatedAt(LocalDateTime.now());
            delivery.setUpdatedAt(LocalDateTime.now());
            Delivery savedDelivery = deliveryRepository.save(delivery);

            // Send delivery status to RabbitMQ
            rabbitTemplate.convertAndSend("delivery-status-queue", "Delivery Assigned for Order ID: " + orderId);

            return savedDelivery;
        }
        throw new RuntimeException("No available delivery personnel");
    }

    // Update delivery status
    public Delivery updateDeliveryStatus(String deliveryId, String status) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryId);
        if (deliveryOptional.isPresent()) {
            Delivery delivery = deliveryOptional.get();
            delivery.setDeliveryStatus(status);
            delivery.setUpdatedAt(LocalDateTime.now());
            Delivery updatedDelivery = deliveryRepository.save(delivery);

            // If delivered, mark the delivery person as available
            if ("Delivered".equalsIgnoreCase(status)) {
                Optional<DeliveryPerson> personOptional = deliveryPersonRepository.findById(delivery.getDeliveryPersonId());
                personOptional.ifPresent(person -> {
                    person.setAvailable(true);
                    deliveryPersonRepository.save(person);
                });
            }

            // Send updated status to RabbitMQ
            rabbitTemplate.convertAndSend("delivery-status-queue", "Delivery Status Updated: " + status);

            return updatedDelivery;
        }
        throw new RuntimeException("Delivery not found");
    }

    // Track a delivery by ID
    public Delivery trackDelivery(String deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }
}


