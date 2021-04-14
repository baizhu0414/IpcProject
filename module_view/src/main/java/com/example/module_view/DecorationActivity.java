package com.example.module_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.views.SimpleItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 尝试使用AndroidUI：RecyclerView的ItemDecoration.
 *
 * */
public class DecorationActivity extends AppCompatActivity {

    public static String TAG = "DecorationActivity";

    private String[] names = new String[]{"B神", "基神", "曹神"};
    private String[] says = new String[]{"无形被黑，最为致命", "大神好厉害~", "我将带头日狗~"};
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
            item.put("desc", says[i]);
            listitem.add(item);
        }
        TestAdapter adapter = new TestAdapter(this, listitem);

        RecyclerView recyclerView = findViewById(R.id.rec_decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleItemDecoration(this, RecyclerView.VERTICAL));
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
            View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, viewGroup,false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.icon.setImageResource(R.mipmap.ic_launcher);
            viewHolder.name.setText(mDatas.get(i).get("name").toString());
            viewHolder.desc.setText(mDatas.get(i).get("desc").toString());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击"+mDatas.get(i).toString(), Toast.LENGTH_SHORT).show();
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