class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] ans = new char[s.length()];

        for (int i = 0; i < s.length(); i++) {
            int cur = target.charAt(i) - 'a';

            if (freq[cur] > 0) {
                ans[i] = target.charAt(i);
                freq[cur]--;
            } else {
                for (int j = cur + 1; j < 26; j++) {
                    if (freq[j] > 0) {
                        ans[i] = (char) ('a' + j);
                        freq[j]--;

                        fillSmallest(ans, i + 1, freq);
                        return new String(ans);
                    }
                }

                break;
            }
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            if (ans[i] == 0) {
                continue;
            }

            freq[ans[i] - 'a']++;

            int cur = target.charAt(i) - 'a';

            for (int j = cur + 1; j < 26; j++) {
                if (freq[j] > 0) {
                    ans[i] = (char) ('a' + j);
                    freq[j]--;

                    fillSmallest(ans, i + 1, freq);
                    return new String(ans);
                }
            }
        }

        return "";
    }

    private void fillSmallest(char[] ans, int start, int[] freq) {
        int pos = start;

        for (int i = 0; i < 26; i++) {
            while (freq[i] > 0) {
                ans[pos++] = (char) ('a' + i);
                freq[i]--;
            }
        }
    }
}