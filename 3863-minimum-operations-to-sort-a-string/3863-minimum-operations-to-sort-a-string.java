
class Solution {
    public int minOperations(String s) {
        int n = s.length();
        char[] a = s.toCharArray();

        boolean sorted = true;
        char mn = a[0];
        char mx = a[0];

        for (int i = 1; i < n; i++) {
            if (a[i] < a[i - 1]) {
                sorted = false;
            }
            mn = (char) Math.min(mn, a[i]);
            mx = (char) Math.max(mx, a[i]);
        }

        if (sorted) return 0;
        if (n == 2) return -1;

        if (a[0] == mn || a[n - 1] == mx) return 1;

        for (int i = 1; i < n - 1; i++) {
            if (a[i] == mn || a[i] == mx) return 2;
        }

        return 3;
    }
}
