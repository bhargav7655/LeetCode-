import java.util.*;

class Solution {
    List<Integer>[] adj;
    int[] parent, depth, size, heavy, head, pos;
    int[] seg;
    int timer;
    char[] chars;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        chars = s.toCharArray();
        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];

        Arrays.fill(heavy, -1);
        dfs(0, -1);
        decompose(0, 0);

        seg = new int[4 * n];
        for (int u = 0; u < n; u++) {
            updateTree(1, 0, n - 1, pos[u], 1 << (chars[u] - 'a'));
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                chars[u] = p[2].charAt(0);
                updateTree(1, 0, n - 1, pos[u], 1 << (chars[u] - 'a'));
            } else {
                int v = Integer.parseInt(p[2]);
                int mask = 0;

                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int t = u;
                        u = v;
                        v = t;
                    }

                    mask ^= queryTree(1, 0, n - 1, pos[head[u]], pos[u]);
                    u = parent[head[u]];
                }

                if (depth[u] > depth[v]) {
                    int t = u;
                    u = v;
                    v = t;
                }

                mask ^= queryTree(1, 0, n - 1, pos[u], pos[v]);
                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    int dfs(int u, int p) {
        parent[u] = p;
        size[u] = 1;
        int maxSize = 0;

        for (int v : adj[u]) {
            if (v == p) continue;

            depth[v] = depth[u] + 1;
            int sub = dfs(v, u);
            size[u] += sub;

            if (sub > maxSize) {
                maxSize = sub;
                heavy[u] = v;
            }
        }

        return size[u];
    }

    void decompose(int u, int h) {
        head[u] = h;
        pos[u] = timer++;

        if (heavy[u] != -1) decompose(heavy[u], h);

        for (int v : adj[u]) {
            if (v != parent[u] && v != heavy[u]) {
                decompose(v, v);
            }
        }
    }

    void updateTree(int node, int l, int r, int idx, int val) {
        if (l == r) {
            seg[node] = val;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) updateTree(node * 2, l, mid, idx, val);
        else updateTree(node * 2 + 1, mid + 1, r, idx, val);

        seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
    }

    int queryTree(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) return seg[node];

        int mid = (l + r) / 2;
        int res = 0;

        if (ql <= mid) res ^= queryTree(node * 2, l, mid, ql, qr);
        if (qr > mid) res ^= queryTree(node * 2 + 1, mid + 1, r, ql, qr);

        return res;
    }
}