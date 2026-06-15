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
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.profileinstaller.ProfileVerifier;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.storage.UserPreferences;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EligibilityCheckerScreen.kt */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\u001a#\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007¢\u0006\u0002\u0010\u0006¨\u0006\u0007²\u0006\n\u0010\b\u001a\u00020\tX\u008a\u0084\u0002²\u0006\n\u0010\n\u001a\u00020\u000bX\u008a\u008e\u0002²\u0006\n\u0010\f\u001a\u00020\rX\u008a\u008e\u0002"}, d2 = {"EligibilityCheckerScreen", "", "userPreferences", "Lcom/vantedge/app/data/storage/UserPreferences;", "onBack", "Lkotlin/Function0;", "(Lcom/vantedge/app/data/storage/UserPreferences;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobDescriptionText", "", "isChecking", ""}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class EligibilityCheckerScreenKt {
    public static final void EligibilityCheckerScreen(final UserPreferences userPreferences, final Function0<Unit> onBack, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Intrinsics.checkNotNullParameter(userPreferences, "userPreferences");
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(-2061466934);
        ComposerKt.sourceInformation($composer2, "C(EligibilityCheckerScreen)P(1)21@720L39,22@790L31,23@844L34,25@884L1224:EligibilityCheckerScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-2061466934, $changed, -1, "com.vantedge.app.ui.screens.EligibilityCheckerScreen (EligibilityCheckerScreen.kt:20)");
        }
        SnapshotStateKt.collectAsState(userPreferences.getUserProfileFlow(), new UserProfile(null, null, null, null, null, null, null, null, null, null, null, 2047, null), null, $composer2, 72, 2);
        $composer2.startReplaceableGroup(620192897);
        ComposerKt.sourceInformation($composer2, "CC(remember):EligibilityCheckerScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("", null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState jobDescriptionText$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(620192951);
        ComposerKt.sourceInformation($composer2, "CC(remember):EligibilityCheckerScreen.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        final MutableState isChecking$delegate = (MutableState) value$iv2;
        $composer2.endReplaceableGroup();
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -1967307898, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EligibilityCheckerScreenKt$EligibilityCheckerScreen$1
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
                ComposerKt.sourceInformation($composer3, "C27@925L290:EligibilityCheckerScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1967307898, $changed2, -1, "com.vantedge.app.ui.screens.EligibilityCheckerScreen.<anonymous> (EligibilityCheckerScreen.kt:27)");
                    }
                    Function2<Composer, Integer, Unit> m6492getLambda1$app_debug = ComposableSingletons$EligibilityCheckerScreenKt.INSTANCE.m6492getLambda1$app_debug();
                    final Function0<Unit> function0 = onBack;
                    AppBarKt.TopAppBar(m6492getLambda1$app_debug, null, ComposableLambdaKt.composableLambda($composer3, 1250917068, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EligibilityCheckerScreenKt$EligibilityCheckerScreen$1.1
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
                            ComposerKt.sourceInformation($composer4, "C30@1048L135:EligibilityCheckerScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(1250917068, $changed3, -1, "com.vantedge.app.ui.screens.EligibilityCheckerScreen.<anonymous>.<anonymous> (EligibilityCheckerScreen.kt:30)");
                                }
                                IconButtonKt.IconButton(function0, null, false, null, null, ComposableSingletons$EligibilityCheckerScreenKt.INSTANCE.m6493getLambda2$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    }), null, null, null, null, $composer3, 390, 122);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, null, 0, 0L, 0L, null, ComposableLambdaKt.composableLambda($composer2, -600895013, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EligibilityCheckerScreenKt$EligibilityCheckerScreen$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:31:0x01f4  */
            /* JADX WARN: Removed duplicated region for block: B:34:0x027c  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x02dc  */
            /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:40:0x028c  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x0204  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.PaddingValues r59, androidx.compose.runtime.Composer r60, int r61) {
                /*
                    Method dump skipped, instructions count: 736
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EligibilityCheckerScreenKt$EligibilityCheckerScreen$2.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 805306416, 509);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EligibilityCheckerScreenKt$EligibilityCheckerScreen$3
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
                    EligibilityCheckerScreenKt.EligibilityCheckerScreen(UserPreferences.this, onBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    private static final UserProfile EligibilityCheckerScreen$lambda$0(State<UserProfile> state) {
        Object thisObj$iv = state.getValue();
        return (UserProfile) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EligibilityCheckerScreen$lambda$2(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    private static final boolean EligibilityCheckerScreen$lambda$5(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void EligibilityCheckerScreen$lambda$6(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }
}
