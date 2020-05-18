package org.ggyool.exam01.web.domain.posts;


import org.ggyool.exam01.domain.posts.Posts;
import org.ggyool.exam01.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// 넣으면 별다른 설정 없이 h2 db 사용하게 해준다.
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){

        // given
        String title = "test title";
        String content = "test content";

        // 없으면 insert, 있으면 update
        postsRepository.save(
                Posts.builder()
                .title(title)
                .content(content)
                .author("ggyool")
                .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts post = postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTImeEntity_등록(){

        // given
        LocalDateTime now = LocalDateTime.of(2020,4,30,0,0,0);
        postsRepository.save(Posts.builder().
                title("title").content("content").author("author").build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>> createDate=" + posts.getCreateDate());
        System.out.println(">>>>>>>>>> modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }
}
