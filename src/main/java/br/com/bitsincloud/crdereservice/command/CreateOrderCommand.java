package br.com.bitsincloud.crdereservice.command;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateOrderCommand implements Serializable {
    private String orderId;
    private BigDecimal amount;
}
