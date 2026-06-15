package com.vantedge.app.navigation;

import androidx.core.view.InputDeviceCompat;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.PopUpToBuilder;
import com.vantedge.app.data.viewmodel.OnboardingViewModel;
import kotlin.KotlinNothingValueException;
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
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;

/* compiled from: Navigation.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.navigation.NavigationKt$AppNavigation$5", f = "Navigation.kt", i = {}, l = {InputDeviceCompat.SOURCE_KEYBOARD}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class NavigationKt$AppNavigation$5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ NavHostController $navController;
    final /* synthetic */ OnboardingViewModel $onboardingViewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NavigationKt$AppNavigation$5(OnboardingViewModel onboardingViewModel, NavHostController navHostController, Continuation<? super NavigationKt$AppNavigation$5> continuation) {
        super(2, continuation);
        this.$onboardingViewModel = onboardingViewModel;
        this.$navController = navHostController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NavigationKt$AppNavigation$5(this.$onboardingViewModel, this.$navController, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NavigationKt$AppNavigation$5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                SharedFlow<Unit> commitComplete = this.$onboardingViewModel.getCommitComplete();
                final NavHostController navHostController = this.$navController;
                this.label = 1;
                if (commitComplete.collect(new FlowCollector() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$5.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                        return emit((Unit) value, (Continuation<? super Unit>) $completion);
                    }

                    public final Object emit(Unit it, Continuation<? super Unit> continuation) {
                        NavHostController.this.navigate("dashboard", new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.5.1.1
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                                invoke2(navOptionsBuilder);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(NavOptionsBuilder navigate) {
                                Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                                navigate.popUpTo(0, new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.5.1.1.1
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
