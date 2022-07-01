package tp.command.preset

import com.github.ajalt.clikt.core.CliktCommand
import org.koin.core.component.KoinComponent
import tp.client.ktor.feature.yaml.readYamlProperties
import java.util.*

class ShowCommand(private val configLocation: String) : CliktCommand() {

    companion object : KoinComponent;

    override fun run() {
        val properties = readYamlProperties(configLocation)

        val values = TreeMap<String, MutableMap<String, String?>>()

        properties.toSortedMap()
            .filter { it.key.startsWith("time.presets") }
            .map { it.key.substring("time.presets.".length) }
            .map { it.split('.') }

            .forEach {
                if (values[it[0]] == null) {
                    values[it[0]] = TreeMap<String, String?>()
                }
                values[it[0]]?.put(it[1], properties["time.presets.${it[0]}.${it[1]}"])
            }

        values.forEach { preset ->
            println(preset.key)
            preset.value.forEach { values ->
                println("\t${values.key} : ${values.value}")
            }
        }

    }
}
