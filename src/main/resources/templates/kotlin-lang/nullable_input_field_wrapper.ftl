<#if package?has_content>
package ${package}

</#if>
<#if generatedAnnotation && generatedInfo.getGeneratedType()?has_content>
@${generatedInfo.getGeneratedType()}(
    value = ["com.kobylynskyi.graphql.codegen.GraphQLCodegen"],
    date = "${generatedInfo.getDateTime()}"
)
</#if>
sealed interface ${className}<out T> {
    data class Defined<T>(val value: T) : ${className}<T>
    data object Undefined : ${className}<Nothing>
}
