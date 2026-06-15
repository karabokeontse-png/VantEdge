package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ProfileExtractionEngine.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J\t\u0010\u0019\u001a\u00020\nHÆ\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006 "}, d2 = {"Lcom/vantedge/app/data/engine/Gate0Result;", "", "score", "", "threshold", "accepted", "", "reason", "Lcom/vantedge/app/data/engine/Gate0Reason;", "extractionMode", "Lcom/vantedge/app/data/engine/ExtractionMode;", "(IIZLcom/vantedge/app/data/engine/Gate0Reason;Lcom/vantedge/app/data/engine/ExtractionMode;)V", "getAccepted", "()Z", "getExtractionMode", "()Lcom/vantedge/app/data/engine/ExtractionMode;", "getReason", "()Lcom/vantedge/app/data/engine/Gate0Reason;", "getScore", "()I", "getThreshold", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class Gate0Result {
    public static final int $stable = 0;
    private final boolean accepted;
    private final ExtractionMode extractionMode;
    private final Gate0Reason reason;
    private final int score;
    private final int threshold;

    public static /* synthetic */ Gate0Result copy$default(Gate0Result gate0Result, int i, int i2, boolean z, Gate0Reason gate0Reason, ExtractionMode extractionMode, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = gate0Result.score;
        }
        if ((i3 & 2) != 0) {
            i2 = gate0Result.threshold;
        }
        int i4 = i2;
        if ((i3 & 4) != 0) {
            z = gate0Result.accepted;
        }
        boolean z2 = z;
        if ((i3 & 8) != 0) {
            gate0Reason = gate0Result.reason;
        }
        Gate0Reason gate0Reason2 = gate0Reason;
        if ((i3 & 16) != 0) {
            extractionMode = gate0Result.extractionMode;
        }
        return gate0Result.copy(i, i4, z2, gate0Reason2, extractionMode);
    }

    /* renamed from: component1, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    /* renamed from: component2, reason: from getter */
    public final int getThreshold() {
        return this.threshold;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean getAccepted() {
        return this.accepted;
    }

    /* renamed from: component4, reason: from getter */
    public final Gate0Reason getReason() {
        return this.reason;
    }

    /* renamed from: component5, reason: from getter */
    public final ExtractionMode getExtractionMode() {
        return this.extractionMode;
    }

    public final Gate0Result copy(int score, int threshold, boolean accepted, Gate0Reason reason, ExtractionMode extractionMode) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(extractionMode, "extractionMode");
        return new Gate0Result(score, threshold, accepted, reason, extractionMode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Gate0Result)) {
            return false;
        }
        Gate0Result gate0Result = (Gate0Result) other;
        return this.score == gate0Result.score && this.threshold == gate0Result.threshold && this.accepted == gate0Result.accepted && this.reason == gate0Result.reason && this.extractionMode == gate0Result.extractionMode;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.score) * 31) + Integer.hashCode(this.threshold)) * 31) + Boolean.hashCode(this.accepted)) * 31) + this.reason.hashCode()) * 31) + this.extractionMode.hashCode();
    }

    public String toString() {
        return "Gate0Result(score=" + this.score + ", threshold=" + this.threshold + ", accepted=" + this.accepted + ", reason=" + this.reason + ", extractionMode=" + this.extractionMode + ")";
    }

    public Gate0Result(int score, int threshold, boolean accepted, Gate0Reason reason, ExtractionMode extractionMode) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(extractionMode, "extractionMode");
        this.score = score;
        this.threshold = threshold;
        this.accepted = accepted;
        this.reason = reason;
        this.extractionMode = extractionMode;
    }

    public final int getScore() {
        return this.score;
    }

    public final int getThreshold() {
        return this.threshold;
    }

    public final boolean getAccepted() {
        return this.accepted;
    }

    public final Gate0Reason getReason() {
        return this.reason;
    }

    public final ExtractionMode getExtractionMode() {
        return this.extractionMode;
    }
}
