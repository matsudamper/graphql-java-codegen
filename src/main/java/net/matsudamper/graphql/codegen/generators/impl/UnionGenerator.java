package net.matsudamper.graphql.codegen.generators.impl;

import net.matsudamper.graphql.codegen.generators.FilesGenerator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateFilesCreator;
import net.matsudamper.graphql.codegen.generators.FreeMarkerTemplateType;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.mapper.UnionDefinitionToDataModelMapper;
import net.matsudamper.graphql.codegen.model.MappingContext;
import net.matsudamper.graphql.codegen.model.definitions.ExtendedUnionTypeDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates files for unions
 */
public class UnionGenerator implements FilesGenerator {

    private final MappingContext mappingContext;
    private final UnionDefinitionToDataModelMapper unionDefinitionToDataModelMapper;

    public UnionGenerator(MappingContext mappingContext, DataModelMapperFactory dataModelMapperFactory) {
        this.mappingContext = mappingContext;
        this.unionDefinitionToDataModelMapper = dataModelMapperFactory.getUnionDefinitionMapper();
    }

    @Override
    public List<File> generate() {
        List<File> generatedFiles = new ArrayList<>();
        for (ExtendedUnionTypeDefinition definition : mappingContext.getDocument().getUnionDefinitions()) {
            generatedFiles.add(generate(definition));
        }
        return generatedFiles;
    }

    private File generate(ExtendedUnionTypeDefinition definition) {
        Map<String, Object> dataModel = unionDefinitionToDataModelMapper.map(mappingContext, definition);
        return FreeMarkerTemplateFilesCreator.create(mappingContext, FreeMarkerTemplateType.UNION, dataModel);
    }

}
