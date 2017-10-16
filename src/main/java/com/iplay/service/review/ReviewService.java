package com.iplay.service.review;

import java.util.List;

import com.iplay.dto.review.ReviewDTO;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.review.PostReviewVO;

public interface ReviewService {
	int addReviews(int orderId, SimplifiedUser author, PostReviewVO vo);
	
	boolean deleteReview(int reviewId);
	
	boolean deleteReviews(EntityDeletionVO vo);
	
	List<ReviewDTO> listReviews(int page, int pageSize);
}
