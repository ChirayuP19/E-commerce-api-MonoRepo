package com.chirayu.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerExistException extends RuntimeException {
    public final String message;
}
