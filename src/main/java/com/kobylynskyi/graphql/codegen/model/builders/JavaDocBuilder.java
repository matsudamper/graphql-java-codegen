package com.kobylynskyi.graphql.codegen.model.builders;

import com.kobylynskyi.graphql.codegen.model.definitions.ExtendedDefinition;
import com.kobylynskyi.graphql.codegen.utils.Utils;
import graphql.language.AbstractDescribedNode;
import graphql.language.Comment;
import graphql.language.Description;
import graphql.language.NamedNode;
import graphql.language.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Java doc builder
 */
public class JavaDocBuilder {

    private JavaDocBuilder() {
    }

    /**
     * Get java doc from description of the definition and it's extensions.
     * If no description is present in the definition and extension then return from comments
     *
     * @param extendedDefinition Extended GraphQL definition
     * @param <T>                base type
     * @param <E>                extension type
     * @return List of java docs
     */
    public static <T extends NamedNode<T>, E extends T> List<String> build(
            ExtendedDefinition<T, E> extendedDefinition) {
        List<String> javaDocFromDescription = buildFromDescription(extendedDefinition);
        if (javaDocFromDescription.isEmpty()) {
            return buildFromComments(extendedDefinition);
        }
        return javaDocFromDescription;
    }

    /**
     * Get java doc from description of the node.
     * If no description is present then return from comments.
     *
     * @param node GraphQL node
     * @return List of java docs
     */
    public static List<String> build(Node<?> node) {
        List<String> javaDocFromDescription = buildFromDescription(node);
        if (javaDocFromDescription.isEmpty()) {
            return buildFromComments(node);
        }
        return javaDocFromDescription;
    }

    /**
     * Get java doc from description for the given definition
     *
     * @param extendedDefinition Extended GraphQL definition
     * @param <T>                base type
     * @param <E>                extension type
     * @return List of java docs
     */
    public static <T extends NamedNode<T>, E extends T> List<String> buildFromDescription(
            ExtendedDefinition<T, E> extendedDefinition) {
        T definition = extendedDefinition.getDefinition();
        List<E> extensions = extendedDefinition.getExtensions();

        List<String> descriptions = new ArrayList<>();
        if (definition instanceof AbstractDescribedNode) {
            Description description = ((AbstractDescribedNode<?>) definition).getDescription();
            if (description != null && Utils.isNotBlank(description.getContent())) {
                descriptions.add(description.getContent().trim());
            }
            extensions.stream()
                    .filter(Objects::nonNull)
                    .map(AbstractDescribedNode.class::cast)
                    .map(AbstractDescribedNode::getDescription).filter(Objects::nonNull)
                    .map(Description::getContent).filter(Utils::isNotBlank)
                    .map(String::trim).forEach(descriptions::add);
        }
        return descriptions;
    }

    /**
     * Get java doc from description for the given node
     *
     * @param node GraphQL node
     * @return List of java docs
     */
    public static List<String> buildFromDescription(Node<?> node) {
        if (node instanceof AbstractDescribedNode) {
            Description description = ((AbstractDescribedNode<?>) node).getDescription();
            if (description != null && Utils.isNotBlank(description.getContent())) {
                return Collections.singletonList(description.getContent().trim());
            }
        }
        return Collections.emptyList();
    }

    /**
     * Get java doc from comments for the given definition
     *
     * @param extendedDefinition Extended GraphQL definition
     * @param <T>                base type
     * @param <E>                extension type
     * @return List of java docs
     */
    public static <T extends NamedNode<T>, E extends T> List<String> buildFromComments(
            ExtendedDefinition<T, E> extendedDefinition) {
        T definition = extendedDefinition.getDefinition();
        List<E> extensions = extendedDefinition.getExtensions();

        List<String> comments = new ArrayList<>();
        if (definition != null && definition.getComments() != null) {
            definition.getComments().stream()
                    .map(Comment::getContent).filter(Utils::isNotBlank)
                    .map(String::trim).forEach(comments::add);
        }
        extensions.stream()
                .map(Node::getComments)
                .flatMap(Collection::stream).filter(Objects::nonNull)
                .map(Comment::getContent).filter(Utils::isNotBlank)
                .map(String::trim).forEach(comments::add);
        return comments;
    }

    /**
     * Get java doc from comments for the given node
     *
     * @param node GraphQL node
     * @return List of java docs
     */
    public static List<String> buildFromComments(Node<?> node) {
        if (node != null && node.getComments() != null) {
            return node.getComments().stream()
                    .map(Comment::getContent).filter(Utils::isNotBlank)
                    .map(String::trim).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
