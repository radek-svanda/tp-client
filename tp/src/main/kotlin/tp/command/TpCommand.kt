/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package tp.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.fileProperties
import tp.api.ApiUrlFactory
import tp.api.time.TimeApi
import tp.client.Settings
import tp.client.json.DateDeserializer
import tp.client.ktor.feature.xml.XmlFeature
import tp.client.ktor.feature.xml.XmlSerializer
import tp.client.ktor.feature.yaml.readYamlProperties
import tp.client.parser.DateParser
import tp.command.time.AddCommand
import tp.command.time.ShowCommand
import tp.command.time.TimeCommand
import java.time.LocalDateTime

class TpCommand : CliktCommand() {

    override fun run() {
        // nothing here yet
    }

}

fun main(args: Array<String>) {

    startKoin {
        modules(
            timesheetModule
        )
        fileProperties("/koin.properties")
        properties(readYamlProperties(
            "${System.getProperty("user.home")}/.config/tp/tp.yaml"
        ))
    }

    TpCommand()
        .subcommands(
            TimeCommand()
                .subcommands(
                    ShowCommand(),
                    AddCommand()
                )
        )
        .main(args)
}

val timesheetModule = module {
    val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer {
                registerTypeAdapter(LocalDateTime::class.java, DateDeserializer())
            }
        }
        install(XmlFeature) {
            serializer = XmlSerializer.Default()
        }
    }

    single {
        httpClient
    }

    single { ApiUrlFactory() }
    single { TimeApi() }
    single { Settings() }
    single { DateParser() }

}
