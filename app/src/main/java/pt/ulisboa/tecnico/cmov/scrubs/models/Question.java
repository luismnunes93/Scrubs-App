package pt.ulisboa.tecnico.cmov.scrubs.models;

/**
 * Created by luisnunes on 28/11/2017.
 */

public class Question {
    private String question_text;
    private String pub_date;
    private String thumbnail;


    public Question(){
      
    }

    public Question(String question_text, String pub_date, String thumbnail){
        this.question_text = question_text;
        this.pub_date = pub_date;
        this.thumbnail = thumbnail;
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
}
