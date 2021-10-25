package com.iliankm.demo.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A container object which might be definite (known) or unknown (undefined).
 * If it is definite, it may hold null or some value.
 * When it is unknown, the value is considered as undefined thus {@link #isKnown()} returns false and
 * the action passed to {@link #ifKnown(Consumer)} method is never executed.
 *
 * @param <T> the type of value
 */
public interface Maybe<T> {

    /**
     * If the value is definite (known), returns the value, otherwise throws {@link NoSuchElementException}.
     *
     * @return the value that this container holds
     */
    T get();

    /**
     * If the value is definite (known), returns true, otherwise false.
     *
     * @return true if value is definite (known), otherwise false
     */
    boolean isKnown();

    /**
     * If the container is definite (known), performs the given action with the value, otherwise does nothing.
     *
     * @param action the action to be performed, if the container is definite (known)
     */
    default void ifKnown(Consumer<? super T> action) {
        if (isKnown()) {
            action.accept(get());
        }
    }

    /**
     * Creates unknown Maybe instance.
     *
     * @param <T> the type of value
     * @return unknown Maybe instance
     */
    static <T> Maybe<T> unknown() {
        return new Maybe<>() {
            @Override
            public T get() {
                throw new NoSuchElementException("Unknown value.");
            }

            @Override
            public boolean isKnown() {
                return false;
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }

            @Override
            public String toString() {
                return "Unknown";
            }
        };
    }

    /**
     * Creates definite Maybe instance holding certain value.
     *
     * @param value the value that the container will hold, might be null
     * @param <T> the type of value
     * @return definite Maybe instance holding the passed value
     */
    static <T> Maybe<T> definitely(T value) {
        return new Maybe<>() {
            @Override
            public T get() {
                return value;
            }

            @Override
            public boolean isKnown() {
                return true;
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(get());
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }

                if (!(obj instanceof Maybe)) {
                    return false;
                }

                Maybe<?> other = (Maybe<?>) obj;
                return other.isKnown() && Objects.equals(value, other.get());
            }

            @Override
            public String toString() {
                return "definitely " + (value != null ? value.toString() : "null");
            }
        };
    }
}
