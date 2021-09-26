package tp.command.time

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.MissingOption
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.long
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tp.api.time.TimeApi
import tp.client.parser.DateParser

class AddCommand : CliktCommand() {

    private val task: Long? by option(help = "The task number").long()
    private val date: String by option(help = "Add time for this date").required()
    private val spent: Int? by option(help = "Spent hours").int()
    private val remain: Int? by option(help = "Remaining hours").int()
    private val preset: String? by option(help = "Use preset defined in configuration to set parameters")

    private val timePreset: TimePreset
        get() = TimePreset.getPreset(preset)

    private val taskValue: Long
        get() = task ?: timePreset.task ?: throw missingOption("task")

    private val spentValue: Int
        get() = spent ?: timePreset.spent ?: throw missingOption("spent")

    private val remainValue: Int
        get() = remain ?: timePreset.remain ?: throw missingOption("remain")

    private fun missingOption(name: String) = MissingOption(
        registeredOptions().first { it.names.contains("--${name}") },
        currentContext
    )

    companion object : KoinComponent {
        val dateParser: DateParser by inject()
        val timeApi: TimeApi by inject()
    }

    override fun run() {
        runBlocking {
            timeApi.add(
                taskId = taskValue,
                date = date.let { dateParser.parse(it) },
                spent = spentValue,
                remain = remainValue
            )
        }
    }

}
