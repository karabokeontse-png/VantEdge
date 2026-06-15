package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserProfile.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/vantedge/app/data/model/WorkExperience;", "", "role", "", "company", "startDate", "endDate", "description", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCompany", "()Ljava/lang/String;", "getDescription", "getEndDate", "getRole", "getStartDate", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class WorkExperience {
    public static final int $stable = 0;
    private final String company;
    private final String description;
    private final String endDate;
    private final String role;
    private final String startDate;

    public static /* synthetic */ WorkExperience copy$default(WorkExperience workExperience, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = workExperience.role;
        }
        if ((i & 2) != 0) {
            str2 = workExperience.company;
        }
        String str6 = str2;
        if ((i & 4) != 0) {
            str3 = workExperience.startDate;
        }
        String str7 = str3;
        if ((i & 8) != 0) {
            str4 = workExperience.endDate;
        }
        String str8 = str4;
        if ((i & 16) != 0) {
            str5 = workExperience.description;
        }
        return workExperience.copy(str, str6, str7, str8, str5);
    }

    /* renamed from: component1, reason: from getter */
    public final String getRole() {
        return this.role;
    }

    /* renamed from: component2, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    /* renamed from: component3, reason: from getter */
    public final String getStartDate() {
        return this.startDate;
    }

    /* renamed from: component4, reason: from getter */
    public final String getEndDate() {
        return this.endDate;
    }

    /* renamed from: component5, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    public final WorkExperience copy(String role, String company, String startDate, String endDate, String description) {
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(endDate, "endDate");
        Intrinsics.checkNotNullParameter(description, "description");
        return new WorkExperience(role, company, startDate, endDate, description);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WorkExperience)) {
            return false;
        }
        WorkExperience workExperience = (WorkExperience) other;
        return Intrinsics.areEqual(this.role, workExperience.role) && Intrinsics.areEqual(this.company, workExperience.company) && Intrinsics.areEqual(this.startDate, workExperience.startDate) && Intrinsics.areEqual(this.endDate, workExperience.endDate) && Intrinsics.areEqual(this.description, workExperience.description);
    }

    public int hashCode() {
        return (((((((this.role.hashCode() * 31) + this.company.hashCode()) * 31) + this.startDate.hashCode()) * 31) + this.endDate.hashCode()) * 31) + this.description.hashCode();
    }

    public String toString() {
        return "WorkExperience(role=" + this.role + ", company=" + this.company + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", description=" + this.description + ")";
    }

    public WorkExperience(String role, String company, String startDate, String endDate, String description) {
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(endDate, "endDate");
        Intrinsics.checkNotNullParameter(description, "description");
        this.role = role;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public final String getRole() {
        return this.role;
    }

    public final String getCompany() {
        return this.company;
    }

    public final String getStartDate() {
        return this.startDate;
    }

    public final String getEndDate() {
        return this.endDate;
    }

    public final String getDescription() {
        return this.description;
    }
}
