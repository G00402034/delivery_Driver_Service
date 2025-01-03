package ie.atu.delivery_driver_service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    private Delivery sampleDelivery;
    private DeliveryPerson samplePerson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        samplePerson = new DeliveryPerson();
        samplePerson.setDeliveryPersonId("1");
        samplePerson.setName("John Doe");
        samplePerson.setPhoneNumber("123456789");
        samplePerson.setAvailable(true);

        sampleDelivery = new Delivery();
        sampleDelivery.setDeliveryId("101");
        sampleDelivery.setOrderId("5001");
        sampleDelivery.setDeliveryAddress("123 Main St");
        sampleDelivery.setDeliveryPersonId("1");
        sampleDelivery.setDeliveryStatus("Assigned");
    }

    @Test
    void testAssignDelivery() {
        AssignDeliveryRequest request = new AssignDeliveryRequest("5001", "123 Main St");
        when(deliveryService.assignDelivery(request.getOrderId(), request.getDeliveryAddress())).thenReturn(sampleDelivery);

        Delivery assignedDelivery = deliveryController.assignDelivery(request);

        assertNotNull(assignedDelivery);
        assertEquals("5001", assignedDelivery.getOrderId());
        verify(deliveryService, times(1)).assignDelivery("5001", "123 Main St");
    }

    @Test
    void testUpdateDeliveryStatus() {
        when(deliveryService.updateDeliveryStatus("101", "Assigned")).thenReturn(sampleDelivery);

        Delivery updatedDelivery = deliveryController.updateDeliveryStatus("101", "Assigned");

        assertNotNull(updatedDelivery);
        assertEquals("Assigned", updatedDelivery.getDeliveryStatus());
        verify(deliveryService, times(1)).updateDeliveryStatus("101", "Assigned");
    }

    @Test
    void testTrackDelivery() {
        when(deliveryService.trackDelivery("101")).thenReturn(sampleDelivery);

        Delivery trackedDelivery = deliveryController.trackDelivery("101");

        assertNotNull(trackedDelivery);
        assertEquals("101", trackedDelivery.getDeliveryId());
        verify(deliveryService, times(1)).trackDelivery("101");
    }

    @Test
    void testAddDriver() {
        when(deliveryService.addDeliveryPerson(samplePerson)).thenReturn(samplePerson);

        DeliveryPerson addedDriver = deliveryController.addDriver(samplePerson);

        assertNotNull(addedDriver);
        assertEquals("1", addedDriver.getDeliveryPersonId());
        verify(deliveryService, times(1)).addDeliveryPerson(samplePerson);
    }
}

