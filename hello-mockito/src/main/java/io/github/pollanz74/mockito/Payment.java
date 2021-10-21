package io.github.pollanz74.mockito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

import static io.github.pollanz74.mockito.constant.Constants.COMPANY_NAME;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Integer cardNumber;
    private OffsetDateTime validUntil;
    private String currency;
    private Double total;
    private Receipt receipt;


    public boolean canPay() {
        return OffsetDateTime.now().isBefore(validUntil);
    }

    public Receipt generateReceipt() {
        receipt.setCompanyName(COMPANY_NAME);
        receipt.setTotal(this.getTotal());
        receipt.setVat(getVatValue(this));
        receipt.setNetImport(receipt.getTotal() - receipt.getVat());
        return receipt;
    }

    public void printReceipt() {
        System.out.println("thank you client, this is your receipt: " + this.receipt);
    }

    public Double getVatValue(Payment payment) {
        //se euro 22% se dollaro 10 se Bitcoin 0%
        double vatPerc;
        switch (payment.getCurrency()) {
            case "€":
                vatPerc = 22.0;
                break;
            case "$":
                vatPerc = 10.0;
                break;
            case "B":
                vatPerc = 0.0;
                break;
            default:
                vatPerc = 22.0;
        }
        //scorporo dell'iva
        return (payment.getTotal() - payment.getTotal() * 100 / (100 + vatPerc));
    }

}
