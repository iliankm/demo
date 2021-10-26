package com.iliankm.demo.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;

class MaybeTest {

    @Test
    void givenUnknown_whenIsKnown_thenFalse() {
        // given
        var unknown = Maybe.<String>unknown();

        // when
        assertThat(unknown.isKnown(), is(false));
    }

    @Test
    void givenUnknown_whenGet_shouldThrowNoSuchElementException() {
        // given & when & then
        //noinspection ResultOfMethodCallIgnored
        assertThrows(NoSuchElementException.class, () -> Maybe.unknown().get());
    }

    @Test
    void givenUnknown_whenIfKnown_thenDoNothing() {
        // given
        var unknown = Maybe.<String>unknown();
        @SuppressWarnings("unchecked")
        Consumer<String> action = Mockito.mock(Consumer.class);

        // when
        unknown.ifKnown(action);

        // then
        then(action).shouldHaveNoInteractions();
    }

    @Test
    void givenDefinitely_whenIsKnown_thenTrue() {
        // given
        var def = Maybe.definitely("");

        // when & then
        assertThat(def.isKnown(), is(true));
    }

    @Test
    void givenDefinitely_whenGet_shouldReturn() {
        // given
        var def = Maybe.definitely("text");

        // when & then
        assertThat(def.get(), is("text"));
    }

    @Test
    void givenDefinitely_whenIfKnown_thenPerformAction() {
        // given
        var def = Maybe.definitely("text");

        // when
        StringBuilder value = new StringBuilder();
        def.ifKnown(value::append);

        // then
        assertThat(value.toString(), is("text"));
    }

    @Test
    void givenDefinitelyWithNull_whenGet_shouldReturnNull() {
        // given
        var def = Maybe.<String>definitely(null);

        // when & then
        assertThat(def.get(), nullValue());
    }

    @ParameterizedTest
    @MethodSource("givenMaybe_shouldMatchHashCode_testCases")
    void givenMaybe_shouldMatchHashCode(Maybe<String> maybe, int expectedHashCode) {
        // given & when & then
        assertThat(maybe.hashCode(), is(expectedHashCode));
    }

    private static Stream<Arguments> givenMaybe_shouldMatchHashCode_testCases() {
        return Stream.of(
                Arguments.of(Maybe.unknown(), 0),
                Arguments.of(Maybe.definitely(null), 0),
                Arguments.of(Maybe.definitely("test"), "test".hashCode())
        );
    }

    @ParameterizedTest
    @MethodSource("givenMaybe_shouldMatchEquals_testCases")
    void givenMaybe_shouldMatchEquals(Maybe<String> maybe, Object other, boolean expectedEquals) {
        // given & when & then
        assertThat(maybe.equals(other), is(expectedEquals));
    }

    private static Stream<Arguments> givenMaybe_shouldMatchEquals_testCases() {
        return Stream.of(
                Arguments.of(Maybe.unknown(), null, false),
                Arguments.of(Maybe.unknown(), "test", false),
                Arguments.of(Maybe.unknown(), Maybe.unknown(), true),
                Arguments.of(Maybe.unknown(), Maybe.definitely(null), false),
                Arguments.of(Maybe.unknown(), Maybe.definitely("test"), false),
                Arguments.of(Maybe.definitely(null), null, false),
                Arguments.of(Maybe.definitely(null), "test", false),
                Arguments.of(Maybe.definitely(null), Maybe.unknown(), false),
                Arguments.of(Maybe.definitely(null), Maybe.definitely(null), true),
                Arguments.of(Maybe.definitely(null), Maybe.definitely("test"), false),
                Arguments.of(Maybe.definitely("test"), null, false),
                Arguments.of(Maybe.definitely("test"), "test", false),
                Arguments.of(Maybe.definitely("test"), Maybe.unknown(), false),
                Arguments.of(Maybe.definitely("test"), Maybe.definitely(null), false),
                Arguments.of(Maybe.definitely("test"), Maybe.definitely("test"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("givenMaybe_shouldMatchToString_testCases")
    void givenMaybe_shouldMatchToString(Maybe<String> maybe, String expectedToString) {
        // given & when & then
        assertThat(maybe.toString(), is(expectedToString));
    }

    private static Stream<Arguments> givenMaybe_shouldMatchToString_testCases() {
        return Stream.of(
                Arguments.of(Maybe.unknown(), "Unknown"),
                Arguments.of(Maybe.definitely(null), "definitely -> null"),
                Arguments.of(Maybe.definitely("test"), "definitely -> test")
        );
    }

}