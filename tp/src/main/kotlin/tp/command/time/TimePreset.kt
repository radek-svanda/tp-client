package tp.command.time

import org.koin.core.component.KoinComponent

internal class TimePreset(
    val task: Long? = null,
    val spent: Int? = null,
    val remain: Int? = null
) {

    companion object : KoinComponent {

        fun getPreset(name: String?): TimePreset {
            return if (name == null) {
                emptyPreset
            } else {
                val prefix = "time.presets.${name}"
                TimePreset(
                    getKoin().getProperty<String>("${prefix}.task")?.toLong(),
                    getKoin().getProperty<String>("${prefix}.spent")?.toInt(),
                    getKoin().getProperty<String>("${prefix}.remain")?.toInt()
                )
            }
        }

    }

}

private val emptyPreset: TimePreset = TimePreset()
