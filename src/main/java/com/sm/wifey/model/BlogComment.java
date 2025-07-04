package com.sm.wifey.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter @Setter
public class BlogComment {

    @Id
    @SequenceGenerator(name="blog_comment_seq_gen", sequenceName = "blog_comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_comment_seq_gen")
    @Setter(AccessLevel.NONE)
    private Long id;

    private String authorName;

    private String content;

    @Column(nullable = false)
    private boolean showEnabled = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne
    private BlogPost post;

    @ManyToOne
    private BlogComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<BlogComment> replies;

}
