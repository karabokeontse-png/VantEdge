package com.vantedge.app.ui.screens;

import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.TextUnitKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WelcomeScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$WelcomeScreenKt {
    public static final ComposableSingletons$WelcomeScreenKt INSTANCE = new ComposableSingletons$WelcomeScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function3<AnimatedVisibilityScope, Composer, Integer, Unit> f166lambda1 = ComposableLambdaKt.composableLambdaInstance(1905907825, false, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$WelcomeScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
            invoke(animatedVisibilityScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0203  */
        /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final void invoke(androidx.compose.animation.AnimatedVisibilityScope r76, androidx.compose.runtime.Composer r77, int r78) {
            /*
                Method dump skipped, instructions count: 519
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ComposableSingletons$WelcomeScreenKt$lambda1$1.invoke(androidx.compose.animation.AnimatedVisibilityScope, androidx.compose.runtime.Composer, int):void");
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function3<AnimatedVisibilityScope, Composer, Integer, Unit> f167lambda2 = ComposableLambdaKt.composableLambdaInstance(223854298, false, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$WelcomeScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
            invoke(animatedVisibilityScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(AnimatedVisibilityScope AnimatedVisibility, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(AnimatedVisibility, "$this$AnimatedVisibility");
            ComposerKt.sourceInformation($composer, "C120@4116L306:WelcomeScreen.kt#fpoywd");
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(223854298, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$WelcomeScreenKt.lambda-2.<anonymous> (WelcomeScreen.kt:120)");
            }
            long sp = TextUnitKt.getSp(15);
            long Color = ColorKt.Color(4288585374L);
            int m5962getCentere0LSkKk = TextAlign.INSTANCE.m5962getCentere0LSkKk();
            TextKt.m2466Text4IGK_g("VantEdge builds a living career profile\nthat improves with every application.", (Modifier) null, Color, sp, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, TextAlign.m5955boximpl(m5962getCentere0LSkKk), TextUnitKt.getSp(23), 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3462, 6, 129522);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static Function3<AnimatedVisibilityScope, Composer, Integer, Unit> f168lambda3 = ComposableLambdaKt.composableLambdaInstance(1343009703, false, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$WelcomeScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
            invoke(animatedVisibilityScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(AnimatedVisibilityScope AnimatedVisibility, Composer $composer, int $changed) {
            long m3748copywmQWz5c;
            Intrinsics.checkNotNullParameter(AnimatedVisibility, "$this$AnimatedVisibility");
            ComposerKt.sourceInformation($composer, "C151@5128L203:WelcomeScreen.kt#fpoywd");
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1343009703, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$WelcomeScreenKt.lambda-3.<anonymous> (WelcomeScreen.kt:151)");
            }
            long sp = TextUnitKt.getSp(12);
            m3748copywmQWz5c = Color.m3748copywmQWz5c(r16, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r16) : 0.5f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r16) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r16) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(ColorKt.Color(4288585374L)) : 0.0f);
            TextKt.m2466Text4IGK_g("tap anywhere to continue", (Modifier) null, m3748copywmQWz5c, sp, (FontStyle) null, (FontWeight) null, (FontFamily) null, TextUnitKt.getSp(1), (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 12586374, 0, 130930);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function3<AnimatedVisibilityScope, Composer, Integer, Unit> m6532getLambda1$app_debug() {
        return f166lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function3<AnimatedVisibilityScope, Composer, Integer, Unit> m6533getLambda2$app_debug() {
        return f167lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function3<AnimatedVisibilityScope, Composer, Integer, Unit> m6534getLambda3$app_debug() {
        return f168lambda3;
    }
}
