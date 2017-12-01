package pt.ulisboa.tecnico.cmov.scrubs.entities;

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

    @ColumnInfo(name = "question_text")
    private String question_text;

    @ColumnInfo(name = "pub_date")
    private String pub_date;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;


    public QuestionEntity(String question_text, String pub_date, String thumbnail){
        this.uid = UUID.randomUUID().toString();
        this.question_text = question_text;
        this.pub_date = pub_date;
        this.thumbnail = thumbnail;
    }

    public String getUid() {
        return this.uid;
    }

    public String getQuestion_text(){
        return this.question_text;
    }

    public String getPub_date(){
        return  this.pub_date;
    }

    public String getThumbnail(){
        return this.thumbnail;
    }

    public void setQuestion_text(String question_text){
        this.question_text = question_text;
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

