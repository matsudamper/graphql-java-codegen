package net.matsudamper.graphql.codegen.generators;

import java.io.File;
import java.util.List;

/**
 * Represents files generator
 */
@FunctionalInterface
public interface FilesGenerator {

    List<File> generate();

}
