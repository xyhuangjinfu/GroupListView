package cn.hjf.grouplistview;

import org.junit.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertArrayEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GroupListViewTest {
    @Test
    public void showEmpty_HaveEmpty() throws Exception {
        GroupListView groupListView = new GroupListView(null);
        groupListView.setShowEmptyGroup(true);

        Map<Integer, Integer> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        map.put(0, 3);
        map.put(1, 0);
        map.put(2, 3);
        map.put(3, 2);

        assertArrayEquals(groupListView.getGroupAndPosition(0, map), new int[]{0, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(1, map), new int[]{0, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(2, map), new int[]{0, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(3, map), new int[]{0, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(4, map), new int[]{1, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(5, map), new int[]{2, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(6, map), new int[]{2, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(7, map), new int[]{2, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(8, map), new int[]{2, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(9, map), new int[]{3, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(10, map), new int[]{3, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(11, map), new int[]{3, 1});
    }

    @Test
    public void showEmpty_NoEmpty() throws Exception {
        GroupListView groupListView = new GroupListView(null);
        groupListView.setShowEmptyGroup(true);

        Map<Integer, Integer> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        map.put(0, 3);
        map.put(1, 4);
        map.put(2, 2);

        assertArrayEquals(groupListView.getGroupAndPosition(0, map), new int[]{0, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(1, map), new int[]{0, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(2, map), new int[]{0, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(3, map), new int[]{0, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(4, map), new int[]{1, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(5, map), new int[]{1, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(6, map), new int[]{1, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(7, map), new int[]{1, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(8, map), new int[]{1, 3});
        assertArrayEquals(groupListView.getGroupAndPosition(9, map), new int[]{2, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(10, map), new int[]{2, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(11, map), new int[]{2, 1});
    }

    @Test
    public void hideEmpty_HaveEmpty() throws Exception {
        GroupListView groupListView = new GroupListView(null);
        groupListView.setShowEmptyGroup(false);

        Map<Integer, Integer> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        map.put(0, 3);
        map.put(1, 0);
        map.put(2, 3);
        map.put(3, 2);

        assertArrayEquals(groupListView.getGroupAndPosition(0, map), new int[]{0, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(1, map), new int[]{0, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(2, map), new int[]{0, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(3, map), new int[]{0, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(4, map), new int[]{2, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(5, map), new int[]{2, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(6, map), new int[]{2, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(7, map), new int[]{2, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(8, map), new int[]{3, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(9, map), new int[]{3, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(10, map), new int[]{3, 1});
    }

    @Test
    public void hideEmpty_NoEmpty() throws Exception {
        GroupListView groupListView = new GroupListView(null);
        groupListView.setShowEmptyGroup(false);

        Map<Integer, Integer> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        map.put(0, 3);
        map.put(1, 4);
        map.put(2, 2);

        assertArrayEquals(groupListView.getGroupAndPosition(0, map), new int[]{0, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(1, map), new int[]{0, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(2, map), new int[]{0, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(3, map), new int[]{0, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(4, map), new int[]{1, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(5, map), new int[]{1, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(6, map), new int[]{1, 1});
        assertArrayEquals(groupListView.getGroupAndPosition(7, map), new int[]{1, 2});
        assertArrayEquals(groupListView.getGroupAndPosition(8, map), new int[]{1, 3});
        assertArrayEquals(groupListView.getGroupAndPosition(9, map), new int[]{2, -1});
        assertArrayEquals(groupListView.getGroupAndPosition(10, map), new int[]{2, 0});
        assertArrayEquals(groupListView.getGroupAndPosition(11, map), new int[]{2, 1});
    }
}