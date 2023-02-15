package com.butreik.dmask.core;

import com.butreik.dmask.core.maskers.ReplaceMiddleCharactersMaskers;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.butreik.dmask.core.maskers.Maskers.ISO_LOCAL_DATE_MASKER;
import static com.butreik.dmask.core.maskers.Maskers.NUMBER_MASKER;
import static com.butreik.dmask.core.maskers.Maskers.REMOVE_MASKER;
import static com.butreik.dmask.core.maskers.Maskers.SECRET_MASKER;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonMaskTest {

    @Test
    public void maskTest() throws JSONException {
        // given
        JsonMask jsonMask = JsonMask.builder()
                .masker(ReplaceMiddleCharactersMaskers.builder().name("PhoneMasker").from(2).to(9).build())
                .filter("PhoneMasker", "$..phone")
                .filter(NUMBER_MASKER, filtersBuilder -> filtersBuilder
                        .jsonPath("$..id")
                        .jsonPath("$..amount"))
                .filter(NUMBER_MASKER, filtersBuilder -> filtersBuilder
                        .jsonPath("$..notNumber")
                        .jsonPath("$..another"))
                .filter(REMOVE_MASKER, "$..documents")
                .filter(SECRET_MASKER, "$..password")
                .filter("SecretMasker", filtersBuilder -> filtersBuilder
                        .jsonPath(List.of("$..secret", "$..pass")))
                .filter(ISO_LOCAL_DATE_MASKER, "$..birthday")
                .build();

        String inputJson = "{\n" +
                           "    \"id\":15,\n" +
                           "    \"phone\":\"+123456789123\",\n" +
                           "    \"amount\":3.14,\n" +
                           "    \"birthday\": \"1990-10-10\",\n" +
                           "    \"notNumber\":\"notNumber\",\n" +
                           "    \"password\":\"Qwerty123\",\n" +
                           "    \"anyField\":\"value\",\n" +
                           "    \"documents\":[\n" +
                           "        {\n" +
                           "            \"id\":123\n" +
                           "        },\n" +
                           "        {\n" +
                           "            \"id\":234\n" +
                           "        }\n" +
                           "    ]\n" +
                           "}";
        String maskedJson = "{\n" +
                            "    \"id\":0,\n" +
                            "    \"phone\":\"+1*******9123\",\n" +
                            "    \"amount\":0,\n" +
                            "    \"birthday\": \"2000-01-01\",\n" +
                            "    \"notNumber\":\"notNumber\",\n" +
                            "    \"password\":\"******\",\n" +
                            "    \"anyField\":\"value\"\n" +
                            "}";

        //when
        String result = jsonMask.mask(inputJson);
        //then
        assertEquals(maskedJson, result, true);
    }
}
