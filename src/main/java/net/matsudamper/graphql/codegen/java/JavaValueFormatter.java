package net.matsudamper.graphql.codegen.java;

import net.matsudamper.graphql.codegen.mapper.ValueFormatter;

import java.util.StringJoiner;

/**
 * Class contains various formatting logic that is specific only for Java language
 */
public class JavaValueFormatter implements ValueFormatter {

    @Override
    public String getEmptyListValue() {
        return "java.util.Collections.emptyList()";
    }

    @Override
    public StringJoiner getListJoiner() {
        return new StringJoiner(", ", "java.util.Arrays.asList(", ")");
    }

    @Override
    public StringJoiner getArrayJoiner() {
        return new StringJoiner(", ", "{", "}");
    }

}
