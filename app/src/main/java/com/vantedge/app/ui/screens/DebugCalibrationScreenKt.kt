package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarColors;
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
import com.vantedge.app.BuildConfig;
import com.vantedge.app.data.model.TelemetryRecord;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DebugCalibrationScreen.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a7\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007¢\u0006\u0002\u0010\b\u001a\"\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0003ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u000f"}, d2 = {"DebugCalibrationScreen", "", "records", "", "Lcom/vantedge/app/data/model/TelemetryRecord;", "onShareClick", "Lkotlin/Function0;", "onNavigateBack", "(Ljava/util/List;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "TelemetryRecordCard", "record", "cardBg", "Landroidx/compose/ui/graphics/Color;", "TelemetryRecordCard-RPmYEkk", "(Lcom/vantedge/app/data/model/TelemetryRecord;JLandroidx/compose/runtime/Composer;I)V", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DebugCalibrationScreenKt {
    public static final void DebugCalibrationScreen(final List<TelemetryRecord> records, final Function0<Unit> onShareClick, final Function0<Unit> onNavigateBack, Composer $composer, final int $changed) {
        Intrinsics.checkNotNullParameter(records, "records");
        Intrinsics.checkNotNullParameter(onShareClick, "onShareClick");
        Intrinsics.checkNotNullParameter(onNavigateBack, "onNavigateBack");
        Composer $composer2 = $composer.startRestartGroup(1352944373);
        ComposerKt.sourceInformation($composer2, "C(DebugCalibrationScreen)P(2,1)39@1329L1680:DebugCalibrationScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(1352944373, $changed, -1, "com.vantedge.app.ui.screens.DebugCalibrationScreen (DebugCalibrationScreen.kt:32)");
        }
        if (!BuildConfig.DEBUG) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$1
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
                        DebugCalibrationScreenKt.DebugCalibrationScreen(records, onShareClick, onNavigateBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                    }
                });
                return;
            }
            return;
        }
        final long bg = ColorKt.Color(4279045389L);
        final long cardDark = ColorKt.Color(4279900718L);
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -1735850823, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$2
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
                ComposerKt.sourceInformation($composer3, "C44@1530L36,42@1399L651:DebugCalibrationScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1735850823, $changed2, -1, "com.vantedge.app.ui.screens.DebugCalibrationScreen.<anonymous> (DebugCalibrationScreen.kt:42)");
                    }
                    TopAppBarColors m2625topAppBarColorszjMxDiM = TopAppBarDefaults.INSTANCE.m2625topAppBarColorszjMxDiM(bg, 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30);
                    Function2<Composer, Integer, Unit> m6482getLambda1$app_debug = ComposableSingletons$DebugCalibrationScreenKt.INSTANCE.m6482getLambda1$app_debug();
                    final Function0<Unit> function0 = onNavigateBack;
                    ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer3, 785832371, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$2.1
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
                            ComposerKt.sourceInformation($composer4, "C46@1623L163:DebugCalibrationScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(785832371, $changed3, -1, "com.vantedge.app.ui.screens.DebugCalibrationScreen.<anonymous>.<anonymous> (DebugCalibrationScreen.kt:46)");
                                }
                                IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$DebugCalibrationScreenKt.INSTANCE.m6483getLambda2$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    });
                    final Function0<Unit> function02 = onShareClick;
                    AppBarKt.TopAppBar(m6482getLambda1$app_debug, null, composableLambda, ComposableLambdaKt.composableLambda($composer3, 1523595562, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$2.2
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
                            ComposerKt.sourceInformation($composer4, "C50@1853L165:DebugCalibrationScreen.kt#fpoywd");
                            if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(1523595562, $changed3, -1, "com.vantedge.app.ui.screens.DebugCalibrationScreen.<anonymous>.<anonymous> (DebugCalibrationScreen.kt:50)");
                                }
                                IconButtonKt.IconButton(function02, null, false, null, null, ComposableSingletons$DebugCalibrationScreenKt.INSTANCE.m6484getLambda3$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), null, m2625topAppBarColorszjMxDiM, null, $composer3, 3462, 82);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, null, 0, bg, 0L, null, ComposableLambdaKt.composableLambda($composer2, 954914116, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                ComposerKt.sourceInformation($composer3, "C:DebugCalibrationScreen.kt#fpoywd");
                int $dirty = $changed2;
                if (($changed2 & 14) == 0) {
                    $dirty |= $composer3.changed(padding) ? 4 : 2;
                }
                int $dirty2 = $dirty;
                if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(954914116, $dirty2, -1, "com.vantedge.app.ui.screens.DebugCalibrationScreen.<anonymous> (DebugCalibrationScreen.kt:57)");
                    }
                    if (records.isEmpty()) {
                        $composer3.startReplaceableGroup(-802415492);
                        ComposerKt.sourceInformation($composer3, "58@2125L268");
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
                            ComposerKt.sourceInformationMarkerStart($composer3, -1193432078, "C64@2324L55:DebugCalibrationScreen.kt#fpoywd");
                            TextKt.m2466Text4IGK_g("No telemetry records found.", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 390, 0, 131066);
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
                        ComposerKt.sourceInformationMarkerStart($composer3, -1193432078, "C64@2324L55:DebugCalibrationScreen.kt#fpoywd");
                        TextKt.m2466Text4IGK_g("No telemetry records found.", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 390, 0, 131066);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        ComposerKt.sourceInformationMarkerEnd($composer3);
                        $composer3.endReplaceableGroup();
                        $composer3.endNode();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                        $composer3.endReplaceableGroup();
                    } else {
                        $composer3.startReplaceableGroup(-802415194);
                        ComposerKt.sourceInformation($composer3, "67@2423L570");
                        Modifier m566paddingVpY3zN4$default = PaddingKt.m566paddingVpY3zN4$default(PaddingKt.padding(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), padding), Dp.m6092constructorimpl(16), 0.0f, 2, null);
                        Arrangement.HorizontalOrVertical m473spacedBy0680j_4 = Arrangement.INSTANCE.m473spacedBy0680j_4(Dp.m6092constructorimpl(12));
                        final List<TelemetryRecord> list = records;
                        final long j = cardDark;
                        LazyDslKt.LazyColumn(m566paddingVpY3zN4$default, null, null, false, m473spacedBy0680j_4, null, null, false, new Function1<LazyListScope, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$3.2
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
                                LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$DebugCalibrationScreenKt.INSTANCE.m6485getLambda4$app_debug(), 3, null);
                                final List items$iv = list;
                                final long j2 = j;
                                final Function1 contentType$iv = new Function1() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$3$2$invoke$$inlined$items$default$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                                        return invoke((TelemetryRecord) p1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Void invoke(TelemetryRecord telemetryRecord) {
                                        return null;
                                    }
                                };
                                LazyColumn.items(items$iv.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$3$2$invoke$$inlined$items$default$3
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
                                }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$3$2$invoke$$inlined$items$default$4
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
                                        TelemetryRecord record = (TelemetryRecord) items$iv.get(it);
                                        ComposerKt.sourceInformationMarkerStart($composer4, -837725435, "C*77@2822L55:DebugCalibrationScreen.kt#fpoywd");
                                        DebugCalibrationScreenKt.m6537TelemetryRecordCardRPmYEkk(record, j2, $composer4, ((($dirty3 & 14) >> 3) & 14) | 48);
                                        ComposerKt.sourceInformationMarkerEnd($composer4);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }));
                                LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$DebugCalibrationScreenKt.INSTANCE.m6486getLambda5$app_debug(), 3, null);
                            }
                        }, $composer3, 24576, 238);
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
        ScopeUpdateScope endRestartGroup2 = $composer2.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.DebugCalibrationScreenKt$DebugCalibrationScreen$4
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
                    DebugCalibrationScreenKt.DebugCalibrationScreen(records, onShareClick, onNavigateBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006a, code lost:
    
        if (r0.equals("NO_SECTIONAL_STRUCTURE") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007f, code lost:
    
        r0 = kotlin.TuplesKt.to(androidx.compose.ui.graphics.Color.m3740boximpl(androidx.compose.ui.graphics.ColorKt.Color(4294948912L)), androidx.compose.ui.graphics.Color.m3740boximpl(androidx.compose.ui.graphics.ColorKt.Color(4294948912L)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0073, code lost:
    
        if (r0.equals("HIGH_NARRATIVE_DENSITY") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x007c, code lost:
    
        if (r0.equals("NO_CHRONOLOGY") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c2, code lost:
    
        if (r0.equals("LOW_STRUCTURAL_EVIDENCE") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ce, code lost:
    
        r0 = kotlin.TuplesKt.to(androidx.compose.ui.graphics.Color.m3740boximpl(androidx.compose.ui.graphics.ColorKt.Color(4293212469L)), androidx.compose.ui.graphics.Color.m3740boximpl(androidx.compose.ui.graphics.ColorKt.Color(4293212469L)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00cb, code lost:
    
        if (r0.equals("OCR_TOO_FRAGMENTED") == false) goto L49;
     */
    /* renamed from: TelemetryRecordCard-RPmYEkk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6537TelemetryRecordCardRPmYEkk(final com.vantedge.app.data.model.TelemetryRecord r25, final long r26, androidx.compose.runtime.Composer r28, int r29) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.DebugCalibrationScreenKt.m6537TelemetryRecordCardRPmYEkk(com.vantedge.app.data.model.TelemetryRecord, long, androidx.compose.runtime.Composer, int):void");
    }
}
