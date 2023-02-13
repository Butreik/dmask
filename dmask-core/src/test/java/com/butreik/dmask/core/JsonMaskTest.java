package com.butreik.dmask.core;

import com.butreik.dmask.core.maskers.Maskers;
import com.butreik.dmask.core.validate.AssertException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonMaskTest {

    @Test
    public void maskTest() throws JSONException {
        // given
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.builder().jsonPath("$..documents").masker(Maskers.REMOVE_MASKER).build());
        filters.add(Filter.builder().jsonPath("$..password").masker(Maskers.SECRET_MASKER).build());
        filters.add(Filter.builder().jsonPath("$..id").masker(Maskers.NUMBER_MASKER).build());
        filters.add(Filter.builder().jsonPath("$..notNumber").masker(Maskers.NUMBER_MASKER).build());
        filters.add(Filter.builder().jsonPath("$..amount").masker(Maskers.NUMBER_MASKER).build());
        filters.add(Filter.builder().jsonPath("$..another").masker(Maskers.NUMBER_MASKER).build());
        JsonMask jsonMask = new JsonMask(filters);
        String inputJson = """
                {
                    "id":15,
                    "amount":3.14,
                    "notNumber":"notNumber",
                    "password":"Qwerty123",
                    "anyField":"value",
                    "documents":[
                        {
                            "id":123
                        },
                        {
                            "id":234
                        }
                    ]
                }""";
        String maskedJson = """
                {
                    "id":0,
                    "amount":0,
                    "notNumber":"notNumber",
                    "password":"******",
                    "anyField":"value"
                }""";

        //when
        String result = jsonMask.mask(inputJson);
        //then
        assertEquals(maskedJson, result, true);
    }

    @Test
    public void assertTest() {
        assertThrows(AssertException.class, () -> new JsonMask(new ArrayList<>()));
        assertThrows(AssertException.class, () -> new JsonMask(null));
    }
}
