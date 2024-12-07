package com.alpenraum.shimstack.base.logger

import kotlin.test.Test
import kotlin.test.assertEquals

class ShimstackLoggerTest {
    @Test
    fun `extract class name from android specific stacktrace`() {
        val logger = ShimstackLogger()

        testCases.forEach { (input, result) ->
            val actual = logger.getClassNameFromStacktrace(input)
            assertEquals(result, actual)
        }
    }

    companion object {
        private val testCases =
            mapOf(
                "java.lang.Exception\n" +
                    "\tat com.alpenraum.shimstack.Greeting.greet(Greeting.kt:15)\n" +
                    "\tat com.alpenraum.shimstack.ComposableSingletons,AppKt,lambda-2,1,1,2.invoke(App.kt:46)\n" +
                    "\tat com.alpenraum.shimstack.ComposableSingletons,AppKt,lambda-2,1,1,2.invoke(App.kt:45)\n" +
                    "at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:118)\n" +
                    "\tat androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:35)\n" +
                    "at androidx.compose.animation.AnimatedVisibilityKt.AnimatedEnterExitImpl(AnimatedVisibility.kt:771)\n" +
                    "\tat androidx.compose.animation.AnimatedVisibilityKt,AnimatedEnterExitImpl,4.invoke(UnknownSource:27)\n" +
                    "at androidx.compose.animation.AnimatedVisibilityKt,AnimatedEnterExitImpl,4.invoke(UnknownSource:10)\n" +
                    "\tat androidx.compose.runtime.RecomposeScopeImpl.compose(RecomposeScopeImpl.kt:192)\n" +
                    "\tat androidx.compose.runtime.ComposerImpl.recomposeToGroupEnd(Composer.kt:2825)\n" +
                    "at androidx.compose.runtime.ComposerImpl.skipToGroupEnd(Composer.kt:3139)\n" +
                    "\tat androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibilityImpl(AnimatedVisibility.kt:715)\n" +
                    "\tat androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility(AnimatedVisibility.kt:282)\n" +
                    "at com.alpenraum.shimstack.ComposableSingletons,AppKt,lambda-2,1.invoke(App.kt:45)\n" +
                    "\tat com.alpenraum.shimstack.ComposableSingletons,AppKt,lambda-2,1.invoke(App.kt:35)\n" +
                    "\tat androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:109)\n" +
                    "at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke(ComposableLambda.jvm.kt:35)\n" +
                    "\tat androidx.compose.runtime.RecomposeScopeImpl.compose(RecomposeScopeImpl.kt:192)\n" +
                    "\tat androidx.compose.runtime.ComposerImpl.recomposeToGroupEnd(Composer.kt:2825)\n" +
                    "at androidx.compose.runtime.ComposerImpl.skipCurrentGroup(Composer.kt:3116)\n" +
                    "\tat androidx.compose.runtime.ComposerImpl.doCompose(Composer.kt:3607)\n" +
                    "\tat androidx.compose.runtime.ComposerImpl.recompose,runtime_release(Composer.kt:3552)\n" +
                    "at androidx.compose.runtime.CompositionImpl.recompose(Composition.kt:948)\n" +
                    "\tat androidx.compose.runtime.Recomposer.performRecompose(Recomposer.kt:1206)\n" +
                    "\tat androidx.compose.runtime.Recomposer.access,performRecompose(Recomposer.kt:132)\n" +
                    "at androidx.compose.runtime.Recomposer,runRecomposeAndApplyChanges,2,1.invoke(Recomposer.kt:616)\n" +
                    "\tat androidx.compose.runtime.Recomposer,runRecomposeAndApplyChanges,2,1.invoke(Recomposer.kt:585)\n" +
                    "a tandroidx.compose.ui.platform.AndroidUiFrameClock,withFrameNanos,2,callback,1.doFrame(AndroidUiFrameClock.android.kt:41)\n" +
                    "\tat androidx.compose.ui.platform.AndroidUiDispatcher.performFrameDispatch(AndroidUiDispatcher.android.kt:109)\n" +
                    "at androidx.compose.ui.platform.AndroidUiDispatcher.access,performFrameDispatch(AndroidUiDispatcher.android.kt:41)\n" +
                    "\tat androidx.compose.ui.platform.AndroidUiDispatcher,dispatchCallback,1.doFrame(AndroidUiDispatcher.android.kt:69)\n" +
                    "at android.view.Choreographer,CallbackRecord.run(Choreographer.java:1404)\n" +
                    "\tat android.view.Choreographer,CallbackRecord.run(Choreographer.java:1415)\n" +
                    "\tat android.view.Choreographer.doCallbacks(Choreographer.java:1015)\n" +
                    "at android.view.Choreographer.doFrame(Choreographer.java:941)\n" +
                    "\tat android.view.Choreographer,FrameDisplayEventReceiver.run(Choreographer.java:1389)\n" +
                    "\tat android.os.Handler.handleCallback(Handler.java:959)\n" +
                    "\tat android.os.Handler.dispatchMessage(Handler.java:100)\n" +
                    "at android.os.Looper.loopOnce(Looper.java:232)\n" +
                    "\tat android.os.Looper.loop(Looper.java:317)\n" +
                    "\tat android.app.ActivityThread.main(ActivityThread.java:8705)\n" +
                    "\tat java.lang.reflect.Method.invoke(NativeMethod)\n" +
                    "at com.android.internal.os.RuntimeInit,MethodAndArgsCaller.run(RuntimeInit.java:580)" to "Greeting",
            )
    }
}
