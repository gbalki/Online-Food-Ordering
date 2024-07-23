package com.balki.request;

import com.balki.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Address deliveryAddress;
}
