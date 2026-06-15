package com.vantedge.app.data.model;

import com.vantedge.app.BuildConfig;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CompatibilityRecord.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013\u0012\u0006\u0010\u0017\u001a\u00020\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0003¢\u0006\u0002\u0010\u001aJ\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u000fHÆ\u0003J\t\u00103\u001a\u00020\u0011HÆ\u0003J\u000f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013HÆ\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013HÆ\u0003J\t\u00106\u001a\u00020\tHÆ\u0003J\t\u00107\u001a\u00020\u0005HÆ\u0003J\t\u00108\u001a\u00020\u0003HÆ\u0003J\t\u00109\u001a\u00020\u0005HÆ\u0003J\t\u0010:\u001a\u00020\u0005HÆ\u0003J\t\u0010;\u001a\u00020\u0005HÆ\u0003J\t\u0010<\u001a\u00020\tHÆ\u0003J\t\u0010=\u001a\u00020\tHÆ\u0003J\t\u0010>\u001a\u00020\u0005HÆ\u0003J\t\u0010?\u001a\u00020\u0005HÆ\u0003J\t\u0010@\u001a\u00020\u0005HÆ\u0003Jµ\u0001\u0010A\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00132\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u0003HÆ\u0001J\u0013\u0010B\u001a\u00020C2\b\u0010D\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010E\u001a\u00020\tHÖ\u0001J\t\u0010F\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0019\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0017\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001cR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001eR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001cR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\b\n\u0000\u001a\u0004\b,\u0010$R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001cR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0011\u0010\u0018\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001cR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b0\u0010 ¨\u0006G"}, d2 = {"Lcom/vantedge/app/data/model/CompatibilityRecord;", "", "id", "", "jobTitle", "", "company", "jobDescription", "score", "", "vacancyScore", "roleSummary", "eligibilitySummary", "dataIntegrityNote", "profileStats", "Lcom/vantedge/app/data/model/ProfileStats;", "qualificationRatio", "Lcom/vantedge/app/data/model/QualificationRatio;", "relevancyItems", "", "Lcom/vantedge/app/data/model/RelevancyItem;", "gaps", "Lcom/vantedge/app/data/model/GapItem;", "criticalGapCount", "scoringVersion", "createdAt", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/ProfileStats;Lcom/vantedge/app/data/model/QualificationRatio;Ljava/util/List;Ljava/util/List;ILjava/lang/String;J)V", "getCompany", "()Ljava/lang/String;", "getCreatedAt", "()J", "getCriticalGapCount", "()I", "getDataIntegrityNote", "getEligibilitySummary", "getGaps", "()Ljava/util/List;", "getId", "getJobDescription", "getJobTitle", "getProfileStats", "()Lcom/vantedge/app/data/model/ProfileStats;", "getQualificationRatio", "()Lcom/vantedge/app/data/model/QualificationRatio;", "getRelevancyItems", "getRoleSummary", "getScore", "getScoringVersion", "getVacancyScore", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class CompatibilityRecord {
    public static final int $stable = 8;
    private final String company;
    private final long createdAt;
    private final int criticalGapCount;
    private final String dataIntegrityNote;
    private final String eligibilitySummary;
    private final List<GapItem> gaps;
    private final long id;
    private final String jobDescription;
    private final String jobTitle;
    private final ProfileStats profileStats;
    private final QualificationRatio qualificationRatio;
    private final List<RelevancyItem> relevancyItems;
    private final String roleSummary;
    private final int score;
    private final String scoringVersion;
    private final int vacancyScore;

    /* renamed from: component1, reason: from getter */
    public final long getId() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final ProfileStats getProfileStats() {
        return this.profileStats;
    }

    /* renamed from: component11, reason: from getter */
    public final QualificationRatio getQualificationRatio() {
        return this.qualificationRatio;
    }

    public final List<RelevancyItem> component12() {
        return this.relevancyItems;
    }

    public final List<GapItem> component13() {
        return this.gaps;
    }

    /* renamed from: component14, reason: from getter */
    public final int getCriticalGapCount() {
        return this.criticalGapCount;
    }

    /* renamed from: component15, reason: from getter */
    public final String getScoringVersion() {
        return this.scoringVersion;
    }

    /* renamed from: component16, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* renamed from: component2, reason: from getter */
    public final String getJobTitle() {
        return this.jobTitle;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    /* renamed from: component4, reason: from getter */
    public final String getJobDescription() {
        return this.jobDescription;
    }

    /* renamed from: component5, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    /* renamed from: component6, reason: from getter */
    public final int getVacancyScore() {
        return this.vacancyScore;
    }

    /* renamed from: component7, reason: from getter */
    public final String getRoleSummary() {
        return this.roleSummary;
    }

    /* renamed from: component8, reason: from getter */
    public final String getEligibilitySummary() {
        return this.eligibilitySummary;
    }

    /* renamed from: component9, reason: from getter */
    public final String getDataIntegrityNote() {
        return this.dataIntegrityNote;
    }

    public final CompatibilityRecord copy(long id, String jobTitle, String company, String jobDescription, int score, int vacancyScore, String roleSummary, String eligibilitySummary, String dataIntegrityNote, ProfileStats profileStats, QualificationRatio qualificationRatio, List<RelevancyItem> relevancyItems, List<GapItem> gaps, int criticalGapCount, String scoringVersion, long createdAt) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        Intrinsics.checkNotNullParameter(roleSummary, "roleSummary");
        Intrinsics.checkNotNullParameter(eligibilitySummary, "eligibilitySummary");
        Intrinsics.checkNotNullParameter(dataIntegrityNote, "dataIntegrityNote");
        Intrinsics.checkNotNullParameter(profileStats, "profileStats");
        Intrinsics.checkNotNullParameter(qualificationRatio, "qualificationRatio");
        Intrinsics.checkNotNullParameter(relevancyItems, "relevancyItems");
        Intrinsics.checkNotNullParameter(gaps, "gaps");
        Intrinsics.checkNotNullParameter(scoringVersion, "scoringVersion");
        return new CompatibilityRecord(id, jobTitle, company, jobDescription, score, vacancyScore, roleSummary, eligibilitySummary, dataIntegrityNote, profileStats, qualificationRatio, relevancyItems, gaps, criticalGapCount, scoringVersion, createdAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompatibilityRecord)) {
            return false;
        }
        CompatibilityRecord compatibilityRecord = (CompatibilityRecord) other;
        return this.id == compatibilityRecord.id && Intrinsics.areEqual(this.jobTitle, compatibilityRecord.jobTitle) && Intrinsics.areEqual(this.company, compatibilityRecord.company) && Intrinsics.areEqual(this.jobDescription, compatibilityRecord.jobDescription) && this.score == compatibilityRecord.score && this.vacancyScore == compatibilityRecord.vacancyScore && Intrinsics.areEqual(this.roleSummary, compatibilityRecord.roleSummary) && Intrinsics.areEqual(this.eligibilitySummary, compatibilityRecord.eligibilitySummary) && Intrinsics.areEqual(this.dataIntegrityNote, compatibilityRecord.dataIntegrityNote) && Intrinsics.areEqual(this.profileStats, compatibilityRecord.profileStats) && Intrinsics.areEqual(this.qualificationRatio, compatibilityRecord.qualificationRatio) && Intrinsics.areEqual(this.relevancyItems, compatibilityRecord.relevancyItems) && Intrinsics.areEqual(this.gaps, compatibilityRecord.gaps) && this.criticalGapCount == compatibilityRecord.criticalGapCount && Intrinsics.areEqual(this.scoringVersion, compatibilityRecord.scoringVersion) && this.createdAt == compatibilityRecord.createdAt;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((Long.hashCode(this.id) * 31) + this.jobTitle.hashCode()) * 31) + this.company.hashCode()) * 31) + this.jobDescription.hashCode()) * 31) + Integer.hashCode(this.score)) * 31) + Integer.hashCode(this.vacancyScore)) * 31) + this.roleSummary.hashCode()) * 31) + this.eligibilitySummary.hashCode()) * 31) + this.dataIntegrityNote.hashCode()) * 31) + this.profileStats.hashCode()) * 31) + this.qualificationRatio.hashCode()) * 31) + this.relevancyItems.hashCode()) * 31) + this.gaps.hashCode()) * 31) + Integer.hashCode(this.criticalGapCount)) * 31) + this.scoringVersion.hashCode()) * 31) + Long.hashCode(this.createdAt);
    }

    public String toString() {
        return "CompatibilityRecord(id=" + this.id + ", jobTitle=" + this.jobTitle + ", company=" + this.company + ", jobDescription=" + this.jobDescription + ", score=" + this.score + ", vacancyScore=" + this.vacancyScore + ", roleSummary=" + this.roleSummary + ", eligibilitySummary=" + this.eligibilitySummary + ", dataIntegrityNote=" + this.dataIntegrityNote + ", profileStats=" + this.profileStats + ", qualificationRatio=" + this.qualificationRatio + ", relevancyItems=" + this.relevancyItems + ", gaps=" + this.gaps + ", criticalGapCount=" + this.criticalGapCount + ", scoringVersion=" + this.scoringVersion + ", createdAt=" + this.createdAt + ")";
    }

    public CompatibilityRecord(long id, String jobTitle, String company, String jobDescription, int score, int vacancyScore, String roleSummary, String eligibilitySummary, String dataIntegrityNote, ProfileStats profileStats, QualificationRatio qualificationRatio, List<RelevancyItem> relevancyItems, List<GapItem> gaps, int criticalGapCount, String scoringVersion, long createdAt) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        Intrinsics.checkNotNullParameter(roleSummary, "roleSummary");
        Intrinsics.checkNotNullParameter(eligibilitySummary, "eligibilitySummary");
        Intrinsics.checkNotNullParameter(dataIntegrityNote, "dataIntegrityNote");
        Intrinsics.checkNotNullParameter(profileStats, "profileStats");
        Intrinsics.checkNotNullParameter(qualificationRatio, "qualificationRatio");
        Intrinsics.checkNotNullParameter(relevancyItems, "relevancyItems");
        Intrinsics.checkNotNullParameter(gaps, "gaps");
        Intrinsics.checkNotNullParameter(scoringVersion, "scoringVersion");
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.jobDescription = jobDescription;
        this.score = score;
        this.vacancyScore = vacancyScore;
        this.roleSummary = roleSummary;
        this.eligibilitySummary = eligibilitySummary;
        this.dataIntegrityNote = dataIntegrityNote;
        this.profileStats = profileStats;
        this.qualificationRatio = qualificationRatio;
        this.relevancyItems = relevancyItems;
        this.gaps = gaps;
        this.criticalGapCount = criticalGapCount;
        this.scoringVersion = scoringVersion;
        this.createdAt = createdAt;
    }

    public /* synthetic */ CompatibilityRecord(long j, String str, String str2, String str3, int i, int i2, String str4, String str5, String str6, ProfileStats profileStats, QualificationRatio qualificationRatio, List list, List list2, int i3, String str7, long j2, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? System.currentTimeMillis() : j, str, str2, str3, i, (i4 & 32) != 0 ? 0 : i2, str4, str5, (i4 & 256) != 0 ? "" : str6, profileStats, (i4 & 1024) != 0 ? new QualificationRatio(0, 0, 0) : qualificationRatio, list, list2, i3, (i4 & 16384) != 0 ? BuildConfig.VERSION_NAME : str7, (i4 & 32768) != 0 ? System.currentTimeMillis() : j2);
    }

    public final long getId() {
        return this.id;
    }

    public final String getJobTitle() {
        return this.jobTitle;
    }

    public final String getCompany() {
        return this.company;
    }

    public final String getJobDescription() {
        return this.jobDescription;
    }

    public final int getScore() {
        return this.score;
    }

    public final int getVacancyScore() {
        return this.vacancyScore;
    }

    public final String getRoleSummary() {
        return this.roleSummary;
    }

    public final String getEligibilitySummary() {
        return this.eligibilitySummary;
    }

    public final String getDataIntegrityNote() {
        return this.dataIntegrityNote;
    }

    public final ProfileStats getProfileStats() {
        return this.profileStats;
    }

    public final QualificationRatio getQualificationRatio() {
        return this.qualificationRatio;
    }

    public final List<RelevancyItem> getRelevancyItems() {
        return this.relevancyItems;
    }

    public final List<GapItem> getGaps() {
        return this.gaps;
    }

    public final int getCriticalGapCount() {
        return this.criticalGapCount;
    }

    public final String getScoringVersion() {
        return this.scoringVersion;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }
}
