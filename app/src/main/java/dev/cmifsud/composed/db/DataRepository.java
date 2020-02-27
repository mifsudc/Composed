package dev.cmifsud.composed.db;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import dev.cmifsud.composed.models.Task;

public class DataRepository {

    private TaskDao _taskDao;

    public DataRepository(Application app) {
        Log.d("BEDUG", "Creating data repo");
        AppDatabase db = AppDatabase.getDb(app);
        _taskDao = db.getTaskDao();
        Log.d("BEDUG", "Data repo created");
    }

    // Accessors
    public List<Task> fetchTasks() {
        return _taskDao.fetch();
    }

    public void updateTask(Task task) {
        new UpdateTaskAsync(_taskDao).execute(task);
    }

    public void insertTask(Task task) {
        new InsertTaskAsync(_taskDao).execute(task);
    }

    // Async sub-accessors
    private static class InsertTaskAsync extends AsyncTask<Task, Void, Integer> {
        private TaskDao _asyncDao;
        private Task _task;

        InsertTaskAsync(TaskDao dao) {
            _asyncDao = dao;
        }

        @Override
        protected Integer doInBackground(final Task... params) {
            _task = params[0];
            return (int) _asyncDao.insert(_task);
        }

        @Override
        protected void onPostExecute(Integer insertId) {
            //super.onPostExecute(insertId);
            _task.setTaskId(insertId);
        }
    }

    private static class UpdateTaskAsync extends AsyncTask<Task, Void, Void> {
        private TaskDao _asyncDao;

        UpdateTaskAsync(TaskDao dao) {
            _asyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            _asyncDao.update(params[0]);
            return null;
        }
    }
}
