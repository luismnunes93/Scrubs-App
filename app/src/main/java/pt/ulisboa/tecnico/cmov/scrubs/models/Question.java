package pt.ulisboa.tecnico.cmov.scrubs.models;

/**
 * Created by luisnunes on 28/11/2017.
 */

public class Question {
    private String name;
    private String pub_date;
    private String thumbnail;


    public Question(){
      
    }

    public Question(String name, String pub_date, String thumbnail){
        this.name = name;
        this.pub_date = pub_date;
        this.thumbnail = thumbnail;
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
        this.name = question_text;
    }

    public void setPub_date(String pub_date){
        this.pub_date = pub_date;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }
}
