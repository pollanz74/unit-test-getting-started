package io.github.pollanz74.mockito;


public class Order {

    private Integer orderNumber;
    private Delivery delivery;
    private Payment payment;

    public Order(Integer orderNumber, Delivery delivery, Payment payment) {
        this.orderNumber = orderNumber;
        this.delivery = delivery;
        this.payment = payment;
    }


    //DO ORDER
    //PERO POSSIAMO TESTARE I CASI DI ECCEZIONE; I CASI POSITIVI;
    // I CASI NEGATIVI; I MOCK DI DUE SERVIZI con le varie risposte(TRUE/TRUE TRUE FALSE ecc)
    public Receipt doOrder() {
       Receipt receipt;
        if(delivery.canDelivery(this)) {
           if(payment.isPaymentAccepted()){
                receipt = payment.generateReceipt();
                payment.printReceipt();
            }
            else {
               throw new RuntimeException("Sorry your payment is not successful, try again");
           }
        } else {
            throw new RuntimeException("Sorry, at the moment your order can't be delivered");
        }
        return receipt;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    //GETTERS AND SETTERS
    public Delivery getDelivery() {
        return delivery;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}

