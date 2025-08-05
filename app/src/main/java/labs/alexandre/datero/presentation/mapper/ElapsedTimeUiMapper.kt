package labs.alexandre.datero.presentation.mapper

import labs.alexandre.datero.R
import labs.alexandre.datero.domain.model.ElapsedTime
import labs.alexandre.datero.domain.model.TimeUnit
import labs.alexandre.datero.presentation.model.ElapsedTimeUiModel
import labs.alexandre.datero.core.resources.StringProvider
import javax.inject.Inject

class ElapsedTimeUiMapper @Inject constructor(
    private val stringProvider: StringProvider
) {

    fun mapToUiModel(elapsedTime: ElapsedTime): ElapsedTimeUiModel {
        return when (elapsedTime.unit) {
            TimeUnit.MINUTES -> {
                ElapsedTimeUiModel(
                    valueText = elapsedTime.time.toString(),
                    unitText = stringProvider.getString(R.string.dashboard_elapsed_minutes)
                )
            }

            TimeUnit.HOURS -> {
                ElapsedTimeUiModel(
                    valueText = stringProvider.getString(
                        R.string.dashboard_elapsed_plus_value,
                        elapsedTime.time
                    ),
                    unitText = stringProvider.getQuantityString(
                        R.plurals.dashboard_elapsed_hours,
                        elapsedTime.time
                    )
                )
            }
        }
    }

}