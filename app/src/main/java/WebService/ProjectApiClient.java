package WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectApiClient {

private static final String BASE_URL = "https://api.myjson.com/bins/";
private static Retrofit retrofit = null;

public static Retrofit getApiClient(){
    if(retrofit == null){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;
}

}