package com.sm.wifey.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity @Getter @Setter
public class BlogPost {

    @Id
    @SequenceGenerator(name="blog_post_seq_gen", sequenceName = "blog_post_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_post_seq_gen")
    @Setter(AccessLevel.NONE)
    private Long id;

    private String title;
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imageUrl;

    @Column(unique = true)
    private String blogUrl;

    @Column(nullable = false)
    private boolean showEnabled = true;

    @Column(nullable = false)
    private Boolean commentsEnabled = true;

    @UpdateTimestamp
    private LocalDate publishedDate;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ElementCollection
    private List<String> tags;

    private int likeCount;
    private int loveCount;
    private int thanksCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<BlogComment> comments;

}
