package top.defaults.gradientdrawabletuner.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DrawableSpecDao {

    @Query("SELECT * FROM drawable_spec")
    LiveData<List<DrawableSpec>> getAll();

    @Query("SELECT * FROM drawable_spec WHERE id = (:id)")
    DrawableSpec findById(long id);

    @Insert(onConflict = IGNORE)
    void insertAll(DrawableSpec... drawableSpec);

    @Insert(onConflict = IGNORE)
    long insert(DrawableSpec drawableSpec);

    @Update(onConflict = REPLACE)
    void update(DrawableSpec drawableSpec);

    @Delete
    void delete(DrawableSpec drawableSpec);
}
