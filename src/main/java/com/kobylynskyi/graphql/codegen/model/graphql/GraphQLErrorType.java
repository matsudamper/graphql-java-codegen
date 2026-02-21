package com.kobylynskyi.graphql.codegen.model.graphql;

/**
 * Possible types of GraphQL errors
 * Copied from graphql-java library
 */
@SuppressWarnings("checkstyle:TypeName")
public enum GraphQLErrorType {
    InvalidSyntax,
    ValidationError,
    DataFetchingException,
    OperationNotSupported,
    ExecutionAborted
}
