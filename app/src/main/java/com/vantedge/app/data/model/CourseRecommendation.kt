package com.vantedge.app.data.model;

import com.google.android.gms.common.internal.ImagesContract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.i18n.MessageBundle;

/* compiled from: CompatibilityRecord.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u001b\b\u0087\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b¢\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\bHÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u000bHÆ\u0003J\t\u0010 \u001a\u00020\u000bHÆ\u0003JY\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bHÆ\u0001J\u0013\u0010\"\u001a\u00020\b2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020\u000bHÖ\u0001J\t\u0010%\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\f\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f¨\u0006&"}, d2 = {"Lcom/vantedge/app/data/model/CourseRecommendation;", "", MessageBundle.TITLE_ENTRY, "", "provider", ImagesContract.URL, "category", "hasCertificate", "", "estimatedDuration", "relevancyPercent", "", "priority", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;II)V", "getCategory", "()Ljava/lang/String;", "getEstimatedDuration", "getHasCertificate", "()Z", "getPriority", "()I", "getProvider", "getRelevancyPercent", "getTitle", "getUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class CourseRecommendation {
    public static final int $stable = 0;
    private final String category;
    private final String estimatedDuration;
    private final boolean hasCertificate;
    private final int priority;
    private final String provider;
    private final int relevancyPercent;
    private final String title;
    private final String url;

    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component2, reason: from getter */
    public final String getProvider() {
        return this.provider;
    }

    /* renamed from: component3, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* renamed from: component4, reason: from getter */
    public final String getCategory() {
        return this.category;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getHasCertificate() {
        return this.hasCertificate;
    }

    /* renamed from: component6, reason: from getter */
    public final String getEstimatedDuration() {
        return this.estimatedDuration;
    }

    /* renamed from: component7, reason: from getter */
    public final int getRelevancyPercent() {
        return this.relevancyPercent;
    }

    /* renamed from: component8, reason: from getter */
    public final int getPriority() {
        return this.priority;
    }

    public final CourseRecommendation copy(String title, String provider, String url, String category, boolean hasCertificate, String estimatedDuration, int relevancyPercent, int priority) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(provider, "provider");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(estimatedDuration, "estimatedDuration");
        return new CourseRecommendation(title, provider, url, category, hasCertificate, estimatedDuration, relevancyPercent, priority);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseRecommendation)) {
            return false;
        }
        CourseRecommendation courseRecommendation = (CourseRecommendation) other;
        return Intrinsics.areEqual(this.title, courseRecommendation.title) && Intrinsics.areEqual(this.provider, courseRecommendation.provider) && Intrinsics.areEqual(this.url, courseRecommendation.url) && Intrinsics.areEqual(this.category, courseRecommendation.category) && this.hasCertificate == courseRecommendation.hasCertificate && Intrinsics.areEqual(this.estimatedDuration, courseRecommendation.estimatedDuration) && this.relevancyPercent == courseRecommendation.relevancyPercent && this.priority == courseRecommendation.priority;
    }

    public int hashCode() {
        return (((((((((((((this.title.hashCode() * 31) + this.provider.hashCode()) * 31) + this.url.hashCode()) * 31) + this.category.hashCode()) * 31) + Boolean.hashCode(this.hasCertificate)) * 31) + this.estimatedDuration.hashCode()) * 31) + Integer.hashCode(this.relevancyPercent)) * 31) + Integer.hashCode(this.priority);
    }

    public String toString() {
        return "CourseRecommendation(title=" + this.title + ", provider=" + this.provider + ", url=" + this.url + ", category=" + this.category + ", hasCertificate=" + this.hasCertificate + ", estimatedDuration=" + this.estimatedDuration + ", relevancyPercent=" + this.relevancyPercent + ", priority=" + this.priority + ")";
    }

    public CourseRecommendation(String title, String provider, String url, String category, boolean hasCertificate, String estimatedDuration, int relevancyPercent, int priority) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(provider, "provider");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(estimatedDuration, "estimatedDuration");
        this.title = title;
        this.provider = provider;
        this.url = url;
        this.category = category;
        this.hasCertificate = hasCertificate;
        this.estimatedDuration = estimatedDuration;
        this.relevancyPercent = relevancyPercent;
        this.priority = priority;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ CourseRecommendation(java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String r15, boolean r16, java.lang.String r17, int r18, int r19, int r20, kotlin.jvm.internal.DefaultConstructorMarker r21) {
        /*
            r11 = this;
            r0 = r20
            r1 = r0 & 64
            if (r1 == 0) goto L9
            r1 = 0
            r9 = r1
            goto Lb
        L9:
            r9 = r18
        Lb:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L12
            r0 = 1
            r10 = r0
            goto L14
        L12:
            r10 = r19
        L14:
            r2 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r7 = r16
            r8 = r17
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.model.CourseRecommendation.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getProvider() {
        return this.provider;
    }

    public final String getUrl() {
        return this.url;
    }

    public final String getCategory() {
        return this.category;
    }

    public final boolean getHasCertificate() {
        return this.hasCertificate;
    }

    public final String getEstimatedDuration() {
        return this.estimatedDuration;
    }

    public final int getRelevancyPercent() {
        return this.relevancyPercent;
    }

    public final int getPriority() {
        return this.priority;
    }
}
