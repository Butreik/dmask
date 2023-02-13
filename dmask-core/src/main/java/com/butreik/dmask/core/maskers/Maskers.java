package com.butreik.dmask.core.maskers;

@SuppressWarnings("rawtypes")
public interface Maskers {

    Masker REMOVE_MASKER = RemoveMasker.INSTANCE;

    Masker NUMBER_MASKER = NumberMasker.builder().name("NumberMasker").build();

    Masker SECRET_MASKER = ReplaceStringMasker.builder().name("SecretMasker").build();

    Masker REPLACE_STRING_MASKER = ReplaceStringMasker.builder().name("ReplaceStringMasker").build();

    Masker ISO_OFFSET_DATE_MASKER = ReplaceStringMasker.builder()
            .name("IsoOffsetDateMasker").replacementString("2000-01-01+01:00").build();

    Masker ISO_OFFSET_TIME_MASKER = ReplaceStringMasker.builder()
            .name("IsoOffsetTimeMasker").replacementString("00:00+01:00").build();

    Masker ISO_OFFSET_DATE_TIME = ReplaceStringMasker.builder()
            .name("IsoOffsetDateTime").replacementString("2000-01-01T00:00:00+01:00").build();

    Masker ISO_LOCAL_DATE_MASKER = ReplaceStringMasker.builder()
            .name("IsoLocalDateMasker").replacementString("2000-01-01").build();

    Masker ISO_LOCAL_TIME_MASKER = ReplaceStringMasker.builder()
            .name("IsoLocalTimeMasker").replacementString("00:00").build();

    Masker ISO_LOCAL_DATE_TIME_MASKER = ReplaceStringMasker.builder()
            .name("IsoLocalDateTimeMasker").replacementString("2000-01-01T00:00:00").build();

    Masker ISO_INSTANT_MASKER = ReplaceStringMasker.builder()
            .name("IsoInstantMasker").replacementString("2000-01-01T00:00:00Z").build();

    Masker EXCEPT_FIRST_CHARACTER_MASKER = ReplaceMiddleCharactersMaskers.builder()
            .name("ExceptFirstCharacterMasker").from(1).build();
}
