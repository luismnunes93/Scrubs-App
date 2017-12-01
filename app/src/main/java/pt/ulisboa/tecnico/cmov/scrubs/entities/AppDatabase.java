package pt.ulisboa.tecnico.cmov.scrubs.entities;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by luisnunes on 30/11/2017.
 */

@Database(entities = {QuestionEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract QuestionDao questionDao();
}