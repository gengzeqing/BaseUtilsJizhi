package org.base.sort;

import java.util.*;

/**
 * 如何在10亿个随机整数中找出前1000个最大的数
 *
 * PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);：创建一个大小为 1000 的最小堆。
 * minHeap.offer(num)：向堆中插入元素。
 * minHeap.poll()：移除堆顶元素（即当前堆中最小的元素）。
 * minHeap.peek()：查看堆顶元素（即最小元素）。
 * Collections.sort(top1000, Collections.reverseOrder())：对结果进行降序排序，以便输出时按照从大到小的顺序。
 */
public class Top1000LargestNumbers {
    public static void main(String[] args) {
        // 假设我们有 10 亿个随机整数
        int n = 1000000000;
        int k = 1000;

        // 使用最小堆存储前1000个最大数
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        // 模拟生成 10 亿个随机整数，并找到前 1000 个最大的数
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int num = random.nextInt();  // 生成一个随机整数
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }

        // 输出前1000个最大的数
        System.out.println("Top 1000 Largest Numbers:");
        List<Integer> top1000 = new ArrayList<>(minHeap);
        Collections.sort(top1000, Collections.reverseOrder()); // 降序排列
        for (int num : top1000) {
            System.out.println(num);
        }
    }
}
