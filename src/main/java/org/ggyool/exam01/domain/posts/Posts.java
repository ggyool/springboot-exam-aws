package org.ggyool.exam01.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ggyool.exam01.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
//@Entity 하려면 파라미터 없는 생성자 가지고 있어야 한다.
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    // auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=500, nullable = false)
    private String title;

    // 긴 글이라서 TEXT 타입으로 한 듯?
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
