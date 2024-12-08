package com.alpenraum.shimstack.base.logger

expect fun ShimstackLogger.getClassNameFromStacktrace(
    stackTrace: String,
    loggerClass: String = this::class.simpleName.toString(),
): String?
