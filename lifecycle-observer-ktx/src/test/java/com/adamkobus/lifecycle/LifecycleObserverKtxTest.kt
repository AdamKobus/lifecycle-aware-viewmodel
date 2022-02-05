package com.adamkobus.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.concurrent.TimeUnit

class LifecycleObserverKtxTest {

    @JvmField
    @Rule
    val timeoutRule = Timeout(10, TimeUnit.SECONDS)

    private val lifecycleOwner: LifecycleOwner = mockk()
    private val suspendTask: suspend () -> Unit = mockk()
    private val task: () -> Unit = mockk()

    @Before
    fun setUp() {
        coEvery { suspendTask.invoke() } coAnswers {}
        every { task.invoke() } answers {}
    }

    @Test
    fun `GIVEN start task registered with runOnCreateDestroy WHEN ON_CREATE THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnCreateDestroy { onCreate = suspendTask }

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_CREATE)

        // then
        coVerify { suspendTask.invoke() }
    }

    @Test
    fun `GIVEN start task registered with runOnCreateDestroy WHEN not ON_CREATE THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnCreateDestroy { onCreate = suspendTask }
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_CREATE }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { suspendTask.invoke() }
        }
    }

    @Test
    fun `GIVEN stop task registered with runOnCreateDestroy WHEN ON_DESTROY THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnCreateDestroy { onDestroy = task }

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_DESTROY)

        // then
        coVerify { task.invoke() }
    }

    @Test
    fun `GIVEN stop task registered with runOnCreateDestroy WHEN not ON_DESTROY THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnCreateDestroy { onDestroy = task }
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_DESTROY }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { task.invoke() }
        }
    }

    @Test
    fun `GIVEN start task registered with runOnStartStop WHEN ON_START THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStartStop { onStart = suspendTask }

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_START)

        // then
        coVerify { suspendTask.invoke() }
    }

    @Test
    fun `GIVEN start task registered with runOnStartStop WHEN not ON_START THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStartStop { onStart = suspendTask }
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_START }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { suspendTask.invoke() }
        }
    }

    @Test
    fun `GIVEN stop task registered with runOnStartStop WHEN ON_STOP THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStartStop { onStop = task }

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_STOP)

        // then
        coVerify { task.invoke() }
    }

    @Test
    fun `GIVEN stop task registered with runOnStartStop WHEN not ON_STOP THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStartStop { onStop = task }
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_STOP }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { task.invoke() }
        }
    }

    @Test
    fun `GIVEN start task registered with runOnResumePause WHEN ON_RESUME THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnResumePause { onResume = suspendTask }

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_RESUME)

        // then
        coVerify { suspendTask.invoke() }
    }

    @Test
    fun `GIVEN start task registered with runOnResumePause WHEN not ON_RESUME THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnResumePause { onResume = suspendTask }
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_RESUME }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { suspendTask.invoke() }
        }
    }

    @Test
    fun `GIVEN stop task registered with runOnStartStop WHEN ON_PAUSE THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnResumePause { onPause = task }

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_PAUSE)

        // then
        coVerify { task.invoke() }
    }

    @Test
    fun `GIVEN stop task registered with runOnStartStop WHEN not ON_PAUSE THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnResumePause { onPause = task }
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_PAUSE }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { task.invoke() }
        }
    }

    // single event registration

    @Test
    fun `GIVEN start task registered with runOnCreate WHEN ON_CREATE THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnCreate(suspendTask)

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_CREATE)

        // then
        coVerify { suspendTask.invoke() }
    }

    @Test
    fun `GIVEN start task registered with runOnCreate WHEN not ON_CREATE THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnCreate(suspendTask)
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_CREATE }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { suspendTask.invoke() }
        }
    }

    @Test
    fun `GIVEN stop task registered with runOnDestroy WHEN ON_DESTOY THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnDestroy(task)

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_DESTROY)

        // then
        coVerify { task.invoke() }
    }

    @Test
    fun `GIVEN stop task registered with runOnDestroy WHEN not ON_DESTOY THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnDestroy(task)
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_DESTROY }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { task.invoke() }
        }
    }

    @Test
    fun `GIVEN start task registered with runOnStart WHEN ON_START THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStart(suspendTask)

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_START)

        // then
        coVerify { suspendTask.invoke() }
    }

    @Test
    fun `GIVEN start task registered with runOnStart WHEN not ON_START THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStart(suspendTask)
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_START }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { suspendTask.invoke() }
        }
    }

    @Test
    fun `GIVEN stop task registered with runOnStop WHEN ON_STOP THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStop(task)

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_STOP)

        // then
        coVerify { task.invoke() }
    }

    @Test
    fun `GIVEN stop task registered with runOnDestroy WHEN not ON_STOP THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnStop(task)
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_STOP }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { task.invoke() }
        }
    }

    @Test
    fun `GIVEN start task registered with runOnResume WHEN ON_RESUME THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnResume(suspendTask)

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_RESUME)

        // then
        coVerify { suspendTask.invoke() }
    }

    @Test
    fun `GIVEN start task registered with runOnResume WHEN not ON_RESUME THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnResume(suspendTask)
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_RESUME }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { suspendTask.invoke() }
        }
    }

    @Test
    fun `GIVEN stop task registered with runOnPause WHEN ON_PAUSE THEN task is executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnPause(task)

        // when
        testSubject.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_PAUSE)

        // then
        coVerify { task.invoke() }
    }

    @Test
    fun `GIVEN stop task registered with runOnPause WHEN not ON_PAUSE THEN task not executed`() = runBlocking {
        // given
        val testSubject = testSubject(this)
        testSubject.runOnPause(task)
        Lifecycle.Event.values().filter { it != Lifecycle.Event.ON_PAUSE }.forEach { event ->
            // when
            testSubject.onStateChanged(lifecycleOwner, event)

            // then
            coVerify(exactly = 0) { task.invoke() }
        }
    }

    private fun testSubject(scope: CoroutineScope) = LifecycleObserverKtx(scope, Dispatchers.Default)
}