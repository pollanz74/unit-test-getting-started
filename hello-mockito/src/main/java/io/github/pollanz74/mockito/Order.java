package io.github.pollanz74.mockito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.github.pollanz74.mockito.constant.Constants.ADDRESS;
import static io.github.pollanz74.mockito.constant.Constants.SYSTEM;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer orderNumber;
    private Delivery delivery;
    private Payment payment;

    public Receipt doOrder() {
        Receipt receipt;
        if (delivery.canDelivery(ADDRESS,SYSTEM,10).isDeliveryAccepted()) {
            if (payment.isPaymentAccepted()) {
                receipt = payment.generateReceipt();
                payment.printReceipt();
            } else {
                throw new RuntimeException("Sorry your payment is not successful, try again");
            }
        } else {
            throw new RuntimeException("Sorry, at the moment your order can't be delivered");
        }
        return receipt;
    }

}

