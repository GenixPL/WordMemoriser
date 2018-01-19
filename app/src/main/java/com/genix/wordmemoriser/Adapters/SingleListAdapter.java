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

public class SingleListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SetNamesForAdapter> setNamesArray;

    public SingleListAdapter(Context context, ArrayList<SetNamesForAdapter> setNamesArray) {
        this.context = context;
        this.setNamesArray = setNamesArray;
    }

    @Override
    public int getCount() {
        return setNamesArray.size();
    }

    @Override
    public Object getItem(int i) {
        return setNamesArray.get(i);
    }

    public Object getSetName(int i){
        return setNamesArray.get(i).getSetName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View toReturn = View.inflate(context, R.layout.list_view_single_item, null);
        TextView singleItem = toReturn.findViewById(R.id.singleItem);

        singleItem.setText(setNamesArray.get(i).getSetName());

        toReturn.setTag(setNamesArray.get(i).getId());
        return toReturn;
    }
}
