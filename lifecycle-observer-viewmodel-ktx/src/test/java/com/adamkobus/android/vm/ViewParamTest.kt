package com.adamkobus.android.vm

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class ViewParamTest {

    @JvmField
    @Rule
    val timeoutRule = Timeout(10, TimeUnit.SECONDS)

    private val deferred = CompletableDeferred<Unit>()
    private val testSubject = ViewParam<Int>()

    @Test
    fun `GIVEN bound value WHEN collect THEN offered value is collected`() = runTest {
        // given
        val expectedValue = Random.nextInt()
        testSubject.bind(expectedValue)

        // when
        val job = launch(Dispatchers.IO) {
            testSubject.collect { obtained ->

                // then
                assertEquals(expectedValue, obtained)
                deferred.complete(Unit)
            }
        }
        deferred.await()
        job.cancel()
    }

    @Test
    fun `GIVEN bound value WHEN observe THEN offered value is collected`() = runTest {
        // given
        val expectedValue = Random.nextInt()
        testSubject.bind(expectedValue)

        // when
        val obtained = testSubject.observe().first()

        // then
        assertEquals(expectedValue, obtained)
    }
}
