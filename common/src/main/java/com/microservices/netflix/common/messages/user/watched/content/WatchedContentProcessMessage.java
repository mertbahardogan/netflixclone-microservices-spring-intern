package com.microservices.netflix.common.messages.user.watched.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchedContentProcessMessage <T>{
    private WatchedContentProcessType contentProcessType;
    private T content;
}