package com.iliankm.demo.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Utility around java.lang.reflect for getting annotation applied to a class, method or argument.
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
 * For getting an argument level annotation:
 *     SomeAnnotation annotation = forClass(SomeClass.class)
 *         .method("method", Integer.class, String.class)
 *         .argument(0)
 *         .annotation(SomeAnnotation.class);
 */
public final class AnnotationUtil {

    private AnnotationUtil() {}

    /**
     * Creates an instance of {@link ForClass} that wraps a given class.
     *
     * @param clazz the class to wrap
     * @param <T> the type of the class
     * @return instance of {@link ForClass}
     */
    public static <T> ForClass<T> forClass(Class<T> clazz) {
        return new ForClass<>(clazz);
    }

    /**
     * Adapter of Class object that exposes functionality to get method or annotation.
     *
     * @param <T> the type of the class
     */
    public static class ForClass<T> {

        private final Class<T> clazz;

        ForClass(Class<T> clazz) {
            this.clazz = clazz;
        }

        /**
         * Returns annotation applied to the wrapped class for the specified type if present, null otherwise.
         *
         * @param annotationClass the Class object corresponding to the annotation type
         * @param <A> the type of the annotation
         * @return annotation applied to the wrapped class if present, null otherwise
         */
        public <A extends Annotation> A annotation(Class<A> annotationClass) {
            return clazz.getAnnotation(annotationClass);
        }

        /**
         * Creates an instance of {@link AuMethod} that wraps a method of a given class.
         *
         * @param methodName the method name
         * @param argTypes method arguments class objects
         * @return instance of {@link AuMethod}
         */
        public AuMethod method(String methodName, Class<?>... argTypes) {
            return new AuMethod(clazz, methodName, argTypes);
        }
    }

    /**
     * Adapter of Method object that exposes functionality to get argument or annotation.
     */
    public static class AuMethod {

        private final Method method;

        AuMethod(Class<?> clazz, String methodName, Class<?>... argTypes) {
            try {
                this.method = clazz.getMethod(methodName, argTypes);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No such method: " + clazz.getName() + "#" + methodName);
            }
        }

        /**
         * Returns annotation applied to the wrapped method for the specified type if present, null otherwise.
         *
         * @param annotationClass the Class object corresponding to the annotation type
         * @param <T> the type of the annotation
         * @return annotation applied to the wrapped method if present, null otherwise
         */
        public <T extends Annotation> T annotation(Class<T> annotationClass) {
            return method.getAnnotation(annotationClass);
        }

        /**
         * Creates an instance of {@link AuArgument} that wraps an argument of the method wrapped by this object.
         *
         * @param argumentIndex the zero based index of the argument
         * @return instance of {@link AuArgument}
         */
        public AuArgument argument(int argumentIndex) {
            return new AuArgument(method, argumentIndex);
        }
    }

    /**
     * Adapter of Parameter object that exposes functionality to get annotation.
     */
    public static class AuArgument {

        private final Parameter parameter;

        AuArgument(Method method, int argumentIndex) {
            final var parameters = method.getParameters();
            if (argumentIndex < 0 || argumentIndex > parameters.length - 1) {
                throw new RuntimeException("No such argument with index: " + argumentIndex + " for method: " + method);
            }

            this.parameter = parameters[argumentIndex];
        }

        /**
         * Returns annotation applied to the wrapped parameter for the specified type if present, null otherwise.
         *
         * @param annotationClass the Class object corresponding to the annotation type
         * @param <T> the type of the annotation
         * @return annotation applied to the wrapped parameter if present, null otherwise
         */
        public <T extends Annotation> T annotation(Class<T> annotationClass) {
            return parameter.getAnnotation(annotationClass);
        }
    }
}
