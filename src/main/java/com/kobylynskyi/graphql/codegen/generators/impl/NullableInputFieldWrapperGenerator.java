package com.kobylynskyi.graphql.codegen.generators.impl;

import com.kobylynskyi.graphql.codegen.generators.FilesGenerator;
import com.kobylynskyi.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import com.kobylynskyi.graphql.codegen.generators.FreeMarkerTemplateType;
import com.kobylynskyi.graphql.codegen.mapper.DataModelMapper;
import com.kobylynskyi.graphql.codegen.model.GeneratedLanguage;
import com.kobylynskyi.graphql.codegen.model.MappingContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kobylynskyi.graphql.codegen.model.DataModelFields.CLASS_NAME;
import static com.kobylynskyi.graphql.codegen.model.DataModelFields.GENERATED_ANNOTATION;
import static com.kobylynskyi.graphql.codegen.model.DataModelFields.GENERATED_INFO;
import static com.kobylynskyi.graphql.codegen.model.DataModelFields.PACKAGE;

/**
 * Generates a GraphQlInputField sealed interface for Kotlin nullable input type wrapping.
 */
public class NullableInputFieldWrapperGenerator implements FilesGenerator {

    public static final String CLASS_NAME_GRAPHQL_INPUT_FIELD = "GraphQlInputField";

    private final MappingContext mappingContext;

    public NullableInputFieldWrapperGenerator(MappingContext mappingContext) {
        this.mappingContext = mappingContext;
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>(1);
        if (Boolean.TRUE.equals(mappingContext.getUseWrapperForNullableInputTypes()) &&
                GeneratedLanguage.KOTLIN.equals(mappingContext.getGeneratedLanguage())) {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put(PACKAGE, DataModelMapper.getModelPackageName(mappingContext));
            dataModel.put(CLASS_NAME, CLASS_NAME_GRAPHQL_INPUT_FIELD);
            dataModel.put(GENERATED_ANNOTATION, mappingContext.getAddGeneratedAnnotation());
            dataModel.put(GENERATED_INFO, mappingContext.getGeneratedInformation());
            File file = FreeMarkerTemplateFilesCreator.create(
                    mappingContext, FreeMarkerTemplateType.NULLABLE_INPUT_FIELD_WRAPPER, dataModel);
            generatedFiles.add(file);
        }
        return generatedFiles;
    }

}
