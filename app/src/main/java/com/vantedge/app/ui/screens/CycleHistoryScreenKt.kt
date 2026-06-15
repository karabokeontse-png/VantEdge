package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
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
import androidx.profileinstaller.ProfileVerifier;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleHistoryScreen.kt */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aY\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\b0\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\b0\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0013H\u0007¢\u0006\u0002\u0010\u0014\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0005\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002¨\u0006\u0015"}, d2 = {"CHAmber", "Landroidx/compose/ui/graphics/Color;", OperatorName.SET_LINE_CAPSTYLE, "CHBg", "CHCard", "CHOnBg", "CHTeal", "CycleHistoryScreen", "", "cycles", "", "Lcom/vantedge/app/data/model/GenerationCycle;", "viewModel", "Lcom/vantedge/app/data/viewmodel/CycleViewModel;", "onOpen", "Lkotlin/Function1;", "onDelete", "", "onNavigateBack", "Lkotlin/Function0;", "(Ljava/util/List;Lcom/vantedge/app/data/viewmodel/CycleViewModel;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CycleHistoryScreenKt {
    private static final long CHBg = ColorKt.Color(4279045389L);
    private static final long CHCard = ColorKt.Color(4279900718L);
    private static final long CHTeal = ColorKt.Color(4278239141L);
    private static final long CHAmber = ColorKt.Color(4294948912L);
    private static final long CHOnBg = ColorKt.Color(4292927712L);

    public static final void CycleHistoryScreen(final List<GenerationCycle> cycles, final CycleViewModel viewModel, final Function1<? super GenerationCycle, Unit> onOpen, final Function1<? super String, Unit> onDelete, final Function0<Unit> onNavigateBack, Composer $composer, final int $changed) {
        Intrinsics.checkNotNullParameter(cycles, "cycles");
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        Intrinsics.checkNotNullParameter(onOpen, "onOpen");
        Intrinsics.checkNotNullParameter(onDelete, "onDelete");
        Intrinsics.checkNotNullParameter(onNavigateBack, "onNavigateBack");
        Composer $composer2 = $composer.startRestartGroup(-1347014941);
        ComposerKt.sourceInformation($composer2, "C(CycleHistoryScreen)P(!1,4,3)40@1438L8563:CycleHistoryScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1347014941, $changed, -1, "com.vantedge.app.ui.screens.CycleHistoryScreen (CycleHistoryScreen.kt:39)");
        }
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -1737771617, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$1
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
                long j;
                ComposerKt.sourceInformation($composer3, "C50@1868L40,43@1510L412:CycleHistoryScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1737771617, $changed2, -1, "com.vantedge.app.ui.screens.CycleHistoryScreen.<anonymous> (CycleHistoryScreen.kt:43)");
                    }
                    Function2<Composer, Integer, Unit> m6473getLambda1$app_debug = ComposableSingletons$CycleHistoryScreenKt.INSTANCE.m6473getLambda1$app_debug();
                    final Function0<Unit> function0 = onNavigateBack;
                    ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer3, -1955122203, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$1.1
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
                            ComposerKt.sourceInformation($composer4, "C46@1649L156:CycleHistoryScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-1955122203, $changed3, -1, "com.vantedge.app.ui.screens.CycleHistoryScreen.<anonymous>.<anonymous> (CycleHistoryScreen.kt:46)");
                                }
                                IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$CycleHistoryScreenKt.INSTANCE.m6474getLambda2$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    });
                    TopAppBarDefaults topAppBarDefaults = TopAppBarDefaults.INSTANCE;
                    j = CycleHistoryScreenKt.CHCard;
                    AppBarKt.TopAppBar(m6473getLambda1$app_debug, null, composableLambda, null, null, topAppBarDefaults.m2625topAppBarColorszjMxDiM(j, 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30), null, $composer3, 390, 90);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, null, 0, CHBg, 0L, null, ComposableLambdaKt.composableLambda($composer2, -384193420, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(PaddingValues padding, Composer $composer3, int $changed2) {
                Intrinsics.checkNotNullParameter(padding, "padding");
                ComposerKt.sourceInformation($composer3, "C:CycleHistoryScreen.kt#fpoywd");
                int $dirty = $changed2;
                if (($changed2 & 14) == 0) {
                    $dirty |= $composer3.changed(padding) ? 4 : 2;
                }
                int $dirty2 = $dirty;
                if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-384193420, $dirty2, -1, "com.vantedge.app.ui.screens.CycleHistoryScreen.<anonymous> (CycleHistoryScreen.kt:54)");
                    }
                    if (cycles.isEmpty()) {
                        $composer3.startReplaceableGroup(1088849636);
                        ComposerKt.sourceInformation($composer3, "55@1996L261");
                        Modifier modifier$iv = PaddingKt.padding(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), padding);
                        Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
                        $composer3.startReplaceableGroup(733328855);
                        ComposerKt.sourceInformation($composer3, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
                        MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer3, ((48 >> 3) & 14) | ((48 >> 3) & 112));
                        int $changed$iv$iv = (48 << 3) & 112;
                        $composer3.startReplaceableGroup(-1323940314);
                        ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                        int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                        CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
                        Function0 factory$iv$iv$iv = ComposeUiNode.INSTANCE.getConstructor();
                        Function3 skippableUpdate$iv$iv$iv = LayoutKt.modifierMaterializerOf(modifier$iv);
                        int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
                        if (!($composer3.getApplier() instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                        }
                        $composer3.startReusableNode();
                        if ($composer3.getInserting()) {
                            $composer3.createNode(factory$iv$iv$iv);
                        } else {
                            $composer3.useNode();
                        }
                        Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m3280constructorimpl($composer3);
                        Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                        Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                        Function2 block$iv$iv$iv = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                        if (!$this$Layout_u24lambda_u240$iv$iv.getInserting() && Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                            skippableUpdate$iv$iv$iv.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                            $composer3.startReplaceableGroup(2058660585);
                            int i = ($changed$iv$iv$iv >> 9) & 14;
                            ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            int i2 = ((48 >> 6) & 112) | 6;
                            ComposerKt.sourceInformationMarkerStart($composer3, 948568282, "C61@2195L48:CycleHistoryScreen.kt#fpoywd");
                            TextKt.m2466Text4IGK_g("No applications yet.", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 390, 0, 131066);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            ComposerKt.sourceInformationMarkerEnd($composer3);
                            $composer3.endReplaceableGroup();
                            $composer3.endNode();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                            $composer3.endReplaceableGroup();
                        }
                        $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                        $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), block$iv$iv$iv);
                        skippableUpdate$iv$iv$iv.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
                        $composer3.startReplaceableGroup(2058660585);
                        int i3 = ($changed$iv$iv$iv >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer3, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
                        BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                        int i22 = ((48 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer3, 948568282, "C61@2195L48:CycleHistoryScreen.kt#fpoywd");
                        TextKt.m2466Text4IGK_g("No applications yet.", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 390, 0, 131066);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                    } else {
                        $composer3.startReplaceableGroup(1088849927);
                        ComposerKt.sourceInformation($composer3, "64@2287L7698");
                        Modifier padding2 = PaddingKt.padding(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), padding);
                        PaddingValues m557PaddingValues0680j_4 = PaddingKt.m557PaddingValues0680j_4(Dp.m6092constructorimpl(16));
                        Arrangement.HorizontalOrVertical m473spacedBy0680j_4 = Arrangement.INSTANCE.m473spacedBy0680j_4(Dp.m6092constructorimpl(12));
                        final List<GenerationCycle> list = cycles;
                        final CycleViewModel cycleViewModel = viewModel;
                        final Function1<GenerationCycle, Unit> function1 = onOpen;
                        final Function1<String, Unit> function12 = onDelete;
                        LazyDslKt.LazyColumn(padding2, null, m557PaddingValues0680j_4, false, m473spacedBy0680j_4, null, null, false, new Function1<LazyListScope, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
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
                                final List items$iv = list;
                                final Function1 key$iv = new Function1<GenerationCycle, Object>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt.CycleHistoryScreen.2.2.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(GenerationCycle it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        return it.getId();
                                    }
                                };
                                final CycleViewModel cycleViewModel2 = cycleViewModel;
                                final Function1<GenerationCycle, Unit> function13 = function1;
                                final Function1<String, Unit> function14 = function12;
                                final Function1 contentType$iv = new Function1() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$invoke$$inlined$items$default$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                                        return invoke((GenerationCycle) p1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Void invoke(GenerationCycle generationCycle) {
                                        return null;
                                    }
                                };
                                LazyColumn.items(items$iv.size(), key$iv != null ? new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$invoke$$inlined$items$default$2
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
                                } : null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$invoke$$inlined$items$default$3
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
                                }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$invoke$$inlined$items$default$4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(4);
                                    }

                                    @Override // kotlin.jvm.functions.Function4
                                    public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num, Composer composer, Integer num2) {
                                        invoke(lazyItemScope, num.intValue(), composer, num2.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(LazyItemScope $this$items, int it, Composer $composer4, int $changed3) {
                                        CompatibilityRecord compatibility;
                                        final long scoreColor;
                                        long j;
                                        long j2;
                                        ComposerKt.sourceInformation($composer4, "C148@6730L22:LazyDsl.kt#428nma");
                                        int $dirty3 = $changed3;
                                        if (($changed3 & 14) == 0) {
                                            $dirty3 |= $composer4.changed($this$items) ? 4 : 2;
                                        }
                                        if (($changed3 & 112) == 0) {
                                            $dirty3 |= $composer4.changed(it) ? 32 : 16;
                                        }
                                        if (($dirty3 & 731) == 146 && $composer4.getSkipping()) {
                                            $composer4.skipToGroupEnd();
                                            return;
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(-632812321, $dirty3, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                                        }
                                        int i4 = $dirty3 & 14;
                                        final GenerationCycle cycle = (GenerationCycle) items$iv.get(it);
                                        ComposerKt.sourceInformationMarkerStart($composer4, -2136747479, "C*94@3646L35,92@3534L6419:CycleHistoryScreen.kt#fpoywd");
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
                                        CompatibilityRecord compatibility2 = compatibility;
                                        final int score = compatibility2 != null ? compatibility2.getScore() : 0;
                                        if (score >= 75) {
                                            scoreColor = ColorKt.Color(4283215696L);
                                        } else if (score >= 50) {
                                            j = CycleHistoryScreenKt.CHAmber;
                                            scoreColor = j;
                                        } else {
                                            scoreColor = ColorKt.Color(4293212469L);
                                        }
                                        Integer previousScore = cycleViewModel2.getPreviousScore(cycle);
                                        final Integer delta = previousScore != null ? Integer.valueOf(score - previousScore.intValue()) : null;
                                        final String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date(cycle.getCreatedAt()));
                                        Modifier fillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                                        CardDefaults cardDefaults = CardDefaults.INSTANCE;
                                        j2 = CycleHistoryScreenKt.CHCard;
                                        CardColors m1628cardColorsro_MJ88 = cardDefaults.m1628cardColorsro_MJ88(j2, 0L, 0L, 0L, $composer4, (CardDefaults.$stable << 12) | 6, 14);
                                        RoundedCornerShape m834RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10));
                                        final Function1 function15 = function13;
                                        final Function1 function16 = function14;
                                        CardKt.Card(new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$2$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            /* JADX WARN: Multi-variable type inference failed */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public /* bridge */ /* synthetic */ Unit invoke() {
                                                invoke2();
                                                return Unit.INSTANCE;
                                            }

                                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2() {
                                                function15.invoke(cycle);
                                            }
                                        }, fillMaxWidth$default, false, m834RoundedCornerShape0680j_4, m1628cardColorsro_MJ88, null, null, null, ComposableLambdaKt.composableLambda($composer4, 593528297, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$2$2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            /* JADX WARN: Multi-variable type inference failed */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                                invoke(columnScope, composer, num.intValue());
                                                return Unit.INSTANCE;
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:24:0x01ff  */
                                            /* JADX WARN: Removed duplicated region for block: B:27:0x020b  */
                                            /* JADX WARN: Removed duplicated region for block: B:30:0x0244  */
                                            /* JADX WARN: Removed duplicated region for block: B:35:0x03bb  */
                                            /* JADX WARN: Removed duplicated region for block: B:38:0x03c7  */
                                            /* JADX WARN: Removed duplicated region for block: B:41:0x0400  */
                                            /* JADX WARN: Removed duplicated region for block: B:46:0x04f8  */
                                            /* JADX WARN: Removed duplicated region for block: B:63:0x05b2  */
                                            /* JADX WARN: Removed duplicated region for block: B:66:0x0748  */
                                            /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
                                            /* JADX WARN: Removed duplicated region for block: B:69:0x0605  */
                                            /* JADX WARN: Removed duplicated region for block: B:77:0x0416  */
                                            /* JADX WARN: Removed duplicated region for block: B:78:0x03cd  */
                                            /* JADX WARN: Removed duplicated region for block: B:80:0x025a  */
                                            /* JADX WARN: Removed duplicated region for block: B:81:0x0211  */
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                                To view partially-correct add '--show-bad-code' argument
                                            */
                                            public final void invoke(androidx.compose.foundation.layout.ColumnScope r128, androidx.compose.runtime.Composer r129, int r130) {
                                                /*
                                                    Method dump skipped, instructions count: 1868
                                                    To view this dump add '--comments-level debug' option
                                                */
                                                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$2$2$2$2.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                                            }
                                        }), $composer4, 100663344, 228);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }));
                            }
                        }, $composer3, 24960, 234);
                        $composer3.endReplaceableGroup();
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), $composer2, 806879280, 445);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CycleHistoryScreenKt$CycleHistoryScreen$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    CycleHistoryScreenKt.CycleHistoryScreen(cycles, viewModel, onOpen, onDelete, onNavigateBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }
}
