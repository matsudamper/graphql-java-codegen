package com.kobylynskyi.graphql.codegen.model;

/**
 * Defines how nullable Kotlin input types should be wrapped and initialized.
 */
public interface KotlinNullableInputTypeWrapper {

    /**
     * @return fully-qualified wrapper class name
     */
    String getWrapperClassName();

    /**
     * @return expression for explicit null default value
     */
    String getNullValueExpression();

    /**
     * @return expression for omitted (undefined) default value
     */
    String getUndefinedValueExpression();

    /**
     * @param value already formatted Kotlin literal or expression
     * @return expression wrapping provided value
     */
    String getValueExpression(String value);
}
