
class Solution {
    static final long MOD = 1000000007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int size = 1;

        while (size < n) {
            size <<= 1;
        }

        long[] sum = new long[size * 2];
        long[] square = new long[size * 2];
        long[] lazy = new long[size * 2];

        int[] last = new int[100001];
        long answer = 0;

        for (int i = 0; i < n; i++) {
            int value = nums[i];
            int left = last[value];
            last[value] = i + 1;

            update(1, 0, size - 1, left, i, 1, sum, square, lazy);

            answer = (answer + square[1]) % MOD;
        }

        return (int) answer;
    }

    private void update(int node, int start, int end, int l, int r,
                        long val, long[] sum, long[] square, long[] lazy) {
        if (l > end || r < start) {
            return;
        }

        if (l <= start && end <= r) {
            long length = end - start + 1L;
            square[node] = (square[node] + 2 * val * sum[node]
                    + length * val * val) % MOD;
            sum[node] = (sum[node] + length * val) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
            return;
        }

        push(node, start, end, sum, square, lazy);

        int mid = (start + end) / 2;
        update(node * 2, start, mid, l, r, val, sum, square, lazy);
        update(node * 2 + 1, mid + 1, end, l, r, val, sum, square, lazy);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        square[node] = (square[node * 2] + square[node * 2 + 1]) % MOD;
    }

    private void push(int node, int start, int end,
                      long[] sum, long[] square, long[] lazy) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = (start + end) / 2;

        apply(node * 2, mid - start + 1L, lazy[node],
                sum, square, lazy);
        apply(node * 2 + 1, end - mid, lazy[node],
                sum, square, lazy);

        lazy[node] = 0;
    }

    private void apply(int node, long length, long val,
                       long[] sum, long[] square, long[] lazy) {
        square[node] = (square[node] + 2 * val * sum[node]
                + length * val * val) % MOD;
        sum[node] = (sum[node] + length * val) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }
}
