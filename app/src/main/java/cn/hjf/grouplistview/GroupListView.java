package cn.hjf.grouplistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 带标签显示的ListView
 * Created by huangjinfu on 2017/2/21.
 */

public class GroupListView extends ListView {

    private static final String TAG = "GroupListView";

    private boolean showEmptyGroup = false;
    LabelDataAdapter labelDataAdapter;
    OnGroupAndDataClickListener onGroupAndDataClickListener;

    public interface OnGroupAndDataClickListener {
        void onGroupClick(int group);
        void onDataClick(int group, int position);
    }

    public interface LabelDataAdapter {

        /**
         * 获取组视图
         * @param group
         * @param convertView
         * @param parent
         * @return
         */
        View getGroupView(int group, View convertView, ViewGroup parent);

        /**
         * 获取数据视图
         * @param group
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        View getDataView(int group, int position, View convertView, ViewGroup parent);

        /**
         * 获取分组数量
         * @return
         */
        int getGroupCount();

        /**
         * 获取该分组下的数据条数
         * @param groupIndex   从 0 到 {@link LabelDataAdapter#getGroupCount() - 1}
         * @return
         */
        int getDataCount(int groupIndex);

    }

    public GroupListView(Context context) {
        super(context);
    }

    public GroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        throw new UnsupportedOperationException("use setLabelAdapter() and setDataAdapter() instead");
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        throw new UnsupportedOperationException("use setOnGroupAndDataClickListener() instead");
    }

    public void setOnGroupAndDataClickListener(OnGroupAndDataClickListener onGroupAndDataClickListener) {
        this.onGroupAndDataClickListener = onGroupAndDataClickListener;
        InnerClickListener innerClickListener = new InnerClickListener();
        super.setOnItemClickListener(innerClickListener);
    }

    public void setAdapter(LabelDataAdapter adapter) {
        labelDataAdapter = adapter;
        InnerAdapter innerAdapter = new InnerAdapter();
        super.setAdapter(innerAdapter);
    }

    private class InnerClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int[] groupAndPosition = getGroupAndPosition(position);
            if (groupAndPosition[1] == -1) {
                onGroupAndDataClickListener.onGroupClick(groupAndPosition[0]);
            } else {
                onGroupAndDataClickListener.onDataClick(groupAndPosition[0], groupAndPosition[1]);
            }
        }
    }


    private class InnerAdapter extends BaseAdapter {

        private static final int TYPE_GROUP = 0;
        private static final int TYPE_DATA = 1;

        @Override
        public int getCount() {
            int count = 0;

            Map<Integer, Integer> groupAndSize = getGroupAndSize();
            for (Map.Entry<Integer, Integer> e :
                    groupAndSize.entrySet()) {
                count += e.getValue() + 1;
            }
            Log.e(TAG, "count : " + count);
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int[] groupAndPosition = getGroupAndPosition(position);
            if (isGroup(groupAndPosition)) {
                return labelDataAdapter.getGroupView(groupAndPosition[0],
                        getItemViewType(position) == TYPE_GROUP ? convertView : null,
                        parent);
            } else {

                int lastCount = 0;

//                Map<Integer, Integer> groupAndSize = getGroupAndSize();
//                for (Map.Entry<Integer, Integer> e : groupAndSize.entrySet()) {
//                    if (e.getKey() >= groupAndPosition[0]) {
//                        break;
//                    }
//                    lastCount += e.getValue() + 1;
//                }

                return labelDataAdapter.getDataView(groupAndPosition[0],
                        groupAndPosition[1],
                        getItemViewType(position) == TYPE_DATA ? convertView : null,
                        parent);
            }

        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            int[] groupAndPosition = getGroupAndPosition(position);
            if (groupAndPosition[1] == -1) {
                return TYPE_GROUP;
            } else {
                return TYPE_DATA;
            }
        }

    }

//    /**
//     * 计算分组和组内位置
//     * @param position
//     * @return
//     */
//    public int[] getGroupAndPosition(int position, Map<Integer, Integer> getGroupAndSize) {
//        //清除空分组
//        if (!showEmptyGroup) {
////            Log.e(TAG, "清除空分组");
//            List<Integer> emptyGroup = new ArrayList<>();
//            for (Map.Entry<Integer, Integer> e: getGroupAndSize.entrySet()) {
//                if (e.getValue() == 0) {
//                    emptyGroup.add(e.getKey());
//                }
//            }
//            for (int i = 0; i < emptyGroup.size(); i++) {
//                getGroupAndSize.remove(emptyGroup.get(i));
//            }
//        }
//
//
//        int[] result = new int[]{-1, -1};
//
//        int offset = 0;
//        int startIndex = 0;
//        int endIndex = 0;
////        Map<Integer, Integer> getGroupAndSize = getGroupAndSize();
//        for (Map.Entry<Integer, Integer> e : getGroupAndSize.entrySet()) {
//            startIndex = offset;
//            endIndex = offset + e.getValue();
//
//                if (position == startIndex) {
//                    result[0] = e.getKey();
//                    break;
//                }
//                if (position > startIndex && position <= endIndex) {
//                    result[0] = e.getKey();
//                    result[1] = position - offset - 1;
//                    break;
//                }
//
//            offset += e.getValue() + 1;
//        }
//
////        Log.e(TAG, "计算分组和位置 position : " + position + " -> group : " + result[0] + " , position : " + result[1]);
//
//        return result;
//    }

    /**
     * 计算分组和组内位置
     * @param position
     * @return
     */
    public int[] getGroupAndPosition(int position) {
        int[] result = new int[]{-1, -1};

        int offset = 0;
        int startIndex;
        int endIndex;
        Map<Integer, Integer> getGroupAndSize = getGroupAndSize();
        for (Map.Entry<Integer, Integer> e : getGroupAndSize.entrySet()) {
            startIndex = offset;
            endIndex = offset + e.getValue();

            if (position == startIndex) {
                result[0] = e.getKey();
                break;
            }
            if (position > startIndex && position <= endIndex) {
                result[0] = e.getKey();
                result[1] = position - offset - 1;
                break;
            }

            offset += e.getValue() + 1;
        }

        Log.e(TAG, "计算分组和位置 position : " + position + " -> group : " + result[0] + " , position : " + result[1]);

        return result;
    }

    /**
     * 计算分组和每一组的大小
     * @return
     */
    private Map<Integer, Integer> getGroupAndSize() {
        Map<Integer, Integer> groupAndSize = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i = 0; i < labelDataAdapter.getGroupCount(); i++) {
            groupAndSize.put(i, labelDataAdapter.getDataCount(i));
        }
        //清除空分组
        if (!showEmptyGroup) {
            Log.e(TAG, "清除空分组");
            List<Integer> emptyGroup = new ArrayList<>();
            for (Map.Entry<Integer, Integer> e: groupAndSize.entrySet()) {
                if (e.getValue() == 0) {
                    emptyGroup.add(e.getKey());
                }
            }
            for (int i = 0; i < emptyGroup.size(); i++) {
                groupAndSize.remove(emptyGroup.get(i));
            }
        }
        Log.e(TAG, groupAndSize.toString());
        return groupAndSize;
    }

    public void setShowEmptyGroup(boolean showEmptyGroup) {
        this.showEmptyGroup = showEmptyGroup;
    }

    private boolean isGroup(int[] groupAndPosition) {
        return groupAndPosition[1] == -1;
    }

}
