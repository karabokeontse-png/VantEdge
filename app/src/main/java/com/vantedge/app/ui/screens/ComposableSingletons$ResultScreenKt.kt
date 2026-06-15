package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.ArrowBackKt;
import androidx.compose.material.icons.filled.ContentCopyKt;
import androidx.compose.material.icons.filled.HomeKt;
import androidx.compose.material.icons.filled.PictureAsPdfKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ResultScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$ResultScreenKt {
    public static final ComposableSingletons$ResultScreenKt INSTANCE = new ComposableSingletons$ResultScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f146lambda1 = ComposableLambdaKt.composableLambdaInstance(-54977315, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C189@7433L182:ResultScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-54977315, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-1.<anonymous> (ResultScreen.kt:189)");
                }
                ImageVector arrowBack = ArrowBackKt.getArrowBack(Icons.INSTANCE.getDefault());
                j = ResultScreenKt.ROnBg;
                IconKt.m1938Iconww6aTOc(arrowBack, "Back", (Modifier) null, j, $composer, 3120, 4);
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
    public static Function2<Composer, Integer, Unit> f153lambda2 = ComposableLambdaKt.composableLambdaInstance(-2135756175, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C206@8304L74:ResultScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-2135756175, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-2.<anonymous> (ResultScreen.kt:206)");
                }
                ImageVector contentCopy = ContentCopyKt.getContentCopy(Icons.INSTANCE.getDefault());
                j = ResultScreenKt.RTeal;
                IconKt.m1938Iconww6aTOc(contentCopy, "Copy", (Modifier) null, j, $composer, 3120, 4);
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
    public static Function2<Composer, Integer, Unit> f154lambda3 = ComposableLambdaKt.composableLambdaInstance(4649242, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C212@8642L79:ResultScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(4649242, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-3.<anonymous> (ResultScreen.kt:212)");
                }
                ImageVector pictureAsPdf = PictureAsPdfKt.getPictureAsPdf(Icons.INSTANCE.getDefault());
                j = ResultScreenKt.RTeal;
                IconKt.m1938Iconww6aTOc(pictureAsPdf, "Save PDF", (Modifier) null, j, $composer, 3120, 4);
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
    public static Function2<Composer, Integer, Unit> f155lambda4 = ComposableLambdaKt.composableLambdaInstance(723065644, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            long j;
            ComposerKt.sourceInformation($composer, "C221@9067L67:ResultScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(723065644, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-4.<anonymous> (ResultScreen.kt:221)");
                }
                ImageVector home = HomeKt.getHome(Icons.INSTANCE.getDefault());
                j = ResultScreenKt.RTeal;
                IconKt.m1938Iconww6aTOc(home, "Home", (Modifier) null, j, $composer, 3120, 4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f156lambda5 = ComposableLambdaKt.composableLambdaInstance(588396311, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-5$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C315@14009L235:ResultScreen.kt#fpoywd");
            if (($changed & 81) != 16 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(588396311, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-5.<anonymous> (ResultScreen.kt:315)");
                }
                TextKt.m2466Text4IGK_g("Retry", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f157lambda6 = ComposableLambdaKt.composableLambdaInstance(-1227377871, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-6$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C393@17679L277:ResultScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1227377871, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-6.<anonymous> (ResultScreen.kt:393)");
                }
                TextKt.m2466Text4IGK_g("e.g., Highlight AWS Certification, make profile summary concise...", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3462, 0, 131058);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-7, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f158lambda7 = ComposableLambdaKt.composableLambdaInstance(-560745757, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-7$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C440@20110L69:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-560745757, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-7.<anonymous> (ResultScreen.kt:440)");
            }
            TextKt.m2466Text4IGK_g("Save Analysis", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-8, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f159lambda8 = ComposableLambdaKt.composableLambdaInstance(-1980738294, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-8$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope OutlinedButton, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(OutlinedButton, "$this$OutlinedButton");
            ComposerKt.sourceInformation($composer, "C460@21229L33:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1980738294, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-8.<anonymous> (ResultScreen.kt:460)");
            }
            TextKt.m2466Text4IGK_g("Dismiss", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3078, 0, 131062);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-9, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f160lambda9 = ComposableLambdaKt.composableLambdaInstance(2024379980, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-9$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C471@21842L80:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(2024379980, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-9.<anonymous> (ResultScreen.kt:471)");
            }
            TextKt.m2466Text4IGK_g("Continue to CV Generator", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-10, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f147lambda10 = ComposableLambdaKt.composableLambdaInstance(-748606999, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-10$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope OutlinedButton, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(OutlinedButton, "$this$OutlinedButton");
            ComposerKt.sourceInformation($composer, "C491@22974L33:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-748606999, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-10.<anonymous> (ResultScreen.kt:491)");
            }
            TextKt.m2466Text4IGK_g("Dismiss", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3078, 0, 131062);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-11, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f148lambda11 = ComposableLambdaKt.composableLambdaInstance(-1038456021, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-11$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C510@24003L72:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1038456021, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-11.<anonymous> (ResultScreen.kt:510)");
            }
            TextKt.m2466Text4IGK_g("Save Application", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-12, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f149lambda12 = ComposableLambdaKt.composableLambdaInstance(-1849813564, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-12$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C572@26970L190:ResultScreen.kt#fpoywd");
            if (($changed & 81) != 16 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1849813564, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-12.<anonymous> (ResultScreen.kt:572)");
                }
                TextKt.m2466Text4IGK_g("Improve This Application", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-13, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f150lambda13 = ComposableLambdaKt.composableLambdaInstance(-489694131, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-13$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C685@31939L246:ResultScreen.kt#fpoywd");
            if (($changed & 81) != 16 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-489694131, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-13.<anonymous> (ResultScreen.kt:685)");
                }
                TextKt.m2466Text4IGK_g("Retry Generation", (Modifier) null, 0L, TextUnitKt.getSp(14), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199686, 0, 131030);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-14, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f151lambda14 = ComposableLambdaKt.composableLambdaInstance(338308366, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-14$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C862@40249L41,863@40311L158:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(338308366, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-14.<anonymous> (ResultScreen.kt:862)");
            }
            SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), $composer, 6);
            TextKt.m2466Text4IGK_g("What you're missing", PaddingKt.m568paddingqDBjuR0$default(Modifier.INSTANCE, Dp.m6092constructorimpl(16), 0.0f, 0.0f, Dp.m6092constructorimpl(4), 6, null), Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, FontWeight.INSTANCE.getSemiBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: lambda-15, reason: not valid java name */
    public static Function3<LazyItemScope, Composer, Integer, Unit> f152lambda15 = ComposableLambdaKt.composableLambdaInstance(-335758419, false, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt$lambda-15$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
            invoke(lazyItemScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(LazyItemScope item, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(item, "$this$item");
            ComposerKt.sourceInformation($composer, "C869@40672L41,870@40734L161:ResultScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-335758419, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ResultScreenKt.lambda-15.<anonymous> (ResultScreen.kt:869)");
            }
            SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(16)), $composer, 6);
            TextKt.m2466Text4IGK_g("What's working for you", PaddingKt.m568paddingqDBjuR0$default(Modifier.INSTANCE, Dp.m6092constructorimpl(16), 0.0f, 0.0f, Dp.m6092constructorimpl(4), 6, null), Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, FontWeight.INSTANCE.getSemiBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 200118, 0, 131024);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6512getLambda1$app_debug() {
        return f146lambda1;
    }

    /* renamed from: getLambda-10$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6513getLambda10$app_debug() {
        return f147lambda10;
    }

    /* renamed from: getLambda-11$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6514getLambda11$app_debug() {
        return f148lambda11;
    }

    /* renamed from: getLambda-12$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6515getLambda12$app_debug() {
        return f149lambda12;
    }

    /* renamed from: getLambda-13$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6516getLambda13$app_debug() {
        return f150lambda13;
    }

    /* renamed from: getLambda-14$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6517getLambda14$app_debug() {
        return f151lambda14;
    }

    /* renamed from: getLambda-15$app_debug, reason: not valid java name */
    public final Function3<LazyItemScope, Composer, Integer, Unit> m6518getLambda15$app_debug() {
        return f152lambda15;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6519getLambda2$app_debug() {
        return f153lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6520getLambda3$app_debug() {
        return f154lambda3;
    }

    /* renamed from: getLambda-4$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6521getLambda4$app_debug() {
        return f155lambda4;
    }

    /* renamed from: getLambda-5$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6522getLambda5$app_debug() {
        return f156lambda5;
    }

    /* renamed from: getLambda-6$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6523getLambda6$app_debug() {
        return f157lambda6;
    }

    /* renamed from: getLambda-7$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6524getLambda7$app_debug() {
        return f158lambda7;
    }

    /* renamed from: getLambda-8$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6525getLambda8$app_debug() {
        return f159lambda8;
    }

    /* renamed from: getLambda-9$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6526getLambda9$app_debug() {
        return f160lambda9;
    }
}
