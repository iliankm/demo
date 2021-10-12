package com.iliankm.demo.converter.api;

/**
 * Represents merge converter from source to destination types.
 *
 * @param <S> the source type
 * @param <D> the destination type
 */
public interface MergeConverter<S, D> extends SourceAndDestination<S, D> {

    /**
     * Converts by merging from source to destination objects.
     *
     * @param source the source object
     * @param destination the destination object to be merged
     * @return the merged destination object that is passed as an argument
     */
    D convert(S source, D destination);
}
