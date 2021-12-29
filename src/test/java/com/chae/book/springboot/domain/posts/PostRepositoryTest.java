package com.chae.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void save_article() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        postsRepository.save(Posts.builder().title(title).content(content).author("bubsakk@gmail.com").build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);
        assert (post.getTitle().equals(title));
        assert (post.getContent().equals(content));


    }

    @Test
    public void BaseTimeEntity_regist() {
        //given
        LocalDateTime now = LocalDateTime.of(2021,12,26,0,0,0);
        postsRepository.save(Posts.builder()
                                .title("title")
                                .author("author")
                                .content("content")
                                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>createDate = "+posts.getCreatedDate()+" modifiedDate = "+posts.getModifiedDate());

        assert(posts.getCreatedDate().isAfter(now));
        assert(posts.getModifiedDate().isAfter(now));
    }
}
