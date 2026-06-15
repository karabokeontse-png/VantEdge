package com.vantedge.app.ui.screens;

import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.Updater;
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
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CVDesignScreen.kt */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a/\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0007¢\u0006\u0002\u0010\f\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\r"}, d2 = {"cvDesigns", "", "Lcom/vantedge/app/ui/screens/CVDesign;", "getCvDesigns", "()Ljava/util/List;", "CVDesignScreen", "", "onDesignSelected", "Lkotlin/Function1;", "", "onNavigateBack", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CVDesignScreenKt {
    private static final List<CVDesign> cvDesigns = CollectionsKt.listOf((Object[]) new CVDesign[]{new CVDesign("modern", "The Modern Professional", "Sans-serif fonts, subtle color accents, and a two-column layout to save space.", "IT professionals, project managers, mid-to-senior corporate roles.", "High"), new CVDesign("minimalist", "The Minimalist", "Large headings, generous margins, single-column layout. Timeless typography.", "Law, finance, academia, high-end sophistication.", "Excellent"), new CVDesign("creative", "The Creative / Portfolio", "Bold color palettes, unique grid layouts, designed to show personality.", "Graphic designers, UX/UI designers, photographers, marketers.", "Low"), new CVDesign("executive", "The Executive / Traditional", "Serif fonts, centered headers, horizontal dividers. High-density information.", "C-suite executives, government, traditional banking.", "Very High")});

    public static final List<CVDesign> getCvDesigns() {
        return cvDesigns;
    }

    public static final void CVDesignScreen(final Function1<? super String, Unit> onDesignSelected, final Function0<Unit> onNavigateBack, Composer $composer, final int $changed) {
        Composer $composer2;
        Intrinsics.checkNotNullParameter(onDesignSelected, "onDesignSelected");
        Intrinsics.checkNotNullParameter(onNavigateBack, "onNavigateBack");
        Composer $composer3 = $composer.startRestartGroup(663470614);
        ComposerKt.sourceInformation($composer3, "C(CVDesignScreen)75@2836L3244:CVDesignScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changedInstance(onDesignSelected) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changedInstance(onNavigateBack) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(663470614, $dirty2, -1, "com.vantedge.app.ui.screens.CVDesignScreen (CVDesignScreen.kt:74)");
            }
            $composer2 = $composer3;
            ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableSingletons$CVDesignScreenKt.INSTANCE.m6460getLambda2$app_debug(), null, null, null, 0, AppColorsKt.getDarkBg(), 0L, null, ComposableLambdaKt.composableLambda($composer3, -1530755993, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$1
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

                public final void invoke(PaddingValues padding, Composer $composer4, int $changed2) {
                    Object value$iv;
                    Intrinsics.checkNotNullParameter(padding, "padding");
                    ComposerKt.sourceInformation($composer4, "C94@3379L21,89@3210L2864:CVDesignScreen.kt#fpoywd");
                    int $dirty3 = $changed2;
                    if (($changed2 & 14) == 0) {
                        $dirty3 |= $composer4.changed(padding) ? 4 : 2;
                    }
                    if (($dirty3 & 91) != 18 || !$composer4.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1530755993, $dirty3, -1, "com.vantedge.app.ui.screens.CVDesignScreen.<anonymous> (CVDesignScreen.kt:89)");
                        }
                        Modifier modifier$iv = ScrollKt.verticalScroll$default(PaddingKt.m564padding3ABfNKs(PaddingKt.padding(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), padding), Dp.m6092constructorimpl(16)), ScrollKt.rememberScrollState(0, $composer4, 0, 1), false, null, false, 14, null);
                        Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.m473spacedBy0680j_4(Dp.m6092constructorimpl(12));
                        Function0<Unit> function0 = onNavigateBack;
                        final Function1<String, Unit> function1 = onDesignSelected;
                        $composer4.startReplaceableGroup(-483455358);
                        ComposerKt.sourceInformation($composer4, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                        Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
                        MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer4, ((48 >> 3) & 14) | ((48 >> 3) & 112));
                        int $changed$iv$iv = (48 << 3) & 112;
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
                        ComposerKt.sourceInformationMarkerStart($composer4, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                        int i2 = ((48 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer4, 675614181, "C97@3489L166,147@5678L40,152@5874L131,149@5732L332:CVDesignScreen.kt#fpoywd");
                        TextKt.m2466Text4IGK_g("Select the style that best matches your industry and role.", (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3462, 0, 131058);
                        $composer4.startReplaceableGroup(675614371);
                        ComposerKt.sourceInformation($composer4, "*107@3836L31,109@3966L92,104@3715L1935");
                        Iterable $this$forEach$iv = CVDesignScreenKt.getCvDesigns();
                        int $i$f$forEach = 0;
                        for (Object element$iv : $this$forEach$iv) {
                            Iterable $this$forEach$iv2 = $this$forEach$iv;
                            final CVDesign design = (CVDesign) element$iv;
                            int $i$f$forEach2 = $i$f$forEach;
                            int $dirty4 = $dirty3;
                            int compositeKeyHash$iv$iv2 = compositeKeyHash$iv$iv;
                            int $changed$iv$iv$iv2 = $changed$iv$iv$iv;
                            Modifier fillMaxWidth$default = SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null);
                            $composer4.startReplaceableGroup(-58078367);
                            ComposerKt.sourceInformation($composer4, "CC(remember):CVDesignScreen.kt#9igjgp");
                            boolean invalid$iv = $composer4.changedInstance(function1) | $composer4.changed(design);
                            Object it$iv = $composer4.rememberedValue();
                            if (!invalid$iv && it$iv != Composer.INSTANCE.getEmpty()) {
                                value$iv = it$iv;
                                $composer4.endReplaceableGroup();
                                CardKt.Card(ClickableKt.m246clickableXHw0xAI$default(fillMaxWidth$default, false, null, null, (Function0) value$iv, 7, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(12)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(ColorKt.Color(4279308561L), 0L, 0L, 0L, $composer4, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer4, 1365133083, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$1$1$1$2
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                        invoke(columnScope, composer, num.intValue());
                                        return Unit.INSTANCE;
                                    }

                                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                                    /* JADX WARN: Code restructure failed: missing block: B:36:0x02ff, code lost:
                                    
                                        if (r0.equals("Very High") != false) goto L53;
                                     */
                                    /* JADX WARN: Code restructure failed: missing block: B:37:0x031b, code lost:
                                    
                                        r61 = androidx.compose.ui.graphics.ColorKt.Color(4283215696L);
                                     */
                                    /* JADX WARN: Code restructure failed: missing block: B:47:0x0318, code lost:
                                    
                                        if (r0.equals("Excellent") == false) goto L54;
                                     */
                                    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
                                    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
                                    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
                                    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
                                    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
                                    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
                                     */
                                    /* JADX WARN: Removed duplicated region for block: B:35:0x02f9  */
                                    /* JADX WARN: Removed duplicated region for block: B:40:0x041c  */
                                    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
                                    /* JADX WARN: Removed duplicated region for block: B:43:0x0302  */
                                    /* JADX WARN: Removed duplicated region for block: B:46:0x0312  */
                                    /* JADX WARN: Removed duplicated region for block: B:48:0x0327  */
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct add '--show-bad-code' argument
                                    */
                                    public final void invoke(androidx.compose.foundation.layout.ColumnScope r85, androidx.compose.runtime.Composer r86, int r87) {
                                        /*
                                            Method dump skipped, instructions count: 1070
                                            To view this dump add '--comments-level debug' option
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$1$1$1$2.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                                    }
                                }), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 24);
                                $this$forEach$iv = $this$forEach$iv2;
                                $i$f$forEach = $i$f$forEach2;
                                $dirty3 = $dirty4;
                                compositeKeyHash$iv$iv = compositeKeyHash$iv$iv2;
                                $changed$iv$iv$iv = $changed$iv$iv$iv2;
                            }
                            value$iv = (Function0) new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$1$1$1$1$1
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
                                    function1.invoke(design.getId());
                                }
                            };
                            $composer4.updateRememberedValue(value$iv);
                            $composer4.endReplaceableGroup();
                            CardKt.Card(ClickableKt.m246clickableXHw0xAI$default(fillMaxWidth$default, false, null, null, (Function0) value$iv, 7, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(12)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(ColorKt.Color(4279308561L), 0L, 0L, 0L, $composer4, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer4, 1365133083, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$1$1$1$2
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                                    invoke(columnScope, composer, num.intValue());
                                    return Unit.INSTANCE;
                                }

                                public final void invoke(ColumnScope columnScope, Composer composer, int i3) {
                                    /*
                                        Method dump skipped, instructions count: 1070
                                        To view this dump add '--comments-level debug' option
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$1$1$1$2.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                                }
                            }), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 24);
                            $this$forEach$iv = $this$forEach$iv2;
                            $i$f$forEach = $i$f$forEach2;
                            $dirty3 = $dirty4;
                            compositeKeyHash$iv$iv = compositeKeyHash$iv$iv2;
                            $changed$iv$iv$iv = $changed$iv$iv$iv2;
                        }
                        $composer4.endReplaceableGroup();
                        SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(8)), $composer4, 6);
                        ButtonKt.Button(function0, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, null, ButtonDefaults.INSTANCE.m1607buttonColorsro_MJ88(Color.INSTANCE.m3779getDarkGray0d7_KjU(), Color.INSTANCE.m3787getWhite0d7_KjU(), 0L, 0L, $composer4, (ButtonDefaults.$stable << 12) | 54, 12), null, null, null, null, ComposableSingletons$CVDesignScreenKt.INSTANCE.m6461getLambda3$app_debug(), $composer4, 805306416, 492);
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
            }), $composer2, 806879280, 445);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CVDesignScreenKt$CVDesignScreen$2
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
                    CVDesignScreenKt.CVDesignScreen(onDesignSelected, onNavigateBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }
}
