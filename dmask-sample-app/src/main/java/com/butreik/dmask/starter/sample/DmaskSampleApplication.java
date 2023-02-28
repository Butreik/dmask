package com.butreik.dmask.starter.sample;

import com.butreik.dmask.core.JsonMask;
import com.butreik.dmask.core.Masker;
import com.butreik.dmask.starter.JsonMaskConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.butreik.dmask.core.MapFunctions.maskMiddleCharactersMaskers;

@SpringBootApplication
public class DmaskSampleApplication implements CommandLineRunner {

    private JsonMask jsonMask;

    @Configuration
    public static class MaskConfiguration {

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
    public void setJsonMask(JsonMask jsonMask) {
        this.jsonMask = jsonMask;
    }

    public static void main(String[] args) {
        SpringApplication.run(DmaskSampleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(jsonMask.mask(load("input.json")));
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