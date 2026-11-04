import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        long[] cost = new long[n - x + 1];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long leftSum = 0;
        long rightSum = 0;
        int leftSize = 0;
        int rightSize = 0;

        for (int i = 0; i < n; i++) {
            int val = nums[i];

            if (leftSize == 0 || val <= left.lastKey()) {
                left.put(val, left.getOrDefault(val, 0) + 1);
                leftSum += val;
                leftSize++;
            } else {
                right.put(val, right.getOrDefault(val, 0) + 1);
                rightSum += val;
                rightSize++;
            }

            while (leftSize > rightSize + 1) {
                int v = left.lastKey();

                remove(left, v);
                leftSum -= v;
                leftSize--;

                right.put(v, right.getOrDefault(v, 0) + 1);
                rightSum += v;
                rightSize++;
            }

            while (leftSize < rightSize) {
                int v = right.firstKey();

                remove(right, v);
                rightSum -= v;
                rightSize--;

                left.put(v, left.getOrDefault(v, 0) + 1);
                leftSum += v;
                leftSize++;
            }

            if (i >= x) {
                int out = nums[i - x];

                if (left.containsKey(out)) {
                    remove(left, out);
                    leftSum -= out;
                    leftSize--;
                } else {
                    remove(right, out);
                    rightSum -= out;
                    rightSize--;
                }

                while (leftSize > rightSize + 1) {
                    int v = left.lastKey();

                    remove(left, v);
                    leftSum -= v;
                    leftSize--;

                    right.put(v, right.getOrDefault(v, 0) + 1);
                    rightSum += v;
                    rightSize++;
                }

                while (leftSize < rightSize) {
                    int v = right.firstKey();

                    remove(right, v);
                    rightSum -= v;
                    rightSize--;

                    left.put(v, left.getOrDefault(v, 0) + 1);
                    leftSum += v;
                    leftSize++;
                }
            }

            if (i >= x - 1) {
                int start = i - x + 1;
                int median = left.lastKey();

                cost[start] =
                    (long) median * leftSize - leftSum
                    + rightSum - (long) median * rightSize;
            }
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = dp[i - 1][j];

                if (i >= x && dp[i - x][j - 1] != INF) {
                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }

    private void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }
}