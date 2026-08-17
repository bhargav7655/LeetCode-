class RangeFreqQuery {

    HashMap<Integer, ArrayList<Integer>> map;

    public RangeFreqQuery(int[] arr) {
        map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }
    }

    public int query(int left, int right, int value) {
        if (!map.containsKey(value)) {
            return 0;
        }

        ArrayList<Integer> list = map.get(value);

        int start = lowerBound(list, left);
        int end = upperBound(list, right);

        return end - start;
    }

    private int lowerBound(ArrayList<Integer> list, int target) {
        int low = 0;
        int high = list.size();

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (list.get(mid) < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    private int upperBound(ArrayList<Integer> list, int target) {
        int low = 0;
        int high = list.size();

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (list.get(mid) <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }
}