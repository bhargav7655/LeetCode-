import java.util.*;

class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;

        if (n <= 2) {
            return n;
        }

        int answer = 2;

        for (int i = 0; i < n; i++) {
            Map<String, Integer> map = new HashMap<>();
            int duplicates = 0;
            int currentMax = 0;

            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                if (dx == 0 && dy == 0) {
                    duplicates++;
                    continue;
                }

                int gcd = gcd(Math.abs(dx), Math.abs(dy));

                dx /= gcd;
                dy /= gcd;

                if (dx < 0) {
                    dx = -dx;
                    dy = -dy;
                } else if (dx == 0) {
                    dy = 1;
                } else if (dy == 0) {
                    dx = 1;
                }

                String slope = dx + "/" + dy;

                map.put(slope, map.getOrDefault(slope, 0) + 1);
                currentMax = Math.max(currentMax, map.get(slope));
            }

            answer = Math.max(answer, currentMax + duplicates + 1);
        }

        return answer;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}