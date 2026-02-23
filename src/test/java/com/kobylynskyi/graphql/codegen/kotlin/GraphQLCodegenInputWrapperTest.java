package com.kobylynskyi.graphql.codegen.kotlin;

import com.kobylynskyi.graphql.codegen.TestUtils;
import com.kobylynskyi.graphql.codegen.model.GeneratedLanguage;
import com.kobylynskyi.graphql.codegen.model.MappingConfig;
import com.kobylynskyi.graphql.codegen.model.NullableInputTypeWrapperConfig;
import com.kobylynskyi.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphQLCodegenInputWrapperTest {

    private static final NullableInputTypeWrapperConfig TEST_WRAPPER = new NullableInputTypeWrapperConfig(
            "com.example.NullableInputWrapper",
            "com.example.NullableInputWrapper.nullValue()",
            "com.example.NullableInputWrapper.undefined()",
            "com.example.NullableInputWrapper.value(%s)");

    private final File outputBuildDir = new File("build/generated");
    private final File outputClassesDir = new File("build/generated");
    private final MappingConfig mappingConfig = new MappingConfig();

    @BeforeEach
    void before() {
        mappingConfig.setGeneratedLanguage(GeneratedLanguage.KOTLIN);
        mappingConfig.setKotlinNullableInputTypeWrapper(TEST_WRAPPER);
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


    @Test
    void generate_CheckFiles_CustomWrapperWithDirectiveFilter() throws Exception {
        mappingConfig.setNullableInputTypeWrapperForDirectives(singleton("nullableWrapper"));

        new KotlinGraphQLCodegen(singletonList("src/test/resources/schemas/input-wrapper-directives.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();

        String generated = new String(java.nio.file.Files.readAllBytes(new File(outputClassesDir,
                "InputWithDirective.kt").toPath()), java.nio.charset.StandardCharsets.UTF_8);
        assertTrue(generated.contains("val wrapped: com.example.NullableInputWrapper<String>"));
        assertTrue(generated.contains("val plain: String?"));
        assertFalse(generated.contains("NullableInputWrapper<String> plain"));
    }

    @Test
    void generate_ThrowsWhenDirectiveFilterConfiguredWithoutWrapper() {
        mappingConfig.setKotlinNullableInputTypeWrapper(null);
        mappingConfig.setNullableInputTypeWrapperForDirectives(singleton("nullableWrapper"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new KotlinGraphQLCodegen(
                        singletonList("src/test/resources/schemas/input-wrapper-directives.graphqls"),
                        outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate());
        assertTrue(ex.getMessage().contains("nullableInputTypeWrapperForDirectives"));
    }

    private void generate() throws IOException {
        new KotlinGraphQLCodegen(singletonList("src/test/resources/schemas/input-wrapper.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)).generate();
    }
}
