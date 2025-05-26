package com.ecommerce.support

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature

object KeyExtractor {

    fun extract(joinPoint: JoinPoint, keys: Array<String>): String {
        val method = (joinPoint.signature as MethodSignature).method
        val args = joinPoint.args
        val names = method.parameters.map { parameter -> parameter.name }

        return keys.map { key ->
            val index = names.indexOf(key)
            if (index == -1) throw IllegalArgumentException("Key name '$key' not found in parameters")

            args[index].toString()
        }.joinToString(":")
    }

}