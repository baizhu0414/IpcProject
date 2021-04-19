package com.example.module_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableActivity extends AppCompatActivity {

    public String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
    public String[][] childStrings = {
            {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
            {"宋江", "林冲", "李逵", "鲁智深"},
            {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        ExpandableListView expandableListView = findViewById(R.id.expand_list);
        MyExpandableAdapter adapter = new MyExpandableAdapter();
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(ExpandableActivity.this, (String) adapter.getChild(groupPosition, childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    class MyExpandableAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return groupStrings.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childStrings[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupStrings[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childStrings[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return groupPosition * 10 + childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(ExpandableActivity.this).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            GroupViewHolder groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_group = convertView.findViewById(android.R.id.text1);
            groupViewHolder.tv_group.setText(groupStrings[groupPosition]);
            convertView.setTag(groupViewHolder);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(ExpandableActivity.this).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            ChildViewHolder childViewHolder = new ChildViewHolder();
            childViewHolder.tv_child = convertView.findViewById(android.R.id.text1);
            childViewHolder.tv_child.setText(childStrings[groupPosition][childPosition]);
            convertView.setTag(childViewHolder);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupViewHolder {
            TextView tv_group;
        }

        class ChildViewHolder {
            TextView tv_child;
        }
    }
}