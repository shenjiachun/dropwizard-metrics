package io.dropwizard.metrics.influxdb.utils;

import static io.dropwizard.metrics.influxdb.utils.InfluxDbWriteObjectSerializer.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.Before;
import org.junit.Test;

public class InfluxDbWriteObjectSerializerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        final SimpleModule module = new SimpleModule("SimpleModule", new Version(1, 0, 0, null, null, null));
        module.addSerializer(Map.class, new MapSerializer());
        objectMapper.registerModule(module);
    }

    @Test
    public void serializeStringMap() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("key", "value");
        final String json = objectMapper.writeValueAsString(testMap);

        assertThat(json).isNotEmpty();
        assertThat(json).isEqualTo("{\"key\":\"value\"}");
    }

    @Test
    public void serializeStringObjectMap() throws Exception {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("intObject", 10);
        final String json = objectMapper.writeValueAsString(testMap);

        assertThat(json).isNotEmpty();
        assertThat(json).isEqualTo("{\"intObject\":10}");
    }
}
