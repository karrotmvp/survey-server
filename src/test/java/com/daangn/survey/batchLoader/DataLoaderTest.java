package com.daangn.survey.batchLoader;

import com.daangn.survey.mongo.MongoRepository;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@SpringBootTest
public class DataLoaderTest {

    @Autowired
    private MongoRepository mongoRepository;


    public class User {
        private Long id;
        private Long invitedBy;
        private String name;

        public User(Long userId, Long invitedBy, String name){
            this.id = userId;
            this.invitedBy = invitedBy;
            this.name = name;
        }

        public Long getId(){
            return this.id;
        }

        public Long getInvitedByID(){
            return this.invitedBy;
        }
    }

    public  final User ILÚVATAR = new User(-1L, -1L, "Ilúvatar");
    public  final User AULË = new User(10001L, -1L, "Aulë");
    public  final User OROMË = new User(10002L, -1L, "Oromë");
    public  final User YAVANNA = new User(10003L, -1L, "Yavanna");
    public  final User MANWË = new User(10004L, -1L, "Manwë");
    public  final User MORGOTH = new User(10005L, -1L, "Morgoth");
    public  final User CURUNIR = new User(2L, 10001L, "Curunir");
    public  final User ALATAR = new User(3L, 10002L, "Alatar");
    public  final User AIWENDIL = new User(4L, 10003L, "Aiwendil");
    public  final User OLÓRIN = new User(1L, 10004L, "Olórin");
    public  final User SAURON = new User(5L, 10005L, "Sauron");

    final Map<Long, User> users = new LinkedHashMap<>();

    {
        add(ILÚVATAR);

        add(AULË);
        add(OROMË);
        add(YAVANNA);
        add(MANWË);
        add(MORGOTH);

        add(CURUNIR);
        add(ALATAR);
        add(AIWENDIL);
        add(OLÓRIN);
        add(SAURON);
    }

    private void add(User user) {
        users.put(user.getId(), user);
    }

    public User loadUserById(Long userId) {
        return users.get(userId);
    }

    public List<User> loadUsersById(List<Long> userIds) {
        List<User> users = userIds.stream().map(this::loadUserById).collect(Collectors.toList());

        System.out.println(users);

        return users;
    }

    @Test
    void 데이터로더_테스트(){
        BatchLoader<Long, User> lessEfficientUserBatchLoader = new BatchLoader<Long, User>() {
            @Override
            public CompletionStage<List<User>> load(List<Long> userIds) {
                return CompletableFuture.supplyAsync(() -> {
                    //
                    // notice how it makes N calls to load by single user id out of the batch of N keys
                    //
                    return userIds.stream()
                            .map(id -> loadUserById(id))
                            .collect(Collectors.toList());
                });
            }
        };

        DataLoader<Long, User> userLoader = DataLoaderFactory.newDataLoader(lessEfficientUserBatchLoader);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Arrays.asList(1L, 2L, 3L, 4L, 5L).forEach(el -> {
            userLoader.load(el).thenAccept(user -> {
                System.out.println("user = " + user.name);
                userLoader.load(user.getInvitedByID())
                        .thenAccept(invitedBy -> {
                            System.out.println(user.name + "invitedBy = " + invitedBy.name);
                        });
            });
        });
//        userLoader.load(1L)
//                .thenAccept(user -> {
//                    System.out.println("user = " + user);
//                    userLoader.load(user.getInvitedByID())
//                            .thenAccept(invitedBy -> {
//                                System.out.println("invitedBy = " + invitedBy);
//                            });
//                });
//
//        userLoader.load(2L)
//                .thenAccept(user -> {
//                    System.out.println("user = " + user);
//                    userLoader.load(user.getInvitedByID())
//                            .thenAccept(invitedBy -> {
//                                System.out.println("invitedBy = " + invitedBy);
//                            });
//                });

        userLoader.dispatchAndJoin();

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    void 데이터로더_배치_테스트(){
        BatchLoader<Long, User> userBatchLoader = new BatchLoader<Long, User>() {
            @Override
            public CompletionStage<List<User>> load(List<Long> userIds) {
                return CompletableFuture.supplyAsync(() -> loadUsersById(userIds));
            }
        };

        DataLoader<Long, User> userLoader = DataLoaderFactory.newDataLoader(userBatchLoader);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

//        userLoader.loadMany(Arrays.asList(1L, 2L, 3L, 4L, 5L)).thenAccept(users -> {
//            users.forEach(user -> System.out.println("user = " + user));
//
//            userLoader.loadMany(users.stream().map(User::getId).collect(Collectors.toList()))
//                    .thenAccept(invitedBys -> {
//                        invitedBys.forEach(invitation -> System.out.println(" invitedBy " + invitation.name));
//                    });
//        });


        /**
         * 1. forEach로 불렀음에도, 한 번에 요청을 모아서 호출하는 게 신기하다.
         */
        Arrays.asList(1L, 2L, 3L, 4L, 5L).forEach(el -> {
            userLoader.load(el).thenAccept(user -> {
                System.out.println("user = " + user);

                userLoader.load(user.getInvitedByID())
                        .thenAccept(invitedBy -> {
                            System.out.println(user.name + " invitedBy " + invitedBy.name);
                        });
            });
        });

        userLoader.dispatchAndJoin();

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
