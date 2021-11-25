package tp.client.ktor.feature.yaml

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.nio.file.Files
import java.nio.file.Paths

private val mapper: ObjectMapper = ObjectMapper(YAMLFactory())

fun readYaml(location: String): Map<String, Any> {
    val path = Paths.get(location)
    val reference = object : TypeReference<Map<String, Any>>() {}
    return Files.newBufferedReader(path).use {
        mapper.readValue(it, reference)
    }
}

fun readYamlProperties(location: String): Map<String, String> {
    val raw = readYaml(location)
    val props = HashMap<String, String>()
    raw.entries.forEach { collectProperties(props, it) }
    return props
}

@Suppress("UNCHECKED_CAST")
private fun collectProperties(target: MutableMap<String, String>, source: Map.Entry<String, Any>, prefix: String = "") {
    val key = if (prefix == "") {
        source.key
    } else {
        "${prefix}.${source.key}"
    }
    if (source.value is Map<*, *>) {
        (source.value as Map<*, *>).forEach { collectProperties(target, it as Map.Entry<String, Any>, key) }
    } else {
        target[key] = "${source.value}"
    }
}
