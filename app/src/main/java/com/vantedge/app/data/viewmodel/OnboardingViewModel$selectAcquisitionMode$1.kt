package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.AcquisitionMode;
import com.vantedge.app.data.model.OnboardingDraft;
import com.vantedge.app.data.model.OnboardingStage;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: OnboardingViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.OnboardingViewModel$selectAcquisitionMode$1", f = "OnboardingViewModel.kt", i = {}, l = {138}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class OnboardingViewModel$selectAcquisitionMode$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ AcquisitionMode $mode;
    int label;
    final /* synthetic */ OnboardingViewModel this$0;

    /* compiled from: OnboardingViewModel.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AcquisitionMode.values().length];
            try {
                iArr[AcquisitionMode.CV_UPLOAD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[AcquisitionMode.MANUAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OnboardingViewModel$selectAcquisitionMode$1(AcquisitionMode acquisitionMode, OnboardingViewModel onboardingViewModel, Continuation<? super OnboardingViewModel$selectAcquisitionMode$1> continuation) {
        super(2, continuation);
        this.$mode = acquisitionMode;
        this.this$0 = onboardingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new OnboardingViewModel$selectAcquisitionMode$1(this.$mode, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((OnboardingViewModel$selectAcquisitionMode$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        final OnboardingStage.UploadingCv nextStage;
        Object updateDraft;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                switch (WhenMappings.$EnumSwitchMapping$0[this.$mode.ordinal()]) {
                    case 1:
                        nextStage = OnboardingStage.UploadingCv.INSTANCE;
                        break;
                    case 2:
                        nextStage = OnboardingStage.EditingProfile.INSTANCE;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                OnboardingViewModel onboardingViewModel = this.this$0;
                final AcquisitionMode acquisitionMode = this.$mode;
                this.label = 1;
                updateDraft = onboardingViewModel.updateDraft(new Function1<OnboardingDraft, OnboardingDraft>() { // from class: com.vantedge.app.data.viewmodel.OnboardingViewModel$selectAcquisitionMode$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final OnboardingDraft invoke(OnboardingDraft it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return OnboardingDraft.copy$default(it, OnboardingStage.this, acquisitionMode, null, null, null, null, 60, null);
                    }
                }, this);
                if (updateDraft != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
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
