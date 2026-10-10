
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] freq = new int[100001];
        long operations = (long) k1 + k2;
        int maxDiff = 0;

        for (int i = 0; i < n; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);
            freq[diff]++;
            maxDiff = Math.max(maxDiff, diff);
        }

        for (int d = maxDiff; d > 0 && operations > 0; d--) {
            if (freq[d] == 0) {
                continue;
            }

            long count = Math.min((long) freq[d], operations);
            freq[d] -= (int) count;
            freq[d - 1] += (int) count;
            operations -= count;
        }

        long result = 0;

        for (int d = 1; d <= 100000; d++) {
            result += (long) d * d * freq[d];
        }

        return result;
    }
}
