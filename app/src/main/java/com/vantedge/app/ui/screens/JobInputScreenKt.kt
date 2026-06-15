package com.vantedge.app.ui.screens;

import android.content.Context;
import android.net.Uri;
import androidx.activity.compose.ActivityResultRegistryKt;
import androidx.activity.compose.ManagedActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.InfiniteRepeatableSpec;
import androidx.compose.animation.core.InfiniteTransition;
import androidx.compose.animation.core.InfiniteTransitionKt;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.SnackbarHostKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambda;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.profileinstaller.ProfileVerifier;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.GenerationMode;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.viewmodel.CycleUiState;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import com.vantedge.app.data.viewmodel.JobExtractionState;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: JobInputScreen.kt */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\u001a0\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0003ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u001aO\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0014H\u0007¢\u0006\u0002\u0010\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u0018²\u0006\n\u0010\u0019\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n\u0010\u001a\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n\u0010\u001b\u001a\u00020\u001cX\u008a\u0084\u0002²\u0006\n\u0010\u001d\u001a\u00020\u001eX\u008a\u0084\u0002²\u0006\n\u0010\u001f\u001a\u00020 X\u008a\u008e\u0002²\u0006\n\u0010!\u001a\u00020\u0001X\u008a\u008e\u0002²\u0006\n\u0010\"\u001a\u00020\u0001X\u008a\u008e\u0002²\u0006\n\u0010#\u001a\u00020\u0001X\u008a\u008e\u0002²\u0006\n\u0010$\u001a\u00020\u0001X\u008a\u008e\u0002²\u0006\n\u0010%\u001a\u00020&X\u008a\u008e\u0002"}, d2 = {"TAG", "", "AnimatedLoadingIndicator", "", "modifier", "Landroidx/compose/ui/Modifier;", "color", "Landroidx/compose/ui/graphics/Color;", "strokeWidth", "", "AnimatedLoadingIndicator-3IgeMak", "(Landroidx/compose/ui/Modifier;JFLandroidx/compose/runtime/Composer;II)V", JobInputScreenKt.TAG, "viewModel", "Lcom/vantedge/app/data/viewmodel/CycleViewModel;", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "mode", "Lcom/vantedge/app/data/model/GenerationMode;", "onNavigateBack", "Lkotlin/Function0;", "onNavigateToResult", "onNavigateToDesign", "(Lcom/vantedge/app/data/viewmodel/CycleViewModel;Lcom/vantedge/app/data/model/UserProfile;Lcom/vantedge/app/data/model/GenerationMode;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug", "rotation", "sweep", "uiState", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "extractionState", "Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "stage", "Lcom/vantedge/app/ui/screens/JobInputStage;", "jobTitle", "company", "jobDescription", "urlInput", "showUrlInput", ""}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class JobInputScreenKt {
    private static final String TAG = "JobInputScreen";

    /* compiled from: JobInputScreen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GenerationMode.values().length];
            try {
                iArr[GenerationMode.NEW_APPLICATION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[GenerationMode.QUICK_ANALYSIS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[GenerationMode.QUICK_GENERATE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[GenerationMode.IMPROVE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: AnimatedLoadingIndicator-3IgeMak, reason: not valid java name */
    public static final void m6549AnimatedLoadingIndicator3IgeMak(Modifier modifier, long color, float strokeWidth, Composer $composer, final int $changed, final int i) {
        Modifier modifier2;
        long j;
        float strokeWidth2;
        Object value$iv;
        Modifier modifier3;
        long color2;
        Composer $composer2 = $composer.startRestartGroup(464463892);
        ComposerKt.sourceInformation($composer2, "C(AnimatedLoadingIndicator)P(1,0:c#ui.graphics.Color)66@2580L44,67@2664L260,76@2961L258,86@3253L860,86@3225L888:JobInputScreen.kt#fpoywd");
        int $dirty = $changed;
        int i2 = i & 1;
        if (i2 != 0) {
            $dirty |= 6;
            modifier2 = modifier;
        } else if (($changed & 14) == 0) {
            modifier2 = modifier;
            $dirty |= $composer2.changed(modifier2) ? 4 : 2;
        } else {
            modifier2 = modifier;
        }
        int i3 = i & 2;
        if (i3 != 0) {
            $dirty |= 48;
            j = color;
        } else if (($changed & 112) == 0) {
            j = color;
            $dirty |= $composer2.changed(j) ? 32 : 16;
        } else {
            j = color;
        }
        int i4 = i & 4;
        if (i4 != 0) {
            $dirty |= KyberEngine.KyberPolyBytes;
            strokeWidth2 = strokeWidth;
        } else if (($changed & 896) == 0) {
            strokeWidth2 = strokeWidth;
            $dirty |= $composer2.changed(strokeWidth2) ? 256 : 128;
        } else {
            strokeWidth2 = strokeWidth;
        }
        if (($dirty & 731) == 146 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            color2 = j;
            modifier3 = modifier2;
        } else {
            Modifier.Companion modifier4 = i2 != 0 ? Modifier.INSTANCE : modifier2;
            long color3 = i3 != 0 ? ColorKt.Color(4278239141L) : j;
            float strokeWidth3 = i4 != 0 ? 8.0f : strokeWidth2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(464463892, $dirty, -1, "com.vantedge.app.ui.screens.AnimatedLoadingIndicator (JobInputScreen.kt:65)");
            }
            InfiniteTransition infiniteTransition = InfiniteTransitionKt.rememberInfiniteTransition("loader", $composer2, 6, 0);
            final State rotation$delegate = InfiniteTransitionKt.animateFloat(infiniteTransition, 0.0f, 360.0f, AnimationSpecKt.m126infiniteRepeatable9IiC70o$default(AnimationSpecKt.tween$default(1000, 0, EasingKt.getLinearEasing(), 2, null), RepeatMode.Restart, 0L, 4, null), "rotation", $composer2, InfiniteTransition.$stable | 25008 | (InfiniteRepeatableSpec.$stable << 9), 0);
            final State sweep$delegate = InfiniteTransitionKt.animateFloat(infiniteTransition, 60.0f, 300.0f, AnimationSpecKt.m126infiniteRepeatable9IiC70o$default(AnimationSpecKt.tween$default(ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED, 0, EasingKt.getLinearEasing(), 2, null), RepeatMode.Reverse, 0L, 4, null), "sweep", $composer2, InfiniteTransition.$stable | 25008 | (InfiniteRepeatableSpec.$stable << 9), 0);
            $composer2.startReplaceableGroup(188063838);
            ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
            boolean invalid$iv = (($dirty & 896) == 256) | (($dirty & 112) == 32) | $composer2.changed(rotation$delegate) | $composer2.changed(sweep$delegate);
            Object it$iv = $composer2.rememberedValue();
            if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
                final float f = strokeWidth3;
                final long j2 = color3;
                value$iv = new Function1<DrawScope, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$AnimatedLoadingIndicator$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DrawScope drawScope) {
                        invoke2(drawScope);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(DrawScope Canvas) {
                        long m3748copywmQWz5c;
                        float AnimatedLoadingIndicator_3IgeMak$lambda$0;
                        float AnimatedLoadingIndicator_3IgeMak$lambda$1;
                        Intrinsics.checkNotNullParameter(Canvas, "$this$Canvas");
                        float diameter = Size.m3577getMinDimensionimpl(Canvas.mo4298getSizeNHjbRc());
                        float radius = (diameter / 2.0f) - f;
                        long topLeft = OffsetKt.Offset(((Size.m3578getWidthimpl(Canvas.mo4298getSizeNHjbRc()) - diameter) / 2.0f) + f, ((Size.m3575getHeightimpl(Canvas.mo4298getSizeNHjbRc()) - diameter) / 2.0f) + f);
                        m3748copywmQWz5c = Color.m3748copywmQWz5c(r2, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r2) : 0.15f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r2) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r2) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(j2) : 0.0f);
                        float f2 = 2;
                        DrawScope.m4278drawArcyD3GUKo$default(Canvas, m3748copywmQWz5c, 0.0f, 360.0f, false, topLeft, SizeKt.Size(radius * f2, radius * f2), 0.0f, new Stroke(f, 0.0f, StrokeCap.INSTANCE.m4102getRoundKaPHkGw(), 0, null, 26, null), null, 0, 832, null);
                        long j3 = j2;
                        AnimatedLoadingIndicator_3IgeMak$lambda$0 = JobInputScreenKt.AnimatedLoadingIndicator_3IgeMak$lambda$0(rotation$delegate);
                        AnimatedLoadingIndicator_3IgeMak$lambda$1 = JobInputScreenKt.AnimatedLoadingIndicator_3IgeMak$lambda$1(sweep$delegate);
                        DrawScope.m4278drawArcyD3GUKo$default(Canvas, j3, AnimatedLoadingIndicator_3IgeMak$lambda$0 - 90.0f, AnimatedLoadingIndicator_3IgeMak$lambda$1, false, topLeft, SizeKt.Size(radius * f2, radius * f2), 0.0f, new Stroke(f, 0.0f, StrokeCap.INSTANCE.m4102getRoundKaPHkGw(), 0, null, 26, null), null, 0, 832, null);
                    }
                };
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            $composer2.endReplaceableGroup();
            CanvasKt.Canvas(modifier4, (Function1) value$iv, $composer2, $dirty & 14);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier3 = modifier4;
            color2 = color3;
            strokeWidth2 = strokeWidth3;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier5 = modifier3;
            final long j3 = color2;
            final float f2 = strokeWidth2;
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$AnimatedLoadingIndicator$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i5) {
                    JobInputScreenKt.m6549AnimatedLoadingIndicator3IgeMak(Modifier.this, j3, f2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float AnimatedLoadingIndicator_3IgeMak$lambda$0(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float AnimatedLoadingIndicator_3IgeMak$lambda$1(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    public static final void JobInputScreen(final CycleViewModel viewModel, final UserProfile profile, final GenerationMode mode, final Function0<Unit> onNavigateBack, final Function0<Unit> onNavigateToResult, final Function0<Unit> onNavigateToDesign, Composer $composer, final int $changed) {
        Object value$iv$iv$iv;
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        String str;
        final String buttonLabel;
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(onNavigateBack, "onNavigateBack");
        Intrinsics.checkNotNullParameter(onNavigateToResult, "onNavigateToResult");
        Intrinsics.checkNotNullParameter(onNavigateToDesign, "onNavigateToDesign");
        Composer $composer2 = $composer.startRestartGroup(774826028);
        ComposerKt.sourceInformation($composer2, "C(JobInputScreen)P(5,4!2,3)126@4416L16,127@4489L16,129@4542L7,131@4567L24,132@4611L32,138@4768L48,140@4838L52,141@4910L51,142@4988L58,143@5067L31,144@5123L34,150@5386L114,153@5505L111,156@5621L132,161@5846L1026,187@6897L2971,316@12207L15699:JobInputScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(774826028, $changed, -1, "com.vantedge.app.ui.screens.JobInputScreen (JobInputScreen.kt:125)");
        }
        final State uiState$delegate = SnapshotStateKt.collectAsState(viewModel.getUiState(), null, $composer2, 8, 1);
        State extractionState$delegate = SnapshotStateKt.collectAsState(viewModel.getJobExtractionState(), null, $composer2, 8, 1);
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) consume;
        $composer2.startReplaceableGroup(773894976);
        ComposerKt.sourceInformation($composer2, "CC(rememberCoroutineScope)489@20472L144:Effects.kt#9igjgp");
        $composer2.startReplaceableGroup(-492369756);
        ComposerKt.sourceInformation($composer2, "CC(remember):Composables.kt#9igjgp");
        Object it$iv$iv$iv = $composer2.rememberedValue();
        if (it$iv$iv$iv == Composer.INSTANCE.getEmpty()) {
            value$iv$iv$iv = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, $composer2));
            $composer2.updateRememberedValue(value$iv$iv$iv);
        } else {
            value$iv$iv$iv = it$iv$iv$iv;
        }
        $composer2.endReplaceableGroup();
        CompositionScopedCoroutineScopeCanceller wrapper$iv = (CompositionScopedCoroutineScopeCanceller) value$iv$iv$iv;
        final CoroutineScope scope = wrapper$iv.getCoroutineScope();
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1741349911);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = new SnackbarHostState();
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final SnackbarHostState snackbar = (SnackbarHostState) value$iv;
        $composer2.endReplaceableGroup();
        final long teal = ColorKt.Color(4278239141L);
        long bgDark = ColorKt.Color(4279045389L);
        final long cardDark = ColorKt.Color(4279900718L);
        $composer2.startReplaceableGroup(-1741349754);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object value$iv4 = $composer2.rememberedValue();
        if (value$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(JobInputStage.INPUT, null, 2, null);
            $composer2.updateRememberedValue(value$iv4);
        }
        final MutableState stage$delegate = (MutableState) value$iv4;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1741349684);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(viewModel.getSavedJobTitle(), null, 2, null);
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        final MutableState jobTitle$delegate = (MutableState) value$iv2;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1741349612);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object value$iv5 = $composer2.rememberedValue();
        if (value$iv5 == Composer.INSTANCE.getEmpty()) {
            value$iv5 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(viewModel.getSavedCompany(), null, 2, null);
            $composer2.updateRememberedValue(value$iv5);
        }
        final MutableState company$delegate = (MutableState) value$iv5;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1741349534);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object value$iv6 = $composer2.rememberedValue();
        if (value$iv6 == Composer.INSTANCE.getEmpty()) {
            value$iv6 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(viewModel.getSavedJobDescription(), null, 2, null);
            $composer2.updateRememberedValue(value$iv6);
        }
        final MutableState jobDescription$delegate = (MutableState) value$iv6;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1741349455);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object value$iv7 = $composer2.rememberedValue();
        if (value$iv7 == Composer.INSTANCE.getEmpty()) {
            value$iv7 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
            $composer2.updateRememberedValue(value$iv7);
        }
        final MutableState urlInput$delegate = (MutableState) value$iv7;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1741349399);
        ComposerKt.sourceInformation($composer2, "CC(remember):JobInputScreen.kt#9igjgp");
        Object it$iv3 = $composer2.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        final MutableState showUrlInput$delegate = (MutableState) value$iv3;
        $composer2.endReplaceableGroup();
        final boolean isLoading = JobInputScreen$lambda$3(uiState$delegate) instanceof CycleUiState.Loading;
        EffectsKt.LaunchedEffect(JobInputScreen$lambda$10(jobTitle$delegate), new JobInputScreenKt$JobInputScreen$1(viewModel, stage$delegate, jobTitle$delegate, null), $composer2, 64);
        EffectsKt.LaunchedEffect(JobInputScreen$lambda$13(company$delegate), new JobInputScreenKt$JobInputScreen$2(viewModel, stage$delegate, company$delegate, null), $composer2, 64);
        EffectsKt.LaunchedEffect(JobInputScreen$lambda$16(jobDescription$delegate), new JobInputScreenKt$JobInputScreen$3(viewModel, stage$delegate, jobDescription$delegate, null), $composer2, 64);
        EffectsKt.LaunchedEffect(JobInputScreen$lambda$4(extractionState$delegate), new JobInputScreenKt$JobInputScreen$4(snackbar, viewModel, extractionState$delegate, stage$delegate, jobTitle$delegate, company$delegate, jobDescription$delegate, null), $composer2, 64);
        final ManagedActivityResultLauncher fileLauncher = ActivityResultRegistryKt.rememberLauncherForActivityResult(new ActivityResultContracts.GetContent(), new Function1<Uri, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fileLauncher$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Uri uri) {
                invoke2(uri);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Uri uri) {
                if (uri == null) {
                    return;
                }
                jobTitle$delegate.setValue("");
                company$delegate.setValue("");
                jobDescription$delegate.setValue("");
                stage$delegate.setValue(JobInputStage.EXTRACTING);
                BuildersKt__Builders_commonKt.launch$default(CoroutineScope.this, null, null, new AnonymousClass1(context, uri, snackbar, viewModel, CoroutineScope.this, stage$delegate, null), 3, null);
            }

            /* compiled from: JobInputScreen.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
            @DebugMetadata(c = "com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fileLauncher$1$1", f = "JobInputScreen.kt", i = {1}, l = {217, 223, 254}, m = "invokeSuspend", n = {"mimeType"}, s = {"L$0"})
            /* renamed from: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fileLauncher$1$1, reason: invalid class name */
            static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ Context $context;
                final /* synthetic */ CoroutineScope $scope;
                final /* synthetic */ SnackbarHostState $snackbar;
                final /* synthetic */ MutableState<JobInputStage> $stage$delegate;
                final /* synthetic */ Uri $uri;
                final /* synthetic */ CycleViewModel $viewModel;
                Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                AnonymousClass1(Context context, Uri uri, SnackbarHostState snackbarHostState, CycleViewModel cycleViewModel, CoroutineScope coroutineScope, MutableState<JobInputStage> mutableState, Continuation<? super AnonymousClass1> continuation) {
                    super(2, continuation);
                    this.$context = context;
                    this.$uri = uri;
                    this.$snackbar = snackbarHostState;
                    this.$viewModel = cycleViewModel;
                    this.$scope = coroutineScope;
                    this.$stage$delegate = mutableState;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new AnonymousClass1(this.$context, this.$uri, this.$snackbar, this.$viewModel, this.$scope, this.$stage$delegate, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:13:0x00eb  */
                /* JADX WARN: Removed duplicated region for block: B:21:0x010a  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 318
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fileLauncher$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static final void invokeSuspend$lambda$1(CoroutineScope $scope, SnackbarHostState $snackbar, MutableState $stage$delegate, Exception e) {
                    BuildersKt__Builders_commonKt.launch$default($scope, null, null, new JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1($snackbar, e, $stage$delegate, null), 3, null);
                }
            }
        }, $composer2, 8);
        switch (WhenMappings.$EnumSwitchMapping$0[mode.ordinal()]) {
            case 1:
                str = "New Application";
                break;
            case 2:
                str = "Quick Analysis";
                break;
            case 3:
                str = "Quick Generate";
                break;
            case 4:
                str = "Improve Application";
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        final String screenTitle = str;
        if (isLoading && mode == GenerationMode.QUICK_GENERATE) {
            buttonLabel = "Generating...";
        } else if (isLoading && mode == GenerationMode.IMPROVE) {
            buttonLabel = "Re-analysing...";
        } else if (isLoading) {
            buttonLabel = "Analysing...";
        } else if (JobInputScreen$lambda$7(stage$delegate) == JobInputStage.REVIEWING) {
            buttonLabel = "Confirm & Analyze";
        } else if (mode == GenerationMode.QUICK_GENERATE) {
            buttonLabel = "Generate CV & Cover Letter";
        } else if (mode == GenerationMode.QUICK_ANALYSIS) {
            buttonLabel = "Analyse";
        } else {
            buttonLabel = mode == GenerationMode.IMPROVE ? "Re-Analyse & Improve" : "Analyse & Continue";
        }
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, 216266216, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$5
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
                ComposerKt.sourceInformation($composer3, "C326@12653L42,319@12281L428:JobInputScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(216266216, $changed2, -1, "com.vantedge.app.ui.screens.JobInputScreen.<anonymous> (JobInputScreen.kt:319)");
                    }
                    final String str2 = screenTitle;
                    ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer3, 793343404, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$5.1
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
                            ComposerKt.sourceInformation($composer4, "C320@12318L44:JobInputScreen.kt#fpoywd");
                            if (($changed3 & 11) == 2 && $composer4.getSkipping()) {
                                $composer4.skipToGroupEnd();
                                return;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(793343404, $changed3, -1, "com.vantedge.app.ui.screens.JobInputScreen.<anonymous>.<anonymous> (JobInputScreen.kt:320)");
                            }
                            TextKt.m2466Text4IGK_g(str2, (Modifier) null, ColorKt.Color(4292927712L), 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, KyberEngine.KyberPolyBytes, 0, 131066);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    });
                    final Function0<Unit> function0 = onNavigateBack;
                    AppBarKt.TopAppBar(composableLambda, null, ComposableLambdaKt.composableLambda($composer3, -520486482, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$5.2
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
                            ComposerKt.sourceInformation($composer4, "C322@12421L169:JobInputScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-520486482, $changed3, -1, "com.vantedge.app.ui.screens.JobInputScreen.<anonymous>.<anonymous> (JobInputScreen.kt:322)");
                                }
                                IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$JobInputScreenKt.INSTANCE.m6503getLambda1$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), null, null, TopAppBarDefaults.INSTANCE.m2625topAppBarColorszjMxDiM(cardDark, 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30), null, $composer3, 390, 90);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, ComposableLambdaKt.composableLambda($composer2, 725045030, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$6
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer $composer3, int $changed2) {
                ComposerKt.sourceInformation($composer3, "C329@12746L22:JobInputScreen.kt#fpoywd");
                if (($changed2 & 11) == 2 && $composer3.getSkipping()) {
                    $composer3.skipToGroupEnd();
                    return;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(725045030, $changed2, -1, "com.vantedge.app.ui.screens.JobInputScreen.<anonymous> (JobInputScreen.kt:329)");
                }
                SnackbarHostKt.SnackbarHost(SnackbarHostState.this, null, null, $composer3, 6, 6);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }), null, 0, bgDark, 0L, null, ComposableLambdaKt.composableLambda($composer2, -965028867, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$7

            /* compiled from: JobInputScreen.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[GenerationMode.values().length];
                    try {
                        iArr[GenerationMode.NEW_APPLICATION.ordinal()] = 1;
                    } catch (NoSuchFieldError e) {
                    }
                    try {
                        iArr[GenerationMode.QUICK_ANALYSIS.ordinal()] = 2;
                    } catch (NoSuchFieldError e2) {
                    }
                    try {
                        iArr[GenerationMode.QUICK_GENERATE.ordinal()] = 3;
                    } catch (NoSuchFieldError e3) {
                    }
                    try {
                        iArr[GenerationMode.IMPROVE.ordinal()] = 4;
                    } catch (NoSuchFieldError e4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

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

            /* JADX WARN: Removed duplicated region for block: B:102:0x0b06  */
            /* JADX WARN: Removed duplicated region for block: B:105:0x0b8d  */
            /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:109:0x0a3a  */
            /* JADX WARN: Removed duplicated region for block: B:110:0x07d3  */
            /* JADX WARN: Removed duplicated region for block: B:114:0x08ec  */
            /* JADX WARN: Removed duplicated region for block: B:115:0x05c0  */
            /* JADX WARN: Removed duplicated region for block: B:117:0x04a0  */
            /* JADX WARN: Removed duplicated region for block: B:119:0x0388  */
            /* JADX WARN: Removed duplicated region for block: B:121:0x029f  */
            /* JADX WARN: Removed duplicated region for block: B:122:0x01e7  */
            /* JADX WARN: Removed duplicated region for block: B:123:0x01ec  */
            /* JADX WARN: Removed duplicated region for block: B:124:0x01f1  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x01dc  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x01e2  */
            /* JADX WARN: Removed duplicated region for block: B:36:0x023e  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x02b9  */
            /* JADX WARN: Removed duplicated region for block: B:44:0x0378  */
            /* JADX WARN: Removed duplicated region for block: B:47:0x03e2  */
            /* JADX WARN: Removed duplicated region for block: B:52:0x0490  */
            /* JADX WARN: Removed duplicated region for block: B:55:0x0505  */
            /* JADX WARN: Removed duplicated region for block: B:60:0x05b0  */
            /* JADX WARN: Removed duplicated region for block: B:63:0x0628  */
            /* JADX WARN: Removed duplicated region for block: B:76:0x07c3  */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0904  */
            /* JADX WARN: Removed duplicated region for block: B:83:0x096d  */
            /* JADX WARN: Removed duplicated region for block: B:88:0x09e2  */
            /* JADX WARN: Removed duplicated region for block: B:91:0x0a41  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.PaddingValues r143, androidx.compose.runtime.Composer r144, int r145) {
                /*
                    Method dump skipped, instructions count: 2974
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$7.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 806882352, 437);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$8
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
                    JobInputScreenKt.JobInputScreen(CycleViewModel.this, profile, mode, onNavigateBack, onNavigateToResult, onNavigateToDesign, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CycleUiState JobInputScreen$lambda$3(State<? extends CycleUiState> state) {
        Object thisObj$iv = state.getValue();
        return (CycleUiState) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JobExtractionState JobInputScreen$lambda$4(State<? extends JobExtractionState> state) {
        Object thisObj$iv = state.getValue();
        return (JobExtractionState) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JobInputStage JobInputScreen$lambda$7(MutableState<JobInputStage> mutableState) {
        MutableState<JobInputStage> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String JobInputScreen$lambda$10(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String JobInputScreen$lambda$13(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String JobInputScreen$lambda$16(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String JobInputScreen$lambda$19(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean JobInputScreen$lambda$22(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void JobInputScreen$lambda$23(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void JobInputScreen$fetchUrl(CoroutineScope scope, MutableState<String> mutableState, MutableState<String> mutableState2, MutableState<String> mutableState3, MutableState<JobInputStage> mutableState4, CycleViewModel $viewModel, SnackbarHostState snackbar, MutableState<Boolean> mutableState5, MutableState<String> mutableState6, String url) {
        mutableState.setValue("");
        mutableState2.setValue("");
        mutableState3.setValue("");
        mutableState4.setValue(JobInputStage.EXTRACTING);
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new JobInputScreenKt$JobInputScreen$fetchUrl$1($viewModel, snackbar, url, mutableState5, mutableState6, mutableState4, null), 3, null);
    }
}
