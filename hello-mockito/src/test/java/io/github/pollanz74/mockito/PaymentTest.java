package io.github.pollanz74.mockito;

import com.flextrade.jfixture.JFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static io.github.pollanz74.mockito.constant.Constants.COMPANY_NAME;

@ExtendWith(MockitoExtension.class)
class PaymentTest {

    @Mock
    private Receipt receipt;

    @InjectMocks
    private Payment payment;

    private Date date;

    @BeforeEach
    void init() throws ParseException {

        JFixture fixture = new JFixture();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        /*Date min = dateFormat.parse("2029-10-01");
        Date max = dateFormat.parse("2030-10-31");*/

        Date min = dateFormat.parse("2019-10-01");
        Date max = dateFormat.parse("2020-10-31");

        date = fixture.create().inRange(Date.class, min.getTime(), max.getTime());
    }

    @Disabled
    @DisplayName("can pay is true, card is valid")
    @RepeatedTest(100)
    void canPayReturnTrue() {
        OffsetDateTime dataScadenza = date.toInstant().atOffset(ZoneOffset.ofHours(2));
        System.out.println("data di scadenza: " + dataScadenza);
        payment.setValidUntil(dataScadenza);
        Assertions.assertTrue(payment.canPay());
    }

    @DisplayName("can pay is false, card is expired")
    @RepeatedTest(100)
    void canPayReturnFalse() {
        OffsetDateTime dataScadenza = date.toInstant().atOffset(ZoneOffset.ofHours(2));
        System.out.println("data di scadenza: " + dataScadenza);
        payment.setValidUntil(dataScadenza);
        Assertions.assertFalse(payment.canPay());
    }

    @Test
    @DisplayName("generate correct receipt")
    void generateReceiptOK() {
        //pagamento in bitcoin
        payment = new Payment(1, OffsetDateTime.now(), "B", 100.0, new Receipt());
        Assertions.assertEquals(COMPANY_NAME, payment.generateReceipt().getCompanyName());
        Assertions.assertEquals(0.0, payment.generateReceipt().getVat());
        Assertions.assertEquals(100.0, payment.generateReceipt().getTotal());
        Assertions.assertEquals(100.0, payment.generateReceipt().getNetImport());

        payment = new Payment(1, OffsetDateTime.now(), "$", 100.0, new Receipt());
        Assertions.assertEquals(COMPANY_NAME, payment.generateReceipt().getCompanyName());
        Assertions.assertEquals(100.0 - 10000.0 / 110.0, payment.generateReceipt().getVat());
        Assertions.assertEquals(100.0, payment.generateReceipt().getTotal());
        Assertions.assertEquals(100.0 - (100.0 - 10000.0 / 110.0), payment.generateReceipt().getNetImport());

        payment = new Payment(1, OffsetDateTime.now(), "€", 100.0, new Receipt());
        Assertions.assertEquals(COMPANY_NAME, payment.generateReceipt().getCompanyName());
        Assertions.assertEquals(100.0 - 10000.0 / 122.0, payment.generateReceipt().getVat());
        Assertions.assertEquals(100.0, payment.generateReceipt().getTotal());
        Assertions.assertEquals(100.0 - (100.0 - 10000.0 / 122.0), payment.generateReceipt().getNetImport());


    }

    @Test
    void getVatValue() {
    }
}