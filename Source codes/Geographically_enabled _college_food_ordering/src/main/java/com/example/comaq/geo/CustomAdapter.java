package com.example.comaq.geo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseExpandableListAdapter {
    public CustomAdapter(Context context, HashMap<String, List<String>> childtitles, List<String> headertitles) {
        this.context = context;
        this.childtitles = childtitles;
        this.headertitles = headertitles;
    }

    private Context context;
    private HashMap<String , List<String>> childtitles;
    List<String> headertitles;
    @Override
    public int getGroupCount() {
        return this.headertitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childtitles.get(this.headertitles.get(groupPosition)).size() ;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headertitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childtitles.get(this.headertitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String ListTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.group_header, null);
        }
            TextView listTitleTextView = (TextView) convertView.findViewById(R.id.title);
            listTitleTextView.setText(ListTitle);
            return convertView;
        }




    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String expandedListText= (String) getChild(groupPosition ,childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.child_item, null);
        }
            TextView expandedListTextView = (TextView) convertView.findViewById(R.id.childItem);
            expandedListTextView.setText(expandedListText);
            return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
