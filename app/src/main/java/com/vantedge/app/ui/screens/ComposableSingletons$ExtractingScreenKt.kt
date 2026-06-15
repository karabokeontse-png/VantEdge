package com.vantedge.app.ui.screens;

import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.foundation.layout.RowScope;
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
import androidx.compose.ui.unit.TextUnitKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ExtractingScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$ExtractingScreenKt {
    public static final ComposableSingletons$ExtractingScreenKt INSTANCE = new ComposableSingletons$ExtractingScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f131lambda1 = ComposableLambdaKt.composableLambdaInstance(1493332392, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ExtractingScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C61@2212L38:ExtractingScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1493332392, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ExtractingScreenKt.lambda-1.<anonymous> (ExtractingScreen.kt:61)");
            }
            TextKt.m2466Text4IGK_g("Try again", (Modifier) null, Color.INSTANCE.m3776getBlack0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 390, 0, 131066);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function4<AnimatedContentScope, String, Composer, Integer, Unit> f132lambda2 = ComposableLambdaKt.composableLambdaInstance(-1772091643, false, new Function4<AnimatedContentScope, String, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ExtractingScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function4
        public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, String str, Composer composer, Integer num) {
            invoke(animatedContentScope, str, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(AnimatedContentScope AnimatedContent, String text, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(AnimatedContent, "$this$AnimatedContent");
            Intrinsics.checkNotNullParameter(text, "text");
            ComposerKt.sourceInformation($composer, "C125@4568L223:ExtractingScreen.kt#fpoywd");
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1772091643, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ExtractingScreenKt.lambda-2.<anonymous> (ExtractingScreen.kt:125)");
            }
            TextKt.m2466Text4IGK_g(text, (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, TextAlign.m5955boximpl(TextAlign.INSTANCE.m5962getCentere0LSkKk()), 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, (($changed >> 3) & 14) | 3456, 0, 130546);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6497getLambda1$app_debug() {
        return f131lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function4<AnimatedContentScope, String, Composer, Integer, Unit> m6498getLambda2$app_debug() {
        return f132lambda2;
    }
}
