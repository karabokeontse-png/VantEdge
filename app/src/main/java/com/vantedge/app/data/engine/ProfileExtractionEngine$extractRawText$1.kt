package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: ProfileExtractionEngine.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.engine.ProfileExtractionEngine", f = "ProfileExtractionEngine.kt", i = {}, l = {110}, m = "extractRawText-gIAlu-s", n = {}, s = {})
/* loaded from: classes6.dex */
final class ProfileExtractionEngine$extractRawText$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ProfileExtractionEngine this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProfileExtractionEngine$extractRawText$1(ProfileExtractionEngine profileExtractionEngine, Continuation<? super ProfileExtractionEngine$extractRawText$1> continuation) {
        super(continuation);
        this.this$0 = profileExtractionEngine;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object m6421extractRawTextgIAlus = this.this$0.m6421extractRawTextgIAlus(null, this);
        return m6421extractRawTextgIAlus == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? m6421extractRawTextgIAlus : Result.m6581boximpl(m6421extractRawTextgIAlus);
    }
}
