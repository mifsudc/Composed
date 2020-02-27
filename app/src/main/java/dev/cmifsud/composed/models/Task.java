package dev.cmifsud.composed.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * A single to-do task
 */
@Entity( foreignKeys = {
        @ForeignKey(
                entity = Task.class,
                parentColumns = "taskId",
                childColumns = "parentId"
        )
})
public class Task implements Parcelable
{
    // Member fields
    @PrimaryKey(autoGenerate = true)
    private int taskId;
    private String name;
    private boolean complete;
    private boolean archived;
    @Nullable
    private Integer parentId;

    public Task() {}

    // Accessors
    public Integer getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setComplete(boolean  status) { complete = status; }
    public boolean getComplete() { return complete; }

    public void setArchived(boolean  status) { archived = status; }
    public boolean getArchived() { return archived; }

    public void setParentId(@Nullable Integer parentId) { this.parentId = parentId; }
    @Nullable
    public Integer getParentId() { return parentId; }

    /*
     * Parcelable methods
     */
    private Task(Parcel in)
    {
        this.taskId = in.readInt();
        this.name = in.readString();
        this.complete = in.readInt() == 1;
        this.archived = in.readInt() == 1;
        this.parentId = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(taskId);
        dest.writeString(name);
        dest.writeInt(complete ? 1 : 0);
        dest.writeInt(archived ? 1 : 0);
        dest.writeInt(parentId);
    }

    public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>()
    {
        @Override
        public Task createFromParcel(Parcel in)
        {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size)
        {
            return new Task[size];
        }
    };
}
