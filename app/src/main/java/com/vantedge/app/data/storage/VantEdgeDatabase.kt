package com.vantedge.app.data.storage;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VantEdgeDatabase.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&¨\u0006\b"}, d2 = {"Lcom/vantedge/app/data/storage/VantEdgeDatabase;", "Landroidx/room/RoomDatabase;", "()V", "cycleDao", "Lcom/vantedge/app/data/storage/CycleDao;", "onboardingDraftDao", "Lcom/vantedge/app/data/storage/OnboardingDraftDao;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public abstract class VantEdgeDatabase extends RoomDatabase {
    public static final int $stable = 0;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static volatile VantEdgeDatabase INSTANCE;

    public abstract CycleDao cycleDao();

    public abstract OnboardingDraftDao onboardingDraftDao();

    /* compiled from: VantEdgeDatabase.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/vantedge/app/data/storage/VantEdgeDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/vantedge/app/data/storage/VantEdgeDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final VantEdgeDatabase getInstance(Context context) {
            VantEdgeDatabase instance;
            Intrinsics.checkNotNullParameter(context, "context");
            VantEdgeDatabase vantEdgeDatabase = VantEdgeDatabase.INSTANCE;
            if (vantEdgeDatabase != null) {
                return vantEdgeDatabase;
            }
            synchronized (this) {
                Context applicationContext = context.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                instance = (VantEdgeDatabase) Room.databaseBuilder(applicationContext, VantEdgeDatabase.class, "vantedge_database").fallbackToDestructiveMigration().build();
                Companion companion = VantEdgeDatabase.INSTANCE;
                VantEdgeDatabase.INSTANCE = instance;
            }
            return instance;
        }
    }
}
