package dev.cmifsud.composed.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.cmifsud.composed.models.Task;

/**
 * Task database access object to interface db & repo
 */
@Dao
public interface TaskDao
{
    @Query( "SELECT * FROM Task" )
    List<Task> fetch();

    @Insert
    long insert(Task task);

    @Update
    void update(Task task);

    // TODO: Delete
    @Query("DELETE FROM Task WHERE archived != 1")  // SQLite false = 1
    void nuke();
}