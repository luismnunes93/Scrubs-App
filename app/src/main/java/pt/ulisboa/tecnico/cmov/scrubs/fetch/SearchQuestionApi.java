package pt.ulisboa.tecnico.cmov.scrubs.fetch;

import pt.ulisboa.tecnico.cmov.scrubs.models.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by luisnunes on 28/11/2017.
 */

public interface SearchQuestionApi {

    @GET("api/v1/question/")
    Call<Question[]> getQuestionsList(@Query("format") String format);
}
