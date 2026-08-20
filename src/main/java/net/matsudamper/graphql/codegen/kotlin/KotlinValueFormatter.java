package net.matsudamper.graphql.codegen.kotlin;

import net.matsudamper.graphql.codegen.mapper.ValueFormatter;

import java.util.StringJoiner;

/**
 * Class contains various formatting logic that is specific only for Kotlin language
 *
 * @author 梦境迷离
 * @since 2020/12/09
 */
public class KotlinValueFormatter implements ValueFormatter {

    @Override
    public String getNullValue() {
        return getEmptyListValue();
    }

    @Override
    public String getEmptyListValue() {
        return "emptyList()";
    }

    @Override
    public StringJoiner getListJoiner() {
        return new StringJoiner(", ", "listOf(", ")");
    }

    @Override
    public StringJoiner getArrayJoiner() {
        return new StringJoiner(", ", "arrayOf(", ")");
    }
}
