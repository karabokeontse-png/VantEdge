package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.model.GenerationMode;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.viewmodel.CycleUiState;
import com.vantedge.app.domain.OptimizationOrchestrator;
import com.vantedge.app.domain.PipelineStep;
import kotlin.Metadata;
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
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/vantedge/app/data/model/GenerationCycle;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2$cycle$1", f = "CycleViewModel.kt", i = {}, l = {715}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$runPipeline$2$cycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super GenerationCycle>, Object> {
    final /* synthetic */ String $company;
    final /* synthetic */ String $improvementContext;
    final /* synthetic */ String $jobDescription;
    final /* synthetic */ String $jobTitle;
    final /* synthetic */ GenerationMode $mode;
    final /* synthetic */ UserProfile $profile;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$runPipeline$2$cycle$1(CycleViewModel cycleViewModel, UserProfile userProfile, String str, String str2, String str3, GenerationMode generationMode, String str4, Continuation<? super CycleViewModel$runPipeline$2$cycle$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$profile = userProfile;
        this.$jobTitle = str;
        this.$company = str2;
        this.$jobDescription = str3;
        this.$mode = generationMode;
        this.$improvementContext = str4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$runPipeline$2$cycle$1(this.this$0, this.$profile, this.$jobTitle, this.$company, this.$jobDescription, this.$mode, this.$improvementContext, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super GenerationCycle> continuation) {
        return ((CycleViewModel$runPipeline$2$cycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        OptimizationOrchestrator optimizationOrchestrator;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                optimizationOrchestrator = this.this$0.orchestrator;
                UserProfile userProfile = this.$profile;
                String str = this.$jobTitle;
                String str2 = this.$company;
                String str3 = this.$jobDescription;
                GenerationMode generationMode = this.$mode;
                String str4 = this.$improvementContext;
                final CycleViewModel cycleViewModel = this.this$0;
                this.label = 1;
                Object runFullPipeline = optimizationOrchestrator.runFullPipeline(userProfile, str, str2, str3, generationMode, str4, new Function1<PipelineStep, Unit>() { // from class: com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2$cycle$1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(PipelineStep pipelineStep) {
                        invoke2(pipelineStep);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(PipelineStep step) {
                        MutableStateFlow mutableStateFlow;
                        Intrinsics.checkNotNullParameter(step, "step");
                        mutableStateFlow = CycleViewModel.this._uiState;
                        mutableStateFlow.setValue(new CycleUiState.Loading(step));
                    }
                }, this);
                return runFullPipeline == coroutine_suspended ? coroutine_suspended : runFullPipeline;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
