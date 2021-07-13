package com.microservices.netflix.common.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessMessage<T> {

    private ProcessType processType;
    private T content;

}
