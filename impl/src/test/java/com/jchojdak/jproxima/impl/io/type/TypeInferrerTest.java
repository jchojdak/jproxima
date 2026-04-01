package com.jchojdak.jproxima.impl.io.type;

import com.jchojdak.jproxima.data.DataType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeInferrerTest {

    private static final String NULL_VALUE = "";
    private final TypeInferrer inferrer = new TypeInferrer(NULL_VALUE);

    @ParameterizedTest
    @CsvSource(
            value = {
                    "1,INTEGER",
                    "123,INTEGER",
                    "1.11,DOUBLE",
                    "true,BOOLEAN",
                    "false,BOOLEAN",
                    "string,STRING",
                    "'',STRING",
                    "NULL,STRING"
            },
            nullValues = "NULL"
    )
    void shouldInferCorrectTypeFromSingleValue(String value, String expectedType) {
        DataType expected = DataType.valueOf(expectedType);

        assertEquals(expected, inferrer.inferType(value));
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "1,2,3,INTEGER",
                    "1.11,2.22,3.33,DOUBLE",
                    "true,false,true,BOOLEAN",
                    "string1,string2,string3,STRING",
                    "NULL,NULL,NULL,STRING"
            },
            nullValues = "NULL"
    )
    void shouldInferCorrectType(String value1, String value2, String value3, String expectedType) {
        List<String> data = Arrays.asList(value1, value2, value3);
        DataType expected = DataType.valueOf(expectedType);

        assertEquals(expected, inferrer.inferType(data));
    }
}
