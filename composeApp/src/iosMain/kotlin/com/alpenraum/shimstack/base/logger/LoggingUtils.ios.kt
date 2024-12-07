package com.alpenraum.shimstack.base.logger

actual fun ShimstackLogger.getClassNameFromStacktrace(
    stackTrace: String,
    loggerClass: String,
): String? {
    val regex = "\\.(\\w+)#".toRegex()

    val lines = stackTrace.lines()
    val filteredLines =
        lines.subList(lines.indexOfFirst { it.contains(loggerClass) } + 1, lines.lastIndex)
    val matchingLine =
        filteredLines
            .firstOrNull { line ->
                regex.containsMatchIn(line)
            } ?: return null

    return regex
        .find(matchingLine)
        ?.value
        ?.replace(".", "")
        ?.replace("#", "")
}
