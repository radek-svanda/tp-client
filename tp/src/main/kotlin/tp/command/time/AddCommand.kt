package tp.command.time

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.long
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tp.client.parser.DateParser

class AddCommand : CliktCommand() {

    private val date: String? by option(help = "Add time for this date").default("today")
    private val task: Long by option(help = "The task number").long().required()
    private val hours: Int by option(help = "Spent hours").int().required()
    private val remain: Int by option(help = "Remaining hours").int().default(0)

    companion object : KoinComponent {
        val dateParser: DateParser by inject()
    }

    override fun run() {
        TODO("Not yet implemented")
    }

}
