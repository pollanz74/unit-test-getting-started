package io.github.pollanz74.mockito;

import java.util.Random;

import static io.github.pollanz74.mockito.constant.Constants.*;

public class Delivery {

    private String address;
    private String customerName;
    private String trackNumber;
    private boolean deliveryAccepted;

    public Delivery() {
    }

    public Delivery(String address, String customerName, boolean deliveryAccepted, String trackNumber) {
        this.address = address;
        this.customerName = customerName;
        this.deliveryAccepted = deliveryAccepted;
        this.trackNumber = trackNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isDeliveryAccepted() {
        return deliveryAccepted;
    }

    public void setDeliveryAccepted(boolean deliveryAccepted) {
        this.deliveryAccepted = deliveryAccepted;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public boolean canDelivery(Order order) {
        Random random = new Random();
        Delivery delivery = new Delivery();
        if(random.nextBoolean()) {
            delivery.setDeliveryAccepted(true);
            delivery.setAddress(ADDRESS);
            delivery.setCustomerName(SYSTEM);
            delivery.setTrackNumber(TRACKER_PREFIX+ random.nextInt());
            order.setDelivery(delivery);
        }
        else {
            delivery.setDeliveryAccepted(false);
            delivery.setAddress(ADDRESS);
            delivery.setCustomerName(SYSTEM);
            delivery.setTrackNumber(TRACKER_PREFIX+ random.nextInt());
            order.setDelivery(delivery);
            return  false;
        }
        return true;
    }
}
