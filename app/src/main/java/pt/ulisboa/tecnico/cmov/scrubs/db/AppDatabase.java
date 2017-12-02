package pt.ulisboa.tecnico.cmov.scrubs.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import pt.ulisboa.tecnico.cmov.scrubs.db.dao.QuestionDao;
import pt.ulisboa.tecnico.cmov.scrubs.db.entity.QuestionEntity;

/**
 * Created by luisnunes on 30/11/2017.
 */

@Database(entities = {QuestionEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract QuestionDao questionDao();
}