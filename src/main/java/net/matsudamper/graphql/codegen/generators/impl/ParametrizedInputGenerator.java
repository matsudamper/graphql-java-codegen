package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.RequestResponseDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedDefinition;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedFieldDefinition;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedInterfaceTypeDefinition;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedObjectTypeDefinition;
import net.matsudamper.graphql.codegen.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generates files for parametrized inputs
 */
public class ParametrizedInputGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final RequestResponseDefinitionToDataModelMapper requestResponseDefinitionMapper;

    public ParametrizedInputGenerator(MappingContext mappingContext,
                                      DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.requestResponseDefinitionMapper = dataModelMapperFactory.getRequestResponseDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        if (!Boolean.TRUE.equals(mappingContext.getGenerateClient())) {
            return Collections.emptyList();
        }
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedInterfaceTypeDefinition definition : mappingContext.getDocument().getInterfaceDefinitions()) {
            generatedFiles.addAll(generate(definition, definition.getFieldDefinitions()));
        }
        for (ExtendedObjectTypeDefinition definition : mappingContext.getDocument().getTypeDefinitions()) {
            generatedFiles.addAll(generate(definition, definition.getFieldDefinitions()));
        }
        return generatedFiles;
    }

    private List<File> generate(ExtendedDefinition<?, ?> definition,
                                List<ExtendedFieldDefinition> fieldDefinitions) {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedFieldDefinition fieldDefinition : fieldDefinitions) {
            if (Utils.isEmpty(fieldDefinition.getInputValueDefinitions())) {
                continue;
            }
            File file = generate(definition, fieldDefinition);
            generatedFiles.add(file);
        }
        return generatedFiles;
    }

    private File generate(ExtendedDefinition<?, ?> definition,
                          ExtendedFieldDefinition fieldDefinition) {
        Map<String, Object> dataModel = requestResponseDefinitionMapper.mapParametrizedInput(
                mappingContext, fieldDefinition, definition);
        return FreeMarkerTemplateFilesCreator.create(
                mappingContext, FreeMarkerTemplateType.PARAMETRIZED_INPUT, dataModel);
    }

}
