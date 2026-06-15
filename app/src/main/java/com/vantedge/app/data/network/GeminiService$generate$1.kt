package com.vantedge.app.data.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: GeminiService.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.network.GeminiService", f = "GeminiService.kt", i = {}, l = {75}, m = "generate", n = {}, s = {})
/* loaded from: classes10.dex */
final class GeminiService$generate$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ GeminiService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GeminiService$generate$1(GeminiService geminiService, Continuation<? super GeminiService$generate$1> continuation) {
        super(continuation);
        this.this$0 = geminiService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.generate(null, null, null, this);
    }
}
