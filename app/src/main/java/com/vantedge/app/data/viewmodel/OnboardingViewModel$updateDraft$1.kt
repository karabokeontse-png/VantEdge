package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: OnboardingViewModel.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.OnboardingViewModel", f = "OnboardingViewModel.kt", i = {0, 0, 0, 1, 1, 1}, l = {378, 367}, m = "updateDraft", n = {"this", "transform", "$this$withLock_u24default$iv", "this", "$this$withLock_u24default$iv", "updated"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2"})
/* loaded from: classes9.dex */
final class OnboardingViewModel$updateDraft$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OnboardingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OnboardingViewModel$updateDraft$1(OnboardingViewModel onboardingViewModel, Continuation<? super OnboardingViewModel$updateDraft$1> continuation) {
        super(continuation);
        this.this$0 = onboardingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object updateDraft;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        updateDraft = this.this$0.updateDraft(null, this);
        return updateDraft;
    }
}
