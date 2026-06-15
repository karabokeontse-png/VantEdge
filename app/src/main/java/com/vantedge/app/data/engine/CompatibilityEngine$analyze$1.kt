package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: CompatibilityEngine.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.engine.CompatibilityEngine", f = "CompatibilityEngine.kt", i = {0, 0, 0, 0, 0}, l = {111}, m = "analyze", n = {"jobTitle", "company", "jobDescription", "onResult", "startTime"}, s = {"L$0", "L$1", "L$2", "L$3", "J$0"})
/* loaded from: classes6.dex */
final class CompatibilityEngine$analyze$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CompatibilityEngine this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CompatibilityEngine$analyze$1(CompatibilityEngine compatibilityEngine, Continuation<? super CompatibilityEngine$analyze$1> continuation) {
        super(continuation);
        this.this$0 = compatibilityEngine;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.analyze(null, null, null, null, null, this);
    }
}
