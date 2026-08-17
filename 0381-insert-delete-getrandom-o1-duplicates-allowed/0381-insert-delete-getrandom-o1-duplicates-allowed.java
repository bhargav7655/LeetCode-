class RandomizedCollection {

    ArrayList<Integer> list;
    HashMap<Integer, HashSet<Integer>> map;

    public RandomizedCollection() {
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    public boolean insert(int val) {
        boolean first = !map.containsKey(val);

        map.putIfAbsent(val, new HashSet<>());
        map.get(val).add(list.size());
        list.add(val);

        return first;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val) || map.get(val).isEmpty()) {
            return false;
        }

        int removeIndex = map.get(val).iterator().next();
        int lastIndex = list.size() - 1;
        int lastValue = list.get(lastIndex);

        map.get(val).remove(removeIndex);

        if (removeIndex != lastIndex) {
            list.set(removeIndex, lastValue);

            map.get(lastValue).remove(lastIndex);
            map.get(lastValue).add(removeIndex);
        }

        list.remove(lastIndex);

        if (map.get(val).isEmpty()) {
            map.remove(val);
        }

        return true;
    }

    public int getRandom() {
        return list.get((int) (Math.random() * list.size()));
    }
}