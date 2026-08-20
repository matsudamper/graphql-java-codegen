package net.matsudamper.graphql.codegen.generators;

import net.matsudamper.graphql.codegen.generators.impl.EnumsGenerator;
import net.matsudamper.graphql.codegen.generators.impl.FieldResolversGenerator;
import net.matsudamper.graphql.codegen.generators.impl.InputGenerator;
import net.matsudamper.graphql.codegen.generators.impl.InterfaceGenerator;
import net.matsudamper.graphql.codegen.generators.impl.JacksonTypeIdResolverGenerator;
import net.matsudamper.graphql.codegen.generators.impl.OperationsGenerator;
import net.matsudamper.graphql.codegen.generators.impl.ParametrizedInputGenerator;
import net.matsudamper.graphql.codegen.generators.impl.RequestResponseGenerator;
import net.matsudamper.graphql.codegen.generators.impl.ResponseProjectionGenerator;
import net.matsudamper.graphql.codegen.generators.impl.TypeGenerator;
import net.matsudamper.graphql.codegen.generators.impl.UnionGenerator;
import net.matsudamper.graphql.codegen.mapper.DataModelMapperFactory;
import net.matsudamper.graphql.codegen.model.MappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for building files generators
 */
public class FilesGeneratorsFactory {

    private FilesGeneratorsFactory() {
    }

    /**
     * Factory method for building files generators
     *
     * @param context                Global mapping context
     * @param dataModelMapperFactory Data model mapper factory
     * @return a list of all files generators
     */
    public static List<FilesGenerator> getAll(MappingContext context,
                                              DataModelMapperFactory dataModelMapperFactory) {
        List<FilesGenerator> generators = new ArrayList<>();
        generators.add(new EnumsGenerator(context, dataModelMapperFactory));
        generators.add(new InterfaceGenerator(context, dataModelMapperFactory));
        generators.add(new TypeGenerator(context, dataModelMapperFactory));
        generators.add(new ResponseProjectionGenerator(context, dataModelMapperFactory));
        generators.add(new ParametrizedInputGenerator(context, dataModelMapperFactory));
        generators.add(new FieldResolversGenerator(context, dataModelMapperFactory));
        generators.add(new InputGenerator(context, dataModelMapperFactory));
        generators.add(new UnionGenerator(context, dataModelMapperFactory));
        generators.add(new RequestResponseGenerator(context, dataModelMapperFactory));
        generators.add(new OperationsGenerator(context, dataModelMapperFactory));
        generators.add(new JacksonTypeIdResolverGenerator(context));
        return generators;
    }

}
