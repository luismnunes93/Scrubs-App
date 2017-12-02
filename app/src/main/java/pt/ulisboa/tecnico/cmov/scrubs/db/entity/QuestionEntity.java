package pt.ulisboa.tecnico.cmov.scrubs.db.entity;

/**
 * Created by luisnunes on 30/11/2017.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "questions")
public class QuestionEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userid")
    private String uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "pub_date")
    private String pub_date;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;


    public QuestionEntity(String name, String pub_date, String thumbnail){
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.pub_date = pub_date;
        this.thumbnail = thumbnail;
    }

    public String getUid() {
        return this.uid;
    }

    public String getName(){
        return this.name;
    }

    public String getPub_date(){
        return  this.pub_date;
    }

    public String getThumbnail(){
        return this.thumbnail;
    }

    public void setName(String question_text){
        this.name = name;
    }

    public void setPub_date(String pub_date){
        this.pub_date = pub_date;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public void setUid(String uid){
        this.uid = UUID.randomUUID().toString();
    }
}

