package did.chris.alt.layoutCoordination.viewTransitions.viewActions


interface ViewAction<in T> {

    // Functions
    fun execute(executor:T)
}
