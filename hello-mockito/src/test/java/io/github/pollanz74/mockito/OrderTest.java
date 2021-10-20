package io.github.pollanz74.mockito;

import com.flextrade.jfixture.annotations.Fixture;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static com.flextrade.jfixture.FixtureAnnotations.initFixtures;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
class OrderTest {

    //TODO info su differenze mockito 2.x e 3.x su specifico metodo deprecato
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        initFixtures(this);
    }

    //uso dell'annotation mock
    @Mock
    private Delivery delivery;

    @Mock
    private Payment payment;

    @Fixture
    private Receipt receipt;

    private static final Faker faker = Faker.instance();

    @InjectMocks
    Order order;

    //CASO 1: uso di mock senza annotation + uso di org.assertj.core.api.Assertions (assertj)
    @Test
    @DisplayName("Order successful, utilizzo di mock senza annotazioni")
    void orderShouldbeCompletedSuccessfully() {

        Payment paymentMock = Mockito.mock(Payment.class);
        Delivery deliveryMock = Mockito.mock(Delivery.class);
        Order order1 = new Order(123, deliveryMock, paymentMock);
        when(deliveryMock.canDelivery(any())).thenReturn(true);
        when(paymentMock.isPaymentAccepted()).thenReturn(true);
        when(paymentMock.generateReceipt()).thenReturn(receipt);

        //ASSERTJ
        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);
        verify(paymentMock, times(1)).printReceipt();

    }

    //CASO 2: uso di mock con annotation + uso di org.junit.jupiter.api.Assertions (jupiter)
    @Test
    @DisplayName("order successful, utilizzo di mock con annotazioni")
    void orderShouldbeCompletedSuccessfully2() {

        when(delivery.canDelivery(any())).thenReturn(true);
        when(payment.isPaymentAccepted()).thenReturn(true);
        when(payment.generateReceipt()).thenReturn(receipt);

        //JUPITER
        Assertions.assertEquals(receipt, order.doOrder());
        verify(payment, times(1)).printReceipt();

    }

    // uso di spy
    @Test
    @DisplayName("order successful, semplice uso di spy")
    void orderShouldbeCompletedSuccessfully3() {

        Payment paymentSpy = spy(new Payment());
        Delivery deliverySpy = Mockito.spy(new Delivery());
        paymentSpy.setCardNumber("12");
        Order order1 = new Order(123, deliverySpy, paymentSpy);
        Mockito.doReturn(true).when(deliverySpy).canDelivery(any());
        Mockito.doReturn(true).when(paymentSpy).isPaymentAccepted();
        Mockito.doReturn(receipt).when(paymentSpy).generateReceipt();

        Assertions.assertEquals("12", paymentSpy.getCardNumber());

        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);
        verify(paymentSpy, times(1)).printReceipt();

    }

    @Test
    @DisplayName("order fail because delivery can't be performed")
    void orderShouldThrowsRuntimeExceptionBecauseOfDelivery() {

        when(delivery.canDelivery(any())).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> order.doOrder());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry, at the moment your order can't be delivered");

        verify(payment, times(0)).printReceipt();
        verify(payment, times(0)).isPaymentAccepted();

    }

    @Test
    @DisplayName("order fail because payment is rejected")
    void orderShouldThrowsRuntimeExceptionBecauseOfPaymentRejected() {
        when(delivery.canDelivery(any())).thenReturn(true);
        when(payment.isPaymentAccepted()).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> order.doOrder());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry your payment is not successful, try again");

        verify(delivery, times(1)).canDelivery(any());

    }

    @Test
    @DisplayName("order successful, utilizzo di argument captor")
    void orderShouldbeCompletedSuccessfully4() {

        Payment paymentMock = Mockito.mock(Payment.class);
        Delivery deliveryMock = Mockito.mock(Delivery.class);
        Order order1 = new Order(123, deliveryMock, paymentMock);
        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);

        when(deliveryMock.canDelivery(any())).thenReturn(true);
        when(paymentMock.isPaymentAccepted()).thenReturn(true);
        when(paymentMock.generateReceipt()).thenReturn(receipt);

        //ASSERTJ
        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);

        verify(deliveryMock).canDelivery(argumentCaptor.capture());

        Assertions.assertEquals(123, argumentCaptor.getValue().getOrderNumber());

        verify(paymentMock, times(1)).printReceipt();

    }
}