package pt.ulisboa.tecnico.cmov.scrubs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.scrubs.R;
import pt.ulisboa.tecnico.cmov.scrubs.models.Question;

/**
 * Created by luisnunes on 28/11/2017.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Question> questionListFiltered;
    private List<Question> questionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question_text, pub_date;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            question_text = (TextView) view.findViewById(R.id.question_text);
            pub_date = (TextView) view.findViewById(R.id.pub_date);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public QuestionsAdapter(List<Question> questionsList) {
        this.questionsList = questionsList;
        this.questionListFiltered = questionsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_row, parent, false);
        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Question question = questionListFiltered.get(position);
        holder.question_text.setText(question.getQuestion_text());
        holder.pub_date.setText(question.getPub_date());


        Glide.with(context)
                .load(question.getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return questionListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    questionListFiltered = questionsList;
                } else {
                    List<Question> filteredList = new ArrayList<>();
                    for (Question row : questionsList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getQuestion_text().toLowerCase().contains(charString.toLowerCase()) || row.getPub_date().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    questionListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = questionListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                questionListFiltered = (ArrayList<Question>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
