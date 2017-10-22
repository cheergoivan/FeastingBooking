package com.iplay.service.review;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.iplay.dao.hotel.rating.HotelRatingDAO;
import com.iplay.dao.order.OrderDAO;
import com.iplay.dao.review.ReviewDAO;

//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;

import com.iplay.dto.review.ReviewDTO;
import com.iplay.entity.hotel.rating.HotelRatingDO;
import com.iplay.entity.order.OrderBHReviewedCustomerIdDO;
import com.iplay.entity.review.ReviewDO;
import com.iplay.service.exception.ResourceForbiddenException;
import com.iplay.service.exception.ResourceNotFoundException;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.review.PostReviewVO;

public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private HotelRatingDAO hotelRatingDAO;
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Override
	@Transactional
	public int addReviews(int orderId, SimplifiedUser author, PostReviewVO vo) {
		OrderBHReviewedCustomerIdDO order = orderDAO.findBHReviewedCustomerIdById(orderId);
		if(order == null)
			throw new ResourceNotFoundException("Order with id:"+orderId+" doesn't exist!");
		if(order.isReviewed()||order.getCustomerId()!=author.getUserId())
			throw new ResourceForbiddenException("Order with id:"+orderId+" already has been reviewed or you don't have authority!");
		String banquetHall = order.getBanquetHallName();
		int hotelId = order.getHotelId();
		double score = vo.getRating();
		ReviewDO savedReview = reviewDAO.save(new ReviewDO(hotelId, banquetHall, author.getUserId(), author.getUsername(), score, vo.getReview()));
		//update hotel rating
		HotelRatingDO rating = hotelRatingDAO.findOne(hotelId);
		if(rating==null){
			rating = hotelRatingDAO.save(new HotelRatingDO(hotelId, 0, 0, 5.0));
		}
		rating.setRating((rating.getTotalScore()+score)/(rating.getTimes()+1));
		rating.setTimes(rating.getTimes()+1);
		rating.setTotalScore(rating.getTotalScore()+score);
		hotelRatingDAO.save(rating);
		return savedReview.getId();
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
