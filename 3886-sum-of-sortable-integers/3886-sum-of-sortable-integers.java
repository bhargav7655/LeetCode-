import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            for (int start = 0; start < n && valid; start += k) {
                int[] pattern = Arrays.copyOfRange(sorted, start, start + k);
                int[] block = Arrays.copyOfRange(nums, start, start + k);

                if (!isRotation(block, pattern)) {
                    valid = false;
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }

    private boolean isRotation(int[] a, int[] b) {
        int n = a.length;
        int[] lps = new int[n];

        for (int i = 1, len = 0; i < n;) {
            if (b[i] == b[len]) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                i++;
            }
        }

        for (int i = 0, j = 0; i < 2 * n - 1; i++) {
            int value = a[i % n];

            while (j > 0 && value != b[j]) {
                j = lps[j - 1];
            }

            if (value == b[j]) {
                j++;
            }

            if (j == n) {
                return true;
            }
        }

        return false;
    }
}