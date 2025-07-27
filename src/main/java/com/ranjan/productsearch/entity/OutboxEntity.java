package com.ranjan.productsearch.entity;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class OutboxEntity {
    private String id;
    private String aggregateType;
    private String aggregateId;
    private String type;
    private String payload;
    private Instant timestamp;
    private EventStatus status;

    public enum EventStatus{
        PENDING,SENT,FAILED;
    }
}
