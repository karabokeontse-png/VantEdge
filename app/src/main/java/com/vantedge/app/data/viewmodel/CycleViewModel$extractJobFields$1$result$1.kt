package com.vantedge.app.data.viewmodel;

import androidx.work.WorkRequest;
import com.vantedge.app.data.engine.JobExtractionEngine;
import com.vantedge.app.data.model.JobExtractionResult;
import com.vantedge.app.data.model.JobSourceType;
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
import kotlinx.coroutines.TimeoutKt;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "Lkotlin/Result;", "Lcom/vantedge/app/data/model/JobExtractionResult;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$extractJobFields$1$result$1", f = "CycleViewModel.kt", i = {}, l = {422}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$extractJobFields$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Result<? extends JobExtractionResult>>, Object> {
    final /* synthetic */ String $rawText;
    final /* synthetic */ JobSourceType $sourceType;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$extractJobFields$1$result$1(CycleViewModel cycleViewModel, String str, JobSourceType jobSourceType, Continuation<? super CycleViewModel$extractJobFields$1$result$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$rawText = str;
        this.$sourceType = jobSourceType;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$extractJobFields$1$result$1(this.this$0, this.$rawText, this.$sourceType, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Result<? extends JobExtractionResult>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super Result<JobExtractionResult>>) continuation);
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super Result<JobExtractionResult>> continuation) {
        return ((CycleViewModel$extractJobFields$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "Lkotlin/Result;", "Lcom/vantedge/app/data/model/JobExtractionResult;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$extractJobFields$1$result$1$1", f = "CycleViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.vantedge.app.data.viewmodel.CycleViewModel$extractJobFields$1$result$1$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Result<? extends JobExtractionResult>>, Object> {
        final /* synthetic */ String $rawText;
        final /* synthetic */ JobSourceType $sourceType;
        int label;
        final /* synthetic */ CycleViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(CycleViewModel cycleViewModel, String str, JobSourceType jobSourceType, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = cycleViewModel;
            this.$rawText = str;
            this.$sourceType = jobSourceType;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$rawText, this.$sourceType, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Result<? extends JobExtractionResult>> continuation) {
            return invoke2(coroutineScope, (Continuation<? super Result<JobExtractionResult>>) continuation);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super Result<JobExtractionResult>> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            JobExtractionEngine jobExtractionEngine;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    jobExtractionEngine = this.this$0.jobExtractionEngine;
                    return Result.m6581boximpl(jobExtractionEngine.m6419extractJobgIAlus(this.$rawText, this.$sourceType));
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                Object withTimeout = TimeoutKt.withTimeout(WorkRequest.MIN_BACKOFF_MILLIS, new AnonymousClass1(this.this$0, this.$rawText, this.$sourceType, null), this);
                return withTimeout == coroutine_suspended ? coroutine_suspended : withTimeout;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
