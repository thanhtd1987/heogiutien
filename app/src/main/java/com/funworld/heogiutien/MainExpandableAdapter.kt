package com.funworld.heogiutien

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

import com.funworld.heogiutien.data.model.Expense

import java.util.HashMap

/**
 * Created by ThanhTD on 6/25/2017.
 */

class MainExpandableAdapter(private val mContext: Context,
                            private val mHeaderGroup: List<String>,
                            private val mDataChild: HashMap<String, List<Expense>>)
    : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return mHeaderGroup.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return mDataChild[mHeaderGroup[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return mHeaderGroup[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return mDataChild[mHeaderGroup[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val li = LayoutInflater.from(mContext)
            convertView = li.inflate(R.layout.group_layout, parent, false)
        }

        val tvHeader = convertView!!.findViewById<View>(R.id.tv_grp_name) as TextView
        val tv_grp_total = convertView.findViewById<View>(R.id.tv_grp_total) as TextView
        tvHeader.text = mHeaderGroup[groupPosition]
        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val li = LayoutInflater.from(mContext)
            convertView = li.inflate(R.layout.expense_row_layout, parent, false)
        }

        val tv_row_money_amount = convertView!!.findViewById<View>(R.id.tv_row_money_amount) as TextView
        val tv_row_reason = convertView.findViewById<View>(R.id.tv_row_reason) as TextView
        val tv_row_time = convertView.findViewById<View>(R.id.tv_row_time) as TextView

        tv_row_reason.text = (getChild(groupPosition, childPosition) as Expense).purpose
        tv_row_time.text = (getChild(groupPosition, childPosition) as Expense).createdAt.toString() + "" // 2017-06-24 15:30:23 --> 15:30
        tv_row_money_amount.text = (getChild(groupPosition, childPosition) as Expense).amount.toString() + "k"
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    companion object {
        private val TAG = "MainExpandableAdapter"
    }
}

//     HashMap<String, List<Expense>> mData;
//     private ExpandableListView elv_main;

//    void initRecentExpenseList(){
//
//        //init data
//        mData = new HashMap<>();
//
//        final List<String> dates = new ArrayList<>();
//        dates.add("2017-06-24");
//        dates.add("2017-06-23");
//        dates.add("2017-06-22");
//        dates.add("2017-06-21");
//
//        List<Expense> expenses = new ArrayList<>();
////        for(int i=0; i<5; i++)
////            expenses.add(new Expense(i, "using", "", i*10+5, "an sang", new java.sql.Date(System.currentTimeMillis()), ""));
//
//        for(int i=0; i < dates.size(); i++)
//            mData.put(dates.get(i), expenses);
//
//        //init adapter
//        MainExpandableAdapter mAdapter = new MainExpandableAdapter(this, dates, mData);
//        elv_main.setAdapter(mAdapter);
//
//        elv_main.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Log.e(TAG, "onChildClick: " + dates.get(groupPosition) + ", "
//                        + mData.get(dates.get(groupPosition)).get(childPosition).getAmount());
//                return false;
//            }
//        });
//
//        elv_main.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                Log.e(TAG, "onGroupClick: " + groupPosition);
//                return false;
//            }
//        });
//
//        elv_main.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Log.e(TAG, "onGroupCollapse: " + groupPosition);
//            }
//        });
//
//        elv_main.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Log.e(TAG, "onGroupExpand: " + groupPosition);
//            }
//        });
//    }
