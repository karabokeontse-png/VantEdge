package com.vantedge.app.util;

import android.util.Log;
import com.vantedge.app.data.model.UserDecisionEvent;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: TelemetryCollector.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.util.TelemetryCollector$recordDecision$1", f = "TelemetryCollector.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
final class TelemetryCollector$recordDecision$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ UserDecisionEvent $event;
    int label;
    final /* synthetic */ TelemetryCollector this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TelemetryCollector$recordDecision$1(TelemetryCollector telemetryCollector, UserDecisionEvent userDecisionEvent, Continuation<? super TelemetryCollector$recordDecision$1> continuation) {
        super(2, continuation);
        this.this$0 = telemetryCollector;
        this.$event = userDecisionEvent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TelemetryCollector$recordDecision$1(this.this$0, this.$event, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TelemetryCollector$recordDecision$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ReentrantLock reentrantLock;
        ArrayDeque arrayDeque;
        String logLine;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                reentrantLock = this.this$0.lock;
                ReentrantLock reentrantLock2 = reentrantLock;
                TelemetryCollector telemetryCollector = this.this$0;
                UserDecisionEvent userDecisionEvent = this.$event;
                reentrantLock2.lock();
                try {
                    arrayDeque = telemetryCollector.decisionLog;
                    arrayDeque.addLast(userDecisionEvent);
                    logLine = telemetryCollector.toLogLine(userDecisionEvent);
                    telemetryCollector.appendToFile(logLine);
                    Log.i("TelemetryCollector", "[TelemetryCollector] Decision — hash=" + StringsKt.take(userDecisionEvent.getDocumentHash(), 8) + "… session=" + StringsKt.take(userDecisionEvent.getSessionId(), 8) + "… type=" + userDecisionEvent.getDecisionType());
                    Unit unit = Unit.INSTANCE;
                    reentrantLock2.unlock();
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    reentrantLock2.unlock();
                    throw th;
                }
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
