package net.matsudamper.graphql.codegen.mapper;

import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.builders.JavaDocBuilder;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedInterfaceTypeDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.matsudamper.graphql.codegen.model.DataModelFields.ANNOTATIONS;
import static net.matsudamper.graphql.codegen.model.DataModelFields.CLASS_NAME;
import static net.matsudamper.graphql.codegen.model.DataModelFields.FIELDS;
import static net.matsudamper.graphql.codegen.model.DataModelFields.GENERATED_ANNOTATION;
import static net.matsudamper.graphql.codegen.model.DataModelFields.GENERATED_INFO;
import static net.matsudamper.graphql.codegen.model.DataModelFields.GENERATE_SEALED_INTERFACES;
import static net.matsudamper.graphql.codegen.model.DataModelFields.IMMUTABLE_MODELS;
import static net.matsudamper.graphql.codegen.model.DataModelFields.IMPLEMENTS;
import static net.matsudamper.graphql.codegen.model.DataModelFields.JAVA_DOC;
import static net.matsudamper.graphql.codegen.model.DataModelFields.PACKAGE;
import static net.matsudamper.graphql.codegen.model.DataModelFields.PARENT_INTERFACE_PROPERTIES;

/**
 * Map interface definition to a Freemarker data model
 *
 * @author kobylynskyi
 */
public class InterfaceDefinitionToDataModelMapper {

    private final GraphQLTypeMapper graphQLTypeMapper;
    private final AnnotationsMapper annotationsMapper;
    private final DataModelMapper dataModelMapper;
    private final FieldDefinitionToParameterMapper fieldDefinitionToParameterMapper;

    public InterfaceDefinitionToDataModelMapper(MapperFactory mapperFactory,
                                                FieldDefinitionToParameterMapper fieldDefinitionToParameterMapper) {
        this.graphQLTypeMapper = mapperFactory.getGraphQLTypeMapper();
        this.annotationsMapper = mapperFactory.getAnnotationsMapper();
        this.dataModelMapper = mapperFactory.getDataModelMapper();
        this.fieldDefinitionToParameterMapper = fieldDefinitionToParameterMapper;
    }

    /**
     * Map interface definition to a Freemarker data model
     *
     * @param mappingContext Global mapping context
     * @param definition     Definition of interface type including base definition and its extensions
     * @return Freemarker data model of the GraphQL interface
     */
    public Map<String, Object> map(MappingContext mappingContext, ExtendedInterfaceTypeDefinition definition) {
        Map<String, Object> dataModel = new HashMap<>();
        // type/enum/input/interface/union classes do not require any imports
        dataModel.put(PACKAGE, DataModelMapper.getModelPackageName(mappingContext));
        dataModel.put(CLASS_NAME, dataModelMapper.getModelClassNameWithPrefixAndSuffix(mappingContext, definition));
        dataModel.put(JAVA_DOC, JavaDocBuilder.build(definition));
        dataModel.put(IMPLEMENTS, getInterfaces(mappingContext, definition));
        dataModel.put(ANNOTATIONS, annotationsMapper.getAnnotations(mappingContext, definition));
        dataModel.put(FIELDS, fieldDefinitionToParameterMapper
                .mapFields(mappingContext, definition.getFieldDefinitions(), definition));
        dataModel.put(GENERATED_ANNOTATION, mappingContext.getAddGeneratedAnnotation());
        dataModel.put(GENERATED_INFO, mappingContext.getGeneratedInformation());
        dataModel.put(IMMUTABLE_MODELS, mappingContext.getGenerateImmutableModels());
        dataModel.put(PARENT_INTERFACE_PROPERTIES, mappingContext.getParentInterfaceProperties());
        dataModel.put(GENERATE_SEALED_INTERFACES, mappingContext.isGenerateSealedInterfaces());
        return dataModel;
    }

    private Set<String> getInterfaces(MappingContext mappingContext,
                                      ExtendedInterfaceTypeDefinition definition) {
        return definition.getImplements()
                .stream()
                .map(anImplement -> graphQLTypeMapper.getLanguageType(mappingContext, anImplement))
                .collect(Collectors.toSet());
    }

}
