class Twitter {

    class Tweet {
        int id;
        int time;

        Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    HashMap<Integer, Set<Integer>> following;
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(b.time, a.time)
        );

        if (tweets.containsKey(userId)) {
            pq.addAll(tweets.get(userId));
        }

        if (following.containsKey(userId)) {
            for (int followeeId : following.get(userId)) {
                if (tweets.containsKey(followeeId)) {
                    pq.addAll(tweets.get(followeeId));
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!pq.isEmpty() && result.size() < 10) {
            result.add(pq.poll().id);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        following.putIfAbsent(followerId, new HashSet<>());
        following.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId)) {
            following.get(followerId).remove(followeeId);
        }
    }
}