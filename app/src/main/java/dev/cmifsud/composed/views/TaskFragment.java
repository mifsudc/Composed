package dev.cmifsud.composed.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.cmifsud.composed.R;
import dev.cmifsud.composed.models.Task;
import dev.cmifsud.composed.models.TaskViewModel;

public class TaskFragment extends Fragment {

    private static final String RESID = "RESID";
    private Task _task;
    private TaskListFragment.IOnInteractionListener _listener;
    private TaskViewModel _viewModel;

    public TaskFragment() {} // Required empty public constructor

    public static TaskFragment newInstance(Integer resourceId) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(RESID, resourceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _viewModel = new ViewModelProvider( getActivity() ).get( TaskViewModel.class );

        if ( getArguments() == null ) return;

        Integer id = getArguments().getInt(RESID);
        if (id != 0) {
            _task = _viewModel.getTaskById(id);
            getActivity().setTitle( _task.getName() );
        }
        else {

        }

        Fragment f = TaskListFragment.newInstance( id );
        getChildFragmentManager()
            .beginTransaction()
            .replace(R.id.list_frame, f)
            .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TaskListFragment.IOnInteractionListener) {
            _listener = (TaskListFragment.IOnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TaskListFragment.IOnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }
}
