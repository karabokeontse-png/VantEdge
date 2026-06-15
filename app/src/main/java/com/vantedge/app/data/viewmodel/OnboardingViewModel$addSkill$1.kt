package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.OnboardingDraft;
import com.vantedge.app.data.model.UserProfile;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: OnboardingViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.OnboardingViewModel$addSkill$1", f = "OnboardingViewModel.kt", i = {}, l = {284}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class OnboardingViewModel$addSkill$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $skillValue;
    int label;
    final /* synthetic */ OnboardingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OnboardingViewModel$addSkill$1(OnboardingViewModel onboardingViewModel, String str, Continuation<? super OnboardingViewModel$addSkill$1> continuation) {
        super(2, continuation);
        this.this$0 = onboardingViewModel;
        this.$skillValue = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new OnboardingViewModel$addSkill$1(this.this$0, this.$skillValue, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((OnboardingViewModel$addSkill$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        Object updateDraft;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                mutableStateFlow = this.this$0._draft;
                final UserProfile current = ((OnboardingDraft) mutableStateFlow.getValue()).getEditedProfile();
                if (current != null) {
                    final List updated = CollectionsKt.plus((Collection<? extends String>) current.getSkills(), this.$skillValue);
                    this.label = 1;
                    updateDraft = this.this$0.updateDraft(new Function1<OnboardingDraft, OnboardingDraft>() { // from class: com.vantedge.app.data.viewmodel.OnboardingViewModel$addSkill$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final OnboardingDraft invoke(OnboardingDraft it) {
                            UserProfile copy;
                            Intrinsics.checkNotNullParameter(it, "it");
                            copy = r12.copy((r24 & 1) != 0 ? r12.name : null, (r24 & 2) != 0 ? r12.email : null, (r24 & 4) != 0 ? r12.phone : null, (r24 & 8) != 0 ? r12.location : null, (r24 & 16) != 0 ? r12.linkedIn : null, (r24 & 32) != 0 ? r12.summary : null, (r24 & 64) != 0 ? r12.skills : updated, (r24 & 128) != 0 ? r12.workHistory : null, (r24 & 256) != 0 ? r12.education : null, (r24 & 512) != 0 ? r12.certifications : null, (r24 & 1024) != 0 ? UserProfile.this.languages : null);
                            return OnboardingDraft.copy$default(it, null, null, null, null, null, copy, 31, null);
                        }
                    }, this);
                    if (updateDraft != coroutine_suspended) {
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                } else {
                    return Unit.INSTANCE;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
