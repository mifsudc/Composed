package dev.cmifsud.composed.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.cmifsud.composed.R;
import dev.cmifsud.composed.models.Task;
import dev.cmifsud.composed.models.TaskViewModel;
import dev.cmifsud.composed.utils.TaskListRVAdapter;

/**
 * Main fragment showing items in a list
 */
public class TaskListFragment extends Fragment {

    public static final String RESID = "RESID";

    private RecyclerView _rv;
    private TaskViewModel _viewModel;
    private IOnInteractionListener _listener;
    private List<Task> _tasks;

    public TaskListFragment() {} // required empty constructor

    public static TaskListFragment newInstance(Integer resourceId) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(RESID, resourceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _viewModel = new ViewModelProvider( getActivity() ).get( TaskViewModel.class );

        if (getArguments() != null) {
            Integer id = getArguments().getInt(RESID);
            Log.d("BEDUG", "Creating new list fragment with id " + id);
            _tasks = _viewModel.getTasksByParentId(id);
        }
        else {
            _tasks = new ArrayList<>();
            Log.d("BEDUG", "Error in resid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle instanceState) {

        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        // Set the adapter
        _rv = view.findViewById(R.id.list);
        _rv.setLayoutManager( new LinearLayoutManager( getActivity() ) );
//        _rv.addItemDecoration( new DividerItemDecoration(
//                _rv.getContext(), DividerItemDecoration.VERTICAL) );

        _rv.setAdapter(
            new TaskListRVAdapter(
                _tasks,
                getActivity(),
                _listener
        ));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnInteractionListener) {
            _listener = (IOnInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent i)
//    {
//        Log.d("BEDUG", String.valueOf(resultCode));
//        if (resultCode == RESULT_OK)
//        {
//            Task newTask = i.getParcelableExtra(TaskEditActivity.TASK);
//            Log.d("BEDUG", String.valueOf(newTask == null));
//            if (newTask == null) return;
//            Log.d("BEDUG", newTask.getName());
//            Log.d("BEDUG", String.valueOf( newTask.getTaskId() ));
//            if (newTask.getTaskId() == 0)
//            {
//                Log.d("BEDUG", String.valueOf( _node.getTask().getTaskId() ));
//                newTask.setParentId(_node.getTask().getTaskId());
//                _viewModel.insertTask(newTask);
//                _node.addSubtask( new TaskNode(newTask) );
//                //_tasks.add(newTask);
//            }
//            else _viewModel.updateTask(newTask);
//            _rv.getAdapter().notifyDataSetChanged();
//        }
//    }

    public interface IOnInteractionListener {
        void openTask(Integer taskId);
        void saveTask(Task task);
    }

    /*
     * Menu methods
     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // conf dialog
//        // archive
//        // toast
//        return true;
//    }
}