# GraphQL Codegen #

[![Donate](https://img.shields.io/badge/Donate-green.svg)](https://send.monobank.ua/jar/2bpWyBqBp3)

![Build](https://github.com/matsudamper/graphql-java-codegen/workflows/Build/badge.svg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)



GraphQL Java Codegen makes it easy to make your Java application to follow a schema-first approach whether it is a server or client application.

Following classes can be generated based on your GraphQL schema:
* Interfaces for GraphQL queries, mutations and subscriptions.
* Interfaces for GraphQL unions.
* POJO classes for GraphQL types and inputs.
* Enum classes for GraphQL enums.
* Interfaces for GraphQL type fields (e.g. for parametrized fields) aka "Resolvers".
* Client Request classes for GraphQL queries, mutations and subscriptions.


## Features
* Generate classes in Java or Kotlin.
* Recursive schemas lookup by file name pattern.
* Generate code based on GraphQL schema or GraphQL Query Introspection Result.
* Generate POJOs with or without: Builder pattern, immutable fields, `toString()`, `equals()` and `hashCode()`, etc.
* Flexible API interfaces naming conventions (based on schema file name, folder name, etc.)
* Custom java package names for model and API classes.
* Custom prefix/suffix for model, API, type resolver, request, response classes.
* Custom annotations for generated classes (e.g.: validation annotations for generated model classes or specific type fields, annotations for GraphQL directives, etc.)
* Custom FreeMarker templates for generated classes.
* Configurable wrapper for nullable fields of input types (e.g. spring-graphql `ArgumentValue`).
* Support of unknown fields in generated model classes.
* Relay support.


**For the full list of codegen configs please refer to: [Codegen Options](docs/codegen-options.md)**


## Supported plugins

* Gradle plugin: [graphql-java-codegen-gradle-plugin](plugins/gradle)

The core library is also published as `io.github.kobylynskyi:graphql-java-codegen` and can be used directly via
`com.kobylynskyi.graphql.codegen.java.JavaGraphQLCodegen` / `com.kobylynskyi.graphql.codegen.kotlin.KotlinGraphQLCodegen`.


## Inspired by

[swagger-codegen](https://github.com/swagger-api/swagger-codegen)

