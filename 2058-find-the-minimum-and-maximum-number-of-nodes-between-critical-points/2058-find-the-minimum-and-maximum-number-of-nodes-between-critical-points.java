class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int[] ans = {-1, -1};

        if (head == null || head.next == null || head.next.next == null) {
            return ans;
        }

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;
        int first = -1;
        int prevCritical = -1;
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = 0;

        while (curr.next != null) {
            int prevVal = prev.val;
            int currVal = curr.val;
            int nextVal = curr.next.val;

            if ((currVal > prevVal && currVal > nextVal) ||
                (currVal < prevVal && currVal < nextVal)) {

                if (first == -1) {
                    first = index;
                } else {
                    minDistance = Math.min(minDistance, index - prevCritical);
                    maxDistance = index - first;
                }

                prevCritical = index;
            }

            prev = curr;
            curr = curr.next;
            index++;
        }

        if (first == -1 || first == prevCritical) {
            return ans;
        }

        return new int[]{minDistance, maxDistance};
    }
}