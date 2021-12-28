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
import tp.api.assignables.TaskApi
import tp.api.time.TimeApi
import tp.client.parser.DateParser
import java.io.File
import java.time.DayOfWeek
import java.util.concurrent.TimeUnit

class AddCommand : CliktCommand() {

    private val task: Long? by option(help = "The task number").long()
    private val date: String by option(help = "Add time for this date").required()
    private val spent: Int? by option(help = "Spent hours").int()
    private val remain: Int? by option(help = "Remaining hours").int()
    private val preset: String? by option(help = "Use preset defined in configuration to set parameters")

    companion object : KoinComponent {
        val dateParser: DateParser by inject()
        val timeApi: TimeApi by inject()
        val taskApi: TaskApi by inject()
    }

    private val timePreset: TimePreset
        get() = TimePreset.getPreset(preset)

    private val taskValue: Long
        get() = task ?: timePreset.task ?: selectTask() ?: throw missingOption("task")

    private val spentValue: Int
        get() = spent ?: timePreset.spent ?: throw missingOption("spent")

    private val remainValue: Int
        get() = remain ?: timePreset.remain ?: throw missingOption("remain")

    private fun missingOption(name: String) = MissingOption(
        registeredOptions().first { it.names.contains("--${name}") },
        currentContext
    )

    private fun selectTask(): Long {
        val rows = runBlocking {
            val tasks = taskApi.myTasks()
            tasks.items.map { "${it.id} ${it.name}" }.joinToString("\n")
        }
        val temp = File.createTempFile("tp-client", "tasks")
        temp.writeText(rows)
        val sel =
//            "cat ${temp.absolutePath} | fzf"
            "fzf --help"
                .runCommand(
//            input = temp
                )
        // needs terminal:
        // https://github.com/JetBrains/pty4j

        println("${sel}")
//        TODO()
        return 1L
    }

    override fun run() {
        runBlocking {
            date.let { dateParser.parse(it) }
                .filter {
                    it.dayOfWeek != DayOfWeek.SATURDAY && it.dayOfWeek != DayOfWeek.SUNDAY
                }
                .forEach { dateValue ->
                    timeApi.add(
                        taskId = taskValue,
                        date = dateValue,
                        spent = spentValue,
                        remain = remainValue
                    )
                }
        }
    }

}

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
    input: File? = null
): String? = runCatching {
    ProcessBuilder("\\s".toRegex().split(this))
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
//        .redirectInput(input)
        .start().also {
            it.waitFor()
//            it.waitFor(timeoutAmount, timeoutUnit)
        }

        .inputStream.bufferedReader().readText()
}.onFailure { it.printStackTrace() }.getOrNull()
