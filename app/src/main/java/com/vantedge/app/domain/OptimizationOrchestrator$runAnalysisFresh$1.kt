package com.vantedge.app.domain;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: OptimizationOrchestrator.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.domain.OptimizationOrchestrator", f = "OptimizationOrchestrator.kt", i = {0, 0}, l = {253}, m = "runAnalysisFresh", n = {"result", "startTime"}, s = {"L$0", "J$0"})
/* loaded from: classes11.dex */
final class OptimizationOrchestrator$runAnalysisFresh$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OptimizationOrchestrator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OptimizationOrchestrator$runAnalysisFresh$1(OptimizationOrchestrator optimizationOrchestrator, Continuation<? super OptimizationOrchestrator$runAnalysisFresh$1> continuation) {
        super(continuation);
        this.this$0 = optimizationOrchestrator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object runAnalysisFresh;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        runAnalysisFresh = this.this$0.runAnalysisFresh(null, null, null, null, this);
        return runAnalysisFresh;
    }
}
