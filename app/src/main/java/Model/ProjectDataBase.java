package Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database( entities = {ProjectEntity.class}, version = 1)
public abstract class ProjectDataBase extends RoomDatabase {

public abstract ProjectDao projectDao();

private  static ProjectDataBase INSTANCE;


    public static ProjectDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), ProjectDataBase.class, "projects")
                            .build();
        }
        return INSTANCE;
    }

  //  public static void destroyInstance() {
     //   INSTANCE = null;
   // }

}
