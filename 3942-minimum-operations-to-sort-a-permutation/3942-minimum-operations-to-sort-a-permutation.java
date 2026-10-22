class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = -1;
        int last = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) zero = i;
            if (nums[i] == n - 1) last = i;
        }

        boolean normal = true;
        boolean reversed = true;

        for (int i = 0; i < n; i++) {
            if (nums[(zero + i) % n] != i) {
                normal = false;
            }

            if (nums[(zero - i + n) % n] != i) {
                reversed = false;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (normal) {
            ans = Math.min(zero, n - zero + 2);
        }

        if (reversed) {
            ans = Math.min(ans, Math.min(last + 1, n - last + 1));
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}