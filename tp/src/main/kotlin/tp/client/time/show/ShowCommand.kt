package tp.client.time.show

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tp.api.time.TimeApi
import tp.client.Settings
import tp.client.parser.YearMonthParser
import java.time.DayOfWeek

class ShowCommand(
    private val monthParser: YearMonthParser = YearMonthParser()
) : CliktCommand() {

    private val month: String? by option(help = "Show this month timesheet").default("current")

    companion object : KoinComponent {
        val timeApi: TimeApi by inject()
        val settings: Settings by inject()
    }

    override fun run() {
        val now = monthParser.parse(month)

        runBlocking {
            val response = timeApi.timesheet(settings.username!!, now.atDay(1), now.atEndOfMonth())
            for (i in 1..now.month.length(now.isLeapYear)) {
                val d = now.atDay(i)
                if (d.dayOfWeek == DayOfWeek.SATURDAY || d.dayOfWeek == DayOfWeek.SUNDAY) {
                    println("* ${d} - N/A")
                } else if (response.containsDay(d)) {
                    response.getDay(d).forEach { item ->
                        println("* ${item.dateAsDate()} - ${item.spent}h - ${item.assignable.id} (${item.project.name}) ${item.assignable.name}")
                    }
                } else {
                    println("* ${d} - 0h")
                }
            }
        }

    }
}
