class Solution {
    private int[][][] memo;
    private int m, n;
    private int[][] grid;

    private int resolve(int i, int j, int dir) {
        if (i >= m || j >= n) {
            return -1;
        }

        if (memo[i][j][dir] != -2) {
            return memo[i][j][dir];
        }

        if (grid[i][j] == 0) {
            return memo[i][j][dir] = i * n + j;
        }

        if (dir == 0) {
            return memo[i][j][dir] = resolve(i + 1, j, 1);
        } else {
            return memo[i][j][dir] = resolve(i, j + 1, 0);
        }
    }

    public int uniquePaths(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;

        int MOD = 1_000_000_007;

        memo = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j][0] = -2;
                memo[i][j][1] = -2;
            }
        }

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0 || grid[i][j] == 1) {
                    continue;
                }

                int right = resolve(i, j + 1, 0);

                if (right != -1) {
                    int x = right / n;
                    int y = right % n;

                    dp[x][y] = (dp[x][y] + dp[i][j]) % MOD;
                }

                int down = resolve(i + 1, j, 1);

                if (down != -1) {
                    int x = down / n;
                    int y = down % n;

                    dp[x][y] = (dp[x][y] + dp[i][j]) % MOD;
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}