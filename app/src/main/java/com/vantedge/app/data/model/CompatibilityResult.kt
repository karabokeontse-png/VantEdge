package com.vantedge.app.data.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GenerationCycle.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0005¢\u0006\u0002\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0005HÆ\u0003Jc\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0005HÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001J\t\u0010 \u001a\u00020\u0006HÖ\u0001R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e¨\u0006!"}, d2 = {"Lcom/vantedge/app/data/model/CompatibilityResult;", "", "score", "", "matchedSkills", "", "", "missingSkills", "weakSections", "suggestions", "courses", "Lcom/vantedge/app/data/model/CourseRecommendation;", "(ILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getCourses", "()Ljava/util/List;", "getMatchedSkills", "getMissingSkills", "getScore", "()I", "getSuggestions", "getWeakSections", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class CompatibilityResult {
    public static final int $stable = 8;
    private final List<CourseRecommendation> courses;
    private final List<String> matchedSkills;
    private final List<String> missingSkills;
    private final int score;
    private final List<String> suggestions;
    private final List<String> weakSections;

    public static /* synthetic */ CompatibilityResult copy$default(CompatibilityResult compatibilityResult, int i, List list, List list2, List list3, List list4, List list5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = compatibilityResult.score;
        }
        if ((i2 & 2) != 0) {
            list = compatibilityResult.matchedSkills;
        }
        List list6 = list;
        if ((i2 & 4) != 0) {
            list2 = compatibilityResult.missingSkills;
        }
        List list7 = list2;
        if ((i2 & 8) != 0) {
            list3 = compatibilityResult.weakSections;
        }
        List list8 = list3;
        if ((i2 & 16) != 0) {
            list4 = compatibilityResult.suggestions;
        }
        List list9 = list4;
        if ((i2 & 32) != 0) {
            list5 = compatibilityResult.courses;
        }
        return compatibilityResult.copy(i, list6, list7, list8, list9, list5);
    }

    /* renamed from: component1, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    public final List<String> component2() {
        return this.matchedSkills;
    }

    public final List<String> component3() {
        return this.missingSkills;
    }

    public final List<String> component4() {
        return this.weakSections;
    }

    public final List<String> component5() {
        return this.suggestions;
    }

    public final List<CourseRecommendation> component6() {
        return this.courses;
    }

    public final CompatibilityResult copy(int score, List<String> matchedSkills, List<String> missingSkills, List<String> weakSections, List<String> suggestions, List<CourseRecommendation> courses) {
        Intrinsics.checkNotNullParameter(matchedSkills, "matchedSkills");
        Intrinsics.checkNotNullParameter(missingSkills, "missingSkills");
        Intrinsics.checkNotNullParameter(weakSections, "weakSections");
        Intrinsics.checkNotNullParameter(suggestions, "suggestions");
        Intrinsics.checkNotNullParameter(courses, "courses");
        return new CompatibilityResult(score, matchedSkills, missingSkills, weakSections, suggestions, courses);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompatibilityResult)) {
            return false;
        }
        CompatibilityResult compatibilityResult = (CompatibilityResult) other;
        return this.score == compatibilityResult.score && Intrinsics.areEqual(this.matchedSkills, compatibilityResult.matchedSkills) && Intrinsics.areEqual(this.missingSkills, compatibilityResult.missingSkills) && Intrinsics.areEqual(this.weakSections, compatibilityResult.weakSections) && Intrinsics.areEqual(this.suggestions, compatibilityResult.suggestions) && Intrinsics.areEqual(this.courses, compatibilityResult.courses);
    }

    public int hashCode() {
        return (((((((((Integer.hashCode(this.score) * 31) + this.matchedSkills.hashCode()) * 31) + this.missingSkills.hashCode()) * 31) + this.weakSections.hashCode()) * 31) + this.suggestions.hashCode()) * 31) + this.courses.hashCode();
    }

    public String toString() {
        return "CompatibilityResult(score=" + this.score + ", matchedSkills=" + this.matchedSkills + ", missingSkills=" + this.missingSkills + ", weakSections=" + this.weakSections + ", suggestions=" + this.suggestions + ", courses=" + this.courses + ")";
    }

    public CompatibilityResult(int score, List<String> matchedSkills, List<String> missingSkills, List<String> weakSections, List<String> suggestions, List<CourseRecommendation> courses) {
        Intrinsics.checkNotNullParameter(matchedSkills, "matchedSkills");
        Intrinsics.checkNotNullParameter(missingSkills, "missingSkills");
        Intrinsics.checkNotNullParameter(weakSections, "weakSections");
        Intrinsics.checkNotNullParameter(suggestions, "suggestions");
        Intrinsics.checkNotNullParameter(courses, "courses");
        this.score = score;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.weakSections = weakSections;
        this.suggestions = suggestions;
        this.courses = courses;
    }

    public final int getScore() {
        return this.score;
    }

    public final List<String> getMatchedSkills() {
        return this.matchedSkills;
    }

    public final List<String> getMissingSkills() {
        return this.missingSkills;
    }

    public final List<String> getWeakSections() {
        return this.weakSections;
    }

    public final List<String> getSuggestions() {
        return this.suggestions;
    }

    public final List<CourseRecommendation> getCourses() {
        return this.courses;
    }
}
