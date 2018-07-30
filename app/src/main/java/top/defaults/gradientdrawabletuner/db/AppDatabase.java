package top.defaults.gradientdrawabletuner.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {DrawableSpec.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static Executor executor = Executors.newSingleThreadScheduledExecutor();

    public abstract DrawableSpecDao drawableSpecDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                     instance = Room.databaseBuilder(context,
                            AppDatabase.class, "gradient-drawable-db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    execute(() -> getInstance(context).drawableSpecDao().insertAll(DrawableSpec.populateData()));
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }

    public interface ExecutorCallback {
        void onExecute();
    }

    public static void execute(ExecutorCallback callback) {
        executor.execute(callback::onExecute);
    }
}
