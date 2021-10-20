package io.github.pollanz74.mockito;

import com.flextrade.jfixture.annotations.Fixture;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

    //CASO 1
    @Test
    @DisplayName("order successful")
    void orderShouldbeCompletedSuccesssfully() {

        Payment paymentMock = Mockito.mock(Payment.class);
        Order order1 = new Order(123,delivery,paymentMock);
        when(delivery.canDelivery(any())).thenReturn(true);
        when(paymentMock.isPaymentAccepted()).thenReturn(true);
        when(paymentMock.generateReceipt()).thenReturn(receipt);

        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);
        //JUPITER
        Assertions.assertEquals(receipt, order1.doOrder());
        verify(paymentMock,times(2)).printReceipt();
    }

    @Test
    @DisplayName("order fail because delivery can't be performed")
    void orderShouldThrowsRuntimeExceptionBecauseOfDelivery() {

        when(delivery.canDelivery(any())).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> order.doOrder());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry, at the moment your order can't be delivered");

        verify(payment,times(0)).printReceipt();
        verify(payment,times(0)).isPaymentAccepted();

    }

    @Test
    @DisplayName("order fail because payment is rejected")
    void orderShouldThrowsRuntimeExceptionBecauseOfPaymentRejected() {
        when(delivery.canDelivery(any())).thenReturn(true);
        when(payment.isPaymentAccepted()).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> order.doOrder());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry your payment is not successful, try again");

        verify(delivery,times(1)).canDelivery(any());
    }


}