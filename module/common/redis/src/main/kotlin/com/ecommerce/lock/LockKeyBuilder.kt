package com.ecommerce.lock

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Component
class LockKeyBuilder(private val property: DistributedLockProperty) {

    fun extractKey(point: JoinPoint, keyTarget: Class<LockKey>): String {
        val method = (point.signature as? MethodSignature)?.method
            ?: throw IllegalArgumentException(NOT_METHOD_SIGNATURE)

        return method.parameters.withIndex()
            .filter { indexed -> indexed.value.isAnnotationPresent(keyTarget) }
            .also { filtered -> if (filtered.isEmpty()) throw IllegalStateException(NOT_FOUND_ANNOTATION_TARGET) }
            .map { (index, param) -> point.args[index] to param.getAnnotation(keyTarget) }
            .sortedBy { it.second.order }
            .map { (arg, annotation) ->
                when {
                    annotation.wildcard -> "*"
                    arg == null && annotation.wildcardOnNull -> "*"
                    arg == null -> throw IllegalArgumentException(NOT_ENABLE_WILDCARD_ON_NULL_PARAMETER)
                    else -> arg.toString()
                }
            }.joinToString(":")
    }

    fun buildLockKey(prefix: String, key: String) =
        "${property.keyPrefix}:$prefix:$key"

    companion object {
        const val NOT_METHOD_SIGNATURE =
            "LockKeyBuilder: Lock annotation must be a MethodSignature"
        const val NOT_FOUND_ANNOTATION_TARGET =
            "LockKeyBuilder: No parameter annotated with @LockKey"
        const val NOT_ENABLE_WILDCARD_ON_NULL_PARAMETER =
            "LockKeyBuilder: Null parameter encountered without wildcardOnNull enabled"
    }

}
