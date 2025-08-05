package labs.alexandre.datero.presentation.root.intent

sealed interface DateroRootIntent {
    object RecalculateTimes: DateroRootIntent
    object DismissRecalculateTimesDialog: DateroRootIntent
}