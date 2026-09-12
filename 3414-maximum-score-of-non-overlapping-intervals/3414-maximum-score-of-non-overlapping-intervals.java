import java.util.*;

class Solution {
    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] a = new Interval[n];

        for (int i = 0; i < n; i++) {
            a[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        Arrays.sort(a, (x, y) -> {
            if (x.r != y.r) {
                return Integer.compare(x.r, y.r);
            }
            return Integer.compare(x.idx, y.idx);
        });

        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            ends[i] = a[i].r;
        }

        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = i - 1;
            int pos = -1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (ends[mid] < a[i].l) {
                    pos = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            prev[i] = pos;
        }

        long[][] dp = new long[n + 1][5];

        ArrayList<Integer>[][] best =
            new ArrayList[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                best[i][k] = new ArrayList<>();
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= 4; k++) {
                dp[i][k] = dp[i - 1][k];
                best[i][k] = new ArrayList<>(best[i - 1][k]);

                int p = prev[i - 1] + 1;

                long takeScore = dp[p][k - 1] + a[i - 1].w;

                ArrayList<Integer> take =
                    new ArrayList<>(best[p][k - 1]);

                take.add(a[i - 1].idx);
                Collections.sort(take);

                if (takeScore > dp[i][k] ||
                    (takeScore == dp[i][k] &&
                     smaller(take, best[i][k]))) {

                    dp[i][k] = takeScore;
                    best[i][k] = take;
                }
            }
        }

        long maxScore = 0;
        ArrayList<Integer> answer = new ArrayList<>();

        for (int k = 1; k <= 4; k++) {
            if (dp[n][k] > maxScore ||
                (dp[n][k] == maxScore &&
                 smaller(best[n][k], answer))) {

                maxScore = dp[n][k];
                answer = best[n][k];
            }
        }

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    private boolean smaller(
        ArrayList<Integer> a,
        ArrayList<Integer> b
    ) {
        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}