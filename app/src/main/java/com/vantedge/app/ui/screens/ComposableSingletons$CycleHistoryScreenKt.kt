package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ArrowBackKt;
import androidx.compose.material.icons.filled.DeleteKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: CycleHistoryScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$CycleHistoryScreenKt {
    public static final ComposableSingletons$CycleHistoryScreenKt INSTANCE = new ComposableSingletons$CycleHistoryScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f107lambda1 = ComposableLambdaKt.composableLambdaInstance(325999715, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C44@1547L43:CycleHistoryScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(325999715, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt.lambda-1.<anonymous> (CycleHistoryScreen.kt:44)");
            }
            j = CycleHistoryScreenKt.CHOnBg;
            TextKt.m2466Text4IGK_g("Application History", (Modifier) null, j, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 390, 0, 131066);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f108lambda2 = ComposableLambdaKt.composableLambdaInstance(-1621870910, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C47@1712L71:CycleHistoryScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1621870910, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt.lambda-2.<anonymous> (CycleHistoryScreen.kt:47)");
                }
                ImageVector arrowBack = ArrowBackKt.getArrowBack(Icons.INSTANCE.getDefault());
                j = CycleHistoryScreenKt.CHOnBg;
                IconKt.m1938Iconww6aTOc(arrowBack, (String) null, (Modifier) null, j, $composer, 3120, 4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f109lambda3 = ComposableLambdaKt.composableLambdaInstance(-1504834822, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C163@7504L466:CycleHistoryScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1504834822, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt.lambda-3.<anonymous> (CycleHistoryScreen.kt:163)");
            }
            TextKt.m2466Text4IGK_g("Complete", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(6), Dp.m6092constructorimpl(2)), ColorKt.Color(4283215696L), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f110lambda4 = ComposableLambdaKt.composableLambdaInstance(1456249571, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C177@8449L453:CycleHistoryScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1456249571, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt.lambda-4.<anonymous> (CycleHistoryScreen.kt:177)");
            }
            j = CycleHistoryScreenKt.CHAmber;
            TextKt.m2466Text4IGK_g("Ready", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(6), Dp.m6092constructorimpl(2)), j, TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f111lambda5 = ComposableLambdaKt.composableLambdaInstance(91022634, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt$lambda-5$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C199@9657L218:CycleHistoryScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(91022634, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CycleHistoryScreenKt.lambda-5.<anonymous> (CycleHistoryScreen.kt:199)");
                }
                IconKt.m1938Iconww6aTOc(DeleteKt.getDelete(Icons.INSTANCE.getDefault()), "Delete", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), $composer, 3120, 4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6473getLambda1$app_debug() {
        return f107lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6474getLambda2$app_debug() {
        return f108lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6475getLambda3$app_debug() {
        return f109lambda3;
    }

    /* renamed from: getLambda-4$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6476getLambda4$app_debug() {
        return f110lambda4;
    }

    /* renamed from: getLambda-5$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6477getLambda5$app_debug() {
        return f111lambda5;
    }
}
