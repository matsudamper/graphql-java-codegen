package com.kobylynskyi.graphql.codegen.model;

import java.util.Objects;

/**
 * String-based configuration for nullable input type wrapping.
 * Replaces the former JavaNullableInputTypeWrapper / KotlinNullableInputTypeWrapper interfaces.
 */
public class NullableInputTypeWrapperConfig {

    private String wrapperClassName;
    private String nullValueExpression;
    private String undefinedValueExpression;
    private String valueExpression;

    public NullableInputTypeWrapperConfig() {
    }

    public NullableInputTypeWrapperConfig(String wrapperClassName,
                                          String nullValueExpression,
                                          String undefinedValueExpression,
                                          String valueExpression) {
        this.wrapperClassName = wrapperClassName;
        this.nullValueExpression = nullValueExpression;
        this.undefinedValueExpression = undefinedValueExpression;
        this.valueExpression = valueExpression;
    }

    public String getWrapperClassName() {
        return wrapperClassName;
    }

    public void setWrapperClassName(String wrapperClassName) {
        this.wrapperClassName = wrapperClassName;
    }

    public String getNullValueExpression() {
        return nullValueExpression;
    }

    public void setNullValueExpression(String nullValueExpression) {
        this.nullValueExpression = nullValueExpression;
    }

    public String getUndefinedValueExpression() {
        return undefinedValueExpression;
    }

    public void setUndefinedValueExpression(String undefinedValueExpression) {
        this.undefinedValueExpression = undefinedValueExpression;
    }

    public String getValueExpression() {
        return valueExpression;
    }

    public void setValueExpression(String valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     * Returns the value expression with the provided value inserted via {@code String.format}.
     *
     * @param value the value to insert into the expression template
     * @return formatted value expression
     */
    public String getValueExpression(String value) {
        return String.format(this.valueExpression, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NullableInputTypeWrapperConfig that = (NullableInputTypeWrapperConfig) o;
        return Objects.equals(wrapperClassName, that.wrapperClassName) &&
                Objects.equals(nullValueExpression, that.nullValueExpression) &&
                Objects.equals(undefinedValueExpression, that.undefinedValueExpression) &&
                Objects.equals(valueExpression, that.valueExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wrapperClassName, nullValueExpression, undefinedValueExpression, valueExpression);
    }
}
