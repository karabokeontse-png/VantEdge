package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GenerationCycle.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/vantedge/app/data/model/DesignConfig;", "", "templateId", "", "colorScheme", "(Ljava/lang/String;Ljava/lang/String;)V", "getColorScheme", "()Ljava/lang/String;", "getTemplateId", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class DesignConfig {
    public static final int $stable = 0;
    private final String colorScheme;
    private final String templateId;

    public static /* synthetic */ DesignConfig copy$default(DesignConfig designConfig, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = designConfig.templateId;
        }
        if ((i & 2) != 0) {
            str2 = designConfig.colorScheme;
        }
        return designConfig.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getTemplateId() {
        return this.templateId;
    }

    /* renamed from: component2, reason: from getter */
    public final String getColorScheme() {
        return this.colorScheme;
    }

    public final DesignConfig copy(String templateId, String colorScheme) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        return new DesignConfig(templateId, colorScheme);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DesignConfig)) {
            return false;
        }
        DesignConfig designConfig = (DesignConfig) other;
        return Intrinsics.areEqual(this.templateId, designConfig.templateId) && Intrinsics.areEqual(this.colorScheme, designConfig.colorScheme);
    }

    public int hashCode() {
        return (this.templateId.hashCode() * 31) + this.colorScheme.hashCode();
    }

    public String toString() {
        return "DesignConfig(templateId=" + this.templateId + ", colorScheme=" + this.colorScheme + ")";
    }

    public DesignConfig(String templateId, String colorScheme) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        this.templateId = templateId;
        this.colorScheme = colorScheme;
    }

    public final String getTemplateId() {
        return this.templateId;
    }

    public final String getColorScheme() {
        return this.colorScheme;
    }
}
