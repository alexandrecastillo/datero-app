package labs.alexandre.datero.presentation.root.state

data class DateroRootState(
    val dialogState: DateroRootDialogState
) {

    companion object {
        val DEFAULT = DateroRootState(
            dialogState = DateroRootDialogState.None
        )
    }

}

sealed interface DateroRootDialogState {
    object None : DateroRootDialogState
    object InvalidTimeDialog : DateroRootDialogState
}