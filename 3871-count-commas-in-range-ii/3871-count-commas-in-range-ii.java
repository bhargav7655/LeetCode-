class Solution {
    public long countCommas(long n) {
        long total = 0;
        long start = 1000;
        long commas = 1;

        while (start <= n) {
            long end = start * 1000 - 1;
            long count = Math.min(n, end) - start + 1;
            total += count * commas;

            if (start > n / 1000) {
                break;
            }

            start *= 1000;
            commas++;
        }

        return total;
    }
}