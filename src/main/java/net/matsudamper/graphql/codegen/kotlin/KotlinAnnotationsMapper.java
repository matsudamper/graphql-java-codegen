package net.matsudamper.graphql.codegen.kotlin;

import net.matsudamper.graphql.codegen.mapper.AnnotationsMapper;
import net.matsudamper.graphql.codegen.mapper.ValueMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;

import java.util.Collections;
import java.util.List;

/**
 * Mapper class for converting GraphQL types to Kotlin types
 *
 * @author 梦境迷离
 * @since 2020/12/09
 */
public class KotlinAnnotationsMapper extends AnnotationsMapper {

    private final ValueMapper valueMapper;

    public KotlinAnnotationsMapper(ValueMapper valueMapper) {
        this.valueMapper = valueMapper;
    }

    @Override
    public boolean addModelValidationAnnotationForType(String possiblyPrimitiveType) {
        return false;
    }

    @Override
    public String getJacksonResolverTypeIdAnnotation(String modelPackageName) {
        return "com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver(" + modelPackageName +
                "GraphqlJacksonTypeIdResolver::class)";
    }

    @Override
    protected List<String> getAdditionalAnnotations(MappingContext mappingContext, String typeName) {
        return Collections.emptyList();
    }

    @Override
    public ValueMapper getValueMapper() {
        return valueMapper;
    }
}
