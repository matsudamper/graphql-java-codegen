package com.kobylynskyi.graphql.codegen.model;

/**
 * Defines how nullable Java input types should be wrapped and initialized.
 */
public interface JavaNullableInputTypeWrapper {

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
     * @param value already formatted Java literal or expression
     * @return expression wrapping provided value
     */
    String getValueExpression(String value);
}
