package com.butreik.dmask.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * A configuration properties class that maps the YAML configuration file "bmask.yaml" \
 * with prefix "json-mask" to a Java object.
 * This class is used to retrieve the maskers and their corresponding JSON paths from the YAML file.
 *
 * @author Vladimir Rudnev
 */
@ConfigurationProperties(prefix = "json-mask")
public class MaskerProperties {

    /**
     * The map of maskers to be applied to JSON paths.
     * The keys are masker names to apply, and the values are JSON paths, for which the mask is applied.
     */
    private Map<String, List<String>> maskers;

    public Map<String, List<String>> getMaskers() {
        return maskers;
    }

    public void setMaskers(Map<String, List<String>> maskers) {
        this.maskers = maskers;
    }
}
