package com.qfleaf.cosmosimserver.log.domain;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpsLogEntity {
    private Long id;
    private String ipAddress;
    private String opsName;
    private String args;
    private Object result;
    private Instant opsTime;
}
