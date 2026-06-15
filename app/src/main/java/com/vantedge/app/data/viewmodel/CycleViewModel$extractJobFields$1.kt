package com.vantedge.app.data.viewmodel;

import android.util.Log;
import com.vantedge.app.data.model.JobExtractionResult;
import com.vantedge.app.data.model.JobSourceType;
import com.vantedge.app.data.viewmodel.JobExtractionState;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.flow.MutableStateFlow;
import okhttp3.internal.http.StatusLine;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$extractJobFields$1", f = "CycleViewModel.kt", i = {0, 0, 0}, l = {StatusLine.HTTP_MISDIRECTED_REQUEST}, m = "invokeSuspend", n = {"$this$launch", "token", "startTime"}, s = {"L$0", "J$0", "J$1"})
/* loaded from: classes9.dex */
final class CycleViewModel$extractJobFields$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $rawText;
    final /* synthetic */ JobSourceType $sourceType;
    long J$0;
    long J$1;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$extractJobFields$1(CycleViewModel cycleViewModel, JobSourceType jobSourceType, String str, Continuation<? super CycleViewModel$extractJobFields$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$sourceType = jobSourceType;
        this.$rawText = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CycleViewModel$extractJobFields$1 cycleViewModel$extractJobFields$1 = new CycleViewModel$extractJobFields$1(this.this$0, this.$sourceType, this.$rawText, continuation);
        cycleViewModel$extractJobFields$1.L$0 = obj;
        return cycleViewModel$extractJobFields$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$extractJobFields$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.vantedge.app.data.viewmodel.CycleViewModel$extractJobFields$1] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        MutableStateFlow mutableStateFlow;
        MutableStateFlow mutableStateFlow2;
        MutableStateFlow mutableStateFlow3;
        MutableStateFlow mutableStateFlow4;
        MutableStateFlow mutableStateFlow5;
        long longValue;
        MutableStateFlow mutableStateFlow6;
        MutableStateFlow mutableStateFlow7;
        long currentTimeMillis;
        CoroutineScope coroutineScope;
        Object obj3;
        Object obj4;
        MutableStateFlow mutableStateFlow8;
        MutableStateFlow mutableStateFlow9;
        MutableStateFlow mutableStateFlow10;
        MutableStateFlow mutableStateFlow11;
        MutableStateFlow mutableStateFlow12;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        CycleViewModel$extractJobFields$1 cycleViewModel$extractJobFields$1 = this.label;
        try {
            switch (cycleViewModel$extractJobFields$1) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    CycleViewModel$extractJobFields$1 cycleViewModel$extractJobFields$12 = this;
                    CoroutineScope coroutineScope2 = (CoroutineScope) cycleViewModel$extractJobFields$12.L$0;
                    mutableStateFlow5 = cycleViewModel$extractJobFields$12.this$0._extractionToken;
                    longValue = ((Number) mutableStateFlow5.getValue()).longValue() + 1;
                    mutableStateFlow6 = cycleViewModel$extractJobFields$12.this$0._extractionToken;
                    mutableStateFlow6.setValue(Boxing.boxLong(longValue));
                    Log.i("CycleViewModel", "[JobExtraction] START: token=" + longValue + " sourceType=" + cycleViewModel$extractJobFields$12.$sourceType + " rawLen=" + cycleViewModel$extractJobFields$12.$rawText.length());
                    Log.i("CycleViewModel", "[PIPELINE] ENTRY extraction token=" + longValue + " len=" + cycleViewModel$extractJobFields$12.$rawText.length());
                    mutableStateFlow7 = cycleViewModel$extractJobFields$12.this$0._jobExtractionState;
                    mutableStateFlow7.setValue(new JobExtractionState.Extracting("Analyzing job posting..."));
                    currentTimeMillis = System.currentTimeMillis();
                    cycleViewModel$extractJobFields$12.L$0 = coroutineScope2;
                    cycleViewModel$extractJobFields$12.J$0 = longValue;
                    cycleViewModel$extractJobFields$12.J$1 = currentTimeMillis;
                    cycleViewModel$extractJobFields$12.label = 1;
                    Object withContext = BuildersKt.withContext(Dispatchers.getDefault(), new CycleViewModel$extractJobFields$1$result$1(cycleViewModel$extractJobFields$12.this$0, cycleViewModel$extractJobFields$12.$rawText, cycleViewModel$extractJobFields$12.$sourceType, null), cycleViewModel$extractJobFields$12);
                    if (withContext == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    coroutineScope = coroutineScope2;
                    obj3 = obj;
                    obj4 = withContext;
                    cycleViewModel$extractJobFields$1 = cycleViewModel$extractJobFields$12;
                    break;
                case 1:
                    CycleViewModel$extractJobFields$1 cycleViewModel$extractJobFields$13 = this;
                    obj4 = obj;
                    long j = cycleViewModel$extractJobFields$13.J$1;
                    longValue = cycleViewModel$extractJobFields$13.J$0;
                    coroutineScope = (CoroutineScope) cycleViewModel$extractJobFields$13.L$0;
                    ResultKt.throwOnFailure(obj4);
                    currentTimeMillis = j;
                    obj3 = obj4;
                    cycleViewModel$extractJobFields$1 = cycleViewModel$extractJobFields$13;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (TimeoutCancellationException e) {
        } catch (Exception e2) {
            e = e2;
        }
        try {
            obj2 = ((Result) obj4).getValue();
            Log.i("CycleViewModel", "[PIPELINE] EXIT extraction duration=" + (System.currentTimeMillis() - currentTimeMillis));
            mutableStateFlow8 = cycleViewModel$extractJobFields$1.this$0._extractionToken;
        } catch (TimeoutCancellationException e3) {
            obj2 = obj3;
            mutableStateFlow3 = cycleViewModel$extractJobFields$1.this$0._extractionToken;
            if (longValue != ((Number) mutableStateFlow3.getValue()).longValue()) {
                return Unit.INSTANCE;
            }
            Log.e("CycleViewModel", "[JobExtraction] TIMEOUT: token=" + longValue);
            mutableStateFlow4 = cycleViewModel$extractJobFields$1.this$0._jobExtractionState;
            mutableStateFlow4.setValue(new JobExtractionState.Failure("Extraction timed out. Please try again."));
            return Unit.INSTANCE;
        } catch (Exception e4) {
            e = e4;
            obj2 = obj3;
            mutableStateFlow = cycleViewModel$extractJobFields$1.this$0._extractionToken;
            if (longValue != ((Number) mutableStateFlow.getValue()).longValue()) {
                return Unit.INSTANCE;
            }
            Log.e("CycleViewModel", "[JobExtraction] EXCEPTION: token=" + longValue + " msg=" + e.getMessage(), e);
            mutableStateFlow2 = cycleViewModel$extractJobFields$1.this$0._jobExtractionState;
            String message = e.getMessage();
            if (message == null) {
                message = "Unexpected error";
            }
            mutableStateFlow2.setValue(new JobExtractionState.Failure(message));
            return Unit.INSTANCE;
        }
        if (longValue != ((Number) mutableStateFlow8.getValue()).longValue()) {
            mutableStateFlow12 = cycleViewModel$extractJobFields$1.this$0._extractionToken;
            Log.w("CycleViewModel", "[JobExtraction] STALE: token=" + longValue + " current=" + mutableStateFlow12.getValue() + " — discarding");
            return Unit.INSTANCE;
        }
        Log.d("CycleViewModel", "[JobExtraction] FOLD_ENTRY: token=" + longValue + " isSuccess=" + Result.m6589isSuccessimpl(obj2));
        CycleViewModel cycleViewModel = cycleViewModel$extractJobFields$1.this$0;
        CycleViewModel cycleViewModel2 = cycleViewModel$extractJobFields$1.this$0;
        Throwable m6585exceptionOrNullimpl = Result.m6585exceptionOrNullimpl(obj2);
        if (m6585exceptionOrNullimpl == null) {
            JobExtractionResult jobExtractionResult = (JobExtractionResult) obj2;
            mutableStateFlow10 = cycleViewModel._jobExtractionState;
            Log.d("CycleViewModel", "[JobExtraction] PRE_EMIT_SUCCESS: currentState=" + Reflection.getOrCreateKotlinClass(mutableStateFlow10.getValue().getClass()).getSimpleName());
            Log.e("CycleViewModel", "[JobExtraction] VM_EMIT_SUCCESS: vm=" + coroutineScope.hashCode() + " id=" + jobExtractionResult.getExtractionId() + " confidence=" + jobExtractionResult.getConfidenceScore());
            mutableStateFlow11 = cycleViewModel._jobExtractionState;
            mutableStateFlow11.setValue(new JobExtractionState.Success(jobExtractionResult));
            Boxing.boxInt(Log.d("CycleViewModel", "[JobExtraction] POST_EMIT_SUCCESS"));
        } else {
            Log.e("CycleViewModel", "[JobExtraction] VM_EMIT_FAILURE: " + m6585exceptionOrNullimpl.getMessage());
            mutableStateFlow9 = cycleViewModel2._jobExtractionState;
            String message2 = m6585exceptionOrNullimpl.getMessage();
            if (message2 == null) {
                message2 = "Extraction failed";
            }
            mutableStateFlow9.setValue(new JobExtractionState.Failure(message2));
        }
        return Unit.INSTANCE;
    }
}
