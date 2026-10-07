
import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        Set<String> result = new HashSet<>();
        backtrack(s, 0, left, right, 0, new StringBuilder(), result);

        return new ArrayList<>(result);
    }

    private void backtrack(String s, int index, int leftRemove, int rightRemove,
                            int balance, StringBuilder current, Set<String> result) {

        if (index == s.length()) {
            if (leftRemove == 0 && rightRemove == 0 && balance == 0) {
                result.add(current.toString());
            }
            return;
        }

        char c = s.charAt(index);

        if (c == '(') {
            if (leftRemove > 0) {
                backtrack(s, index + 1, leftRemove - 1, rightRemove,
                        balance, current, result);
            }

            current.append(c);
            backtrack(s, index + 1, leftRemove, rightRemove,
                    balance + 1, current, result);
            current.deleteCharAt(current.length() - 1);

        } else if (c == ')') {
            if (rightRemove > 0) {
                backtrack(s, index + 1, leftRemove, rightRemove - 1,
                        balance, current, result);
            }

            if (balance > 0) {
                current.append(c);
                backtrack(s, index + 1, leftRemove, rightRemove,
                        balance - 1, current, result);
                current.deleteCharAt(current.length() - 1);
            }

        } else {
            current.append(c);
            backtrack(s, index + 1, leftRemove, rightRemove,
                    balance, current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}

