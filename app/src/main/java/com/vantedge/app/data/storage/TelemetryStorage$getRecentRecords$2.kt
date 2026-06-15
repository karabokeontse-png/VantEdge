package com.vantedge.app.data.storage;

import com.vantedge.app.data.model.TelemetryRecord;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: TelemetryStorage.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/vantedge/app/data/model/TelemetryRecord;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.storage.TelemetryStorage$getRecentRecords$2", f = "TelemetryStorage.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes5.dex */
final class TelemetryStorage$getRecentRecords$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends TelemetryRecord>>, Object> {
    final /* synthetic */ int $limit;
    int label;
    final /* synthetic */ TelemetryStorage this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TelemetryStorage$getRecentRecords$2(TelemetryStorage telemetryStorage, int i, Continuation<? super TelemetryStorage$getRecentRecords$2> continuation) {
        super(2, continuation);
        this.this$0 = telemetryStorage;
        this.$limit = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TelemetryStorage$getRecentRecords$2(this.this$0, this.$limit, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends TelemetryRecord>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super List<TelemetryRecord>>) continuation);
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<TelemetryRecord>> continuation) {
        return ((TelemetryStorage$getRecentRecords$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List readGate0Records;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                readGate0Records = this.this$0.readGate0Records(this.$limit);
                return readGate0Records;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
