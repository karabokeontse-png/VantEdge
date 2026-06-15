package com.vantedge.app.data.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GapAnalysisResult.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BS\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\fHÆ\u0003Jc\u0010\u001c\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\fHÖ\u0001J\t\u0010!\u001a\u00020\u0004HÖ\u0001R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011¨\u0006\""}, d2 = {"Lcom/vantedge/app/data/model/GapAnalysisResult;", "", "matchedSkills", "", "", "missingSkills", "skillGaps", "Lcom/vantedge/app/data/model/SkillGap;", "transferableSkills", "learningRecommendations", "Lcom/vantedge/app/data/model/LearningRecommendation;", "gapScore", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;I)V", "getGapScore", "()I", "getLearningRecommendations", "()Ljava/util/List;", "getMatchedSkills", "getMissingSkills", "getSkillGaps", "getTransferableSkills", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class GapAnalysisResult {
    public static final int $stable = 8;
    private final int gapScore;
    private final List<LearningRecommendation> learningRecommendations;
    private final List<String> matchedSkills;
    private final List<String> missingSkills;
    private final List<SkillGap> skillGaps;
    private final List<String> transferableSkills;

    public static /* synthetic */ GapAnalysisResult copy$default(GapAnalysisResult gapAnalysisResult, List list, List list2, List list3, List list4, List list5, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = gapAnalysisResult.matchedSkills;
        }
        if ((i2 & 2) != 0) {
            list2 = gapAnalysisResult.missingSkills;
        }
        List list6 = list2;
        if ((i2 & 4) != 0) {
            list3 = gapAnalysisResult.skillGaps;
        }
        List list7 = list3;
        if ((i2 & 8) != 0) {
            list4 = gapAnalysisResult.transferableSkills;
        }
        List list8 = list4;
        if ((i2 & 16) != 0) {
            list5 = gapAnalysisResult.learningRecommendations;
        }
        List list9 = list5;
        if ((i2 & 32) != 0) {
            i = gapAnalysisResult.gapScore;
        }
        return gapAnalysisResult.copy(list, list6, list7, list8, list9, i);
    }

    public final List<String> component1() {
        return this.matchedSkills;
    }

    public final List<String> component2() {
        return this.missingSkills;
    }

    public final List<SkillGap> component3() {
        return this.skillGaps;
    }

    public final List<String> component4() {
        return this.transferableSkills;
    }

    public final List<LearningRecommendation> component5() {
        return this.learningRecommendations;
    }

    /* renamed from: component6, reason: from getter */
    public final int getGapScore() {
        return this.gapScore;
    }

    public final GapAnalysisResult copy(List<String> matchedSkills, List<String> missingSkills, List<SkillGap> skillGaps, List<String> transferableSkills, List<LearningRecommendation> learningRecommendations, int gapScore) {
        Intrinsics.checkNotNullParameter(matchedSkills, "matchedSkills");
        Intrinsics.checkNotNullParameter(missingSkills, "missingSkills");
        Intrinsics.checkNotNullParameter(skillGaps, "skillGaps");
        Intrinsics.checkNotNullParameter(transferableSkills, "transferableSkills");
        Intrinsics.checkNotNullParameter(learningRecommendations, "learningRecommendations");
        return new GapAnalysisResult(matchedSkills, missingSkills, skillGaps, transferableSkills, learningRecommendations, gapScore);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GapAnalysisResult)) {
            return false;
        }
        GapAnalysisResult gapAnalysisResult = (GapAnalysisResult) other;
        return Intrinsics.areEqual(this.matchedSkills, gapAnalysisResult.matchedSkills) && Intrinsics.areEqual(this.missingSkills, gapAnalysisResult.missingSkills) && Intrinsics.areEqual(this.skillGaps, gapAnalysisResult.skillGaps) && Intrinsics.areEqual(this.transferableSkills, gapAnalysisResult.transferableSkills) && Intrinsics.areEqual(this.learningRecommendations, gapAnalysisResult.learningRecommendations) && this.gapScore == gapAnalysisResult.gapScore;
    }

    public int hashCode() {
        return (((((((((this.matchedSkills.hashCode() * 31) + this.missingSkills.hashCode()) * 31) + this.skillGaps.hashCode()) * 31) + this.transferableSkills.hashCode()) * 31) + this.learningRecommendations.hashCode()) * 31) + Integer.hashCode(this.gapScore);
    }

    public String toString() {
        return "GapAnalysisResult(matchedSkills=" + this.matchedSkills + ", missingSkills=" + this.missingSkills + ", skillGaps=" + this.skillGaps + ", transferableSkills=" + this.transferableSkills + ", learningRecommendations=" + this.learningRecommendations + ", gapScore=" + this.gapScore + ")";
    }

    public GapAnalysisResult(List<String> matchedSkills, List<String> missingSkills, List<SkillGap> skillGaps, List<String> transferableSkills, List<LearningRecommendation> learningRecommendations, int gapScore) {
        Intrinsics.checkNotNullParameter(matchedSkills, "matchedSkills");
        Intrinsics.checkNotNullParameter(missingSkills, "missingSkills");
        Intrinsics.checkNotNullParameter(skillGaps, "skillGaps");
        Intrinsics.checkNotNullParameter(transferableSkills, "transferableSkills");
        Intrinsics.checkNotNullParameter(learningRecommendations, "learningRecommendations");
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.skillGaps = skillGaps;
        this.transferableSkills = transferableSkills;
        this.learningRecommendations = learningRecommendations;
        this.gapScore = gapScore;
    }

    public final List<String> getMatchedSkills() {
        return this.matchedSkills;
    }

    public final List<String> getMissingSkills() {
        return this.missingSkills;
    }

    public final List<SkillGap> getSkillGaps() {
        return this.skillGaps;
    }

    public final List<String> getTransferableSkills() {
        return this.transferableSkills;
    }

    public final List<LearningRecommendation> getLearningRecommendations() {
        return this.learningRecommendations;
    }

    public final int getGapScore() {
        return this.gapScore;
    }
}
