package org.ggyool.exam01.web.dto;

import lombok.Getter;
import org.ggyool.exam01.domain.posts.Posts;

import java.time.LocalDateTime;

// 게시판 리스트에서 보여 주기 위한 dto
@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }

}
