package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ArrowBackKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
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
import org.bouncycastle.openssl.PEMParser;

/* compiled from: AdvancedAnalysisScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$AdvancedAnalysisScreenKt {
    public static final ComposableSingletons$AdvancedAnalysisScreenKt INSTANCE = new ComposableSingletons$AdvancedAnalysisScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f80lambda1 = ComposableLambdaKt.composableLambdaInstance(1700714934, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C62@2359L197:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(1700714934, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-1.<anonymous> (AdvancedAnalysisScreen.kt:62)");
                }
                IconKt.m1938Iconww6aTOc(ArrowBackKt.getArrowBack(Icons.INSTANCE.getDefault()), (String) null, (Modifier) null, AppColors.INSTANCE.m6574getOnBackground0d7_KjU(), $composer, 3120, 4);
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
    public static Function3<RowScope, Composer, Integer, Unit> f81lambda2 = ComposableLambdaKt.composableLambdaInstance(2023915367, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C85@3314L137:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) != 16 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(2023915367, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-2.<anonymous> (AdvancedAnalysisScreen.kt:85)");
                }
                TextKt.m2466Text4IGK_g("Generate My CV & Cover Letter", (Modifier) null, 0L, 0L, (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 196614, 0, 131038);
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
    public static Function3<LazyItemScope, Composer, Integer, Unit> f82lambda3 = ComposableLambdaKt.composableLambdaInstance(996879288, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C196@7455L302:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(996879288, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-3.<anonymous> (AdvancedAnalysisScreen.kt:196)");
            }
            TextKt.m2466Text4IGK_g("STRONG MATCH — direct requirements", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(10)), AppColors.INSTANCE.m6576getSuccess0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f83lambda4 = ComposableLambdaKt.composableLambdaInstance(245330135, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C209@7929L303:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(245330135, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-4.<anonymous> (AdvancedAnalysisScreen.kt:209)");
            }
            TextKt.m2466Text4IGK_g("PARTIAL MATCH — transferable skills", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(10)), AppColors.INSTANCE.m6577getWarning0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f84lambda5 = ComposableLambdaKt.composableLambdaInstance(-506219018, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-5$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C222@8403L302:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-506219018, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-5.<anonymous> (AdvancedAnalysisScreen.kt:222)");
            }
            TextKt.m2466Text4IGK_g("LOW MATCH — peripheral to this role", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(10)), AppColors.INSTANCE.m6575getSubtle0d7_KjU(), TextUnitKt.getSp(10), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f85lambda6 = ComposableLambdaKt.composableLambdaInstance(519755208, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-6$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C381@14675L306:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(519755208, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-6.<anonymous> (AdvancedAnalysisScreen.kt:381)");
            }
            TextKt.m2466Text4IGK_g("CRITICAL — address before applying", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(10)), AppColors.INSTANCE.m6572getDestructive0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-7, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f86lambda7 = ComposableLambdaKt.composableLambdaInstance(-231793945, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-7$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C394@15177L298:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-231793945, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-7.<anonymous> (AdvancedAnalysisScreen.kt:394)");
            }
            TextKt.m2466Text4IGK_g("IMPORTANT — worth closing soon", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(10)), AppColors.INSTANCE.m6577getWarning0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-8, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f87lambda8 = ComposableLambdaKt.composableLambdaInstance(-983343098, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-8$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C407@15669L294:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-983343098, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-8.<anonymous> (AdvancedAnalysisScreen.kt:407)");
            }
            TextKt.m2466Text4IGK_g("NICE TO HAVE — not blocking", PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(10)), AppColors.INSTANCE.m6575getSubtle0d7_KjU(), TextUnitKt.getSp(11), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-9, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f88lambda9 = ComposableLambdaKt.composableLambdaInstance(1546034207, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt$lambda-9$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C549@21169L349:AdvancedAnalysisScreen.kt#fpoywd");
            if (($changed & 11) == 2 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1546034207, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$AdvancedAnalysisScreenKt.lambda-9.<anonymous> (AdvancedAnalysisScreen.kt:549)");
            }
            TextKt.m2466Text4IGK_g(PEMParser.TYPE_CERTIFICATE, PaddingKt.m565paddingVpY3zN4(Modifier.INSTANCE, Dp.m6092constructorimpl(5), Dp.m6092constructorimpl(2)), AppColors.INSTANCE.m6569getAnalysis0d7_KjU(), TextUnitKt.getSp(9), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6446getLambda1$app_debug() {
        return f80lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6447getLambda2$app_debug() {
        return f81lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6448getLambda3$app_debug() {
        return f82lambda3;
    }

    /* renamed from: getLambda-4$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6449getLambda4$app_debug() {
        return f83lambda4;
    }

    /* renamed from: getLambda-5$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6450getLambda5$app_debug() {
        return f84lambda5;
    }

    /* renamed from: getLambda-6$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6451getLambda6$app_debug() {
        return f85lambda6;
    }

    /* renamed from: getLambda-7$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6452getLambda7$app_debug() {
        return f86lambda7;
    }

    /* renamed from: getLambda-8$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6453getLambda8$app_debug() {
        return f87lambda8;
    }

    /* renamed from: getLambda-9$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6454getLambda9$app_debug() {
        return f88lambda9;
    }
}
