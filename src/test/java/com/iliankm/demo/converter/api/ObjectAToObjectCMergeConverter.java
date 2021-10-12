package com.iliankm.demo.converter.api;

import org.springframework.stereotype.Component;

@Component
class ObjectAToObjectCMergeConverter implements MergeConverter<ObjectA, ObjectC> {

    @Override
    public ObjectC convert(ObjectA source, ObjectC destination) {
        destination.setProp1(source.getProp1());
        destination.setProp2(source.getProp2());
        return destination;
    }
}
