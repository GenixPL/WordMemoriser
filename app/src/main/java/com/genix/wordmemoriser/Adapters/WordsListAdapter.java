package com.genix.wordmemoriser.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.genix.wordmemoriser.R;

import java.util.ArrayList;

/**
 * Created by genix on 1/19/2018.
 */

public class WordsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WordsForAdapter> wordsArray;

    public WordsListAdapter(Context context, ArrayList<WordsForAdapter> wordsArray) {
        this.context = context;
        this.wordsArray = wordsArray;
    }

    @Override
    public int getCount() {
        return wordsArray.size();
    }

    @Override
    public Object getItem(int i) {
        return wordsArray.get(i);
    }

    public Object getWord1(int i) {
        return wordsArray.get(i).getWord1();
    }

    public Object getWord2(int i) {
        return wordsArray.get(i).getWord2();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View toReturn = View.inflate(context, R.layout.list_view_words, null);
        TextView firstItemText = toReturn.findViewById(R.id.firstItem);
        TextView secondItemText = toReturn.findViewById(R.id.secondItem);

        firstItemText.setText(wordsArray.get(i).getWord1());
        secondItemText.setText(wordsArray.get(i).getWord2());

        toReturn.setTag(wordsArray.get(i).getId());

        return toReturn;
    }
}
