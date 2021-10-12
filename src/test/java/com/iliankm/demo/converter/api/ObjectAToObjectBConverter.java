package com.iliankm.demo.converter.api;

import org.springframework.stereotype.Component;

@Component
class ObjectAToObjectBConverter implements Converter<ObjectA, ObjectB> {

    @Override
    public ObjectB convert(ObjectA source) {
        return new ObjectB(source.getProp1(), source.getProp2());
    }
}
