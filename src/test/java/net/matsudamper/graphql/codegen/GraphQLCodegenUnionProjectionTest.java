package net.matsudamper.graphql.codegen;

import net.matsudamper.graphql.codegen.java.JavaGraphQLCodegen;
import net.matsudamper.graphql.codegen.model.MappingConfig;
import net.matsudamper.graphql.codegen.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static net.matsudamper.graphql.codegen.TestUtils.assertSameTrimmedContent;
import static net.matsudamper.graphql.codegen.TestUtils.getFileByName;
import static java.util.Collections.singletonList;

class GraphQLCodegenUnionProjectionTest {

    private final File outputBuildDir = new File("build/generated");

    private MappingConfig mappingConfig;

    @BeforeEach
    void init() {
        mappingConfig = new MappingConfig();
        mappingConfig.setGenerateClient(true);
    }

    @AfterEach
    void cleanup() {
        Utils.deleteDir(outputBuildDir);
    }

    @Test
    void generate_CheckFiles_with_projections() throws Exception {
        generate();

        File outputJavaClassesDir = new File("build/generated");
        File[] files = Objects.requireNonNull(outputJavaClassesDir.listFiles());

        assertSameTrimmedContent(new File("src/test/resources/expected-classes/" +
                        "UnionToResolveResponseProjection.java.txt"),
                getFileByName(files, "UnionToResolveResponseProjection.java"));
    }

    private void generate() throws IOException {
        new JavaGraphQLCodegen(singletonList("src/test/resources/schemas/union-projection.graphqls"),
                outputBuildDir, mappingConfig, TestUtils.getStaticGeneratedInfo(mappingConfig))
                .generate();
    }

}
