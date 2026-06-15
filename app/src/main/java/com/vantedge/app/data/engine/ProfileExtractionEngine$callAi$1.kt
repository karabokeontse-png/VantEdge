package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: ProfileExtractionEngine.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.engine.ProfileExtractionEngine", f = "ProfileExtractionEngine.kt", i = {0}, l = {401}, m = "callAi", n = {"this"}, s = {"L$0"})
/* loaded from: classes6.dex */
final class ProfileExtractionEngine$callAi$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ProfileExtractionEngine this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProfileExtractionEngine$callAi$1(ProfileExtractionEngine profileExtractionEngine, Continuation<? super ProfileExtractionEngine$callAi$1> continuation) {
        super(continuation);
        this.this$0 = profileExtractionEngine;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object callAi;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        callAi = this.this$0.callAi(null, null, this);
        return callAi;
    }
}
