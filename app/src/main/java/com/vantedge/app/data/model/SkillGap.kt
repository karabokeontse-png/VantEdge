package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SkillGap.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0017"}, d2 = {"Lcom/vantedge/app/data/model/SkillGap;", "", "skill", "", "severity", "Lcom/vantedge/app/data/model/GapSeverity;", "reason", "(Ljava/lang/String;Lcom/vantedge/app/data/model/GapSeverity;Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "getSeverity", "()Lcom/vantedge/app/data/model/GapSeverity;", "getSkill", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class SkillGap {
    public static final int $stable = 0;
    private final String reason;
    private final GapSeverity severity;
    private final String skill;

    public static /* synthetic */ SkillGap copy$default(SkillGap skillGap, String str, GapSeverity gapSeverity, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = skillGap.skill;
        }
        if ((i & 2) != 0) {
            gapSeverity = skillGap.severity;
        }
        if ((i & 4) != 0) {
            str2 = skillGap.reason;
        }
        return skillGap.copy(str, gapSeverity, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getSkill() {
        return this.skill;
    }

    /* renamed from: component2, reason: from getter */
    public final GapSeverity getSeverity() {
        return this.severity;
    }

    /* renamed from: component3, reason: from getter */
    public final String getReason() {
        return this.reason;
    }

    public final SkillGap copy(String skill, GapSeverity severity, String reason) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(reason, "reason");
        return new SkillGap(skill, severity, reason);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SkillGap)) {
            return false;
        }
        SkillGap skillGap = (SkillGap) other;
        return Intrinsics.areEqual(this.skill, skillGap.skill) && this.severity == skillGap.severity && Intrinsics.areEqual(this.reason, skillGap.reason);
    }

    public int hashCode() {
        return (((this.skill.hashCode() * 31) + this.severity.hashCode()) * 31) + this.reason.hashCode();
    }

    public String toString() {
        return "SkillGap(skill=" + this.skill + ", severity=" + this.severity + ", reason=" + this.reason + ")";
    }

    public SkillGap(String skill, GapSeverity severity, String reason) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.skill = skill;
        this.severity = severity;
        this.reason = reason;
    }

    public final String getSkill() {
        return this.skill;
    }

    public final GapSeverity getSeverity() {
        return this.severity;
    }

    public final String getReason() {
        return this.reason;
    }
}
