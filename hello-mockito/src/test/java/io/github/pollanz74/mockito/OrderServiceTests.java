package io.github.pollanz74.mockito;

import com.flextrade.jfixture.JFixture;
import com.github.javafaker.Faker;
import io.github.pollanz74.mockito.OrderService.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    // oggetto del test
    private OrderService orderService;

    // dipendenze di orderService
    @Mock
    private PaymentService paymentService;

    @Mock
    private DeliveryService deliveryService;

    // helpers
    private static JFixture fixture;
    private static Faker faker;

    @BeforeEach
    void init() {
        // vogliamo controllare le interazioni con order service
        orderService = Mockito.spy(new OrderService(paymentService, deliveryService));
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(paymentService, deliveryService);
        orderService = null;
    }

    @BeforeAll
    static void initAll() {
        fixture = new JFixture();
        faker = new Faker(Locale.ITALIAN);
    }

    @Test
    void processOrderShouldReturnFalseWhenPaymentFails() {
        // order non impatta il test
        Order order = Mockito.mock(Order.class);

        // definiamo il comportamento dei mock
        Mockito.when(paymentService.process(order)).thenReturn(false);

        // chiamiamo il metodo oggetto del test
        boolean result = orderService.process(order);

        // verifichiamo il risultato
        Assertions.assertThat(result).isFalse();

        // verifichiamo la corretta invocazione delle dipendenze di orderService
        Mockito.verify(paymentService, Mockito.times(1)).process(order);
        Mockito.verify(deliveryService, Mockito.never()).process(order);
    }

    @Test
    void processOrderShouldReturnFalseWhenDeliveryFails() {
        // order non impatta il test
        Order order = new Order();
        order.setOrderId(faker.number().randomNumber());
        order.setPaymentDone(faker.random().nextBoolean());
        order.setOrderDate(faker.date().past(10, TimeUnit.DAYS));
        order.setAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 100)));
        order.setCustomerName(faker.name().fullName());
        order.setProductId(faker.number().randomNumber());
        order.setProductName(faker.commerce().productName());
        System.out.println("order creato con Faker: " + order);

        // definiamo il comportamento dei mock
        Mockito.when(paymentService.process(order)).thenReturn(true);
        Mockito.when(deliveryService.process(order)).thenReturn(false);

        // chiamiamo il metodo oggetto del test
        boolean result = orderService.process(order);

        // verifichiamo il risultato
        Assertions.assertThat(result).isFalse();

        // verifichiamo la corretta invocazione delle dipendenze di orderService
        Mockito.verify(paymentService, Mockito.times(1)).process(order);
        Mockito.verify(deliveryService, Mockito.times(1)).process(order);
    }

    @Test
    void processOrderShouldReturnTrue() {
        // order non impatta il test
        Order order = fixture.create(Order.class);
        order.setPaymentDone(false);
        System.out.println("order creato con JFixture: " + order);

        // definiamo il comportamento dei mock
        Mockito.when(paymentService.process(order)).thenReturn(true);
        Mockito.when(deliveryService.process(order)).thenReturn(true);

        // chiamiamo il metodo oggetto del test
        boolean result = orderService.process(order);

        // verifichiamo il risultato
        Assertions.assertThat(result).isTrue();

        // verifichiamo la corretta invocazione delle dipendenze di orderService
        Mockito.verify(paymentService, Mockito.times(1)).process(order);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.verify(deliveryService, Mockito.times(1)).process(argumentCaptor.capture());

        Order capturedValue = argumentCaptor.getValue();
        Assertions.assertThat(capturedValue).isNotNull()
                .hasFieldOrPropertyWithValue("paymentDone", true)
                .hasFieldOrPropertyWithValue("orderId", order.getOrderId())
                .hasFieldOrPropertyWithValue("orderDate", order.getOrderDate())
                .hasFieldOrPropertyWithValue("productId", order.getProductId())
                .hasFieldOrPropertyWithValue("amount", order.getAmount());
    }

}
