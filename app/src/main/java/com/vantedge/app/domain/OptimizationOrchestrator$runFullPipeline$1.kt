package com.vantedge.app.domain;

import com.google.android.gms.common.Scopes;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: OptimizationOrchestrator.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.domain.OptimizationOrchestrator", f = "OptimizationOrchestrator.kt", i = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4}, l = {145, 165, 175, 188, 239}, m = "runFullPipeline", n = {"this", Scopes.PROFILE, "jobTitle", "company", "jobDescription", "improvementContext", "onProgress", "this", Scopes.PROFILE, "jobTitle", "company", "jobDescription", "improvementContext", "onProgress", "this", Scopes.PROFILE, "jobTitle", "company", "jobDescription", "improvementContext", "onProgress", "compatibility", "enrichedJobDescription", "rawCvJson", "this", Scopes.PROFILE, "jobTitle", "company", "jobDescription", "improvementContext", "compatibility", "cvJson", "rawCoverLetterBody", "cycle"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$9", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$0"})
/* loaded from: classes11.dex */
final class OptimizationOrchestrator$runFullPipeline$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    Object L$8;
    Object L$9;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OptimizationOrchestrator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OptimizationOrchestrator$runFullPipeline$1(OptimizationOrchestrator optimizationOrchestrator, Continuation<? super OptimizationOrchestrator$runFullPipeline$1> continuation) {
        super(continuation);
        this.this$0 = optimizationOrchestrator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.runFullPipeline(null, null, null, null, null, null, null, this);
    }
}
