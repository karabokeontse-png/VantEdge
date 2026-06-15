package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.bouncycastle.asn1.BERTags;

/* compiled from: OnboardingViewModel.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.OnboardingViewModel", f = "OnboardingViewModel.kt", i = {0, 0, 0, 0, 1, 1, 1}, l = {204, BERTags.FLAGS, 240, 244}, m = "runStructuredExtraction", n = {"this", "rawText", "extractionMode", "token", "this", "errorMsg", "token"}, s = {"L$0", "L$1", "L$2", "J$0", "L$0", "L$1", "J$0"})
/* loaded from: classes9.dex */
final class OnboardingViewModel$runStructuredExtraction$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OnboardingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OnboardingViewModel$runStructuredExtraction$1(OnboardingViewModel onboardingViewModel, Continuation<? super OnboardingViewModel$runStructuredExtraction$1> continuation) {
        super(continuation);
        this.this$0 = onboardingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object runStructuredExtraction;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        runStructuredExtraction = this.this$0.runStructuredExtraction(0L, null, this);
        return runStructuredExtraction;
    }
}
