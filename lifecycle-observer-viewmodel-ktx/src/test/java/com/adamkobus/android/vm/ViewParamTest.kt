package com.adamkobus.android.vm

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ViewParamTest {

    @JvmField
    @Rule
    val timeoutRule = Timeout(10, TimeUnit.SECONDS)

    private val testSubject = ViewParam<Int>()

    @Test
    fun `GIVEN bound value WHEN collect THEN offered value is collected`() = runBlocking {
        // given
        val expectedValue = Random.nextInt()
        testSubject.bind(expectedValue)
        val latch = CountDownLatch(1)

        // when
        val job = launch(Dispatchers.IO) {
            testSubject.collect { obtained ->

                // then
                assertEquals(expectedValue, obtained)
                latch.countDown()
            }
        }
        latch.await()
        job.cancel()
    }

    @Test
    fun `GIVEN bound value WHEN observe THEN offered value is collected`() = runBlocking {
        // given
        val expectedValue = Random.nextInt()
        testSubject.bind(expectedValue)

        // when
        val obtained = testSubject.observe().first()

        // then
        assertEquals(expectedValue, obtained)
    }
}
