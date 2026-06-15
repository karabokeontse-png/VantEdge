package com.vantedge.app.data.network;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: RequestThrottleManager.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.network.RequestThrottleManager", f = "RequestThrottleManager.kt", i = {0, 0, 1, 1, 2}, l = {34, 21, 25}, m = "throttle", n = {"block", "$this$withLock_u24default$iv", "block", "$this$withLock_u24default$iv", "$this$withLock_u24default$iv"}, s = {"L$0", "L$1", "L$0", "L$1", "L$0"})
/* loaded from: classes10.dex */
final class RequestThrottleManager$throttle$1<T> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ RequestThrottleManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    RequestThrottleManager$throttle$1(RequestThrottleManager requestThrottleManager, Continuation<? super RequestThrottleManager$throttle$1> continuation) {
        super(continuation);
        this.this$0 = requestThrottleManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.throttle(null, this);
    }
}
