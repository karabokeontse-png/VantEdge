package com.vantedge.app.ui.screens;

import androidx.compose.material3.SnackbarHostState;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import com.vantedge.app.data.viewmodel.ExportEvent;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;

/* compiled from: ResultScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$2", f = "ResultScreen.kt", i = {}, l = {127}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class ResultScreenKt$ResultScreen$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ SnackbarHostState $snackbar;
    final /* synthetic */ CycleViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultScreenKt$ResultScreen$2(CycleViewModel cycleViewModel, SnackbarHostState snackbarHostState, Continuation<? super ResultScreenKt$ResultScreen$2> continuation) {
        super(2, continuation);
        this.$viewModel = cycleViewModel;
        this.$snackbar = snackbarHostState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ResultScreenKt$ResultScreen$2(this.$viewModel, this.$snackbar, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ResultScreenKt$ResultScreen$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                SharedFlow<ExportEvent> exportEvents = this.$viewModel.getExportEvents();
                final SnackbarHostState snackbarHostState = this.$snackbar;
                this.label = 1;
                if (exportEvents.collect(new FlowCollector() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                        return emit((ExportEvent) value, (Continuation<? super Unit>) $completion);
                    }

                    public final Object emit(ExportEvent event, Continuation<? super Unit> continuation) {
                        if (event instanceof ExportEvent.Success) {
                            Object showSnackbar$default = SnackbarHostState.showSnackbar$default(SnackbarHostState.this, "Export successful", null, false, null, continuation, 14, null);
                            return showSnackbar$default == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? showSnackbar$default : Unit.INSTANCE;
                        }
                        if (event instanceof ExportEvent.Error) {
                            Object showSnackbar$default2 = SnackbarHostState.showSnackbar$default(SnackbarHostState.this, "Export failed: " + ((ExportEvent.Error) event).getMessage(), null, false, null, continuation, 14, null);
                            return showSnackbar$default2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? showSnackbar$default2 : Unit.INSTANCE;
                        }
                        boolean z = event instanceof ExportEvent.OpenDocument;
                        return Unit.INSTANCE;
                    }
                }, this) != coroutine_suspended) {
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
        throw new KotlinNothingValueException();
    }
}
