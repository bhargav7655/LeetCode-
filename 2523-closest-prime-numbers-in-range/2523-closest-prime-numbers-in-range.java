class Solution {
    public int[] closestPrimes(int left, int right) {
        boolean[] isPrime = new boolean[right + 1];

        for (int i = 2; i <= right; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= right; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= right; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int prev = -1;
        int first = -1;
        int second = -1;
        int minGap = Integer.MAX_VALUE;

        for (int i = Math.max(2, left); i <= right; i++) {
            if (isPrime[i]) {
                if (prev != -1 && i - prev < minGap) {
                    minGap = i - prev;
                    first = prev;
                    second = i;
                }

                prev = i;
            }
        }

        return new int[]{first, second};
    }
}