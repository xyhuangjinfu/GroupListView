package cn.hjf.grouplistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HideHas extends AppCompatActivity {

    GroupListView groupListView;

    List<String> inService = new ArrayList<>();
    List<String> outService = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        prepareData();

        groupListView = (GroupListView) findViewById(R.id.lv);
        groupListView.setShowEmptyGroup(false);

        groupListView.setAdapter(new GroupListView.LabelDataAdapter() {
            @Override
            public View getGroupView(int group, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new ImageView(HideHas.this);
                }
                ((ImageView) convertView).setImageResource(group % 2 == 0 ? R.mipmap.ic_lyy : R.mipmap.ic_launcher);
                return convertView;
            }

            @Override
            public View getDataView(int group, int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(HideHas.this);
                }
                ((TextView) convertView).setMinHeight(100);
                ((TextView) convertView).setText(group % 2 == 0 ? inService.get(position) : outService.get(position));
                return convertView;
            }

            @Override
            public int getGroupCount() {
                return 4;
            }

            @Override
            public int getDataCount(int groupIndex) {
                return groupIndex % 2 == 0 ? inService.size() : outService.size();
            }
        });
        groupListView.setOnGroupAndDataClickListener(new GroupListView.OnGroupAndDataClickListener() {
            @Override
            public void onGroupClick(int group) {
                Toast.makeText(HideHas.this, "group : " + group, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataClick(int group, int position) {
                Toast.makeText(HideHas.this, "data : " + group + " , " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareData() {
        for (int i = 0; i < 15; i++) {
            inService.add("服务中 ： " + i);
        }
//        for (int i = 0; i < 10; i++) {
//            outService.add("已结束 ： " + i);
//        }
    }


}
