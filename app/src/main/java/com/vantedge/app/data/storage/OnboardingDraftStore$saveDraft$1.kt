package com.vantedge.app.data.storage;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: OnboardingDraftStore.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.storage.OnboardingDraftStore", f = "OnboardingDraftStore.kt", i = {0, 0}, l = {23, 30}, m = "saveDraft", n = {"this", "draft"}, s = {"L$0", "L$1"})
/* loaded from: classes5.dex */
final class OnboardingDraftStore$saveDraft$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OnboardingDraftStore this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OnboardingDraftStore$saveDraft$1(OnboardingDraftStore onboardingDraftStore, Continuation<? super OnboardingDraftStore$saveDraft$1> continuation) {
        super(continuation);
        this.this$0 = onboardingDraftStore;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.saveDraft(null, this);
    }
}
