class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        int[] prefix = new int[n];
        prefix[0] = stones[0];

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        int ans = prefix[n - 1];

        for (int i = n - 2; i >= 1; i--) {
            ans = Math.max(ans, prefix[i] - ans);
        }

        return ans;
    }
}