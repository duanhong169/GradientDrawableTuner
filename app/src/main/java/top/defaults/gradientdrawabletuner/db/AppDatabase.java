package top.defaults.gradientdrawabletuner.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {DrawableSpec.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

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
                                    Executors.newSingleThreadScheduledExecutor().execute(() ->
                                            getInstance(context).drawableSpecDao().insert(DrawableSpec.populateData()));
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }
}
