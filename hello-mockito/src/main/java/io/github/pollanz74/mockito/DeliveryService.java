package io.github.pollanz74.mockito;

public class DeliveryService {

    public boolean process(OrderService.Order order) {
        return order.isPaymentDone();
    }

}
