import java.util.HashMap;

class LFUCache {

    class Node {
        int key;
        int value;
        int freq;

        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DoublyLinkedList {

        Node head;
        Node tail;
        int size;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);

            head.next = tail;
            tail.prev = head;

            size = 0;
        }

        // Add node to the front
        // Front = Most Recently Used
        void addFirst(Node node) {

            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

            size++;
        }

        // Remove a specific node
        void remove(Node node) {

            node.prev.next = node.next;
            node.next.prev = node.prev;

            size--;
        }

        // Remove the least recently used node
        Node removeLast() {

            if (size == 0) {
                return null;
            }

            Node node = tail.prev;

            remove(node);

            return node;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    private int capacity;
    private int size;
    private int minFreq;

    // key -> node
    private HashMap<Integer, Node> keyToNode;

    // frequency -> doubly linked list
    private HashMap<Integer, DoublyLinkedList> freqToList;

    public LFUCache(int capacity) {

        this.capacity = capacity;
        this.size = 0;
        this.minFreq = 0;

        keyToNode = new HashMap<>();
        freqToList = new HashMap<>();
    }

    public int get(int key) {

        // Key doesn't exist
        if (!keyToNode.containsKey(key)) {
            return -1;
        }

        Node node = keyToNode.get(key);

        // Increase frequency
        increaseFrequency(node);

        return node.value;
    }

    public void put(int key, int value) {

        // If capacity is zero
        if (capacity == 0) {
            return;
        }

        // Key already exists
        if (keyToNode.containsKey(key)) {

            Node node = keyToNode.get(key);

            // Update value
            node.value = value;

            // put() also increases frequency
            increaseFrequency(node);

            return;
        }

        // Cache is full
        if (size == capacity) {

            // Get the list containing the LFU keys
            DoublyLinkedList list = freqToList.get(minFreq);

            // Remove LRU key from that list
            Node removedNode = list.removeLast();

            // Remove it from key map
            keyToNode.remove(removedNode.key);

            size--;
        }

        // Create new node
        Node newNode = new Node(key, value);

        // New node has frequency 1
        keyToNode.put(key, newNode);

        // Get frequency 1 list
        DoublyLinkedList list =
                freqToList.computeIfAbsent(
                        1,
                        k -> new DoublyLinkedList()
                );

        // Add new node as most recently used
        list.addFirst(newNode);

        // New minimum frequency is 1
        minFreq = 1;

        size++;
    }

    private void increaseFrequency(Node node) {

        int oldFreq = node.freq;

        // Remove node from old frequency list
        DoublyLinkedList oldList = freqToList.get(oldFreq);

        oldList.remove(node);

        // If this was the minimum frequency
        // and the list is now empty,
        // increase minFreq
        if (oldFreq == minFreq && oldList.isEmpty()) {
            minFreq++;
        }

        // Increase node frequency
        node.freq++;

        // Get/create new frequency list
        DoublyLinkedList newList =
                freqToList.computeIfAbsent(
                        node.freq,
                        k -> new DoublyLinkedList()
                );

        // Add node to the front
        // Therefore it becomes most recently used
        newList.addFirst(node);
    }
}