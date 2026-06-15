package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: JobExtractionMetrics.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u001f\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0010HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J\t\u0010$\u001a\u00020\u0007HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u000eHÆ\u0003Jm\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010HÆ\u0001J\u0013\u0010,\u001a\u00020\u000e2\b\u0010-\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010.\u001a\u00020\u0007HÖ\u0001J\t\u0010/\u001a\u000200HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u00061"}, d2 = {"Lcom/vantedge/app/data/model/JobExtractionMetrics;", "", "durationMs", "", "sourceType", "Lcom/vantedge/app/data/model/JobSourceType;", "rawTextLength", "", "gate0DurationMs", "gate1DurationMs", "gate2DurationMs", "gate3DurationMs", "gate4DurationMs", "qualificationPassed", "", "narrativeDensity", "", "(JLcom/vantedge/app/data/model/JobSourceType;IJJJJJZF)V", "getDurationMs", "()J", "getGate0DurationMs", "getGate1DurationMs", "getGate2DurationMs", "getGate3DurationMs", "getGate4DurationMs", "getNarrativeDensity", "()F", "getQualificationPassed", "()Z", "getRawTextLength", "()I", "getSourceType", "()Lcom/vantedge/app/data/model/JobSourceType;", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class JobExtractionMetrics {
    public static final int $stable = 0;
    private final long durationMs;
    private final long gate0DurationMs;
    private final long gate1DurationMs;
    private final long gate2DurationMs;
    private final long gate3DurationMs;
    private final long gate4DurationMs;
    private final float narrativeDensity;
    private final boolean qualificationPassed;
    private final int rawTextLength;
    private final JobSourceType sourceType;

    /* renamed from: component1, reason: from getter */
    public final long getDurationMs() {
        return this.durationMs;
    }

    /* renamed from: component10, reason: from getter */
    public final float getNarrativeDensity() {
        return this.narrativeDensity;
    }

    /* renamed from: component2, reason: from getter */
    public final JobSourceType getSourceType() {
        return this.sourceType;
    }

    /* renamed from: component3, reason: from getter */
    public final int getRawTextLength() {
        return this.rawTextLength;
    }

    /* renamed from: component4, reason: from getter */
    public final long getGate0DurationMs() {
        return this.gate0DurationMs;
    }

    /* renamed from: component5, reason: from getter */
    public final long getGate1DurationMs() {
        return this.gate1DurationMs;
    }

    /* renamed from: component6, reason: from getter */
    public final long getGate2DurationMs() {
        return this.gate2DurationMs;
    }

    /* renamed from: component7, reason: from getter */
    public final long getGate3DurationMs() {
        return this.gate3DurationMs;
    }

    /* renamed from: component8, reason: from getter */
    public final long getGate4DurationMs() {
        return this.gate4DurationMs;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean getQualificationPassed() {
        return this.qualificationPassed;
    }

    public final JobExtractionMetrics copy(long durationMs, JobSourceType sourceType, int rawTextLength, long gate0DurationMs, long gate1DurationMs, long gate2DurationMs, long gate3DurationMs, long gate4DurationMs, boolean qualificationPassed, float narrativeDensity) {
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        return new JobExtractionMetrics(durationMs, sourceType, rawTextLength, gate0DurationMs, gate1DurationMs, gate2DurationMs, gate3DurationMs, gate4DurationMs, qualificationPassed, narrativeDensity);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JobExtractionMetrics)) {
            return false;
        }
        JobExtractionMetrics jobExtractionMetrics = (JobExtractionMetrics) other;
        return this.durationMs == jobExtractionMetrics.durationMs && this.sourceType == jobExtractionMetrics.sourceType && this.rawTextLength == jobExtractionMetrics.rawTextLength && this.gate0DurationMs == jobExtractionMetrics.gate0DurationMs && this.gate1DurationMs == jobExtractionMetrics.gate1DurationMs && this.gate2DurationMs == jobExtractionMetrics.gate2DurationMs && this.gate3DurationMs == jobExtractionMetrics.gate3DurationMs && this.gate4DurationMs == jobExtractionMetrics.gate4DurationMs && this.qualificationPassed == jobExtractionMetrics.qualificationPassed && Float.compare(this.narrativeDensity, jobExtractionMetrics.narrativeDensity) == 0;
    }

    public int hashCode() {
        return (((((((((((((((((Long.hashCode(this.durationMs) * 31) + this.sourceType.hashCode()) * 31) + Integer.hashCode(this.rawTextLength)) * 31) + Long.hashCode(this.gate0DurationMs)) * 31) + Long.hashCode(this.gate1DurationMs)) * 31) + Long.hashCode(this.gate2DurationMs)) * 31) + Long.hashCode(this.gate3DurationMs)) * 31) + Long.hashCode(this.gate4DurationMs)) * 31) + Boolean.hashCode(this.qualificationPassed)) * 31) + Float.hashCode(this.narrativeDensity);
    }

    public String toString() {
        return "JobExtractionMetrics(durationMs=" + this.durationMs + ", sourceType=" + this.sourceType + ", rawTextLength=" + this.rawTextLength + ", gate0DurationMs=" + this.gate0DurationMs + ", gate1DurationMs=" + this.gate1DurationMs + ", gate2DurationMs=" + this.gate2DurationMs + ", gate3DurationMs=" + this.gate3DurationMs + ", gate4DurationMs=" + this.gate4DurationMs + ", qualificationPassed=" + this.qualificationPassed + ", narrativeDensity=" + this.narrativeDensity + ")";
    }

    public JobExtractionMetrics(long durationMs, JobSourceType sourceType, int rawTextLength, long gate0DurationMs, long gate1DurationMs, long gate2DurationMs, long gate3DurationMs, long gate4DurationMs, boolean qualificationPassed, float narrativeDensity) {
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        this.durationMs = durationMs;
        this.sourceType = sourceType;
        this.rawTextLength = rawTextLength;
        this.gate0DurationMs = gate0DurationMs;
        this.gate1DurationMs = gate1DurationMs;
        this.gate2DurationMs = gate2DurationMs;
        this.gate3DurationMs = gate3DurationMs;
        this.gate4DurationMs = gate4DurationMs;
        this.qualificationPassed = qualificationPassed;
        this.narrativeDensity = narrativeDensity;
    }

    public final long getDurationMs() {
        return this.durationMs;
    }

    public final JobSourceType getSourceType() {
        return this.sourceType;
    }

    public final int getRawTextLength() {
        return this.rawTextLength;
    }

    public final long getGate0DurationMs() {
        return this.gate0DurationMs;
    }

    public final long getGate1DurationMs() {
        return this.gate1DurationMs;
    }

    public final long getGate2DurationMs() {
        return this.gate2DurationMs;
    }

    public final long getGate3DurationMs() {
        return this.gate3DurationMs;
    }

    public final long getGate4DurationMs() {
        return this.gate4DurationMs;
    }

    public final boolean getQualificationPassed() {
        return this.qualificationPassed;
    }

    public final float getNarrativeDensity() {
        return this.narrativeDensity;
    }
}
