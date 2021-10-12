package com.iliankm.demo.converter.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link ConverterServiceImpl}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConverterServiceImpl.class, ObjectAToObjectBConverter.class, ObjectAToObjectCMergeConverter.class})
class ConverterServiceImplTest {

    @Autowired
    private ConverterService converterService;

    @Test
    void shouldConvertObjectAToObjectB() {
        // given
        final ObjectA objectA = new ObjectA("text", 2021);

        // when
        final ObjectB objectB = converterService.convert(objectA, ObjectB.class);

        // then
        assertThat(objectB.getProp1(), is("text"));
        assertThat(objectB.getProp2(), is(2021));
    }

    @Test
    void whenConvertUnimplementedTypes_thenThrowIllegalArgumentException() {
        // given
        final ObjectA objectA = new ObjectA("text", 2021);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> converterService.convert(objectA, ObjectC.class));
    }

    @Test
    void shouldMergeObjectAToObjectC() {
        // given
        final ObjectA objectA = new ObjectA("text", 2021);
        final ObjectC objectC = new ObjectC();
        objectC.setProp1("");
        objectC.setProp2(0);

        // when
        final ObjectC result = converterService.convert(objectA, objectC);

        // then
        assertThat(result, is(objectC));
        assertThat(objectC.getProp1(), is("text"));
        assertThat(objectC.getProp2(), is(2021));
    }

    @Test
    void whenMergeUnimplementedTypes_thenThrowIllegalArgumentException() {
        // given
        final ObjectA objectA = new ObjectA("text", 2021);
        final ObjectB objectB = new ObjectB("", 0);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> converterService.convert(objectA, objectB));
    }

}