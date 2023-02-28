package com.butreik.dmask.core;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static com.butreik.dmask.core.MapFunctions.maskMiddleCharactersMaskers;
import static com.butreik.dmask.core.Maskers.EMAIL_MASKER;
import static com.butreik.dmask.core.Maskers.EXCEPT_FIRST_CHARACTER_MASKER;
import static com.butreik.dmask.core.Maskers.ISO_LOCAL_DATE_MASKER;
import static com.butreik.dmask.core.Maskers.NUMBER_MASKER;
import static com.butreik.dmask.core.Maskers.REMOVE_MASKER;
import static com.butreik.dmask.core.Maskers.SECRET_MASKER;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

/**
 * The JsonMaskTest class contains a test method that tests the functionality of the JsonMaskImpl class.
 *
 * @author Vladimir Rudnev
 */
public class JsonMaskTest {

    @Test
    public void maskTest() throws JSONException {
        // given
        JsonMask jsonMask = JsonMaskImpl.builder()
                .filter(input -> "Full Name", "$..fullName")
                .masker(Masker.builder()
                        .name("phone-masker")
                        .mapFunction(maskMiddleCharactersMaskers(2, 9))
                        .build())
                .filter("phone-masker", "$..phone")
                .filter(SECRET_MASKER, "$..password")
                .filter(NUMBER_MASKER, filtersBuilder -> filtersBuilder
                        .jsonPath("$..id")
                        .jsonPath("$..amount"))
                .filter(EMAIL_MASKER, "$..email")
                .filter(EXCEPT_FIRST_CHARACTER_MASKER, "$..login")
                .filter(REMOVE_MASKER, "$..documents")
                .filter(ISO_LOCAL_DATE_MASKER, "$..birthday")
                .build();

        String inputJson = "{\n" +
                           "    \"id\":15,\n" +
                           "    \"fullName\":\"Julius Caesar\",\n" +
                           "    \"phone\":\"+123456789123\",\n" +
                           "    \"email\":\"prefix@domain.com\",\n" +
                           "    \"amount\":3.14,\n" +
                           "    \"birthday\": \"1990-10-10\",\n" +
                           "    \"login\": \"userlogin\",\n" +
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
                            "    \"fullName\":\"Full Name\",\n" +
                            "    \"phone\":\"+1*******9123\",\n" +
                            "    \"email\":\"******@domain.com\",\n" +
                            "    \"amount\":0,\n" +
                            "    \"birthday\": \"2000-01-01\",\n" +
                            "    \"login\": \"u********\",\n" +
                            "    \"password\":\"******\",\n" +
                            "    \"anyField\":\"value\"\n" +
                            "}";

        //when
        String result = jsonMask.mask(inputJson);
        //then
        assertEquals(maskedJson, result, true);
    }
}
