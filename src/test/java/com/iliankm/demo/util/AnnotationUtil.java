package com.iliankm.demo.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Utility around java.lang.reflect for getting annotation applied to a class, method or parameter.
 *
 * Examples:
 *
 * For getting a class level annotation:
 *     SomeAnnotation annotation = forClass(SomeClass.class)
 *         .annotation(SomeAnnotation.class);
 *
 * For getting a method level annotation:
 *     SomeAnnotation annotation = forClass(SomeClass.class)
 *         .method("method1", Integer.class, String.class)
 *         .annotation(SomeAnnotation.class);
 *
 * For getting a parameter level annotation:
 *     SomeAnnotation annotation = forClass(SomeClass.class)
 *         .method("method", Integer.class, String.class)
 *         .parameter(0)
 *         .annotation(SomeAnnotation.class);
 */
public final class AnnotationUtil {

    private AnnotationUtil() {}

    /**
     * Creates an instance of {@link ClassAdapter} that wraps a given class.
     *
     * @param clazz the class to wrap
     * @param <T> the type of the class
     * @return instance of {@link ClassAdapter}
     */
    public static <T> ClassAdapter forClass(Class<T> clazz) {
        return new ClassAdapter(clazz);
    }

    /**
     * Adapter of {@link AnnotatedElement} object that exposes functionality for getting annotation applied to it.
     */
    static abstract class AnnotatedElementAdapter<T extends AnnotatedElement> {

        private final T annotatedElement;

        AnnotatedElementAdapter(T annotatedElement) {
            this.annotatedElement = annotatedElement;
        }

        T getAnnotatedElement() {
            return annotatedElement;
        }

        /**
         * Returns annotation applied to the wrapped object for the specified type if present, null otherwise.
         *
         * @param annotationClass the Class object corresponding to the annotation type
         * @param <A> the type of the annotation
         * @return annotation applied to the wrapped object if present, null otherwise
         */
        public <A extends Annotation> A annotation(Class<A> annotationClass) {
            return annotatedElement.getAnnotation(annotationClass);
        }
    }

    /**
     * Adapter of Class object that exposes functionality for getting method or annotation.
     */
    public static class ClassAdapter extends AnnotatedElementAdapter<Class<?>> {

        ClassAdapter(Class<?> clazz) {
            super(clazz);
        }

        /**
         * Creates an instance of {@link MethodAdapter} that wraps a method of a given class.
         *
         * @param methodName the method name
         * @param argTypes method arguments class objects
         * @return instance of {@link MethodAdapter}
         */
        public MethodAdapter method(String methodName, Class<?>... argTypes) {
            return new MethodAdapter(this.getAnnotatedElement(), methodName, argTypes);
        }
    }

    /**
     * Adapter of {@link Method} object that exposes functionality for getting parameter or annotation.
     */
    public static class MethodAdapter extends AnnotatedElementAdapter<Method> {

        MethodAdapter(Class<?> clazz, String methodName, Class<?>... argTypes) {
            super(getMethodOrThrow(clazz, methodName, argTypes));
        }

        private static Method getMethodOrThrow(Class<?> clazz, String methodName, Class<?>... argTypes) {
            try {
                return clazz.getMethod(methodName, argTypes);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No such method: " + clazz.getName() + "#" + methodName);
            }
        }

        /**
         * Creates an instance of {@link ParameterAdapter} that wraps a parameter of the method wrapped by this object.
         *
         * @param argumentIndex the zero based index of the parameter
         * @return instance of {@link ParameterAdapter}
         */
        public ParameterAdapter parameter(int argumentIndex) {
            return new ParameterAdapter(this.getAnnotatedElement(), argumentIndex);
        }
    }

    /**
     * Adapter of {@link Parameter} object that exposes functionality for getting annotation.
     */
    public static class ParameterAdapter extends AnnotatedElementAdapter<Parameter> {

        ParameterAdapter(Method method, int argumentIndex) {
            super(getParameterOrThrow(method, argumentIndex));
        }

        private static Parameter getParameterOrThrow(Method method, int argumentIndex) {
            final var parameters = method.getParameters();
            if (argumentIndex < 0 || argumentIndex > parameters.length - 1) {
                throw new RuntimeException("No such argument with index: " + argumentIndex + " for method: " + method);
            }

            return parameters[argumentIndex];
        }
    }
}
