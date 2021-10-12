package com.iliankm.demo.converter.api;

/**
 * Represents converter from source to destination types.
 *
 * @param <S> the source type
 * @param <D> the destination type
 */
public interface Converter<S, D> extends SourceAndDestination<S, D> {

    /**
     * Converts source to destination object.
     *
     * @param source the source object
     * @return converted destination object
     */
    D convert(S source);
}
