package com.vantedge.app.ui.screens;

import androidx.compose.runtime.MutableState;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: JobInputScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$2", f = "JobInputScreen.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class JobInputScreenKt$JobInputScreen$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<String> $company$delegate;
    final /* synthetic */ MutableState<JobInputStage> $stage$delegate;
    final /* synthetic */ CycleViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JobInputScreenKt$JobInputScreen$2(CycleViewModel cycleViewModel, MutableState<JobInputStage> mutableState, MutableState<String> mutableState2, Continuation<? super JobInputScreenKt$JobInputScreen$2> continuation) {
        super(2, continuation);
        this.$viewModel = cycleViewModel;
        this.$stage$delegate = mutableState;
        this.$company$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JobInputScreenKt$JobInputScreen$2(this.$viewModel, this.$stage$delegate, this.$company$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((JobInputScreenKt$JobInputScreen$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        JobInputStage JobInputScreen$lambda$7;
        String JobInputScreen$lambda$13;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                JobInputScreen$lambda$7 = JobInputScreenKt.JobInputScreen$lambda$7(this.$stage$delegate);
                if (JobInputScreen$lambda$7 != JobInputStage.EXTRACTING) {
                    CycleViewModel cycleViewModel = this.$viewModel;
                    JobInputScreen$lambda$13 = JobInputScreenKt.JobInputScreen$lambda$13(this.$company$delegate);
                    cycleViewModel.setSavedCompany(JobInputScreen$lambda$13);
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
