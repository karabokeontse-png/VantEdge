package com.vantedge.app.ui.screens;

import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.SnackbarHostKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import androidx.profileinstaller.ProfileVerifier;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.ui.theme.AppColors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.bouncycastle.i18n.MessageBundle;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: FinalReviewScreen.kt */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\u001aM\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007¢\u0006\u0002\u0010\n\u001aT\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u001c\u0010\u0012\u001a\u0018\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u0013¢\u0006\u0002\b\u0015¢\u0006\u0002\b\u0016H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a\u001d\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005H\u0003¢\u0006\u0002\u0010\u001c\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u001d²\u0006\n\u0010\u001e\u001a\u00020\u001fX\u008a\u008e\u0002"}, d2 = {"FinalReviewScreen", "", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "errorMessage", "", "onConfirm", "Lkotlin/Function0;", "onBack", "onClearError", "(Lcom/vantedge/app/data/model/UserProfile;Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;II)V", "SummaryCard", MessageBundle.TITLE_ENTRY, "teal", "Landroidx/compose/ui/graphics/Color;", "cardDark", "badge", "", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "SummaryCard-DTcfvLk", "(Ljava/lang/String;JJLjava/lang/Integer;Lkotlin/jvm/functions/Function3;Landroidx/compose/runtime/Composer;II)V", "SummaryRow", AnnotatedPrivateKey.LABEL, "value", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/runtime/Composer;I)V", "app_debug", "showContent", ""}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class FinalReviewScreenKt {
    public static final void FinalReviewScreen(final UserProfile profile, String errorMessage, final Function0<Unit> onConfirm, final Function0<Unit> onBack, Function0<Unit> function0, Composer $composer, final int $changed, final int i) {
        String errorMessage2;
        Function0 onClearError;
        Object value$iv;
        boolean z;
        Object value$iv2;
        FinalReviewScreenKt$FinalReviewScreen$2$1 value$iv3;
        FinalReviewScreenKt$FinalReviewScreen$3$1 value$iv4;
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(onConfirm, "onConfirm");
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(-1734577398);
        ComposerKt.sourceInformation($composer2, "C(FinalReviewScreen)P(4!1,3)35@1339L32,36@1395L34,41@1561L22,41@1540L43,43@1618L164,43@1589L193,50@1788L12061:FinalReviewScreen.kt#fpoywd");
        if ((i & 2) != 0) {
            errorMessage2 = null;
        } else {
            errorMessage2 = errorMessage;
        }
        if ((i & 16) == 0) {
            onClearError = function0;
        } else {
            onClearError = new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$1
                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }
            };
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1734577398, $changed, -1, "com.vantedge.app.ui.screens.FinalReviewScreen (FinalReviewScreen.kt:34)");
        }
        $composer2.startReplaceableGroup(-134894078);
        ComposerKt.sourceInformation($composer2, "CC(remember):FinalReviewScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = new SnackbarHostState();
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final SnackbarHostState snackbarHostState = (SnackbarHostState) value$iv;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-134894022);
        ComposerKt.sourceInformation($composer2, "CC(remember):FinalReviewScreen.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            z = false;
            value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv2);
        } else {
            z = false;
            value$iv2 = it$iv2;
        }
        final MutableState showContent$delegate = (MutableState) value$iv2;
        $composer2.endReplaceableGroup();
        final long teal = ColorKt.Color(4278239141L);
        final long cardDark = ColorKt.Color(4279900718L);
        final long subtle = ColorKt.Color(4288585374L);
        Unit unit = Unit.INSTANCE;
        $composer2.startReplaceableGroup(-134893856);
        ComposerKt.sourceInformation($composer2, "CC(remember):FinalReviewScreen.kt#9igjgp");
        Object it$iv3 = $composer2.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = new FinalReviewScreenKt$FinalReviewScreen$2$1(showContent$delegate, null);
            $composer2.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        $composer2.endReplaceableGroup();
        EffectsKt.LaunchedEffect(unit, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) value$iv3, $composer2, 70);
        $composer2.startReplaceableGroup(-134893799);
        ComposerKt.sourceInformation($composer2, "CC(remember):FinalReviewScreen.kt#9igjgp");
        boolean invalid$iv = ((((($changed & 112) ^ 48) <= 32 || !$composer2.changed(errorMessage2)) && ($changed & 48) != 32) ? z : true) | (((((57344 & $changed) ^ 24576) <= 16384 || !$composer2.changedInstance(onClearError)) && ($changed & 24576) != 16384) ? z : true);
        Object it$iv4 = $composer2.rememberedValue();
        if (invalid$iv || it$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = new FinalReviewScreenKt$FinalReviewScreen$3$1(errorMessage2, snackbarHostState, onClearError, null);
            $composer2.updateRememberedValue(value$iv4);
        } else {
            value$iv4 = it$iv4;
        }
        $composer2.endReplaceableGroup();
        EffectsKt.LaunchedEffect(errorMessage2, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) value$iv4, $composer2, (($changed >> 3) & 14) | 64);
        final Function0 onClearError2 = onClearError;
        final String errorMessage3 = errorMessage2;
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -1656306226, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$4
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
                ComposerKt.sourceInformation($composer3, "C71@2564L92,53@1876L794:FinalReviewScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1656306226, $changed2, -1, "com.vantedge.app.ui.screens.FinalReviewScreen.<anonymous> (FinalReviewScreen.kt:53)");
                    }
                    Function2<Composer, Integer, Unit> m6499getLambda1$app_debug = ComposableSingletons$FinalReviewScreenKt.INSTANCE.m6499getLambda1$app_debug();
                    final Function0<Unit> function02 = onBack;
                    AppBarKt.TopAppBar(m6499getLambda1$app_debug, null, ComposableLambdaKt.composableLambda($composer3, 488070472, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$4.1
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
                            ComposerKt.sourceInformation($composer4, "C63@2219L282:FinalReviewScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(488070472, $changed3, -1, "com.vantedge.app.ui.screens.FinalReviewScreen.<anonymous>.<anonymous> (FinalReviewScreen.kt:63)");
                                }
                                IconButtonKt.IconButton(function02, null, false, null, null, ComposableSingletons$FinalReviewScreenKt.INSTANCE.m6500getLambda2$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), null, null, TopAppBarDefaults.INSTANCE.m2625topAppBarColorszjMxDiM(AppColors.INSTANCE.m6570getBackground0d7_KjU(), 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30), null, $composer3, 390, 90);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), ComposableLambdaKt.composableLambda($composer2, -1463375025, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:24:0x0222  */
            /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.runtime.Composer r51, int r52) {
                /*
                    Method dump skipped, instructions count: 550
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$5.invoke(androidx.compose.runtime.Composer, int):void");
            }
        }), ComposableLambdaKt.composableLambda($composer2, -1270443824, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$6
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer $composer3, int $changed2) {
                ComposerKt.sourceInformation($composer3, "C77@2719L313:FinalReviewScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1270443824, $changed2, -1, "com.vantedge.app.ui.screens.FinalReviewScreen.<anonymous> (FinalReviewScreen.kt:77)");
                    }
                    SnackbarHostKt.SnackbarHost(SnackbarHostState.this, null, ComposableSingletons$FinalReviewScreenKt.INSTANCE.m6502getLambda4$app_debug(), $composer3, 390, 2);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, 0, AppColors.INSTANCE.m6570getBackground0d7_KjU(), 0L, null, ComposableLambdaKt.composableLambda($composer2, -1872804071, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(final PaddingValues padding, Composer $composer3, int $changed2) {
                boolean FinalReviewScreen$lambda$2;
                Intrinsics.checkNotNullParameter(padding, "padding");
                ComposerKt.sourceInformation($composer3, "C117@4161L9682:FinalReviewScreen.kt#fpoywd");
                int $dirty = $changed2;
                if (($changed2 & 14) == 0) {
                    $dirty |= $composer3.changed(padding) ? 4 : 2;
                }
                int $dirty2 = $dirty;
                if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1872804071, $dirty2, -1, "com.vantedge.app.ui.screens.FinalReviewScreen.<anonymous> (FinalReviewScreen.kt:117)");
                    }
                    FinalReviewScreen$lambda$2 = FinalReviewScreenKt.FinalReviewScreen$lambda$2(showContent$delegate);
                    EnterTransition fadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(500, 0, null, 6, null), 0.0f, 2, null);
                    final long j = teal;
                    final long j2 = cardDark;
                    final UserProfile userProfile = profile;
                    final long j3 = subtle;
                    AnimatedVisibilityKt.AnimatedVisibility(FinalReviewScreen$lambda$2, (Modifier) null, fadeIn$default, (ExitTransition) null, (String) null, ComposableLambdaKt.composableLambda($composer3, 764361537, true, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$7.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
                            invoke(animatedVisibilityScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:17:0x020d  */
                        /* JADX WARN: Removed duplicated region for block: B:20:0x0219  */
                        /* JADX WARN: Removed duplicated region for block: B:28:0x0391  */
                        /* JADX WARN: Removed duplicated region for block: B:31:0x0428  */
                        /* JADX WARN: Removed duplicated region for block: B:34:0x0470  */
                        /* JADX WARN: Removed duplicated region for block: B:37:0x04b8  */
                        /* JADX WARN: Removed duplicated region for block: B:40:0x0518  */
                        /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:46:0x021f  */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public final void invoke(androidx.compose.animation.AnimatedVisibilityScope r103, androidx.compose.runtime.Composer r104, int r105) {
                            /*
                                Method dump skipped, instructions count: 1308
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$7.AnonymousClass1.invoke(androidx.compose.animation.AnimatedVisibilityScope, androidx.compose.runtime.Composer, int):void");
                        }
                    }), $composer3, 196992, 26);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), $composer2, 806882736, 433);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$8
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
                    FinalReviewScreenKt.FinalReviewScreen(UserProfile.this, errorMessage3, onConfirm, onBack, onClearError2, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean FinalReviewScreen$lambda$2(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void FinalReviewScreen$lambda$3(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: SummaryCard-DTcfvLk, reason: not valid java name */
    public static final void m6547SummaryCardDTcfvLk(final String title, final long teal, final long cardDark, Integer badge, final Function3<? super ColumnScope, ? super Composer, ? super Integer, Unit> function3, Composer $composer, final int $changed, final int i) {
        Integer num;
        Integer badge2;
        Composer $composer2;
        Composer $composer3 = $composer.startRestartGroup(-1173297702);
        ComposerKt.sourceInformation($composer3, "C(SummaryCard)P(4,3:c#ui.graphics.Color,1:c#ui.graphics.Color)337@14186L37,335@14106L1399:FinalReviewScreen.kt#fpoywd");
        int $dirty = $changed;
        if ((i & 1) != 0) {
            $dirty |= 6;
        } else if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(title) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty |= 48;
        } else if (($changed & 112) == 0) {
            $dirty |= $composer3.changed(teal) ? 32 : 16;
        }
        if ((i & 4) != 0) {
            $dirty |= KyberEngine.KyberPolyBytes;
        } else if (($changed & 896) == 0) {
            $dirty |= $composer3.changed(cardDark) ? 256 : 128;
        }
        int i2 = i & 8;
        if (i2 != 0) {
            $dirty |= 3072;
            num = badge;
        } else if (($changed & 7168) == 0) {
            num = badge;
            $dirty |= $composer3.changed(num) ? 2048 : 1024;
        } else {
            num = badge;
        }
        if ((i & 16) != 0) {
            $dirty |= 24576;
        } else if ((57344 & $changed) == 0) {
            $dirty |= $composer3.changedInstance(function3) ? 16384 : 8192;
        }
        if ((46811 & $dirty) == 9362 && $composer3.getSkipping()) {
            $composer3.skipToGroupEnd();
            badge2 = num;
            $composer2 = $composer3;
        } else {
            badge2 = i2 != 0 ? null : num;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1173297702, $dirty, -1, "com.vantedge.app.ui.screens.SummaryCard (FinalReviewScreen.kt:334)");
            }
            final Integer num2 = badge2;
            $composer2 = $composer3;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(14)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(cardDark, 0L, 0L, 0L, $composer3, (($dirty >> 6) & 14) | (CardDefaults.$stable << 12), 14), null, null, ComposableLambdaKt.composableLambda($composer3, 1456158668, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$SummaryCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num3) {
                    invoke(columnScope, composer, num3.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:24:0x01ea  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x01f6  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0229  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x0305  */
                /* JADX WARN: Removed duplicated region for block: B:51:0x04f6  */
                /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:59:0x023f  */
                /* JADX WARN: Removed duplicated region for block: B:60:0x01fa  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r90, androidx.compose.runtime.Composer r91, int r92) {
                    /*
                        Method dump skipped, instructions count: 1274
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.FinalReviewScreenKt$SummaryCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer3, 196614, 24);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            final Integer num3 = badge2;
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.FinalReviewScreenKt$SummaryCard$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num4) {
                    invoke(composer, num4.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i3) {
                    FinalReviewScreenKt.m6547SummaryCardDTcfvLk(title, teal, cardDark, num3, function3, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0114, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1.rememberedValue(), java.lang.Integer.valueOf(r55)) != false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void SummaryRow(final java.lang.String r73, final java.lang.String r74, androidx.compose.runtime.Composer r75, final int r76) {
        /*
            Method dump skipped, instructions count: 621
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.FinalReviewScreenKt.SummaryRow(java.lang.String, java.lang.String, androidx.compose.runtime.Composer, int):void");
    }
}
