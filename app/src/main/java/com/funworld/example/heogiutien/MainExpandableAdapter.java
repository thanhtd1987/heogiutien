package com.funworld.example.heogiutien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.funworld.example.heogiutien.data.dao.Expense;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThanhTD on 6/25/2017.
 */

public class MainExpandableAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "MainExpandableAdapter";
    private Context mContext;
    private List<String> mHeaderGroup;
    private HashMap<String, List<Expense>> mDataChild;

    public MainExpandableAdapter(Context context, List<String> headerGroup, HashMap<String, List<Expense>> data) {
        mContext = context;
        mHeaderGroup = headerGroup;
        mDataChild = data;
    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).get(childPosition);
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
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.group_layout, parent, false);
        }

        TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_grp_name);
        TextView tv_grp_total = (TextView) convertView.findViewById(R.id.tv_grp_total);
        tvHeader.setText(mHeaderGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.expense_row_layout, parent, false);
        }

        TextView tv_row_money_amount = (TextView) convertView.findViewById(R.id.tv_row_money_amount);
        TextView tv_row_reason = (TextView) convertView.findViewById(R.id.tv_row_reason);
        TextView tv_row_time = (TextView) convertView.findViewById(R.id.tv_row_time);

        tv_row_reason.setText(((Expense) getChild(groupPosition, childPosition)).getPurpose());
        tv_row_time.setText(((Expense) getChild(groupPosition, childPosition)).getCreateAt() + ""); // 2017-06-24 15:30:23 --> 15:30
        tv_row_money_amount.setText(String.valueOf(((Expense) getChild(groupPosition, childPosition)).getAmount())+ "k");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
