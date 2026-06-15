package com.vantedge.app.data.network;

import com.vantedge.app.BuildConfig;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/* compiled from: GeminiService.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J4\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00150\u0014H\u0086@¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J:\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0082@¢\u0006\u0002\u0010\u001cJ&\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0082@¢\u0006\u0002\u0010\u001fJ&\u0010 \u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0082@¢\u0006\u0002\u0010\u001fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/vantedge/app/data/network/GeminiService;", "", "()V", "apiKey", "", "baseUrl", "client", "Lokhttp3/OkHttpClient;", "mediaType", "Lokhttp3/MediaType;", "models", "", "delayForAttempt", "", "index", "", "generate", "systemInstruction", "userPrompt", "onProgress", "Lkotlin/Function1;", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "statusForAttempt", "tryChain", "Lcom/vantedge/app/data/network/GeminiService$ModelResult;", "isRetry", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryModelOnce", "model", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryModelWithRetry", "ModelResult", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class GeminiService {
    public static final int $stable = 8;
    private final OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();
    private final MediaType mediaType = MediaType.INSTANCE.get("application/json");
    private final String apiKey = BuildConfig.OPENROUTER_API_KEY;
    private final String baseUrl = "https://openrouter.ai/api/v1/chat/completions";
    private final List<String> models = CollectionsKt.listOf((Object[]) new String[]{"openrouter/free", "nvidia/nemotron-3-nano-30b-a3b:free", "meta-llama/llama-3.3-70b-instruct:free"});

    private final long delayForAttempt(int index) {
        if (index < 1) {
            return 2000L;
        }
        return index < 2 ? 4000L : 8000L;
    }

    private final String statusForAttempt(int index) {
        switch (index) {
            case 0:
                return "Analyzing your document (Attempt 1 of 3)...";
            case 1:
                return "Still working — trying alternative method (Attempt 2 of 3)...";
            default:
                return "Final attempt in progress (Attempt 3 of 3)...";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GeminiService.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lcom/vantedge/app/data/network/GeminiService$ModelResult;", "", "()V", "Failure", "FailureType", "Success", "Lcom/vantedge/app/data/network/GeminiService$ModelResult$Failure;", "Lcom/vantedge/app/data/network/GeminiService$ModelResult$Success;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class ModelResult {

        /* compiled from: GeminiService.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/vantedge/app/data/network/GeminiService$ModelResult$FailureType;", "", "(Ljava/lang/String;I)V", "DNS", "TIMEOUT", "RATE_LIMIT", "SERVER_ERROR", "NETWORK", "PARSE", "UNKNOWN", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum FailureType {
            DNS,
            TIMEOUT,
            RATE_LIMIT,
            SERVER_ERROR,
            NETWORK,
            PARSE,
            UNKNOWN;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

            public static EnumEntries<FailureType> getEntries() {
                return $ENTRIES;
            }
        }

        public /* synthetic */ ModelResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* compiled from: GeminiService.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/network/GeminiService$ModelResult$Success;", "Lcom/vantedge/app/data/network/GeminiService$ModelResult;", "content", "", "(Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Success extends ModelResult {
            public static final int $stable = 0;
            private final String content;

            public static /* synthetic */ Success copy$default(Success success, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = success.content;
                }
                return success.copy(str);
            }

            /* renamed from: component1, reason: from getter */
            public final String getContent() {
                return this.content;
            }

            public final Success copy(String content) {
                Intrinsics.checkNotNullParameter(content, "content");
                return new Success(content);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Success) && Intrinsics.areEqual(this.content, ((Success) other).content);
            }

            public int hashCode() {
                return this.content.hashCode();
            }

            public String toString() {
                return "Success(content=" + this.content + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Success(String content) {
                super(null);
                Intrinsics.checkNotNullParameter(content, "content");
                this.content = content;
            }

            public final String getContent() {
                return this.content;
            }
        }

        private ModelResult() {
        }

        /* compiled from: GeminiService.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/vantedge/app/data/network/GeminiService$ModelResult$Failure;", "Lcom/vantedge/app/data/network/GeminiService$ModelResult;", "type", "Lcom/vantedge/app/data/network/GeminiService$ModelResult$FailureType;", "message", "", "(Lcom/vantedge/app/data/network/GeminiService$ModelResult$FailureType;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getType", "()Lcom/vantedge/app/data/network/GeminiService$ModelResult$FailureType;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Failure extends ModelResult {
            public static final int $stable = 0;
            private final String message;
            private final FailureType type;

            public static /* synthetic */ Failure copy$default(Failure failure, FailureType failureType, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    failureType = failure.type;
                }
                if ((i & 2) != 0) {
                    str = failure.message;
                }
                return failure.copy(failureType, str);
            }

            /* renamed from: component1, reason: from getter */
            public final FailureType getType() {
                return this.type;
            }

            /* renamed from: component2, reason: from getter */
            public final String getMessage() {
                return this.message;
            }

            public final Failure copy(FailureType type, String message) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(message, "message");
                return new Failure(type, message);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Failure)) {
                    return false;
                }
                Failure failure = (Failure) other;
                return this.type == failure.type && Intrinsics.areEqual(this.message, failure.message);
            }

            public int hashCode() {
                return (this.type.hashCode() * 31) + this.message.hashCode();
            }

            public String toString() {
                return "Failure(type=" + this.type + ", message=" + this.message + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Failure(FailureType type, String message) {
                super(null);
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(message, "message");
                this.type = type;
                this.message = message;
            }

            public final String getMessage() {
                return this.message;
            }

            public final FailureType getType() {
                return this.type;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object generate(java.lang.String r10, java.lang.String r11, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r12, kotlin.coroutines.Continuation<? super java.lang.String> r13) {
        /*
            r9 = this;
            boolean r0 = r13 instanceof com.vantedge.app.data.network.GeminiService$generate$1
            if (r0 == 0) goto L14
            r0 = r13
            com.vantedge.app.data.network.GeminiService$generate$1 r0 = (com.vantedge.app.data.network.GeminiService$generate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L19
        L14:
            com.vantedge.app.data.network.GeminiService$generate$1 r0 = new com.vantedge.app.data.network.GeminiService$generate$1
            r0.<init>(r9, r13)
        L19:
            r13 = r0
            java.lang.Object r6 = r13.result
            java.lang.Object r7 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r0 = r13.label
            java.lang.String r8 = "GeminiService"
            switch(r0) {
                case 0: goto L34;
                case 1: goto L2f;
                default: goto L27;
            }
        L27:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r10 = r6
            goto L61
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            r0 = r9
            r2 = r11
            r1 = r10
            r3 = r12
            int r10 = r2.length()
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "ENTRY generate promptLength="
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r10 = r11.append(r10)
            java.lang.String r10 = r10.toString()
            android.util.Log.d(r8, r10)
            r4 = 0
            r10 = 1
            r13.label = r10
            r5 = r13
            java.lang.Object r10 = r0.tryChain(r1, r2, r3, r4, r5)
            if (r10 != r7) goto L61
            return r7
        L61:
            com.vantedge.app.data.network.GeminiService$ModelResult r10 = (com.vantedge.app.data.network.GeminiService.ModelResult) r10
            boolean r11 = r10 instanceof com.vantedge.app.data.network.GeminiService.ModelResult.Success
            if (r11 == 0) goto L75
            java.lang.String r11 = "EXIT generate SUCCESS"
            android.util.Log.d(r8, r11)
            r11 = r10
            com.vantedge.app.data.network.GeminiService$ModelResult$Success r11 = (com.vantedge.app.data.network.GeminiService.ModelResult.Success) r11
            java.lang.String r11 = r11.getContent()
            goto La8
        L75:
            boolean r11 = r10 instanceof com.vantedge.app.data.network.GeminiService.ModelResult.Failure
            if (r11 == 0) goto La9
            r11 = r10
            com.vantedge.app.data.network.GeminiService$ModelResult$Failure r11 = (com.vantedge.app.data.network.GeminiService.ModelResult.Failure) r11
            com.vantedge.app.data.network.GeminiService$ModelResult$FailureType r11 = r11.getType()
            r12 = r10
            com.vantedge.app.data.network.GeminiService$ModelResult$Failure r12 = (com.vantedge.app.data.network.GeminiService.ModelResult.Failure) r12
            java.lang.String r12 = r12.getMessage()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "EXIT generate FAILURE — "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r11 = r0.append(r11)
            java.lang.String r0 = ": "
            java.lang.StringBuilder r11 = r11.append(r0)
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            android.util.Log.e(r8, r11)
            r11 = 0
        La8:
            return r11
        La9:
            kotlin.NoWhenBranchMatchedException r10 = new kotlin.NoWhenBranchMatchedException
            r10.<init>()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.network.GeminiService.generate(java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
    /* JADX WARN: Type inference failed for: r1v4, types: [T, com.vantedge.app.data.network.GeminiService$ModelResult, java.lang.Object] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x01c4 -> B:12:0x01cf). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x01d9 -> B:13:0x00ab). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object tryChain(java.lang.String r19, java.lang.String r20, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r21, boolean r22, kotlin.coroutines.Continuation<? super com.vantedge.app.data.network.GeminiService.ModelResult> r23) {
        /*
            Method dump skipped, instructions count: 556
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.network.GeminiService.tryChain(java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00e8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object tryModelWithRetry(java.lang.String r9, java.lang.String r10, java.lang.String r11, kotlin.coroutines.Continuation<? super com.vantedge.app.data.network.GeminiService.ModelResult> r12) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.network.GeminiService.tryModelWithRetry(java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object tryModelOnce(java.lang.String r18, java.lang.String r19, java.lang.String r20, kotlin.coroutines.Continuation<? super com.vantedge.app.data.network.GeminiService.ModelResult> r21) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.network.GeminiService.tryModelOnce(java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
