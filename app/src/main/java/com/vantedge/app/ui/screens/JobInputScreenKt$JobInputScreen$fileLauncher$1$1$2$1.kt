package com.vantedge.app.ui.screens;

import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.MutableState;
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
@DebugMetadata(c = "com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1", f = "JobInputScreen.kt", i = {}, l = {212}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Exception $e;
    final /* synthetic */ SnackbarHostState $snackbar;
    final /* synthetic */ MutableState<JobInputStage> $stage$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1(SnackbarHostState snackbarHostState, Exception exc, MutableState<JobInputStage> mutableState, Continuation<? super JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1> continuation) {
        super(2, continuation);
        this.$snackbar = snackbarHostState;
        this.$e = exc;
        this.$stage$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1(this.$snackbar, this.$e, this.$stage$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        JobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1 jobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                SnackbarHostState snackbarHostState = this.$snackbar;
                String message = this.$e.getMessage();
                this.label = 1;
                if (SnackbarHostState.showSnackbar$default(snackbarHostState, "Image read failed: " + message, null, false, null, this, 14, null) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                jobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1 = this;
                break;
            case 1:
                jobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        jobInputScreenKt$JobInputScreen$fileLauncher$1$1$2$1.$stage$delegate.setValue(JobInputStage.INPUT);
        return Unit.INSTANCE;
    }
}
