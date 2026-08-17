class RangeModule {

    TreeMap<Integer, Integer> map;

    public RangeModule() {
        map = new TreeMap<>();
    }

    public void addRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = map.floorEntry(left);

        if (entry != null && entry.getValue() >= left) {
            left = entry.getKey();
            right = Math.max(right, entry.getValue());
            map.remove(entry.getKey());
        }

        entry = map.ceilingEntry(left);

        while (entry != null && entry.getKey() <= right) {
            right = Math.max(right, entry.getValue());
            map.remove(entry.getKey());
            entry = map.ceilingEntry(left);
        }

        map.put(left, right);
    }

    public boolean queryRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = map.floorEntry(left);

        return entry != null && entry.getValue() >= right;
    }

    public void removeRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = map.floorEntry(left);

        if (entry != null && entry.getValue() > left) {
            int start = entry.getKey();
            int end = entry.getValue();

            map.remove(start);

            if (start < left) {
                map.put(start, left);
            }

            if (end > right) {
                map.put(right, end);
                return;
            }
        }

        entry = map.ceilingEntry(left);

        while (entry != null && entry.getKey() < right) {
            int start = entry.getKey();
            int end = entry.getValue();

            map.remove(start);

            if (end > right) {
                map.put(right, end);
                break;
            }

            entry = map.ceilingEntry(left);
        }
    }
}