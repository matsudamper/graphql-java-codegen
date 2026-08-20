package net.matsudamper.graphql.codegen.kotlin;

import net.matsudamper.graphql.codegen.MaxQueryTokensExtension;
import net.matsudamper.graphql.codegen.TestUtils;
import net.matsudamper.graphql.codegen.model.GeneratedLanguage;
import net.matsudamper.graphql.codegen.model.MappingConfig;
import net.matsudamper.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static net.matsudamper.graphql.codegen.TestUtils.assertSameTrimmedContent;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MaxQueryTokensExtension.class)
class GraphQLCodegenSuspendTest {

    private final File outputBuildDir = new File("build/generated");
    private final MappingConfig mappingConfig = new MappingConfig();

    @BeforeEach
    void init() {
        mappingConfig.setGenerateApisWithSuspendFunctions(true);
        mappingConfig.setGeneratedLanguage(GeneratedLanguage.KOTLIN);
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_ApiWithSuspendFunction() throws Exception {
        new KotlinGraphQLCodegen(
                singletonList("src/test/resources/schemas/kt/suspend.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig)
        ).generate();

        File[] files = Objects.requireNonNull(outputBuildDir.listFiles());

        Set<String> generatedFileNames = Arrays.stream(files).map(File::getName).collect(toSet());
        assertEquals(new HashSet<>(asList("FriendsQueryResolver.kt", "QueryResolver.kt", "Friend.kt")),
                generatedFileNames);

        for (File file : files) {
            assertSameTrimmedContent(
                    new File(String.format("src/test/resources/expected-classes/kt/suspend/%s.txt", file.getName())),
                    file);
        }
    }

}
