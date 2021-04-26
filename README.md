# `Iuliia`

> Transliterate Cyrillic → Latin in every possible way

Transliteration means representing Cyrillic data (mainly names and geographic locations) with Latin letters. It is used for international passports, visas, green cards, driving licenses, mail and goods delivery etc.

## Why use `Iuliia`

-   [20 transliteration schemas](https://github.com/nalgeon/iuliia/blob/master/README.md#supported-schemas) (rule sets), including all main international and Russian standards.
-   Correctly implements not only the base mapping, but all the special rules for letter combinations and word endings (AFAIK, Iuliia is the only library which does so).
-   Simple API.

For schema details and other information, see <https://dangry.ru/iuliia> (in Russian).

## Requirements

-   Java 8 or higher
-   Jackson 2 (see [another solution](https://github.com/massita99/iuliia-java) if you prefer Gson)

## Installation
Maven dependency (works fine with maven 3.6.3)
```xml
<!-- https://mvnrepository.com/artifact/com.github.radist-nt/iuliia-java -->
<dependency>
    <groupId>com.github.radist-nt</groupId>
    <artifactId>iuliia-java</artifactId>
    <version>0.1.1</version>
</dependency>

```

## Usage

Simple example:

```java
import com.radist_nt.iuliia.Iuliia;

public class Clazz {
    public static void test() {        
        Iuliia.transliterate("Юлия", Iuliia.ICAO_DOC_9303); //Iuliia
    }
}
```

Advanced usage example:

```java
import com.radist_nt.iuliia.Iuliia;
import com.radist_nt.iuliia.Transliterator;

public class Clazz {
    private static final Transliterator ICAO_DOC_9303 = Iuliia.withSchema(Iuliia.ICAO_DOC_9303);
    public static void test() {
        ICAO_DOC_9303.transliterate("Юлия"); //Iuliia
    }
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Make sure to add or update tests as appropriate.

Test "testConstants" could fail after adding new transliteration schemas in https://github.com/nalgeon/iuliia. To make it pass, temporary enable the test "generateIuliiaConstants" and run `mvn test`. Reformat code before commiting (using built-in eclipse formatting settings).

## License

[MIT](https://choosealicense.com/licenses/mit/)
