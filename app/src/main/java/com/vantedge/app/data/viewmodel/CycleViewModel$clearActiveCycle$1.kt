package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: CycleViewModel.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel", f = "CycleViewModel.kt", i = {0}, l = {377}, m = "clearActiveCycle", n = {"this"}, s = {"L$0"})
/* loaded from: classes9.dex */
final class CycleViewModel$clearActiveCycle$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$clearActiveCycle$1(CycleViewModel cycleViewModel, Continuation<? super CycleViewModel$clearActiveCycle$1> continuation) {
        super(continuation);
        this.this$0 = cycleViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object clearActiveCycle;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        clearActiveCycle = this.this$0.clearActiveCycle(this);
        return clearActiveCycle;
    }
}
