package dev.cmifsud.composed.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Random;

import dev.cmifsud.composed.models.Task;

import static java.sql.Types.NULL;

/**
 * Room base database class
 */
@Database( entities = {Task.class}, version = 1, exportSchema = false )
public abstract class AppDatabase extends RoomDatabase
{
    // Data access objects
    public abstract TaskDao getTaskDao();

    // Singleton instance
    public static AppDatabase INSTANCE;

    public static AppDatabase getDb(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder( context, AppDatabase.class, "TaskDb" )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()               // TODO: Delet this
//                            .addCallback(testDbPopulateCallback)    // TODO: and this
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // TODO: Remove this
    private static RoomDatabase.Callback testDbPopulateCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);
            new DummyPopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class DummyPopulateDbAsync extends AsyncTask<Void,Void,Void>
    {
        private final TaskDao _taskDao;

        DummyPopulateDbAsync(AppDatabase db)
        {
            _taskDao = db.getTaskDao();
        }

        @Override
        protected Void doInBackground(final Void... params)
        {
            Log.d("BEDUG", "Inserting sample data");
            String[] names = {"Do", "The", "Thing", "Dont", "Not",
                    "Do", "The", "Things", "But", "Butt"};

            _taskDao.nuke();

            Random rng = new Random();
            // db test population
            for (int i = 0; i < 10; i++)
            {
                Task t = new Task();
                t.setName(names[i]);
                t.setComplete( i % 5 == 0);
                t.setArchived(false);
//                t.setParentId(NULL);
                int id = (int)_taskDao.insert(t);

                for (int j = 0; j < rng.nextInt(3); j++)
                {
                    Task s = new Task();
                    s.setName(names[j] + rng.nextInt());
                    s.setComplete(j % 2 == 0);
                    s.setArchived(false);
                    s.setParentId(id);
                    Log.d("BEDUG", "Inserting " + s.getName() );
                    _taskDao.insert(s);
                }
            }

            Log.d("BEDUG", "Sample insertion complete");
            return null;
        }
    }
}