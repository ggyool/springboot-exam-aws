package org.ggyool.exam01.web.dto;

import lombok.Getter;
import org.ggyool.exam01.domain.posts.Posts;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity){
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        author = entity.getAuthor();
    }
}
