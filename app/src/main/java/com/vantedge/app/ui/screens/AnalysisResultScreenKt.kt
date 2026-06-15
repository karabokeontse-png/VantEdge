package com.vantedge.app.ui.screens;

import android.content.Context;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
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
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambda;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.profileinstaller.ProfileVerifier;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.GapItem;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.model.RelevancyItem;
import com.vantedge.app.ui.theme.AppColors;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: AnalysisResultScreen.kt */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\u001aW\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007¢\u0006\u0002\u0010\u000b\u001a\u001d\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0003¢\u0006\u0002\u0010\u0011\u001a\u0015\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0014H\u0003¢\u0006\u0002\u0010\u0015\u001a\u0015\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018H\u0003¢\u0006\u0002\u0010\u0019¨\u0006\u001a²\u0006\n\u0010\u001b\u001a\u00020\u001cX\u008a\u008e\u0002²\u0006\n\u0010\u001d\u001a\u00020\u001eX\u008a\u0084\u0002"}, d2 = {"AnalysisResultScreen", "", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "mode", "Lcom/vantedge/app/ui/screens/AnalysisScreenMode;", "onGenerateCv", "Lkotlin/Function0;", "onAdvancedAnalysis", "onDismiss", "onBack", "(Lcom/vantedge/app/data/model/GenerationCycle;Lcom/vantedge/app/ui/screens/AnalysisScreenMode;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;II)V", "GapCard", "gap", "Lcom/vantedge/app/data/model/GapItem;", "context", "Landroid/content/Context;", "(Lcom/vantedge/app/data/model/GapItem;Landroid/content/Context;Landroidx/compose/runtime/Composer;I)V", "RelevancyCard", "item", "Lcom/vantedge/app/data/model/RelevancyItem;", "(Lcom/vantedge/app/data/model/RelevancyItem;Landroidx/compose/runtime/Composer;I)V", "ScoreCircle", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "(Lcom/vantedge/app/data/model/CompatibilityRecord;Landroidx/compose/runtime/Composer;I)V", "app_debug", "selectedTab", "", "sweep", ""}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AnalysisResultScreenKt {
    public static final void AnalysisResultScreen(final GenerationCycle cycle, AnalysisScreenMode mode, final Function0<Unit> onGenerateCv, final Function0<Unit> onAdvancedAnalysis, final Function0<Unit> onDismiss, final Function0<Unit> onBack, Composer $composer, final int $changed, final int i) {
        final AnalysisScreenMode mode2;
        CompatibilityRecord compatibility;
        Object value$iv;
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        Intrinsics.checkNotNullParameter(onGenerateCv, "onGenerateCv");
        Intrinsics.checkNotNullParameter(onAdvancedAnalysis, "onAdvancedAnalysis");
        Intrinsics.checkNotNullParameter(onDismiss, "onDismiss");
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(-1650372428);
        ComposerKt.sourceInformation($composer2, "C(AnalysisResultScreen)P(!2,5!1,4)58@2155L30,59@2217L7,61@2230L13092:AnalysisResultScreen.kt#fpoywd");
        if ((i & 2) != 0) {
            mode2 = AnalysisScreenMode.Live;
        } else {
            mode2 = mode;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1650372428, $changed, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen (AnalysisResultScreen.kt:51)");
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
        $composer2.startReplaceableGroup(-2045705851);
        ComposerKt.sourceInformation($composer2, "CC(remember):AnalysisResultScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(0, null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState selectedTab$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) consume;
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -307260552, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$1
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
                ComposerKt.sourceInformation($composer3, "C85@3172L48,64@2318L916:AnalysisResultScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-307260552, $changed2, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen.<anonymous> (AnalysisResultScreen.kt:64)");
                    }
                    final GenerationCycle generationCycle = GenerationCycle.this;
                    ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer3, 627708724, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$1.1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:24:0x01d3  */
                        /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public final void invoke(androidx.compose.runtime.Composer r76, int r77) {
                            /*
                                Method dump skipped, instructions count: 471
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$1.AnonymousClass1.invoke(androidx.compose.runtime.Composer, int):void");
                        }
                    });
                    final Function0<Unit> function0 = onBack;
                    AppBarKt.TopAppBar(composableLambda, null, ComposableLambdaKt.composableLambda($composer3, 1714668274, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$1.2
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
                            ComposerKt.sourceInformation($composer4, "C77@2835L274:AnalysisResultScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(1714668274, $changed3, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen.<anonymous>.<anonymous> (AnalysisResultScreen.kt:77)");
                                }
                                IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$AnalysisResultScreenKt.INSTANCE.m6455getLambda1$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
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
        }), ComposableLambdaKt.composableLambda($composer2, -1575930631, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$2
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
                ComposerKt.sourceInformation($composer3, "C89@3280L2844:AnalysisResultScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1575930631, $changed2, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen.<anonymous> (AnalysisResultScreen.kt:89)");
                    }
                    long m6571getCard0d7_KjU = AppColors.INSTANCE.m6571getCard0d7_KjU();
                    float m6092constructorimpl = Dp.m6092constructorimpl(4);
                    final AnalysisScreenMode analysisScreenMode = AnalysisScreenMode.this;
                    final Function0<Unit> function0 = onGenerateCv;
                    final Function0<Unit> function02 = onDismiss;
                    SurfaceKt.m2318SurfaceT9BRK9s(null, null, m6571getCard0d7_KjU, 0L, m6092constructorimpl, 0.0f, null, ComposableLambdaKt.composableLambda($composer3, -128392236, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$2.1
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
                            final String generateLabel;
                            Composer $composer5;
                            Function0<Unit> function03;
                            long m3748copywmQWz5c;
                            ComposerKt.sourceInformation($composer4, "C90@3353L2757:AnalysisResultScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-128392236, $changed3, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen.<anonymous>.<anonymous> (AnalysisResultScreen.kt:90)");
                                }
                                Modifier modifier$iv = PaddingKt.m564padding3ABfNKs(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16));
                                Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m473spacedBy0680j_4(Dp.m6092constructorimpl(10));
                                final AnalysisScreenMode analysisScreenMode2 = AnalysisScreenMode.this;
                                Function0<Unit> function04 = function0;
                                Function0<Unit> function05 = function02;
                                $composer4.startReplaceableGroup(-483455358);
                                ComposerKt.sourceInformation($composer4, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                                Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
                                MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer4, ((54 >> 3) & 14) | ((54 >> 3) & 112));
                                int $changed$iv$iv = (54 << 3) & 112;
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
                                int i2 = ($changed$iv$iv$iv >> 9) & 14;
                                ComposerKt.sourceInformationMarkerStart($composer4, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                int i3 = ((54 >> 6) & 112) | 6;
                                ComposerKt.sourceInformationMarkerStart($composer4, -453129211, "C137@5574L112,133@5347L745:AnalysisResultScreen.kt#fpoywd");
                                $composer4.startReplaceableGroup(-453129211);
                                ComposerKt.sourceInformation($composer4, "97@3669L588");
                                if (analysisScreenMode2 == AnalysisScreenMode.HistoricalReadOnly) {
                                    m3748copywmQWz5c = Color.m3748copywmQWz5c(r30, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r30) : 0.15f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r30) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r30) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(AppColors.INSTANCE.m6575getSubtle0d7_KjU()) : 0.0f);
                                    SurfaceKt.m2318SurfaceT9BRK9s(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), m3748copywmQWz5c, 0L, 0.0f, 0.0f, null, ComposableSingletons$AnalysisResultScreenKt.INSTANCE.m6456getLambda2$app_debug(), $composer4, 12583302, 120);
                                }
                                $composer4.endReplaceableGroup();
                                $composer4.startReplaceableGroup(-453128502);
                                ComposerKt.sourceInformation($composer4, "124@4998L173,120@4760L543");
                                if (analysisScreenMode2 == AnalysisScreenMode.Live || analysisScreenMode2 == AnalysisScreenMode.HistoricalContinuation) {
                                    if (analysisScreenMode2 == AnalysisScreenMode.HistoricalContinuation) {
                                        generateLabel = "Resume — Generate My CV & Cover Letter";
                                    } else {
                                        generateLabel = "Generate My CV & Cover Letter";
                                    }
                                    $composer5 = $composer4;
                                    function03 = function05;
                                    ButtonKt.Button(function04, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), ButtonDefaults.INSTANCE.m1607buttonColorsro_MJ88(AppColors.INSTANCE.m6573getGeneration0d7_KjU(), Color.INSTANCE.m3776getBlack0d7_KjU(), 0L, 0L, $composer4, (ButtonDefaults.$stable << 12) | 54, 12), null, null, null, null, ComposableLambdaKt.composableLambda($composer4, 1897243548, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$2$1$1$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                            invoke(rowScope, composer, num.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(RowScope Button, Composer $composer6, int $changed4) {
                                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                                            ComposerKt.sourceInformation($composer6, "C129@5228L49:AnalysisResultScreen.kt#fpoywd");
                                            if (($changed4 & 81) == 16 && $composer6.getSkipping()) {
                                                $composer6.skipToGroupEnd();
                                                return;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(1897243548, $changed4, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AnalysisResultScreen.kt:129)");
                                            }
                                            TextKt.m2466Text4IGK_g(generateLabel, (Modifier) null, 0L, 0L, (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer6, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 0, 131038);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }), $composer5, 805306416, 484);
                                } else {
                                    $composer5 = $composer4;
                                    function03 = function05;
                                }
                                $composer5.endReplaceableGroup();
                                Composer $composer6 = $composer5;
                                ButtonKt.OutlinedButton(function03, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8)), ButtonDefaults.INSTANCE.m1617outlinedButtonColorsro_MJ88(0L, AppColors.INSTANCE.m6572getDestructive0d7_KjU(), 0L, 0L, $composer5, (ButtonDefaults.$stable << 12) | 48, 13), null, BorderStrokeKt.m239BorderStrokecXLIe8U(Dp.m6092constructorimpl(1), AppColors.INSTANCE.m6572getDestructive0d7_KjU()), null, null, ComposableLambdaKt.composableLambda($composer6, -1526121748, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$2$1$1$2
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                                        invoke(rowScope, composer, num.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(RowScope OutlinedButton, Composer $composer7, int $changed4) {
                                        Intrinsics.checkNotNullParameter(OutlinedButton, "$this$OutlinedButton");
                                        ComposerKt.sourceInformation($composer7, "C144@5893L177:AnalysisResultScreen.kt#fpoywd");
                                        if (($changed4 & 81) != 16 || !$composer7.getSkipping()) {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart(-1526121748, $changed4, -1, "com.vantedge.app.ui.screens.AnalysisResultScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AnalysisResultScreen.kt:144)");
                                            }
                                            TextKt.m2466Text4IGK_g(AnalysisScreenMode.this == AnalysisScreenMode.Live ? "Dismiss" : "Back", (Modifier) null, AppColors.INSTANCE.m6572getDestructive0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer7, KyberEngine.KyberPolyBytes, 0, 131066);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                                return;
                                            }
                                            return;
                                        }
                                        $composer7.skipToGroupEnd();
                                    }
                                }), $composer6, 806879280, 420);
                                ComposerKt.sourceInformationMarkerEnd($composer6);
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
                    }), $composer3, 12607872, 107);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, 0, AppColors.INSTANCE.m6570getBackground0d7_KjU(), 0L, null, ComposableLambdaKt.composableLambda($composer2, 347613379, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:33:0x01c1  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.PaddingValues r54, androidx.compose.runtime.Composer r55, int r56) {
                /*
                    Method dump skipped, instructions count: 526
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$3.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 806879664, 441);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            final AnalysisScreenMode analysisScreenMode = mode2;
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$AnalysisResultScreen$4
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
                    AnalysisResultScreenKt.AnalysisResultScreen(GenerationCycle.this, analysisScreenMode, onGenerateCv, onAdvancedAnalysis, onDismiss, onBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int AnalysisResultScreen$lambda$1(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void AnalysisResultScreen$lambda$2(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static final void RelevancyCard(final RelevancyItem item, Composer $composer, final int $changed) {
        long m6575getSubtle0d7_KjU;
        final String groupLabel;
        Composer $composer2 = $composer.startRestartGroup(-1350997715);
        ComposerKt.sourceInformation($composer2, "C(RelevancyCard)396@16038L43,392@15887L1575:AnalysisResultScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(item) ? 4 : 2;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 11) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1350997715, $dirty2, -1, "com.vantedge.app.ui.screens.RelevancyCard (AnalysisResultScreen.kt:376)");
            }
            String relevancyGroup = item.getRelevancyGroup();
            switch (relevancyGroup.hashCode()) {
                case -2024701067:
                    if (relevancyGroup.equals("MEDIUM")) {
                        m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6577getWarning0d7_KjU();
                        break;
                    }
                    m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6575getSubtle0d7_KjU();
                    break;
                case -171693850:
                    if (relevancyGroup.equals("PROFESSIONAL_MISMATCH")) {
                        m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6572getDestructive0d7_KjU();
                        break;
                    }
                    m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6575getSubtle0d7_KjU();
                    break;
                case 75572:
                    if (relevancyGroup.equals("LOW")) {
                        m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6575getSubtle0d7_KjU();
                        break;
                    }
                    m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6575getSubtle0d7_KjU();
                    break;
                case 2217378:
                    if (relevancyGroup.equals("HIGH")) {
                        m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6576getSuccess0d7_KjU();
                        break;
                    }
                    m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6575getSubtle0d7_KjU();
                    break;
                default:
                    m6575getSubtle0d7_KjU = AppColors.INSTANCE.m6575getSubtle0d7_KjU();
                    break;
            }
            final long groupColor = m6575getSubtle0d7_KjU;
            String relevancyGroup2 = item.getRelevancyGroup();
            switch (relevancyGroup2.hashCode()) {
                case -2024701067:
                    if (relevancyGroup2.equals("MEDIUM")) {
                        groupLabel = "Partial match";
                        break;
                    }
                    groupLabel = item.getRelevancyGroup();
                    break;
                case -171693850:
                    if (relevancyGroup2.equals("PROFESSIONAL_MISMATCH")) {
                        groupLabel = "Not relevant";
                        break;
                    }
                    groupLabel = item.getRelevancyGroup();
                    break;
                case 75572:
                    if (relevancyGroup2.equals("LOW")) {
                        groupLabel = "Weak match";
                        break;
                    }
                    groupLabel = item.getRelevancyGroup();
                    break;
                case 2217378:
                    if (relevancyGroup2.equals("HIGH")) {
                        groupLabel = "Strong match";
                        break;
                    }
                    groupLabel = item.getRelevancyGroup();
                    break;
                default:
                    groupLabel = item.getRelevancyGroup();
                    break;
            }
            CardKt.Card(PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(5)), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(AppColors.INSTANCE.m6571getCard0d7_KjU(), 0L, 0L, 0L, $composer2, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer2, 518269599, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$RelevancyCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:35:0x03c3  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x0431  */
                /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r95, androidx.compose.runtime.Composer r96, int r97) {
                    /*
                        Method dump skipped, instructions count: 1077
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AnalysisResultScreenKt$RelevancyCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, 196614, 24);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$RelevancyCard$2
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
                    AnalysisResultScreenKt.RelevancyCard(RelevancyItem.this, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void GapCard(final GapItem gap, final Context context, Composer $composer, final int $changed) {
        long m3748copywmQWz5c;
        Composer $composer2 = $composer.startRestartGroup(151314018);
        ComposerKt.sourceInformation($composer2, "C(GapCard)P(1)456@18036L86,452@17885L3921:AnalysisResultScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(151314018, $changed, -1, "com.vantedge.app.ui.screens.GapCard (AnalysisResultScreen.kt:440)");
        }
        String importance = gap.getImportance();
        long importanceColor = Intrinsics.areEqual(importance, "MANDATORY") ? AppColors.INSTANCE.m6572getDestructive0d7_KjU() : Intrinsics.areEqual(importance, "IMPORTANT") ? AppColors.INSTANCE.m6577getWarning0d7_KjU() : AppColors.INSTANCE.m6575getSubtle0d7_KjU();
        String importance2 = gap.getImportance();
        final String importanceLabel = Intrinsics.areEqual(importance2, "MANDATORY") ? "Mandatory" : Intrinsics.areEqual(importance2, "IMPORTANT") ? "Important" : "Nice to have";
        Modifier m565paddingVpY3zN4 = PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(5));
        CardDefaults cardDefaults = CardDefaults.INSTANCE;
        m3748copywmQWz5c = Color.m3748copywmQWz5c(importanceColor, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(importanceColor) : 0.06f, (r12 & 2) != 0 ? Color.m3756getRedimpl(importanceColor) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(importanceColor) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(importanceColor) : 0.0f);
        final long j = importanceColor;
        CardKt.Card(m565paddingVpY3zN4, RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), cardDefaults.m1628cardColorsro_MJ88(m3748copywmQWz5c, 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14), null, null, ComposableLambdaKt.composableLambda($composer2, 1943939284, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$GapCard$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                invoke(columnScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:24:0x01ea  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x01f6  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x022d  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x03f3  */
            /* JADX WARN: Removed duplicated region for block: B:54:0x069c  */
            /* JADX WARN: Removed duplicated region for block: B:57:0x06a8  */
            /* JADX WARN: Removed duplicated region for block: B:65:0x07bb  */
            /* JADX WARN: Removed duplicated region for block: B:67:0x07c2  */
            /* JADX WARN: Removed duplicated region for block: B:70:0x0810  */
            /* JADX WARN: Removed duplicated region for block: B:73:0x0815 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:74:0x0800  */
            /* JADX WARN: Removed duplicated region for block: B:75:0x07be  */
            /* JADX WARN: Removed duplicated region for block: B:78:0x06ae  */
            /* JADX WARN: Removed duplicated region for block: B:90:0x08d9  */
            /* JADX WARN: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:94:0x0243  */
            /* JADX WARN: Removed duplicated region for block: B:95:0x01fc  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.ColumnScope r123, androidx.compose.runtime.Composer r124, int r125) {
                /*
                    Method dump skipped, instructions count: 2269
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AnalysisResultScreenKt$GapCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 196614, 24);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.AnalysisResultScreenKt$GapCard$2
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
                    AnalysisResultScreenKt.GapCard(GapItem.this, context, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x05a9  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x05b2  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x05bf  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x033d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0258  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ScoreCircle(final com.vantedge.app.data.model.CompatibilityRecord r119, androidx.compose.runtime.Composer r120, final int r121) {
        /*
            Method dump skipped, instructions count: 1474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.AnalysisResultScreenKt.ScoreCircle(com.vantedge.app.data.model.CompatibilityRecord, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float ScoreCircle$lambda$3(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }
}
