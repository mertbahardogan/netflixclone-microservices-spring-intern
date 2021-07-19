package com.microservices.netflix.common.messages.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteProcessMessage<T> {
    private FavouriteProcessType filmProcessType;
    private T content;
}
