class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = Integer.MAX_VALUE;
        int minOdd = Integer.MAX_VALUE;

        for (int x : nums1) {
            min = Math.min(min, x);

            if ((x & 1) == 1) {
                minOdd = Math.min(minOdd, x);
            }
        }

        for (int x : nums1) {
            if ((x & 1) != (min & 1)) {
                if (minOdd >= x) {
                    return false;
                }
            }
        }

        return true;
    }
}