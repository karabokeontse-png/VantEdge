package com.vantedge.app.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.compose.ActivityResultRegistryKt;
import androidx.activity.compose.ManagedActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.lifecycle.viewmodel.compose.ViewModelKt;
import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.NavType;
import androidx.navigation.Navigator;
import androidx.navigation.PopUpToBuilder;
import androidx.navigation.compose.NavGraphBuilderKt;
import androidx.navigation.compose.NavHostControllerKt;
import androidx.navigation.compose.NavHostKt;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.BuildConfig;
import com.vantedge.app.data.domain.DocumentExportUseCase;
import com.vantedge.app.data.engine.CompatibilityEngine;
import com.vantedge.app.data.engine.GeneratorEngine;
import com.vantedge.app.data.engine.ProfileExtractionEngine;
import com.vantedge.app.data.infrastructure.MediaStoreExporter;
import com.vantedge.app.data.model.AcquisitionMode;
import com.vantedge.app.data.model.DesignConfig;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.model.GenerationMode;
import com.vantedge.app.data.model.OnboardingDraft;
import com.vantedge.app.data.model.OnboardingStage;
import com.vantedge.app.data.model.StructuredProfileExtraction;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.network.AiGateway;
import com.vantedge.app.data.network.GeminiService;
import com.vantedge.app.data.storage.HistoryStore;
import com.vantedge.app.data.storage.OnboardingDraftStore;
import com.vantedge.app.data.storage.TelemetryStorage;
import com.vantedge.app.data.storage.UserPreferences;
import com.vantedge.app.data.storage.VantEdgeDatabase;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import com.vantedge.app.data.viewmodel.DebugViewModel;
import com.vantedge.app.data.viewmodel.OnboardingExtractionState;
import com.vantedge.app.data.viewmodel.OnboardingViewModel;
import com.vantedge.app.data.viewmodel.OnboardingViewModelFactory;
import com.vantedge.app.data.viewmodel.RestorationState;
import com.vantedge.app.domain.OnboardingCommitService;
import com.vantedge.app.domain.OptimizationOrchestrator;
import com.vantedge.app.ui.screens.CVDesignScreenKt;
import com.vantedge.app.ui.screens.ChoosePathScreenKt;
import com.vantedge.app.ui.screens.ColorSchemeScreenKt;
import com.vantedge.app.ui.screens.CycleHistoryScreenKt;
import com.vantedge.app.ui.screens.DashboardScreenKt;
import com.vantedge.app.ui.screens.DebugCalibrationScreenKt;
import com.vantedge.app.ui.screens.EditProfileScreenKt;
import com.vantedge.app.ui.screens.ExtractingScreenKt;
import com.vantedge.app.ui.screens.FinalReviewScreenKt;
import com.vantedge.app.ui.screens.JobInputScreenKt;
import com.vantedge.app.ui.screens.ResultScreenKt;
import com.vantedge.app.ui.screens.ResultScreenMode;
import com.vantedge.app.ui.screens.ReviewExtractionScreenKt;
import com.vantedge.app.ui.screens.WelcomeScreenKt;
import com.vantedge.app.util.TelemetryCollector;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;
import okhttp3.internal.ws.WebSocketProtocol;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: Navigation.kt */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002\u001a\u0014\u0010\u000b\u001a\u00020\u0001*\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f²\u0006\f\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u008a\u0084\u0002²\u0006\n\u0010\t\u001a\u00020\nX\u008a\u0084\u0002²\u0006\n\u0010\u0012\u001a\u00020\u0013X\u008a\u0084\u0002²\u0006\n\u0010\u0014\u001a\u00020\u0015X\u008a\u0084\u0002²\u0006\u0010\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u008a\u0084\u0002"}, d2 = {"AppNavigation", "", "userPreferences", "Lcom/vantedge/app/data/storage/UserPreferences;", "(Lcom/vantedge/app/data/storage/UserPreferences;Landroidx/compose/runtime/Composer;I)V", "canEnter", "", "stage", "Lcom/vantedge/app/data/model/OnboardingStage;", "draft", "Lcom/vantedge/app/data/model/OnboardingDraft;", "navigateOnboarding", "Landroidx/navigation/NavHostController;", "route", "", "app_debug", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "extractionState", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "restorationState", "Lcom/vantedge/app/data/viewmodel/RestorationState;", "cycles", "", "Lcom/vantedge/app/data/model/GenerationCycle;"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NavigationKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void navigateOnboarding(NavHostController $this$navigateOnboarding, String route) {
        $this$navigateOnboarding.navigate(route, new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$navigateOnboarding$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                invoke2(navOptionsBuilder);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(NavOptionsBuilder navigate) {
                Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                navigate.popUpTo("onboarding_extracting", new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$navigateOnboarding$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(PopUpToBuilder popUpToBuilder) {
                        invoke2(popUpToBuilder);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(PopUpToBuilder popUpTo) {
                        Intrinsics.checkNotNullParameter(popUpTo, "$this$popUpTo");
                        popUpTo.setInclusive(true);
                    }
                });
                navigate.setLaunchSingleTop(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean canEnter(OnboardingStage stage, OnboardingDraft draft) {
        if (Intrinsics.areEqual(stage, OnboardingStage.Welcome.INSTANCE) || Intrinsics.areEqual(stage, OnboardingStage.ChoosePath.INSTANCE) || Intrinsics.areEqual(stage, OnboardingStage.UploadingCv.INSTANCE) || Intrinsics.areEqual(stage, OnboardingStage.Extracting.INSTANCE)) {
            return true;
        }
        if (Intrinsics.areEqual(stage, OnboardingStage.ReviewingExtraction.INSTANCE)) {
            return draft.getExtraction() != null;
        }
        if (Intrinsics.areEqual(stage, OnboardingStage.EditingProfile.INSTANCE)) {
            return draft.getEditedProfile() != null;
        }
        if (Intrinsics.areEqual(stage, OnboardingStage.FinalReview.INSTANCE)) {
            return draft.getEditedProfile() != null && (StringsKt.isBlank(draft.getEditedProfile().getName()) ^ true) && (StringsKt.isBlank(draft.getEditedProfile().getEmail()) ^ true) && (draft.getEditedProfile().getSkills().isEmpty() ^ true);
        }
        if (Intrinsics.areEqual(stage, OnboardingStage.Completed.INSTANCE)) {
            return true;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final void AppNavigation(final UserPreferences userPreferences, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Object value$iv5;
        Object value$iv6;
        Object value$iv7;
        Object value$iv8;
        Object value$iv9;
        Object value$iv10;
        CreationExtras.Empty empty;
        Object value$iv11;
        Object value$iv12;
        Object value$iv13;
        CreationExtras extras$iv;
        Intrinsics.checkNotNullParameter(userPreferences, "userPreferences");
        Composer $composer2 = $composer.startRestartGroup(1862250844);
        ComposerKt.sourceInformation($composer2, "C(AppNavigation)97@4391L23,98@4446L7,100@4479L28,101@4528L37,102@4579L50,104@4654L40,105@4726L42,107@4800L43,108@4870L39,110@4934L202,118@5167L40,120@5236L88,124@5360L152,132@5594L291,131@5565L326,142@5922L40,143@5995L54,146@6121L466,145@6092L501,160@6646L30,161@6720L16,162@6800L16,163@6877L16,165@6920L593,189@7778L406,200@8190L1042,228@9238L1186,255@10430L203,263@10639L8127:Navigation.kt#5r64yc");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(1862250844, $changed, -1, "com.vantedge.app.navigation.AppNavigation (Navigation.kt:95)");
        }
        final NavHostController navController = NavHostControllerKt.rememberNavController(new Navigator[0], $composer2, 8);
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) consume;
        $composer2.startReplaceableGroup(68715716);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = new GeminiService();
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        GeminiService geminiService = (GeminiService) value$iv;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68715765);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = new AiGateway(geminiService);
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        AiGateway aiGateway = (AiGateway) value$iv2;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68715816);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv3 = $composer2.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = VantEdgeDatabase.INSTANCE.getInstance(context);
            $composer2.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        VantEdgeDatabase db = (VantEdgeDatabase) value$iv3;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68715891);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv4 = $composer2.rememberedValue();
        if (it$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = new HistoryStore(db.cycleDao());
            $composer2.updateRememberedValue(value$iv4);
        } else {
            value$iv4 = it$iv4;
        }
        final HistoryStore historyStore = (HistoryStore) value$iv4;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68715963);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv5 = $composer2.rememberedValue();
        if (it$iv5 == Composer.INSTANCE.getEmpty()) {
            value$iv5 = new OnboardingDraftStore(context);
            $composer2.updateRememberedValue(value$iv5);
        } else {
            value$iv5 = it$iv5;
        }
        OnboardingDraftStore onboardingDraftStore = (OnboardingDraftStore) value$iv5;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716037);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv6 = $composer2.rememberedValue();
        if (it$iv6 == Composer.INSTANCE.getEmpty()) {
            value$iv6 = new CompatibilityEngine(aiGateway);
            $composer2.updateRememberedValue(value$iv6);
        } else {
            value$iv6 = it$iv6;
        }
        CompatibilityEngine compatibilityEngine = (CompatibilityEngine) value$iv6;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716107);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv7 = $composer2.rememberedValue();
        if (it$iv7 == Composer.INSTANCE.getEmpty()) {
            value$iv7 = new GeneratorEngine(aiGateway);
            $composer2.updateRememberedValue(value$iv7);
        } else {
            value$iv7 = it$iv7;
        }
        GeneratorEngine generatorEngine = (GeneratorEngine) value$iv7;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716171);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv8 = $composer2.rememberedValue();
        if (it$iv8 == Composer.INSTANCE.getEmpty()) {
            value$iv8 = new OptimizationOrchestrator(compatibilityEngine, generatorEngine, historyStore);
            $composer2.updateRememberedValue(value$iv8);
        } else {
            value$iv8 = it$iv8;
        }
        final OptimizationOrchestrator orchestrator = (OptimizationOrchestrator) value$iv8;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716404);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object value$iv14 = $composer2.rememberedValue();
        if (value$iv14 == Composer.INSTANCE.getEmpty()) {
            value$iv14 = new TelemetryCollector(context, null, 2, null);
            $composer2.updateRememberedValue(value$iv14);
        }
        TelemetryCollector telemetryCollector = (TelemetryCollector) value$iv14;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716473);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv9 = $composer2.rememberedValue();
        if (it$iv9 == Composer.INSTANCE.getEmpty()) {
            value$iv9 = new ProfileExtractionEngine(context, aiGateway, telemetryCollector);
            $composer2.updateRememberedValue(value$iv9);
        } else {
            value$iv9 = it$iv9;
        }
        ProfileExtractionEngine extractionEngine = (ProfileExtractionEngine) value$iv9;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716597);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv10 = $composer2.rememberedValue();
        if (it$iv10 == Composer.INSTANCE.getEmpty()) {
            value$iv10 = new OnboardingCommitService(userPreferences, onboardingDraftStore);
            $composer2.updateRememberedValue(value$iv10);
        } else {
            value$iv10 = it$iv10;
        }
        OnboardingCommitService onboardingCommitService = (OnboardingCommitService) value$iv10;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68716831);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object value$iv15 = $composer2.rememberedValue();
        if (value$iv15 == Composer.INSTANCE.getEmpty()) {
            value$iv15 = new OnboardingViewModelFactory(extractionEngine, onboardingCommitService, onboardingDraftStore, telemetryCollector);
            $composer2.updateRememberedValue(value$iv15);
        }
        $composer2.endReplaceableGroup();
        ViewModelProvider.Factory factory$iv = (OnboardingViewModelFactory) value$iv15;
        $composer2.startReplaceableGroup(1729797275);
        ComposerKt.sourceInformation($composer2, "CC(viewModel)P(3,2,1)*80@3834L7,90@4209L68:ViewModel.kt#3tja67");
        ViewModelStoreOwner viewModelStoreOwner$iv = LocalViewModelStoreOwner.INSTANCE.getCurrent($composer2, 6);
        if (viewModelStoreOwner$iv == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        if (viewModelStoreOwner$iv instanceof HasDefaultViewModelProviderFactory) {
            empty = ((HasDefaultViewModelProviderFactory) viewModelStoreOwner$iv).getDefaultViewModelCreationExtras();
        } else {
            empty = CreationExtras.Empty.INSTANCE;
        }
        CreationExtras extras$iv2 = empty;
        ViewModel viewModel = ViewModelKt.viewModel(OnboardingViewModel.class, viewModelStoreOwner$iv, null, factory$iv, extras$iv2, $composer2, ((512 << 3) & 896) | 36936, 0);
        $composer2.endReplaceableGroup();
        final OnboardingViewModel onboardingViewModel = (OnboardingViewModel) viewModel;
        $composer2.startReplaceableGroup(68717159);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv11 = $composer2.rememberedValue();
        if (it$iv11 == Composer.INSTANCE.getEmpty()) {
            value$iv11 = new MediaStoreExporter(context);
            $composer2.updateRememberedValue(value$iv11);
        } else {
            value$iv11 = it$iv11;
        }
        MediaStoreExporter mediaStoreExporter = (MediaStoreExporter) value$iv11;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68717232);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv12 = $composer2.rememberedValue();
        if (it$iv12 == Composer.INSTANCE.getEmpty()) {
            value$iv12 = new DocumentExportUseCase(mediaStoreExporter);
            $composer2.updateRememberedValue(value$iv12);
        } else {
            value$iv12 = it$iv12;
        }
        final DocumentExportUseCase documentExportUseCase = (DocumentExportUseCase) value$iv12;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(68717358);
        ComposerKt.sourceInformation($composer2, "CC(remember):Navigation.kt#9igjgp");
        Object it$iv13 = $composer2.rememberedValue();
        if (it$iv13 == Composer.INSTANCE.getEmpty()) {
            value$iv13 = new ViewModelProvider.Factory() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$cycleViewModel$1$1
                @Override // androidx.lifecycle.ViewModelProvider.Factory
                public <T extends ViewModel> T create(Class<T> modelClass) {
                    Intrinsics.checkNotNullParameter(modelClass, "modelClass");
                    return new CycleViewModel(OptimizationOrchestrator.this, historyStore, documentExportUseCase);
                }
            };
            $composer2.updateRememberedValue(value$iv13);
        } else {
            value$iv13 = it$iv13;
        }
        $composer2.endReplaceableGroup();
        ViewModelProvider.Factory factory$iv2 = (NavigationKt$AppNavigation$cycleViewModel$1$1) value$iv13;
        $composer2.startReplaceableGroup(1729797275);
        ComposerKt.sourceInformation($composer2, "CC(viewModel)P(3,2,1)*80@3834L7,90@4209L68:ViewModel.kt#3tja67");
        ViewModelStoreOwner viewModelStoreOwner$iv2 = LocalViewModelStoreOwner.INSTANCE.getCurrent($composer2, 6);
        if (viewModelStoreOwner$iv2 == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        if (viewModelStoreOwner$iv2 instanceof HasDefaultViewModelProviderFactory) {
            extras$iv = ((HasDefaultViewModelProviderFactory) viewModelStoreOwner$iv2).getDefaultViewModelCreationExtras();
        } else {
            extras$iv = CreationExtras.Empty.INSTANCE;
        }
        ViewModel viewModel2 = ViewModelKt.viewModel(CycleViewModel.class, viewModelStoreOwner$iv2, null, factory$iv2, extras$iv, $composer2, ((KyberEngine.KyberPolyBytes << 3) & 896) | 36936, 0);
        $composer2.endReplaceableGroup();
        final CycleViewModel cycleViewModel = (CycleViewModel) viewModel2;
        final State profile$delegate = SnapshotStateKt.collectAsState(userPreferences.getUserProfileFlow(), null, null, $composer2, 56, 2);
        final State draft$delegate = SnapshotStateKt.collectAsState(onboardingViewModel.getDraft(), null, $composer2, 8, 1);
        final State extractionState$delegate = SnapshotStateKt.collectAsState(onboardingViewModel.getExtractionState(), null, $composer2, 8, 1);
        State restorationState$delegate = SnapshotStateKt.collectAsState(cycleViewModel.getRestorationState(), null, $composer2, 8, 1);
        final ManagedActivityResultLauncher cvFileLauncher = ActivityResultRegistryKt.rememberLauncherForActivityResult(new ActivityResultContracts.OpenDocument(), new Function1<Uri, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$cvFileLauncher$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Uri uri) {
                invoke2(uri);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Uri uri) {
                if (uri != null) {
                    Context context2 = context;
                    OnboardingViewModel onboardingViewModel2 = onboardingViewModel;
                    try {
                        context2.getContentResolver().takePersistableUriPermission(uri, 3);
                        onboardingViewModel2.startExtraction(uri);
                    } catch (SecurityException e) {
                        onboardingViewModel2.clearError();
                    }
                }
            }
        }, $composer2, 8);
        $composer2.startReplaceableGroup(68718756);
        ComposerKt.sourceInformation($composer2, "182@7550L68");
        if (AppNavigation$lambda$15(profile$delegate) == null) {
            BoxKt.Box(BackgroundKt.m211backgroundbw27NRU$default(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), ColorKt.Color(4279045389L), null, 2, null), $composer2, 6);
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                        invoke(composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(Composer composer, int i) {
                        NavigationKt.AppNavigation(UserPreferences.this, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                    }
                });
                return;
            }
            return;
        }
        $composer2.endReplaceableGroup();
        UserProfile AppNavigation$lambda$15 = AppNavigation$lambda$15(profile$delegate);
        Intrinsics.checkNotNull(AppNavigation$lambda$15);
        boolean isOnboarded = !StringsKt.isBlank(AppNavigation$lambda$15.getName());
        String startDestination = isOnboarded ? "dashboard" : "onboarding_welcome";
        EffectsKt.LaunchedEffect(AppNavigation$lambda$18(restorationState$delegate), new NavigationKt$AppNavigation$2(startDestination, navController, restorationState$delegate, null), $composer2, 64);
        EffectsKt.LaunchedEffect(Unit.INSTANCE, new NavigationKt$AppNavigation$3(cycleViewModel, navController, null), $composer2, 70);
        EffectsKt.LaunchedEffect(AppNavigation$lambda$16(draft$delegate).getStage(), new NavigationKt$AppNavigation$4(onboardingViewModel, navController, draft$delegate, null), $composer2, 64);
        EffectsKt.LaunchedEffect(Unit.INSTANCE, new NavigationKt$AppNavigation$5(onboardingViewModel, navController, null), $composer2, 70);
        NavHostKt.NavHost(navController, startDestination, null, null, null, null, null, null, null, new Function1<NavGraphBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(NavGraphBuilder navGraphBuilder) {
                invoke2(navGraphBuilder);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(NavGraphBuilder NavHost) {
                Intrinsics.checkNotNullParameter(NavHost, "$this$NavHost");
                final OnboardingViewModel onboardingViewModel2 = OnboardingViewModel.this;
                NavGraphBuilderKt.composable$default(NavHost, "onboarding_welcome", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(329480762, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.1
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C266@10773L73:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(329480762, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:266)");
                        }
                        final OnboardingViewModel onboardingViewModel3 = OnboardingViewModel.this;
                        WelcomeScreenKt.WelcomeScreen(new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.1.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                OnboardingViewModel.this.advanceToChoosePath();
                            }
                        }, $composer3, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final OnboardingViewModel onboardingViewModel3 = OnboardingViewModel.this;
                final ManagedActivityResultLauncher<String[], Uri> managedActivityResultLauncher = cvFileLauncher;
                NavGraphBuilderKt.composable$default(NavHost, "onboarding_choose_path", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-815068381, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C270@10917L592:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-815068381, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:270)");
                        }
                        final OnboardingViewModel onboardingViewModel4 = OnboardingViewModel.this;
                        final ManagedActivityResultLauncher<String[], Uri> managedActivityResultLauncher2 = managedActivityResultLauncher;
                        ChoosePathScreenKt.ChoosePathScreen(new Function1<AcquisitionMode, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.2.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(AcquisitionMode acquisitionMode) {
                                invoke2(acquisitionMode);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(AcquisitionMode mode) {
                                Intrinsics.checkNotNullParameter(mode, "mode");
                                OnboardingViewModel.this.selectAcquisitionMode(mode);
                                if (mode == AcquisitionMode.CV_UPLOAD) {
                                    managedActivityResultLauncher2.launch(new String[]{"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"});
                                }
                            }
                        }, $composer3, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final State<OnboardingExtractionState> state = extractionState$delegate;
                final OnboardingViewModel onboardingViewModel4 = OnboardingViewModel.this;
                NavGraphBuilderKt.composable$default(NavHost, "onboarding_extracting", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-947392126, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        OnboardingExtractionState AppNavigation$lambda$17;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C287@11579L241:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-947392126, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:287)");
                        }
                        AppNavigation$lambda$17 = NavigationKt.AppNavigation$lambda$17(state);
                        final OnboardingViewModel onboardingViewModel5 = onboardingViewModel4;
                        ExtractingScreenKt.ExtractingScreen(AppNavigation$lambda$17, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.3.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                OnboardingViewModel.this.clearError();
                                OnboardingViewModel.this.resetToChoosePath();
                            }
                        }, $composer3, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final State<OnboardingDraft> state2 = draft$delegate;
                final OnboardingViewModel onboardingViewModel5 = OnboardingViewModel.this;
                NavGraphBuilderKt.composable$default(NavHost, "onboarding_review", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1079715871, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        OnboardingDraft AppNavigation$lambda$16;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C299@11974L351:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1079715871, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:297)");
                        }
                        AppNavigation$lambda$16 = NavigationKt.AppNavigation$lambda$16(state2);
                        StructuredProfileExtraction extraction = AppNavigation$lambda$16.getExtraction();
                        if (extraction != null) {
                            final OnboardingViewModel onboardingViewModel6 = onboardingViewModel5;
                            Function1<UserProfile, Unit> function1 = new Function1<UserProfile, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.4.1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(UserProfile userProfile) {
                                    invoke2(userProfile);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(UserProfile it2) {
                                    Intrinsics.checkNotNullParameter(it2, "it");
                                    OnboardingViewModel.this.updateEditedProfile(it2);
                                    OnboardingViewModel.this.advanceToFinalReview();
                                }
                            };
                            final OnboardingViewModel onboardingViewModel7 = onboardingViewModel5;
                            ReviewExtractionScreenKt.ReviewExtractionScreen(extraction, function1, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.4.2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    OnboardingViewModel.this.resetToChoosePath();
                                }
                            }, $composer3, 0);
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final State<OnboardingDraft> state3 = draft$delegate;
                final State<UserProfile> state4 = profile$delegate;
                final OnboardingViewModel onboardingViewModel6 = OnboardingViewModel.this;
                NavGraphBuilderKt.composable$default(NavHost, "onboarding_edit_profile", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1212039616, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.5
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        OnboardingDraft AppNavigation$lambda$16;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C311@12411L275:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1212039616, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:311)");
                        }
                        AppNavigation$lambda$16 = NavigationKt.AppNavigation$lambda$16(state3);
                        UserProfile editedProfile = AppNavigation$lambda$16.getEditedProfile();
                        if (editedProfile == null) {
                            editedProfile = NavigationKt.AppNavigation$lambda$15(state4);
                            Intrinsics.checkNotNull(editedProfile);
                        }
                        final OnboardingViewModel onboardingViewModel7 = onboardingViewModel6;
                        EditProfileScreenKt.EditProfileScreen(editedProfile, new Function1<UserProfile, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.5.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(UserProfile userProfile) {
                                invoke2(userProfile);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(UserProfile it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                OnboardingViewModel.this.updateEditedProfile(it2);
                                OnboardingViewModel.this.advanceToFinalReview();
                            }
                        }, $composer3, 8);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final OnboardingViewModel onboardingViewModel7 = OnboardingViewModel.this;
                final State<OnboardingDraft> state5 = draft$delegate;
                final NavHostController navHostController = navController;
                NavGraphBuilderKt.composable$default(NavHost, "onboarding_final_review", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1344363361, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.6
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        OnboardingDraft AppNavigation$lambda$16;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C325@12959L16,323@12841L367:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1344363361, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:321)");
                        }
                        AppNavigation$lambda$16 = NavigationKt.AppNavigation$lambda$16(state5);
                        UserProfile edited = AppNavigation$lambda$16.getEditedProfile();
                        if (edited != null) {
                            String str = (String) SnapshotStateKt.collectAsState(OnboardingViewModel.this.getError(), null, $composer3, 8, 1).getValue();
                            final OnboardingViewModel onboardingViewModel8 = OnboardingViewModel.this;
                            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.6.1
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    OnboardingViewModel.this.commitProfile();
                                }
                            };
                            final NavHostController navHostController2 = navHostController;
                            Function0<Unit> function02 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.6.2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    NavHostController.this.popBackStack();
                                }
                            };
                            final OnboardingViewModel onboardingViewModel9 = OnboardingViewModel.this;
                            FinalReviewScreenKt.FinalReviewScreen(edited, str, function0, function02, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.6.3
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    OnboardingViewModel.this.clearError();
                                }
                            }, $composer3, 8, 0);
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final CycleViewModel cycleViewModel2 = cycleViewModel;
                final NavHostController navHostController2 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "dashboard", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1476687106, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.7
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C334@13280L657:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1476687106, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:334)");
                        }
                        CycleViewModel cycleViewModel3 = CycleViewModel.this;
                        final NavHostController navHostController3 = navHostController2;
                        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.7.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "history", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController4 = navHostController2;
                        Function0<Unit> function02 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.7.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "edit_profile", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController5 = navHostController2;
                        Function0<Unit> function03 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.7.3
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "new_application", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController6 = navHostController2;
                        Function0<Unit> function04 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.7.4
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "quick_analysis", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController7 = navHostController2;
                        Function0<Unit> function05 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.7.5
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "quick_generate", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController8 = navHostController2;
                        DashboardScreenKt.DashboardScreen(cycleViewModel3, function0, function02, function03, function04, function05, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.7.6
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                if (BuildConfig.DEBUG) {
                                    NavController.navigate$default(NavHostController.this, "debug_calibration", null, null, 6, null);
                                }
                            }
                        }, $composer3, 8, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final NavHostController navHostController3 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "cv_design", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1609010851, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.8
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C348@13995L235:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1609010851, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:348)");
                        }
                        final NavHostController navHostController4 = NavHostController.this;
                        Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.8.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String designId) {
                                Intrinsics.checkNotNullParameter(designId, "designId");
                                NavController.navigate$default(NavHostController.this, "cv_color_scheme/" + designId, null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController5 = NavHostController.this;
                        CVDesignScreenKt.CVDesignScreen(function1, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.8.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavHostController.this.popBackStack();
                            }
                        }, $composer3, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                List listOf = CollectionsKt.listOf(NamedNavArgumentKt.navArgument("designId", new Function1<NavArgumentBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.9
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(NavArgumentBuilder navArgumentBuilder) {
                        invoke2(navArgumentBuilder);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(NavArgumentBuilder navArgument) {
                        Intrinsics.checkNotNullParameter(navArgument, "$this$navArgument");
                        navArgument.setType(NavType.StringType);
                    }
                }));
                final CycleViewModel cycleViewModel3 = cycleViewModel;
                final NavHostController navHostController4 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "cv_color_scheme/{designId}", listOf, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1741334596, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.10
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry backStackEntry, Composer $composer3, int $changed2) {
                        final String designId;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
                        ComposerKt.sourceInformation($composer3, "C362@14549L402:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1741334596, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:360)");
                        }
                        Bundle arguments = backStackEntry.getArguments();
                        if (arguments == null || (designId = arguments.getString("designId")) == null) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                                return;
                            }
                            return;
                        }
                        final CycleViewModel cycleViewModel4 = CycleViewModel.this;
                        Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.10.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String schemeId) {
                                Intrinsics.checkNotNullParameter(schemeId, "schemeId");
                                CycleViewModel.this.applyDesign(new DesignConfig(designId, schemeId));
                            }
                        };
                        final NavHostController navHostController5 = navHostController4;
                        ColorSchemeScreenKt.ColorSchemeScreen(function1, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.10.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavHostController.this.popBackStack();
                            }
                        }, $composer3, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), 124, null);
                final CycleViewModel cycleViewModel4 = cycleViewModel;
                final NavHostController navHostController5 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "history", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-1873658341, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.11
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    private static final List<GenerationCycle> invoke$lambda$0(State<? extends List<GenerationCycle>> state6) {
                        Object thisObj$iv = state6.getValue();
                        return (List) thisObj$iv;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C376@15054L16,377@15083L416:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-1873658341, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:376)");
                        }
                        State cycles$delegate = SnapshotStateKt.collectAsState(CycleViewModel.this.getVisibleCyclesFlow(), null, $composer3, 8, 1);
                        List<GenerationCycle> invoke$lambda$0 = invoke$lambda$0(cycles$delegate);
                        CycleViewModel cycleViewModel5 = CycleViewModel.this;
                        final CycleViewModel cycleViewModel6 = CycleViewModel.this;
                        final NavHostController navHostController6 = navHostController5;
                        Function1<GenerationCycle, Unit> function1 = new Function1<GenerationCycle, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.11.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(GenerationCycle generationCycle) {
                                invoke2(generationCycle);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(GenerationCycle cycle) {
                                Intrinsics.checkNotNullParameter(cycle, "cycle");
                                CycleViewModel.this.openCycle(cycle);
                                NavController.navigate$default(navHostController6, "result_historical", null, null, 6, null);
                            }
                        };
                        final CycleViewModel cycleViewModel7 = CycleViewModel.this;
                        Function1<String, Unit> function12 = new Function1<String, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.11.2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String id) {
                                Intrinsics.checkNotNullParameter(id, "id");
                                CycleViewModel.this.deleteCycle(id);
                            }
                        };
                        final NavHostController navHostController7 = navHostController5;
                        CycleHistoryScreenKt.CycleHistoryScreen(invoke$lambda$0, cycleViewModel5, function1, function12, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.11.3
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavHostController.this.popBackStack();
                            }
                        }, $composer3, 72);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final State<UserProfile> state6 = profile$delegate;
                final NavHostController navHostController6 = navController;
                final UserPreferences userPreferences2 = userPreferences;
                NavGraphBuilderKt.composable$default(NavHost, "edit_profile", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(190014477, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.12
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Object value$iv$iv$iv;
                        UserProfile AppNavigation$lambda$152;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C390@15572L24,391@15609L273:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(190014477, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:390)");
                        }
                        $composer3.startReplaceableGroup(773894976);
                        ComposerKt.sourceInformation($composer3, "CC(rememberCoroutineScope)489@20472L144:Effects.kt#9igjgp");
                        $composer3.startReplaceableGroup(-492369756);
                        ComposerKt.sourceInformation($composer3, "CC(remember):Composables.kt#9igjgp");
                        Object it$iv$iv$iv = $composer3.rememberedValue();
                        if (it$iv$iv$iv == Composer.INSTANCE.getEmpty()) {
                            value$iv$iv$iv = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, $composer3));
                            $composer3.updateRememberedValue(value$iv$iv$iv);
                        } else {
                            value$iv$iv$iv = it$iv$iv$iv;
                        }
                        $composer3.endReplaceableGroup();
                        CompositionScopedCoroutineScopeCanceller wrapper$iv = (CompositionScopedCoroutineScopeCanceller) value$iv$iv$iv;
                        final CoroutineScope scope = wrapper$iv.getCoroutineScope();
                        $composer3.endReplaceableGroup();
                        AppNavigation$lambda$152 = NavigationKt.AppNavigation$lambda$15(state6);
                        Intrinsics.checkNotNull(AppNavigation$lambda$152);
                        final NavHostController navHostController7 = navHostController6;
                        final UserPreferences userPreferences3 = userPreferences2;
                        EditProfileScreenKt.EditProfileScreen(AppNavigation$lambda$152, new Function1<UserProfile, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.12.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(UserProfile userProfile) {
                                invoke2(userProfile);
                                return Unit.INSTANCE;
                            }

                            /* compiled from: Navigation.kt */
                            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                            @DebugMetadata(c = "com.vantedge.app.navigation.NavigationKt$AppNavigation$6$12$1$1", f = "Navigation.kt", i = {}, l = {395}, m = "invokeSuspend", n = {}, s = {})
                            /* renamed from: com.vantedge.app.navigation.NavigationKt$AppNavigation$6$12$1$1, reason: invalid class name and collision with other inner class name */
                            static final class C00871 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                final /* synthetic */ UserProfile $updatedProfile;
                                final /* synthetic */ UserPreferences $userPreferences;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                C00871(UserPreferences userPreferences, UserProfile userProfile, Continuation<? super C00871> continuation) {
                                    super(2, continuation);
                                    this.$userPreferences = userPreferences;
                                    this.$updatedProfile = userProfile;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                    return new C00871(this.$userPreferences, this.$updatedProfile, continuation);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                    return ((C00871) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object $result) {
                                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    switch (this.label) {
                                        case 0:
                                            ResultKt.throwOnFailure($result);
                                            this.label = 1;
                                            if (this.$userPreferences.saveProfile(this.$updatedProfile, this) != coroutine_suspended) {
                                                break;
                                            } else {
                                                return coroutine_suspended;
                                            }
                                        case 1:
                                            ResultKt.throwOnFailure($result);
                                            break;
                                        default:
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(UserProfile updatedProfile) {
                                Intrinsics.checkNotNullParameter(updatedProfile, "updatedProfile");
                                BuildersKt__Builders_commonKt.launch$default(CoroutineScope.this, null, null, new C00871(userPreferences3, updatedProfile, null), 3, null);
                                navHostController7.popBackStack();
                            }
                        }, $composer3, 8);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final CycleViewModel cycleViewModel5 = cycleViewModel;
                final State<UserProfile> state7 = profile$delegate;
                final NavHostController navHostController7 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "new_application", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(57690732, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.13
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        UserProfile AppNavigation$lambda$152;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C401@15946L384:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(57690732, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:401)");
                        }
                        CycleViewModel cycleViewModel6 = CycleViewModel.this;
                        AppNavigation$lambda$152 = NavigationKt.AppNavigation$lambda$15(state7);
                        Intrinsics.checkNotNull(AppNavigation$lambda$152);
                        GenerationMode generationMode = GenerationMode.NEW_APPLICATION;
                        final NavHostController navHostController8 = navHostController7;
                        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.13.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavHostController.this.popBackStack();
                            }
                        };
                        final NavHostController navHostController9 = navHostController7;
                        Function0<Unit> function02 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.13.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "result", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController10 = navHostController7;
                        JobInputScreenKt.JobInputScreen(cycleViewModel6, AppNavigation$lambda$152, generationMode, function0, function02, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.13.3
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "cv_design", null, null, 6, null);
                            }
                        }, $composer3, 456);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final CycleViewModel cycleViewModel6 = cycleViewModel;
                final State<UserProfile> state8 = profile$delegate;
                final NavHostController navHostController8 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "quick_analysis", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-74633013, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.14
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        UserProfile AppNavigation$lambda$152;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C412@16393L383:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-74633013, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:412)");
                        }
                        CycleViewModel cycleViewModel7 = CycleViewModel.this;
                        AppNavigation$lambda$152 = NavigationKt.AppNavigation$lambda$15(state8);
                        Intrinsics.checkNotNull(AppNavigation$lambda$152);
                        GenerationMode generationMode = GenerationMode.QUICK_ANALYSIS;
                        final NavHostController navHostController9 = navHostController8;
                        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.14.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavHostController.this.popBackStack();
                            }
                        };
                        final NavHostController navHostController10 = navHostController8;
                        Function0<Unit> function02 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.14.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "result", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController11 = navHostController8;
                        JobInputScreenKt.JobInputScreen(cycleViewModel7, AppNavigation$lambda$152, generationMode, function0, function02, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.14.3
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "cv_design", null, null, 6, null);
                            }
                        }, $composer3, 456);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final CycleViewModel cycleViewModel7 = cycleViewModel;
                final State<UserProfile> state9 = profile$delegate;
                final NavHostController navHostController9 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "quick_generate", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-206956758, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.15
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        UserProfile AppNavigation$lambda$152;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C423@16839L383:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-206956758, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:423)");
                        }
                        CycleViewModel cycleViewModel8 = CycleViewModel.this;
                        AppNavigation$lambda$152 = NavigationKt.AppNavigation$lambda$15(state9);
                        Intrinsics.checkNotNull(AppNavigation$lambda$152);
                        GenerationMode generationMode = GenerationMode.QUICK_GENERATE;
                        final NavHostController navHostController10 = navHostController9;
                        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.15.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavHostController.this.popBackStack();
                            }
                        };
                        final NavHostController navHostController11 = navHostController9;
                        Function0<Unit> function02 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.15.2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "result", null, null, 6, null);
                            }
                        };
                        final NavHostController navHostController12 = navHostController9;
                        JobInputScreenKt.JobInputScreen(cycleViewModel8, AppNavigation$lambda$152, generationMode, function0, function02, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.15.3
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                NavController.navigate$default(NavHostController.this, "cv_design", null, null, 6, null);
                            }
                        }, $composer3, 456);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final NavHostController navHostController10 = navController;
                final CycleViewModel cycleViewModel8 = cycleViewModel;
                NavGraphBuilderKt.composable$default(NavHost, "result", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-339280503, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.16
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C434@17277L163:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-339280503, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:434)");
                        }
                        ResultScreenKt.ResultScreen(NavHostController.this, cycleViewModel8, ResultScreenMode.Live.INSTANCE, $composer3, 456);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                final CycleViewModel cycleViewModel9 = cycleViewModel;
                final NavHostController navHostController11 = navController;
                NavGraphBuilderKt.composable$default(NavHost, "result_historical", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-471604248, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.17
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                        invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        Intrinsics.checkNotNullParameter(it, "it");
                        ComposerKt.sourceInformation($composer3, "C444@17595L192:Navigation.kt#5r64yc");
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-471604248, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:442)");
                        }
                        GenerationCycle cycle = CycleViewModel.this.getCurrentCycle();
                        if (cycle != null) {
                            ResultScreenKt.ResultScreen(navHostController11, CycleViewModel.this, new ResultScreenMode.Historical(cycle), $composer3, 584);
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }), WebSocketProtocol.PAYLOAD_SHORT, null);
                if (BuildConfig.DEBUG) {
                    final Context context2 = context;
                    final NavHostController navHostController12 = navController;
                    NavGraphBuilderKt.composable$default(NavHost, "debug_calibration", null, null, null, null, null, null, ComposableLambdaKt.composableLambdaInstance(-2106426241, true, new Function4<AnimatedContentScope, NavBackStackEntry, Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$6.18
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(AnimatedContentScope animatedContentScope, NavBackStackEntry navBackStackEntry, Composer composer, Integer num) {
                            invoke(animatedContentScope, navBackStackEntry, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(AnimatedContentScope composable, NavBackStackEntry it, Composer $composer3, int $changed2) {
                            Intrinsics.checkNotNullParameter(composable, "$this$composable");
                            Intrinsics.checkNotNullParameter(it, "it");
                            ComposerKt.sourceInformation($composer3, "C454@17940L216,461@18245L16,460@18173L251,465@18441L295:Navigation.kt#5r64yc");
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart(-2106426241, $changed2, -1, "com.vantedge.app.navigation.AppNavigation.<anonymous>.<anonymous> (Navigation.kt:454)");
                            }
                            ViewModelProvider.Factory factory$iv3 = DebugViewModel.INSTANCE.factory(context2, new TelemetryStorage(context2));
                            $composer3.startReplaceableGroup(1729797275);
                            ComposerKt.sourceInformation($composer3, "CC(viewModel)P(3,2,1)*80@3834L7,90@4209L68:ViewModel.kt#3tja67");
                            ViewModelStoreOwner viewModelStoreOwner$iv3 = LocalViewModelStoreOwner.INSTANCE.getCurrent($composer3, 6);
                            if (viewModelStoreOwner$iv3 == null) {
                                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                            }
                            CreationExtras extras$iv3 = viewModelStoreOwner$iv3 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) viewModelStoreOwner$iv3).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                            ViewModel viewModel3 = ViewModelKt.viewModel(DebugViewModel.class, viewModelStoreOwner$iv3, null, factory$iv3, extras$iv3, $composer3, ((512 << 3) & 896) | 36936, 0);
                            $composer3.endReplaceableGroup();
                            final DebugViewModel viewModel4 = (DebugViewModel) viewModel3;
                            List list = (List) SnapshotStateKt.collectAsState(viewModel4.getRecords(), null, $composer3, 8, 1).getValue();
                            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.18.1
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    DebugViewModel.this.exportTelemetry();
                                }
                            };
                            final NavHostController navHostController13 = navHostController12;
                            DebugCalibrationScreenKt.DebugCalibrationScreen(list, function0, new Function0<Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.18.2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    NavHostController.this.popBackStack();
                                }
                            }, $composer3, 8);
                            EffectsKt.LaunchedEffect(viewModel4, new AnonymousClass3(viewModel4, context2, null), $composer3, 72);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }

                        /* compiled from: Navigation.kt */
                        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                        @DebugMetadata(c = "com.vantedge.app.navigation.NavigationKt$AppNavigation$6$18$3", f = "Navigation.kt", i = {}, l = {467}, m = "invokeSuspend", n = {}, s = {})
                        /* renamed from: com.vantedge.app.navigation.NavigationKt$AppNavigation$6$18$3, reason: invalid class name */
                        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                            final /* synthetic */ Context $context;
                            final /* synthetic */ DebugViewModel $viewModel;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass3(DebugViewModel debugViewModel, Context context, Continuation<? super AnonymousClass3> continuation) {
                                super(2, continuation);
                                this.$viewModel = debugViewModel;
                                this.$context = context;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                return new AnonymousClass3(this.$viewModel, this.$context, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object $result) {
                                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                switch (this.label) {
                                    case 0:
                                        ResultKt.throwOnFailure($result);
                                        SharedFlow<Intent> exportEvent = this.$viewModel.getExportEvent();
                                        final Context context = this.$context;
                                        this.label = 1;
                                        if (exportEvent.collect(new FlowCollector() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.6.18.3.1
                                            @Override // kotlinx.coroutines.flow.FlowCollector
                                            public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                                                return emit((Intent) value, (Continuation<? super Unit>) $completion);
                                            }

                                            public final Object emit(Intent intent, Continuation<? super Unit> continuation) {
                                                if (intent != null) {
                                                    context.startActivity(Intent.createChooser(intent, "Share Telemetry Export"));
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, this) != coroutine_suspended) {
                                            break;
                                        } else {
                                            return coroutine_suspended;
                                        }
                                    case 1:
                                        ResultKt.throwOnFailure($result);
                                        break;
                                    default:
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                throw new KotlinNothingValueException();
                            }
                        }
                    }), WebSocketProtocol.PAYLOAD_SHORT, null);
                }
            }
        }, $composer2, 8, 508);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup2 = $composer2.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    NavigationKt.AppNavigation(UserPreferences.this, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UserProfile AppNavigation$lambda$15(State<UserProfile> state) {
        Object thisObj$iv = state.getValue();
        return (UserProfile) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OnboardingDraft AppNavigation$lambda$16(State<OnboardingDraft> state) {
        Object thisObj$iv = state.getValue();
        return (OnboardingDraft) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OnboardingExtractionState AppNavigation$lambda$17(State<? extends OnboardingExtractionState> state) {
        Object thisObj$iv = state.getValue();
        return (OnboardingExtractionState) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final RestorationState AppNavigation$lambda$18(State<? extends RestorationState> state) {
        Object thisObj$iv = state.getValue();
        return (RestorationState) thisObj$iv;
    }
}
