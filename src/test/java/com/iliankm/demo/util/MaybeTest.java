package com.iliankm.demo.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

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
        // given
        var unknown = Maybe.<String>unknown();

        // when & then
        assertThrows(NoSuchElementException.class, unknown::get);
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

}