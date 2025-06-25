package com.dgsw.javasuhangminilet.review.service;

import com.dgsw.javasuhangminilet.review.dto.ReviewDTO;
import com.dgsw.javasuhangminilet.review.entity.ReviewEntity;
import com.dgsw.javasuhangminilet.review.repository.ReviewRepository;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public BaseResponse<String> addReview(ReviewDTO dto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUserId(dto.getUserId());
        reviewEntity.setTitle(dto.getTitle());
        reviewEntity.setContent(dto.getContent());
        ReviewEntity saved = reviewRepository.save(reviewEntity);
        String message = saved!=null? "created": "faild";
        return BaseResponse.success(message);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(entity -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(entity.getId());
            dto.setUserId(entity.getUserId());
            dto.setTitle(entity.getTitle());
            dto.setContent(entity.getContent());
            return dto;
        }).collect(Collectors.toList());
    }

    public BaseResponse<String> updateReview(Long id, ReviewDTO dto) {
        // 기존 리뷰를 ID로 찾기
        ReviewEntity existing = reviewRepository.findById(id).orElse(null);
        if (existing == null) return BaseResponse.success("faild");
        // 값이 null이 아니면 해당 값으로 업데이트
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle()); // 제목 수정
        if (dto.getContent() != null) existing.setContent(dto.getContent()); // 내용 수정

        reviewRepository.save(existing); // 저장소에 다시 저장
        return BaseResponse.success("updated"); // 성공
    }
}
