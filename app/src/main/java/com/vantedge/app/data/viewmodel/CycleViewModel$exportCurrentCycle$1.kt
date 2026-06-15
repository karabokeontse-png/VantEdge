package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.domain.DocumentExportUseCase;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.viewmodel.ExportEvent;
import com.vantedge.app.data.viewmodel.ExportState;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$exportCurrentCycle$1", f = "CycleViewModel.kt", i = {}, l = {853, 857}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$exportCurrentCycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ GenerationCycle $cycle;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$exportCurrentCycle$1(CycleViewModel cycleViewModel, GenerationCycle generationCycle, Continuation<? super CycleViewModel$exportCurrentCycle$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$cycle = generationCycle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$exportCurrentCycle$1(this.this$0, this.$cycle, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$exportCurrentCycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        DocumentExportUseCase documentExportUseCase;
        MutableStateFlow mutableStateFlow;
        MutableSharedFlow mutableSharedFlow;
        MutableStateFlow mutableStateFlow2;
        MutableSharedFlow mutableSharedFlow2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                documentExportUseCase = this.this$0.exportUseCase;
                Object m6418exportIoAF18A = documentExportUseCase.m6418exportIoAF18A(this.$cycle);
                CycleViewModel cycleViewModel = this.this$0;
                CycleViewModel cycleViewModel2 = this.this$0;
                Throwable error = Result.m6585exceptionOrNullimpl(m6418exportIoAF18A);
                if (error == null) {
                    mutableStateFlow2 = cycleViewModel._exportState;
                    mutableStateFlow2.setValue(ExportState.Ready.INSTANCE);
                    mutableSharedFlow2 = cycleViewModel._exportEvents;
                    ExportEvent.Success success = ExportEvent.Success.INSTANCE;
                    this.label = 1;
                    if (mutableSharedFlow2.emit(success, this) != coroutine_suspended) {
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                } else {
                    mutableStateFlow = cycleViewModel2._exportState;
                    mutableStateFlow.setValue(ExportState.Idle.INSTANCE);
                    mutableSharedFlow = cycleViewModel2._exportEvents;
                    String message = error.getMessage();
                    if (message == null) {
                        message = "Export failed";
                    }
                    ExportEvent.Error error2 = new ExportEvent.Error(message);
                    this.label = 2;
                    if (mutableSharedFlow.emit(error2, this) != coroutine_suspended) {
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            case 2:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
