package io.github.pollanz74.mockito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

import static io.github.pollanz74.mockito.constant.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    private String address;
    private String customerName;
    private String trackNumber;
    private boolean deliveryAccepted;

    public Delivery canDelivery(String address,String customerName, Integer trackNumber) {
        this.deliveryAccepted = new Random().nextBoolean();
        this.address=address;
        this.customerName=customerName;
        this.trackNumber=TRACKER_PREFIX+trackNumber;
        return this;
    }
}
