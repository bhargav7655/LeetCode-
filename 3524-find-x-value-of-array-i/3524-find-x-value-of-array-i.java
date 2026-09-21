class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] dp = new long[k];
        long[] next = new long[k];
        long[] ans = new long[k];

        for (int num : nums) {
            for (int r = 0; r < k; r++) {
                next[r] = 0;
            }

            int val = num % k;

            next[val]++;

            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    int nr = (r * val) % k;
                    next[nr] += dp[r];
                }
            }

            for (int r = 0; r < k; r++) {
                dp[r] = next[r];
                ans[r] += dp[r];
            }
        }

        return ans;
    }
}