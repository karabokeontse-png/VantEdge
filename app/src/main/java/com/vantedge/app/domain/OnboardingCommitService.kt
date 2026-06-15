package com.vantedge.app.domain;

import com.vantedge.app.data.model.Certification;
import com.vantedge.app.data.model.ExtractedCertification;
import com.vantedge.app.data.model.ExtractedEducation;
import com.vantedge.app.data.model.ExtractedExperience;
import com.vantedge.app.data.model.ExtractedField;
import com.vantedge.app.data.model.StructuredProfileExtraction;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.model.WorkExperience;
import com.vantedge.app.data.storage.OnboardingDraftStore;
import com.vantedge.app.data.storage.UserPreferences;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnboardingCommitService.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@¢\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/domain/OnboardingCommitService;", "", "userPreferences", "Lcom/vantedge/app/data/storage/UserPreferences;", "draftStore", "Lcom/vantedge/app/data/storage/OnboardingDraftStore;", "(Lcom/vantedge/app/data/storage/UserPreferences;Lcom/vantedge/app/data/storage/OnboardingDraftStore;)V", "commit", "", "draft", "Lcom/vantedge/app/data/model/OnboardingDraft;", "(Lcom/vantedge/app/data/model/OnboardingDraft;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractionToProfile", "Lcom/vantedge/app/data/model/UserProfile;", "extraction", "Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class OnboardingCommitService {
    public static final int $stable = 8;
    private final OnboardingDraftStore draftStore;
    private final UserPreferences userPreferences;

    public OnboardingCommitService(UserPreferences userPreferences, OnboardingDraftStore draftStore) {
        Intrinsics.checkNotNullParameter(userPreferences, "userPreferences");
        Intrinsics.checkNotNullParameter(draftStore, "draftStore");
        this.userPreferences = userPreferences;
        this.draftStore = draftStore;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object commit(com.vantedge.app.data.model.OnboardingDraft r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.vantedge.app.domain.OnboardingCommitService$commit$1
            if (r0 == 0) goto L14
            r0 = r7
            com.vantedge.app.domain.OnboardingCommitService$commit$1 r0 = (com.vantedge.app.domain.OnboardingCommitService$commit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.vantedge.app.domain.OnboardingCommitService$commit$1 r0 = new com.vantedge.app.domain.OnboardingCommitService$commit$1
            r0.<init>(r5, r7)
        L19:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L39;
                case 1: goto L31;
                case 2: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L61
        L31:
            java.lang.Object r6 = r7.L$0
            com.vantedge.app.domain.OnboardingCommitService r6 = (com.vantedge.app.domain.OnboardingCommitService) r6
            kotlin.ResultKt.throwOnFailure(r0)
            goto L52
        L39:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            com.vantedge.app.data.model.UserProfile r6 = r6.getEditedProfile()
            if (r6 == 0) goto L64
            com.vantedge.app.data.storage.UserPreferences r3 = r2.userPreferences
            r7.L$0 = r2
            r4 = 1
            r7.label = r4
            java.lang.Object r6 = r3.saveProfile(r6, r7)
            if (r6 != r1) goto L51
            return r1
        L51:
            r6 = r2
        L52:
            com.vantedge.app.data.storage.OnboardingDraftStore r2 = r6.draftStore
            r3 = 0
            r7.L$0 = r3
            r3 = 2
            r7.label = r3
            java.lang.Object r6 = r2.clearDraft(r7)
            if (r6 != r1) goto L61
            return r1
        L61:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L64:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r1 = "Cannot commit: no profile in draft"
            r6.<init>(r1)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.domain.OnboardingCommitService.commit(com.vantedge.app.data.model.OnboardingDraft, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final UserProfile extractionToProfile(StructuredProfileExtraction extraction) {
        String value;
        String value2;
        String value3;
        String value4;
        String str;
        int $i$f$map;
        String value5;
        String value6;
        String value7;
        String value8;
        String value9;
        String value10;
        Intrinsics.checkNotNullParameter(extraction, "extraction");
        ExtractedField name = extraction.getPersonalInfo().getName();
        String str2 = "";
        String str3 = (name == null || (value10 = name.getValue()) == null) ? "" : value10;
        ExtractedField email = extraction.getPersonalInfo().getEmail();
        String str4 = (email == null || (value9 = email.getValue()) == null) ? "" : value9;
        ExtractedField phone = extraction.getPersonalInfo().getPhone();
        String str5 = (phone == null || (value8 = phone.getValue()) == null) ? "" : value8;
        ExtractedField location = extraction.getPersonalInfo().getLocation();
        String str6 = (location == null || (value7 = location.getValue()) == null) ? "" : value7;
        ExtractedField linkedIn = extraction.getPersonalInfo().getLinkedIn();
        String str7 = (linkedIn == null || (value6 = linkedIn.getValue()) == null) ? "" : value6;
        ExtractedField summary = extraction.getSummary();
        String str8 = (summary == null || (value5 = summary.getValue()) == null) ? "" : value5;
        Iterable $this$map$iv = extraction.getSkills();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            ExtractedField it = (ExtractedField) item$iv$iv;
            destination$iv$iv.add(it.getValue());
        }
        ArrayList arrayList = (List) destination$iv$iv;
        Iterable $this$map$iv2 = extraction.getLanguages();
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv2 : $this$map$iv2) {
            ExtractedField it2 = (ExtractedField) item$iv$iv2;
            destination$iv$iv2.add(it2.getValue());
            $this$map$iv2 = $this$map$iv2;
        }
        ArrayList arrayList2 = (List) destination$iv$iv2;
        Iterable $this$map$iv3 = extraction.getEducation();
        int $i$f$map2 = 0;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        for (Object item$iv$iv3 : $this$map$iv3) {
            ExtractedEducation edu = (ExtractedEducation) item$iv$iv3;
            StringBuilder $this$extractionToProfile_u24lambda_u243_u24lambda_u242 = new StringBuilder();
            Iterable $this$map$iv4 = $this$map$iv3;
            $this$extractionToProfile_u24lambda_u243_u24lambda_u242.append(edu.getQualification().getValue());
            if (edu.getFieldOfStudy() != null) {
                str = str2;
                $i$f$map = $i$f$map2;
                $this$extractionToProfile_u24lambda_u243_u24lambda_u242.append(" in " + edu.getFieldOfStudy().getValue());
            } else {
                str = str2;
                $i$f$map = $i$f$map2;
            }
            $this$extractionToProfile_u24lambda_u243_u24lambda_u242.append(" — " + edu.getInstitution().getValue());
            if (edu.getGraduationYear() != null) {
                $this$extractionToProfile_u24lambda_u243_u24lambda_u242.append(" (" + edu.getGraduationYear().getValue() + ")");
            }
            String sb = $this$extractionToProfile_u24lambda_u243_u24lambda_u242.toString();
            Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
            destination$iv$iv3.add(sb);
            str2 = str;
            $i$f$map2 = $i$f$map;
            $this$map$iv3 = $this$map$iv4;
        }
        String str9 = str2;
        ArrayList arrayList3 = (List) destination$iv$iv3;
        Iterable $this$map$iv5 = extraction.getWorkHistory();
        Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv5, 10));
        for (Object item$iv$iv4 : $this$map$iv5) {
            ExtractedExperience exp = (ExtractedExperience) item$iv$iv4;
            Iterable $this$map$iv6 = $this$map$iv5;
            String value11 = exp.getJobTitle().getValue();
            String value12 = exp.getCompany().getValue();
            ExtractedField startDate = exp.getStartDate();
            String str10 = (startDate == null || (value4 = startDate.getValue()) == null) ? str9 : value4;
            ExtractedField endDate = exp.getEndDate();
            String str11 = (endDate == null || (value3 = endDate.getValue()) == null) ? str9 : value3;
            ExtractedField description = exp.getDescription();
            destination$iv$iv4.add(new WorkExperience(value11, value12, str10, str11, (description == null || (value2 = description.getValue()) == null) ? str9 : value2));
            $this$map$iv5 = $this$map$iv6;
        }
        ArrayList arrayList4 = (List) destination$iv$iv4;
        Iterable $this$map$iv7 = extraction.getCertifications();
        Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv7, 10));
        for (Object item$iv$iv5 : $this$map$iv7) {
            ExtractedCertification cert = (ExtractedCertification) item$iv$iv5;
            Iterable $this$map$iv8 = $this$map$iv7;
            String value13 = cert.getName().getValue();
            ExtractedField issuer = cert.getIssuer();
            destination$iv$iv5.add(new Certification(null, value13, (issuer == null || (value = issuer.getValue()) == null) ? str9 : value, null, null, null, 0L, 0L, 0L, 505, null));
            $this$map$iv7 = $this$map$iv8;
        }
        return new UserProfile(str3, str4, str5, str6, str7, str8, arrayList, arrayList4, arrayList3, (List) destination$iv$iv5, arrayList2);
    }
}
