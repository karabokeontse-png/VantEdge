package com.vantedge.app.data.storage;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vantedge.app.data.storage.CycleDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes5.dex */
public final class CycleDao_Impl implements CycleDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<CycleEntity> __insertionAdapterOfCycleEntity;
    private final SharedSQLiteStatement __preparedStmtOfClearActiveFlag;
    private final SharedSQLiteStatement __preparedStmtOfDeleteCycleById;
    private final SharedSQLiteStatement __preparedStmtOfMarkCycleActive;

    public CycleDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfCycleEntity = new EntityInsertionAdapter<CycleEntity>(__db) { // from class: com.vantedge.app.data.storage.CycleDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT OR REPLACE INTO `cycles` (`id`,`jobTitle`,`company`,`createdAt`,`isCommitted`,`isVisibleInHistory`,`isActive`,`version`,`cycleJson`) VALUES (?,?,?,?,?,?,?,?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, CycleEntity cycleEntity) {
                if (cycleEntity.getId() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, cycleEntity.getId());
                }
                if (cycleEntity.getJobTitle() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, cycleEntity.getJobTitle());
                }
                if (cycleEntity.getCompany() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, cycleEntity.getCompany());
                }
                supportSQLiteStatement.bindLong(4, cycleEntity.getCreatedAt());
                supportSQLiteStatement.bindLong(5, cycleEntity.isCommitted() ? 1L : 0L);
                supportSQLiteStatement.bindLong(6, cycleEntity.isVisibleInHistory() ? 1L : 0L);
                supportSQLiteStatement.bindLong(7, cycleEntity.isActive() ? 1L : 0L);
                if (cycleEntity.getVersion() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindLong(8, cycleEntity.getVersion().intValue());
                }
                if (cycleEntity.getCycleJson() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, cycleEntity.getCycleJson());
                }
            }
        };
        this.__preparedStmtOfDeleteCycleById = new SharedSQLiteStatement(__db) { // from class: com.vantedge.app.data.storage.CycleDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM cycles WHERE id = ?";
            }
        };
        this.__preparedStmtOfClearActiveFlag = new SharedSQLiteStatement(__db) { // from class: com.vantedge.app.data.storage.CycleDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE cycles SET isActive = 0 WHERE isActive = 1";
            }
        };
        this.__preparedStmtOfMarkCycleActive = new SharedSQLiteStatement(__db) { // from class: com.vantedge.app.data.storage.CycleDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE cycles SET isActive = 1 WHERE id = ?";
            }
        };
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object insertCycle(final CycleEntity cycle, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                CycleDao_Impl.this.__db.beginTransaction();
                try {
                    CycleDao_Impl.this.__insertionAdapterOfCycleEntity.insert((EntityInsertionAdapter) cycle);
                    CycleDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    CycleDao_Impl.this.__db.endTransaction();
                }
            }
        }, $completion);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$setActiveCycle$0(String cycleId, Continuation __cont) {
        return CycleDao.DefaultImpls.setActiveCycle(this, cycleId, __cont);
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object setActiveCycle(final String cycleId, final Continuation<? super Unit> $completion) {
        return RoomDatabaseKt.withTransaction(this.__db, new Function1() { // from class: com.vantedge.app.data.storage.CycleDao_Impl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Object lambda$setActiveCycle$0;
                lambda$setActiveCycle$0 = CycleDao_Impl.this.lambda$setActiveCycle$0(cycleId, (Continuation) obj);
                return lambda$setActiveCycle$0;
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object deleteCycleById(final String id, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = CycleDao_Impl.this.__preparedStmtOfDeleteCycleById.acquire();
                if (id != null) {
                    _stmt.bindString(1, id);
                } else {
                    _stmt.bindNull(1);
                }
                try {
                    CycleDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        CycleDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        CycleDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    CycleDao_Impl.this.__preparedStmtOfDeleteCycleById.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object clearActiveFlag(final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.7
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = CycleDao_Impl.this.__preparedStmtOfClearActiveFlag.acquire();
                try {
                    CycleDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        CycleDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        CycleDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    CycleDao_Impl.this.__preparedStmtOfClearActiveFlag.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object markCycleActive(final String cycleId, final Continuation<? super Unit> $completion) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.8
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement _stmt = CycleDao_Impl.this.__preparedStmtOfMarkCycleActive.acquire();
                if (cycleId != null) {
                    _stmt.bindString(1, cycleId);
                } else {
                    _stmt.bindNull(1);
                }
                try {
                    CycleDao_Impl.this.__db.beginTransaction();
                    try {
                        _stmt.executeUpdateDelete();
                        CycleDao_Impl.this.__db.setTransactionSuccessful();
                        return Unit.INSTANCE;
                    } finally {
                        CycleDao_Impl.this.__db.endTransaction();
                    }
                } finally {
                    CycleDao_Impl.this.__preparedStmtOfMarkCycleActive.release(_stmt);
                }
            }
        }, $completion);
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Flow<List<CycleEntity>> getAllCycles() {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM cycles ORDER BY createdAt DESC", 0);
        return CoroutinesRoom.createFlow(this.__db, false, new String[]{"cycles"}, new Callable<List<CycleEntity>>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.9
            @Override // java.util.concurrent.Callable
            public List<CycleEntity> call() throws Exception {
                String _tmpId;
                String _tmpJobTitle;
                String _tmpCompany;
                Integer _tmpVersion;
                String _tmpCycleJson;
                Cursor _cursor = DBUtil.query(CycleDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfJobTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "jobTitle");
                    int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
                    int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
                    int _cursorIndexOfIsCommitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCommitted");
                    int _cursorIndexOfIsVisibleInHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisibleInHistory");
                    int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
                    int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
                    int _cursorIndexOfCycleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "cycleJson");
                    List<CycleEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _tmpId = null;
                        } else {
                            _tmpId = _cursor.getString(_cursorIndexOfId);
                        }
                        if (_cursor.isNull(_cursorIndexOfJobTitle)) {
                            _tmpJobTitle = null;
                        } else {
                            String _tmpJobTitle2 = _cursor.getString(_cursorIndexOfJobTitle);
                            _tmpJobTitle = _tmpJobTitle2;
                        }
                        if (_cursor.isNull(_cursorIndexOfCompany)) {
                            _tmpCompany = null;
                        } else {
                            String _tmpCompany2 = _cursor.getString(_cursorIndexOfCompany);
                            _tmpCompany = _tmpCompany2;
                        }
                        long _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsCommitted);
                        boolean _tmpIsCommitted = _tmp != 0;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsVisibleInHistory);
                        boolean _tmpIsVisibleInHistory = _tmp_1 != 0;
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
                        boolean _tmpIsActive = _tmp_2 != 0;
                        if (_cursor.isNull(_cursorIndexOfVersion)) {
                            _tmpVersion = null;
                        } else {
                            _tmpVersion = Integer.valueOf(_cursor.getInt(_cursorIndexOfVersion));
                        }
                        if (_cursor.isNull(_cursorIndexOfCycleJson)) {
                            _tmpCycleJson = null;
                        } else {
                            String _tmpCycleJson2 = _cursor.getString(_cursorIndexOfCycleJson);
                            _tmpCycleJson = _tmpCycleJson2;
                        }
                        CycleEntity _item = new CycleEntity(_tmpId, _tmpJobTitle, _tmpCompany, _tmpCreatedAt, _tmpIsCommitted, _tmpIsVisibleInHistory, _tmpIsActive, _tmpVersion, _tmpCycleJson);
                        _result.add(_item);
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        });
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Flow<List<CycleEntity>> getVisibleCycles() {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM cycles WHERE isVisibleInHistory = 1 ORDER BY createdAt DESC", 0);
        return CoroutinesRoom.createFlow(this.__db, false, new String[]{"cycles"}, new Callable<List<CycleEntity>>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.10
            @Override // java.util.concurrent.Callable
            public List<CycleEntity> call() throws Exception {
                String _tmpId;
                String _tmpJobTitle;
                String _tmpCompany;
                Integer _tmpVersion;
                String _tmpCycleJson;
                Cursor _cursor = DBUtil.query(CycleDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfJobTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "jobTitle");
                    int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
                    int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
                    int _cursorIndexOfIsCommitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCommitted");
                    int _cursorIndexOfIsVisibleInHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisibleInHistory");
                    int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
                    int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
                    int _cursorIndexOfCycleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "cycleJson");
                    List<CycleEntity> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _tmpId = null;
                        } else {
                            _tmpId = _cursor.getString(_cursorIndexOfId);
                        }
                        if (_cursor.isNull(_cursorIndexOfJobTitle)) {
                            _tmpJobTitle = null;
                        } else {
                            String _tmpJobTitle2 = _cursor.getString(_cursorIndexOfJobTitle);
                            _tmpJobTitle = _tmpJobTitle2;
                        }
                        if (_cursor.isNull(_cursorIndexOfCompany)) {
                            _tmpCompany = null;
                        } else {
                            String _tmpCompany2 = _cursor.getString(_cursorIndexOfCompany);
                            _tmpCompany = _tmpCompany2;
                        }
                        long _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsCommitted);
                        boolean _tmpIsCommitted = _tmp != 0;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsVisibleInHistory);
                        boolean _tmpIsVisibleInHistory = _tmp_1 != 0;
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
                        boolean _tmpIsActive = _tmp_2 != 0;
                        if (_cursor.isNull(_cursorIndexOfVersion)) {
                            _tmpVersion = null;
                        } else {
                            _tmpVersion = Integer.valueOf(_cursor.getInt(_cursorIndexOfVersion));
                        }
                        if (_cursor.isNull(_cursorIndexOfCycleJson)) {
                            _tmpCycleJson = null;
                        } else {
                            String _tmpCycleJson2 = _cursor.getString(_cursorIndexOfCycleJson);
                            _tmpCycleJson = _tmpCycleJson2;
                        }
                        CycleEntity _item = new CycleEntity(_tmpId, _tmpJobTitle, _tmpCompany, _tmpCreatedAt, _tmpIsCommitted, _tmpIsVisibleInHistory, _tmpIsActive, _tmpVersion, _tmpCycleJson);
                        _result.add(_item);
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }

            protected void finalize() {
                _statement.release();
            }
        });
    }

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object getCycleById(final String id, final Continuation<? super CycleEntity> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM cycles WHERE id = ? LIMIT 1", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<CycleEntity>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.11
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public CycleEntity call() throws Exception {
                CycleEntity _result;
                String _tmpId;
                String _tmpJobTitle;
                String _tmpCompany;
                Integer _tmpVersion;
                String _tmpCycleJson;
                Cursor _cursor = DBUtil.query(CycleDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfJobTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "jobTitle");
                    int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
                    int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
                    int _cursorIndexOfIsCommitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCommitted");
                    int _cursorIndexOfIsVisibleInHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisibleInHistory");
                    int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
                    int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
                    int _cursorIndexOfCycleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "cycleJson");
                    if (_cursor.moveToFirst()) {
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _tmpId = null;
                        } else {
                            _tmpId = _cursor.getString(_cursorIndexOfId);
                        }
                        if (_cursor.isNull(_cursorIndexOfJobTitle)) {
                            _tmpJobTitle = null;
                        } else {
                            String _tmpJobTitle2 = _cursor.getString(_cursorIndexOfJobTitle);
                            _tmpJobTitle = _tmpJobTitle2;
                        }
                        if (_cursor.isNull(_cursorIndexOfCompany)) {
                            _tmpCompany = null;
                        } else {
                            String _tmpCompany2 = _cursor.getString(_cursorIndexOfCompany);
                            _tmpCompany = _tmpCompany2;
                        }
                        long _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsCommitted);
                        boolean _tmpIsCommitted = _tmp != 0;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsVisibleInHistory);
                        boolean _tmpIsVisibleInHistory = _tmp_1 != 0;
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
                        boolean _tmpIsActive = _tmp_2 != 0;
                        if (_cursor.isNull(_cursorIndexOfVersion)) {
                            _tmpVersion = null;
                        } else {
                            _tmpVersion = Integer.valueOf(_cursor.getInt(_cursorIndexOfVersion));
                        }
                        if (_cursor.isNull(_cursorIndexOfCycleJson)) {
                            _tmpCycleJson = null;
                        } else {
                            String _tmpCycleJson2 = _cursor.getString(_cursorIndexOfCycleJson);
                            _tmpCycleJson = _tmpCycleJson2;
                        }
                        _result = new CycleEntity(_tmpId, _tmpJobTitle, _tmpCompany, _tmpCreatedAt, _tmpIsCommitted, _tmpIsVisibleInHistory, _tmpIsActive, _tmpVersion, _tmpCycleJson);
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

    @Override // com.vantedge.app.data.storage.CycleDao
    public Object getActiveCycle(final Continuation<? super CycleEntity> $completion) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM cycles WHERE isActive = 1 LIMIT 1", 0);
        CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
        return CoroutinesRoom.execute(this.__db, false, _cancellationSignal, new Callable<CycleEntity>() { // from class: com.vantedge.app.data.storage.CycleDao_Impl.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public CycleEntity call() throws Exception {
                CycleEntity _result;
                String _tmpId;
                String _tmpJobTitle;
                String _tmpCompany;
                Integer _tmpVersion;
                String _tmpCycleJson;
                Cursor _cursor = DBUtil.query(CycleDao_Impl.this.__db, _statement, false, null);
                try {
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfJobTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "jobTitle");
                    int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
                    int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
                    int _cursorIndexOfIsCommitted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCommitted");
                    int _cursorIndexOfIsVisibleInHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisibleInHistory");
                    int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
                    int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
                    int _cursorIndexOfCycleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "cycleJson");
                    if (_cursor.moveToFirst()) {
                        if (_cursor.isNull(_cursorIndexOfId)) {
                            _tmpId = null;
                        } else {
                            _tmpId = _cursor.getString(_cursorIndexOfId);
                        }
                        if (_cursor.isNull(_cursorIndexOfJobTitle)) {
                            _tmpJobTitle = null;
                        } else {
                            String _tmpJobTitle2 = _cursor.getString(_cursorIndexOfJobTitle);
                            _tmpJobTitle = _tmpJobTitle2;
                        }
                        if (_cursor.isNull(_cursorIndexOfCompany)) {
                            _tmpCompany = null;
                        } else {
                            String _tmpCompany2 = _cursor.getString(_cursorIndexOfCompany);
                            _tmpCompany = _tmpCompany2;
                        }
                        long _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                        int _tmp = _cursor.getInt(_cursorIndexOfIsCommitted);
                        boolean _tmpIsCommitted = _tmp != 0;
                        int _tmp_1 = _cursor.getInt(_cursorIndexOfIsVisibleInHistory);
                        boolean _tmpIsVisibleInHistory = _tmp_1 != 0;
                        int _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
                        boolean _tmpIsActive = _tmp_2 != 0;
                        if (_cursor.isNull(_cursorIndexOfVersion)) {
                            _tmpVersion = null;
                        } else {
                            _tmpVersion = Integer.valueOf(_cursor.getInt(_cursorIndexOfVersion));
                        }
                        if (_cursor.isNull(_cursorIndexOfCycleJson)) {
                            _tmpCycleJson = null;
                        } else {
                            String _tmpCycleJson2 = _cursor.getString(_cursorIndexOfCycleJson);
                            _tmpCycleJson = _tmpCycleJson2;
                        }
                        _result = new CycleEntity(_tmpId, _tmpJobTitle, _tmpCompany, _tmpCreatedAt, _tmpIsCommitted, _tmpIsVisibleInHistory, _tmpIsActive, _tmpVersion, _tmpCycleJson);
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
