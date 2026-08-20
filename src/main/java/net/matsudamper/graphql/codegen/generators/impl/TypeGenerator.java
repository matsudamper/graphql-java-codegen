package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.TypeDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedObjectTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates files for types
 */
public class TypeGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final TypeDefinitionToDataModelMapper typeDefinitionMapper;

    public TypeGenerator(MappingContext mappingContext, DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.typeDefinitionMapper = dataModelMapperFactory.getTypeDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedObjectTypeDefinition definition : mappingContext.getDocument().getTypeDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedObjectTypeDefinition definition) {
        boolean typeAsInterface = mappingContext.getTypesAsInterfaces().contains(definition.getName());
        if (!typeAsInterface) {
            typeAsInterface = definition.getDirectiveNames().stream().anyMatch(directiveName ->
                    mappingContext.getTypesAsInterfaces().contains("@" + directiveName));
        }

        Map<String, Object> dataModel = typeDefinitionMapper.map(mappingContext, definition);
        if (typeAsInterface) {
            return FreeMarkerTemplateFilesCreator.create(
                    mappingContext, FreeMarkerTemplateType.INTERFACE, dataModel);
        } else {
            return FreeMarkerTemplateFilesCreator.create(
                    mappingContext, FreeMarkerTemplateType.TYPE, dataModel);
        }
    }
}
