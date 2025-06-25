package com.sm.wifey.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "authors")
public class Author {

    @Id
    @SequenceGenerator(name="blog_comment_seq_gen", sequenceName = "blog_comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_comment_seq_gen")
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;
    private String credentials;
    private String bio;
    private String avatarUrl;

}
