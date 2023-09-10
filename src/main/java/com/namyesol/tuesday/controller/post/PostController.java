package com.namyesol.tuesday.controller.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.domain.post.Comment;
import com.namyesol.tuesday.domain.post.Post;
import com.namyesol.tuesday.dto.post.CommentDetails;
import com.namyesol.tuesday.dto.post.NewPostForm;
import com.namyesol.tuesday.dto.post.PostDetails;
import com.namyesol.tuesday.service.post.PostReadService;
import com.namyesol.tuesday.service.post.PostCommandService;
import com.namyesol.tuesday.service.post.CommentService;

@Controller
public class PostController {

    private final PostCommandService postCommandService;
    private final PostReadService postReadService;
    private final CommentService commentService;

    
    public PostController(PostCommandService postCommandService, PostReadService postReadService, CommentService commentService) {
        this.postCommandService = postCommandService;
        this.postReadService = postReadService;
        this.commentService = commentService;
    }

    @GetMapping("/posts/new")
    public String showNewPostForm(@ModelAttribute("postForm") NewPostForm form) {
    	return "post/post-new";
    }

    @PostMapping("/posts/new")
    public String newPost(@Valid @ModelAttribute("postForm") NewPostForm form, BindingResult bindingResult,
            @SessionAttribute(SessionConstants.AUTHENTICATED_MEMBER) Member member, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "post/post-new";
        }
        
        Post post = new Post(member.getId(), form.getTitle(), form.getContent());

        postCommandService.save(post);

        redirectAttributes.addAttribute("postId", post.getId());
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/posts/{postId}")
    public String showPostDetailsPage(@PathVariable("postId") Long id, Model model) {
        postCommandService.increaseViews(id);
    	
        PostDetails post = postReadService.findById(id);
        List<CommentDetails> comments = postReadService.findCommentsByPostId(id);
        
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        
        return "post/post-details";
    }

    @GetMapping("/posts/{postId}/edit")
    public String showEditPostForm(@PathVariable Long postId, @SessionAttribute(SessionConstants.AUTHENTICATED_MEMBER) Member member, Model model) {
    	
        Post post = postCommandService.findByPostId(postId);

        if (!post.getMemberId().equals(member.getId())) {
            return "error/403";
        }

        model.addAttribute("post", post);
        
        return "post/post-edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable Long postId, @ModelAttribute("post") Post post, @SessionAttribute(SessionConstants.AUTHENTICATED_MEMBER) Member member) {
    	
    	postCommandService.update(postId, member.getId(), post);

        return "redirect:/posts/{postId}";
    }

    @GetMapping("/posts")
    public String PostDetailsList(Model model) {
        List<PostDetails> posts = postReadService.findAll();
        model.addAttribute("posts", posts);
        return "/post/post-list";
    }

    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId, @SessionAttribute(SessionConstants.AUTHENTICATED_MEMBER) Member member) {

    	postCommandService.deletePost(postId, member.getId());
    	
    	return "redirect:/posts";
    }
    
    @PostMapping("/posts/{postId}/comments")
    public String newComment(@RequestParam("comment") String content, @PathVariable Long postId, @SessionAttribute(SessionConstants.AUTHENTICATED_MEMBER) Member member) {
        
    	Comment comment = new Comment(member.getId(), postId, content);
        
    	commentService.save(comment);
        
    	return "redirect:/posts/{postId}";
    }
}
