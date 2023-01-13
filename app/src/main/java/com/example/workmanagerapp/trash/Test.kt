package com.example.workmanagerapp.trash

import android.os.Build
import java.util.*

class Test2 {
    fun intersect(nums1: IntArray, nums2: IntArray): IntArray? {
        if (nums1.size > nums2.size) {
            return intersect(nums2, nums1)
        }
        val m = HashMap<Int, Int>()
        for (n in nums1) {
            m[n] = m.getOrDefault(n, 0) + 1
        }
        var k = 0
        for (n in nums2) {
            val cnt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                m.getOrDefault(n, 0)
            } else {
                TODO("VERSION.SDK_INT < N")
            }
            if (cnt > 0) {
                nums1[k++] = n
                m[n] = cnt - 1
            }
        }
        return nums1.copyOfRange(0, k)
    }
}

fun main(args: Array<String>) {
    val test = Test2()
    print(test.intersect(intArrayOf(1,1), intArrayOf(1,1,1)))
}