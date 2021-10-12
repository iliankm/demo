package com.iliankm.demo.converter.api;

import java.lang.reflect.ParameterizedType;

/**
 * Interface for source and destination generic type representations.
 *
 * @param <S> the source type
 * @param <D> the destination type
 */
public interface SourceAndDestination<S, D> {

    /**
     * Determines the class of the source type.
     *
     * @return the class of <S>
     */
    default Class<S> getSourceType() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     * Determines the class of the destination type.
     *
     * @return the class of <D>
     */
    default Class<D> getDestinationType() {
        return (Class<D>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
    }
}
