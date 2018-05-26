package Model;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;


@Dao
public abstract class ProjectDao {

    @Insert
    public abstract void createProject(ProjectEntity projectEntity);

    //wrapped in liveData object
    @Query("SELECT * FROM projects")
    public abstract LiveData<List<ProjectEntity>> getAllProjects();

    @Delete
    public abstract void delete(ProjectEntity projectEntity);

    @Transaction
   public void updateData(List<ProjectEntity> projects) {
        deleteAll();
        insertAll(projects);
    }

    @Query("DELETE FROM projects")
    public abstract void deleteAll();

    @Query("DELETE FROM projects WHERE uid = :uid")
    public abstract void deleteByUid(int uid);

    @Insert
    public abstract void insertAll(List<ProjectEntity> projects);

    //wrapped in liveData object
    @Query("SELECT * FROM Projects WHERE uid IN (:uid)")
    public abstract LiveData<List<ProjectEntity>> loadAllByIds(int[] uid);

}
