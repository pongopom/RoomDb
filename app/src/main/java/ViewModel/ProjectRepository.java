package ViewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import Model.ProjectDao;
import Model.ProjectDataBase;
import Model.ProjectEntity;
import WebService.ApiInterface;
import WebService.ProjectApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    /**
     * Code for Room DaterBase --------------------------------------------------------
     */

    private ProjectDao mProjectDao;
    private LiveData<List<ProjectEntity>> mAllProjects;

     ProjectRepository(Application application) {
      // get an instance of the db.
      // We use a singleton so there can only be one instance across the application
        ProjectDataBase db = ProjectDataBase.getAppDatabase(application);
        mProjectDao = db.projectDao();
        mAllProjects = mProjectDao.getAllProjects();
    }

  public LiveData<List<ProjectEntity>> getAllProjects() {
        return mAllProjects;
    }

// creates an insert asyncTask on a background thread
    public void insert (ProjectEntity projectEntity) {
        new insertAsyncTask(mProjectDao).execute(projectEntity);
    }

    private static class insertAsyncTask extends AsyncTask<ProjectEntity, Void, Void> {
        private ProjectDao mAsyncTaskDao;
        insertAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProjectEntity... params) {
            mAsyncTaskDao.createProject(params[0]);
            return null;
        }
    }

    // delete an object from the db
    public void delete (Integer uid) {
        new deleteAsyncTask(mProjectDao).execute(uid);
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ProjectDao mAsyncTaskDao;
        deleteAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteByUid(params[0]);
            return null;
        }
    }

    // delete all objects from the db
    public void deleteAll () {
        new deleteAllAsyncTask(mProjectDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao mAsyncTaskDao;
        deleteAllAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
           mAsyncTaskDao.deleteAll();
            return null;
        }
    }



    private void insertAll(List<ProjectEntity> projects) {
         new insertAllAsyncTask(mProjectDao).execute(projects);
    }
    private static class insertAllAsyncTask extends AsyncTask<List<ProjectEntity>, Void, Void> {
        private ProjectDao mAsyncTaskDao;
        insertAllAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<ProjectEntity>... params) {
            mAsyncTaskDao.updateData(params[0]);

            return null;
        }
    }









    /**
 * Code for Retrofit webservice API -------------------------------------------
 */

    private MutableLiveData<List<ProjectEntity>> data = new MutableLiveData<>();
    public LiveData<List<ProjectEntity>> getWebData() {
        return data;
    }

    private  MutableLiveData<String> failed = new MutableLiveData<>();
    public  LiveData<String> getOnFail() {
        return failed;
    }

    public  void loadProjectsFromWebService() {
    ApiInterface apiInterface;

    apiInterface = ProjectApiClient.getApiClient().create(ApiInterface.class);
    Call<List<ProjectEntity>> call = apiInterface.getProjects();
    call.enqueue(new Callback<List<ProjectEntity>>() {
        @Override
        public void onResponse(@NonNull Call<List<ProjectEntity>>
                                       call, @NonNull Response<List<ProjectEntity>> response) {
            System.out.println("Past" + response);

            List<ProjectEntity> projectEntities = response.body();

            if(projectEntities != null){
data.postValue(projectEntities);
             //  deleteAll();
insertAll(projectEntities);
            }

            System.out.println("Past" + projectEntities);
        }

        @Override
        public void onFailure(@NonNull Call<List<ProjectEntity>> call, @NonNull Throwable t) {
            System.out.println("Failed" + t.getLocalizedMessage());
            failed.postValue(t.getLocalizedMessage());
        }
    });


}


}
