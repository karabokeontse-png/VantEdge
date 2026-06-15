package com.vantedge.app.data.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Gate0JobResult.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u001c\b\u0087\b\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0006HÆ\u0003J\t\u0010!\u001a\u00020\bHÆ\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\nHÆ\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\nHÆ\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000b0\nHÆ\u0003J\t\u0010%\u001a\u00020\u000fHÆ\u0003Jk\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u000e\u001a\u00020\u000fHÆ\u0001J\u0013\u0010'\u001a\u00020\u00062\b\u0010(\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010)\u001a\u00020\u0003HÖ\u0001J\t\u0010*\u001a\u00020\u000bHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001c¨\u0006+"}, d2 = {"Lcom/vantedge/app/data/model/Gate0JobResult;", "", "score", "", "threshold", "accepted", "", "reason", "Lcom/vantedge/app/data/model/Gate0JobReason;", "detectedSignals", "", "", "appliedPenalties", "rejectionCauses", "narrativeDensity", "", "(IIZLcom/vantedge/app/data/model/Gate0JobReason;Ljava/util/List;Ljava/util/List;Ljava/util/List;F)V", "getAccepted", "()Z", "getAppliedPenalties", "()Ljava/util/List;", "getDetectedSignals", "getNarrativeDensity", "()F", "getReason", "()Lcom/vantedge/app/data/model/Gate0JobReason;", "getRejectionCauses", "getScore", "()I", "getThreshold", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Gate0JobResult {
    public static final int $stable = 8;
    private final boolean accepted;
    private final List<String> appliedPenalties;
    private final List<String> detectedSignals;
    private final float narrativeDensity;
    private final Gate0JobReason reason;
    private final List<String> rejectionCauses;
    private final int score;
    private final int threshold;

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
    public final Gate0JobReason getReason() {
        return this.reason;
    }

    public final List<String> component5() {
        return this.detectedSignals;
    }

    public final List<String> component6() {
        return this.appliedPenalties;
    }

    public final List<String> component7() {
        return this.rejectionCauses;
    }

    /* renamed from: component8, reason: from getter */
    public final float getNarrativeDensity() {
        return this.narrativeDensity;
    }

    public final Gate0JobResult copy(int score, int threshold, boolean accepted, Gate0JobReason reason, List<String> detectedSignals, List<String> appliedPenalties, List<String> rejectionCauses, float narrativeDensity) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(detectedSignals, "detectedSignals");
        Intrinsics.checkNotNullParameter(appliedPenalties, "appliedPenalties");
        Intrinsics.checkNotNullParameter(rejectionCauses, "rejectionCauses");
        return new Gate0JobResult(score, threshold, accepted, reason, detectedSignals, appliedPenalties, rejectionCauses, narrativeDensity);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Gate0JobResult)) {
            return false;
        }
        Gate0JobResult gate0JobResult = (Gate0JobResult) other;
        return this.score == gate0JobResult.score && this.threshold == gate0JobResult.threshold && this.accepted == gate0JobResult.accepted && this.reason == gate0JobResult.reason && Intrinsics.areEqual(this.detectedSignals, gate0JobResult.detectedSignals) && Intrinsics.areEqual(this.appliedPenalties, gate0JobResult.appliedPenalties) && Intrinsics.areEqual(this.rejectionCauses, gate0JobResult.rejectionCauses) && Float.compare(this.narrativeDensity, gate0JobResult.narrativeDensity) == 0;
    }

    public int hashCode() {
        return (((((((((((((Integer.hashCode(this.score) * 31) + Integer.hashCode(this.threshold)) * 31) + Boolean.hashCode(this.accepted)) * 31) + this.reason.hashCode()) * 31) + this.detectedSignals.hashCode()) * 31) + this.appliedPenalties.hashCode()) * 31) + this.rejectionCauses.hashCode()) * 31) + Float.hashCode(this.narrativeDensity);
    }

    public String toString() {
        return "Gate0JobResult(score=" + this.score + ", threshold=" + this.threshold + ", accepted=" + this.accepted + ", reason=" + this.reason + ", detectedSignals=" + this.detectedSignals + ", appliedPenalties=" + this.appliedPenalties + ", rejectionCauses=" + this.rejectionCauses + ", narrativeDensity=" + this.narrativeDensity + ")";
    }

    public Gate0JobResult(int score, int threshold, boolean accepted, Gate0JobReason reason, List<String> detectedSignals, List<String> appliedPenalties, List<String> rejectionCauses, float narrativeDensity) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(detectedSignals, "detectedSignals");
        Intrinsics.checkNotNullParameter(appliedPenalties, "appliedPenalties");
        Intrinsics.checkNotNullParameter(rejectionCauses, "rejectionCauses");
        this.score = score;
        this.threshold = threshold;
        this.accepted = accepted;
        this.reason = reason;
        this.detectedSignals = detectedSignals;
        this.appliedPenalties = appliedPenalties;
        this.rejectionCauses = rejectionCauses;
        this.narrativeDensity = narrativeDensity;
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

    public final Gate0JobReason getReason() {
        return this.reason;
    }

    public final List<String> getDetectedSignals() {
        return this.detectedSignals;
    }

    public final List<String> getAppliedPenalties() {
        return this.appliedPenalties;
    }

    public final List<String> getRejectionCauses() {
        return this.rejectionCauses;
    }

    public final float getNarrativeDensity() {
        return this.narrativeDensity;
    }
}
