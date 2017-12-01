package pt.ulisboa.tecnico.cmov.scrubs.entities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Created by luisnunes on 30/11/2017.
 */

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions")
    List<QuestionEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(QuestionEntity... questionEntities);

    @Query("SELECT COUNT(*) from questions")
    int countUsers();

    @Query("DELETE FROM questions")
    void nukeTable();
}
