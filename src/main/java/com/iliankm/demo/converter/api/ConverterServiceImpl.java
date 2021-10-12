package com.iliankm.demo.converter.api;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * Implementation of {@link ConverterService}.
 */
@Service
class ConverterServiceImpl implements ConverterService {

    private final Map<Pair<String, String>, Converter<?, ?>> converters;
    private final Map<Pair<String, String>, MergeConverter<?, ?>> mergeConverters;

    /**
     * Constructor with dependencies.
     *
     * @param converters all {@link Converter} beans
     * @param mergeConverters all {@link MergeConverter} beans
     */
    ConverterServiceImpl(List<Converter<?, ?>> converters, List<MergeConverter<?, ?>> mergeConverters) {
        this.converters = mapConverters(converters);
        this.mergeConverters = mapConverters(mergeConverters);
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        final var converter = (Converter<S, D>) converters.get(
                Pair.of(source.getClass().getName(), destinationClass.getName()));
        if (converter != null) {
            return converter.convert(source);
        } else {
            throw new IllegalArgumentException("No implementation found for Converter from "
                    + source.getClass().getName() + " to " + destinationClass.getName());
        }
    }

    @Override
    public <S, D> D convert(S source, D destination) {
        final var mergeConverter = (MergeConverter<S, D>) mergeConverters.get(
                Pair.of(source.getClass().getName(), destination.getClass().getName()));
        if (mergeConverter != null) {
            return mergeConverter.convert(source, destination);
        } else {
            throw new IllegalArgumentException("No implementation found for MergeConverter from "
                    + source.getClass().getName() + " to " + destination.getClass().getName());
        }
    }

    private static <T extends SourceAndDestination<?, ?>> Map<Pair<String, String>, T> mapConverters(
            List<T> converters) {
        return converters.stream().collect(toUnmodifiableMap(
                c -> Pair.of(c.getSourceType().getName(), c.getDestinationType().getName()), identity()));
    }
}
