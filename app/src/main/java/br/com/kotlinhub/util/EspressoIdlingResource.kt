package br.com.kotlinhub.util

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "NetworkCall"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.apply {
        if (isIdleNow.not()) decrement()
    }
}