class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int score = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(score);
                score = 0;
            } else {
                int inside = score == 0 ? 1 : 2 * score;
                score = stack.pop() + inside;
            }
        }

        return score;
    }
}