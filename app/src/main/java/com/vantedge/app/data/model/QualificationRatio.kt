package com.vantedge.app.data.model;

import kotlin.Metadata;

/* compiled from: CompatibilityRecord.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/vantedge/app/data/model/QualificationRatio;", "", "matched", "", "total", "gaps", "(III)V", "getGaps", "()I", "getMatched", "getTotal", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class QualificationRatio {
    public static final int $stable = 0;
    private final int gaps;
    private final int matched;
    private final int total;

    public static /* synthetic */ QualificationRatio copy$default(QualificationRatio qualificationRatio, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = qualificationRatio.matched;
        }
        if ((i4 & 2) != 0) {
            i2 = qualificationRatio.total;
        }
        if ((i4 & 4) != 0) {
            i3 = qualificationRatio.gaps;
        }
        return qualificationRatio.copy(i, i2, i3);
    }

    /* renamed from: component1, reason: from getter */
    public final int getMatched() {
        return this.matched;
    }

    /* renamed from: component2, reason: from getter */
    public final int getTotal() {
        return this.total;
    }

    /* renamed from: component3, reason: from getter */
    public final int getGaps() {
        return this.gaps;
    }

    public final QualificationRatio copy(int matched, int total, int gaps) {
        return new QualificationRatio(matched, total, gaps);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof QualificationRatio)) {
            return false;
        }
        QualificationRatio qualificationRatio = (QualificationRatio) other;
        return this.matched == qualificationRatio.matched && this.total == qualificationRatio.total && this.gaps == qualificationRatio.gaps;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.matched) * 31) + Integer.hashCode(this.total)) * 31) + Integer.hashCode(this.gaps);
    }

    public String toString() {
        return "QualificationRatio(matched=" + this.matched + ", total=" + this.total + ", gaps=" + this.gaps + ")";
    }

    public QualificationRatio(int matched, int total, int gaps) {
        this.matched = matched;
        this.total = total;
        this.gaps = gaps;
    }

    public final int getMatched() {
        return this.matched;
    }

    public final int getTotal() {
        return this.total;
    }

    public final int getGaps() {
        return this.gaps;
    }
}
