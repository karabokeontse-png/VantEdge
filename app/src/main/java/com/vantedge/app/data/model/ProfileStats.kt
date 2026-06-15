package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CompatibilityRecord.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\tHÆ\u0003JE\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001e\u001a\u00020\tHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u001f"}, d2 = {"Lcom/vantedge/app/data/model/ProfileStats;", "", "yearsExperience", "", "certificationCount", "skillCount", "matchedCount", "gapCount", "dataIntegrityNote", "", "(IIIIILjava/lang/String;)V", "getCertificationCount", "()I", "getDataIntegrityNote", "()Ljava/lang/String;", "getGapCount", "getMatchedCount", "getSkillCount", "getYearsExperience", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ProfileStats {
    public static final int $stable = 0;
    private final int certificationCount;
    private final String dataIntegrityNote;
    private final int gapCount;
    private final int matchedCount;
    private final int skillCount;
    private final int yearsExperience;

    public static /* synthetic */ ProfileStats copy$default(ProfileStats profileStats, int i, int i2, int i3, int i4, int i5, String str, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i = profileStats.yearsExperience;
        }
        if ((i6 & 2) != 0) {
            i2 = profileStats.certificationCount;
        }
        int i7 = i2;
        if ((i6 & 4) != 0) {
            i3 = profileStats.skillCount;
        }
        int i8 = i3;
        if ((i6 & 8) != 0) {
            i4 = profileStats.matchedCount;
        }
        int i9 = i4;
        if ((i6 & 16) != 0) {
            i5 = profileStats.gapCount;
        }
        int i10 = i5;
        if ((i6 & 32) != 0) {
            str = profileStats.dataIntegrityNote;
        }
        return profileStats.copy(i, i7, i8, i9, i10, str);
    }

    /* renamed from: component1, reason: from getter */
    public final int getYearsExperience() {
        return this.yearsExperience;
    }

    /* renamed from: component2, reason: from getter */
    public final int getCertificationCount() {
        return this.certificationCount;
    }

    /* renamed from: component3, reason: from getter */
    public final int getSkillCount() {
        return this.skillCount;
    }

    /* renamed from: component4, reason: from getter */
    public final int getMatchedCount() {
        return this.matchedCount;
    }

    /* renamed from: component5, reason: from getter */
    public final int getGapCount() {
        return this.gapCount;
    }

    /* renamed from: component6, reason: from getter */
    public final String getDataIntegrityNote() {
        return this.dataIntegrityNote;
    }

    public final ProfileStats copy(int yearsExperience, int certificationCount, int skillCount, int matchedCount, int gapCount, String dataIntegrityNote) {
        Intrinsics.checkNotNullParameter(dataIntegrityNote, "dataIntegrityNote");
        return new ProfileStats(yearsExperience, certificationCount, skillCount, matchedCount, gapCount, dataIntegrityNote);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProfileStats)) {
            return false;
        }
        ProfileStats profileStats = (ProfileStats) other;
        return this.yearsExperience == profileStats.yearsExperience && this.certificationCount == profileStats.certificationCount && this.skillCount == profileStats.skillCount && this.matchedCount == profileStats.matchedCount && this.gapCount == profileStats.gapCount && Intrinsics.areEqual(this.dataIntegrityNote, profileStats.dataIntegrityNote);
    }

    public int hashCode() {
        return (((((((((Integer.hashCode(this.yearsExperience) * 31) + Integer.hashCode(this.certificationCount)) * 31) + Integer.hashCode(this.skillCount)) * 31) + Integer.hashCode(this.matchedCount)) * 31) + Integer.hashCode(this.gapCount)) * 31) + this.dataIntegrityNote.hashCode();
    }

    public String toString() {
        return "ProfileStats(yearsExperience=" + this.yearsExperience + ", certificationCount=" + this.certificationCount + ", skillCount=" + this.skillCount + ", matchedCount=" + this.matchedCount + ", gapCount=" + this.gapCount + ", dataIntegrityNote=" + this.dataIntegrityNote + ")";
    }

    public ProfileStats(int yearsExperience, int certificationCount, int skillCount, int matchedCount, int gapCount, String dataIntegrityNote) {
        Intrinsics.checkNotNullParameter(dataIntegrityNote, "dataIntegrityNote");
        this.yearsExperience = yearsExperience;
        this.certificationCount = certificationCount;
        this.skillCount = skillCount;
        this.matchedCount = matchedCount;
        this.gapCount = gapCount;
        this.dataIntegrityNote = dataIntegrityNote;
    }

    public final int getYearsExperience() {
        return this.yearsExperience;
    }

    public final int getCertificationCount() {
        return this.certificationCount;
    }

    public final int getSkillCount() {
        return this.skillCount;
    }

    public final int getMatchedCount() {
        return this.matchedCount;
    }

    public final int getGapCount() {
        return this.gapCount;
    }

    public final String getDataIntegrityNote() {
        return this.dataIntegrityNote;
    }
}
