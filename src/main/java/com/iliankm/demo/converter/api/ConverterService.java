package com.iliankm.demo.converter.api;

/**
 * Represents a service for conversion of objects.
 */
public interface ConverterService {

    /**
     * Converts a source object to a destination of the given type.
     * A new instance of the destination object is created and returned.
     * In order for this method to work,
     * you need to have a proper implementation of {@link Converter} for the needed source and destination types,
     * otherwise {@link IllegalArgumentException} is thrown.
     *
     * @param source the source object
     * @param destinationClass the class of the destination object
     * @param <S> the source type
     * @param <D> the destination type
     * @return converted destination object
     */
    <S, D> D convert(S source, Class<D> destinationClass);

    /**
     * Converts by merging from source to destination objects.
     * The same instance passed as a destination is merged and returned.
     * In order for this method to work,
     * you need to have a proper implementation of {@link MergeConverter} for the needed source and destination types,
     * otherwise {@link IllegalArgumentException} is thrown.
     *
     * @param source the source object
     * @param destination the destination object
     * @param <S> the source type
     * @param <D> the destination type
     * @return the merged destination object that is passed as an argument
     */
    <S, D> D convert(S source, D destination);
}
