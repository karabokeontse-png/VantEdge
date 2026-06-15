package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ArrowBackKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.SnackbarData;
import androidx.compose.material3.SnackbarKt;
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
import androidx.compose.ui.unit.TextUnitKt;
import com.vantedge.app.ui.theme.AppColors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FinalReviewScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$FinalReviewScreenKt {
    public static final ComposableSingletons$FinalReviewScreenKt INSTANCE = new ComposableSingletons$FinalReviewScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f133lambda1 = ComposableLambdaKt.composableLambdaInstance(996746, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C55@1933L211:FinalReviewScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(996746, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt.lambda-1.<anonymous> (FinalReviewScreen.kt:55)");
                }
                TextKt.m2466Text4IGK_g("Almost there", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), TextUnitKt.getSp(18), (FontStyle) null, FontWeight.INSTANCE.getSemiBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200070, 0, 131026);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f134lambda2 = ComposableLambdaKt.composableLambdaInstance(-560754805, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C64@2274L205:FinalReviewScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-560754805, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt.lambda-2.<anonymous> (FinalReviewScreen.kt:64)");
                }
                IconKt.m1938Iconww6aTOc(ArrowBackKt.getArrowBack(Icons.INSTANCE.getDefault()), "Go back", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), $composer, 3120, 4);
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
    public static Function3<RowScope, Composer, Integer, Unit> f135lambda3 = ComposableLambdaKt.composableLambdaInstance(-1480997835, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C107@3923L167:FinalReviewScreen.kt#fpoywd");
            if (($changed & 81) != 16 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1480997835, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt.lambda-3.<anonymous> (FinalReviewScreen.kt:107)");
                }
                TextKt.m2466Text4IGK_g("Activate VantEdge", (Modifier) null, 0L, TextUnitKt.getSp(15), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static Function3<SnackbarData, Composer, Integer, Unit> f136lambda4 = ComposableLambdaKt.composableLambdaInstance(-1830960995, false, new Function3<SnackbarData, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(SnackbarData snackbarData, Composer composer, Integer num) {
            invoke(snackbarData, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(SnackbarData data, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(data, "data");
            ComposerKt.sourceInformation($composer, "C78@2789L229:FinalReviewScreen.kt#fpoywd");
            int $dirty = $changed;
            if (($changed & 14) == 0) {
                $dirty |= $composer.changed(data) ? 4 : 2;
            }
            int $dirty2 = $dirty;
            if (($dirty2 & 91) != 18 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1830960995, $dirty2, -1, "com.vantedge.app.ui.screens.ComposableSingletons$FinalReviewScreenKt.lambda-4.<anonymous> (FinalReviewScreen.kt:78)");
                }
                SnackbarKt.m2235SnackbarsDKtq54(data, null, false, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), AppColors.INSTANCE.m6572getDestructive0d7_KjU(), Color.INSTANCE.m3787getWhite0d7_KjU(), 0L, 0L, 0L, $composer, ($dirty2 & 14) | 221184, 454);
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
    public final Function2<Composer, Integer, Unit> m6499getLambda1$app_debug() {
        return f133lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6500getLambda2$app_debug() {
        return f134lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6501getLambda3$app_debug() {
        return f135lambda3;
    }

    /* renamed from: getLambda-4$app_debug, reason: not valid java name */
    public final Function3<SnackbarData, Composer, Integer, Unit> m6502getLambda4$app_debug() {
        return f136lambda4;
    }
}
