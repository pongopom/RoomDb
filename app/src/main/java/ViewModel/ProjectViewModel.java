package ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import Model.ProjectEntity;

public class ProjectViewModel extends AndroidViewModel {
    //RoomDb
    private ProjectRepository mRepository;

    private LiveData<List<ProjectEntity>> mAllProjects;

    public ProjectViewModel(Application application) {
        super(application);
        mRepository = new ProjectRepository(application);
        mAllProjects = mRepository.getAllProjects();
        fetchAllProjects();
    }

   public LiveData<List<ProjectEntity>> getAllProjects() {
        return mAllProjects;
    }
    public void insert(ProjectEntity projectEntity) {
        mRepository.insert(projectEntity);
    }
    public void deleteByUid(int uid) {

        mRepository.delete(uid);
    }

    //Retrofit Api webService
    private void fetchAllProjects() {
        mRepository.loadProjectsFromWebService();
    }
    public   LiveData<List<ProjectEntity>> webServiceData(){
        return mRepository.getWebData();
    }
    public LiveData<String> getOnWebServiceFailed (){
        return mRepository.getOnFail();
    }


}
