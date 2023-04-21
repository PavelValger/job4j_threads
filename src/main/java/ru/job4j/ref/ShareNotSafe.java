package main.java.ru.job4j.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("main");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("first");
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("second");
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(cache.findById(1).getName());
        User user1 = new User();
        user1.setName("qwerty");
        User user2 = new User();
        user2.setName("qwerty2");
        UserCache userCache = new UserCache();
        userCache.add(user1);
        userCache.add(user2);
        userCache.findAll().get(0).setName("wow");
        System.out.println(userCache.findAll());
    }
}
