package com.vantedge.app.data.storage;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public final class VantEdgeDatabase_Impl extends VantEdgeDatabase {
    private volatile CycleDao _cycleDao;
    private volatile OnboardingDraftDao _onboardingDraftDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(final DatabaseConfiguration config) {
        SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) { // from class: com.vantedge.app.data.storage.VantEdgeDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(final SupportSQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `cycles` (`id` TEXT NOT NULL, `jobTitle` TEXT NOT NULL, `company` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `isCommitted` INTEGER NOT NULL, `isVisibleInHistory` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `version` INTEGER, `cycleJson` TEXT NOT NULL, PRIMARY KEY(`id`))");
                db.execSQL("CREATE TABLE IF NOT EXISTS `onboarding_draft` (`id` TEXT NOT NULL, `draftJson` TEXT NOT NULL, PRIMARY KEY(`id`))");
                db.execSQL(RoomMasterTable.CREATE_QUERY);
                db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '546ef7d6fc55b939409760efb2dda59d')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(final SupportSQLiteDatabase db) {
                db.execSQL("DROP TABLE IF EXISTS `cycles`");
                db.execSQL("DROP TABLE IF EXISTS `onboarding_draft`");
                List<? extends RoomDatabase.Callback> _callbacks = VantEdgeDatabase_Impl.this.mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onDestructiveMigration(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(final SupportSQLiteDatabase db) {
                List<? extends RoomDatabase.Callback> _callbacks = VantEdgeDatabase_Impl.this.mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onCreate(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(final SupportSQLiteDatabase db) {
                VantEdgeDatabase_Impl.this.mDatabase = db;
                VantEdgeDatabase_Impl.this.internalInitInvalidationTracker(db);
                List<? extends RoomDatabase.Callback> _callbacks = VantEdgeDatabase_Impl.this.mCallbacks;
                if (_callbacks != null) {
                    for (RoomDatabase.Callback _callback : _callbacks) {
                        _callback.onOpen(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(final SupportSQLiteDatabase db) {
                DBUtil.dropFtsSyncTriggers(db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(final SupportSQLiteDatabase db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public RoomOpenHelper.ValidationResult onValidateSchema(final SupportSQLiteDatabase db) {
                HashMap<String, TableInfo.Column> _columnsCycles = new HashMap<>(9);
                _columnsCycles.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, 1));
                _columnsCycles.put("jobTitle", new TableInfo.Column("jobTitle", "TEXT", true, 0, null, 1));
                _columnsCycles.put("company", new TableInfo.Column("company", "TEXT", true, 0, null, 1));
                _columnsCycles.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, 1));
                _columnsCycles.put("isCommitted", new TableInfo.Column("isCommitted", "INTEGER", true, 0, null, 1));
                _columnsCycles.put("isVisibleInHistory", new TableInfo.Column("isVisibleInHistory", "INTEGER", true, 0, null, 1));
                _columnsCycles.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, 1));
                _columnsCycles.put("version", new TableInfo.Column("version", "INTEGER", false, 0, null, 1));
                _columnsCycles.put("cycleJson", new TableInfo.Column("cycleJson", "TEXT", true, 0, null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysCycles = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesCycles = new HashSet<>(0);
                TableInfo _infoCycles = new TableInfo("cycles", _columnsCycles, _foreignKeysCycles, _indicesCycles);
                TableInfo _existingCycles = TableInfo.read(db, "cycles");
                if (!_infoCycles.equals(_existingCycles)) {
                    return new RoomOpenHelper.ValidationResult(false, "cycles(com.vantedge.app.data.storage.CycleEntity).\n Expected:\n" + _infoCycles + "\n Found:\n" + _existingCycles);
                }
                HashMap<String, TableInfo.Column> _columnsOnboardingDraft = new HashMap<>(2);
                _columnsOnboardingDraft.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, 1));
                _columnsOnboardingDraft.put("draftJson", new TableInfo.Column("draftJson", "TEXT", true, 0, null, 1));
                HashSet<TableInfo.ForeignKey> _foreignKeysOnboardingDraft = new HashSet<>(0);
                HashSet<TableInfo.Index> _indicesOnboardingDraft = new HashSet<>(0);
                TableInfo _infoOnboardingDraft = new TableInfo("onboarding_draft", _columnsOnboardingDraft, _foreignKeysOnboardingDraft, _indicesOnboardingDraft);
                TableInfo _existingOnboardingDraft = TableInfo.read(db, "onboarding_draft");
                if (!_infoOnboardingDraft.equals(_existingOnboardingDraft)) {
                    return new RoomOpenHelper.ValidationResult(false, "onboarding_draft(com.vantedge.app.data.storage.OnboardingDraftEntity).\n Expected:\n" + _infoOnboardingDraft + "\n Found:\n" + _existingOnboardingDraft);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "546ef7d6fc55b939409760efb2dda59d", "096e4d4de2ede31f23ed79556d31c311");
        SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
        SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
        return _helper;
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        HashMap<String, String> _shadowTablesMap = new HashMap<>(0);
        HashMap<String, Set<String>> _viewTables = new HashMap<>(0);
        return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cycles", "onboarding_draft");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            _db.execSQL("DELETE FROM `cycles`");
            _db.execSQL("DELETE FROM `onboarding_draft`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<>();
        _typeConvertersMap.put(CycleDao.class, CycleDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(OnboardingDraftDao.class, OnboardingDraftDao_Impl.getRequiredConverters());
        return _typeConvertersMap;
    }

    @Override // androidx.room.RoomDatabase
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<>();
        return _autoMigrationSpecsSet;
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> getAutoMigrations(final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
        List<Migration> _autoMigrations = new ArrayList<>();
        return _autoMigrations;
    }

    @Override // com.vantedge.app.data.storage.VantEdgeDatabase
    public CycleDao cycleDao() {
        CycleDao cycleDao;
        if (this._cycleDao != null) {
            return this._cycleDao;
        }
        synchronized (this) {
            if (this._cycleDao == null) {
                this._cycleDao = new CycleDao_Impl(this);
            }
            cycleDao = this._cycleDao;
        }
        return cycleDao;
    }

    @Override // com.vantedge.app.data.storage.VantEdgeDatabase
    public OnboardingDraftDao onboardingDraftDao() {
        OnboardingDraftDao onboardingDraftDao;
        if (this._onboardingDraftDao != null) {
            return this._onboardingDraftDao;
        }
        synchronized (this) {
            if (this._onboardingDraftDao == null) {
                this._onboardingDraftDao = new OnboardingDraftDao_Impl(this);
            }
            onboardingDraftDao = this._onboardingDraftDao;
        }
        return onboardingDraftDao;
    }
}
