class Solution {
    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        long maxArea2 = 0;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    long area2 = Math.abs(
                        (long) points[i][0] * (points[j][1] - points[k][1])
                        + (long) points[j][0] * (points[k][1] - points[i][1])
                        + (long) points[k][0] * (points[i][1] - points[j][1])
                    );

                    maxArea2 = Math.max(maxArea2, area2);
                }
            }
        }

        return maxArea2 / 2.0;
    }
}