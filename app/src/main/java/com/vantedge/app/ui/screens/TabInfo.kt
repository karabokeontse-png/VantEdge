package com.vantedge.app.ui.screens;

import com.vantedge.app.data.model.CompatibilityRecord;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.i18n.MessageBundle;

/* compiled from: ResultScreen.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/ui/screens/TabInfo;", "", MessageBundle.TITLE_ENTRY, "", "(Ljava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "Ana", "Doc", "Lcom/vantedge/app/ui/screens/TabInfo$Ana;", "Lcom/vantedge/app/ui/screens/TabInfo$Doc;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
abstract class TabInfo {
    private final String title;

    public /* synthetic */ TabInfo(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    /* compiled from: ResultScreen.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/ui/screens/TabInfo$Doc;", "Lcom/vantedge/app/ui/screens/TabInfo;", MessageBundle.TITLE_ENTRY, "", "content", "isCorrupted", "", "(Ljava/lang/String;Ljava/lang/String;Z)V", "getContent", "()Ljava/lang/String;", "()Z", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Doc extends TabInfo {
        public static final int $stable = 0;
        private final String content;
        private final boolean isCorrupted;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Doc(String title, String content, boolean isCorrupted) {
            super(title, null);
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(content, "content");
            this.content = content;
            this.isCorrupted = isCorrupted;
        }

        public final String getContent() {
            return this.content;
        }

        /* renamed from: isCorrupted, reason: from getter */
        public final boolean getIsCorrupted() {
            return this.isCorrupted;
        }
    }

    private TabInfo(String title) {
        this.title = title;
    }

    public final String getTitle() {
        return this.title;
    }

    /* compiled from: ResultScreen.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/vantedge/app/ui/screens/TabInfo$Ana;", "Lcom/vantedge/app/ui/screens/TabInfo;", "compat", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "(Lcom/vantedge/app/data/model/CompatibilityRecord;)V", "getCompat", "()Lcom/vantedge/app/data/model/CompatibilityRecord;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Ana extends TabInfo {
        public static final int $stable = 8;
        private final CompatibilityRecord compat;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Ana(CompatibilityRecord compat) {
            super("Analysis", null);
            Intrinsics.checkNotNullParameter(compat, "compat");
            this.compat = compat;
        }

        public final CompatibilityRecord getCompat() {
            return this.compat;
        }
    }
}
