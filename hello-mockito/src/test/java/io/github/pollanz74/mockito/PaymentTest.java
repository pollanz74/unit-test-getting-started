package io.github.pollanz74.mockito;

import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import org.assertj.core.error.ShouldBeOdd;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static com.flextrade.jfixture.FixtureAnnotations.initFixtures;
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

        /*  Date min = dateFormat.parse("2029-10-01");
        Date max = dateFormat.parse("2030-10-31");*/

        Date min = dateFormat.parse("2019-10-01");
        Date max = dateFormat.parse("2020-10-31");

        date = fixture.create().inRange(Date.class, min.getTime(), max.getTime());
    }

    @DisplayName("can pay is true, card is valid")
    @RepeatedTest(100)
    void canPayReturnTrue(){
        OffsetDateTime dataScadenza  = date.toInstant().atOffset(ZoneOffset.ofHours(2));
        System.out.println("data di scadenza: "+dataScadenza);
        payment.setValidUntil(dataScadenza);
        Assertions.assertTrue(payment.canPay());
    }

    @DisplayName("can pay is false, card is expired")
    @RepeatedTest(100)
    void canPayReturnFalse(){
        OffsetDateTime dataScadenza  = date.toInstant().atOffset(ZoneOffset.ofHours(2));
        System.out.println("data di scadenza: "+dataScadenza);
        payment.setValidUntil(dataScadenza);
        Assertions.assertFalse(payment.canPay());
    }

    @Test
    @DisplayName("generate correct receipt")
    void generateReceiptOK() {
       /* receipt.setCompanyName(COMPANY_NAME);
        receipt.setTotal(this.getTotal());
        receipt.setVat(getVatValue(this));
        receipt.setNetImport(receipt.getTotal() - receipt.getVat());*/
    }

    @Test
    void getVatValue() {
    }
}