package com.butreik.dmask.starter;

import com.butreik.dmask.core.JsonMask;
import com.butreik.dmask.core.JsonMaskImpl;
import com.butreik.dmask.core.Masker;
import com.butreik.dmask.core.Maskers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 *
 * This class provides autoconfiguration for the dmask-core library.
 *
 * @author Vladimir Rudnev
 */
@Configuration
@EnableConfigurationProperties(MaskerProperties.class)
@PropertySource(value = "classpath:/bmask.yaml", factory = YamlPropertySourceFactory.class)
public class MaskAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MaskAutoConfiguration.class);

    /**
     * Creates a {@link JsonMask} bean that can be used to mask JSON data.
     * The configuration of the maskers and filters is done using {@link JsonMaskConfigurer} and {@link MaskerProperties}.
     * @param jsonMaskConfigurers ObjectProvider for {@link JsonMaskConfigurer} beans.
     * @param maskerProperties Configuration properties for JSON masking.
     * @return A {@link JsonMask} instance that can be used to mask JSON data.
     */
    @Bean
    @ConditionalOnProperty(name = "json-mask.enabled", havingValue = "true", matchIfMissing = true)
    public JsonMask jsonMask(ObjectProvider<JsonMaskConfigurer> jsonMaskConfigurers,
                             MaskerProperties maskerProperties) {
        JsonMaskImpl.Builder builder = JsonMaskImpl.builder();
        gwtDefaultMaskers().forEach(builder::masker);
        jsonMaskConfigurers.stream().forEach(configurer -> configurer.configure(builder));
        maskerProperties.getMaskers().forEach((maskerMame, paths) ->
                builder.filter(maskerMame, filtersBuilder -> filtersBuilder.jsonPath(paths)));
        logger.info("MaskAutoConfiguration finished successfully");
        return builder.build();
    }

    /**
     * Returns a list of default maskers that are used if no custom maskers are configured.
     * @return List of default maskers.
     */
    private List<Masker> gwtDefaultMaskers() {
        return List.of(
                Maskers.REMOVE_MASKER,
                Maskers.NUMBER_MASKER,
                Maskers.SECRET_MASKER,
                Maskers.REPLACE_STRING_MASKER,
                Maskers.EMAIL_MASKER,
                Maskers.ISO_OFFSET_DATE_MASKER,
                Maskers.ISO_OFFSET_TIME_MASKER,
                Maskers.ISO_OFFSET_DATE_TIME,
                Maskers.ISO_LOCAL_DATE_MASKER,
                Maskers.ISO_LOCAL_TIME_MASKER,
                Maskers.ISO_LOCAL_DATE_TIME_MASKER,
                Maskers.ISO_INSTANT_MASKER,
                Maskers.EXCEPT_FIRST_CHARACTER_MASKER
        );
    }

    /**
     * Creates a stub implementation of {@link JsonMask} bean that does not mask any data.
     * This bean is used if the JSON masking feature is disabled.
     * @return A stub implementation of {@link JsonMask} bean that does not mask any data.
     */
    @Bean
    @ConditionalOnMissingBean
    public JsonMask jsonMaskStub() {
        logger.info("MaskAutoConfiguration finished with stub implementation for JsonMask");
        return input -> input;
    }
}
