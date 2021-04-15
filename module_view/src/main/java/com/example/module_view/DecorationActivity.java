package com.example.module_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.views.SimpleItemDecoration;
import com.example.views.scroll.SimpleScroll;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 尝试使用AndroidUI：RecyclerView的ItemDecoration.
 * FlexboxLayoutManager + RecyclerView流式布局.
 * */
public class DecorationActivity extends AppCompatActivity {

    public static String TAG = "DecorationActivity";

    private String[] names = new String[]{"B神", "基神", "曹神"};
    private String[] says = new String[]{"无形被黑", "大神好厉害~", "我将带头日狗~"};
    private int[] imgIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration);

        // init data
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("icon", imgIds[i]);
            item.put("name", names[i]);
            item.put("desc", "des:" + says[i]);
            listitem.add(item);
        }
        TestAdapter adapter = new TestAdapter(this, listitem);


        // 流式布局
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

        RecyclerView recyclerView = findViewById(R.id.rec_decoration);
        // 添加分割线
        recyclerView.addItemDecoration(new SimpleItemDecoration(this, RecyclerView.VERTICAL));
        // 设置布局管理器
        recyclerView.setLayoutManager(flexboxLayoutManager);
        //        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public static class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

        public String TAG = "TestAdapter";

        private List<Map<String, Object>> mDatas;
        private final Context context;
        public TestAdapter(Context context, List<Map<String, Object>> mDatas) {
            this.context = context;
            this.mDatas = mDatas;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            // attachToRoot不能传null，否则看不见。
            View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, viewGroup,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.icon.setImageResource(R.mipmap.ic_launcher_round);
            viewHolder.name.setText(mDatas.get(i).get("name").toString());
            SpannableStringBuilder ssbDesc = new SpannableStringBuilder(mDatas.get(i).get("desc").toString());
            ssbDesc.setSpan(new UnderlineSpan(), 7, ssbDesc.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHolder.desc.setText(ssbDesc);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击"+mDatas.get(i).toString(), Toast.LENGTH_SHORT).show();
                    if (i == 1) {
//                        context.startActivity(new Intent(context, SimpleScrollActivity.class));
                    }
                }
            });
            Log.d(TAG, "onBindView-" + "i:" + i + "-data:" + mDatas.get(i));
        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView icon;
            public TextView name;
            public TextView desc;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.img_icon);
                name = itemView.findViewById(R.id.tv_name);
                desc = itemView.findViewById(R.id.tv_desc);
            }
        }
    }
}