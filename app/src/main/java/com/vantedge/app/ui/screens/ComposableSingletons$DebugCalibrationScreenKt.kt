package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ArrowBackKt;
import androidx.compose.material.icons.filled.ShareKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DebugCalibrationScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$DebugCalibrationScreenKt {
    public static final ComposableSingletons$DebugCalibrationScreenKt INSTANCE = new ComposableSingletons$DebugCalibrationScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f116lambda1 = ComposableLambdaKt.composableLambdaInstance(-1543148171, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C43@1436L47:DebugCalibrationScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1543148171, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt.lambda-1.<anonymous> (DebugCalibrationScreen.kt:43)");
            }
            TextKt.m2466Text4IGK_g("Gate 0 Calibration", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 390, 0, 131066);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f117lambda2 = ComposableLambdaKt.composableLambdaInstance(-2086081226, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C47@1686L78:DebugCalibrationScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-2086081226, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt.lambda-2.<anonymous> (DebugCalibrationScreen.kt:47)");
            }
            IconKt.m1938Iconww6aTOc(ArrowBackKt.getArrowBack(Icons.INSTANCE.getDefault()), "Back", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), $composer, 3120, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f118lambda3 = ComposableLambdaKt.composableLambdaInstance(-952604409, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C51@1914L82:DebugCalibrationScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-952604409, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt.lambda-3.<anonymous> (DebugCalibrationScreen.kt:51)");
            }
            IconKt.m1938Iconww6aTOc(ShareKt.getShare(Icons.INSTANCE.getDefault()), "Share Export", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), $composer, 3120, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f119lambda4 = ComposableLambdaKt.composableLambdaInstance(-366640884, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C74@2699L40:DebugCalibrationScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-366640884, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt.lambda-4.<anonymous> (DebugCalibrationScreen.kt:74)");
            }
            SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(4)), $composer, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f120lambda5 = ComposableLambdaKt.composableLambdaInstance(-754475339, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt$lambda-5$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C80@2936L41:DebugCalibrationScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-754475339, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$DebugCalibrationScreenKt.lambda-5.<anonymous> (DebugCalibrationScreen.kt:80)");
            }
            SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(16)), $composer, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6482getLambda1$app_debug() {
        return f116lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6483getLambda2$app_debug() {
        return f117lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6484getLambda3$app_debug() {
        return f118lambda3;
    }

    /* renamed from: getLambda-4$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6485getLambda4$app_debug() {
        return f119lambda4;
    }

    /* renamed from: getLambda-5$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6486getLambda5$app_debug() {
        return f120lambda5;
    }
}
