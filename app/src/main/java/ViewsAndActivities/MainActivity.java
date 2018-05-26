package ViewsAndActivities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import Model.ProjectEntity;
import ViewModel.ProjectViewModel;
import uk.co.pongosoft.roomdb.R;

public class MainActivity extends AppCompatActivity {

    private ProjectViewModel mProjectViewModel;
    List<ProjectEntity> mProject;
    ProjectRecyclerViewAdapter mViewAdapter;
    RecyclerView mProjectRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set the viewModel to this activity
        mProjectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        //Observe LiveData
        mProjectViewModel.getAllProjects().observe(this, new Observer<List<ProjectEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ProjectEntity> projectEntities) {
                mProject = projectEntities;
                mViewAdapter.setProjects(mProject);
            }
        });
        mProjectViewModel.webServiceData().observe(this, new Observer<List<ProjectEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ProjectEntity> projectEntities) {
                System.out.println("THE DATA ON RETROFIT CHANGED");
            }
        });
        mProjectViewModel.getOnWebServiceFailed().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String failed) {
                System.out.println(" RETROFIT FAILED" + failed);
            }
        });

        mProjectRecycleView = findViewById(R.id.recycler_view);
        mProjectRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mProjectRecycleView.setHasFixedSize(true);
        mViewAdapter = new ProjectRecyclerViewAdapter(mProject);
        mProjectRecycleView.setAdapter(mViewAdapter);

        final Button button = findViewById(R.id.create_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProjectEntity projectEntity = new ProjectEntity();
                projectEntity.setTitle("hello world");
                projectEntity.setDate(22);
                mProjectViewModel.insert(projectEntity);
            }
        });

        final Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mProjectViewModel.deleteByUid(4);
            }
        });

    }

}

