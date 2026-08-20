package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.RequestResponseDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedDefinition;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedInterfaceTypeDefinition;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedObjectTypeDefinition;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedUnionTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generates files for response projections
 */
public class ResponseProjectionGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final RequestResponseDefinitionToDataModelMapper requestResponseDefinitionMapper;

    public ResponseProjectionGenerator(MappingContext mappingContext,
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
            generatedFiles.add(generate(definition));
        }
        for (ExtendedObjectTypeDefinition definition : mappingContext.getDocument().getTypeDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        for (ExtendedUnionTypeDefinition definition : mappingContext.getDocument().getUnionDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedDefinition<?, ?> definition) {
        Map<String, Object> responseProjDataModel = requestResponseDefinitionMapper.mapResponseProjection(
                mappingContext, definition);
        return FreeMarkerTemplateFilesCreator.create(
                mappingContext, FreeMarkerTemplateType.RESPONSE_PROJECTION, responseProjDataModel);
    }

}
