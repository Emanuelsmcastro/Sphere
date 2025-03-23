package com.post.server.controllers.v1;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post.server.dtos.v1.post.CommentRequestDTO;
import com.post.server.dtos.v1.post.ReactionRequestDTO;
import com.post.server.dtos.v1.post.ResponseCommentDTO;
import com.post.server.dtos.v1.post.ResponsePostDTO;
import com.post.server.dtos.v1.post.ResponseReactionDTO;
import com.post.server.services.v1.interfaces.CommentService;
import com.post.server.services.v1.interfaces.PostService;
import com.post.server.services.v1.interfaces.ReactionService;

@RestController
@RequestMapping("/post/v1")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	ReactionService reactionService;

	@Autowired
	CommentService commentService;

	@GetMapping("/get-friend-posts")
	public ResponseEntity<Page<ResponsePostDTO>> getFriendPosts(Authentication authentication, Pageable pageable) {
		UUID profileUUID = getUserProfileUUID(authentication);
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllFriendPosts(profileUUID, pageable));
	}

	@PostMapping("/reaction")
	public ResponseEntity<Void> likePost(Authentication authentication,
			@RequestBody ReactionRequestDTO reactionRequestDTO) {
		Map<String, Object> profile = getUserProfile(authentication);
		reactionService.save(getUserProfileUUID(authentication), (String) profile.get("name"), reactionRequestDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/reaction/search/{postUUID}")
	public ResponseEntity<Page<ResponseReactionDTO>> getPostReactions(Pageable pageable,
			@PathVariable("postUUID") UUID postUUID) {
		return ResponseEntity.ok(reactionService.getReactionByPostUuid(postUUID, pageable));
	}

	@PostMapping("/comment")
	public ResponseEntity<Void> commentPost(Authentication authentication,
			@RequestBody CommentRequestDTO commentRequestDTO) {
		Map<String, Object> profile = getUserProfile(authentication);
		commentService.save(getUserProfileUUID(authentication), (String) profile.get("name"), commentRequestDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/comment/search/{postUUID}")
	public ResponseEntity<Page<ResponseCommentDTO>> getPostComments(Pageable pageable,
			@PathVariable("postUUID") UUID postUUID) {
		return ResponseEntity.ok(commentService.getCommentByPostUUID(postUUID, pageable));
	}

	private UUID getUserProfileUUID(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return UUID.fromString((String) profileMap.get("uuid"));

	}

	private Map<String, Object> getUserProfile(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return profileMap;
	}
}
