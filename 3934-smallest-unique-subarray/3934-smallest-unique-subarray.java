
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] temp = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        for (int k = 1; k < n; k *= 2) {
            final int len = k;
            final int[] currentRank = rank;

            Integer[] order = new Integer[n];

            for (int i = 0; i < n; i++) {
                order[i] = i;
            }

            Arrays.sort(order, (a, b) -> {
                if (currentRank[a] != currentRank[b])
                    return Integer.compare(currentRank[a], currentRank[b]);

                int x = a + len < n ? currentRank[a + len] : -1;
                int y = b + len < n ? currentRank[b + len] : -1;

                return Integer.compare(x, y);
            });

            for (int i = 0; i < n; i++) {
                sa[i] = order[i];
            }

            temp[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                boolean different = rank[a] != rank[b];

                int x = a + len < n ? rank[a + len] : -1;
                int y = b + len < n ? rank[b + len] : -1;

                if (different || x != y)
                    temp[b] = temp[a] + 1;
                else
                    temp[b] = temp[a];
            }

            int[] swap = rank;
            rank = temp;
            temp = swap;

            if (rank[sa[n - 1]] == n - 1)
                break;
        }

        int[] lcp = new int[n];
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        int h = 0;

        for (int i = 0; i < n; i++) {
            int p = pos[i];

            if (p == 0) {
                continue;
            }

            int j = sa[p - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[p] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int left = i > 0 ? lcp[i] : 0;
            int right = i + 1 < n ? lcp[i + 1] : 0;

            int need = Math.max(left, right) + 1;

            if (sa[i] + need <= n) {
                ans = Math.min(ans, need);
            }
        }

        return ans;
    }
}
