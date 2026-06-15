package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.i18n.MessageBundle;

/* compiled from: JobData.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/vantedge/app/data/model/JobData;", "", "description", "", MessageBundle.TITLE_ENTRY, "company", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCompany", "()Ljava/lang/String;", "getDescription", "getTitle", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class JobData {
    public static final int $stable = 0;
    private final String company;
    private final String description;
    private final String title;

    public static /* synthetic */ JobData copy$default(JobData jobData, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jobData.description;
        }
        if ((i & 2) != 0) {
            str2 = jobData.title;
        }
        if ((i & 4) != 0) {
            str3 = jobData.company;
        }
        return jobData.copy(str, str2, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    public final JobData copy(String description, String title, String company) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(company, "company");
        return new JobData(description, title, company);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JobData)) {
            return false;
        }
        JobData jobData = (JobData) other;
        return Intrinsics.areEqual(this.description, jobData.description) && Intrinsics.areEqual(this.title, jobData.title) && Intrinsics.areEqual(this.company, jobData.company);
    }

    public int hashCode() {
        return (((this.description.hashCode() * 31) + this.title.hashCode()) * 31) + this.company.hashCode();
    }

    public String toString() {
        return "JobData(description=" + this.description + ", title=" + this.title + ", company=" + this.company + ")";
    }

    public JobData(String description, String title, String company) {
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(company, "company");
        this.description = description;
        this.title = title;
        this.company = company;
    }

    public /* synthetic */ JobData(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3);
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getCompany() {
        return this.company;
    }
}
