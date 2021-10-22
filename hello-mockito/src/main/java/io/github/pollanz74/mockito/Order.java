package io.github.pollanz74.mockito;

import io.github.pollanz74.mockito.exception.DeliveryFailedException;
import io.github.pollanz74.mockito.exception.PaymentFailedExcepiton;
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
        if (delivery.canDelivery(ADDRESS,SYSTEM,this.getOrderNumber()).isDeliveryAccepted()) {
            if (payment.canPay()) {
                receipt = payment.generateReceipt();
                payment.printReceipt();
            } else {
                throw new PaymentFailedExcepiton("Sorry your payment is not successful, try again");
            }
        } else {
            throw new DeliveryFailedException("Sorry, at the moment your order can't be delivered");
        }
        return receipt;
    }

}

