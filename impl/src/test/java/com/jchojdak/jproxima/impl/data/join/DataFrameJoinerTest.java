package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.JoinType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataFrameJoinerTest {

    @Mock
    private DataFrame left;

    @Mock
    private DataFrame right;

    @Mock
    private DataFrame expectedResult;

    @Mock
    private JoinStrategy mockStrategy;

    @Test
    void shouldThrowWhenLeftKeysEmpty() {
        List<String> leftKeys = List.of();
        List<String> rightKeys = List.of("id");

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        leftKeys,
                        rightKeys,
                        "_x",
                        "_y"
                )
        );
    }

    @Test
    void shouldThrowWhenRightKeysEmpty() {
        List<String> leftKeys = List.of("id");
        List<String> rightKeys = List.of();

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        leftKeys,
                        rightKeys,
                        "_x",
                        "_y"
                )
        );
    }

    @Test
    void shouldThrowWhenKeySizesDiffer() {
        List<String> leftKeys = List.of("id");
        List<String> rightKeys = List.of("id", "title");

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        leftKeys,
                        rightKeys,
                        "_x",
                        "_y"
                )
        );
    }

    @Test
    void shouldDelegateToRegisteredStrategy() {
        when(mockStrategy.join(any(), any(), any(), any(), any(), any()))
                .thenReturn(expectedResult);
        DataFrameJoiner.register(JoinType.INNER, mockStrategy);

        DataFrame result = DataFrameJoiner.join(
                left,
                right,
                JoinType.INNER,
                List.of("id"),
                List.of("id"),
                "_x",
                "_y"
        );

        assertSame(expectedResult, result);

        verify(mockStrategy).join(
                left,
                right,
                List.of("id"),
                List.of("id"),
                "_x",
                "_y"
        );
    }

    @Test
    void shouldThrowWhenStrategyNotRegistered() {
        DataFrameJoiner.register(JoinType.INNER, null);

        List<String> keys = List.of("id");

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        keys,
                        keys,
                        "_x",
                        "_y"
                )
        );
    }
}
