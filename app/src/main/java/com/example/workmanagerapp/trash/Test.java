package com.example.workmanagerapp.trash;

import java.util.Arrays;
import java.util.HashMap;

public class Test {
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            //shift array with lower length to the left
            return intersect(nums2, nums1);
        }
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int n : nums1) {
            //put elements of the array with lower count as key,
            //and an incremented number as value(number of times it occurs)
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }

    public int[] plusOne(int[] digits) {
        int n = digits.length;

        // move along the input array starting from the end
        for (int idx = n - 1; idx >= 0; --idx) {
            // set all the nines at the end of array to zeros
            if (digits[idx] == 9) {
                digits[idx] = 0;
            }
            // here we have the rightmost not-nine
            else {
                // increase this rightmost not-nine by 1
                digits[idx]++;
                // and the job is done
                return digits;
            }
        }
        // we're here because all the digits are nines
        digits = new int[n + 1];
        digits[0] = 1;
        return digits;
    }





    public void twoSum() {
        //save each item into a hashmap in a forloop
        //check if complement of item being saved is not null or it's not equal to 0
        //then return
    }

    public static void main (String [] args) {
        Test test = new Test();

    }
}
