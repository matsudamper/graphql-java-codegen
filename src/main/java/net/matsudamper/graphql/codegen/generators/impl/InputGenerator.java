package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.InputDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedInputObjectTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates files for inputs
 */
public class InputGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final InputDefinitionToDataModelMapper inputDefinitionMapper;

    public InputGenerator(MappingContext mappingContext,
                          DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.inputDefinitionMapper = dataModelMapperFactory.getInputDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedInputObjectTypeDefinition definition : mappingContext.getDocument().getInputDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedInputObjectTypeDefinition definition) {
        Map<String, Object> dataModel = inputDefinitionMapper.map(mappingContext, definition);
        return FreeMarkerTemplateFilesCreator
                .create(mappingContext, FreeMarkerTemplateType.TYPE, dataModel);
    }

}
