package com.vantedge.app.data.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConfidenceBreakdown.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\n\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003JK\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\n\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020\tHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r¨\u0006!"}, d2 = {"Lcom/vantedge/app/data/model/ConfidenceBreakdown;", "", "baseScore", "", "titleContribution", "companyContribution", "qualificationContribution", "penalties", "", "", "finalScore", "(FFFFLjava/util/List;F)V", "getBaseScore", "()F", "getCompanyContribution", "getFinalScore", "getPenalties", "()Ljava/util/List;", "getQualificationContribution", "getTitleContribution", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ConfidenceBreakdown {
    public static final int $stable = 8;
    private final float baseScore;
    private final float companyContribution;
    private final float finalScore;
    private final List<String> penalties;
    private final float qualificationContribution;
    private final float titleContribution;

    public static /* synthetic */ ConfidenceBreakdown copy$default(ConfidenceBreakdown confidenceBreakdown, float f, float f2, float f3, float f4, List list, float f5, int i, Object obj) {
        if ((i & 1) != 0) {
            f = confidenceBreakdown.baseScore;
        }
        if ((i & 2) != 0) {
            f2 = confidenceBreakdown.titleContribution;
        }
        float f6 = f2;
        if ((i & 4) != 0) {
            f3 = confidenceBreakdown.companyContribution;
        }
        float f7 = f3;
        if ((i & 8) != 0) {
            f4 = confidenceBreakdown.qualificationContribution;
        }
        float f8 = f4;
        if ((i & 16) != 0) {
            list = confidenceBreakdown.penalties;
        }
        List list2 = list;
        if ((i & 32) != 0) {
            f5 = confidenceBreakdown.finalScore;
        }
        return confidenceBreakdown.copy(f, f6, f7, f8, list2, f5);
    }

    /* renamed from: component1, reason: from getter */
    public final float getBaseScore() {
        return this.baseScore;
    }

    /* renamed from: component2, reason: from getter */
    public final float getTitleContribution() {
        return this.titleContribution;
    }

    /* renamed from: component3, reason: from getter */
    public final float getCompanyContribution() {
        return this.companyContribution;
    }

    /* renamed from: component4, reason: from getter */
    public final float getQualificationContribution() {
        return this.qualificationContribution;
    }

    public final List<String> component5() {
        return this.penalties;
    }

    /* renamed from: component6, reason: from getter */
    public final float getFinalScore() {
        return this.finalScore;
    }

    public final ConfidenceBreakdown copy(float baseScore, float titleContribution, float companyContribution, float qualificationContribution, List<String> penalties, float finalScore) {
        Intrinsics.checkNotNullParameter(penalties, "penalties");
        return new ConfidenceBreakdown(baseScore, titleContribution, companyContribution, qualificationContribution, penalties, finalScore);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConfidenceBreakdown)) {
            return false;
        }
        ConfidenceBreakdown confidenceBreakdown = (ConfidenceBreakdown) other;
        return Float.compare(this.baseScore, confidenceBreakdown.baseScore) == 0 && Float.compare(this.titleContribution, confidenceBreakdown.titleContribution) == 0 && Float.compare(this.companyContribution, confidenceBreakdown.companyContribution) == 0 && Float.compare(this.qualificationContribution, confidenceBreakdown.qualificationContribution) == 0 && Intrinsics.areEqual(this.penalties, confidenceBreakdown.penalties) && Float.compare(this.finalScore, confidenceBreakdown.finalScore) == 0;
    }

    public int hashCode() {
        return (((((((((Float.hashCode(this.baseScore) * 31) + Float.hashCode(this.titleContribution)) * 31) + Float.hashCode(this.companyContribution)) * 31) + Float.hashCode(this.qualificationContribution)) * 31) + this.penalties.hashCode()) * 31) + Float.hashCode(this.finalScore);
    }

    public String toString() {
        return "ConfidenceBreakdown(baseScore=" + this.baseScore + ", titleContribution=" + this.titleContribution + ", companyContribution=" + this.companyContribution + ", qualificationContribution=" + this.qualificationContribution + ", penalties=" + this.penalties + ", finalScore=" + this.finalScore + ")";
    }

    public ConfidenceBreakdown(float baseScore, float titleContribution, float companyContribution, float qualificationContribution, List<String> penalties, float finalScore) {
        Intrinsics.checkNotNullParameter(penalties, "penalties");
        this.baseScore = baseScore;
        this.titleContribution = titleContribution;
        this.companyContribution = companyContribution;
        this.qualificationContribution = qualificationContribution;
        this.penalties = penalties;
        this.finalScore = finalScore;
    }

    public final float getBaseScore() {
        return this.baseScore;
    }

    public final float getTitleContribution() {
        return this.titleContribution;
    }

    public final float getCompanyContribution() {
        return this.companyContribution;
    }

    public final float getQualificationContribution() {
        return this.qualificationContribution;
    }

    public final List<String> getPenalties() {
        return this.penalties;
    }

    public final float getFinalScore() {
        return this.finalScore;
    }
}
