package com.qfleaf.cosmosimserver.shared.ws;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WsMessage<T> implements Serializable {
    private String type;
    private T data;
    private long timestamp;

    public static <T> WsMessage<T> create(String type, T data, long timestamp) {
        return new WsMessage<>(type, data, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WsMessage<?> wsMessage = (WsMessage<?>) o;
        return Objects.equals(type, wsMessage.type) && Objects.equals(data, wsMessage.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }
}
