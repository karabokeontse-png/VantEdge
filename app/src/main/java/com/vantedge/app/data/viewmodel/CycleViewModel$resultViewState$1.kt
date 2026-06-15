package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationCycle;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u008a@"}, d2 = {"<anonymous>", "Lcom/vantedge/app/data/viewmodel/ResultViewState;", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "uiState", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "restoration", "Lcom/vantedge/app/data/viewmodel/RestorationState;", "improvementBase"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$resultViewState$1", f = "CycleViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$resultViewState$1 extends SuspendLambda implements Function5<GenerationCycle, CycleUiState, RestorationState, GenerationCycle, Continuation<? super ResultViewState>, Object> {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$resultViewState$1(CycleViewModel cycleViewModel, Continuation<? super CycleViewModel$resultViewState$1> continuation) {
        super(5, continuation);
        this.this$0 = cycleViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(GenerationCycle generationCycle, CycleUiState cycleUiState, RestorationState restorationState, GenerationCycle generationCycle2, Continuation<? super ResultViewState> continuation) {
        CycleViewModel$resultViewState$1 cycleViewModel$resultViewState$1 = new CycleViewModel$resultViewState$1(this.this$0, continuation);
        cycleViewModel$resultViewState$1.L$0 = generationCycle;
        cycleViewModel$resultViewState$1.L$1 = cycleUiState;
        cycleViewModel$resultViewState$1.L$2 = restorationState;
        cycleViewModel$resultViewState$1.L$3 = generationCycle2;
        return cycleViewModel$resultViewState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ResultViewState mapToResultViewState;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                GenerationCycle cycle = (GenerationCycle) this.L$0;
                CycleUiState uiState = (CycleUiState) this.L$1;
                RestorationState restoration = (RestorationState) this.L$2;
                GenerationCycle improvementBase = (GenerationCycle) this.L$3;
                mapToResultViewState = this.this$0.mapToResultViewState(cycle, uiState, restoration, improvementBase);
                return mapToResultViewState;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
