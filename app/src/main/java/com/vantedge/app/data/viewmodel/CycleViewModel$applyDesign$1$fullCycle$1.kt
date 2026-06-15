package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.DesignConfig;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.domain.OptimizationOrchestrator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/vantedge/app/data/model/GenerationCycle;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$applyDesign$1$fullCycle$1", f = "CycleViewModel.kt", i = {}, l = {820}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$applyDesign$1$fullCycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super GenerationCycle>, Object> {
    final /* synthetic */ GenerationCycle $cycle;
    final /* synthetic */ DesignConfig $design;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$applyDesign$1$fullCycle$1(CycleViewModel cycleViewModel, GenerationCycle generationCycle, DesignConfig designConfig, Continuation<? super CycleViewModel$applyDesign$1$fullCycle$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$cycle = generationCycle;
        this.$design = designConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$applyDesign$1$fullCycle$1(this.this$0, this.$cycle, this.$design, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super GenerationCycle> continuation) {
        return ((CycleViewModel$applyDesign$1$fullCycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        OptimizationOrchestrator optimizationOrchestrator;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                optimizationOrchestrator = this.this$0.orchestrator;
                this.label = 1;
                Object applyDesign = optimizationOrchestrator.applyDesign(this.$cycle.getId(), this.$design, this);
                return applyDesign == coroutine_suspended ? coroutine_suspended : applyDesign;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
