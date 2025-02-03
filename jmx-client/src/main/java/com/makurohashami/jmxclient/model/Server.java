package com.makurohashami.jmxclient.model;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    private String url;
    private long connectionTimeout;
    private boolean useSSL;
    private Map<String, String> config = new HashMap<>();

}
