package com.vantedge.app.data.storage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.DesignConfig;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleStateSerializer.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/storage/CycleStateDeserializer;", "Lcom/google/gson/JsonDeserializer;", "Lcom/vantedge/app/data/model/CycleState;", "()V", "deserialize", "json", "Lcom/google/gson/JsonElement;", "typeOfT", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonDeserializationContext;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CycleStateDeserializer implements JsonDeserializer<CycleState> {
    public static final int $stable = 0;

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.gson.JsonDeserializer
    public CycleState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        Type type;
        List keywords;
        Type type2;
        Type type3;
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(typeOfT, "typeOfT");
        Intrinsics.checkNotNullParameter(context, "context");
        JsonObject obj = json.getAsJsonObject();
        JsonElement jsonElement = obj.get("type");
        String type4 = jsonElement != null ? jsonElement.getAsString() : null;
        if (type4 == null) {
            throw new JsonParseException("Missing type field in CycleState JSON");
        }
        switch (type4.hashCode()) {
            case -1584116536:
                if (type4.equals("AnalysisOnly")) {
                    JsonElement jsonElement2 = obj.get("compatibility");
                    type = CycleStateSerializerKt.compatibilityType;
                    Object deserialize = context.deserialize(jsonElement2, type);
                    Intrinsics.checkNotNullExpressionValue(deserialize, "deserialize(...)");
                    return new CycleState.AnalysisOnly((CompatibilityRecord) deserialize);
                }
                break;
            case -436017333:
                if (type4.equals("GenerationReady")) {
                    JsonElement keywordsElement = obj.get("matchedKeywords");
                    if (keywordsElement == null || !keywordsElement.isJsonArray()) {
                        keywords = CollectionsKt.emptyList();
                    } else {
                        Iterable asJsonArray = keywordsElement.getAsJsonArray();
                        Intrinsics.checkNotNullExpressionValue(asJsonArray, "getAsJsonArray(...)");
                        Iterable $this$mapNotNull$iv = asJsonArray;
                        Collection destination$iv$iv = new ArrayList();
                        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                            JsonElement element = (JsonElement) element$iv$iv$iv;
                            String asString = (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) ? element.getAsString() : null;
                            if (asString != null) {
                                destination$iv$iv.add(asString);
                            }
                        }
                        keywords = (List) destination$iv$iv;
                    }
                    JsonElement jsonElement3 = obj.get("compatibility");
                    type2 = CycleStateSerializerKt.compatibilityType;
                    Object deserialize2 = context.deserialize(jsonElement3, type2);
                    Intrinsics.checkNotNullExpressionValue(deserialize2, "deserialize(...)");
                    CompatibilityRecord compatibilityRecord = (CompatibilityRecord) deserialize2;
                    JsonElement jsonElement4 = obj.get("coverLetterBody");
                    String asString2 = jsonElement4 != null ? jsonElement4.getAsString() : null;
                    return new CycleState.GenerationReady(compatibilityRecord, keywords, asString2 != null ? asString2 : "");
                }
                break;
            case 301262711:
                if (type4.equals("FullCycle")) {
                    JsonElement jsonElement5 = obj.get("compatibility");
                    type3 = CycleStateSerializerKt.compatibilityType;
                    Object deserialize3 = context.deserialize(jsonElement5, type3);
                    Intrinsics.checkNotNullExpressionValue(deserialize3, "deserialize(...)");
                    CompatibilityRecord compatibilityRecord2 = (CompatibilityRecord) deserialize3;
                    JsonElement jsonElement6 = obj.get("cvContent");
                    String asString3 = jsonElement6 != null ? jsonElement6.getAsString() : null;
                    if (asString3 == null) {
                        asString3 = "";
                    }
                    JsonElement jsonElement7 = obj.get("coverLetterContent");
                    String asString4 = jsonElement7 != null ? jsonElement7.getAsString() : null;
                    String str = asString4 != null ? asString4 : "";
                    Object deserialize4 = context.deserialize(obj.get("design"), DesignConfig.class);
                    Intrinsics.checkNotNullExpressionValue(deserialize4, "deserialize(...)");
                    return new CycleState.FullCycle(compatibilityRecord2, asString3, str, (DesignConfig) deserialize4);
                }
                break;
        }
        throw new JsonParseException("Unknown CycleState type: " + type4);
    }
}
