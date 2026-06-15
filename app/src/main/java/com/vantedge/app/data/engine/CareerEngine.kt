package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.Certification;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.model.WorkExperience;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: CareerEngine.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"}, d2 = {"Lcom/vantedge/app/data/engine/CareerEngine;", "", "()V", "generateCV", "", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "generateCoverLetter", "jobDescription", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CareerEngine {
    public static final int $stable = 0;
    public static final CareerEngine INSTANCE = new CareerEngine();

    private CareerEngine() {
    }

    public final String generateCV(UserProfile profile) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        StringBuilder $this$generateCV_u24lambda_u245 = new StringBuilder();
        StringBuilder append = $this$generateCV_u24lambda_u245.append(profile.getName());
        Intrinsics.checkNotNullExpressionValue(append, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append.append('\n'), "append(...)");
        StringBuilder append2 = $this$generateCV_u24lambda_u245.append(profile.getEmail());
        Intrinsics.checkNotNullExpressionValue(append2, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append2.append('\n'), "append(...)");
        StringBuilder append3 = $this$generateCV_u24lambda_u245.append(profile.getPhone());
        Intrinsics.checkNotNullExpressionValue(append3, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append3.append('\n'), "append(...)");
        StringBuilder append4 = $this$generateCV_u24lambda_u245.append(profile.getLocation());
        Intrinsics.checkNotNullExpressionValue(append4, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append4.append('\n'), "append(...)");
        StringBuilder append5 = $this$generateCV_u24lambda_u245.append(profile.getLinkedIn());
        Intrinsics.checkNotNullExpressionValue(append5, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append5.append('\n'), "append(...)");
        Intrinsics.checkNotNullExpressionValue($this$generateCV_u24lambda_u245.append('\n'), "append(...)");
        StringBuilder append6 = $this$generateCV_u24lambda_u245.append("Summary");
        Intrinsics.checkNotNullExpressionValue(append6, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append6.append('\n'), "append(...)");
        StringBuilder append7 = $this$generateCV_u24lambda_u245.append(profile.getSummary());
        Intrinsics.checkNotNullExpressionValue(append7, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append7.append('\n'), "append(...)");
        Intrinsics.checkNotNullExpressionValue($this$generateCV_u24lambda_u245.append('\n'), "append(...)");
        StringBuilder append8 = $this$generateCV_u24lambda_u245.append("Skills");
        Intrinsics.checkNotNullExpressionValue(append8, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append8.append('\n'), "append(...)");
        Iterable $this$forEach$iv = profile.getSkills();
        for (Object element$iv : $this$forEach$iv) {
            String it = (String) element$iv;
            StringBuilder append9 = $this$generateCV_u24lambda_u245.append("- " + it);
            Intrinsics.checkNotNullExpressionValue(append9, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append9.append('\n'), "append(...)");
        }
        Intrinsics.checkNotNullExpressionValue($this$generateCV_u24lambda_u245.append('\n'), "append(...)");
        StringBuilder append10 = $this$generateCV_u24lambda_u245.append("Work Experience");
        Intrinsics.checkNotNullExpressionValue(append10, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append10.append('\n'), "append(...)");
        Iterable $this$forEach$iv2 = profile.getWorkHistory();
        for (Object element$iv2 : $this$forEach$iv2) {
            WorkExperience work = (WorkExperience) element$iv2;
            StringBuilder append11 = $this$generateCV_u24lambda_u245.append(work.getRole() + " at " + work.getCompany());
            Intrinsics.checkNotNullExpressionValue(append11, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append11.append('\n'), "append(...)");
            StringBuilder append12 = $this$generateCV_u24lambda_u245.append(work.getStartDate() + " - " + work.getEndDate());
            Intrinsics.checkNotNullExpressionValue(append12, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append12.append('\n'), "append(...)");
            StringBuilder append13 = $this$generateCV_u24lambda_u245.append(work.getDescription());
            Intrinsics.checkNotNullExpressionValue(append13, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append13.append('\n'), "append(...)");
            Intrinsics.checkNotNullExpressionValue($this$generateCV_u24lambda_u245.append('\n'), "append(...)");
        }
        StringBuilder append14 = $this$generateCV_u24lambda_u245.append("Education");
        Intrinsics.checkNotNullExpressionValue(append14, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append14.append('\n'), "append(...)");
        Iterable $this$forEach$iv3 = profile.getEducation();
        for (Object element$iv3 : $this$forEach$iv3) {
            String it2 = (String) element$iv3;
            StringBuilder append15 = $this$generateCV_u24lambda_u245.append("- " + it2);
            Intrinsics.checkNotNullExpressionValue(append15, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append15.append('\n'), "append(...)");
        }
        Intrinsics.checkNotNullExpressionValue($this$generateCV_u24lambda_u245.append('\n'), "append(...)");
        StringBuilder append16 = $this$generateCV_u24lambda_u245.append("Certifications");
        Intrinsics.checkNotNullExpressionValue(append16, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append16.append('\n'), "append(...)");
        Iterable $this$forEach$iv4 = profile.getCertifications();
        for (Object element$iv4 : $this$forEach$iv4) {
            Certification it3 = (Certification) element$iv4;
            StringBuilder append17 = $this$generateCV_u24lambda_u245.append("- " + it3);
            Intrinsics.checkNotNullExpressionValue(append17, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append17.append('\n'), "append(...)");
        }
        Intrinsics.checkNotNullExpressionValue($this$generateCV_u24lambda_u245.append('\n'), "append(...)");
        StringBuilder append18 = $this$generateCV_u24lambda_u245.append("Languages");
        Intrinsics.checkNotNullExpressionValue(append18, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append18.append('\n'), "append(...)");
        Iterable $this$forEach$iv5 = profile.getLanguages();
        for (Object element$iv5 : $this$forEach$iv5) {
            String it4 = (String) element$iv5;
            StringBuilder append19 = $this$generateCV_u24lambda_u245.append("- " + it4);
            Intrinsics.checkNotNullExpressionValue(append19, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append19.append('\n'), "append(...)");
        }
        String sb = $this$generateCV_u24lambda_u245.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
        return sb;
    }

    public final String generateCoverLetter(UserProfile profile, String jobDescription) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        return StringsKt.trimIndent("\n            Dear Hiring Manager,\n\n            My name is " + profile.getName() + ", and I am applying for this role.\n\n            " + jobDescription + "\n\n            With my experience in:\n            " + CollectionsKt.joinToString$default(profile.getSkills(), ", ", null, null, 0, null, null, 62, null) + "\n\n            I believe I am a strong candidate.\n\n            Sincerely,\n            " + profile.getName() + "\n        ");
    }
}
