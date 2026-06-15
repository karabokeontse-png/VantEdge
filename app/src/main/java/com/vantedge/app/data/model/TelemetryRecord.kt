package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TelemetryRecord.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001d\b\u0087\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001e\u001a\u00020\bHÆ\u0003J\t\u0010\u001f\u001a\u00020\bHÆ\u0003J\t\u0010 \u001a\u00020\u000bHÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003JY\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u0003HÆ\u0001J\u0013\u0010$\u001a\u00020\u000b2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\bHÖ\u0001J\t\u0010'\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006("}, d2 = {"Lcom/vantedge/app/data/model/TelemetryRecord;", "", "documentHash", "", "sessionId", "timestampMs", "", "gate0Score", "", "gate0Threshold", "gate0Accepted", "", "gate0Reason", "extractionMode", "(Ljava/lang/String;Ljava/lang/String;JIIZLjava/lang/String;Ljava/lang/String;)V", "getDocumentHash", "()Ljava/lang/String;", "getExtractionMode", "getGate0Accepted", "()Z", "getGate0Reason", "getGate0Score", "()I", "getGate0Threshold", "getSessionId", "getTimestampMs", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class TelemetryRecord {
    public static final int $stable = 0;
    private final String documentHash;
    private final String extractionMode;
    private final boolean gate0Accepted;
    private final String gate0Reason;
    private final int gate0Score;
    private final int gate0Threshold;
    private final String sessionId;
    private final long timestampMs;

    /* renamed from: component1, reason: from getter */
    public final String getDocumentHash() {
        return this.documentHash;
    }

    /* renamed from: component2, reason: from getter */
    public final String getSessionId() {
        return this.sessionId;
    }

    /* renamed from: component3, reason: from getter */
    public final long getTimestampMs() {
        return this.timestampMs;
    }

    /* renamed from: component4, reason: from getter */
    public final int getGate0Score() {
        return this.gate0Score;
    }

    /* renamed from: component5, reason: from getter */
    public final int getGate0Threshold() {
        return this.gate0Threshold;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getGate0Accepted() {
        return this.gate0Accepted;
    }

    /* renamed from: component7, reason: from getter */
    public final String getGate0Reason() {
        return this.gate0Reason;
    }

    /* renamed from: component8, reason: from getter */
    public final String getExtractionMode() {
        return this.extractionMode;
    }

    public final TelemetryRecord copy(String documentHash, String sessionId, long timestampMs, int gate0Score, int gate0Threshold, boolean gate0Accepted, String gate0Reason, String extractionMode) {
        Intrinsics.checkNotNullParameter(documentHash, "documentHash");
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(gate0Reason, "gate0Reason");
        Intrinsics.checkNotNullParameter(extractionMode, "extractionMode");
        return new TelemetryRecord(documentHash, sessionId, timestampMs, gate0Score, gate0Threshold, gate0Accepted, gate0Reason, extractionMode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TelemetryRecord)) {
            return false;
        }
        TelemetryRecord telemetryRecord = (TelemetryRecord) other;
        return Intrinsics.areEqual(this.documentHash, telemetryRecord.documentHash) && Intrinsics.areEqual(this.sessionId, telemetryRecord.sessionId) && this.timestampMs == telemetryRecord.timestampMs && this.gate0Score == telemetryRecord.gate0Score && this.gate0Threshold == telemetryRecord.gate0Threshold && this.gate0Accepted == telemetryRecord.gate0Accepted && Intrinsics.areEqual(this.gate0Reason, telemetryRecord.gate0Reason) && Intrinsics.areEqual(this.extractionMode, telemetryRecord.extractionMode);
    }

    public int hashCode() {
        return (((((((((((((this.documentHash.hashCode() * 31) + this.sessionId.hashCode()) * 31) + Long.hashCode(this.timestampMs)) * 31) + Integer.hashCode(this.gate0Score)) * 31) + Integer.hashCode(this.gate0Threshold)) * 31) + Boolean.hashCode(this.gate0Accepted)) * 31) + this.gate0Reason.hashCode()) * 31) + this.extractionMode.hashCode();
    }

    public String toString() {
        return "TelemetryRecord(documentHash=" + this.documentHash + ", sessionId=" + this.sessionId + ", timestampMs=" + this.timestampMs + ", gate0Score=" + this.gate0Score + ", gate0Threshold=" + this.gate0Threshold + ", gate0Accepted=" + this.gate0Accepted + ", gate0Reason=" + this.gate0Reason + ", extractionMode=" + this.extractionMode + ")";
    }

    public TelemetryRecord(String documentHash, String sessionId, long timestampMs, int gate0Score, int gate0Threshold, boolean gate0Accepted, String gate0Reason, String extractionMode) {
        Intrinsics.checkNotNullParameter(documentHash, "documentHash");
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        Intrinsics.checkNotNullParameter(gate0Reason, "gate0Reason");
        Intrinsics.checkNotNullParameter(extractionMode, "extractionMode");
        this.documentHash = documentHash;
        this.sessionId = sessionId;
        this.timestampMs = timestampMs;
        this.gate0Score = gate0Score;
        this.gate0Threshold = gate0Threshold;
        this.gate0Accepted = gate0Accepted;
        this.gate0Reason = gate0Reason;
        this.extractionMode = extractionMode;
    }

    public final String getDocumentHash() {
        return this.documentHash;
    }

    public final String getSessionId() {
        return this.sessionId;
    }

    public final long getTimestampMs() {
        return this.timestampMs;
    }

    public final int getGate0Score() {
        return this.gate0Score;
    }

    public final int getGate0Threshold() {
        return this.gate0Threshold;
    }

    public final boolean getGate0Accepted() {
        return this.gate0Accepted;
    }

    public final String getGate0Reason() {
        return this.gate0Reason;
    }

    public final String getExtractionMode() {
        return this.extractionMode;
    }
}
