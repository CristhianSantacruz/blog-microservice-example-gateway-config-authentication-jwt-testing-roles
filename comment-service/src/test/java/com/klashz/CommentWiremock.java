package com.klashz;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.WireMockServer.*;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class CommentWiremock implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServe;

    @Override
    public Map<String, String> start() {
        String postJson = "{\n" +
                "    \"id\": \"bd4b8cfa-822a-4e6c-bcd9-c0cd23eb6ffc\",\n" +
                "    \"title\": \"Quarkus\",\n" +
                "    \"description\": \"Quarkus is a framework...\",\n" +
                "    \"imageUrl\": \"http://image.example.com\",\n" +
                "    \"localdate\": \"2024-04-18\",\n" +
                "    \"likeCount\": 0,\n" +
                "    \"tags\": [\n" +
                "        \"string\",\n" +
                "        \"kotlin\"\n" +
                "    ],\n" +
                "    \"viewCount\": 0\n" +
                "}";

        wireMockServe = new WireMockServer(9090);
        wireMockServe.start();
        wireMockServe.stubFor(
                get(urlEqualTo("/post/all"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(postJson)));
        return Collections.singletonMap("http://localhost:9090", wireMockServe.baseUrl() + "/post/all");
    }



    @Override
    public void stop() {
        if(wireMockServe != null) {
            wireMockServe.stop();
        }
    }
}
