package org.base.sort;
/***
 * 从一组数据中找出前100个最大的数
 * 可以使用 PriorityQueue 来实现
 * 1. 创建一个大小为 100 的最小堆。
 * 2. 遍历数组中的每个元素。
 * 3. 如果堆的大小小于 100，将元素插入堆中。
 * 4. 如果堆的大小等于 100，将元素与堆顶元素进行比较。
 * 5. 如果元素大于堆顶元素，将堆顶元素移除，将新元素插入堆中。
 * 6. 重复步骤 3-5，直到遍历完整个数组。
 * 7. 堆中剩下的元素即为前 100 个最大的数。

 * PriorityQueue<Integer> minHeap = new PriorityQueue<>(100);
 * PriorityQueue 中的元素是按照自然顺序（升序）排列的。
 * 因此，堆顶元素就是最小的元素。
 * 当我们需要找出前 100 个最大的数时，我们可以将元素插入堆中，
 * 当堆的大小超过 100 时，将堆顶元素移除，
 * 这样堆中剩下的元素就是前 100 个最大的数。
 * 同时也可使改变为最小堆
 * PriorityQueue<Integer> minHeap = new PriorityQueue<>(100, Collections.reverseOrder());

 * 组要的方法是 peek(), offer() 和 poll()  方法
 * offer() 方法用于将元素插入堆中，
 * poll() 方法用于移除堆顶元素。
 * peek() 方法用于查看堆顶元素，但不会将其从堆中移除。

 * Comparator.naturalOrder() 和 Comparator.reverseOrder() 是 Java 8 中提供的两个比较器。
 * Comparator.naturalOrder() 返回一个比较器，该比较器按照自然顺序（升序）对元素进行排序。
 * Comparator.reverseOrder() 返回一个比较器，该比较器按照自然顺序（降序）对元素进行排序。
 *
 */