package com.vantedge.app.data.viewmodel;

import android.util.Log;
import com.vantedge.app.data.storage.TelemetryStorage;
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

/* compiled from: DebugViewmodel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.DebugViewModel$refreshRecords$1", f = "DebugViewmodel.kt", i = {}, l = {137}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class DebugViewModel$refreshRecords$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ DebugViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DebugViewModel$refreshRecords$1(DebugViewModel debugViewModel, Continuation<? super DebugViewModel$refreshRecords$1> continuation) {
        super(2, continuation);
        this.this$0 = debugViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DebugViewModel$refreshRecords$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DebugViewModel$refreshRecords$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        DebugViewModel$refreshRecords$1 debugViewModel$refreshRecords$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                debugViewModel$refreshRecords$1 = this;
                Log.i("DebugViewModel", "[DebugViewModel] Manual refresh triggered.");
                debugViewModel$refreshRecords$1.label = 1;
                Object recentRecords$default = TelemetryStorage.getRecentRecords$default(debugViewModel$refreshRecords$1.this$0.telemetryStorage, 0, debugViewModel$refreshRecords$1, 1, null);
                if (recentRecords$default != coroutine_suspended) {
                    $result = recentRecords$default;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                debugViewModel$refreshRecords$1 = this;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        List parsed = (List) $result;
        debugViewModel$refreshRecords$1.this$0._records.setValue(parsed);
        Log.i("DebugViewModel", "[DebugViewModel] Refresh complete — " + parsed.size() + " records.");
        return Unit.INSTANCE;
    }
}
