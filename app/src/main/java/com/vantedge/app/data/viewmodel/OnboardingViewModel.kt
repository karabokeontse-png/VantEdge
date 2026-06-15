package com.vantedge.app.data.viewmodel;

import android.net.Uri;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.engine.ProfileExtractionEngine;
import com.vantedge.app.data.model.AcquisitionMode;
import com.vantedge.app.data.model.OnboardingDraft;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.storage.OnboardingDraftStore;
import com.vantedge.app.data.viewmodel.OnboardingExtractionState;
import com.vantedge.app.domain.OnboardingCommitService;
import com.vantedge.app.util.TelemetryCollector;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: OnboardingViewModel.kt */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 K2\u00020\u0001:\u0001KB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010-\u001a\u00020\r2\u0006\u0010.\u001a\u00020\u0012J\u0006\u0010/\u001a\u00020\rJ\u0006\u00100\u001a\u00020\rJ\u0006\u00101\u001a\u00020\rJ\u0006\u00102\u001a\u00020\rJ\u001e\u00103\u001a\u00020\r2\u0006\u00104\u001a\u00020\u00162\u0006\u00105\u001a\u000206H\u0082@¢\u0006\u0002\u00107J\b\u00108\u001a\u00020\rH\u0002J\u000e\u00109\u001a\u00020\r2\u0006\u0010.\u001a\u00020\u0012J\u0006\u0010:\u001a\u00020\rJ\u001e\u0010;\u001a\u00020\r2\u0006\u00104\u001a\u00020\u00162\u0006\u0010<\u001a\u00020\u0012H\u0082@¢\u0006\u0002\u0010=J\u000e\u0010>\u001a\u00020\r2\u0006\u0010?\u001a\u00020@J\u000e\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020CJ\"\u0010D\u001a\u00020\r2\u0012\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100FH\u0082@¢\u0006\u0002\u0010GJ\u000e\u0010H\u001a\u00020\r2\u0006\u0010I\u001a\u00020JR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u001c¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00140\u001c¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001eR\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006L"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingViewModel;", "Landroidx/lifecycle/ViewModel;", "extractionEngine", "Lcom/vantedge/app/data/engine/ProfileExtractionEngine;", "commitService", "Lcom/vantedge/app/domain/OnboardingCommitService;", "draftStore", "Lcom/vantedge/app/data/storage/OnboardingDraftStore;", "telemetryCollector", "Lcom/vantedge/app/util/TelemetryCollector;", "(Lcom/vantedge/app/data/engine/ProfileExtractionEngine;Lcom/vantedge/app/domain/OnboardingCommitService;Lcom/vantedge/app/data/storage/OnboardingDraftStore;Lcom/vantedge/app/util/TelemetryCollector;)V", "_commitComplete", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_draft", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/vantedge/app/data/model/OnboardingDraft;", "_error", "", "_extractionState", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "_extractionToken", "", "commitComplete", "Lkotlinx/coroutines/flow/SharedFlow;", "getCommitComplete", "()Lkotlinx/coroutines/flow/SharedFlow;", "draft", "Lkotlinx/coroutines/flow/StateFlow;", "getDraft", "()Lkotlinx/coroutines/flow/StateFlow;", "draftMutex", "Lkotlinx/coroutines/sync/Mutex;", "error", "getError", "extractionJob", "Lkotlinx/coroutines/Job;", "extractionMutex", "extractionState", "getExtractionState", "extractionToken", "getExtractionToken", "isCommitting", "Ljava/util/concurrent/atomic/AtomicBoolean;", "sessionId", "addSkill", "skillValue", "advanceToChoosePath", "advanceToFinalReview", "clearError", "commitProfile", "handleSuccess", "token", "extraction", "Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "(JLcom/vantedge/app/data/model/StructuredProfileExtraction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadDraftIfExists", "removeSkill", "resetToChoosePath", "runStructuredExtraction", "rawText", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectAcquisitionMode", "mode", "Lcom/vantedge/app/data/model/AcquisitionMode;", "startExtraction", "uri", "Landroid/net/Uri;", "updateDraft", "transform", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateEditedProfile", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class OnboardingViewModel extends ViewModel {
    private static final String TAG = "OnboardingViewModel";
    private final MutableSharedFlow<Unit> _commitComplete;
    private final MutableStateFlow<OnboardingDraft> _draft;
    private final MutableStateFlow<String> _error;
    private final MutableStateFlow<OnboardingExtractionState> _extractionState;
    private final MutableStateFlow<Long> _extractionToken;
    private final SharedFlow<Unit> commitComplete;
    private final OnboardingCommitService commitService;
    private final StateFlow<OnboardingDraft> draft;
    private final Mutex draftMutex;
    private final OnboardingDraftStore draftStore;
    private final StateFlow<String> error;
    private final ProfileExtractionEngine extractionEngine;
    private Job extractionJob;
    private final Mutex extractionMutex;
    private final StateFlow<OnboardingExtractionState> extractionState;
    private final StateFlow<Long> extractionToken;
    private final AtomicBoolean isCommitting;
    private final String sessionId;
    private final TelemetryCollector telemetryCollector;
    public static final int $stable = 8;

    public OnboardingViewModel(ProfileExtractionEngine extractionEngine, OnboardingCommitService commitService, OnboardingDraftStore draftStore, TelemetryCollector telemetryCollector) {
        Intrinsics.checkNotNullParameter(extractionEngine, "extractionEngine");
        Intrinsics.checkNotNullParameter(commitService, "commitService");
        Intrinsics.checkNotNullParameter(draftStore, "draftStore");
        Intrinsics.checkNotNullParameter(telemetryCollector, "telemetryCollector");
        this.extractionEngine = extractionEngine;
        this.commitService = commitService;
        this.draftStore = draftStore;
        this.telemetryCollector = telemetryCollector;
        this.extractionMutex = MutexKt.Mutex$default(false, 1, null);
        this.draftMutex = MutexKt.Mutex$default(false, 1, null);
        this.isCommitting = new AtomicBoolean(false);
        String uuid = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "toString(...)");
        this.sessionId = uuid;
        this._draft = StateFlowKt.MutableStateFlow(new OnboardingDraft(null, null, null, null, null, null, 63, null));
        this.draft = FlowKt.asStateFlow(this._draft);
        this._extractionState = StateFlowKt.MutableStateFlow(OnboardingExtractionState.Idle.INSTANCE);
        this.extractionState = FlowKt.asStateFlow(this._extractionState);
        this._commitComplete = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5, null);
        this.commitComplete = FlowKt.asSharedFlow(this._commitComplete);
        this._error = StateFlowKt.MutableStateFlow(null);
        this.error = FlowKt.asStateFlow(this._error);
        this._extractionToken = StateFlowKt.MutableStateFlow(0L);
        this.extractionToken = FlowKt.asStateFlow(this._extractionToken);
        loadDraftIfExists();
    }

    public final StateFlow<OnboardingDraft> getDraft() {
        return this.draft;
    }

    public final StateFlow<OnboardingExtractionState> getExtractionState() {
        return this.extractionState;
    }

    public final SharedFlow<Unit> getCommitComplete() {
        return this.commitComplete;
    }

    public final StateFlow<String> getError() {
        return this.error;
    }

    public final StateFlow<Long> getExtractionToken() {
        return this.extractionToken;
    }

    private final void loadDraftIfExists() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$loadDraftIfExists$1(this, null), 3, null);
    }

    public final void selectAcquisitionMode(AcquisitionMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$selectAcquisitionMode$1(mode, this, null), 3, null);
    }

    public final void startExtraction(Uri uri) {
        Job launch$default;
        Intrinsics.checkNotNullParameter(uri, "uri");
        Log.i(TAG, "[Extraction] ENTRY: startExtraction called, uri=" + uri);
        Job job = this.extractionJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        launch$default = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$startExtraction$1(this, uri, null), 3, null);
        this.extractionJob = launch$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object runStructuredExtraction(long r18, java.lang.String r20, kotlin.coroutines.Continuation<? super kotlin.Unit> r21) {
        /*
            Method dump skipped, instructions count: 536
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.OnboardingViewModel.runStructuredExtraction(long, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object handleSuccess(long r7, final com.vantedge.app.data.model.StructuredProfileExtraction r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r6 = this;
            boolean r0 = r10 instanceof com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$1
            if (r0 == 0) goto L14
            r0 = r10
            com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$1 r0 = (com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L19
        L14:
            com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$1 r0 = new com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$1
            r0.<init>(r6, r10)
        L19:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            java.lang.String r3 = "OnboardingViewModel"
            switch(r2) {
                case 0: goto L3b;
                case 1: goto L2f;
                default: goto L27;
            }
        L27:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2f:
            java.lang.Object r7 = r10.L$1
            com.vantedge.app.data.model.StructuredProfileExtraction r7 = (com.vantedge.app.data.model.StructuredProfileExtraction) r7
            java.lang.Object r8 = r10.L$0
            com.vantedge.app.data.viewmodel.OnboardingViewModel r8 = (com.vantedge.app.data.viewmodel.OnboardingViewModel) r8
            kotlin.ResultKt.throwOnFailure(r0)
            goto L86
        L3b:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> r4 = r2._extractionToken
            java.lang.Object r4 = r4.getValue()
            java.lang.Number r4 = (java.lang.Number) r4
            long r4 = r4.longValue()
            int r4 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r4 == 0) goto L57
            java.lang.String r7 = "[Extraction] EXIT: token mismatch in handleSuccess — stale job"
            android.util.Log.i(r3, r7)
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L57:
            float r7 = r9.getOverallConfidence()
            r8 = 1053609165(0x3ecccccd, float:0.4)
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 >= 0) goto L69
            kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> r7 = r2._error
            java.lang.String r8 = "Low confidence extraction. Please review."
            r7.setValue(r8)
        L69:
            com.vantedge.app.domain.OnboardingCommitService r7 = r2.commitService
            com.vantedge.app.data.model.UserProfile r7 = r7.extractionToProfile(r9)
            com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$2 r8 = new com.vantedge.app.data.viewmodel.OnboardingViewModel$handleSuccess$2
            r8.<init>()
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            r10.L$0 = r2
            r10.L$1 = r9
            r4 = 1
            r10.label = r4
            java.lang.Object r7 = r2.updateDraft(r8, r10)
            if (r7 != r1) goto L84
            return r1
        L84:
            r7 = r9
            r8 = r2
        L86:
            float r9 = r7.getOverallConfidence()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[Extraction] EXIT: success — confidence="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r9 = r1.append(r9)
            java.lang.String r9 = r9.toString()
            android.util.Log.i(r3, r9)
            kotlinx.coroutines.flow.MutableStateFlow<com.vantedge.app.data.viewmodel.OnboardingExtractionState> r9 = r8._extractionState
            com.vantedge.app.data.viewmodel.OnboardingExtractionState$Success r1 = com.vantedge.app.data.viewmodel.OnboardingExtractionState.Success.INSTANCE
            r9.setValue(r1)
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.OnboardingViewModel.handleSuccess(long, com.vantedge.app.data.model.StructuredProfileExtraction, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void updateEditedProfile(UserProfile profile) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$updateEditedProfile$1(this, profile, null), 3, null);
    }

    public final void addSkill(String skillValue) {
        Intrinsics.checkNotNullParameter(skillValue, "skillValue");
        if (StringsKt.isBlank(skillValue)) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$addSkill$1(this, skillValue, null), 3, null);
    }

    public final void removeSkill(String skillValue) {
        Intrinsics.checkNotNullParameter(skillValue, "skillValue");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$removeSkill$1(this, skillValue, null), 3, null);
    }

    public final void commitProfile() {
        UserProfile profile = this._draft.getValue().getEditedProfile();
        if (profile == null || StringsKt.isBlank(profile.getName()) || StringsKt.isBlank(profile.getEmail()) || profile.getSkills().isEmpty()) {
            this._error.setValue("Complete required fields before continuing");
        } else if (this.isCommitting.compareAndSet(false, true)) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$commitProfile$1(this, null), 3, null);
        }
    }

    public final void clearError() {
        this._error.setValue(null);
    }

    public final void advanceToChoosePath() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$advanceToChoosePath$1(this, null), 3, null);
    }

    public final void advanceToFinalReview() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$advanceToFinalReview$1(this, null), 3, null);
    }

    public final void resetToChoosePath() {
        Log.i(TAG, "[Extraction] CANCEL: resetToChoosePath — cancelling extractionJob");
        Job job = this.extractionJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this._extractionState.setValue(OnboardingExtractionState.Idle.INSTANCE);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new OnboardingViewModel$resetToChoosePath$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0096 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object updateDraft(kotlin.jvm.functions.Function1<? super com.vantedge.app.data.model.OnboardingDraft, com.vantedge.app.data.model.OnboardingDraft> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof com.vantedge.app.data.viewmodel.OnboardingViewModel$updateDraft$1
            if (r0 == 0) goto L14
            r0 = r12
            com.vantedge.app.data.viewmodel.OnboardingViewModel$updateDraft$1 r0 = (com.vantedge.app.data.viewmodel.OnboardingViewModel$updateDraft$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L19
        L14:
            com.vantedge.app.data.viewmodel.OnboardingViewModel$updateDraft$1 r0 = new com.vantedge.app.data.viewmodel.OnboardingViewModel$updateDraft$1
            r0.<init>(r10, r12)
        L19:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r12.label
            switch(r2) {
                case 0: goto L58;
                case 1: goto L43;
                case 2: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L2d:
            r11 = 0
            r1 = 0
            java.lang.Object r2 = r12.L$2
            com.vantedge.app.data.model.OnboardingDraft r2 = (com.vantedge.app.data.model.OnboardingDraft) r2
            r3 = 0
            java.lang.Object r4 = r12.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r5 = r12.L$0
            com.vantedge.app.data.viewmodel.OnboardingViewModel r5 = (com.vantedge.app.data.viewmodel.OnboardingViewModel) r5
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Throwable -> L40
            goto L9b
        L40:
            r1 = move-exception
            goto Lad
        L43:
            r11 = 0
            r2 = 0
            java.lang.Object r3 = r12.L$2
            kotlinx.coroutines.sync.Mutex r3 = (kotlinx.coroutines.sync.Mutex) r3
            java.lang.Object r4 = r12.L$1
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            java.lang.Object r5 = r12.L$0
            com.vantedge.app.data.viewmodel.OnboardingViewModel r5 = (com.vantedge.app.data.viewmodel.OnboardingViewModel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            r9 = r3
            r3 = r2
            r2 = r9
            goto L76
        L58:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            r4 = r11
            kotlinx.coroutines.sync.Mutex r11 = r2.draftMutex
            r3 = 0
            r5 = 0
            r12.L$0 = r2
            r12.L$1 = r4
            r12.L$2 = r11
            r6 = 1
            r12.label = r6
            java.lang.Object r6 = r11.lock(r3, r12)
            if (r6 != r1) goto L72
            return r1
        L72:
            r9 = r2
            r2 = r11
            r11 = r5
            r5 = r9
        L76:
            r6 = 0
            kotlinx.coroutines.flow.MutableStateFlow<com.vantedge.app.data.model.OnboardingDraft> r7 = r5._draft     // Catch: java.lang.Throwable -> Lab
            java.lang.Object r7 = r7.getValue()     // Catch: java.lang.Throwable -> Lab
            java.lang.Object r7 = r4.invoke(r7)     // Catch: java.lang.Throwable -> Lab
            com.vantedge.app.data.model.OnboardingDraft r7 = (com.vantedge.app.data.model.OnboardingDraft) r7     // Catch: java.lang.Throwable -> Lab
            r4 = r7
            com.vantedge.app.data.storage.OnboardingDraftStore r7 = r5.draftStore     // Catch: java.lang.Throwable -> Lab
            r12.L$0 = r5     // Catch: java.lang.Throwable -> Lab
            r12.L$1 = r2     // Catch: java.lang.Throwable -> Lab
            r12.L$2 = r4     // Catch: java.lang.Throwable -> Lab
            r8 = 2
            r12.label = r8     // Catch: java.lang.Throwable -> Lab
            java.lang.Object r7 = r7.saveDraft(r4, r12)     // Catch: java.lang.Throwable -> Lab
            if (r7 != r1) goto L97
            return r1
        L97:
            r1 = r6
            r9 = r4
            r4 = r2
            r2 = r9
        L9b:
            kotlinx.coroutines.flow.MutableStateFlow<com.vantedge.app.data.model.OnboardingDraft> r6 = r5._draft     // Catch: java.lang.Throwable -> L40
            r6.setValue(r2)     // Catch: java.lang.Throwable -> L40
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L40
            r4.unlock(r3)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        Lab:
            r1 = move-exception
            r4 = r2
        Lad:
            r4.unlock(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.OnboardingViewModel.updateDraft(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
