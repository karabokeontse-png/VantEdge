package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationCycle;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$startImproveFromCycle$1", f = "CycleViewModel.kt", i = {}, l = {552, 554}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$startImproveFromCycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ GenerationCycle $cycle;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$startImproveFromCycle$1(CycleViewModel cycleViewModel, GenerationCycle generationCycle, Continuation<? super CycleViewModel$startImproveFromCycle$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$cycle = generationCycle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$startImproveFromCycle$1(this.this$0, this.$cycle, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$startImproveFromCycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0054 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0055  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            switch(r1) {
                case 0: goto L1b;
                case 1: goto L16;
                case 2: goto L11;
                default: goto L9;
            }
        L9:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L11:
            r0 = r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L56
        L16:
            r1 = r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L30
        L1b:
            kotlin.ResultKt.throwOnFailure(r7)
            r1 = r6
            com.vantedge.app.data.viewmodel.CycleViewModel r2 = r1.this$0
            com.vantedge.app.data.model.GenerationCycle r3 = r1.$cycle
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 1
            r1.label = r5
            java.lang.Object r2 = com.vantedge.app.data.viewmodel.CycleViewModel.access$setActiveCycle(r2, r3, r4)
            if (r2 != r0) goto L30
            return r0
        L30:
            com.vantedge.app.data.viewmodel.CycleViewModel r2 = r1.this$0
            kotlinx.coroutines.flow.MutableStateFlow r2 = com.vantedge.app.data.viewmodel.CycleViewModel.access$get_uiState$p(r2)
            com.vantedge.app.data.viewmodel.CycleUiState$AnalysisDone r3 = new com.vantedge.app.data.viewmodel.CycleUiState$AnalysisDone
            com.vantedge.app.data.model.GenerationCycle r4 = r1.$cycle
            r3.<init>(r4)
            r2.setValue(r3)
            com.vantedge.app.data.viewmodel.CycleViewModel r2 = r1.this$0
            kotlinx.coroutines.flow.MutableSharedFlow r2 = com.vantedge.app.data.viewmodel.CycleViewModel.access$get_navEvent$p(r2)
            com.vantedge.app.data.viewmodel.CycleNavEvent$ToAnalysisResult r3 = com.vantedge.app.data.viewmodel.CycleNavEvent.ToAnalysisResult.INSTANCE
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 2
            r1.label = r5
            java.lang.Object r2 = r2.emit(r3, r4)
            if (r2 != r0) goto L55
            return r0
        L55:
            r0 = r1
        L56:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel$startImproveFromCycle$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
