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
                """
                [] 🟢 (kotlin.Exception
                    at 0   Shimstack.debug.dylib               0x1080f78a3        kfun:kotlin.Throwable#<init>(){} + 99 
                    at 1   Shimstack.debug.dylib               0x1080f0aab        kfun:kotlin.Exception#<init>(){} + 87 
                    at 2   Shimstack.debug.dylib               0x1077580b3        kfun:com.alpenraum.shimstack.base.logger.ShimstackLogger#generateTag(){}kotlin.String + 207 
                    at 3   Shimstack.debug.dylib               0x107756bcb        kfun:com.alpenraum.shimstack.Greeting#greet(){}kotlin.String + 507 
                    at 4   Shimstack.debug.dylib               0x1077529af        kfun:com.alpenraum.shimstack.ComposableSingletons,AppKt.<init>,lambda,{','}7,lambda,{','}6#internal + 1463 
                    at 5   Shimstack.debug.dylib               0x1077561bf        kfun:com.alpenraum.shimstack.ComposableSingletons,AppKt.,{','}<init>,lambda,{','}7,lambda,{','}6,FUNCTION_REFERENCE,{','}8.invoke#internal + 143 
                    at 6   Shimstack.debug.dylib               0x10775630b        kfun:com.alpenraum.shimstack.ComposableSingletons,AppKt.,{','}<init>,lambda,{','}7,lambda,{','}6,FUNCTION_REFERENCE,{','}8.,{','}<bridge-DNNNNU>invoke(androidx.compose.animation.AnimatedVisibilityScope;androidx.compose.runtime.Composer;kotlin.Int){}#internal + 195 
                    at 7   Shimstack.debug.dylib               0x108223d07        kfun:kotlin.Function3#invoke(1:0;1:1;1:2){}1:3-trampoline + 123 
                    at 8   Shimstack.debug.dylib               0x10840854b        kfun:androidx.compose.runtime.internal.ComposableLambdaImpl#invoke(kotlin.Any?;androidx.compose.runtime.Composer;kotlin.Int){}kotlin.Any? + 635 
                    at 9   Shimstack.debug.dylib               0x108416d57        kfun:androidx.compose.runtime.internal.ComposableLambdaImpl#,{','}<bridge-NNNNNU>invoke(kotlin.Any?;androidx.compose.runtime.Composer;kotlin.Int){}kotlin.Any?(kotlin.Any?;androidx.compose.runtime.Composer;kotlin.Any?){}kotlin.Any? + 199 
                    at 10  Shimstack.debug.dylib               0x108223d07        kfun:kotlin.Function3#invoke(1:0;1:1;1:2){}1:3-trampoline + 123 
                    at 11  Shimstack.debug.dylib               0x10883a6b3        kfun:androidx.compose.animation#AnimatedEnterExitImpl(androidx.compose.animation.core.Transition<0:0>;kotlin.Function1<0:0,kotlin.Boolean>;androidx.compose.ui.Modifier;androidx.compose.animation.EnterTransition;androidx.compose.animation.ExitTransition;kotlin.Function2<androidx.compose.animation.EnterExitState,androidx.compose.animation.EnterExitState,kotlin.Boolean>;androidx.compose.animation.OnLookaheadMeasured?;kotlin.Function3<androidx.compose.animation.AnimatedVisibilityScope,androidx.compose.runtime. + 11311 
                    at 12  Shimstack.debug.dylib               0x10883e7e3        kfun:androidx.compose.animation.AnimatedEnterExitImpl,lambda,{','}24#internal + 551 
                    at 13  Shimstack.debug.dylib               0x10883f7b7        kfun:androidx.compose.animation.,AnimatedEnterExitImpl,lambda,{','}24,FUNCTION_REFERENCE,{','}20.invoke#internal + 171 
                    at 14  Shimstack.debug.dylib               0x10883fa63        kfun:androidx.compose.animation.,AnimatedEnterExitImpl,lambda,{','}24,FUNCTION_REFERENCE,{','}20.,{','}<bridge-DNNNU>invoke(androidx.compose.runtime.Composer?;kotlin.Int){}#internal + 159 
                    at 15  Shimstack.debug.dylib               0x108223c0b        kfun:kotlin.Function2#invoke(1:0;1:1){}1:2-trampoline + 115 
                    at 16  Shimstack.debug.dylib               0x10830e007        kfun:androidx.compose.runtime.RecomposeScopeImpl#compose(androidx.compose.runtime.Composer){} + 767 
                    at 17  Shimstack.debug.dylib               0x1082d877b        kfun:androidx.compose.runtime.ComposerImpl.recomposeToGroupEnd#internal + 1435 
                    at 18  Shimstack.debug.dylib               0x1082dba9f        kfun:androidx.compose.runtime.ComposerImpl#skipToGroupEnd(){} + 371 
                    at 19  Shimstack.debug.dylib               0x1084202af        kfun:androidx.compose.runtime.Composer#skipToGroupEnd(){}-trampoline + 91 
                    at 20  Shimstack.debug.dylib               0x108837947        kfun:androidx.compose.animation#AnimatedVisibilityImpl(androidx.compose.animation.core.Transition<0:0>;kotlin.Function1<0:0,kotlin.Boolean>;androidx.compose.ui.Modifier;androidx.compose.animation.EnterTransition;androidx.compose.animation.ExitTransition;kotlin.Function3<androidx.compose.animation.AnimatedVisibilityScope,androidx.compose.runtime.Composer,kotlin.Int,kotlin.Unit>;androidx.compose.runtime.Composer?;kotlin.Int){0§<kotlin.Any?>} + 2499 
                    at 21  Shimstack.debug.dylib               0x10883594b        kfun:androidx.compose.animation#AnimatedVisibility__at__androidx.compose.foundation.layout.ColumnScope(kotlin.Boolean;androidx.compose.ui.Modifier?;androidx.compose.animation.EnterTransition?;androidx.compose.animation.ExitTransition?;kotlin.String?;kotlin.Function3<androidx.compose.animation.AnimatedVisibilityScope,androidx.compose.runtime.Composer,kotlin.Int,kotlin.Unit>;androidx.compose.runtime.Composer?;kotlin.Int;kotlin.Int){} + 3079 
                    at 22  Shimstack.debug.dylib               0x107755237        kfun:com.alpenraum.shimstack.ComposableSingletons,AppKt.<init>,lambda,{','}7#internal + 6923 
                    at 23  Shimstack.debug.dylib               0x107755b4b        kfun:com.alpenraum.shimstack.ComposableSingletons,AppKt.,{','}<init>,lambda,{','}7,FUNCTION_REFERENCE,{','}3.invoke#internal + 95 
                    at 24  Shimstack.debug.dylib               0x107755bff        kfun:com.alpenraum.shimstack.ComposableSingletons,AppKt.,{','}<init>,lambda,{','}7,FUNCTION_REFERENCE,{','}3.,{','}<bridge-DNNNU>invoke(androidx.compose.runtime.Composer;kotlin.Int){}#internal + 159 
                    at 25  Shimstack.debug.dylib               0x108223c0b        kfun:kotlin.Function2#invoke(1:0;1:1){}1:2-trampoline + 115 
                    at 26  Shimstack.debug.dylib               0x1084081df        kfun:androidx.compose.runtime.internal.ComposableLambdaImpl#invoke(androidx.compose.runtime.Composer;kotlin.Int){}kotlin.Any? + 603 
                    at 27  Shimstack.debug.dylib               0x10840ed3f        kfun:androidx.compose.runtime.internal.ComposableLambdaImpl.invoke,invoke#internal + 171 
                    at 28  Shimstack.debug.dylib               0x108411f8b        kfun:androidx.compose.runtime.internal.ComposableLambdaImpl.,invoke,FUNCTION_REFERENCE,{','}0.invoke#internal + 115 
                    at 29  Shimstack.debug.dylib               0x108412143        kfun:androidx.compose.runtime.internal.ComposableLambdaImpl.,invoke,FUNCTION_REFERENCE,{','}0.,{','}<bridge-DNNNU>invoke(androidx.compose.runtime.Composer;kotlin.Int){}#internal + 159 
                    at 30  Shimstack.debug.dylib               0x108223c0b        kfun:kotlin.Function2#invoke(1:0;1:1){}1:2-trampoline + 115 
                    at 31  Shimstack.debug.dylib               0x10830e007        kfun:androidx.compose.runtime.RecomposeScopeImpl#compose(androidx.compose.runtime.Composer){} + 767 
                    at 32  Shimstack.debug.dylib               0x1082d877b        kfun:androidx.compose.runtime.ComposerImpl.recomposeToGroupEnd#internal + 1435 
                    at 33  Shimstack.debug.dylib               0x1082db267        kfun:androidx.compose.runtime.ComposerImpl#skipCurrentGroup(){} + 1955 
                    at 34  Shimstack.debug.dylib               0x1082e0623        kfun:androidx.compose.runtime.ComposerImpl.doCompose#internal + 2959 
                    at 35  Shimstack.debug.dylib               0x1082dfa1f        kfun:androidx.compose.runtime.ComposerImpl#recompose(androidx.compose.runtime.collection.ScopeMap<androidx.compose.runtime.RecomposeScopeImpl,kotlin.Any>){}kotlin.Boolean + 487 
                    at 36  Shimstack.debug.dylib               0x1082f4e9f        kfun:androidx.compose.runtime.CompositionImpl#recompose(){}kotlin.Boolean + 919 
                    at 37  Shimstack.debug.dylib               0x108422aff        kfun:androidx.compose.runtime.ControlledComposition#recompose(){}kotlin.Boolean-trampoline + 91 
                    at 38  Shimstack.debug.dylib               0x108318feb        kfun:androidx.compose.runtime.Recomposer.performRecompose#internal + 1451 
                    at 39  Shimstack.debug.dylib               0x108320663        kfun:androidx.compose.runtime.Recomposer.runRecomposeAndApplyChanges,lambda,{','}4,lambda,{','}3#internal + 2619 
                    at 40  Shimstack.debug.dylib               0x10832647f        kfun:androidx.compose.runtime.Recomposer.,runRecomposeAndApplyChanges,lambda,{','}4,lambda,{','}3,FUNCTION_REFERENCE,{','}13.invoke#internal + 131 
                    at 41  Shimstack.debug.dylib               0x1083266fb        kfun:androidx.compose.runtime.Recomposer.,runRecomposeAndApplyChanges,lambda,{','}4,lambda,{','}3,FUNCTION_REFERENCE,{','}13.,{','}<bridge-DNNU>invoke(kotlin.Long){}#internal + 123 
                    at 42  Shimstack.debug.dylib               0x10821f35b        kfun:kotlin.Function1#invoke(1:0){}1:1-trampoline + 107 
                    at 43  Shimstack.debug.dylib               0x1082c1bc7        kfun:androidx.compose.runtime.BroadcastFrameClock.FrameAwaiter.resume#internal + 439 
                    at 44  Shimstack.debug.dylib               0x1082c248f        kfun:androidx.compose.runtime.BroadcastFrameClock#sendFrame(kotlin.Long){} + 611 
                    at 45  Shimstack.debug.dylib               0x108715257        kfun:androidx.compose.ui.scene.BaseComposeScene#render(androidx.compose.ui.graphics.Canvas;kotlin.Long){} + 1395 
                    at 46  Shimstack.debug.dylib               0x1087ef633        kfun:androidx.compose.ui.scene.ComposeScene#render(androidx.compose.ui.graphics.Canvas;kotlin.Long){}-trampoline + 107 
                    at 47  Shimstack.debug.dylib               0x108772f4b        kfun:androidx.compose.ui.scene.ComposeSceneMediator#render(androidx.compose.ui.graphics.Canvas;kotlin.Long){} + 187 
                    at 48  Shimstack.debug.dylib               0x108767e8b        kfun:androidx.compose.ui.scene.ComposeHostingViewController.<init>,lambda,{','}2,lambda,{','}1#internal + 271 
                    at 49  Shimstack.debug.dylib               0x10876a633        kfun:androidx.compose.ui.scene.ComposeHostingViewController.,{','}<init>,lambda,{','}2,lambda,{','}1,FUNCTION_REFERENCE,{','}10.invoke#internal + 115 
                    at 50  Shimstack.debug.dylib               0x10876a75b        kfun:androidx.compose.ui.scene.ComposeHostingViewController.,{','}<init>,lambda,{','}2,lambda,{','}1,FUNCTION_REFERENCE,{','}10.,{','}<bridge-DNNNU>invoke(org.jetbrains.skia.Canvas;kotlin.Long){}#internal + 159 
                    at 51  Shimstack.debug.dylib               0x108223c0b        kfun:kotlin.Function2#invoke(1:0;1:1){}1:2-trampoline + 115 
                    at 52  Shimstack.debug.dylib               0x1087c675b        kfun:androidx.compose.ui.window.MetalView.<init>,lambda,{','}0#internal + 279 
                    at 53  Shimstack.debug.dylib               0x1087c67f3        kfun:androidx.compose.ui.window.MetalView.,{','}<init>,lambda,{','}0,FUNCTION_REFERENCE,{','}4.invoke#internal + 115 
                    at 54  Shimstack.debug.dylib               0x1087c691b        kfun:androidx.compose.ui.window.MetalView.,{','}<init>,lambda,{','}0,FUNCTION_REFERENCE,{','}4.,{','}<bridge-DNNNU>invoke(org.jetbrains.skia.Canvas;kotlin.Double){}#internal + 159 
                    at 55  Shimstack.debug.dylib               0x108223c0b        kfun:kotlin.Function2#invoke(1:0;1:1){}1:2-trampoline + 115 
                    at 56  Shimstack.debug.dylib               0x1087bc89b        kfun:androidx.compose.ui.window.MetalRedrawer.draw#internal + 4403 
                    at 57  Shimstack.debug.dylib               0x1087bf627        kfun:androidx.compose.ui.window.MetalRedrawer.<init>,lambda,{','}1#internal + 443 
                    at 58  Shimstack.debug.dylib               0x1087c1ee7        kfun:androidx.compose.ui.window.MetalRedrawer.,{','}<init>,lambda,{','}1,FUNCTION_REFERENCE,{','}5.invoke#internal + 71 
                    at 59  Shimstack.debug.dylib               0x1087c1fb7        kfun:androidx.compose.ui.window.MetalRedrawer.,{','}<init>,lambda,{','}1,FUNCTION_REFERENCE,{','}5.,{','}<bridge-DNN>invoke(){}#internal + 71 
                    at 60  Shimstack.debug.dylib               0x108220777        kfun:kotlin.Function0#invoke(){}1:0-trampoline + 99 
                    at 61  Shimstack.debug.dylib               0x1087c2d67        kfun:androidx.compose.ui.window.DisplayLinkProxy.handleDisplayLinkTick#internal + 155 
                    at 62  Shimstack.debug.dylib               0x1087c2e27        kfun:androidx.compose.ui.window.DisplayLinkProxy.,imp:handleDisplayLinkTick#internal + 155 
                    at 63  QuartzCore                          0x18af2f553        _ZN2CA7Display15DisplayLinkItem9dispatch_ERNS_8SignPost8IntervalILNS2_11CAEventCodeE835322056EEE + 43 
                    at 64  QuartzCore                          0x18af2b4eb        _ZN2CA7Display11DisplayLink14dispatch_itemsEyyy + 807 
                    at 65  QuartzCore                          0x18af3227f        _ZN2CA7Display11DisplayLink31dispatch_deferred_display_linksEj + 327 
                    at 66  UIKitCore                           0x18505d28b        _UIUpdateSequenceRun + 75 
                    at 67  UIKitCore                           0x185a0766f        schedulerStepScheduledMainSection + 167 
                    at 68  UIKitCore                           0x185a06aa7        runloopSourceCallback + 79 
                    at 69  CoreFoundation                      0x18041b7c3        __CFRUNLOOP_IS_CALLING_OUT_TO_A_SOURCE0_PERFORM_FUNCTION__ + 23 
                    at 70  CoreFoundation                      0x18041b70b        __CFRunLoopDoSource0 + 171 
                    at 71  CoreFoundation                      0x18041ae6f        __CFRunLoopDoSources0 + 231 
                    at 72  CoreFoundation                      0x1804153b3        __CFRunLoopRun + 787 
                    at 73  CoreFoundation                      0x180414c23        CFRunLoopRunSpecific + 551 
                    at 74  GraphicsServices                    0x19020ab0f        GSEventRunModal + 159 
                    at 75  UIKitCore                           0x185ad82fb        -[UIApplication _run] + 795 
                    at 76  UIKitCore                           0x185adc4f3        UIApplicationMain + 123 
                    at 77  SwiftUI                             0x1d290b41b        ,s7SwiftUI17KitRendererCommon33_ACC2C5639A7D76F611E170E831FCA491LLys5NeverOyXlXpFAESpySpys4Int8VGSgGXEfU_ + 163 
                    at 78  SwiftUI                             0x1d290b143        ,s7SwiftUI6runAppys5NeverOxAA0D0RzlF + 83 
                    at 79  SwiftUI                             0x1d266bef3        ,s7SwiftUI3AppPAAE4mainyyFZ + 147 
                    at 80  Shimstack.debug.dylib               0x10774a677        ,s9Shimstack6iOSAppV5,mainyyFZ + 39 
                    at 81  Shimstack.debug.dylib               0x10774a727        __debug_main_executable_dylib_entry_point + 11 (/Users/finnzimmer/Documents/ShimstackMultiPlatform/iosApp/iosApp/iOSApp.swift:4:8)
                    at 82  dyld                                0x10492940f        0x0 + 4371682319 
                    at 83  ???                                 0x10466a273        0x0 + 4368802419 
                ) kotlin.Exception
                """.trimIndent() to "Greeting"
            )
    }
}