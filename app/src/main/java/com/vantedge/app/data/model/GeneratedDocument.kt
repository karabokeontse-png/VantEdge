package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeneratedDocument.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\bHÆ\u0003J\t\u0010\u001a\u001a\u00020\nHÆ\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020\bHÖ\u0001J\t\u0010 \u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006!"}, d2 = {"Lcom/vantedge/app/data/model/GeneratedDocument;", "", "id", "", "jobTitle", "company", "content", "score", "", "date", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IJ)V", "getCompany", "()Ljava/lang/String;", "getContent", "getDate", "()J", "getId", "getJobTitle", "getScore", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class GeneratedDocument {
    public static final int $stable = 0;
    private final String company;
    private final String content;
    private final long date;
    private final String id;
    private final String jobTitle;
    private final int score;

    public static /* synthetic */ GeneratedDocument copy$default(GeneratedDocument generatedDocument, String str, String str2, String str3, String str4, int i, long j, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = generatedDocument.id;
        }
        if ((i2 & 2) != 0) {
            str2 = generatedDocument.jobTitle;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            str3 = generatedDocument.company;
        }
        String str6 = str3;
        if ((i2 & 8) != 0) {
            str4 = generatedDocument.content;
        }
        String str7 = str4;
        if ((i2 & 16) != 0) {
            i = generatedDocument.score;
        }
        int i3 = i;
        if ((i2 & 32) != 0) {
            j = generatedDocument.date;
        }
        return generatedDocument.copy(str, str5, str6, str7, i3, j);
    }

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
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
    public final String getContent() {
        return this.content;
    }

    /* renamed from: component5, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    /* renamed from: component6, reason: from getter */
    public final long getDate() {
        return this.date;
    }

    public final GeneratedDocument copy(String id, String jobTitle, String company, String content, int score, long date) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(content, "content");
        return new GeneratedDocument(id, jobTitle, company, content, score, date);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeneratedDocument)) {
            return false;
        }
        GeneratedDocument generatedDocument = (GeneratedDocument) other;
        return Intrinsics.areEqual(this.id, generatedDocument.id) && Intrinsics.areEqual(this.jobTitle, generatedDocument.jobTitle) && Intrinsics.areEqual(this.company, generatedDocument.company) && Intrinsics.areEqual(this.content, generatedDocument.content) && this.score == generatedDocument.score && this.date == generatedDocument.date;
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.jobTitle.hashCode()) * 31) + this.company.hashCode()) * 31) + this.content.hashCode()) * 31) + Integer.hashCode(this.score)) * 31) + Long.hashCode(this.date);
    }

    public String toString() {
        return "GeneratedDocument(id=" + this.id + ", jobTitle=" + this.jobTitle + ", company=" + this.company + ", content=" + this.content + ", score=" + this.score + ", date=" + this.date + ")";
    }

    public GeneratedDocument(String id, String jobTitle, String company, String content, int score, long date) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(content, "content");
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.content = content;
        this.score = score;
        this.date = date;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ GeneratedDocument(java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, int r15, long r16, int r18, kotlin.jvm.internal.DefaultConstructorMarker r19) {
        /*
            r10 = this;
            r0 = r18 & 32
            if (r0 == 0) goto La
            long r0 = java.lang.System.currentTimeMillis()
            r8 = r0
            goto Lc
        La:
            r8 = r16
        Lc:
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            r7 = r15
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.model.GeneratedDocument.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getId() {
        return this.id;
    }

    public final String getJobTitle() {
        return this.jobTitle;
    }

    public final String getCompany() {
        return this.company;
    }

    public final String getContent() {
        return this.content;
    }

    public final int getScore() {
        return this.score;
    }

    public final long getDate() {
        return this.date;
    }
}
