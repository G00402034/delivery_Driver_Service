package ie.atu.delivery_driver_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DeliveryService deliveryService;

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
        sampleDelivery.setCreatedAt(LocalDateTime.now());
        sampleDelivery.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testAssignDelivery() {
        when(deliveryPersonRepository.findFirstByIsAvailableTrue()).thenReturn(Optional.of(samplePerson));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(sampleDelivery);

        Delivery assignedDelivery = deliveryService.assignDelivery("5001", "123 Main St");

        assertNotNull(assignedDelivery);
        assertEquals("5001", assignedDelivery.getOrderId());
        assertEquals("1", assignedDelivery.getDeliveryPersonId());
        verify(deliveryPersonRepository, times(1)).save(samplePerson);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("delivery-status-queue"), anyString());
    }

    @Test
    void testUpdateDeliveryStatus() {
        when(deliveryRepository.findById("101")).thenReturn(Optional.of(sampleDelivery));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(sampleDelivery);

        Delivery updatedDelivery = deliveryService.updateDeliveryStatus("101", "Delivered");

        assertNotNull(updatedDelivery);
        assertEquals("Delivered", updatedDelivery.getDeliveryStatus());
        verify(deliveryRepository, times(1)).save(sampleDelivery);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("delivery-status-queue"), anyString());
    }

    @Test
    void testTrackDelivery() {
        when(deliveryRepository.findById("101")).thenReturn(Optional.of(sampleDelivery));

        Delivery trackedDelivery = deliveryService.trackDelivery("101");

        assertNotNull(trackedDelivery);
        assertEquals("101", trackedDelivery.getDeliveryId());
        verify(deliveryRepository, times(1)).findById("101");
    }
}

