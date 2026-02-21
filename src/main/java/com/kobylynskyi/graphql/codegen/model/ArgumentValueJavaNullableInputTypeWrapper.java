package com.kobylynskyi.graphql.codegen.model;

/**
 * Backward-compatible wrapper implementation using Spring GraphQL ArgumentValue.
 */
public class ArgumentValueJavaNullableInputTypeWrapper implements JavaNullableInputTypeWrapper {

    public static final String INPUT_WRAPPER_CLASS = "org.springframework.graphql.data.ArgumentValue";
    public static final String INPUT_WRAPPER_NULL = INPUT_WRAPPER_CLASS + ".ofNullable(null)";
    public static final String INPUT_WRAPPER_UNDEFINED = INPUT_WRAPPER_CLASS + ".omitted()";
    public static final String INPUT_WRAPPER_WITH_VALUE = INPUT_WRAPPER_CLASS + ".ofNullable(%s)";

    @Override
    public String getWrapperClassName() {
        return INPUT_WRAPPER_CLASS;
    }

    @Override
    public String getNullValueExpression() {
        return INPUT_WRAPPER_NULL;
    }

    @Override
    public String getUndefinedValueExpression() {
        return INPUT_WRAPPER_UNDEFINED;
    }

    @Override
    public String getValueExpression(String value) {
        return String.format(INPUT_WRAPPER_WITH_VALUE, value);
    }
}
