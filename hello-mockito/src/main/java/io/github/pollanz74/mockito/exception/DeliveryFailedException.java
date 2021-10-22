package io.github.pollanz74.mockito.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryFailedException extends RuntimeException {

    private final String message;
}
