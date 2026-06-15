package com.vantedge.app.data.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CompatibilityRecord.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0007HÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\nHÆ\u0003JK\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u00072\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\t\u0010!\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010¨\u0006\""}, d2 = {"Lcom/vantedge/app/data/model/GapItem;", "", "skill", "", "importance", "description", "experienceGap", "", "platformGap", "courses", "", "Lcom/vantedge/app/data/model/CourseRecommendation;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZLjava/util/List;)V", "getCourses", "()Ljava/util/List;", "getDescription", "()Ljava/lang/String;", "getExperienceGap", "()Z", "getImportance", "getPlatformGap", "getSkill", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class GapItem {
    public static final int $stable = 8;
    private final List<CourseRecommendation> courses;
    private final String description;
    private final boolean experienceGap;
    private final String importance;
    private final boolean platformGap;
    private final String skill;

    public static /* synthetic */ GapItem copy$default(GapItem gapItem, String str, String str2, String str3, boolean z, boolean z2, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = gapItem.skill;
        }
        if ((i & 2) != 0) {
            str2 = gapItem.importance;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            str3 = gapItem.description;
        }
        String str5 = str3;
        if ((i & 8) != 0) {
            z = gapItem.experienceGap;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = gapItem.platformGap;
        }
        boolean z4 = z2;
        if ((i & 32) != 0) {
            list = gapItem.courses;
        }
        return gapItem.copy(str, str4, str5, z3, z4, list);
    }

    /* renamed from: component1, reason: from getter */
    public final String getSkill() {
        return this.skill;
    }

    /* renamed from: component2, reason: from getter */
    public final String getImportance() {
        return this.importance;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getExperienceGap() {
        return this.experienceGap;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getPlatformGap() {
        return this.platformGap;
    }

    public final List<CourseRecommendation> component6() {
        return this.courses;
    }

    public final GapItem copy(String skill, String importance, String description, boolean experienceGap, boolean platformGap, List<CourseRecommendation> courses) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        Intrinsics.checkNotNullParameter(importance, "importance");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(courses, "courses");
        return new GapItem(skill, importance, description, experienceGap, platformGap, courses);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GapItem)) {
            return false;
        }
        GapItem gapItem = (GapItem) other;
        return Intrinsics.areEqual(this.skill, gapItem.skill) && Intrinsics.areEqual(this.importance, gapItem.importance) && Intrinsics.areEqual(this.description, gapItem.description) && this.experienceGap == gapItem.experienceGap && this.platformGap == gapItem.platformGap && Intrinsics.areEqual(this.courses, gapItem.courses);
    }

    public int hashCode() {
        return (((((((((this.skill.hashCode() * 31) + this.importance.hashCode()) * 31) + this.description.hashCode()) * 31) + Boolean.hashCode(this.experienceGap)) * 31) + Boolean.hashCode(this.platformGap)) * 31) + this.courses.hashCode();
    }

    public String toString() {
        return "GapItem(skill=" + this.skill + ", importance=" + this.importance + ", description=" + this.description + ", experienceGap=" + this.experienceGap + ", platformGap=" + this.platformGap + ", courses=" + this.courses + ")";
    }

    public GapItem(String skill, String importance, String description, boolean experienceGap, boolean platformGap, List<CourseRecommendation> courses) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        Intrinsics.checkNotNullParameter(importance, "importance");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(courses, "courses");
        this.skill = skill;
        this.importance = importance;
        this.description = description;
        this.experienceGap = experienceGap;
        this.platformGap = platformGap;
        this.courses = courses;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ GapItem(java.lang.String r10, java.lang.String r11, java.lang.String r12, boolean r13, boolean r14, java.util.List r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
        /*
            r9 = this;
            r0 = r16 & 8
            r1 = 0
            if (r0 == 0) goto L7
            r6 = r1
            goto L8
        L7:
            r6 = r13
        L8:
            r0 = r16 & 16
            if (r0 == 0) goto Le
            r7 = r1
            goto Lf
        Le:
            r7 = r14
        Lf:
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r8 = r15
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.model.GapItem.<init>(java.lang.String, java.lang.String, java.lang.String, boolean, boolean, java.util.List, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getSkill() {
        return this.skill;
    }

    public final String getImportance() {
        return this.importance;
    }

    public final String getDescription() {
        return this.description;
    }

    public final boolean getExperienceGap() {
        return this.experienceGap;
    }

    public final boolean getPlatformGap() {
        return this.platformGap;
    }

    public final List<CourseRecommendation> getCourses() {
        return this.courses;
    }
}
