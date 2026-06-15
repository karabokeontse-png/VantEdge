package com.vantedge.app.navigation;

import androidx.compose.runtime.State;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.PopUpToBuilder;
import com.vantedge.app.data.viewmodel.RestorationState;
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
@DebugMetadata(c = "com.vantedge.app.navigation.NavigationKt$AppNavigation$2", f = "Navigation.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class NavigationKt$AppNavigation$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ NavHostController $navController;
    final /* synthetic */ State<RestorationState> $restorationState$delegate;
    final /* synthetic */ String $startDestination;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    NavigationKt$AppNavigation$2(String str, NavHostController navHostController, State<? extends RestorationState> state, Continuation<? super NavigationKt$AppNavigation$2> continuation) {
        super(2, continuation);
        this.$startDestination = str;
        this.$navController = navHostController;
        this.$restorationState$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NavigationKt$AppNavigation$2(this.$startDestination, this.$navController, this.$restorationState$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NavigationKt$AppNavigation$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        RestorationState AppNavigation$lambda$18;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                AppNavigation$lambda$18 = NavigationKt.AppNavigation$lambda$18(this.$restorationState$delegate);
                if ((AppNavigation$lambda$18 instanceof RestorationState.Restored) && Intrinsics.areEqual(this.$startDestination, "dashboard")) {
                    NavDestination currentDestination = this.$navController.getCurrentDestination();
                    if (!Intrinsics.areEqual(currentDestination != null ? currentDestination.getRoute() : null, "result")) {
                        this.$navController.navigate("result", new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt$AppNavigation$2.1
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                                invoke2(navOptionsBuilder);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(NavOptionsBuilder navigate) {
                                Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                                navigate.popUpTo("dashboard", new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.navigation.NavigationKt.AppNavigation.2.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public /* bridge */ /* synthetic */ Unit invoke(PopUpToBuilder popUpToBuilder) {
                                        invoke2(popUpToBuilder);
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2(PopUpToBuilder popUpTo) {
                                        Intrinsics.checkNotNullParameter(popUpTo, "$this$popUpTo");
                                        popUpTo.setInclusive(false);
                                    }
                                });
                                navigate.setLaunchSingleTop(true);
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
