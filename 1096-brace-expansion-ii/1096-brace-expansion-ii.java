import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression, new int[]{0});
        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);
        return ans;
    }

    private Set<String> parse(String s, int[] i) {
        Set<String> result = new HashSet<>();
        Set<String> current = new HashSet<>();
        current.add("");

        while (i[0] < s.length() && s.charAt(i[0]) != '}') {
            char c = s.charAt(i[0]);

            if (c == ',') {
                result.addAll(current);
                current = new HashSet<>();
                current.add("");
                i[0]++;
            } else {
                Set<String> next;

                if (c == '{') {
                    i[0]++;
                    next = parse(s, i);
                    i[0]++;
                } else {
                    next = new HashSet<>();
                    next.add(String.valueOf(c));
                    i[0]++;
                }

                Set<String> combined = new HashSet<>();

                for (String a : current) {
                    for (String b : next) {
                        combined.add(a + b);
                    }
                }

                current = combined;
            }
        }

        result.addAll(current);
        return result;
    }
}