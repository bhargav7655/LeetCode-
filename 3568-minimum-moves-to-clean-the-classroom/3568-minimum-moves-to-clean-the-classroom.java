import java.util.*;

class Solution {
    static class State {
        int pos;
        int mask;
        int energy;

        State(int pos, int mask, int energy) {
            this.pos = pos;
            this.mask = mask;
            this.energy = energy;
        }
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0;
        int sc = 0;
        int litterCount = 0;

        int[][] id = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(id[i], -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    id[i][j] = litterCount++;
                }
            }
        }

        if (litterCount == 0) {
            return 0;
        }

        int fullMask = (1 << litterCount) - 1;

        int[][][] best = new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(best[i][j], -1);
            }
        }

        ArrayDeque<State> queue = new ArrayDeque<>();

        best[sr][sc][0] = energy;
        queue.offer(new State(sr * n + sc, 0, energy));

        int[][] dirs = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                State cur = queue.poll();

                int r = cur.pos / n;
                int c = cur.pos % n;

                if (cur.mask == fullMask) {
                    return moves;
                }

                if (cur.energy == 0) {
                    continue;
                }

                if (best[r][c][cur.mask] > cur.energy) {
                    continue;
                }

                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    char cell = classroom[nr].charAt(nc);

                    if (cell == 'X') {
                        continue;
                    }

                    int newEnergy = cur.energy - 1;
                    int newMask = cur.mask;

                    if (cell == 'R') {
                        newEnergy = energy;
                    }

                    if (id[nr][nc] != -1) {
                        newMask |= 1 << id[nr][nc];
                    }

                    if (newEnergy > best[nr][nc][newMask]) {
                        best[nr][nc][newMask] = newEnergy;
                        queue.offer(
                            new State(nr * n + nc, newMask, newEnergy)
                        );
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}