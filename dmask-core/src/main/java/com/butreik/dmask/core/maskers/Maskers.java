package com.butreik.dmask.core.maskers;

/**
 * A collection of predefined maskers that implement the Masker interface.
 *
 * @author Vladimir Rudnev
 */
@SuppressWarnings("rawtypes")
public interface Maskers {

    /**
     * A masker that removes JSON block from the input value.
     */
    Masker REMOVE_MASKER = RemoveMasker.INSTANCE;

    /**
     * A masker that replaces all digits in the input value with an 0.
     */
    Masker NUMBER_MASKER = NumberMasker.builder().name("NumberMasker").build();

    /**
     * A masker that replaces the entire input value with a constant string "******",
     * which is usually used to hide sensitive information.
     */
    Masker SECRET_MASKER = ReplaceStringMasker.builder().name("SecretMasker").build();

    /**
     * A masker that replaces the entire input value with a constant string "******".
     */
    Masker REPLACE_STRING_MASKER = ReplaceStringMasker.builder().name("ReplaceStringMasker").build();

    /**
     * A masker that replaces an ISO offset date string (e.g., 2022-02-15+01:00)
     * with a constant string (2000-01-01+01:00).
     */
    Masker ISO_OFFSET_DATE_MASKER = ReplaceStringMasker.builder()
            .name("IsoOffsetDateMasker").replacementString("2000-01-01+01:00").build();

    /**
     * A masker that replaces an ISO offset time string (e.g., 01:23:45+01:00)
     * with a constant string (00:00:00+01:00).
     */
    Masker ISO_OFFSET_TIME_MASKER = ReplaceStringMasker.builder()
            .name("IsoOffsetTimeMasker").replacementString("00:00:00+01:00").build();

    /**
     * A masker that replaces an ISO offset date-time string (e.g., 2022-02-15T01:23:45+01:00)
     * with a constant string (2000-01-01T00:00:00+01:00).
     */
    Masker ISO_OFFSET_DATE_TIME = ReplaceStringMasker.builder()
            .name("IsoOffsetDateTime").replacementString("2000-01-01T00:00:00+01:00").build();

    /**
     * A masker that replaces an ISO local date string (e.g., 2022-02-15)
     * with a constant string (2000-01-01).
     */
    Masker ISO_LOCAL_DATE_MASKER = ReplaceStringMasker.builder()
            .name("IsoLocalDateMasker").replacementString("2000-01-01").build();

    /**
     * A masker that replaces an ISO local time string (e.g., 01:23:45)
     * with a constant string (00:00).
     */
    Masker ISO_LOCAL_TIME_MASKER = ReplaceStringMasker.builder()
            .name("IsoLocalTimeMasker").replacementString("00:00").build();

    /**
     * A masker that replaces an ISO local date-time string (e.g., 2022-02-15T01:23:45)
     * with a constant string (2000-01-01T00:00:00).
     */
    Masker ISO_LOCAL_DATE_TIME_MASKER = ReplaceStringMasker.builder()
            .name("IsoLocalDateTimeMasker").replacementString("2000-01-01T00:00:00").build();

    /**
     * A masker that replaces an ISO instant string (e.g., 2022-02-15T01:23:45Z)
     * with a constant string (2000-01-01T00:00:00Z).
     */
    Masker ISO_INSTANT_MASKER = ReplaceStringMasker.builder()
            .name("IsoInstantMasker").replacementString("2000-01-01T00:00:00Z").build();

    /**
     * A masker that replaces all but the first character in a string with a {@link Masker#DEFAULT_REPLACEMENT_CHAR}.
     * Useful for masking sensitive information while still showing the first character.
     */
    Masker EXCEPT_FIRST_CHARACTER_MASKER = ReplaceMiddleCharactersMaskers.builder()
            .name("ExceptFirstCharacterMasker").from(1).build();
}
