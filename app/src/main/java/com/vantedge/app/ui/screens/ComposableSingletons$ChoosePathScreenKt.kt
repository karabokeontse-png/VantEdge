package com.vantedge.app.ui.screens;

import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
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
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChoosePathScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$ChoosePathScreenKt {
    public static final ComposableSingletons$ChoosePathScreenKt INSTANCE = new ComposableSingletons$ChoosePathScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function3<AnimatedVisibilityScope, Composer, Integer, Unit> f96lambda1 = ComposableLambdaKt.composableLambdaInstance(206680278, false, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
            invoke(animatedVisibilityScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x01d8  */
        /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final void invoke(androidx.compose.animation.AnimatedVisibilityScope r75, androidx.compose.runtime.Composer r76, int r77) {
            /*
                Method dump skipped, instructions count: 476
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt$lambda1$1.invoke(androidx.compose.animation.AnimatedVisibilityScope, androidx.compose.runtime.Composer, int):void");
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function3<AnimatedVisibilityScope, Composer, Integer, Unit> f97lambda2 = ComposableLambdaKt.composableLambdaInstance(-821982147, false, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
            invoke(animatedVisibilityScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(AnimatedVisibilityScope AnimatedVisibility, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(AnimatedVisibility, "$this$AnimatedVisibility");
            ComposerKt.sourceInformation($composer, "C126@4534L270:ChoosePathScreen.kt#fpoywd");
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-821982147, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt.lambda-2.<anonymous> (ChoosePathScreen.kt:126)");
            }
            ChoosePathScreenKt.PathCard("🔗", "Import from LinkedIn", "Coming soon.", false, true, new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt$lambda-2$1.1
                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }
            }, $composer, 224694);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f98lambda3 = ComposableLambdaKt.composableLambdaInstance(854573631, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope TextButton, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(TextButton, "$this$TextButton");
            ComposerKt.sourceInformation($composer, "C140@5008L171:ChoosePathScreen.kt#fpoywd");
            if (($changed & 81) != 16 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(854573631, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ChoosePathScreenKt.lambda-3.<anonymous> (ChoosePathScreen.kt:140)");
                }
                TextKt.m2466Text4IGK_g("Export debug logs", (Modifier) null, ColorKt.Color(4282664004L), TextUnitKt.getSp(12), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3462, 0, 131058);
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
    public final Function3<AnimatedVisibilityScope, Composer, Integer, Unit> m6462getLambda1$app_debug() {
        return f96lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function3<AnimatedVisibilityScope, Composer, Integer, Unit> m6463getLambda2$app_debug() {
        return f97lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6464getLambda3$app_debug() {
        return f98lambda3;
    }
}
