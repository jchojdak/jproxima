package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.join.JoinType;
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
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        List.of(),
                        List.of("id"),
                        "_x",
                        "_y"
                )
        );
    }

    @Test
    void shouldThrowWhenRightKeysEmpty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        List.of("id"),
                        List.of(),
                        "_x",
                        "_y"
                )
        );
    }

    @Test
    void shouldThrowWhenKeySizesDiffer() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        List.of("id"),
                        List.of("id", "title"),
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

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFrameJoiner.join(
                        left,
                        right,
                        JoinType.INNER,
                        List.of("id"),
                        List.of("id"),
                        "_x",
                        "_y"
                )
        );
    }
}
