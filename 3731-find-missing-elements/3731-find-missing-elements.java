import java.util.*;

class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        Arrays.sort(nums);

        List<Integer> result = new ArrayList<>();

        int min = nums[0];
        int max = nums[nums.length - 1];

        int index = 0;

        for (int num = min; num <= max; num++) {
            if (nums[index] == num) {
                index++;
            } else {
                result.add(num);
            }
        }

        return result;
    }
}