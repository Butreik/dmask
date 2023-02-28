package com.butreik.dmask.starter;

import com.butreik.dmask.core.JsonMask;
import com.butreik.dmask.core.Masker;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.butreik.dmask.core.MapFunctions.maskMiddleCharactersMaskers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MaskAutoConfiguration.class, MaskAutoConfigurationTest.TestConfig.class})
public class MaskAutoConfigurationTest {

    private static final String INPUT_JSON_FILE = "input.json";
    private static final String OUTPUT_JSON_FILE = "output.json";

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public JsonMaskConfigurer configMasker() {
            return builder -> builder
                    .masker(Masker.builder()
                            .name("phone-masker")
                            .mapFunction(maskMiddleCharactersMaskers(2, 9))
                            .build());
        }
    }

    @Autowired
    private JsonMask jsonMask;

    @Test
    public void jsonMaskTest() throws JSONException {
        String inputJson = load(INPUT_JSON_FILE);
        String outputJson = load(OUTPUT_JSON_FILE);
        String result = jsonMask.mask(inputJson);
        JSONAssert.assertEquals(outputJson, result, true);
    }

    public static String load(String resource) {
        Resource cpr = new ClassPathResource(resource);
        try (InputStream is = cpr.getInputStream()) {
            return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
