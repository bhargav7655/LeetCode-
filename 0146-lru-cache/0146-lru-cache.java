import java.util.HashMap;

class LRUCache {

    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> map;

    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        // Dummy nodes
        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {

        // Key doesn't exist
        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);

        // This key was just used,
        // so move it to the MRU position
        remove(node);
        addToEnd(node);

        return node.value;
    }

    public void put(int key, int value) {

        // Key already exists
        if (map.containsKey(key)) {

            Node node = map.get(key);

            // Update value
            node.value = value;

            // Move to MRU position
            remove(node);
            addToEnd(node);

        } else {

            // Create new node
            Node node = new Node(key, value);

            map.put(key, node);
            addToEnd(node);

            // Capacity exceeded
            if (map.size() > capacity) {

                // Remove LRU node
                Node lru = head.next;

                remove(lru);
                map.remove(lru.key);
            }
        }
    }

    // Remove a node from the linked list
    private void remove(Node node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Add node just before tail
    // This means it becomes MRU
    private void addToEnd(Node node) {

        node.prev = tail.prev;
        node.next = tail;

        tail.prev.next = node;
        tail.prev = node;
    }
}