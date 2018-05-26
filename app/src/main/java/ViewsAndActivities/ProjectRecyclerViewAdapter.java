package ViewsAndActivities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.ProjectEntity;
import uk.co.pongosoft.roomdb.R;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectRecyclerViewAdapter.ProjectViewholder> {
private List<ProjectEntity> mProjects;

    public void setProjects(List<ProjectEntity> projects) {
        this.mProjects = projects;
        notifyDataSetChanged();
    }

     ProjectRecyclerViewAdapter(List<ProjectEntity> projects) {
        this.mProjects = projects;
    }

    @NonNull
    @Override
    public ProjectViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_view_holder_item, parent, false);
        return new ProjectViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewholder holder, int position) {
        ProjectEntity projectEntity = mProjects.get(position);
        holder.mNameTextView.setText(projectEntity.getTitle());
        String dateString = String.valueOf(projectEntity.getDate());
        holder.mDateTextView.setText(dateString);
    }

    @Override
    public int getItemCount() {
        if(mProjects == null){
            return 0;
        }
        return mProjects.size();
    }

     class ProjectViewholder extends RecyclerView.ViewHolder {
     TextView mNameTextView, mDateTextView;

          ProjectViewholder(View itemView) {
             super(itemView);
             mNameTextView = itemView.findViewById(R.id.name_text_view);
             mDateTextView = itemView.findViewById(R.id.date_text_view);
         }
     }

}
