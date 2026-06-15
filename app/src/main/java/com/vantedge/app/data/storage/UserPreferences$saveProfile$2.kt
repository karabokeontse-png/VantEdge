package com.vantedge.app.data.storage;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import com.vantedge.app.data.model.UserProfile;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: UserPreferences.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "prefs", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.storage.UserPreferences$saveProfile$2", f = "UserPreferences.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes5.dex */
final class UserPreferences$saveProfile$2 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
    final /* synthetic */ UserProfile $profile;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserPreferences this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserPreferences$saveProfile$2(UserProfile userProfile, UserPreferences userPreferences, Continuation<? super UserPreferences$saveProfile$2> continuation) {
        super(2, continuation);
        this.$profile = userProfile;
        this.this$0 = userPreferences;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        UserPreferences$saveProfile$2 userPreferences$saveProfile$2 = new UserPreferences$saveProfile$2(this.$profile, this.this$0, continuation);
        userPreferences$saveProfile$2.L$0 = obj;
        return userPreferences$saveProfile$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(MutablePreferences mutablePreferences, Continuation<? super Unit> continuation) {
        return ((UserPreferences$saveProfile$2) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String serializeCertifications;
        String serializeWorkHistory;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                MutablePreferences prefs = (MutablePreferences) this.L$0;
                prefs.set(UserPreferences.INSTANCE.getNAME(), this.$profile.getName());
                prefs.set(UserPreferences.INSTANCE.getEMAIL(), this.$profile.getEmail());
                prefs.set(UserPreferences.INSTANCE.getPHONE(), this.$profile.getPhone());
                prefs.set(UserPreferences.INSTANCE.getLOCATION(), this.$profile.getLocation());
                prefs.set(UserPreferences.INSTANCE.getLINKEDIN(), this.$profile.getLinkedIn());
                prefs.set(UserPreferences.INSTANCE.getSUMMARY(), this.$profile.getSummary());
                prefs.set(UserPreferences.INSTANCE.getSKILLS(), CollectionsKt.joinToString$default(this.$profile.getSkills(), ",", null, null, 0, null, null, 62, null));
                prefs.set(UserPreferences.INSTANCE.getEDUCATION(), CollectionsKt.joinToString$default(this.$profile.getEducation(), ",", null, null, 0, null, null, 62, null));
                prefs.set(UserPreferences.INSTANCE.getLANGUAGES(), CollectionsKt.joinToString$default(this.$profile.getLanguages(), ",", null, null, 0, null, null, 62, null));
                Preferences.Key<String> certifications = UserPreferences.INSTANCE.getCERTIFICATIONS();
                serializeCertifications = this.this$0.serializeCertifications(this.$profile.getCertifications());
                prefs.set(certifications, serializeCertifications);
                Preferences.Key<String> work_history = UserPreferences.INSTANCE.getWORK_HISTORY();
                serializeWorkHistory = this.this$0.serializeWorkHistory(this.$profile.getWorkHistory());
                prefs.set(work_history, serializeWorkHistory);
                prefs.set(UserPreferences.INSTANCE.getONBOARDING_COMPLETE(), Boxing.boxBoolean(true));
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
