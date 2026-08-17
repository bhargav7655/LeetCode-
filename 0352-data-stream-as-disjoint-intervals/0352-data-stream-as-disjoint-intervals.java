import java.util.*;

class SummaryRanges {

    private TreeMap<Integer, int[]> map;

    public SummaryRanges() {
        map = new TreeMap<>();
    }

    public void addNum(int value) {
        Map.Entry<Integer, int[]> left = map.floorEntry(value);
        Map.Entry<Integer, int[]> right = map.ceilingEntry(value);

        if (left != null && left.getValue()[1] >= value) {
            return;
        }

        boolean connectLeft = left != null && left.getValue()[1] + 1 == value;
        boolean connectRight = right != null && right.getKey() - 1 == value;

        if (connectLeft && connectRight) {
            left.getValue()[1] = right.getValue()[1];
            map.remove(right.getKey());
        } else if (connectLeft) {
            left.getValue()[1] = value;
        } else if (connectRight) {
            int end = right.getValue()[1];
            map.remove(right.getKey());
            map.put(value, new int[]{value, end});
        } else {
            map.put(value, new int[]{value, value});
        }
    }

    public int[][] getIntervals() {
        int[][] result = new int[map.size()][2];

        int index = 0;

        for (int[] interval : map.values()) {
            result[index++] = interval;
        }

        return result;
    }
}