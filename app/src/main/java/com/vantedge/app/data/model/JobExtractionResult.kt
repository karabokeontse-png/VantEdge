package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: JobExtractionResult.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\nHÆ\u0003J\t\u0010'\u001a\u00020\fHÆ\u0003J\t\u0010(\u001a\u00020\u000eHÆ\u0003J\t\u0010)\u001a\u00020\u0010HÆ\u0003Jg\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010HÆ\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010.\u001a\u00020/HÖ\u0001J\t\u00100\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u00061"}, d2 = {"Lcom/vantedge/app/data/model/JobExtractionResult;", "", "extractionId", "", "extractedAt", "", "jobTitle", "company", "description", "confidenceScore", "", "confidenceBreakdown", "Lcom/vantedge/app/data/model/ConfidenceBreakdown;", "gate0Result", "Lcom/vantedge/app/data/model/Gate0JobResult;", "metrics", "Lcom/vantedge/app/data/model/JobExtractionMetrics;", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;FLcom/vantedge/app/data/model/ConfidenceBreakdown;Lcom/vantedge/app/data/model/Gate0JobResult;Lcom/vantedge/app/data/model/JobExtractionMetrics;)V", "getCompany", "()Ljava/lang/String;", "getConfidenceBreakdown", "()Lcom/vantedge/app/data/model/ConfidenceBreakdown;", "getConfidenceScore", "()F", "getDescription", "getExtractedAt", "()J", "getExtractionId", "getGate0Result", "()Lcom/vantedge/app/data/model/Gate0JobResult;", "getJobTitle", "getMetrics", "()Lcom/vantedge/app/data/model/JobExtractionMetrics;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class JobExtractionResult {
    public static final int $stable = 8;
    private final String company;
    private final ConfidenceBreakdown confidenceBreakdown;
    private final float confidenceScore;
    private final String description;
    private final long extractedAt;
    private final String extractionId;
    private final Gate0JobResult gate0Result;
    private final String jobTitle;
    private final JobExtractionMetrics metrics;

    /* renamed from: component1, reason: from getter */
    public final String getExtractionId() {
        return this.extractionId;
    }

    /* renamed from: component2, reason: from getter */
    public final long getExtractedAt() {
        return this.extractedAt;
    }

    /* renamed from: component3, reason: from getter */
    public final String getJobTitle() {
        return this.jobTitle;
    }

    /* renamed from: component4, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component6, reason: from getter */
    public final float getConfidenceScore() {
        return this.confidenceScore;
    }

    /* renamed from: component7, reason: from getter */
    public final ConfidenceBreakdown getConfidenceBreakdown() {
        return this.confidenceBreakdown;
    }

    /* renamed from: component8, reason: from getter */
    public final Gate0JobResult getGate0Result() {
        return this.gate0Result;
    }

    /* renamed from: component9, reason: from getter */
    public final JobExtractionMetrics getMetrics() {
        return this.metrics;
    }

    public final JobExtractionResult copy(String extractionId, long extractedAt, String jobTitle, String company, String description, float confidenceScore, ConfidenceBreakdown confidenceBreakdown, Gate0JobResult gate0Result, JobExtractionMetrics metrics) {
        Intrinsics.checkNotNullParameter(extractionId, "extractionId");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(confidenceBreakdown, "confidenceBreakdown");
        Intrinsics.checkNotNullParameter(gate0Result, "gate0Result");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        return new JobExtractionResult(extractionId, extractedAt, jobTitle, company, description, confidenceScore, confidenceBreakdown, gate0Result, metrics);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JobExtractionResult)) {
            return false;
        }
        JobExtractionResult jobExtractionResult = (JobExtractionResult) other;
        return Intrinsics.areEqual(this.extractionId, jobExtractionResult.extractionId) && this.extractedAt == jobExtractionResult.extractedAt && Intrinsics.areEqual(this.jobTitle, jobExtractionResult.jobTitle) && Intrinsics.areEqual(this.company, jobExtractionResult.company) && Intrinsics.areEqual(this.description, jobExtractionResult.description) && Float.compare(this.confidenceScore, jobExtractionResult.confidenceScore) == 0 && Intrinsics.areEqual(this.confidenceBreakdown, jobExtractionResult.confidenceBreakdown) && Intrinsics.areEqual(this.gate0Result, jobExtractionResult.gate0Result) && Intrinsics.areEqual(this.metrics, jobExtractionResult.metrics);
    }

    public int hashCode() {
        return (((((((((((((((this.extractionId.hashCode() * 31) + Long.hashCode(this.extractedAt)) * 31) + (this.jobTitle == null ? 0 : this.jobTitle.hashCode())) * 31) + (this.company != null ? this.company.hashCode() : 0)) * 31) + this.description.hashCode()) * 31) + Float.hashCode(this.confidenceScore)) * 31) + this.confidenceBreakdown.hashCode()) * 31) + this.gate0Result.hashCode()) * 31) + this.metrics.hashCode();
    }

    public String toString() {
        return "JobExtractionResult(extractionId=" + this.extractionId + ", extractedAt=" + this.extractedAt + ", jobTitle=" + this.jobTitle + ", company=" + this.company + ", description=" + this.description + ", confidenceScore=" + this.confidenceScore + ", confidenceBreakdown=" + this.confidenceBreakdown + ", gate0Result=" + this.gate0Result + ", metrics=" + this.metrics + ")";
    }

    public JobExtractionResult(String extractionId, long extractedAt, String jobTitle, String company, String description, float confidenceScore, ConfidenceBreakdown confidenceBreakdown, Gate0JobResult gate0Result, JobExtractionMetrics metrics) {
        Intrinsics.checkNotNullParameter(extractionId, "extractionId");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(confidenceBreakdown, "confidenceBreakdown");
        Intrinsics.checkNotNullParameter(gate0Result, "gate0Result");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        this.extractionId = extractionId;
        this.extractedAt = extractedAt;
        this.jobTitle = jobTitle;
        this.company = company;
        this.description = description;
        this.confidenceScore = confidenceScore;
        this.confidenceBreakdown = confidenceBreakdown;
        this.gate0Result = gate0Result;
        this.metrics = metrics;
    }

    public final String getExtractionId() {
        return this.extractionId;
    }

    public final long getExtractedAt() {
        return this.extractedAt;
    }

    public final String getJobTitle() {
        return this.jobTitle;
    }

    public final String getCompany() {
        return this.company;
    }

    public final String getDescription() {
        return this.description;
    }

    public final float getConfidenceScore() {
        return this.confidenceScore;
    }

    public final ConfidenceBreakdown getConfidenceBreakdown() {
        return this.confidenceBreakdown;
    }

    public final Gate0JobResult getGate0Result() {
        return this.gate0Result;
    }

    public final JobExtractionMetrics getMetrics() {
        return this.metrics;
    }
}
