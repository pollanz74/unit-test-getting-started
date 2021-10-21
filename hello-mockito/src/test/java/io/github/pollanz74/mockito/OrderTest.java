package io.github.pollanz74.mockito;

import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.annotations.FromListOf;
import com.flextrade.jfixture.annotations.Range;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static com.flextrade.jfixture.FixtureAnnotations.initFixtures;
import static io.github.pollanz74.mockito.constant.Constants.ADDRESS;
import static io.github.pollanz74.mockito.constant.Constants.SYSTEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderTest {

    private static final Faker faker = Faker.instance();

    //uso dell'annotation mock
    @Mock
    private Delivery delivery;

    @Mock
    private Payment payment;

    @InjectMocks
    private Order order;

    @Fixture
    private Receipt receipt;

    @Fixture
    @Range(min = 1, max = 10000)
    private Integer orderNumber;

    @Fixture
    @FromListOf(numbers = { 40, 2020, 123, 550, 220, Integer.MAX_VALUE})
    private Integer cardNumber;

    //TODO info su differenze mockito 2.x e 3.x su specifico metodo deprecato
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        initFixtures(this);
        order.setOrderNumber(orderNumber);
    }

    //CASO 1: uso di mock con annotation + uso di org.junit.jupiter.api.Assertions (jupiter)
    @Test
    @DisplayName("order successful, utilizzo di mock annotation e JFixture")
    void orderShouldbeCompletedSuccessfully() {

        when(delivery.canDelivery(anyString(),anyString(),anyInt())).thenReturn(delivery);
        when(delivery.isDeliveryAccepted()).thenReturn(true);
        when(payment.canPay()).thenReturn(true);
        when(payment.generateReceipt()).thenReturn(receipt);

        //JUPITER
        Assertions.assertEquals(receipt, order.doOrder());
        //anche se il metodo viene invocato in console non ne vediamo gli effetti perche è un mock
        verify(payment, times(1)).printReceipt();

    }

    //CASO 2: uso di mock senza annotation + uso di org.assertj.core.api.Assertions (assertj)
    @Test
    @DisplayName("Order successful, di mock with Method Mockito.mock")
    void orderShouldbeCompletedSuccessfully2() {

        Payment paymentMock = Mockito.mock(Payment.class);
        Delivery deliveryMock = Mockito.mock(Delivery.class);
        Order order1 = new Order(orderNumber, deliveryMock, paymentMock);

        when(deliveryMock.canDelivery(anyString(),anyString(),anyInt())).thenReturn(deliveryMock);
        when(deliveryMock.isDeliveryAccepted()).thenReturn(true);

        when(paymentMock.canPay()).thenReturn(true);
        when(paymentMock.generateReceipt()).thenReturn(receipt);

        //ASSERTJ
        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);
        verify(paymentMock, times(1)).printReceipt();

    }

    @Test
    @DisplayName("order fail because delivery can't be performed")
    void orderShouldThrowsRuntimeExceptionBecauseOfDelivery() {

        when(delivery.canDelivery(anyString(),anyString(),anyInt())).thenReturn(delivery);
        when(delivery.isDeliveryAccepted()).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> order.doOrder());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry, at the moment your order can't be delivered");

        verify(payment, times(0)).canPay();
        verify(payment, times(0)).printReceipt();

    }

    @Test
    @DisplayName("order fail because payment is rejected")
    void orderShouldThrowsRuntimeExceptionBecauseOfPaymentRejected() {
        when(delivery.canDelivery(anyString(),anyString(),anyInt())).thenReturn(delivery);
        when(delivery.isDeliveryAccepted()).thenReturn(true);

        when(payment.canPay()).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> order.doOrder());
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry your payment is not successful, try again");

        verify(delivery, times(1)).canDelivery(anyString(),anyString(),anyInt());
        verify(delivery, times(1)).isDeliveryAccepted();
        verify(payment, times(0)).generateReceipt();
        verify(payment, times(0)).printReceipt();
    }

    @RepeatedTest(3)
    @DisplayName("order successful, Mockito.spy e JFixture")
    void orderShouldbeCompletedSuccessfullyWithSpyAndFixtureFeatures() {

        Payment paymentSpy = spy(new Payment());
        //chiamata a metodo reale, receipt creata con JFIXTURE
        paymentSpy.setReceipt(receipt);

      /*  JFixture fixture = new JFixture();
        String prettyCompanyName = fixture.create(String.class);*/

        Delivery deliverySpy = spy(new Delivery());
        //chiamata a metodo reale, cardNumber creato con JFIXTURE
        paymentSpy.setCardNumber(cardNumber);

        Order order1 = new Order(orderNumber, deliverySpy, paymentSpy);

        Mockito.doReturn(deliverySpy).when(deliverySpy).canDelivery(anyString(),anyString(),anyInt());
        Mockito.doReturn(true).when(deliverySpy).isDeliveryAccepted();

        Mockito.doReturn(true).when(paymentSpy).canPay();
        Mockito.doReturn(receipt).when(paymentSpy).generateReceipt();

        Assertions.assertEquals(cardNumber, paymentSpy.getCardNumber());

        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);

        //in console ne vediamo gli effetti perche stiamo usando spy
        verify(paymentSpy, times(1)).printReceipt();

    }

    @RepeatedTest(3)
    @DisplayName("order successful, argument captor")
    void orderShouldbeCompletedSuccessfully4() {

        Payment paymentMock = Mockito.mock(Payment.class);
        Delivery deliverySpy = Mockito.spy(new Delivery());
        Order order1 = new Order(orderNumber, deliverySpy, paymentMock);

        ArgumentCaptor<String> argument1Captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argument2Captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> argument3Captor = ArgumentCaptor.forClass(Integer.class);

        doReturn(deliverySpy).when(deliverySpy).canDelivery(anyString(),anyString(),anyInt());
        doReturn(true).when(deliverySpy).isDeliveryAccepted();

        when(paymentMock.canPay()).thenReturn(true);
        when(paymentMock.generateReceipt()).thenReturn(receipt);

        Assertions.assertEquals(order1.doOrder(),receipt);

        //catturiamo gli argomenti del metodo CanDelivery
        verify(deliverySpy).canDelivery(argument1Captor.capture(),argument2Captor.capture(), argument3Captor.capture());

        String addressCaptured = argument1Captor.getValue();
        String customerNameCaptured = argument2Captor.getValue();
        Integer trackNumberCaptured = argument3Captor.getValue();

        Assertions.assertEquals(ADDRESS, addressCaptured);
        Assertions.assertEquals(SYSTEM, customerNameCaptured);
        Assertions.assertEquals(orderNumber, trackNumberCaptured);

        verify(paymentMock, times(1)).printReceipt();

    }

    @Test
    @DisplayName("Order successful, reset mock and faker")
    void orderShouldbeCompletedSuccessfully5() {

        Payment paymentMock = Mockito.mock(Payment.class);
        Delivery deliveryMock = Mockito.mock(Delivery.class);

        int orderNumber = faker.number().numberBetween(1,10000);

        Order order1 = new Order(orderNumber, deliveryMock, paymentMock);
        when(deliveryMock.canDelivery(anyString(),anyString(),anyInt())).thenReturn(deliveryMock);
        when(deliveryMock.isDeliveryAccepted()).thenReturn(true);
        when(paymentMock.canPay()).thenReturn(true);
        when(paymentMock.generateReceipt()).thenReturn(receipt);

        org.assertj.core.api.Assertions.assertThat(order1.doOrder()).isEqualTo(receipt);

        verify(paymentMock, times(1)).printReceipt();

        reset(paymentMock);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, order1::doOrder);
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Sorry your payment is not successful, try again");

    }
}