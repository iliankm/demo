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
public final class Maybe<T> {

    private final T value;

    private final boolean known;

    private Maybe() {
        this.known = false;
        this.value = null;
    }

    private Maybe(T value) {
        this.known = true;
        this.value = value;
    }

    /**
     * If the value is definite (known), returns the value, otherwise throws {@link NoSuchElementException}.
     *
     * @return the value that this container holds
     */
    public T get() {
        if (known) {
            return value;
        } else {
            throw new NoSuchElementException("Unknown value.");
        }
    }

    /**
     * If the value is definite (known), returns true, otherwise false.
     *
     * @return true if value is definite (known), otherwise false
     */
    public boolean isKnown() {
        return known;
    }

    /**
     * If the container is definite (known), performs the given action with the value, otherwise does nothing.
     *
     * @param action the action to be performed, if the container is definite (known)
     */
    public void ifKnown(Consumer<? super T> action) {
        if (known) {
            action.accept(get());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
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
        return known == other.known && Objects.equals(value, other.value);
    }

    @Override
    public String toString() {
        return known ? "definitely -> " + (value != null ? value.toString() : "null") : "Unknown";
    }

    private static final Maybe<?> UNKNOWN = new Maybe<>();

    private static final Maybe<?> DEFINITELY_NULL = new Maybe<>(null);

    /**
     * Returns unknown Maybe instance.
     *
     * @param <T> the type of value
     * @return unknown Maybe instance
     */
    @SuppressWarnings("unchecked")
    public static <T> Maybe<T> unknown() {
        return (Maybe<T>) UNKNOWN;
    }

    /**
     * Creates definite Maybe instance holding certain value including null.
     *
     * @param value the value that the container will hold, might be null
     * @param <T> the type of value
     * @return definite Maybe instance holding the passed value
     */
    @SuppressWarnings("unchecked")
    public static <T> Maybe<T> definitely(T value) {
        return value == null ? (Maybe<T>) DEFINITELY_NULL : new Maybe<>(value);
    }
}
