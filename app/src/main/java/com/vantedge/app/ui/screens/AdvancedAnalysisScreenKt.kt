package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambda;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.profileinstaller.ProfileVerifier;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.CourseRecommendation;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.GapItem;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.model.RelevancyItem;
import com.vantedge.app.ui.theme.AppColors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdvancedAnalysisScreen.kt */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a1\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007¢\u0006\u0002\u0010\u0007\u001a\u001d\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0003¢\u0006\u0002\u0010\r\u001a\u0015\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0003¢\u0006\u0002\u0010\u0011\u001a\"\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a\"\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0016H\u0003ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a\u0015\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0003¢\u0006\u0002\u0010\u0011\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006 ²\u0006\n\u0010!\u001a\u00020\fX\u008a\u008e\u0002"}, d2 = {"AdvancedAnalysisScreen", "", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "onGenerateCv", "Lkotlin/Function0;", "onBack", "(Lcom/vantedge/app/data/model/GenerationCycle;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "CourseRow", "course", "Lcom/vantedge/app/data/model/CourseRecommendation;", "index", "", "(Lcom/vantedge/app/data/model/CourseRecommendation;ILandroidx/compose/runtime/Composer;I)V", "GapAnalysisContent", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "(Lcom/vantedge/app/data/model/CompatibilityRecord;Landroidx/compose/runtime/Composer;I)V", "GapCard", "gap", "Lcom/vantedge/app/data/model/GapItem;", "priorityColor", "Landroidx/compose/ui/graphics/Color;", "GapCard-RPmYEkk", "(Lcom/vantedge/app/data/model/GapItem;JLandroidx/compose/runtime/Composer;I)V", "SkillMatchCard", "item", "Lcom/vantedge/app/data/model/RelevancyItem;", "accentColor", "SkillMatchCard-RPmYEkk", "(Lcom/vantedge/app/data/model/RelevancyItem;JLandroidx/compose/runtime/Composer;I)V", "SkillsMatchContent", "app_debug", "selectedTab"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdvancedAnalysisScreenKt {
    public static final void AdvancedAnalysisScreen(final GenerationCycle cycle, final Function0<Unit> onGenerateCv, final Function0<Unit> onBack, Composer $composer, final int $changed) {
        CompatibilityRecord compatibility;
        Object value$iv;
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        Intrinsics.checkNotNullParameter(onGenerateCv, "onGenerateCv");
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(-2078163305);
        ComposerKt.sourceInformation($composer2, "C(AdvancedAnalysisScreen)P(!1,2)38@1469L30,41@1559L3946:AdvancedAnalysisScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-2078163305, $changed, -1, "com.vantedge.app.ui.screens.AdvancedAnalysisScreen (AdvancedAnalysisScreen.kt:31)");
        }
        CycleState s = cycle.getState();
        if (s instanceof CycleState.FullCycle) {
            compatibility = ((CycleState.FullCycle) s).getCompatibility();
        } else if (s instanceof CycleState.AnalysisOnly) {
            compatibility = ((CycleState.AnalysisOnly) s).getCompatibility();
        } else {
            if (!(s instanceof CycleState.GenerationReady)) {
                throw new NoWhenBranchMatchedException();
            }
            compatibility = ((CycleState.GenerationReady) s).getCompatibility();
        }
        final CompatibilityRecord compatibility2 = compatibility;
        $composer2.startReplaceableGroup(-622299022);
        ComposerKt.sourceInformation($composer2, "CC(remember):AdvancedAnalysisScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(0, null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState selectedTab$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        final List tabs = CollectionsKt.listOf((Object[]) new String[]{"Skills Match", "Gap Analysis"});
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, 1782710867, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$1
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
                ComposerKt.sourceInformation($composer3, "C69@2641L48,44@1647L1056:AdvancedAnalysisScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(1782710867, $changed2, -1, "com.vantedge.app.ui.screens.AdvancedAnalysisScreen.<anonymous> (AdvancedAnalysisScreen.kt:44)");
                    }
                    final GenerationCycle generationCycle = GenerationCycle.this;
                    ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer3, 1793723415, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$1.1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:24:0x01ec  */
                        /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public final void invoke(androidx.compose.runtime.Composer r76, int r77) {
                            /*
                                Method dump skipped, instructions count: 496
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$1.AnonymousClass1.invoke(androidx.compose.runtime.Composer, int):void");
                        }
                    });
                    final Function0<Unit> function0 = onBack;
                    AppBarKt.TopAppBar(composableLambda, null, ComposableLambdaKt.composableLambda($composer3, 1917005081, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$1.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(Composer $composer4, int $changed3) {
                            ComposerKt.sourceInformation($composer4, "C61@2304L274:AdvancedAnalysisScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(1917005081, $changed3, -1, "com.vantedge.app.ui.screens.AdvancedAnalysisScreen.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:61)");
                                }
                                IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6446getLambda1$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), null, null, TopAppBarDefaults.INSTANCE.m2625topAppBarColorszjMxDiM(AppColors.INSTANCE.m6571getCard0d7_KjU(), 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30), null, $composer3, 390, 90);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), ComposableLambdaKt.composableLambda($composer2, 1121121394, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$2
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
                ComposerKt.sourceInformation($composer3, "C73@2749L734:AdvancedAnalysisScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(1121121394, $changed2, -1, "com.vantedge.app.ui.screens.AdvancedAnalysisScreen.<anonymous> (AdvancedAnalysisScreen.kt:73)");
                    }
                    long m6571getCard0d7_KjU = AppColors.INSTANCE.m6571getCard0d7_KjU();
                    float m6092constructorimpl = Dp.m6092constructorimpl(4);
                    final Function0<Unit> function0 = onGenerateCv;
                    SurfaceKt.m2318SurfaceT9BRK9s(null, null, m6571getCard0d7_KjU, 0L, m6092constructorimpl, 0.0f, null, ComposableLambdaKt.composableLambda($composer3, 996376439, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(Composer $composer4, int $changed3) {
                            ComposerKt.sourceInformation($composer4, "C80@3124L149,74@2822L647:AdvancedAnalysisScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(996376439, $changed3, -1, "com.vantedge.app.ui.screens.AdvancedAnalysisScreen.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:74)");
                                }
                                ButtonKt.Button(function0, PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(12)), false, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), ButtonDefaults.INSTANCE.m1607buttonColorsro_MJ88(AppColors.INSTANCE.m6573getGeneration0d7_KjU(), Color.INSTANCE.m3776getBlack0d7_KjU(), 0L, 0L, $composer4, (ButtonDefaults.$stable << 12) | 54, 12), null, null, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6447getLambda2$app_debug(), $composer4, 805306416, 484);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), $composer3, 12607872, 107);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, 0, AppColors.INSTANCE.m6570getBackground0d7_KjU(), 0L, null, ComposableLambdaKt.composableLambda($composer2, -149750552, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:102:0x06c3  */
            /* JADX WARN: Removed duplicated region for block: B:105:0x072a  */
            /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:108:0x06cf  */
            /* JADX WARN: Removed duplicated region for block: B:109:0x06e3  */
            /* JADX WARN: Removed duplicated region for block: B:111:0x065e A[ADDED_TO_REGION] */
            /* JADX WARN: Removed duplicated region for block: B:112:0x0615  */
            /* JADX WARN: Removed duplicated region for block: B:115:0x03a6  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x01c4  */
            /* JADX WARN: Removed duplicated region for block: B:52:0x0394  */
            /* JADX WARN: Removed duplicated region for block: B:55:0x03a0  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x046b  */
            /* JADX WARN: Removed duplicated region for block: B:77:0x0505  */
            /* JADX WARN: Removed duplicated region for block: B:79:0x0510  */
            /* JADX WARN: Removed duplicated region for block: B:82:0x0517  */
            /* JADX WARN: Removed duplicated region for block: B:84:0x050a  */
            /* JADX WARN: Removed duplicated region for block: B:91:0x0603  */
            /* JADX WARN: Removed duplicated region for block: B:94:0x060f  */
            /* JADX WARN: Removed duplicated region for block: B:97:0x0648  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.PaddingValues r84, androidx.compose.runtime.Composer r85, int r86) {
                /*
                    Method dump skipped, instructions count: 1846
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$3.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 806879664, 441);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$AdvancedAnalysisScreen$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdvancedAnalysisScreenKt.AdvancedAnalysisScreen(GenerationCycle.this, onGenerateCv, onBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int AdvancedAnalysisScreen$lambda$1(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void AdvancedAnalysisScreen$lambda$2(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void SkillsMatchContent(final CompatibilityRecord compatibility, Composer $composer, final int $changed) {
        Composer $composer2 = $composer.startRestartGroup(-987893410);
        ComposerKt.sourceInformation($composer2, "C(SkillsMatchContent)155@5861L2943:AdvancedAnalysisScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-987893410, $changed, -1, "com.vantedge.app.ui.screens.SkillsMatchContent (AdvancedAnalysisScreen.kt:150)");
        }
        Iterable $this$filter$iv = compatibility.getRelevancyItems();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            RelevancyItem it = (RelevancyItem) element$iv$iv;
            if (Intrinsics.areEqual(it.getRelevancyGroup(), "HIGH")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        final List highItems = (List) destination$iv$iv;
        Iterable $this$filter$iv2 = compatibility.getRelevancyItems();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            RelevancyItem it2 = (RelevancyItem) element$iv$iv2;
            if (Intrinsics.areEqual(it2.getRelevancyGroup(), "MEDIUM")) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        final List mediumItems = (List) destination$iv$iv2;
        Iterable $this$filter$iv3 = compatibility.getRelevancyItems();
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv3) {
            RelevancyItem it3 = (RelevancyItem) element$iv$iv3;
            if (Intrinsics.areEqual(it3.getRelevancyGroup(), "LOW")) {
                destination$iv$iv3.add(element$iv$iv3);
            }
        }
        final List lowItems = (List) destination$iv$iv3;
        LazyDslKt.LazyColumn(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, PaddingKt.m561PaddingValuesa9UjIt4$default(0.0f, 0.0f, 0.0f, Dp.m6092constructorimpl(32), 7, null), false, null, null, null, false, new Function1<LazyListScope, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LazyListScope lazyListScope) {
                invoke2(lazyListScope);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LazyListScope LazyColumn) {
                Intrinsics.checkNotNullParameter(LazyColumn, "$this$LazyColumn");
                if (CompatibilityRecord.this.getRelevancyItems().isEmpty()) {
                    final CompatibilityRecord compatibilityRecord = CompatibilityRecord.this;
                    LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(-815941041, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1.1
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
                            invoke(lazyItemScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope item, Composer $composer3, int $changed2) {
                            Pair pair;
                            long m3748copywmQWz5c;
                            Intrinsics.checkNotNullParameter(item, "$this$item");
                            ComposerKt.sourceInformation($composer3, "C171@6555L113,167@6386L940:AdvancedAnalysisScreen.kt#fpoywd");
                            if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-815941041, $changed2, -1, "com.vantedge.app.ui.screens.SkillsMatchContent.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:161)");
                                }
                                if (CompatibilityRecord.this.getScore() >= 80) {
                                    pair = TuplesKt.to("⚡", "You exceed what this role requires — every skill you have is relevant.");
                                } else {
                                    pair = TuplesKt.to("✓", "You meet the requirements for this role.");
                                }
                                final String icon = (String) pair.component1();
                                final String message = (String) pair.component2();
                                Modifier m564padding3ABfNKs = PaddingKt.m564padding3ABfNKs(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16));
                                CardDefaults cardDefaults = CardDefaults.INSTANCE;
                                m3748copywmQWz5c = Color.m3748copywmQWz5c(r17, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r17) : 0.08f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r17) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r17) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(AppColors.INSTANCE.m6569getAnalysis0d7_KjU()) : 0.0f);
                                CardKt.Card(m564padding3ABfNKs, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), cardDefaults.m1628cardColorsro_MJ88(m3748copywmQWz5c, 0L, 0L, 0L, $composer3, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer3, 1432432669, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt.SkillsMatchContent.1.1.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                        invoke(columnScope, composer, num.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(ColumnScope Card, Composer $composer4, int $changed3) {
                                        Intrinsics.checkNotNullParameter(Card, "$this$Card");
                                        ComposerKt.sourceInformation($composer4, "C176@6764L544:AdvancedAnalysisScreen.kt#fpoywd");
                                        if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(1432432669, $changed3, -1, "com.vantedge.app.ui.screens.SkillsMatchContent.<anonymous>.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:176)");
                                            }
                                            Modifier modifier$iv = PaddingKt.m564padding3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(16));
                                            Arrangement.Horizontal horizontalArrangement$iv = Arrangement.INSTANCE.m473spacedBy0680j_4(Dp.m6092constructorimpl(12));
                                            Alignment.Vertical verticalAlignment$iv = Alignment.INSTANCE.getTop();
                                            String str = icon;
                                            String str2 = message;
                                            $composer4.startReplaceableGroup(693286680);
                                            ComposerKt.sourceInformation($composer4, "CC(Row)P(2,1,3)90@4553L58,91@4616L130:Row.kt#2w3rfo");
                                            MeasurePolicy measurePolicy$iv = RowKt.rowMeasurePolicy(horizontalArrangement$iv, verticalAlignment$iv, $composer4, ((438 >> 3) & 14) | ((438 >> 3) & 112));
                                            int $changed$iv$iv = (438 << 3) & 112;
                                            $composer4.startReplaceableGroup(-1323940314);
                                            ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                                            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                                            CompositionLocalMap localMap$iv$iv = $composer4.getCurrentCompositionLocalMap();
                                            Function0 factory$iv$iv$iv = ComposeUiNode.INSTANCE.getConstructor();
                                            Function3 skippableUpdate$iv$iv$iv = LayoutKt.modifierMaterializerOf(modifier$iv);
                                            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                                            if (!($composer4.getApplier() instanceof Applier)) {
                                                ComposablesKt.invalidApplier();
                                            }
                                            $composer4.startReusableNode();
                                            if ($composer4.getInserting()) {
                                                $composer4.createNode(factory$iv$iv$iv);
                                            } else {
                                                $composer4.useNode();
                                            }
                                            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m3280constructorimpl($composer4);
                                            Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                                            Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                                            Function2 block$iv$iv$iv = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                                            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                                                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                                                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), block$iv$iv$iv);
                                            }
                                            skippableUpdate$iv$iv$iv.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                                            $composer4.startReplaceableGroup(2058660585);
                                            int i = ($changed$iv$iv$iv >> 9) & 14;
                                            ComposerKt.sourceInformationMarkerStart($composer4, -326681643, "C92@4661L9:Row.kt#2w3rfo");
                                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                            int i2 = ((438 >> 6) & 112) | 6;
                                            ComposerKt.sourceInformationMarkerStart($composer4, 1351160689, "C181@7012L28,182@7065L221:AdvancedAnalysisScreen.kt#fpoywd");
                                            TextKt.m2466Text4IGK_g(str, (Modifier) null, 0L, TextUnitKt.getSp(20), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3072, 0, 131062);
                                            TextKt.m2466Text4IGK_g(str2, (Modifier) null, AppColors.INSTANCE.m6574getOnBackground0d7_KjU(), TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, TextUnitKt.getSp(21), 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3456, 6, 130034);
                                            ComposerKt.sourceInformationMarkerEnd($composer4);
                                            ComposerKt.sourceInformationMarkerEnd($composer4);
                                            $composer4.endReplaceableGroup();
                                            $composer4.endNode();
                                            $composer4.endReplaceableGroup();
                                            $composer4.endReplaceableGroup();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                                return;
                                            }
                                            return;
                                        }
                                        $composer4.skipToGroupEnd();
                                    }
                                }), $composer3, 196614, 24);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer3.skipToGroupEnd();
                        }
                    }), 3, null);
                    return;
                }
                if (!highItems.isEmpty()) {
                    LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6448getLambda3$app_debug(), 3, null);
                    final List items$iv = highItems;
                    final Function1 contentType$iv = new Function1() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((RelevancyItem) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(RelevancyItem relevancyItem) {
                            return null;
                        }
                    };
                    LazyColumn.items(items$iv.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return Function1.this.invoke(items$iv.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it4, Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer3.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer3.changed(it4) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            RelevancyItem it5 = (RelevancyItem) items$iv.get(it4);
                            ComposerKt.sourceInformationMarkerStart($composer3, 642624710, "C*204@7803L37:AdvancedAnalysisScreen.kt#fpoywd");
                            AdvancedAnalysisScreenKt.m6435SkillMatchCardRPmYEkk(it5, AppColors.INSTANCE.m6576getSuccess0d7_KjU(), $composer3, ((($dirty & 14) >> 3) & 14) | 48);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
                List items$iv2 = mediumItems;
                if (!items$iv2.isEmpty()) {
                    LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6449getLambda4$app_debug(), 3, null);
                    final List items$iv3 = mediumItems;
                    final Function1 contentType$iv2 = new Function1() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$5
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((RelevancyItem) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(RelevancyItem relevancyItem) {
                            return null;
                        }
                    };
                    LazyColumn.items(items$iv3.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return Function1.this.invoke(items$iv3.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$8
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it4, Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer3.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer3.changed(it4) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            RelevancyItem it5 = (RelevancyItem) items$iv3.get(it4);
                            ComposerKt.sourceInformationMarkerStart($composer3, 642625187, "C*217@8280L37:AdvancedAnalysisScreen.kt#fpoywd");
                            AdvancedAnalysisScreenKt.m6435SkillMatchCardRPmYEkk(it5, AppColors.INSTANCE.m6577getWarning0d7_KjU(), $composer3, ((($dirty & 14) >> 3) & 14) | 48);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
                List items$iv4 = lowItems;
                if (!items$iv4.isEmpty()) {
                    LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6450getLambda5$app_debug(), 3, null);
                    final List items$iv5 = lowItems;
                    final Function1 contentType$iv3 = new Function1() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$9
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((RelevancyItem) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(RelevancyItem relevancyItem) {
                            return null;
                        }
                    };
                    LazyColumn.items(items$iv5.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$11
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return Function1.this.invoke(items$iv5.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$1$invoke$$inlined$items$default$12
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it4, Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer3.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer3.changed(it4) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            RelevancyItem it5 = (RelevancyItem) items$iv5.get(it4);
                            ComposerKt.sourceInformationMarkerStart($composer3, 642625657, "C*230@8750L36:AdvancedAnalysisScreen.kt#fpoywd");
                            AdvancedAnalysisScreenKt.m6435SkillMatchCardRPmYEkk(it5, AppColors.INSTANCE.m6575getSubtle0d7_KjU(), $composer3, ((($dirty & 14) >> 3) & 14) | 48);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
            }
        }, $composer2, 390, 250);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillsMatchContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdvancedAnalysisScreenKt.SkillsMatchContent(CompatibilityRecord.this, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: SkillMatchCard-RPmYEkk, reason: not valid java name */
    public static final void m6435SkillMatchCardRPmYEkk(final RelevancyItem item, final long accentColor, Composer $composer, final int $changed) {
        Composer $composer2;
        Composer $composer3 = $composer.startRestartGroup(866144002);
        ComposerKt.sourceInformation($composer3, "C(SkillMatchCard)P(1,0:c#ui.graphics.Color)241@9045L43,237@8894L2191:AdvancedAnalysisScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(item) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changed(accentColor) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) == 18 && $composer3.getSkipping()) {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(866144002, $dirty2, -1, "com.vantedge.app.ui.screens.SkillMatchCard (AdvancedAnalysisScreen.kt:236)");
            }
            $composer2 = $composer3;
            CardKt.Card(PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(3)), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(AppColors.INSTANCE.m6571getCard0d7_KjU(), 0L, 0L, 0L, $composer3, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer2, -1569518128, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillMatchCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:35:0x0387  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x0393  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x0545  */
                /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:51:0x0399  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r106, androidx.compose.runtime.Composer r107, int r108) {
                    /*
                        Method dump skipped, instructions count: 1353
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillMatchCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, 196614, 24);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$SkillMatchCard$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdvancedAnalysisScreenKt.m6435SkillMatchCardRPmYEkk(RelevancyItem.this, accentColor, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void GapAnalysisContent(final CompatibilityRecord compatibility, Composer $composer, final int $changed) {
        Composer $composer2 = $composer.startRestartGroup(-713468337);
        ComposerKt.sourceInformation($composer2, "C(GapAnalysisContent)304@11426L4657:AdvancedAnalysisScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-713468337, $changed, -1, "com.vantedge.app.ui.screens.GapAnalysisContent (AdvancedAnalysisScreen.kt:299)");
        }
        Iterable $this$filter$iv = compatibility.getGaps();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GapItem it = (GapItem) element$iv$iv;
            if (Intrinsics.areEqual(it.getImportance(), "CRITICAL")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        final List criticalGaps = (List) destination$iv$iv;
        Iterable $this$filter$iv2 = compatibility.getGaps();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            GapItem it2 = (GapItem) element$iv$iv2;
            if (Intrinsics.areEqual(it2.getImportance(), "IMPORTANT")) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        final List importantGaps = (List) destination$iv$iv2;
        Iterable $this$filter$iv3 = compatibility.getGaps();
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv3) {
            GapItem it3 = (GapItem) element$iv$iv3;
            if (Intrinsics.areEqual(it3.getImportance(), "NICE_TO_HAVE")) {
                destination$iv$iv3.add(element$iv$iv3);
            }
        }
        final List niceToHaveGaps = (List) destination$iv$iv3;
        LazyDslKt.LazyColumn(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, PaddingKt.m561PaddingValuesa9UjIt4$default(0.0f, 0.0f, 0.0f, Dp.m6092constructorimpl(32), 7, null), false, null, null, null, false, new Function1<LazyListScope, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LazyListScope lazyListScope) {
                invoke2(lazyListScope);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LazyListScope LazyColumn) {
                Intrinsics.checkNotNullParameter(LazyColumn, "$this$LazyColumn");
                if (CompatibilityRecord.this.getGaps().isEmpty()) {
                    final CompatibilityRecord compatibilityRecord = CompatibilityRecord.this;
                    LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(-541515968, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1.1
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
                            invoke(lazyItemScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope item, Composer $composer3, int $changed2) {
                            Triple triple;
                            long m3748copywmQWz5c;
                            Intrinsics.checkNotNullParameter(item, "$this$item");
                            ComposerKt.sourceInformation($composer3, "C326@12432L112,322@12263L1341:AdvancedAnalysisScreen.kt#fpoywd");
                            if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-541515968, $changed2, -1, "com.vantedge.app.ui.screens.GapAnalysisContent.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:310)");
                                }
                                if (CompatibilityRecord.this.getScore() >= 80) {
                                    triple = new Triple("⚡", "You're overqualified for this role", "This role doesn't require anything you don't already have. Your skills exceed what's listed in the job description.");
                                } else {
                                    triple = new Triple("✓", "No gaps to address", "You meet all the listed requirements for this role. Nothing critical is missing from your profile.");
                                }
                                final String icon = (String) triple.component1();
                                final String title = (String) triple.component2();
                                final String message = (String) triple.component3();
                                Modifier m564padding3ABfNKs = PaddingKt.m564padding3ABfNKs(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16));
                                CardDefaults cardDefaults = CardDefaults.INSTANCE;
                                m3748copywmQWz5c = Color.m3748copywmQWz5c(r17, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r17) : 0.08f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r17) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r17) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(AppColors.INSTANCE.m6576getSuccess0d7_KjU()) : 0.0f);
                                CardKt.Card(m564padding3ABfNKs, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), cardDefaults.m1628cardColorsro_MJ88(m3748copywmQWz5c, 0L, 0L, 0L, $composer3, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer3, 1706857742, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt.GapAnalysisContent.1.1.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                        invoke(columnScope, composer, num.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:35:0x0386  */
                                    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct add '--show-bad-code' argument
                                    */
                                    public final void invoke(androidx.compose.foundation.layout.ColumnScope r96, androidx.compose.runtime.Composer r97, int r98) {
                                        /*
                                            Method dump skipped, instructions count: 906
                                            To view this dump add '--comments-level debug' option
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1.AnonymousClass1.C00881.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                                    }
                                }), $composer3, 196614, 24);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer3.skipToGroupEnd();
                        }
                    }), 3, null);
                    return;
                }
                if (CompatibilityRecord.this.getCriticalGapCount() > 0) {
                    final CompatibilityRecord compatibilityRecord2 = CompatibilityRecord.this;
                    LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(1271304361, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1.2
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num) {
                            invoke(lazyItemScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope item, Composer $composer3, int $changed2) {
                            long m3748copywmQWz5c;
                            Intrinsics.checkNotNullParameter(item, "$this$item");
                            ComposerKt.sourceInformation($composer3, "C363@13944L115,359@13745L828:AdvancedAnalysisScreen.kt#fpoywd");
                            if (($changed2 & 81) == 16 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(1271304361, $changed2, -1, "com.vantedge.app.ui.screens.GapAnalysisContent.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:359)");
                            }
                            Modifier m565paddingVpY3zN4 = PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(8));
                            CardDefaults cardDefaults = CardDefaults.INSTANCE;
                            m3748copywmQWz5c = Color.m3748copywmQWz5c(r2, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r2) : 0.1f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r2) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r2) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(AppColors.INSTANCE.m6572getDestructive0d7_KjU()) : 0.0f);
                            CardColors m1628cardColorsro_MJ88 = cardDefaults.m1628cardColorsro_MJ88(m3748copywmQWz5c, 0L, 0L, 0L, $composer3, (CardDefaults.$stable << 12) | 6, 14);
                            RoundedCornerShape m834RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8));
                            final CompatibilityRecord compatibilityRecord3 = CompatibilityRecord.this;
                            CardKt.Card(m565paddingVpY3zN4, m834RoundedCornerShape0680j_4, m1628cardColorsro_MJ88, null, null, ComposableLambdaKt.composableLambda($composer3, 1589889783, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt.GapAnalysisContent.1.2.1
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                    invoke(columnScope, composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(ColumnScope Card, Composer $composer4, int $changed3) {
                                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                                    ComposerKt.sourceInformation($composer4, "C368@14154L401:AdvancedAnalysisScreen.kt#fpoywd");
                                    if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(1589889783, $changed3, -1, "com.vantedge.app.ui.screens.GapAnalysisContent.<anonymous>.<anonymous>.<anonymous> (AdvancedAnalysisScreen.kt:368)");
                                        }
                                        TextKt.m2466Text4IGK_g("You have " + CompatibilityRecord.this.getCriticalGapCount() + " critical gap" + (CompatibilityRecord.this.getCriticalGapCount() > 1 ? OperatorName.CLOSE_AND_STROKE : "") + " to address before applying", PaddingKt.m564padding3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), AppColors.INSTANCE.m6572getDestructive0d7_KjU(), TextUnitKt.getSp(12), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 200112, 0, 131024);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                            return;
                                        }
                                        return;
                                    }
                                    $composer4.skipToGroupEnd();
                                }
                            }), $composer3, 196614, 24);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }), 3, null);
                }
                if (!criticalGaps.isEmpty()) {
                    LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6451getLambda6$app_debug(), 3, null);
                    final List items$iv = criticalGaps;
                    final Function1 contentType$iv = new Function1() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$1
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((GapItem) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(GapItem gapItem) {
                            return null;
                        }
                    };
                    LazyColumn.items(items$iv.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return Function1.this.invoke(items$iv.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it4, Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer3.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer3.changed(it4) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            int i = $dirty & 14;
                            GapItem it5 = (GapItem) items$iv.get(it4);
                            ComposerKt.sourceInformationMarkerStart($composer3, -985662928, "C*389@15030L56:AdvancedAnalysisScreen.kt#fpoywd");
                            AdvancedAnalysisScreenKt.m6434GapCardRPmYEkk(it5, AppColors.INSTANCE.m6572getDestructive0d7_KjU(), $composer3, 56);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
                List items$iv2 = importantGaps;
                if (!items$iv2.isEmpty()) {
                    LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6452getLambda7$app_debug(), 3, null);
                    final List items$iv3 = importantGaps;
                    final Function1 contentType$iv2 = new Function1() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$5
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((GapItem) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(GapItem gapItem) {
                            return null;
                        }
                    };
                    LazyColumn.items(items$iv3.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$7
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return Function1.this.invoke(items$iv3.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$8
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it4, Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer3.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer3.changed(it4) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            int i = $dirty & 14;
                            GapItem it5 = (GapItem) items$iv3.get(it4);
                            ComposerKt.sourceInformationMarkerStart($composer3, -985662433, "C*402@15525L52:AdvancedAnalysisScreen.kt#fpoywd");
                            AdvancedAnalysisScreenKt.m6434GapCardRPmYEkk(it5, AppColors.INSTANCE.m6577getWarning0d7_KjU(), $composer3, 56);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
                List items$iv4 = niceToHaveGaps;
                if (!items$iv4.isEmpty()) {
                    LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$AdvancedAnalysisScreenKt.INSTANCE.m6453getLambda8$app_debug(), 3, null);
                    final List items$iv5 = niceToHaveGaps;
                    final Function1 contentType$iv3 = new Function1() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$9
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                            return invoke((GapItem) p1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(GapItem gapItem) {
                            return null;
                        }
                    };
                    LazyColumn.items(items$iv5.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$11
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Object invoke(Integer num) {
                            return invoke(num.intValue());
                        }

                        public final Object invoke(int index) {
                            return Function1.this.invoke(items$iv5.get(index));
                        }
                    }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$1$invoke$$inlined$items$default$12
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                            invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope $this$items, int it4, Composer $composer3, int $changed2) {
                            ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                            int $dirty = $changed2;
                            if (($changed2 & 14) == 0) {
                                $dirty |= $composer3.changed($this$items) ? 4 : 2;
                            }
                            if (($changed2 & 112) == 0) {
                                $dirty |= $composer3.changed(it4) ? 32 : 16;
                            }
                            if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                $composer3.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                            }
                            int i = $dirty & 14;
                            GapItem it5 = (GapItem) items$iv5.get(it4);
                            ComposerKt.sourceInformationMarkerStart($composer3, -985661944, "C*415@16014L51:AdvancedAnalysisScreen.kt#fpoywd");
                            AdvancedAnalysisScreenKt.m6434GapCardRPmYEkk(it5, AppColors.INSTANCE.m6575getSubtle0d7_KjU(), $composer3, 56);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }));
                }
            }
        }, $composer2, 390, 250);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapAnalysisContent$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdvancedAnalysisScreenKt.GapAnalysisContent(CompatibilityRecord.this, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: GapCard-RPmYEkk, reason: not valid java name */
    public static final void m6434GapCardRPmYEkk(final GapItem gap, final long priorityColor, Composer $composer, final int $changed) {
        Composer $composer2 = $composer.startRestartGroup(435155953);
        ComposerKt.sourceInformation($composer2, "C(GapCard)P(!,1:c#ui.graphics.Color)426@16312L43,422@16161L2141:AdvancedAnalysisScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(435155953, $changed, -1, "com.vantedge.app.ui.screens.GapCard (AdvancedAnalysisScreen.kt:421)");
        }
        CardKt.Card(PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(4)), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(AppColors.INSTANCE.m6571getCard0d7_KjU(), 0L, 0L, 0L, $composer2, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer2, -2067186077, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapCard$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                invoke(columnScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:35:0x03bc  */
            /* JADX WARN: Removed duplicated region for block: B:50:0x0493  */
            /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.ColumnScope r86, androidx.compose.runtime.Composer r87, int r88) {
                /*
                    Method dump skipped, instructions count: 1175
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 196614, 24);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$GapCard$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdvancedAnalysisScreenKt.m6434GapCardRPmYEkk(GapItem.this, priorityColor, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void CourseRow(final CourseRecommendation course, final int index, Composer $composer, final int $changed) {
        long m6577getWarning0d7_KjU;
        Composer $composer2;
        Composer $composer3 = $composer.startRestartGroup(1665231407);
        ComposerKt.sourceInformation($composer3, "C(CourseRow)488@18558L3056:AdvancedAnalysisScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(course) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changed(index) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1665231407, $dirty2, -1, "com.vantedge.app.ui.screens.CourseRow (AdvancedAnalysisScreen.kt:482)");
            }
            String category = course.getCategory();
            if (Intrinsics.areEqual(category, "free")) {
                m6577getWarning0d7_KjU = AppColors.INSTANCE.m6576getSuccess0d7_KjU();
            } else {
                m6577getWarning0d7_KjU = Intrinsics.areEqual(category, "discounted") ? AppColors.INSTANCE.m6577getWarning0d7_KjU() : AppColors.INSTANCE.m6573getGeneration0d7_KjU();
            }
            final long categoryColor = m6577getWarning0d7_KjU;
            $composer2 = $composer3;
            SurfaceKt.m2318SurfaceT9BRK9s(null, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), ColorKt.Color(4279049002L), 0L, 0.0f, 0.0f, null, ComposableLambdaKt.composableLambda($composer3, -1627940236, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$CourseRow$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:24:0x024a  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0256  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x03dd  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x03e9  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x0422  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x05d5  */
                /* JADX WARN: Removed duplicated region for block: B:49:0x05e1  */
                /* JADX WARN: Removed duplicated region for block: B:52:0x0614  */
                /* JADX WARN: Removed duplicated region for block: B:57:0x06fe  */
                /* JADX WARN: Removed duplicated region for block: B:60:0x078c  */
                /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:64:0x062a  */
                /* JADX WARN: Removed duplicated region for block: B:65:0x05e5  */
                /* JADX WARN: Removed duplicated region for block: B:67:0x0438 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:68:0x03ef  */
                /* JADX WARN: Removed duplicated region for block: B:71:0x025c  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.runtime.Composer r162, int r163) {
                    /*
                        Method dump skipped, instructions count: 1936
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$CourseRow$1.invoke(androidx.compose.runtime.Composer, int):void");
                }
            }), $composer3, 12583296, 121);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AdvancedAnalysisScreenKt$CourseRow$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    AdvancedAnalysisScreenKt.CourseRow(CourseRecommendation.this, index, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }
}
