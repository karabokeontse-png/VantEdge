package com.vantedge.app.ui.screens;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentTransitionScope;
import androidx.compose.animation.ContentTransform;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.InfiniteRepeatableSpec;
import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransitionKt;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.ProgressIndicatorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.draw.ScaleKt;
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
import androidx.core.app.NotificationCompat;
import com.tom_roush.fontbox.ttf.OS2WindowsMetricsTable;
import com.vantedge.app.data.viewmodel.OnboardingExtractionState;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ExtractingScreen.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u001a#\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007¢\u0006\u0002\u0010\u0006¨\u0006\u0007²\u0006\n\u0010\b\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n\u0010\n\u001a\u00020\tX\u008a\u0084\u0002"}, d2 = {"ExtractingScreen", "", "extractionState", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "onRetry", "Lkotlin/Function0;", "(Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug", "scale", "", "alpha"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ExtractingScreenKt {
    public static final void ExtractingScreen(final OnboardingExtractionState extractionState, final Function0<Unit> onRetry, Composer $composer, final int $changed) {
        Function0 factory$iv$iv$iv;
        Composer $composer$iv;
        Composer $composer2;
        Composer $composer3;
        String statusText;
        Function0 factory$iv$iv$iv2;
        long m3748copywmQWz5c;
        long m3748copywmQWz5c2;
        Function0 factory$iv$iv$iv3;
        Intrinsics.checkNotNullParameter(extractionState, "extractionState");
        Intrinsics.checkNotNullParameter(onRetry, "onRetry");
        Composer $composer4 = $composer.startRestartGroup(1992033164);
        ComposerKt.sourceInformation($composer4, "C(ExtractingScreen)30@1020L4125:ExtractingScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer4.changed(extractionState) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer4.changedInstance(onRetry) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) != 18 || !$composer4.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1992033164, $dirty2, -1, "com.vantedge.app.ui.screens.ExtractingScreen (ExtractingScreen.kt:26)");
            }
            long teal = ColorKt.Color(4278239141L);
            long bg = ColorKt.Color(4279045389L);
            Modifier modifier$iv = BackgroundKt.m211backgroundbw27NRU$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), bg, null, 2, null);
            Alignment contentAlignment$iv = Alignment.INSTANCE.getCenter();
            $composer4.startReplaceableGroup(733328855);
            ComposerKt.sourceInformation($composer4, "CC(Box)P(2,1,3)71@3309L67,72@3381L130:Box.kt#2w3rfo");
            MeasurePolicy measurePolicy$iv = BoxKt.rememberBoxMeasurePolicy(contentAlignment$iv, false, $composer4, ((54 >> 3) & 14) | ((54 >> 3) & 112));
            int $changed$iv$iv = (54 << 3) & 112;
            $composer4.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
            CompositionLocalMap localMap$iv$iv = $composer4.getCurrentCompositionLocalMap();
            Function0 factory$iv$iv$iv4 = ComposeUiNode.INSTANCE.getConstructor();
            Function3 skippableUpdate$iv$iv$iv = LayoutKt.modifierMaterializerOf(modifier$iv);
            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
            if (!($composer4.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer4.startReusableNode();
            if ($composer4.getInserting()) {
                factory$iv$iv$iv = factory$iv$iv$iv4;
                $composer4.createNode(factory$iv$iv$iv);
            } else {
                factory$iv$iv$iv = factory$iv$iv$iv4;
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
            ComposerKt.sourceInformationMarkerStart($composer4, -1253629263, "C73@3426L9:Box.kt#2w3rfo");
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            int i2 = ((54 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer4, 1465708964, "C:ExtractingScreen.kt#fpoywd");
            if (extractionState instanceof OnboardingExtractionState.Failure) {
                $composer4.startReplaceableGroup(1465709054);
                ComposerKt.sourceInformation($composer4, "39@1277L1013");
                Alignment.Horizontal centerHorizontally = Alignment.INSTANCE.getCenterHorizontally();
                Modifier m566paddingVpY3zN4$default = PaddingKt.m566paddingVpY3zN4$default(Modifier.INSTANCE, Dp.m6092constructorimpl(32), 0.0f, 2, null);
                $composer4.startReplaceableGroup(-483455358);
                ComposerKt.sourceInformation($composer4, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.getTop();
                MeasurePolicy measurePolicy$iv2 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, centerHorizontally, $composer4, ((390 >> 3) & 14) | ((390 >> 3) & 112));
                int $changed$iv$iv2 = (390 << 3) & 112;
                $composer4.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer4, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv2 = ComposablesKt.getCurrentCompositeKeyHash($composer4, 0);
                CompositionLocalMap localMap$iv$iv2 = $composer4.getCurrentCompositionLocalMap();
                Function0 factory$iv$iv$iv5 = ComposeUiNode.INSTANCE.getConstructor();
                Function3 skippableUpdate$iv$iv$iv2 = LayoutKt.modifierMaterializerOf(m566paddingVpY3zN4$default);
                int $changed$iv$iv$iv2 = (($changed$iv$iv2 << 9) & 7168) | 6;
                if (!($composer4.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer4.startReusableNode();
                if ($composer4.getInserting()) {
                    $composer4.createNode(factory$iv$iv$iv5);
                } else {
                    $composer4.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv2 = Updater.m3280constructorimpl($composer4);
                Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv2, measurePolicy$iv2, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv2, localMap$iv$iv2, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2 block$iv$iv$iv2 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if ($this$Layout_u24lambda_u240$iv$iv2.getInserting()) {
                    factory$iv$iv$iv3 = factory$iv$iv$iv5;
                } else {
                    factory$iv$iv$iv3 = factory$iv$iv$iv5;
                    if (Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv2.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv2))) {
                        skippableUpdate$iv$iv$iv2.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                        $composer4.startReplaceableGroup(2058660585);
                        int i3 = ($changed$iv$iv$iv2 >> 9) & 14;
                        ComposerKt.sourceInformationMarkerStart($composer4, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                        int i4 = ((390 >> 6) & 112) | 6;
                        ComposerKt.sourceInformationMarkerStart($composer4, -418254739, "C43@1465L215,49@1701L30,50@1752L205,56@1978L30,59@2128L35,57@2029L243:ExtractingScreen.kt#fpoywd");
                        TextKt.m2466Text4IGK_g("Something went wrong", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), TextUnitKt.getSp(20), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 200070, 0, 131026);
                        SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), $composer4, 6);
                        TextKt.m2466Text4IGK_g(((OnboardingExtractionState.Failure) extractionState).getMessage(), (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, TextAlign.m5955boximpl(TextAlign.INSTANCE.m5962getCentere0LSkKk()), 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3456, 0, 130546);
                        SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(24)), $composer4, 6);
                        $composer$iv = $composer4;
                        $composer2 = $composer4;
                        ButtonKt.Button(onRetry, null, false, null, ButtonDefaults.INSTANCE.m1607buttonColorsro_MJ88(teal, 0L, 0L, 0L, $composer4, (ButtonDefaults.$stable << 12) | 6, 14), null, null, null, null, ComposableSingletons$ExtractingScreenKt.INSTANCE.m6497getLambda1$app_debug(), $composer4, (($dirty2 >> 3) & 14) | 805306368, 494);
                        ComposerKt.sourceInformationMarkerEnd($composer4);
                        ComposerKt.sourceInformationMarkerEnd($composer4);
                        $composer4.endReplaceableGroup();
                        $composer4.endNode();
                        $composer4.endReplaceableGroup();
                        $composer4.endReplaceableGroup();
                        $composer4.endReplaceableGroup();
                        $composer3 = $composer4;
                    }
                }
                $this$Layout_u24lambda_u240$iv$iv2.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv2));
                $this$Layout_u24lambda_u240$iv$iv2.apply(Integer.valueOf(compositeKeyHash$iv$iv2), block$iv$iv$iv2);
                skippableUpdate$iv$iv$iv2.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer4)), $composer4, Integer.valueOf(($changed$iv$iv$iv2 >> 3) & 112));
                $composer4.startReplaceableGroup(2058660585);
                int i32 = ($changed$iv$iv$iv2 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer4, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                int i42 = ((390 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer4, -418254739, "C43@1465L215,49@1701L30,50@1752L205,56@1978L30,59@2128L35,57@2029L243:ExtractingScreen.kt#fpoywd");
                TextKt.m2466Text4IGK_g("Something went wrong", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), TextUnitKt.getSp(20), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 200070, 0, 131026);
                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), $composer4, 6);
                TextKt.m2466Text4IGK_g(((OnboardingExtractionState.Failure) extractionState).getMessage(), (Modifier) null, Color.INSTANCE.m3780getGray0d7_KjU(), TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, TextAlign.m5955boximpl(TextAlign.INSTANCE.m5962getCentere0LSkKk()), 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3456, 0, 130546);
                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(24)), $composer4, 6);
                $composer$iv = $composer4;
                $composer2 = $composer4;
                ButtonKt.Button(onRetry, null, false, null, ButtonDefaults.INSTANCE.m1607buttonColorsro_MJ88(teal, 0L, 0L, 0L, $composer4, (ButtonDefaults.$stable << 12) | 6, 14), null, null, null, null, ComposableSingletons$ExtractingScreenKt.INSTANCE.m6497getLambda1$app_debug(), $composer4, (($dirty2 >> 3) & 14) | 805306368, 494);
                ComposerKt.sourceInformationMarkerEnd($composer4);
                ComposerKt.sourceInformationMarkerEnd($composer4);
                $composer4.endReplaceableGroup();
                $composer4.endNode();
                $composer4.endReplaceableGroup();
                $composer4.endReplaceableGroup();
                $composer4.endReplaceableGroup();
                $composer3 = $composer4;
            } else {
                $composer$iv = $composer4;
                $composer2 = $composer4;
                $composer3 = $composer4;
                $composer3.startReplaceableGroup(1465710121);
                ComposerKt.sourceInformation($composer3, "73@2641L43,75@2724L332,85@3096L329,95@3443L1672");
                if (extractionState instanceof OnboardingExtractionState.Extracting) {
                    statusText = ((OnboardingExtractionState.Extracting) extractionState).getStatusText();
                } else {
                    statusText = extractionState instanceof OnboardingExtractionState.Retrying ? ((OnboardingExtractionState.Retrying) extractionState).getStatusText() : "Analyzing your document...";
                }
                InfiniteTransition infinite = InfiniteTransitionKt.rememberInfiniteTransition("pulse", $composer3, 6, 0);
                State scale$delegate = InfiniteTransitionKt.animateFloat(infinite, 0.85f, 1.1f, AnimationSpecKt.m126infiniteRepeatable9IiC70o$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_BLACK, 0, null, 6, null), RepeatMode.Reverse, 0L, 4, null), "scale", $composer3, InfiniteTransition.$stable | 25008 | (InfiniteRepeatableSpec.$stable << 9), 0);
                State alpha$delegate = InfiniteTransitionKt.animateFloat(infinite, 0.4f, 1.0f, AnimationSpecKt.m126infiniteRepeatable9IiC70o$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_BLACK, 0, null, 6, null), RepeatMode.Reverse, 0L, 4, null), "alpha", $composer3, InfiniteTransition.$stable | 25008 | (InfiniteRepeatableSpec.$stable << 9), 0);
                Alignment.Horizontal centerHorizontally2 = Alignment.INSTANCE.getCenterHorizontally();
                Modifier m566paddingVpY3zN4$default2 = PaddingKt.m566paddingVpY3zN4$default(Modifier.INSTANCE, Dp.m6092constructorimpl(32), 0.0f, 2, null);
                $composer3.startReplaceableGroup(-483455358);
                ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
                Arrangement.Vertical verticalArrangement$iv2 = Arrangement.INSTANCE.getTop();
                MeasurePolicy measurePolicy$iv3 = ColumnKt.columnMeasurePolicy(verticalArrangement$iv2, centerHorizontally2, $composer3, ((390 >> 3) & 14) | ((390 >> 3) & 112));
                int $changed$iv$iv3 = (390 << 3) & 112;
                $composer3.startReplaceableGroup(-1323940314);
                ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
                int compositeKeyHash$iv$iv3 = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
                CompositionLocalMap localMap$iv$iv3 = $composer3.getCurrentCompositionLocalMap();
                Function0 factory$iv$iv$iv6 = ComposeUiNode.INSTANCE.getConstructor();
                Function3 skippableUpdate$iv$iv$iv3 = LayoutKt.modifierMaterializerOf(m566paddingVpY3zN4$default2);
                int $changed$iv$iv$iv3 = (($changed$iv$iv3 << 9) & 7168) | 6;
                if (!($composer3.getApplier() instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                }
                $composer3.startReusableNode();
                if ($composer3.getInserting()) {
                    factory$iv$iv$iv2 = factory$iv$iv$iv6;
                    $composer3.createNode(factory$iv$iv$iv2);
                } else {
                    factory$iv$iv$iv2 = factory$iv$iv$iv6;
                    $composer3.useNode();
                }
                Composer $this$Layout_u24lambda_u240$iv$iv3 = Updater.m3280constructorimpl($composer3);
                Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv3, measurePolicy$iv3, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
                Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv3, localMap$iv$iv3, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
                Function2 block$iv$iv$iv3 = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
                if (!$this$Layout_u24lambda_u240$iv$iv3.getInserting() && Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv3.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv3))) {
                    skippableUpdate$iv$iv$iv3.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                    $composer3.startReplaceableGroup(2058660585);
                    int i5 = ($changed$iv$iv$iv3 >> 9) & 14;
                    ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                    ColumnScopeInstance columnScopeInstance3 = ColumnScopeInstance.INSTANCE;
                    int i6 = ((390 >> 6) & 112) | 6;
                    ComposerKt.sourceInformationMarkerStart($composer3, -418252573, "C99@3631L260,107@3913L30,109@3965L216,116@4203L29,118@4254L559,133@4835L30,135@4887L210:ExtractingScreen.kt#fpoywd");
                    Modifier alpha = AlphaKt.alpha(ScaleKt.scale(SizeKt.m613size3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(90)), ExtractingScreen$lambda$4$lambda$1(scale$delegate)), ExtractingScreen$lambda$4$lambda$2(alpha$delegate));
                    m3748copywmQWz5c = Color.m3748copywmQWz5c(teal, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(teal) : 0.2f, (r12 & 2) != 0 ? Color.m3756getRedimpl(teal) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(teal) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(teal) : 0.0f);
                    BoxKt.Box(BackgroundKt.m211backgroundbw27NRU$default(alpha, m3748copywmQWz5c, null, 2, null), $composer3, 0);
                    SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(24)), $composer3, 6);
                    TextKt.m2466Text4IGK_g("Building your profile", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), TextUnitKt.getSp(20), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 200070, 0, 131026);
                    SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(8)), $composer3, 6);
                    AnimatedContentKt.AnimatedContent(statusText, null, new Function1<AnimatedContentTransitionScope<String>, ContentTransform>() { // from class: com.vantedge.app.ui.screens.ExtractingScreenKt$ExtractingScreen$1$2$1
                        @Override // kotlin.jvm.functions.Function1
                        public final ContentTransform invoke(AnimatedContentTransitionScope<String> AnimatedContent) {
                            Intrinsics.checkNotNullParameter(AnimatedContent, "$this$AnimatedContent");
                            return AnimatedContentKt.togetherWith(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_NORMAL, 0, null, 6, null), 0.0f, 2, null), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_NORMAL, 0, null, 6, null), 0.0f, 2, null));
                        }
                    }, null, NotificationCompat.CATEGORY_STATUS, null, ComposableSingletons$ExtractingScreenKt.INSTANCE.m6498getLambda2$app_debug(), $composer3, 1597824, 42);
                    SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(20)), $composer3, 6);
                    m3748copywmQWz5c2 = Color.m3748copywmQWz5c(teal, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(teal) : 0.15f, (r12 & 2) != 0 ? Color.m3756getRedimpl(teal) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(teal) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(teal) : 0.0f);
                    ProgressIndicatorKt.m2088LinearProgressIndicator2cYBFYY(SizeKt.fillMaxWidth(Modifier.INSTANCE, 0.6f), teal, m3748copywmQWz5c2, 0, $composer3, 438, 8);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    ComposerKt.sourceInformationMarkerEnd($composer3);
                    $composer3.endReplaceableGroup();
                    $composer3.endNode();
                    $composer3.endReplaceableGroup();
                    $composer3.endReplaceableGroup();
                    $composer3.endReplaceableGroup();
                }
                $this$Layout_u24lambda_u240$iv$iv3.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv3));
                $this$Layout_u24lambda_u240$iv$iv3.apply(Integer.valueOf(compositeKeyHash$iv$iv3), block$iv$iv$iv3);
                skippableUpdate$iv$iv$iv3.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv3 >> 3) & 112));
                $composer3.startReplaceableGroup(2058660585);
                int i52 = ($changed$iv$iv$iv3 >> 9) & 14;
                ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
                ColumnScopeInstance columnScopeInstance32 = ColumnScopeInstance.INSTANCE;
                int i62 = ((390 >> 6) & 112) | 6;
                ComposerKt.sourceInformationMarkerStart($composer3, -418252573, "C99@3631L260,107@3913L30,109@3965L216,116@4203L29,118@4254L559,133@4835L30,135@4887L210:ExtractingScreen.kt#fpoywd");
                Modifier alpha2 = AlphaKt.alpha(ScaleKt.scale(SizeKt.m613size3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(90)), ExtractingScreen$lambda$4$lambda$1(scale$delegate)), ExtractingScreen$lambda$4$lambda$2(alpha$delegate));
                m3748copywmQWz5c = Color.m3748copywmQWz5c(teal, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(teal) : 0.2f, (r12 & 2) != 0 ? Color.m3756getRedimpl(teal) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(teal) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(teal) : 0.0f);
                BoxKt.Box(BackgroundKt.m211backgroundbw27NRU$default(alpha2, m3748copywmQWz5c, null, 2, null), $composer3, 0);
                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(24)), $composer3, 6);
                TextKt.m2466Text4IGK_g("Building your profile", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), TextUnitKt.getSp(20), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 200070, 0, 131026);
                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(8)), $composer3, 6);
                AnimatedContentKt.AnimatedContent(statusText, null, new Function1<AnimatedContentTransitionScope<String>, ContentTransform>() { // from class: com.vantedge.app.ui.screens.ExtractingScreenKt$ExtractingScreen$1$2$1
                    @Override // kotlin.jvm.functions.Function1
                    public final ContentTransform invoke(AnimatedContentTransitionScope<String> AnimatedContent) {
                        Intrinsics.checkNotNullParameter(AnimatedContent, "$this$AnimatedContent");
                        return AnimatedContentKt.togetherWith(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_NORMAL, 0, null, 6, null), 0.0f, 2, null), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_NORMAL, 0, null, 6, null), 0.0f, 2, null));
                    }
                }, null, NotificationCompat.CATEGORY_STATUS, null, ComposableSingletons$ExtractingScreenKt.INSTANCE.m6498getLambda2$app_debug(), $composer3, 1597824, 42);
                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(20)), $composer3, 6);
                m3748copywmQWz5c2 = Color.m3748copywmQWz5c(teal, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(teal) : 0.15f, (r12 & 2) != 0 ? Color.m3756getRedimpl(teal) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(teal) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(teal) : 0.0f);
                ProgressIndicatorKt.m2088LinearProgressIndicator2cYBFYY(SizeKt.fillMaxWidth(Modifier.INSTANCE, 0.6f), teal, m3748copywmQWz5c2, 0, $composer3, 438, 8);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                ComposerKt.sourceInformationMarkerEnd($composer3);
                $composer3.endReplaceableGroup();
                $composer3.endNode();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
                $composer3.endReplaceableGroup();
            }
            ComposerKt.sourceInformationMarkerEnd($composer3);
            ComposerKt.sourceInformationMarkerEnd($composer$iv);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer4.skipToGroupEnd();
            $composer2 = $composer4;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ExtractingScreenKt$ExtractingScreen$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i7) {
                    ExtractingScreenKt.ExtractingScreen(OnboardingExtractionState.this, onRetry, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    private static final float ExtractingScreen$lambda$4$lambda$1(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    private static final float ExtractingScreen$lambda$4$lambda$2(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }
}
