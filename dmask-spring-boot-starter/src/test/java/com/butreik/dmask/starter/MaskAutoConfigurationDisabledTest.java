package com.butreik.dmask.starter;

import com.butreik.dmask.core.JsonMask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.butreik.dmask.starter.MaskAutoConfigurationTest.load;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"json-mask.enabled=false"}, classes = MaskAutoConfiguration.class)
public class MaskAutoConfigurationDisabledTest {

    private static final String INPUT_JSON_FILE = "input.json";

    @Autowired
    private JsonMask jsonMask;

    @Test
    public void jsonMaskDisabledTest() {
        String inputJson = load(INPUT_JSON_FILE);
        String result = jsonMask.mask(inputJson);
        assertEquals(inputJson, result);
    }
}
