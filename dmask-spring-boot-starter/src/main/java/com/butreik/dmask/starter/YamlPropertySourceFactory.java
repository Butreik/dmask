package com.butreik.dmask.starter;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Properties;

/**
 * A custom implementation of Spring's {@link PropertySourceFactory} that creates a {@link PropertiesPropertySource}
 * from a YAML file.
 * <p>
 * This implementation uses {@link YamlPropertiesFactoryBean} to load the YAML file and convert it to a {@link Properties}
 * object, which is then used to create a {@link PropertiesPropertySource} with the name of the YAML file as the source name.
 * </p>
 * <p>
 * Usage: To use this factory, simply annotate a configuration class or property source with {@link org.springframework.context.annotation.PropertySource}
 * and set the {@code factory} attribute to an instance of this class.
 * </p>
 * @see org.springframework.context.annotation.PropertySource
 * @see org.springframework.beans.factory.config.YamlPropertiesFactoryBean
 * @see org.springframework.core.env.PropertiesPropertySource
 *
 * @author Vladimir Rudnev
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = factory.getObject();

        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}
