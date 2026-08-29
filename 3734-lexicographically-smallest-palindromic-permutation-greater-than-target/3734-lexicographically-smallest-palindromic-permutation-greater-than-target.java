class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        int mid = -1;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                odd++;
                mid = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        int m = n / 2;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        if (m == 0) {
            String candidate = String.valueOf((char) ('a' + mid));
            return candidate.compareTo(target) > 0 ? candidate : "";
        }

        int[] rem = half.clone();
        char[] equal = new char[m];
        boolean possible = true;

        for (int i = 0; i < m; i++) {
            int x = target.charAt(i) - 'a';

            if (rem[x] == 0) {
                possible = false;
                break;
            }

            equal[i] = target.charAt(i);
            rem[x]--;
        }

        if (possible) {
            String candidate = build(equal, mid, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            int[] available = half.clone();
            boolean valid = true;

            for (int j = 0; j < i; j++) {
                int x = target.charAt(j) - 'a';

                if (available[x] == 0) {
                    valid = false;
                    break;
                }

                available[x]--;
            }

            if (!valid) {
                continue;
            }

            int current = target.charAt(i) - 'a';

            for (int c = current + 1; c < 26; c++) {
                if (available[c] == 0) {
                    continue;
                }

                char[] result = new char[m];

                for (int j = 0; j < i; j++) {
                    result[j] = target.charAt(j);
                }

                result[i] = (char) ('a' + c);

                available[c]--;

                int p = i + 1;

                for (int x = 0; x < 26; x++) {
                    while (available[x] > 0) {
                        result[p++] = (char) ('a' + x);
                        available[x]--;
                    }
                }

                return build(result, mid, n);
            }
        }

        return "";
    }

    private String build(char[] half, int mid, int n) {
        StringBuilder sb = new StringBuilder(n);

        for (char c : half) {
            sb.append(c);
        }

        if ((n & 1) == 1) {
            sb.append((char) ('a' + mid));
        }

        for (int i = half.length - 1; i >= 0; i--) {
            sb.append(half[i]);
        }

        return sb.toString();
    }
}