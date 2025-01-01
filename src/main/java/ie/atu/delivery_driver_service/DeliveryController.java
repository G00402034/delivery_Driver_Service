package ie.atu.delivery_driver_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Assign a delivery person to an order
    @PostMapping("/assign")
    public Delivery assignDelivery(@RequestBody AssignDeliveryRequest request) {
        return deliveryService.assignDelivery(request.getOrderId(), request.getDeliveryAddress());
    }


    // Update delivery status
    @PutMapping("/{deliveryId}/status")
    public Delivery updateDeliveryStatus(@PathVariable String deliveryId, @RequestParam String status) {
        return deliveryService.updateDeliveryStatus(deliveryId, status);
    }

    // Track delivery by ID
    @GetMapping("/{deliveryId}")
    public Delivery trackDelivery(@PathVariable String deliveryId) {
        return deliveryService.trackDelivery(deliveryId);
    }

    @PostMapping("/drivers")
    public DeliveryPerson addDriver(@RequestBody DeliveryPerson deliveryPerson) {
        return deliveryService.addDeliveryPerson(deliveryPerson);
    }

}

