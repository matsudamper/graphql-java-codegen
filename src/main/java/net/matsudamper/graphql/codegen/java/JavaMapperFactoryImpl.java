package net.matsudamper.graphql.codegen.java;

import net.matsudamper.graphql.codegen.mapper.AnnotationsMapper;
import net.matsudamper.graphql.codegen.mapper.DataModelMapper;
import net.matsudamper.graphql.codegen.mapper.GraphQLTypeMapper;
import net.matsudamper.graphql.codegen.mapper.MapperFactory;
import net.matsudamper.graphql.codegen.mapper.ValueMapper;

/**
 * A factory of various mappers for Java language
 */
public class JavaMapperFactoryImpl implements MapperFactory {

    private final DataModelMapper dataModelMapper;
    private final ValueMapper valueMapper;
    private final GraphQLTypeMapper graphQLTypeMapper;
    private final AnnotationsMapper annotationsMapper;

    public JavaMapperFactoryImpl() {
        dataModelMapper = new JavaDataModelMapper();
        valueMapper = new ValueMapper(new JavaValueFormatter(), dataModelMapper);
        graphQLTypeMapper = new JavaGraphQLTypeMapper();
        annotationsMapper = new JavaAnnotationsMapper(valueMapper);
    }

    @Override
    public DataModelMapper getDataModelMapper() {
        return dataModelMapper;
    }

    @Override
    public GraphQLTypeMapper getGraphQLTypeMapper() {
        return graphQLTypeMapper;
    }

    @Override
    public AnnotationsMapper getAnnotationsMapper() {
        return annotationsMapper;
    }

    @Override
    public ValueMapper getValueMapper() {
        return valueMapper;
    }

}
