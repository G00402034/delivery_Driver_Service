package ie.atu.delivery_driver_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery-persons")
public class DeliveryPersonController {

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    // Add a new delivery person
    @PostMapping("/add")
    public DeliveryPerson addDeliveryPerson(@RequestBody DeliveryPerson deliveryPerson) {
        return deliveryPersonService.addDeliveryPerson(deliveryPerson);
    }
}
