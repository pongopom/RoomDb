package WebService;

import java.util.List;

import Model.ProjectEntity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

 @GET("10r46e")
//@GET("10r46e")
Call<List<ProjectEntity>> getProjects();

}
