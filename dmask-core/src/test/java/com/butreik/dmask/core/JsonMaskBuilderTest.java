package com.butreik.dmask.core;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonMaskBuilderTest {

    @Test
    public void filterMapFunctionBuilderTest() throws JSONException {
        JsonMask jsonMask = JsonMaskImpl.builder()
                .filter(input -> "MASKED", fb -> fb.jsonPath("$.value1").jsonPath("$.value2"))
                .build();

        String inputJson = "{ \"value1\":\"one\", \"value2\":\"two\", \"value3\":\"three\" }";
        String expectedJson = "{ \"value1\":\"MASKED\", \"value2\":\"MASKED\", \"value3\":\"three\" }";

        assertEquals(expectedJson, jsonMask.mask(inputJson), true);
    }
}
