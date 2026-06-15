package com.vantedge.app.data.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: GeminiService.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.network.GeminiService", f = "GeminiService.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1}, l = {107, 123}, m = "tryChain", n = {"this", "systemInstruction", "userPrompt", "onProgress", "lastFailure", "model", "isRetry", "index$iv", "index", "this", "systemInstruction", "userPrompt", "onProgress", "lastFailure", "isRetry", "index$iv"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$6", "Z$0", "I$0", "I$1", "L$0", "L$1", "L$2", "L$3", "L$4", "Z$0", "I$0"})
/* loaded from: classes10.dex */
final class GeminiService$tryChain$1 extends ContinuationImpl {
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ GeminiService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GeminiService$tryChain$1(GeminiService geminiService, Continuation<? super GeminiService$tryChain$1> continuation) {
        super(continuation);
        this.this$0 = geminiService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object tryChain;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        tryChain = this.this$0.tryChain(null, null, null, false, this);
        return tryChain;
    }
}
