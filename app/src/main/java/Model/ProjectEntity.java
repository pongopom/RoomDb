package Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the entity for a Room db
 *SEE DOCS
 * https://developer.android.com/topic/libraries/architecture/room
 * It is also used by retrofit
 */

@Entity(tableName = "Projects")
public class ProjectEntity {

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo @SerializedName("NAME")

    private String mTitle;

    @ColumnInfo @SerializedName("DATE")
    private long mDate;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        this.mDate = date;
    }

}
