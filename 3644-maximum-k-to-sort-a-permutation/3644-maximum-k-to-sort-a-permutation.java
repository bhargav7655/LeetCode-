class Solution {
    public int sortPermutation(int[] nums) {
        int n = nums.length;
        boolean sorted = true;

        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                sorted = false;
                break;
            }
        }

        if (sorted) {
            return 0;
        }

        int ans = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                ans &= nums[i];
                ans &= i;
            }
        }

        return ans;
    }
}