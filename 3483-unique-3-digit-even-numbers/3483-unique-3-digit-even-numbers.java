class Solution {
    public int totalNumbers(int[] digits) {
        int[] count = new int[10];

        for (int d : digits) {
            count[d]++;
        }

        int ans = 0;

        for (int num = 100; num <= 998; num += 2) {
            int n = num;
            int a = n / 100;
            int b = (n / 10) % 10;
            int c = n % 10;

            int[] used = new int[10];
            used[a]++;
            used[b]++;
            used[c]++;

            boolean possible = true;

            for (int i = 0; i < 10; i++) {
                if (used[i] > count[i]) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                ans++;
            }
        }

        return ans;
    }
}