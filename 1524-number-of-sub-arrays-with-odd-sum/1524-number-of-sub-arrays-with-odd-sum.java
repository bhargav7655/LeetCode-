class Solution {
    public int numOfSubarrays(int[] arr) {
        long odd = 0;
        long even = 1;
        long ans = 0;
        long mod = 1000000007;

        int prefix = 0;

        for (int num : arr) {
            prefix += num;

            if (prefix % 2 == 0) {
                ans = (ans + odd) % mod;
                even++;
            } else {
                ans = (ans + even) % mod;
                odd++;
            }
        }

        return (int) ans;
    }
}