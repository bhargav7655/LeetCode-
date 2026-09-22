import java.util.*;

class Solution {
    int n, k;
    int[] nums;
    int[][] tree;
    int[] product;

    void build(int node, int l, int r) {
        if (l == r) {
            int rem = nums[l] % k;
            tree[node][rem] = 1;
            product[node] = rem;
            return;
        }

        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);
        merge(node);
    }

    void merge(int node) {
        int left = node * 2;
        int right = node * 2 + 1;

        for (int r = 0; r < k; r++) {
            tree[node][r] = tree[left][r];
        }

        for (int r = 0; r < k; r++) {
            int nr = (product[left] * r) % k;
            tree[node][nr] += tree[right][r];
        }

        product[node] = (product[left] * product[right]) % k;
    }

    void update(int node, int l, int r, int idx, int value) {
        if (l == r) {
            Arrays.fill(tree[node], 0);
            int rem = value % k;
            tree[node][rem] = 1;
            product[node] = rem;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, value);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, value);
        }

        merge(node);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return new Node(product[node], tree[node]);
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        int[] cnt = new int[k];

        for (int i = 0; i < k; i++) {
            cnt[i] = left.count[i];
        }

        for (int i = 0; i < k; i++) {
            int nr = (left.product * i) % k;
            cnt[nr] += right.count[i];
        }

        int prod = (left.product * right.product) % k;

        return new Node(prod, cnt);
    }

    static class Node {
        int product;
        int[] count;

        Node(int product, int[] count) {
            this.product = product;
            this.count = count.clone();
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.nums = nums;
        this.k = k;
        this.n = nums.length;

        tree = new int[4 * n][k];
        product = new int[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            nums[index] = value;
            update(1, 0, n - 1, index, value);

            Node ans = query(1, 0, n - 1, start, n - 1);

            result[q] = ans.count[x];
        }

        return result;
    }
}