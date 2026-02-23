package io.github.kobylynskyi.graphql.codegen.gradle;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * Gradle-specific subclass that adds Gradle input annotations to
 * {@link com.kobylynskyi.graphql.codegen.model.NullableInputTypeWrapperConfig}.
 */
public final class NullableInputTypeWrapperConfig
        extends com.kobylynskyi.graphql.codegen.model.NullableInputTypeWrapperConfig {

    @Input
    @Optional
    @Override
    public String getWrapperClassName() {
        return super.getWrapperClassName();
    }

    @Input
    @Optional
    @Override
    public String getNullValueExpression() {
        return super.getNullValueExpression();
    }

    @Input
    @Optional
    @Override
    public String getUndefinedValueExpression() {
        return super.getUndefinedValueExpression();
    }

    @Input
    @Optional
    @Override
    public String getValueExpression() {
        return super.getValueExpression();
    }

}
