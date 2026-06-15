package com.vantedge.app.ui.screens;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.State;
import com.vantedge.app.domain.PipelineStep;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PipelineLoadingScreen.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003¢\u0006\u0002\u0010\u0004\u001a\r\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0002\u0010\u0006\u001a\u001f\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\u0010\n¨\u0006\u000b²\u0006\n\u0010\f\u001a\u00020\rX\u008a\u0084\u0002²\u0006\n\u0010\u000e\u001a\u00020\u000fX\u008a\u008e\u0002²\u0006\n\u0010\u0010\u001a\u00020\u0011X\u008a\u008e\u0002²\u0006\n\u0010\f\u001a\u00020\rX\u008a\u0084\u0002²\u0006\n\u0010\u0012\u001a\u00020\u000fX\u008a\u008e\u0002²\u0006\n\u0010\u0013\u001a\u00020\u000fX\u008a\u008e\u0002²\u0006\n\u0010\u0014\u001a\u00020\u0011X\u008a\u008e\u0002"}, d2 = {"FullPipelineLoadingScreen", "", "step", "Lcom/vantedge/app/domain/PipelineStep;", "(Lcom/vantedge/app/domain/PipelineStep;Landroidx/compose/runtime/Composer;I)V", "ImproveLoadingScreen", "(Landroidx/compose/runtime/Composer;I)V", "PipelineLoadingScreen", "loadingMode", "Lcom/vantedge/app/ui/screens/LoadingMode;", "(Lcom/vantedge/app/domain/PipelineStep;Lcom/vantedge/app/ui/screens/LoadingMode;Landroidx/compose/runtime/Composer;II)V", "app_debug", "alpha", "", "messageIndex", "", "visible", "", "visualStep", "subMessageIndex", "subVisible"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PipelineLoadingScreenKt {

    /* compiled from: PipelineLoadingScreen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoadingMode.values().length];
            try {
                iArr[LoadingMode.IMPROVE_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[LoadingMode.FULL_PIPELINE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void PipelineLoadingScreen(final PipelineStep step, final LoadingMode loadingMode, Composer $composer, final int $changed, final int i) {
        Intrinsics.checkNotNullParameter(step, "step");
        Composer $composer2 = $composer.startRestartGroup(1348863788);
        ComposerKt.sourceInformation($composer2, "C(PipelineLoadingScreen)P(1):PipelineLoadingScreen.kt#fpoywd");
        int $dirty = $changed;
        if ((i & 1) != 0) {
            $dirty |= 6;
        } else if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(step) ? 4 : 2;
        }
        int i2 = i & 2;
        if (i2 != 0) {
            $dirty |= 48;
        } else if (($changed & 112) == 0) {
            $dirty |= $composer2.changed(loadingMode) ? 32 : 16;
        }
        if (($dirty & 91) != 18 || !$composer2.getSkipping()) {
            if (i2 != 0) {
                loadingMode = LoadingMode.FULL_PIPELINE;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1348863788, $dirty, -1, "com.vantedge.app.ui.screens.PipelineLoadingScreen (PipelineLoadingScreen.kt:36)");
            }
            switch (WhenMappings.$EnumSwitchMapping$0[loadingMode.ordinal()]) {
                case 1:
                    $composer2.startReplaceableGroup(631367762);
                    ComposerKt.sourceInformation($composer2, "38@1367L22");
                    ImproveLoadingScreen($composer2, 0);
                    $composer2.endReplaceableGroup();
                    break;
                case 2:
                    $composer2.startReplaceableGroup(631367822);
                    ComposerKt.sourceInformation($composer2, "39@1427L31");
                    FullPipelineLoadingScreen(step, $composer2, $dirty & 14);
                    $composer2.endReplaceableGroup();
                    break;
                default:
                    $composer2.startReplaceableGroup(631367859);
                    $composer2.endReplaceableGroup();
                    break;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.PipelineLoadingScreenKt$PipelineLoadingScreen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i3) {
                    PipelineLoadingScreenKt.PipelineLoadingScreen(PipelineStep.this, loadingMode, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x045e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x02c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ImproveLoadingScreen(androidx.compose.runtime.Composer r91, final int r92) {
        /*
            Method dump skipped, instructions count: 1138
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.PipelineLoadingScreenKt.ImproveLoadingScreen(androidx.compose.runtime.Composer, int):void");
    }

    private static final float ImproveLoadingScreen$lambda$0(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int ImproveLoadingScreen$lambda$2(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ImproveLoadingScreen$lambda$3(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    private static final boolean ImproveLoadingScreen$lambda$5(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ImproveLoadingScreen$lambda$6(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:100:0x08c9  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0979  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x098a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x09e9  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0a63  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x097e  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x090b  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x081b  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0ac9  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x05d0  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0809  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0815  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void FullPipelineLoadingScreen(final com.vantedge.app.domain.PipelineStep r151, androidx.compose.runtime.Composer r152, final int r153) {
        /*
            Method dump skipped, instructions count: 2790
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.PipelineLoadingScreenKt.FullPipelineLoadingScreen(com.vantedge.app.domain.PipelineStep, androidx.compose.runtime.Composer, int):void");
    }

    private static final float FullPipelineLoadingScreen$lambda$9(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int FullPipelineLoadingScreen$lambda$11(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void FullPipelineLoadingScreen$lambda$12(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int FullPipelineLoadingScreen$lambda$15(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void FullPipelineLoadingScreen$lambda$16(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    private static final boolean FullPipelineLoadingScreen$lambda$18(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void FullPipelineLoadingScreen$lambda$19(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }
}
