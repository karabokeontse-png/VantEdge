package com.vantedge.app.navigation;

import androidx.compose.runtime.State;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.PopUpToBuilder;
import com.vantedge.app.data.model.OnboardingDraft;
import com.vantedge.app.data.model.OnboardingStage;
import com.vantedge.app.data.viewmodel.OnboardingViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: Navigation.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.navigation.NavigationKt$AppNavigation$4", f = "Navigation.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class NavigationKt$AppNavigation$4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ State<OnboardingDraft> $draft$delegate;
    final /* synthetic */ NavHostController $navController;
    final /* synthetic */ OnboardingViewModel $onboardingViewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NavigationKt$AppNavigation$4(OnboardingViewModel onboardingViewModel, NavHostController navHostController, State<OnboardingDraft> state, Continuation<? super NavigationKt$AppNavigation$4> continuation) {
        super(2, continuation);
        this.$onboardingViewModel = onboardingViewModel;
        this.$navController = navHostController;
        this.$draft$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NavigationKt$AppNavigation$4(this.$onboardingViewModel, this.$navController, this.$draft$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NavigationKt$AppNavigation$4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        OnboardingDraft AppNavigation$lambda$16;
        OnboardingDraft AppNavigation$lambda$162;
        boolean canEnter;
        OnboardingDraft AppNavigation$lambda$163;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                AppNavigation$lambda$16 = NavigationKt.AppNavigation$lambda$16(this.$draft$delegate);
                OnboardingStage stage = AppNavigation$lambda$16.getStage();
                AppNavigation$lambda$162 = NavigationKt.AppNavigation$lambda$16(this.$draft$delegate);
                canEnter = NavigationKt.canEnter(stage, AppNavigation$lambda$162);
                if (canEnter) {
                    AppNavigation$lambda$163 = NavigationKt.AppNavigation$lambda$16(this.$draft$delegate);
                    OnboardingStage stage2 = AppNavigation$lambda$163.getStage();
                    if (!Intrinsics.areEqual(stage2, OnboardingStage.Welcome.INSTANCE)) {
                        if (Intrinsics.areEqual(stage2, OnboardingStage.ChoosePath.INSTANCE)) {
                            this.$navController.navigate("onboarding_choose_path", new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$4.1
                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                                    invoke2(navOptionsBuilder);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(NavOptionsBuilder navigate) {
                                    Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                                    navigate.setLaunchSingleTop(true);
                                    navigate.popUpTo("onboarding_extracting", new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.4.1.1
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
                                }
                            });
                        } else if (!Intrinsics.areEqual(stage2, OnboardingStage.UploadingCv.INSTANCE)) {
                            if (Intrinsics.areEqual(stage2, OnboardingStage.Extracting.INSTANCE)) {
                                this.$navController.navigate("onboarding_extracting", new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$4.2
                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                                        invoke2(navOptionsBuilder);
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2(NavOptionsBuilder navigate) {
                                        Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                                        navigate.setLaunchSingleTop(true);
                                        navigate.popUpTo("onboarding_extracting", new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.4.2.1
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
                                    }
                                });
                            } else if (Intrinsics.areEqual(stage2, OnboardingStage.ReviewingExtraction.INSTANCE)) {
                                NavigationKt.navigateOnboarding(this.$navController, "onboarding_review");
                            } else if (Intrinsics.areEqual(stage2, OnboardingStage.EditingProfile.INSTANCE)) {
                                NavigationKt.navigateOnboarding(this.$navController, "onboarding_edit_profile");
                            } else if (Intrinsics.areEqual(stage2, OnboardingStage.FinalReview.INSTANCE)) {
                                NavigationKt.navigateOnboarding(this.$navController, "onboarding_final_review");
                            } else {
                                Intrinsics.areEqual(stage2, OnboardingStage.Completed.INSTANCE);
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
                this.$onboardingViewModel.resetToChoosePath();
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
