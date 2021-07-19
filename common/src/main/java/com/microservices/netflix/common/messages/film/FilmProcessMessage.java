package com.microservices.netflix.common.messages.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmProcessMessage<T> {

    private FilmProcessType filmProcessType;
    private T content;
}
