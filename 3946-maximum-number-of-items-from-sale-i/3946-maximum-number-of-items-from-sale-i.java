class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] count = new int[1501];

        for (int[] item : items) {
            count[item[0]]++;
        }

        int[] multiples = new int[1501];

        for (int f = 1; f <= 1500; f++) {
            for (int j = f; j <= 1500; j += f) {
                multiples[f] += count[j];
            }
        }

        int[] dp = new int[budget + 1];
        int[] next = new int[budget + 1];

        for (int[] item : items) {
            int price = item[1];
            int bonus = multiples[item[0]] - 1;

            System.arraycopy(dp, 0, next, 0, budget + 1);

            for (int r = 0; r < price && r <= budget; r++) {
                int best = Integer.MIN_VALUE;

                for (int b = r; b <= budget; b += price) {
                    if (b >= price) {
                        best = Math.max(best,
                            dp[b - price] - (b - price) / price);
                    }

                    if (best != Integer.MIN_VALUE) {
                        next[b] = Math.max(next[b],
                            best + bonus + b / price);
                    }
                }
            }

            int[] temp = dp;
            dp = next;
            next = temp;
        }

        int ans = 0;

        for (int value : dp) {
            ans = Math.max(ans, value);
        }

        return ans;
    }
}