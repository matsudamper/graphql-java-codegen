package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.InterfaceDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedInterfaceTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates files for interfaces
 */
public class InterfaceGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final InterfaceDefinitionToDataModelMapper interfaceDefinitionMapper;

    public InterfaceGenerator(MappingContext mappingContext,
                              DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.interfaceDefinitionMapper = dataModelMapperFactory.getInterfaceDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedInterfaceTypeDefinition definition : mappingContext.getDocument().getInterfaceDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedInterfaceTypeDefinition definition) {
        Map<String, Object> dataModel = interfaceDefinitionMapper.map(mappingContext, definition);
        return FreeMarkerTemplateFilesCreator.create(mappingContext, FreeMarkerTemplateType.INTERFACE, dataModel);
    }

}
