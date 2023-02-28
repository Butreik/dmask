DMASK - A JSON data masking library
=====

[![javadoc](https://javadoc.io/badge2/com.butreik.dmask/dmask-core/javadoc.svg)](https://javadoc.io/doc/com.butreik.dmask/dmask-core)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.butreik.dmask/dmask-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.butreik-dmask/dmask-core)
 **dmask-core**

[![javadoc](https://javadoc.io/badge2/com.butreik.dmask/dmask-spring-boot-starter/javadoc.svg)](https://javadoc.io/doc/com.butreik.dmask/dmask-spring-boot-starter)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.butreik.dmask/dmask-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.butreik-dmask/dmask-spring-boot-starter)
**dmask-spring-boot-starter**

**DMASK** is a library that provides a way to mask sensitive data in JSON structures. It is especially useful when you need to log or transmit JSON data that contains sensitive information like passwords, credit card numbers, etc.

The library allows you to specify the [JSON path](https://github.com/json-path/JsonPath) to the sensitive data and then apply one of the available maskers that will replace the original data with the masked version.

Features
--------
- Supports masking of values based on JSON path expressions. Uses the [Jayway JSONPath](https://github.com/json-path/JsonPath) library.
- Provides several built-in maskers, including number masking, secret masking, date masking, and more.
- Allows defining custom maskers
- Offers flexible configuration options through YAML configuration files (if you use `dmask-spring-boot-starter`)

Getting Started
---------------
JsonPath is available at the Central Maven Repository. Maven users add this to your POM.

```xml
<dependency>
    <groupId>com.butreik.dmask</groupId>
    <artifactId>dmask-core</artifactId>
    <version>1.0.0</version>
</dependency>
```
In spring-boot applications add this to your POM.
```xml
<dependency>
    <groupId>com.butreik.dmask</groupId>
    <artifactId>dmask-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

Basic usage (dmask-core)
------------------------
To use JsonMask, you need to create an instance of JsonMask and then use it to mask your JSON data. Here's an example:
```java
JsonMask jsonMask = JsonMaskImpl.builder()
        .filter(input -> "Full Name", "$..fullName")
        .filter(EXCEPT_FIRST_CHARACTER_MASKER, "$..login")
        .filter(SECRET_MASKER, "$..password")
        .build();

String input = "{\n" +
        "    \"fullName\":\"Julius Caesar\",\n" +
        "    \"login\": \"userlogin\",\n" +
        "    \"password\":\"Qwerty123\"\n" +
        "}";
String output = jsonMask.mask(input);

System.out.println(output); 
// { "fullName": "Full Name", "login": "u********", "password": "******" }
```
In this example, we create a JsonMask instance using the JsonMaskImpl.builder() method.

Finally, we apply the filters to the input JSON payload using the mask() method, which returns the masked output.

Built-in maskers
---------------------------
DMASK also provides several built-in maskers that you can use out of the box. They defined in `Maskers`

| Constant                        | Name                            | Description                                                                                                                        |
|:--------------------------------|:--------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| `REMOVE_MASKER`                 | `remove-masker`                 | removes JSON block from the input value                                                                                            |
| `NUMBER_MASKER`                 | `number-masker`                 | replaces all digits in the input value with an 0                                                                                   |
| `SECRET_MASKER`                 | `secret-masker`                 | replaces the entire input value with a constant string `******`, which is usually used to hide sensitive information               |
| `REPLACE_STRING_MASKER`         | `replace-string-masker`         | replaces the entire input value with a constant string `******`                                                                    |
| `EMAIL_MASKER`                  | `email-masker`                  | replaces email addresses prefix with the asterisks.                                                                                |
| `EXCEPT_FIRST_CHARACTER_MASKER` | `except-first-character-masker` | replaces all but the first character in a string. Useful for masking sensitive information while still showing the first character |
| `ISO_OFFSET_DATE_MASKER`        | `iso-offset-date-masker`        | replaces an ISO offset date string (e.g., '2022-02-15+01:00`) with a constant string `2000-01-01+01:00`                            |
| `ISO_OFFSET_TIME_MASKER`        | `iso-offset-time-masker`        | replaces an ISO offset time string (e.g., `01:23:45+01:00`) with a constant string `00:00:00+01:00 `                               |
| `ISO_OFFSET_DATE_TIME`          | `iso-offset-date-time-masker`   | replaces an ISO offset date-time string (e.g., `2022-02-15T01:23:45+01:00`) with a constant string `2000-01-01T00:00:00+01:00`     |
| `ISO_LOCAL_DATE_MASKER`         | `iso-local-date-masker`         | replaces an ISO local date string (e.g., `2022-02-15`) with a constant string `2000-01-01`                                         |
| `ISO_LOCAL_TIME_MASKER`         | `iso-local-time-masker`         | replaces an ISO local time string (e.g., `01:23:45`) with a constant string `00:00`                                                |
| `ISO_LOCAL_DATE_TIME_MASKER`    | `iso-local-date-time-masker`    | replaces an ISO local date-time string (e.g., `2022-02-15T01:23:45`) with a constant string `2000-01-01T00:00:00`                  |
| `ISO_INSTANT_MASKER`            | `iso-instant-masker`            | replaces an ISO instant string (e.g., `2022-02-15T01:23:45Z`) with a constant string `2000-01-01T00:00:00Z`                        |


Custom masker
-------------------------
You also can define custom maskers and use them in builder by name
```java
JsonMask jsonMask = JsonMaskImpl.builder()
        .masker(Masker.builder()
                .name("phone-masker")
                .mapFunction(maskMiddleCharactersMaskers(2, 9))
                .build())
        .filter("phone-masker", "$..phone")
        .build();
```

dmask-spring-boot-starter
-------------------------
After adding the library `dmask-spring-boot-starter` you can use DMASK in your application like this:
```java
@Autowired
private JsonMask jsonMask;

String maskedJson = jsonMask.mask(jsonString);
```
You can also configure DMASK programmatically using Spring Boot. Here is an example configuration class:
```java
@Configuration
public class MyConfig {

    @Bean
    public JsonMaskConfigurer configMasker() {
        return builder -> builder
                .masker(Masker.builder()
                        .name("phone-masker")
                        .mapFunction(maskMiddleCharactersMaskers(2, 9))
                        .build());
    }
}
```
By default, `dmask-spring-boot-starter` looks for a YAML configuration file named `bmask.yaml` in the classpath. 
Here is an example configuration:
```yaml
json-mask:
  maskers:
    secret-masker:
      - $..secret
      - $..password
    remove-masker:
      - $..documents
    number-masker:
      - $..id
      - $..amount
    phone-masker:
      - $..phone
    email-masker:
      - $..email
    iso-local-date-masker:
      - $..birthday
```

Conclusion
----------
DMASK is a simple and powerful library for masking sensitive data in JSON structures. 
It provides a convenient way to apply different maskers to different JSON paths, making it easy to customize the masking process to your specific needs.