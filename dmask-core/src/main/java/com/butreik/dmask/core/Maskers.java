package com.butreik.dmask.core;

import static com.butreik.dmask.core.MapFunctions.DEFAULT_REPLACEMENT_NUMBER;
import static com.butreik.dmask.core.MapFunctions.DEFAULT_REPLACEMENT_STRING;

/**
 * A collection of predefined maskers.
 *
 * @author Vladimir Rudnev
 */
public interface Maskers {

    /**
     * A masker that removes JSON block from the input value.
     */
    Masker REMOVE_MASKER = Masker.builder().name("remove-masker").order(Integer.MIN_VALUE)
            .mapFunction(src -> null).build();

    /**
     * A masker that replaces all digits in the input value with an 0.
     */
    Masker NUMBER_MASKER = Masker.builder().name("number-masker")
            .mapFunction(input -> DEFAULT_REPLACEMENT_NUMBER).build();

    /**
     * A masker that replaces the entire input value with a constant string "******",
     * which is usually used to hide sensitive information.
     */
    Masker SECRET_MASKER = Masker.builder().name("secret-masker")
            .mapFunction(input -> DEFAULT_REPLACEMENT_STRING).build();

    /**
     * A masker that replaces the entire input value with a constant string "******".
     */
    Masker REPLACE_STRING_MASKER = Masker.builder().name("replace-string-masker")
            .mapFunction(input -> DEFAULT_REPLACEMENT_STRING).build();

    /**
     * A masker that replaces email addresses prefix with the asterisks.
     */
    Masker EMAIL_MASKER = Masker.builder().name("email-masker")
            .mapFunction(MapFunctions.maskEmail()).build();

    /**
     * A masker that replaces all but the first character in a string with the asterisks.
     * Useful for masking sensitive information while still showing the first character.
     */
    Masker EXCEPT_FIRST_CHARACTER_MASKER = Masker.builder().name("except-first-character-masker")
            .mapFunction(MapFunctions.maskExceptFirstCharacter()).build();

    /**
     * A masker that replaces an ISO offset date string (e.g., 2022-02-15+01:00)
     * with a constant string (2000-01-01+01:00).
     */
    Masker ISO_OFFSET_DATE_MASKER = Masker.builder().name("iso-offset-date-masker")
            .mapFunction(input -> "2000-01-01+01:00").build();

    /**
     * A masker that replaces an ISO offset time string (e.g., 01:23:45+01:00)
     * with a constant string (00:00:00+01:00).
     */
    Masker ISO_OFFSET_TIME_MASKER = Masker.builder().name("iso-offset-time-masker")
            .mapFunction(input -> "00:00:00+01:00").build();

    /**
     * A masker that replaces an ISO offset date-time string (e.g., 2022-02-15T01:23:45+01:00)
     * with a constant string (2000-01-01T00:00:00+01:00).
     */
    Masker ISO_OFFSET_DATE_TIME = Masker.builder().name("iso-offset-date-time-masker")
            .mapFunction(input -> "2000-01-01T00:00:00+01:00").build();

    /**
     * A masker that replaces an ISO local date string (e.g., 2022-02-15)
     * with a constant string (2000-01-01).
     */
    Masker ISO_LOCAL_DATE_MASKER = Masker.builder().name("iso-local-date-masker")
            .mapFunction(input -> "2000-01-01").build();

    /**
     * A masker that replaces an ISO local time string (e.g., 01:23:45)
     * with a constant string (00:00).
     */
    Masker ISO_LOCAL_TIME_MASKER = Masker.builder().name("iso-local-time-masker")
            .mapFunction(input -> "00:00").build();

    /**
     * A masker that replaces an ISO local date-time string (e.g., 2022-02-15T01:23:45)
     * with a constant string (2000-01-01T00:00:00).
     */
    Masker ISO_LOCAL_DATE_TIME_MASKER = Masker.builder().name("iso-local-date-time-masker")
            .mapFunction(input -> "2000-01-01T00:00:00").build();

    /**
     * A masker that replaces an ISO instant string (e.g., 2022-02-15T01:23:45Z)
     * with a constant string (2000-01-01T00:00:00Z).
     */
    Masker ISO_INSTANT_MASKER = Masker.builder().name("iso-instant-masker")
            .mapFunction(input -> "2000-01-01T00:00:00Z").build();
}
