package com.vantedge.app.util;

import android.util.Log;
import com.vantedge.app.data.model.TelemetryRecord;
import java.util.LinkedHashSet;
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
@DebugMetadata(c = "com.vantedge.app.util.TelemetryCollector$record$1", f = "TelemetryCollector.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes11.dex */
final class TelemetryCollector$record$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ TelemetryRecord $telemetry;
    int label;
    final /* synthetic */ TelemetryCollector this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TelemetryCollector$record$1(TelemetryCollector telemetryCollector, TelemetryRecord telemetryRecord, Continuation<? super TelemetryCollector$record$1> continuation) {
        super(2, continuation);
        this.this$0 = telemetryCollector;
        this.$telemetry = telemetryRecord;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TelemetryCollector$record$1(this.this$0, this.$telemetry, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TelemetryCollector$record$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ReentrantLock reentrantLock;
        LinkedHashSet linkedHashSet;
        ArrayDeque arrayDeque;
        ArrayDeque arrayDeque2;
        LinkedHashSet linkedHashSet2;
        String logLine;
        ArrayDeque arrayDeque3;
        ArrayDeque arrayDeque4;
        LinkedHashSet linkedHashSet3;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                reentrantLock = this.this$0.lock;
                ReentrantLock reentrantLock2 = reentrantLock;
                TelemetryCollector telemetryCollector = this.this$0;
                TelemetryRecord telemetryRecord = this.$telemetry;
                reentrantLock2.lock();
                try {
                    linkedHashSet = telemetryCollector.seenHashes;
                    if (!linkedHashSet.contains(telemetryRecord.getDocumentHash())) {
                        arrayDeque = telemetryCollector.queue;
                        if (arrayDeque.size() >= 100) {
                            arrayDeque4 = telemetryCollector.queue;
                            TelemetryRecord evicted = (TelemetryRecord) arrayDeque4.removeFirst();
                            linkedHashSet3 = telemetryCollector.seenHashes;
                            linkedHashSet3.remove(evicted.getDocumentHash());
                            Log.w("TelemetryCollector", "Queue overflow: dropped oldest record");
                        }
                        arrayDeque2 = telemetryCollector.queue;
                        arrayDeque2.addLast(telemetryRecord);
                        linkedHashSet2 = telemetryCollector.seenHashes;
                        linkedHashSet2.add(telemetryRecord.getDocumentHash());
                        logLine = telemetryCollector.toLogLine(telemetryRecord);
                        telemetryCollector.appendToFile(logLine);
                        String take = StringsKt.take(telemetryRecord.getDocumentHash(), 8);
                        String take2 = StringsKt.take(telemetryRecord.getSessionId(), 8);
                        boolean gate0Accepted = telemetryRecord.getGate0Accepted();
                        int gate0Score = telemetryRecord.getGate0Score();
                        int gate0Threshold = telemetryRecord.getGate0Threshold();
                        String gate0Reason = telemetryRecord.getGate0Reason();
                        String extractionMode = telemetryRecord.getExtractionMode();
                        arrayDeque3 = telemetryCollector.queue;
                        Log.i("TelemetryCollector", "[TelemetryCollector] Recorded — hash=" + take + "… session=" + take2 + "… accepted=" + gate0Accepted + " score=" + gate0Score + "/" + gate0Threshold + " reason=" + gate0Reason + " mode=" + extractionMode + " queueSize=" + arrayDeque3.size());
                    } else {
                        Log.d("TelemetryCollector", "[TelemetryCollector] Idempotent skip — hash=" + StringsKt.take(telemetryRecord.getDocumentHash(), 8) + "…");
                    }
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
