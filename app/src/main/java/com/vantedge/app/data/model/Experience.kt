package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Experience.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/vantedge/app/data/model/Experience;", "", "company", "", "role", "duration", "description", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCompany", "()Ljava/lang/String;", "getDescription", "getDuration", "getRole", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Experience {
    public static final int $stable = 0;
    private final String company;
    private final String description;
    private final String duration;
    private final String role;

    public Experience() {
        this(null, null, null, null, 15, null);
    }

    public static /* synthetic */ Experience copy$default(Experience experience, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = experience.company;
        }
        if ((i & 2) != 0) {
            str2 = experience.role;
        }
        if ((i & 4) != 0) {
            str3 = experience.duration;
        }
        if ((i & 8) != 0) {
            str4 = experience.description;
        }
        return experience.copy(str, str2, str3, str4);
    }

    /* renamed from: component1, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    /* renamed from: component2, reason: from getter */
    public final String getRole() {
        return this.role;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDuration() {
        return this.duration;
    }

    /* renamed from: component4, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    public final Experience copy(String company, String role, String duration, String description) {
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(duration, "duration");
        Intrinsics.checkNotNullParameter(description, "description");
        return new Experience(company, role, duration, description);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Experience)) {
            return false;
        }
        Experience experience = (Experience) other;
        return Intrinsics.areEqual(this.company, experience.company) && Intrinsics.areEqual(this.role, experience.role) && Intrinsics.areEqual(this.duration, experience.duration) && Intrinsics.areEqual(this.description, experience.description);
    }

    public int hashCode() {
        return (((((this.company.hashCode() * 31) + this.role.hashCode()) * 31) + this.duration.hashCode()) * 31) + this.description.hashCode();
    }

    public String toString() {
        return "Experience(company=" + this.company + ", role=" + this.role + ", duration=" + this.duration + ", description=" + this.description + ")";
    }

    public Experience(String company, String role, String duration, String description) {
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(duration, "duration");
        Intrinsics.checkNotNullParameter(description, "description");
        this.company = company;
        this.role = role;
        this.duration = duration;
        this.description = description;
    }

    public /* synthetic */ Experience(String str, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4);
    }

    public final String getCompany() {
        return this.company;
    }

    public final String getRole() {
        return this.role;
    }

    public final String getDuration() {
        return this.duration;
    }

    public final String getDescription() {
        return this.description;
    }
}
