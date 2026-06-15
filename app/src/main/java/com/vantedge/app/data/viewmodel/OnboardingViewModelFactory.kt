package com.vantedge.app.data.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.vantedge.app.data.engine.ProfileExtractionEngine;
import com.vantedge.app.data.storage.OnboardingDraftStore;
import com.vantedge.app.domain.OnboardingCommitService;
import com.vantedge.app.util.TelemetryCollector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnboardingViewModelFactory.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ%\u0010\u000b\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\f0\u000fH\u0016¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "extractionEngine", "Lcom/vantedge/app/data/engine/ProfileExtractionEngine;", "commitService", "Lcom/vantedge/app/domain/OnboardingCommitService;", "draftStore", "Lcom/vantedge/app/data/storage/OnboardingDraftStore;", "telemetryCollector", "Lcom/vantedge/app/util/TelemetryCollector;", "(Lcom/vantedge/app/data/engine/ProfileExtractionEngine;Lcom/vantedge/app/domain/OnboardingCommitService;Lcom/vantedge/app/data/storage/OnboardingDraftStore;Lcom/vantedge/app/util/TelemetryCollector;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class OnboardingViewModelFactory implements ViewModelProvider.Factory {
    public static final int $stable = 8;
    private final OnboardingCommitService commitService;
    private final OnboardingDraftStore draftStore;
    private final ProfileExtractionEngine extractionEngine;
    private final TelemetryCollector telemetryCollector;

    public OnboardingViewModelFactory(ProfileExtractionEngine extractionEngine, OnboardingCommitService commitService, OnboardingDraftStore draftStore, TelemetryCollector telemetryCollector) {
        Intrinsics.checkNotNullParameter(extractionEngine, "extractionEngine");
        Intrinsics.checkNotNullParameter(commitService, "commitService");
        Intrinsics.checkNotNullParameter(draftStore, "draftStore");
        Intrinsics.checkNotNullParameter(telemetryCollector, "telemetryCollector");
        this.extractionEngine = extractionEngine;
        this.commitService = commitService;
        this.draftStore = draftStore;
        this.telemetryCollector = telemetryCollector;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        if (modelClass.isAssignableFrom(OnboardingViewModel.class)) {
            return new OnboardingViewModel(this.extractionEngine, this.commitService, this.draftStore, this.telemetryCollector);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
