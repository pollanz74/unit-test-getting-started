package io.github.pollanz74.mockito;

import java.util.logging.Logger;

import static io.github.pollanz74.mockito.constant.Constants.COMPANY_NAME;

public class Payment {

    private static Logger logger = Logger.getLogger(Payment.class.getName());
    private String cardNumber;
    private String currency;
    private Double total;
    private boolean paymentAccepted;
    private Receipt receipt;


    public Receipt generateReceipt() {
        receipt.setCompanyName(COMPANY_NAME);
        receipt.setTotal(this.getTotal());
        receipt.setVat(getVatValue(this));
        receipt.setNetImport(receipt.getTotal() - receipt.getVat());
        return receipt;
    }

    void printReceipt() {
        System.out.println("thank you client, this is your receipt: "+ this.receipt);
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
        double vatValue = payment.getTotal() * 100 / 100 + vatPerc;
        logger.info("vat value is: " + vatValue);
        return payment.getTotal() * 100 / 100 + vatPerc;
    }

    public Payment() {
    }

    public Payment(String cardNumber, String currency, Double total, boolean paymentAccepted) {
        this.cardNumber = cardNumber;
        this.currency = currency;
        this.total = total;
        this.paymentAccepted = paymentAccepted;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean isPaymentAccepted() {
        return paymentAccepted;
    }

    public void setPaymentAccepted(boolean paymentAccepted) {
        this.paymentAccepted = paymentAccepted;
    }

}
