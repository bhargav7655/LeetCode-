class Solution {
    static final int INF = 1_000_000;

    int need2, need3, need5, need7;
    int[][] dp;

    public String smallestNumber(String num, long t) {
        int[] factors = factorize(t);

        if (factors == null) {
            return "-1";
        }

        need2 = factors[0];
        need3 = factors[1];
        need5 = factors[2];
        need7 = factors[3];

        buildDP();

        int n = num.length();

        int[][] prefix = new int[n + 1][4];
        boolean[] zero = new boolean[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i].clone();

            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                zero[i + 1] = true;
            } else {
                add(prefix[i + 1], digit);
            }

            zero[i + 1] |= zero[i];
        }

        if (!zero[n] && satisfies(prefix[n])) {
            return num;
        }

        for (int i = n - 1; i >= 0; i--) {
            if (zero[i]) {
                continue;
            }

            int current = num.charAt(i) - '0';

            for (int digit = current + 1; digit <= 9; digit++) {
                int[] have = prefix[i].clone();
                add(have, digit);

                int[] required = getRequired(have);
                int remaining = n - i - 1;

                if (minDigits(required) <= remaining) {
                    StringBuilder ans = new StringBuilder();
                    ans.append(num, 0, i);
                    ans.append(digit);
                    ans.append(buildSuffix(required, remaining));

                    return ans.toString();
                }
            }
        }

        int[] required = {need2, need3, need5, need7};

        int length = Math.max(n + 1, minDigits(required));

        return buildSuffix(required, length);
    }

    private int[] factorize(long t) {
        int[] result = new int[4];
        int[] primes = {2, 3, 5, 7};

        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                result[i]++;
                t /= primes[i];
            }
        }

        if (t != 1) {
            return null;
        }

        return result;
    }

    private void buildDP() {
        dp = new int[need2 + 1][need3 + 1];

        for (int i = 0; i <= need2; i++) {
            for (int j = 0; j <= need3; j++) {
                dp[i][j] = INF;
            }
        }

        dp[0][0] = 0;

        int[][] factors = {
            {1, 0},
            {0, 1},
            {2, 0},
            {1, 1},
            {3, 0},
            {0, 2}
        };

        for (int i = 0; i <= need2; i++) {
            for (int j = 0; j <= need3; j++) {
                if (dp[i][j] == INF) {
                    continue;
                }

                for (int[] f : factors) {
                    int ni = Math.min(need2, i + f[0]);
                    int nj = Math.min(need3, j + f[1]);

                    dp[ni][nj] = Math.min(dp[ni][nj], dp[i][j] + 1);
                }
            }
        }
    }

    private int minDigits(int[] required) {
        return dp[required[0]][required[1]]
                + required[2]
                + required[3];
    }

    private int[] getRequired(int[] have) {
        return new int[] {
            Math.max(0, need2 - have[0]),
            Math.max(0, need3 - have[1]),
            Math.max(0, need5 - have[2]),
            Math.max(0, need7 - have[3])
        };
    }

    private boolean satisfies(int[] have) {
        return have[0] >= need2 &&
               have[1] >= need3 &&
               have[2] >= need5 &&
               have[3] >= need7;
    }

    private String buildSuffix(int[] required, int length) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int remaining = length - i - 1;

            for (int digit = 1; digit <= 9; digit++) {
                int[] next = required.clone();
                subtract(next, digit);

                if (minDigits(next) <= remaining) {
                    result.append(digit);
                    required = next;
                    break;
                }
            }
        }

        return result.toString();
    }

    private void add(int[] count, int digit) {
        switch (digit) {
            case 2:
                count[0]++;
                break;
            case 3:
                count[1]++;
                break;
            case 4:
                count[0] += 2;
                break;
            case 5:
                count[2]++;
                break;
            case 6:
                count[0]++;
                count[1]++;
                break;
            case 7:
                count[3]++;
                break;
            case 8:
                count[0] += 3;
                break;
            case 9:
                count[1] += 2;
                break;
        }
    }

    private void subtract(int[] required, int digit) {
        switch (digit) {
            case 2:
                required[0] = Math.max(0, required[0] - 1);
                break;
            case 3:
                required[1] = Math.max(0, required[1] - 1);
                break;
            case 4:
                required[0] = Math.max(0, required[0] - 2);
                break;
            case 5:
                required[2] = Math.max(0, required[2] - 1);
                break;
            case 6:
                required[0] = Math.max(0, required[0] - 1);
                required[1] = Math.max(0, required[1] - 1);
                break;
            case 7:
                required[3] = Math.max(0, required[3] - 1);
                break;
            case 8:
                required[0] = Math.max(0, required[0] - 3);
                break;
            case 9:
                required[1] = Math.max(0, required[1] - 2);
                break;
        }
    }
}