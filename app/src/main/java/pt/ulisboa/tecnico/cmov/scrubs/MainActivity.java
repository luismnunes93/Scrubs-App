package pt.ulisboa.tecnico.cmov.scrubs;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.ulisboa.tecnico.cmov.scrubs.adapters.QuestionsAdapter;
import pt.ulisboa.tecnico.cmov.scrubs.decorators.MyDividerItemDecoration;
import pt.ulisboa.tecnico.cmov.scrubs.db.AppDatabase;
import pt.ulisboa.tecnico.cmov.scrubs.db.entity.QuestionEntity;
import pt.ulisboa.tecnico.cmov.scrubs.fetch.SearchQuestionApi;
import pt.ulisboa.tecnico.cmov.scrubs.listeners.RecyclerTouchListener;
import pt.ulisboa.tecnico.cmov.scrubs.models.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String API_BASE_URL = "http://192.168.1.81:8000/polls/";

    private List<Question> questions = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuestionsAdapter mAdapter;
    private SearchView searchView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cache-database").build();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        whiteNotificationBar(recyclerView);
        mAdapter = new QuestionsAdapter(questions);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Question movie = questions.get(position);
                Toast.makeText(getApplicationContext(), movie.getQuestion_text() + " is selected!", Toast.LENGTH_SHORT).show();


                Context context = getApplicationContext();
                Intent intent = new Intent(context, ScrubDetailsActivity.class);
                intent.putExtra("QUESTION_TEXT", questions.get(position).getQuestion_text());
                intent.putExtra("PUB_DATE", questions.get(position).getPub_date());
                intent.putExtra("THUMBNAIL", questions.get(position).getThumbnail());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //setCache();
            }
        });

        initiateQuestionApi();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void initiateQuestionApi() {

        Log.d(LOG_TAG, "initiateQuestionApi");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SearchQuestionApi api = retrofit.create(SearchQuestionApi.class);
        Call<Question[]> call = api.getQuestionsList("json");
        Log.d(LOG_TAG, "beforeCall");
        call.enqueue(new Callback<Question[]>() {
            @Override
            public void onResponse(Call<Question[]> call, Response<Question[]> response) {
                Log.d(LOG_TAG, "onResponse");
                if(response.isSuccessful()) {
                    Log.d(LOG_TAG, "success - response is " + response.body().length);

                    List<Question> questionsAux = Arrays.asList(response.body());

                    questions.addAll(questionsAux);

                    mAdapter.notifyDataSetChanged();

                } else {
                    Log.d(LOG_TAG, "failure response is " + response.raw().toString());
                }
            }
            @Override
            public void onFailure(Call<Question[]> call, Throwable t) {
                //getCache();
                t.printStackTrace();
            }
        });
    }

    private void getCache(){

        Log.d(LOG_TAG, "GET CACHE");

        new Thread(new Runnable() {
            public void run() {
                List<QuestionEntity> questionsEntities = db.questionDao().getAll();

                for(QuestionEntity questionEntity: questionsEntities){
                    Question question = new Question (questionEntity.getQuestion_text(), questionEntity.getPub_date(), questionEntity.getThumbnail());
                    questions.add(question);

                    mAdapter.notifyDataSetChanged();
                }

            }
        }).start();

    }


    private void setCache(){

        Log.d(LOG_TAG, "SET CACHE");

        new Thread(new Runnable() {
            public void run() {
                for(Question question: questions) {
                    QuestionEntity questionEntity = new QuestionEntity(question.getQuestion_text(), question.getPub_date(), question.getThumbnail());
                    db.questionDao().insertUsers(questionEntity);
                }
            }
        }).start();

        //Test purposes only
        /*
        new Thread(new Runnable() {
            public void run() {
                List<QuestionEntity> questionsEntity = db.questionDao().getAll();

                Log.d(LOG_TAG, questionsEntity.get(1).getQuestion_text());
                }
        }).start();
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if( id == android.R.id.home) {
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        Log.d(LOG_TAG, "Back Pressed");
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
