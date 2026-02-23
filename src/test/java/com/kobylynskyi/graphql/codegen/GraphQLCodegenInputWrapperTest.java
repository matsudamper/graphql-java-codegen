package com.kobylynskyi.graphql.codegen;

import com.kobylynskyi.graphql.codegen.java.JavaGraphQLCodegen;
import com.kobylynskyi.graphql.codegen.model.MappingConfig;
import com.kobylynskyi.graphql.codegen.model.NullableInputTypeWrapperConfig;
import com.kobylynskyi.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.singleton;

import static com.kobylynskyi.graphql.codegen.TestUtils.assertSameTrimmedContent;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MaxQueryTokensExtension.class)
class GraphQLCodegenInputWrapperTest {

    private static final NullableInputTypeWrapperConfig TEST_WRAPPER = new NullableInputTypeWrapperConfig(
            "com.example.NullableInputWrapper",
            "com.example.NullableInputWrapper.nullValue()",
            "com.example.NullableInputWrapper.undefined()",
            "com.example.NullableInputWrapper.value(%s)");

    private final File outputBuildDir = new File("build/generated");
    private final File outputJavaClassesDir = new File("build/generated");
    private final MappingConfig mappingConfig = new MappingConfig();

    @BeforeEach
    void before() {
        mappingConfig.setUseWrapperForNullableInputTypes(true);
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_CheckFiles() throws Exception {
        generate();

        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());
        List<String> generatedFileNames = Arrays.stream(files).map(File::getName).sorted().collect(toList());
        assertEquals(asList(
                "InputWithDefaults.java",
                "MyEnum.java",
                "SomeObject.java"
        ), generatedFileNames);

        for (File file : files) {
            assertSameTrimmedContent(new File(
                            String.format("src/test/resources/expected-classes/input-wrapper/%s.txt", file.getName())),
                    file);
        }
    }

    @Test
    void generate_CheckFiles_CustomWrapperWhenLegacyFlagDisabled() throws Exception {
        mappingConfig.setUseWrapperForNullableInputTypes(false);
        mappingConfig.setJavaNullableInputTypeWrapper(TEST_WRAPPER);
        generate();
        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());
        List<String> generatedFileNames = Arrays.stream(files).map(File::getName).sorted().collect(toList());
        assertEquals(asList(
                "InputWithDefaults.java",
                "MyEnum.java",
                "SomeObject.java"
        ), generatedFileNames);

        for (File file : files) {
            assertSameTrimmedContent(
                    new File(
                            String.format("src/test/resources/expected-classes/custom-input-wrapper/%s.txt",
                                    file.getName())
                    ),
                    file);
        }
    }


    @Test
    void generate_CheckFiles_CustomWrapperWithDirectiveFilter() throws Exception {
        mappingConfig.setUseWrapperForNullableInputTypes(false);
        mappingConfig.setJavaNullableInputTypeWrapper(TEST_WRAPPER);
        mappingConfig.setNullableInputTypeWrapperForDirectives(singleton("nullableWrapper"));

        new JavaGraphQLCodegen(singletonList("src/test/resources/schemas/input-wrapper-directives.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();

        File generatedFile = new File(outputJavaClassesDir, "InputWithDirective.java");
        assertSameTrimmedContent(
                new File("src/test/resources/expected-classes/custom-input-wrapper-directives/"
                        + "InputWithDirective.java.txt"),
                generatedFile);
    }

    @Test
    void generate_ThrowsWhenDirectiveFilterConfiguredWithoutWrapper() {
        mappingConfig.setUseWrapperForNullableInputTypes(false);
        mappingConfig.setNullableInputTypeWrapperForDirectives(singleton("nullableWrapper"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new JavaGraphQLCodegen(
                        singletonList("src/test/resources/schemas/input-wrapper-directives.graphqls"),
                        outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate());
        assertTrue(ex.getMessage().contains("nullableInputTypeWrapperForDirectives"));
    }

    private void generate() throws IOException {
        new JavaGraphQLCodegen(singletonList("src/test/resources/schemas/input-wrapper.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();
    }
}
