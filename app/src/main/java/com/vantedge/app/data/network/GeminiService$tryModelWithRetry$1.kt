package com.vantedge.app.data.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: GeminiService.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.network.GeminiService", f = "GeminiService.kt", i = {0, 0, 0, 0, 1, 1, 1, 1}, l = {139, 145, 146}, m = "tryModelWithRetry", n = {"this", "model", "systemInstruction", "userPrompt", "this", "model", "systemInstruction", "userPrompt"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
/* loaded from: classes10.dex */
final class GeminiService$tryModelWithRetry$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ GeminiService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GeminiService$tryModelWithRetry$1(GeminiService geminiService, Continuation<? super GeminiService$tryModelWithRetry$1> continuation) {
        super(continuation);
        this.this$0 = geminiService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object tryModelWithRetry;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        tryModelWithRetry = this.this$0.tryModelWithRetry(null, null, null, this);
        return tryModelWithRetry;
    }
}
