package com.kobylynskyi.graphql.codegen.kotlin;

import com.kobylynskyi.graphql.codegen.TestUtils;
import com.kobylynskyi.graphql.codegen.model.KotlinNullableInputTypeWrapper;
import com.kobylynskyi.graphql.codegen.model.MappingConfig;
import com.kobylynskyi.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.kobylynskyi.graphql.codegen.TestUtils.assertSameTrimmedContent;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphQLCodegenInputWrapperTest {

    private static class TestInputWrapper implements KotlinNullableInputTypeWrapper {

        @Override
        public String getWrapperClassName() {
            return "com.example.NullableInputWrapper";
        }

        @Override
        public String getNullValueExpression() {
            return "com.example.NullableInputWrapper.nullValue()";
        }

        @Override
        public String getUndefinedValueExpression() {
            return "com.example.NullableInputWrapper.undefined()";
        }

        @Override
        public String getValueExpression(String value) {
            return "com.example.NullableInputWrapper.value(" + value + ")";
        }
    }

    private final File outputBuildDir = new File("build/generated");
    private final File outputClassesDir = new File("build/generated");
    private final MappingConfig mappingConfig = new MappingConfig();

    @BeforeEach
    void before() {
        mappingConfig.setKotlinNullableInputTypeWrapper(new TestInputWrapper());
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_CheckFiles_CustomWrapper() throws Exception {
        generate();

        File[] files = Objects.requireNonNull(outputClassesDir.listFiles());
        List<String> generatedFileNames = Arrays.stream(files).map(File::getName).sorted().collect(toList());
        assertEquals(asList(
                "InputWithDefaults.kt",
                "MyEnum.kt",
                "SomeObject.kt"
        ), generatedFileNames);

        for (File file : files) {
            assertSameTrimmedContent(
                    new File(
                            String.format("src/test/resources/expected-classes/kt/custom-input-wrapper/%s.txt",
                                    file.getName())
                    ),
                    file);
        }
    }

    private void generate() throws IOException {
        new KotlinGraphQLCodegen(singletonList("src/test/resources/schemas/input-wrapper.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();
    }
}
