package dev.cmifsud.composed.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import dev.cmifsud.composed.R;
import dev.cmifsud.composed.models.Task;
import dev.cmifsud.composed.models.TaskViewModel;
import dev.cmifsud.composed.utils.TaskListRVAdapter;

public class TaskListActivity extends AppCompatActivity
        implements TaskListFragment.IOnInteractionListener {

    public static final String TASK = "TASK";

    private TaskViewModel _viewModel;

    // Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("BEDUG","Creating main activity");
        _viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        InitialiseUI();
    }


    public void InitialiseUI() {
        Log.d("BEDUG", "Initialising UI");
        setContentView(R.layout.activity_task_list);

        Log.d("BEDUG", "Creating frag");
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.content_frame, TaskFragment.newInstance(0)) // fix w sentinel definition
            .commit();
    }


    // Interaction listener implementation methods
//    @Override
    public void onInteraction(Integer taskId)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, TaskListFragment.newInstance(taskId))
                .addToBackStack(null)
                .commit();
    }

//    @Override
//    public boolean onLongInteraction(Integer taskId) {
//        Intent i = new Intent( this, TaskEditActivity.class );
//        i.putExtra( TaskEditActivity.TASK, _viewModel.getTaskById(taskId) );
//        startActivityForResult(i, 0);
//        return true;
//    }

    @Override
    public void saveTask(Task task) {
        _viewModel.updateTask(task);
    }

    @Override
    public void openTask(Integer taskId) {
        Log.d("BEDUG", "Opening new fragment with id " + taskId);
        getSupportFragmentManager()
            .beginTransaction()
            .replace( R.id.content_frame, TaskFragment.newInstance( taskId ) )
            .addToBackStack("")
            .commit();
    }

//    @Override
    public void onBoxCheck(Integer taskId) {
        Task t = _viewModel.getTaskById(taskId);
        t.setComplete(!t.getComplete());
        _viewModel.updateTask(t);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.task_list_frag, menu);
//        return true;
//    }
}
