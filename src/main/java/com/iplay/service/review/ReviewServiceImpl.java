package com.iplay.service.review;

import java.util.List;

//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;

import com.iplay.dto.review.ReviewDTO;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.review.PostReviewVO;

public class ReviewServiceImpl implements ReviewService{

	@Override
	public int addReviews(int orderId, SimplifiedUser author, PostReviewVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteReview(int reviewId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteReviews(EntityDeletionVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ReviewDTO> listReviews(int page, int pageSize) {
		//Pageable pageable = new PageRequest(0,3, Sort.Direction.DESC,"id");
		return null;
	}

}
