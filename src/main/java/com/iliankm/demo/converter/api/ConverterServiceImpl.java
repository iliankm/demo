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
     * @param converters      all {@link Converter} beans
     * @param mergeConverters all {@link MergeConverter} beans
     */
    ConverterServiceImpl(List<Converter<?, ?>> converters, List<MergeConverter<?, ?>> mergeConverters) {
        this.converters = mapConverters(converters);
        this.mergeConverters = mapConverters(mergeConverters);
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        final Converter<S, D> converter =
                findConverterOrThrow(converters, source.getClass(), destinationClass);
        return converter.convert(source);
    }

    @Override
    public <S, D> D convert(S source, D destination) {
        final MergeConverter<S, D> mergeConverter =
                findConverterOrThrow(mergeConverters, source.getClass(), destination.getClass());
        return mergeConverter.convert(source, destination);
    }

    private static <T extends SourceAndDestination<?, ?>> Map<Pair<String, String>, T> mapConverters(
            List<T> converters) {
        return converters.stream().collect(toUnmodifiableMap(
                c -> Pair.of(c.getSourceType().getName(), c.getDestinationType().getName()), identity()));
    }

    @SuppressWarnings("unchecked")
    private static <T extends SourceAndDestination<?, ?>> T findConverterOrThrow(
            Map<Pair<String, String>, ? extends SourceAndDestination<?, ?>> converters,
            Class<?> sourceClass,
            Class<?> destinationClass) {
        final var converter = converters.get(Pair.of(sourceClass.getName(), destinationClass.getName()));
        if (converter != null) {
            return (T) converter;
        } else {
            throw new IllegalArgumentException("No implementation found for Converter or MergeConverter from "
                    + sourceClass.getName() + " to " + destinationClass.getName());
        }
    }
}
