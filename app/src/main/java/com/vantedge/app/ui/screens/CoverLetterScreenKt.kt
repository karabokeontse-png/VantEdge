package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.profileinstaller.ProfileVerifier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CoverLetterScreen.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a5\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0007¢\u0006\u0002\u0010\u0007¨\u0006\b²\u0006\n\u0010\t\u001a\u00020\u0006X\u008a\u008e\u0002²\u0006\n\u0010\n\u001a\u00020\u0006X\u008a\u008e\u0002"}, d2 = {"CoverLetterScreen", "", "onNavigateBack", "Lkotlin/Function0;", "onOpenPreview", "Lkotlin/Function2;", "", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Landroidx/compose/runtime/Composer;I)V", "app_debug", "jobDescription", "generated"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CoverLetterScreenKt {
    public static final void CoverLetterScreen(final Function0<Unit> onNavigateBack, final Function2<? super String, ? super String, Unit> onOpenPreview, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Composer $composer2;
        Intrinsics.checkNotNullParameter(onNavigateBack, "onNavigateBack");
        Intrinsics.checkNotNullParameter(onOpenPreview, "onOpenPreview");
        Composer $composer3 = $composer.startRestartGroup(93640577);
        ComposerKt.sourceInformation($composer3, "C(CoverLetterScreen)17@506L31,18@559L31,20@596L1426:CoverLetterScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changedInstance(onNavigateBack) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changedInstance(onOpenPreview) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(93640577, $dirty2, -1, "com.vantedge.app.ui.screens.CoverLetterScreen (CoverLetterScreen.kt:15)");
            }
            $composer3.startReplaceableGroup(72415634);
            ComposerKt.sourceInformation($composer3, "CC(remember):CoverLetterScreen.kt#9igjgp");
            Object it$iv = $composer3.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState jobDescription$delegate = (MutableState) value$iv;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(72415687);
            ComposerKt.sourceInformation($composer3, "CC(remember):CoverLetterScreen.kt#9igjgp");
            Object it$iv2 = $composer3.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
                $composer3.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            final MutableState generated$delegate = (MutableState) value$iv2;
            $composer3.endReplaceableGroup();
            $composer2 = $composer3;
            ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer3, 1328827965, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CoverLetterScreenKt$CoverLetterScreen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer4, int $changed2) {
                    ComposerKt.sourceInformation($composer4, "C22@637L294:CoverLetterScreen.kt#fpoywd");
                    if (($changed2 & 11) != 2 || !$composer4.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1328827965, $changed2, -1, "com.vantedge.app.ui.screens.CoverLetterScreen.<anonymous> (CoverLetterScreen.kt:22)");
                        }
                        Function2<Composer, Integer, Unit> m6468getLambda1$app_debug = ComposableSingletons$CoverLetterScreenKt.INSTANCE.m6468getLambda1$app_debug();
                        final Function0<Unit> function0 = onNavigateBack;
                        AppBarKt.TopAppBar(m6468getLambda1$app_debug, null, ComposableLambdaKt.composableLambda($composer4, 1729782915, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CoverLetterScreenKt$CoverLetterScreen$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                                invoke(composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(Composer $composer5, int $changed3) {
                                ComposerKt.sourceInformation($composer5, "C25@756L143:CoverLetterScreen.kt#fpoywd");
                                if (($changed3 & 11) != 2 || !$composer5.getSkipping()) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(1729782915, $changed3, -1, "com.vantedge.app.ui.screens.CoverLetterScreen.<anonymous>.<anonymous> (CoverLetterScreen.kt:25)");
                                    }
                                    IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$CoverLetterScreenKt.INSTANCE.m6469getLambda2$app_debug(), $composer5, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                        return;
                                    }
                                    return;
                                }
                                $composer5.skipToGroupEnd();
                            }
                        }), null, null, null, null, $composer4, 390, 122);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer4.skipToGroupEnd();
                }
            }), null, null, null, 0, 0L, 0L, null, ComposableLambdaKt.composableLambda($composer3, 2013976850, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CoverLetterScreenKt$CoverLetterScreen$2
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

                /* JADX WARN: Removed duplicated region for block: B:31:0x01b1  */
                /* JADX WARN: Removed duplicated region for block: B:34:0x0231  */
                /* JADX WARN: Removed duplicated region for block: B:37:0x028d  */
                /* JADX WARN: Removed duplicated region for block: B:39:0x0290  */
                /* JADX WARN: Removed duplicated region for block: B:48:0x033a  */
                /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:51:0x0241  */
                /* JADX WARN: Removed duplicated region for block: B:52:0x01c1  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.PaddingValues r60, androidx.compose.runtime.Composer r61, int r62) {
                    /*
                        Method dump skipped, instructions count: 830
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.CoverLetterScreenKt$CoverLetterScreen$2.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, 805306416, 509);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.CoverLetterScreenKt$CoverLetterScreen$3
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
                    CoverLetterScreenKt.CoverLetterScreen(onNavigateBack, onOpenPreview, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String CoverLetterScreen$lambda$1(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String CoverLetterScreen$lambda$4(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }
}
