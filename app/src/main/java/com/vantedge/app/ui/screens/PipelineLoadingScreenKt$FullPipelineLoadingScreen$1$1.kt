package com.vantedge.app.ui.screens;

import androidx.compose.runtime.MutableState;
import com.vantedge.app.domain.PipelineStep;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: PipelineLoadingScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.PipelineLoadingScreenKt$FullPipelineLoadingScreen$1$1", f = "PipelineLoadingScreen.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class PipelineLoadingScreenKt$FullPipelineLoadingScreen$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ PipelineStep $step;
    final /* synthetic */ MutableState<Integer> $visualStep$delegate;
    int label;

    /* compiled from: PipelineLoadingScreen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PipelineStep.values().length];
            try {
                iArr[PipelineStep.ANALYSING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[PipelineStep.GENERATING_CV.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[PipelineStep.GENERATING_COVER_LETTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[PipelineStep.APPLYING_DESIGN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PipelineLoadingScreenKt$FullPipelineLoadingScreen$1$1(PipelineStep pipelineStep, MutableState<Integer> mutableState, Continuation<? super PipelineLoadingScreenKt$FullPipelineLoadingScreen$1$1> continuation) {
        super(2, continuation);
        this.$step = pipelineStep;
        this.$visualStep$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PipelineLoadingScreenKt$FullPipelineLoadingScreen$1$1(this.$step, this.$visualStep$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((PipelineLoadingScreenKt$FullPipelineLoadingScreen$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                MutableState<Integer> mutableState = this.$visualStep$delegate;
                switch (WhenMappings.$EnumSwitchMapping$0[this.$step.ordinal()]) {
                    case 1:
                        i = 0;
                        break;
                    case 2:
                        i = 1;
                        break;
                    case 3:
                        i = 2;
                        break;
                    case 4:
                        i = 3;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                PipelineLoadingScreenKt.FullPipelineLoadingScreen$lambda$12(mutableState, i);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
