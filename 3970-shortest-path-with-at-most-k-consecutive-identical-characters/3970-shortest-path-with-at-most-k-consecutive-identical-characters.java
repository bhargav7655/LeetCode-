
import java.util.*;

class Solution {
    int size;
    long[] heap;

    void push(long x) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }

        int i = size++;

        while (i > 0) {
            int p = (i - 1) / 2;
            if (heap[p] <= x) break;
            heap[i] = heap[p];
            i = p;
        }

        heap[i] = x;
    }

    long pop() {
        long result = heap[0];
        long x = heap[--size];

        int i = 0;

        while (2 * i + 1 < size) {
            int c = 2 * i + 1;

            if (c + 1 < size && heap[c + 1] < heap[c]) {
                c++;
            }

            if (heap[c] >= x) break;

            heap[i] = heap[c];
            i = c;
        }

        if (size > 0) heap[i] = x;

        return result;
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {
        int m = edges.length;

        int[] head = new int[n];
        int[] to = new int[m];
        int[] weight = new int[m];
        int[] next = new int[m];

        Arrays.fill(head, -1);

        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            to[i] = edges[i][1];
            weight[i] = edges[i][2];
            next[i] = head[u];
            head[u] = i;
        }

        int[][] dist = new int[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        int states = n * (k + 1);

        heap = new long[1024];
        size = 0;

        dist[0][1] = 0;
        push(1);

        while (size > 0) {
            long val = pop();

            int cost = (int) (val / states);
            int state = (int) (val % states);

            int node = state / (k + 1);
            int count = state % (k + 1);

            if (cost != dist[node][count]) continue;

            if (node == n - 1) return cost;

            for (int e = head[node]; e != -1; e = next[e]) {
                int v = to[e];
                int w = weight[e];

                int newCount = labels.charAt(node) == labels.charAt(v)
                    ? count + 1 : 1;

                if (newCount > k) continue;

                int newCost = cost + w;

                if (newCost < dist[v][newCount]) {
                    dist[v][newCount] = newCost;

                    long newState = (long) v * (k + 1) + newCount;
                    push((long) newCost * states + newState);
                }
            }
        }

        return -1;
    }
}
