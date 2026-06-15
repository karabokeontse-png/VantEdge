package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationCycle;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$deleteCycle$1", f = "CycleViewModel.kt", i = {}, l = {495}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$deleteCycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $id;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$deleteCycle$1(String str, CycleViewModel cycleViewModel, Continuation<? super CycleViewModel$deleteCycle$1> continuation) {
        super(2, continuation);
        this.$id = str;
        this.this$0 = cycleViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$deleteCycle$1(this.$id, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$deleteCycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        CycleViewModel$deleteCycle$1 cycleViewModel$deleteCycle$1;
        CycleViewModel$deleteCycle$1 cycleViewModel$deleteCycle$12;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                cycleViewModel$deleteCycle$1 = this;
                String str = cycleViewModel$deleteCycle$1.$id;
                GenerationCycle currentCycle = cycleViewModel$deleteCycle$1.this$0.getCurrentCycle();
                if (Intrinsics.areEqual(str, currentCycle != null ? currentCycle.getId() : null)) {
                    cycleViewModel$deleteCycle$1.label = 1;
                    if (cycleViewModel$deleteCycle$1.this$0.historyStore.clearActive(cycleViewModel$deleteCycle$1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    cycleViewModel$deleteCycle$12 = cycleViewModel$deleteCycle$1;
                    cycleViewModel$deleteCycle$12.this$0._currentCycle.setValue(null);
                    cycleViewModel$deleteCycle$1 = cycleViewModel$deleteCycle$12;
                }
                cycleViewModel$deleteCycle$1.this$0.historyStore.deleteCycle(cycleViewModel$deleteCycle$1.$id);
                return Unit.INSTANCE;
            case 1:
                cycleViewModel$deleteCycle$12 = this;
                ResultKt.throwOnFailure($result);
                cycleViewModel$deleteCycle$12.this$0._currentCycle.setValue(null);
                cycleViewModel$deleteCycle$1 = cycleViewModel$deleteCycle$12;
                cycleViewModel$deleteCycle$1.this$0.historyStore.deleteCycle(cycleViewModel$deleteCycle$1.$id);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
