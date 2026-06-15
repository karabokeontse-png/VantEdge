package com.vantedge.app.data.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: AiGateway.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.network.AiGateway", f = "AiGateway.kt", i = {0, 0}, l = {16}, m = "generate", n = {"tag", "startTime"}, s = {"L$0", "J$0"})
/* loaded from: classes10.dex */
final class AiGateway$generate$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AiGateway this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AiGateway$generate$1(AiGateway aiGateway, Continuation<? super AiGateway$generate$1> continuation) {
        super(continuation);
        this.this$0 = aiGateway;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.generate(null, null, null, null, this);
    }
}
