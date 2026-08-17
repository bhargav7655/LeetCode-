class StreamChecker {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }

    private TrieNode root;
    private StringBuilder stream;
    private int maxLength;

    public StreamChecker(String[] words) {
        root = new TrieNode();
        stream = new StringBuilder();

        for (String word : words) {
            maxLength = Math.max(maxLength, word.length());

            TrieNode current = root;

            for (int i = word.length() - 1; i >= 0; i--) {
                int index = word.charAt(i) - 'a';

                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }

                current = current.children[index];
            }

            current.isWord = true;
        }
    }

    public boolean query(char letter) {
        stream.append(letter);

        TrieNode current = root;

        int start = Math.max(0, stream.length() - maxLength);

        for (int i = stream.length() - 1; i >= start; i--) {
            int index = stream.charAt(i) - 'a';

            if (current.children[index] == null) {
                return false;
            }

            current = current.children[index];

            if (current.isWord) {
                return true;
            }
        }

        return false;
    }
}