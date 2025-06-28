package com.sm.wifey.config;

import com.sm.wifey.model.Author;
import com.sm.wifey.model.BlogComment;
import com.sm.wifey.model.BlogPost;
import com.sm.wifey.model.User;
import com.sm.wifey.repository.AuthorRepository;
import com.sm.wifey.repository.BlogPostRepository;
import com.sm.wifey.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitialization {

    @Bean
    public CommandLineRunner initData(AuthorRepository authorRepository,
                                      BlogPostRepository blogPostRepository,
                                      UserRepository userRepository) {
        return args -> {
            // Only initialize if database is empty
            if (authorRepository.count() == 0 && blogPostRepository.count() == 0) {
                // Create main author
                Author mainAuthor = new Author();
                mainAuthor.setName("Swathi Anantha");
                mainAuthor.setCredentials("Board Certified Chaplain, MDiv, BCC");
                mainAuthor.setBio("With over 15 years of experience in hospital and hospice chaplaincy, Dr. Johnson brings deep spiritual care expertise to the digital space.");
                mainAuthor.setAvatarUrl("/image/Swathi.jpg");
                authorRepository.save(mainAuthor);

                // Create sample blog posts
                BlogPost post1 = createBlogPost(
                        "Finding Hope in Difficult Times",
                        "How spiritual practices can sustain us during challenges",
                        "In my years as a chaplain, I've witnessed how spiritual practices...",
                        "/image/blog/first_photo1.jpg", "first", true,
                        LocalDate.now().minusDays(7),
                        mainAuthor,
                        Arrays.asList("hope", "spirituality", "innovation")
                );

                BlogPost post2 = createBlogPost(
                        "The Art of Spiritual Listening",
                        "Essential skills for meaningful spiritual conversations",
                        "Active listening forms the foundation of all chaplaincy work...",
                        "/image/blog/second_crispr.png", "second", true,
                        LocalDate.now().minusDays(14),
                        mainAuthor,
                        Arrays.asList("listening", "communication", "chaplaincy")
                );

                BlogPost post3 = createBlogPost(
                        "Virtual Chaplaincy: Challenges and Blessings",
                        "Reflections on providing spiritual care remotely",
                        "The pandemic accelerated the adoption of telechaplaincy...",
                        "/image/blog/third_photo1.png", "third", true,
                        LocalDate.now().minusDays(21),
                        mainAuthor,
                        Arrays.asList("telechaplaincy", "technology", "innovation")
                );

                // Save posts first to generate IDs
                blogPostRepository.saveAll(Arrays.asList(post1, post2, post3));

                // Create comments with replies
                createCommentsWithReplies(post1);
                createCommentsWithReplies(post2);

                // Save posts again with comments
                blogPostRepository.saveAll(Arrays.asList(post1, post2));

                //Admin user
                User user = new User();
                user.setName("Sai Maheshwaran Rajan");
                user.setUsername("Admin");
                user.setPassword("Admin@123");
                user.setPhone("000-000-0000");
                user.setEmail("saimaheshwaran@outlook.com");
                userRepository.save(user);
            }
        };
    }

    private BlogPost createBlogPost(String title, String summary, String content,
                                    String imageUrl, String blogUrl, Boolean commentEnabled, LocalDate publishedDate,
                                    Author author, List<String> tags) {
        BlogPost post = new BlogPost();
        post.setTitle(title);
        post.setSummary(summary);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setBlogUrl(blogUrl);
        post.setCommentsEnabled(commentEnabled);
        post.setPublishedDate(publishedDate);
        post.setAuthor(author);
        post.setTags(tags);
        post.setLikeCount(0);
        post.setLoveCount(0);
        post.setThanksCount(0);
        return post;
    }

    private void createCommentsWithReplies(BlogPost post) {
        // First comment with replies
        BlogComment comment1 = new BlogComment();
        comment1.setAuthorName("James Wilson");
        comment1.setContent("This really resonated with me. Thank you for sharing these insights.");
        comment1.setCreatedAt(LocalDateTime.now().minusDays(2));
        comment1.setPost(post);
        comment1.setShowEnabled(true);

        BlogComment reply1 = new BlogComment();
        reply1.setAuthorName("Chaplain Sarah");
        reply1.setContent("Thank you for your kind words, James. I'm glad you found it helpful.");
        reply1.setCreatedAt(LocalDateTime.now().minusDays(1));
        reply1.setPost(post);
        reply1.setParentComment(comment1);
        reply1.setShowEnabled(true);

        BlogComment reply2 = new BlogComment();
        reply2.setAuthorName("Maria Garcia");
        reply2.setContent("I agree with James. Your perspective on this is so valuable.");
        reply2.setCreatedAt(LocalDateTime.now().minusHours(12));
        reply2.setPost(post);
        reply2.setParentComment(comment1);
        reply2.setShowEnabled(true);

        comment1.setReplies(Arrays.asList(reply1, reply2));

        // Second comment
        BlogComment comment2 = new BlogComment();
        comment2.setAuthorName("David Thompson");
        comment2.setContent("Could you recommend any resources for going deeper with this practice?");
        comment2.setCreatedAt(LocalDateTime.now().minusDays(1));
        comment2.setPost(post);
        comment2.setShowEnabled(true);

        BlogComment reply3 = new BlogComment();
        reply3.setAuthorName("Chaplain Sarah");
        reply3.setContent("Certainly, David! I'll email you some reading suggestions.");
        reply3.setCreatedAt(LocalDateTime.now().minusHours(6));
        reply3.setPost(post);
        reply3.setParentComment(comment2);
        reply3.setShowEnabled(true);

        comment2.setReplies(List.of(reply3));

        // Add comments to post
        post.setComments(Arrays.asList(comment1, comment2));

        // Set reaction counts
        if (post.getTitle().contains("Hope")) {
            post.setLikeCount(12);
            post.setLoveCount(8);
            post.setThanksCount(5);
        } else if (post.getTitle().contains("Listening")) {
            post.setLikeCount(7);
            post.setLoveCount(15);
            post.setThanksCount(3);
        }
    }
}
