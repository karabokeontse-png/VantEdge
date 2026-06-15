package com.vantedge.app.data.storage;

import android.content.Context;
import androidx.autofill.HintConstants;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.core.PreferencesKt;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.Certification;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.model.WorkExperience;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import org.bouncycastle.i18n.ErrorBundle;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: UserPreferences.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000bH\u0086@¢\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u0016\u0010\u001a\u001a\u00020\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u001c"}, d2 = {"Lcom/vantedge/app/data/storage/UserPreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "onboardingCompleteFlow", "Lkotlinx/coroutines/flow/Flow;", "", "getOnboardingCompleteFlow", "()Lkotlinx/coroutines/flow/Flow;", "userProfileFlow", "Lcom/vantedge/app/data/model/UserProfile;", "getUserProfileFlow", "parseCertifications", "", "Lcom/vantedge/app/data/model/Certification;", "json", "", "parseWorkHistory", "Lcom/vantedge/app/data/model/WorkExperience;", "saveProfile", "", Scopes.PROFILE, "(Lcom/vantedge/app/data/model/UserProfile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "serializeCertifications", "list", "serializeWorkHistory", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class UserPreferences {
    private final Context context;
    private final Flow<Boolean> onboardingCompleteFlow;
    private final Flow<UserProfile> userProfileFlow;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int $stable = 8;
    private static final Preferences.Key<String> NAME = PreferencesKeys.stringKey("name");
    private static final Preferences.Key<String> EMAIL = PreferencesKeys.stringKey("email");
    private static final Preferences.Key<String> PHONE = PreferencesKeys.stringKey(HintConstants.AUTOFILL_HINT_PHONE);
    private static final Preferences.Key<String> LOCATION = PreferencesKeys.stringKey("location");
    private static final Preferences.Key<String> LINKEDIN = PreferencesKeys.stringKey("linkedin");
    private static final Preferences.Key<String> SUMMARY = PreferencesKeys.stringKey(ErrorBundle.SUMMARY_ENTRY);
    private static final Preferences.Key<String> SKILLS = PreferencesKeys.stringKey("skills");
    private static final Preferences.Key<String> EDUCATION = PreferencesKeys.stringKey("education");
    private static final Preferences.Key<String> CERTIFICATIONS = PreferencesKeys.stringKey("certifications");
    private static final Preferences.Key<String> LANGUAGES = PreferencesKeys.stringKey("languages");
    private static final Preferences.Key<String> WORK_HISTORY = PreferencesKeys.stringKey("work_history");
    private static final Preferences.Key<Boolean> ONBOARDING_COMPLETE = PreferencesKeys.booleanKey("onboarding_complete");

    public UserPreferences(Context context) {
        DataStore dataStore;
        DataStore dataStore2;
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        dataStore = UserPreferencesKt.getDataStore(this.context);
        final Flow $this$map$iv = dataStore.getData();
        this.userProfileFlow = new Flow<UserProfile>() { // from class: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$1

            /* compiled from: Emitters.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", "T", "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 9, 0}, xi = 48)
            /* renamed from: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ UserPreferences this$0;

                /* compiled from: Emitters.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                @DebugMetadata(c = "com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$1$2", f = "UserPreferences.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                /* renamed from: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserPreferences userPreferences) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userPreferences;
                }

                /* JADX WARN: Removed duplicated region for block: B:109:0x02cc  */
                /* JADX WARN: Removed duplicated region for block: B:112:0x02ed A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:113:0x02ee  */
                /* JADX WARN: Removed duplicated region for block: B:117:0x01fb  */
                /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
                /* JADX WARN: Removed duplicated region for block: B:14:0x003e  */
                /* JADX WARN: Removed duplicated region for block: B:58:0x0164  */
                /* JADX WARN: Removed duplicated region for block: B:82:0x0214  */
                /* JADX WARN: Removed duplicated region for block: B:85:0x0225  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r33, kotlin.coroutines.Continuation r34) {
                    /*
                        Method dump skipped, instructions count: 764
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super UserProfile> flowCollector, Continuation $completion) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), $completion);
                return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        dataStore2 = UserPreferencesKt.getDataStore(this.context);
        final Flow $this$map$iv2 = dataStore2.getData();
        this.onboardingCompleteFlow = new Flow<Boolean>() { // from class: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2

            /* compiled from: Emitters.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", "T", "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 9, 0}, xi = 48)
            /* renamed from: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: Emitters.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                @DebugMetadata(c = "com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2$2", f = "UserPreferences.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                /* renamed from: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
                /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L14
                        r0 = r8
                        com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2$2$1 r0 = (com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r1 = r1 & r2
                        if (r1 == 0) goto L14
                        int r8 = r0.label
                        int r8 = r8 - r2
                        r0.label = r8
                        goto L19
                    L14:
                        com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2$2$1 r0 = new com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L19:
                        r8 = r0
                        java.lang.Object r0 = r8.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r8.label
                        switch(r2) {
                            case 0: goto L32;
                            case 1: goto L2d;
                            default: goto L25;
                        }
                    L25:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2d:
                        r7 = 0
                        kotlin.ResultKt.throwOnFailure(r0)
                        goto L60
                    L32:
                        kotlin.ResultKt.throwOnFailure(r0)
                        r2 = r6
                        kotlinx.coroutines.flow.FlowCollector r2 = r2.$this_unsafeFlow
                        r3 = 0
                        r4 = r8
                        kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                        androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7
                        r4 = 0
                        androidx.datastore.preferences.core.Preferences$Key r5 = com.vantedge.app.data.storage.UserPreferences.access$getONBOARDING_COMPLETE$cp()
                        java.lang.Object r5 = r7.get(r5)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L50
                        boolean r5 = r5.booleanValue()
                        goto L51
                    L50:
                        r5 = 0
                    L51:
                        java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                        r4 = 1
                        r8.label = r4
                        java.lang.Object r7 = r2.emit(r7, r8)
                        if (r7 != r1) goto L5f
                        return r1
                    L5f:
                        r7 = r3
                    L60:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.UserPreferences$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super Boolean> flowCollector, Continuation $completion) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), $completion);
                return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
    }

    /* compiled from: UserPreferences.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0007R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0007R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0007R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0007R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0007¨\u0006\u001f"}, d2 = {"Lcom/vantedge/app/data/storage/UserPreferences$Companion;", "", "()V", "CERTIFICATIONS", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getCERTIFICATIONS", "()Landroidx/datastore/preferences/core/Preferences$Key;", "EDUCATION", "getEDUCATION", "EMAIL", "getEMAIL", "LANGUAGES", "getLANGUAGES", "LINKEDIN", "getLINKEDIN", "LOCATION", "getLOCATION", "NAME", "getNAME", "ONBOARDING_COMPLETE", "", "getONBOARDING_COMPLETE", "PHONE", "getPHONE", "SKILLS", "getSKILLS", "SUMMARY", "getSUMMARY", "WORK_HISTORY", "getWORK_HISTORY", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Preferences.Key<String> getNAME() {
            return UserPreferences.NAME;
        }

        public final Preferences.Key<String> getEMAIL() {
            return UserPreferences.EMAIL;
        }

        public final Preferences.Key<String> getPHONE() {
            return UserPreferences.PHONE;
        }

        public final Preferences.Key<String> getLOCATION() {
            return UserPreferences.LOCATION;
        }

        public final Preferences.Key<String> getLINKEDIN() {
            return UserPreferences.LINKEDIN;
        }

        public final Preferences.Key<String> getSUMMARY() {
            return UserPreferences.SUMMARY;
        }

        public final Preferences.Key<String> getSKILLS() {
            return UserPreferences.SKILLS;
        }

        public final Preferences.Key<String> getEDUCATION() {
            return UserPreferences.EDUCATION;
        }

        public final Preferences.Key<String> getCERTIFICATIONS() {
            return UserPreferences.CERTIFICATIONS;
        }

        public final Preferences.Key<String> getLANGUAGES() {
            return UserPreferences.LANGUAGES;
        }

        public final Preferences.Key<String> getWORK_HISTORY() {
            return UserPreferences.WORK_HISTORY;
        }

        public final Preferences.Key<Boolean> getONBOARDING_COMPLETE() {
            return UserPreferences.ONBOARDING_COMPLETE;
        }
    }

    public final Flow<UserProfile> getUserProfileFlow() {
        return this.userProfileFlow;
    }

    public final Flow<Boolean> getOnboardingCompleteFlow() {
        return this.onboardingCompleteFlow;
    }

    public final Object saveProfile(UserProfile profile, Continuation<? super Unit> continuation) {
        DataStore dataStore;
        dataStore = UserPreferencesKt.getDataStore(this.context);
        Object edit = PreferencesKt.edit(dataStore, new UserPreferences$saveProfile$2(profile, this, null), continuation);
        return edit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? edit : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String serializeCertifications(List<Certification> list) {
        JSONArray array = new JSONArray();
        List<Certification> $this$forEach$iv = list;
        for (Object element$iv : $this$forEach$iv) {
            Certification cert = (Certification) element$iv;
            JSONObject $this$serializeCertifications_u24lambda_u249_u24lambda_u248 = new JSONObject();
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("id", cert.getId());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("name", cert.getName());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("issuer", cert.getIssuer());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("localFileUri", cert.getLocalFileUri());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("cloudUrl", cert.getCloudUrl());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("verificationUrl", cert.getVerificationUrl());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("issueDate", cert.getIssueDate());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("expiryDate", cert.getExpiryDate());
            $this$serializeCertifications_u24lambda_u249_u24lambda_u248.put("dateAdded", cert.getDateAdded());
            array.put($this$serializeCertifications_u24lambda_u249_u24lambda_u248);
        }
        String jSONArray = array.toString();
        Intrinsics.checkNotNullExpressionValue(jSONArray, "toString(...)");
        return jSONArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Certification> parseCertifications(String json) {
        String str = "optString(...)";
        try {
            JSONArray array = new JSONArray(json);
            Iterable $this$map$iv = RangesKt.until(0, array.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                JSONObject obj = array.getJSONObject(item$iv$iv);
                String optString = obj.optString("id");
                Intrinsics.checkNotNullExpressionValue(optString, str);
                String optString2 = obj.optString("name");
                Intrinsics.checkNotNullExpressionValue(optString2, str);
                String optString3 = obj.optString("issuer");
                Intrinsics.checkNotNullExpressionValue(optString3, str);
                String str2 = str;
                String it2 = obj.optString("localFileUri");
                Intrinsics.checkNotNull(it2);
                String str3 = StringsKt.isBlank(it2) ^ true ? it2 : null;
                String it3 = obj.optString("cloudUrl");
                Intrinsics.checkNotNull(it3);
                String str4 = StringsKt.isBlank(it3) ^ true ? it3 : null;
                String it4 = obj.optString("verificationUrl");
                Intrinsics.checkNotNull(it4);
                destination$iv$iv.add(new Certification(optString, optString2, optString3, str3, str4, StringsKt.isBlank(it4) ^ true ? it4 : null, obj.optLong("issueDate"), obj.optLong("expiryDate"), obj.optLong("dateAdded")));
                str = str2;
            }
            return (List) destination$iv$iv;
        } catch (Exception e) {
            return CollectionsKt.emptyList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String serializeWorkHistory(List<WorkExperience> list) {
        JSONArray array = new JSONArray();
        List<WorkExperience> $this$forEach$iv = list;
        for (Object element$iv : $this$forEach$iv) {
            WorkExperience w = (WorkExperience) element$iv;
            JSONObject $this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414 = new JSONObject();
            $this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414.put("role", w.getRole());
            $this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414.put("company", w.getCompany());
            $this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414.put("startDate", w.getStartDate());
            $this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414.put("endDate", w.getEndDate());
            $this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414.put("description", w.getDescription());
            array.put($this$serializeWorkHistory_u24lambda_u2415_u24lambda_u2414);
        }
        String jSONArray = array.toString();
        Intrinsics.checkNotNullExpressionValue(jSONArray, "toString(...)");
        return jSONArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<WorkExperience> parseWorkHistory(String json) {
        String str = "optString(...)";
        try {
            JSONArray array = new JSONArray(json);
            Iterable $this$map$iv = RangesKt.until(0, array.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                JSONObject obj = array.getJSONObject(item$iv$iv);
                String optString = obj.optString("role");
                Intrinsics.checkNotNullExpressionValue(optString, str);
                String optString2 = obj.optString("company");
                Intrinsics.checkNotNullExpressionValue(optString2, str);
                JSONArray array2 = array;
                String optString3 = obj.optString("startDate");
                Intrinsics.checkNotNullExpressionValue(optString3, str);
                String optString4 = obj.optString("endDate");
                Intrinsics.checkNotNullExpressionValue(optString4, str);
                Iterable $this$map$iv2 = $this$map$iv;
                String optString5 = obj.optString("description");
                Intrinsics.checkNotNullExpressionValue(optString5, str);
                destination$iv$iv.add(new WorkExperience(optString, optString2, optString3, optString4, optString5));
                array = array2;
                $this$map$iv = $this$map$iv2;
                str = str;
            }
            return (List) destination$iv$iv;
        } catch (Exception e) {
            return CollectionsKt.emptyList();
        }
    }
}
