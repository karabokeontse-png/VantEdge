package com.vantedge.app.ui.screens;

import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import com.vantedge.app.data.viewmodel.JobExtractionState;
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
@DebugMetadata(c = "com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$4", f = "JobInputScreen.kt", i = {}, l = {172, 176}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class JobInputScreenKt$JobInputScreen$4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<String> $company$delegate;
    final /* synthetic */ State<JobExtractionState> $extractionState$delegate;
    final /* synthetic */ MutableState<String> $jobDescription$delegate;
    final /* synthetic */ MutableState<String> $jobTitle$delegate;
    final /* synthetic */ SnackbarHostState $snackbar;
    final /* synthetic */ MutableState<JobInputStage> $stage$delegate;
    final /* synthetic */ CycleViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    JobInputScreenKt$JobInputScreen$4(SnackbarHostState snackbarHostState, CycleViewModel cycleViewModel, State<? extends JobExtractionState> state, MutableState<JobInputStage> mutableState, MutableState<String> mutableState2, MutableState<String> mutableState3, MutableState<String> mutableState4, Continuation<? super JobInputScreenKt$JobInputScreen$4> continuation) {
        super(2, continuation);
        this.$snackbar = snackbarHostState;
        this.$viewModel = cycleViewModel;
        this.$extractionState$delegate = state;
        this.$stage$delegate = mutableState;
        this.$jobTitle$delegate = mutableState2;
        this.$company$delegate = mutableState3;
        this.$jobDescription$delegate = mutableState4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JobInputScreenKt$JobInputScreen$4(this.$snackbar, this.$viewModel, this.$extractionState$delegate, this.$stage$delegate, this.$jobTitle$delegate, this.$company$delegate, this.$jobDescription$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((JobInputScreenKt$JobInputScreen$4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        JobExtractionState state;
        JobInputStage JobInputScreen$lambda$7;
        JobInputScreenKt$JobInputScreen$4 jobInputScreenKt$JobInputScreen$4;
        JobInputScreenKt$JobInputScreen$4 jobInputScreenKt$JobInputScreen$42;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                state = JobInputScreenKt.JobInputScreen$lambda$4(this.$extractionState$delegate);
                if (!(state instanceof JobExtractionState.Extracting)) {
                    if (state instanceof JobExtractionState.Success) {
                        MutableState<String> mutableState = this.$jobTitle$delegate;
                        String jobTitle = ((JobExtractionState.Success) state).getResult().getJobTitle();
                        if (jobTitle == null) {
                            jobTitle = "";
                        }
                        mutableState.setValue(jobTitle);
                        MutableState<String> mutableState2 = this.$company$delegate;
                        String company = ((JobExtractionState.Success) state).getResult().getCompany();
                        if (company == null) {
                            company = "";
                        }
                        mutableState2.setValue(company);
                        MutableState<String> mutableState3 = this.$jobDescription$delegate;
                        String description = ((JobExtractionState.Success) state).getResult().getDescription();
                        mutableState3.setValue(description != null ? description : "");
                        this.$stage$delegate.setValue(JobInputStage.REVIEWING);
                        this.label = 1;
                        if (SnackbarHostState.showSnackbar$default(this.$snackbar, "Job details extracted successfully.", null, false, null, this, 14, null) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        jobInputScreenKt$JobInputScreen$42 = this;
                        jobInputScreenKt$JobInputScreen$42.$viewModel.resetExtractionState();
                    } else if (state instanceof JobExtractionState.Failure) {
                        SnackbarHostState snackbarHostState = this.$snackbar;
                        String message = ((JobExtractionState.Failure) state).getMessage();
                        this.label = 2;
                        if (SnackbarHostState.showSnackbar$default(snackbarHostState, "Extraction failed: " + message, null, false, null, this, 14, null) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        jobInputScreenKt$JobInputScreen$4 = this;
                        jobInputScreenKt$JobInputScreen$4.$stage$delegate.setValue(JobInputStage.INPUT);
                        jobInputScreenKt$JobInputScreen$4.$viewModel.resetExtractionState();
                    } else if (state instanceof JobExtractionState.Idle) {
                        JobInputScreen$lambda$7 = JobInputScreenKt.JobInputScreen$lambda$7(this.$stage$delegate);
                        if (JobInputScreen$lambda$7 == JobInputStage.EXTRACTING) {
                            this.$stage$delegate.setValue(JobInputStage.INPUT);
                        }
                    }
                } else {
                    this.$stage$delegate.setValue(JobInputStage.EXTRACTING);
                }
                return Unit.INSTANCE;
            case 1:
                jobInputScreenKt$JobInputScreen$42 = this;
                ResultKt.throwOnFailure($result);
                jobInputScreenKt$JobInputScreen$42.$viewModel.resetExtractionState();
                return Unit.INSTANCE;
            case 2:
                jobInputScreenKt$JobInputScreen$4 = this;
                ResultKt.throwOnFailure($result);
                jobInputScreenKt$JobInputScreen$4.$stage$delegate.setValue(JobInputStage.INPUT);
                jobInputScreenKt$JobInputScreen$4.$viewModel.resetExtractionState();
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
