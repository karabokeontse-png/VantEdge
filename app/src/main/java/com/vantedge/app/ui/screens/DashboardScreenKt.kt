package com.vantedge.app.ui.screens;

import android.content.Context;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TopAppBarColors;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.profileinstaller.ProfileVerifier;
import com.vantedge.app.BuildConfig;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;

/* compiled from: DashboardScreen.kt */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001ak\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007¢\u0006\u0002\u0010\u000b\u001a*\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u0014"}, d2 = {"DashboardScreen", "", "cycleViewModel", "Lcom/vantedge/app/data/viewmodel/CycleViewModel;", "onNavigateToHistory", "Lkotlin/Function0;", "onNavigateToEditProfile", "onNavigateToNewApplication", "onNavigateToQuickAnalysis", "onNavigateToQuickGenerate", "onNavigateToDebug", "(Lcom/vantedge/app/data/viewmodel/CycleViewModel;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;II)V", "InsightStat", AnnotatedPrivateKey.LABEL, "", "value", "color", "Landroidx/compose/ui/graphics/Color;", "InsightStat-XO-JAsU", "(Ljava/lang/String;Ljava/lang/String;JLandroidx/compose/runtime/Composer;I)V", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DashboardScreenKt {
    public static final void DashboardScreen(final CycleViewModel cycleViewModel, final Function0<Unit> onNavigateToHistory, final Function0<Unit> onNavigateToEditProfile, final Function0<Unit> onNavigateToNewApplication, final Function0<Unit> onNavigateToQuickAnalysis, final Function0<Unit> onNavigateToQuickGenerate, Function0<Unit> function0, Composer $composer, final int $changed, final int i) {
        final Function0 onNavigateToDebug;
        Intrinsics.checkNotNullParameter(cycleViewModel, "cycleViewModel");
        Intrinsics.checkNotNullParameter(onNavigateToHistory, "onNavigateToHistory");
        Intrinsics.checkNotNullParameter(onNavigateToEditProfile, "onNavigateToEditProfile");
        Intrinsics.checkNotNullParameter(onNavigateToNewApplication, "onNavigateToNewApplication");
        Intrinsics.checkNotNullParameter(onNavigateToQuickAnalysis, "onNavigateToQuickAnalysis");
        Intrinsics.checkNotNullParameter(onNavigateToQuickGenerate, "onNavigateToQuickGenerate");
        Composer $composer2 = $composer.startRestartGroup(313014535);
        ComposerKt.sourceInformation($composer2, "C(DashboardScreen)P(!1,3,2,4,5,6)35@1306L7,50@1782L6274:DashboardScreen.kt#fpoywd");
        if ((i & 64) != 0) {
            onNavigateToDebug = new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.DashboardScreenKt$DashboardScreen$1
                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }
            };
        } else {
            onNavigateToDebug = function0;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(313014535, $changed, -1, "com.vantedge.app.ui.screens.DashboardScreen (DashboardScreen.kt:33)");
        }
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) consume;
        final long teal = ColorKt.Color(4278239141L);
        final long amber = ColorKt.Color(4294948912L);
        final long purple = ColorKt.Color(4286336511L);
        final long green = ColorKt.Color(4278249078L);
        final long blue = ColorKt.Color(4280908287L);
        final long cardDark = ColorKt.Color(4279900718L);
        final long bg = ColorKt.Color(4279045389L);
        final int totalSaved = cycleViewModel.totalSavedApplications();
        final String bestRole = cycleViewModel.bestScoringRole();
        final Integer avgScore = cycleViewModel.averageScore();
        final int improved = cycleViewModel.improvedApplicationsCount();
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -1931321141, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DashboardScreenKt$DashboardScreen$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer $composer3, int $changed2) {
                ComposerKt.sourceInformation($composer3, "C55@1973L36,53@1852L497:DashboardScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1931321141, $changed2, -1, "com.vantedge.app.ui.screens.DashboardScreen.<anonymous> (DashboardScreen.kt:53)");
                    }
                    TopAppBarColors m2625topAppBarColorszjMxDiM = TopAppBarDefaults.INSTANCE.m2625topAppBarColorszjMxDiM(bg, 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30);
                    Function2<Composer, Integer, Unit> m6478getLambda1$app_debug = ComposableSingletons$DashboardScreenKt.INSTANCE.m6478getLambda1$app_debug();
                    final Function0<Unit> function02 = onNavigateToDebug;
                    AppBarKt.TopAppBar(m6478getLambda1$app_debug, null, null, ComposableLambdaKt.composableLambda($composer3, -1145835332, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DashboardScreenKt$DashboardScreen$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                            invoke(rowScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(RowScope TopAppBar, Composer $composer4, int $changed3) {
                            Intrinsics.checkNotNullParameter(TopAppBar, "$this$TopAppBar");
                            ComposerKt.sourceInformation($composer4, "C58@2108L187:DashboardScreen.kt#fpoywd");
                            if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-1145835332, $changed3, -1, "com.vantedge.app.ui.screens.DashboardScreen.<anonymous>.<anonymous> (DashboardScreen.kt:57)");
                                }
                                if (BuildConfig.DEBUG) {
                                    IconButtonKt.IconButton(function02, null, false, null, null, ComposableSingletons$DashboardScreenKt.INSTANCE.m6479getLambda2$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), null, m2625topAppBarColorszjMxDiM, null, $composer3, 3078, 86);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, null, 0, bg, 0L, null, ComposableLambdaKt.composableLambda($composer2, 1488717782, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DashboardScreenKt$DashboardScreen$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:31:0x0290  */
            /* JADX WARN: Removed duplicated region for block: B:34:0x0516  */
            /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:37:0x02f5  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.PaddingValues r96, androidx.compose.runtime.Composer r97, int r98) {
                /*
                    Method dump skipped, instructions count: 1306
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.DashboardScreenKt$DashboardScreen$3.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 806879280, 445);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            final Function0 onNavigateToDebug2 = onNavigateToDebug;
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DashboardScreenKt$DashboardScreen$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i2) {
                    DashboardScreenKt.DashboardScreen(CycleViewModel.this, onNavigateToHistory, onNavigateToEditProfile, onNavigateToNewApplication, onNavigateToQuickAnalysis, onNavigateToQuickGenerate, onNavigateToDebug2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0225  */
    /* renamed from: InsightStat-XO-JAsU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6535InsightStatXOJAsU(final java.lang.String r67, final java.lang.String r68, final long r69, androidx.compose.runtime.Composer r71, final int r72) {
        /*
            Method dump skipped, instructions count: 581
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.DashboardScreenKt.m6535InsightStatXOJAsU(java.lang.String, java.lang.String, long, androidx.compose.runtime.Composer, int):void");
    }
}
