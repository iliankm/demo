package com.iliankm.demo.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
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
    public static <T> ForClass forClass(Class<T> clazz) {
        return new ForClass(clazz);
    }

    /**
     * Adapter of AnnotatedElement object that exposes functionality for getting annotation.
     */
    public static abstract class AbstractAnnotatedElement<T extends AnnotatedElement> {

        private final T annotatedElement;

        AbstractAnnotatedElement(T annotatedElement) {
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
    public static class ForClass extends AbstractAnnotatedElement<Class<?>> {

        ForClass(Class<?> clazz) {
            super(clazz);
        }

        /**
         * Creates an instance of {@link AuMethod} that wraps a method of a given class.
         *
         * @param methodName the method name
         * @param argTypes method arguments class objects
         * @return instance of {@link AuMethod}
         */
        public AuMethod method(String methodName, Class<?>... argTypes) {
            return AuMethod.create(this.getAnnotatedElement(), methodName, argTypes);
        }
    }

    /**
     * Adapter of Method object that exposes functionality to get argument or annotation.
     */
    public static class AuMethod extends AbstractAnnotatedElement<Method> {

        AuMethod(Method method) {
            super(method);
        }

        /**
         * Factory method for {@link AuMethod}.
         *
         * @param clazz the class
         * @param methodName the method name
         * @param argTypes argument types
         * @return {@link AuMethod} instance
         */
        static AuMethod create(Class<?> clazz, String methodName, Class<?>... argTypes) {
            try {
                return new AuMethod(clazz.getMethod(methodName, argTypes));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No such method: " + clazz.getName() + "#" + methodName);
            }
        }

        /**
         * Creates an instance of {@link AuArgument} that wraps an argument of the method wrapped by this object.
         *
         * @param argumentIndex the zero based index of the argument
         * @return instance of {@link AuArgument}
         */
        public AuArgument argument(int argumentIndex) {
            return AuArgument.create(this.getAnnotatedElement(), argumentIndex);
        }
    }

    /**
     * Adapter of Parameter object that exposes functionality to get annotation.
     */
    public static class AuArgument extends AbstractAnnotatedElement<Parameter> {

        AuArgument(Parameter parameter) {
            super(parameter);
        }

        /**
         * Factory method for {@link AuArgument}.
         *
         * @param method the method
         * @param argumentIndex the argument zero based index
         * @return {@link AuArgument} instance
         */
        static AuArgument create(Method method, int argumentIndex) {
            final var parameters = method.getParameters();
            if (argumentIndex < 0 || argumentIndex > parameters.length - 1) {
                throw new RuntimeException("No such argument with index: " + argumentIndex + " for method: " + method);
            }

            return new AuArgument(parameters[argumentIndex]);
        }
    }
}
