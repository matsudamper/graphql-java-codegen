package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.EnumDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedEnumTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates files for enums
 */
public class EnumsGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final EnumDefinitionToDataModelMapper enumDefinitionMapper;

    public EnumsGenerator(MappingContext mappingContext,
                          DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.enumDefinitionMapper = dataModelMapperFactory.getEnumDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedEnumTypeDefinition definition : mappingContext.getDocument().getEnumDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedEnumTypeDefinition definition) {
        Map<String, Object> dataModel = enumDefinitionMapper.map(mappingContext, definition);
        return FreeMarkerTemplateFilesCreator.create(mappingContext, FreeMarkerTemplateType.ENUM, dataModel);
    }

}
