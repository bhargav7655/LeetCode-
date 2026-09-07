class Solution {
    public int distinctSubseqII(String s) {
        final int MOD = 1000000007;
        long[] last = new long[26];

        long total = 0;

        for (char c : s.toCharArray()) {
            int idx = c - 'a';

            long newSubseq = (total + 1) % MOD;

            total = (total + newSubseq - last[idx] + MOD) % MOD;

            last[idx] = newSubseq;
        }

        return (int) total;
    }
}