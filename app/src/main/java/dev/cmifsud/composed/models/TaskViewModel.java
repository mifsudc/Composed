package dev.cmifsud.composed.models;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dev.cmifsud.composed.db.DataRepository;

/**
 * View model data container to interface UI & repo
 */
public class TaskViewModel extends AndroidViewModel {

    private DataRepository _repo;
    private HashMap<Integer, Task> _tasks;
    private List<Task> _roots;
    private HashMap<Integer, List<Task>> _parents;

    public TaskViewModel(Application app) {
        super(app);

        Log.d("BEDUG", "Creating view model");
        _repo = new DataRepository(app);
        _roots = new ArrayList<>();
        _tasks = new HashMap<>();
        _parents = new HashMap<>();

        Log.d("BEDUG", "Structuring tasks");
        for ( Task t : _repo.fetchTasks() ) {
            _tasks.put( t.getTaskId(), t );

            Integer parentId = t.getParentId();
            if (parentId != null) {
                if ( !_parents.containsKey( parentId ) ) {
                    _parents.put( parentId, new ArrayList<>() );
                }
                _parents.get(parentId).add( t );
            }
            else {
                _roots.add( t );
            }
        }

        Log.d("BEDUG", "View model complete");
    }


    // Accessors
    public List<Task> getTasksByParentId(Integer parentId) {
        List<Task> result;
        if (parentId == 0) {
            result = _roots;
        }
        else result = _parents.get(parentId);
        if (result == null) {
            Log.d("BEDUG", "List null");
            return new ArrayList<>();
        }
        Log.d("BEDUG", "subtasks: " + result.size() );
        return result;
    }

    public Task getTaskById(Integer taskId) {
        return _tasks.get(taskId);
    }

    public void insertTask(Task task) {
        _tasks.put( task.getTaskId(), task);
        _repo.insertTask(task);
    }

    public void updateTask(Task task) {
        _tasks.replace( task.getTaskId(), task );
        _repo.updateTask(task);
    }

    public List<Task> testGetAllTasks() {
        return new ArrayList<>( _tasks.values() );
    }

    public List<Task> testGetRootTasks() {
        return _roots;
    }
}