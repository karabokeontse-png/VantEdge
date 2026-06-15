package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LearningRecommendation.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/vantedge/app/data/model/LearningRecommendation;", "", "skill", "", "type", "priority", "Lcom/vantedge/app/data/model/GapSeverity;", "(Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/GapSeverity;)V", "getPriority", "()Lcom/vantedge/app/data/model/GapSeverity;", "getSkill", "()Ljava/lang/String;", "getType", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class LearningRecommendation {
    public static final int $stable = 0;
    private final GapSeverity priority;
    private final String skill;
    private final String type;

    public static /* synthetic */ LearningRecommendation copy$default(LearningRecommendation learningRecommendation, String str, String str2, GapSeverity gapSeverity, int i, Object obj) {
        if ((i & 1) != 0) {
            str = learningRecommendation.skill;
        }
        if ((i & 2) != 0) {
            str2 = learningRecommendation.type;
        }
        if ((i & 4) != 0) {
            gapSeverity = learningRecommendation.priority;
        }
        return learningRecommendation.copy(str, str2, gapSeverity);
    }

    /* renamed from: component1, reason: from getter */
    public final String getSkill() {
        return this.skill;
    }

    /* renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* renamed from: component3, reason: from getter */
    public final GapSeverity getPriority() {
        return this.priority;
    }

    public final LearningRecommendation copy(String skill, String type, GapSeverity priority) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(priority, "priority");
        return new LearningRecommendation(skill, type, priority);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LearningRecommendation)) {
            return false;
        }
        LearningRecommendation learningRecommendation = (LearningRecommendation) other;
        return Intrinsics.areEqual(this.skill, learningRecommendation.skill) && Intrinsics.areEqual(this.type, learningRecommendation.type) && this.priority == learningRecommendation.priority;
    }

    public int hashCode() {
        return (((this.skill.hashCode() * 31) + this.type.hashCode()) * 31) + this.priority.hashCode();
    }

    public String toString() {
        return "LearningRecommendation(skill=" + this.skill + ", type=" + this.type + ", priority=" + this.priority + ")";
    }

    public LearningRecommendation(String skill, String type, GapSeverity priority) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(priority, "priority");
        this.skill = skill;
        this.type = type;
        this.priority = priority;
    }

    public final String getSkill() {
        return this.skill;
    }

    public final String getType() {
        return this.type;
    }

    public final GapSeverity getPriority() {
        return this.priority;
    }
}
