package com.vantedge.app.domain;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: OnboardingCommitService.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.domain.OnboardingCommitService", f = "OnboardingCommitService.kt", i = {0}, l = {16, 17}, m = "commit", n = {"this"}, s = {"L$0"})
/* loaded from: classes11.dex */
final class OnboardingCommitService$commit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OnboardingCommitService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OnboardingCommitService$commit$1(OnboardingCommitService onboardingCommitService, Continuation<? super OnboardingCommitService$commit$1> continuation) {
        super(continuation);
        this.this$0 = onboardingCommitService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.commit(null, this);
    }
}
