package com.vantedge.app.data.storage;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes5.dex */
public final class OnboardingDraftDao_Impl implements OnboardingDraftDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<OnboardingDraftEntity> __insertionAdapterOfOnboardingDraftEntity;
    private final SharedSQLiteStatement __preparedStmtOfClearAll;
    private final SharedSQLiteStatement __preparedStmtOfDeleteDraft;

    public OnboardingDraftDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfOnboardingDraftEntity = new EntityInsertionAdapter<OnboardingDraftEntity>(__db) { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT OR REPLACE INTO `onboarding_draft` (`id`,`draftJson`) VALUES (?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final OnboardingDraftEntity entity) {
                if (entity.getId() == null) {
                    statement.bindNull(1);
                } else {
                    statement.bindString(1, entity.getId());
                }
                if (entity.getDraftJson() == null) {
                    statement.bindNull(2);
                } else {
                    statement.bindString(2, entity.getDraftJson());
                }
            }
        };
        this.__preparedStmtOfDeleteDraft = new SharedSQLiteStatement(__db) { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM onboarding_draft WHERE id = ?";
            }
        };
        this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM onboarding_draft";
            }
        };
    }

    @Override // com.vantedge.app.data.storage.OnboardingDraftDao
    public Object upsert(final OnboardingDraftEntity entity, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                OnboardingDraftDao_Impl.this.__db.beginTransaction();
                try {
                    OnboardingDraftDao_Impl.this.__insertionAdapterOfOnboardingDraftEntity.insert((EntityInsertionAdapter) entity);
                    OnboardingDraftDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    OnboardingDraftDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.OnboardingDraftDao
    public Object deleteDraft(final String id, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = OnboardingDraftDao_Impl.this.__preparedStmtOfDeleteDraft.acquire();
                if (id != null) {
                    _stmt.bindString(1, id);
                } else {
                    _stmt.bindNull(1);
                }
                try {
                    OnboardingDraftDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        OnboardingDraftDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        OnboardingDraftDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    OnboardingDraftDao_Impl.this.__preparedStmtOfDeleteDraft.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.OnboardingDraftDao
    public Object clearAll(final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = OnboardingDraftDao_Impl.this.__preparedStmtOfClearAll.acquire();
                try {
                    OnboardingDraftDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        OnboardingDraftDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        OnboardingDraftDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    OnboardingDraftDao_Impl.this.__preparedStmtOfClearAll.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.OnboardingDraftDao
    public Object getDraft(final String id, final Continuation<? super OnboardingDraftEntity> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM onboarding_draft WHERE id = ?", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<OnboardingDraftEntity>() { // from class: com.vantedge.app.data.storage.OnboardingDraftDao_Impl.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public OnboardingDraftEntity call() throws Exception {
                OnboardingDraftEntity _result;
                String _tmpId;
                String _tmpDraftJson;
                Cursor _cursor = DBUtil.query(OnboardingDraftDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfDraftJson = CursorUtil.getColumnIndexOrThrow(_cursor, "draftJson");
                    if (_cursor.moveToFirst()) {
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _tmpId = null;
                        } else {
                            _tmpId = _cursor.getString(_cursorIndexOfId);
                        }
                        if (_cursor.isNull(_cursorIndexOfDraftJson)) {
                            _tmpDraftJson = null;
                        } else {
                            _tmpDraftJson = _cursor.getString(_cursorIndexOfDraftJson);
                        }
                        _result = new OnboardingDraftEntity(_tmpId, _tmpDraftJson);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _cursor.close();
                    _statement.release();
                }
            }
        }, $completion);
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
