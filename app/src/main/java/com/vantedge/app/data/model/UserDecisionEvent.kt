package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserDecisionEvent.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lcom/vantedge/app/data/model/UserDecisionEvent;", "", "documentHash", "", "sessionId", "decisionType", "timestampMs", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getDecisionType", "()Ljava/lang/String;", "getDocumentHash", "getSessionId", "getTimestampMs", "()J", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class UserDecisionEvent {
    public static final int $stable = 0;
    private final String decisionType;
    private final String documentHash;
    private final String sessionId;
    private final long timestampMs;

    public static /* synthetic */ UserDecisionEvent copy$default(UserDecisionEvent userDecisionEvent, String str, String str2, String str3, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = userDecisionEvent.documentHash;
        }
        if ((i & 2) != 0) {
            str2 = userDecisionEvent.sessionId;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            str3 = userDecisionEvent.decisionType;
        }
        String str5 = str3;
        if ((i & 8) != 0) {
            j = userDecisionEvent.timestampMs;
        }
        return userDecisionEvent.copy(str, str4, str5, j);
    }

    /* renamed from: component1, reason: from getter */
    public final String getDocumentHash() {
        return this.documentHash;
    }

    /* renamed from: component2, reason: from getter */
    public final String getSessionId() {
        return this.sessionId;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDecisionType() {
        return this.decisionType;
    }

    /* renamed from: component4, reason: from getter */
    public final long getTimestampMs() {
        return this.timestampMs;
    }

    public final UserDecisionEvent copy(String documentHash, String sessionId, String decisionType, long timestampMs) {
        Intrinsics.checkNotNullParameter(documentHash, "documentHash");
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(decisionType, "decisionType");
        return new UserDecisionEvent(documentHash, sessionId, decisionType, timestampMs);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserDecisionEvent)) {
            return false;
        }
        UserDecisionEvent userDecisionEvent = (UserDecisionEvent) other;
        return Intrinsics.areEqual(this.documentHash, userDecisionEvent.documentHash) && Intrinsics.areEqual(this.sessionId, userDecisionEvent.sessionId) && Intrinsics.areEqual(this.decisionType, userDecisionEvent.decisionType) && this.timestampMs == userDecisionEvent.timestampMs;
    }

    public int hashCode() {
        return (((((this.documentHash.hashCode() * 31) + this.sessionId.hashCode()) * 31) + this.decisionType.hashCode()) * 31) + Long.hashCode(this.timestampMs);
    }

    public String toString() {
        return "UserDecisionEvent(documentHash=" + this.documentHash + ", sessionId=" + this.sessionId + ", decisionType=" + this.decisionType + ", timestampMs=" + this.timestampMs + ")";
    }

    public UserDecisionEvent(String documentHash, String sessionId, String decisionType, long timestampMs) {
        Intrinsics.checkNotNullParameter(documentHash, "documentHash");
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(decisionType, "decisionType");
        this.documentHash = documentHash;
        this.sessionId = sessionId;
        this.decisionType = decisionType;
        this.timestampMs = timestampMs;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ UserDecisionEvent(java.lang.String r7, java.lang.String r8, java.lang.String r9, long r10, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r12 = r12 & 8
            if (r12 == 0) goto La
            long r10 = java.lang.System.currentTimeMillis()
            r4 = r10
            goto Lb
        La:
            r4 = r10
        Lb:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.model.UserDecisionEvent.<init>(java.lang.String, java.lang.String, java.lang.String, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getDocumentHash() {
        return this.documentHash;
    }

    public final String getSessionId() {
        return this.sessionId;
    }

    public final String getDecisionType() {
        return this.decisionType;
    }

    public final long getTimestampMs() {
        return this.timestampMs;
    }
}
