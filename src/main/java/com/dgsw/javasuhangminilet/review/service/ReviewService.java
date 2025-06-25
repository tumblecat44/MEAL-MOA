package com.dgsw.javasuhangminilet.review.service;

import com.dgsw.javasuhangminilet.auth.entity.UserEntity;
import com.dgsw.javasuhangminilet.auth.repository.AuthRepository;
import com.dgsw.javasuhangminilet.review.dto.ReviewDTO;
import com.dgsw.javasuhangminilet.review.dto.response.ReviewResponse;
import com.dgsw.javasuhangminilet.review.entity.ReviewEntity;
import com.dgsw.javasuhangminilet.review.repository.ReviewRepository;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import com.dgsw.javasuhangminilet.util.ResponseCode;
import com.dgsw.javasuhangminilet.util.TokenClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;

    public BaseResponse<ReviewResponse> addReview(ReviewDTO dto) {
        Optional<UserEntity> user;
        try{
            user = getUserFromToken(dto.getToken());
        } catch (Exception e) {
            return BaseResponse.error(ResponseCode.FORBIDDEN,"접근이 불가능합니다.");
        }
        if(user.isEmpty()){
            return BaseResponse.error(ResponseCode.NOT_FOUND,"그런 사람 또 없습니다.");
        }
        ReviewEntity savedEntity = reviewRepository.save(
                ReviewEntity.builder()
                        .user(user.get())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build()
        );
        ReviewResponse response = new ReviewResponse(
                savedEntity.getId(),
                savedEntity.getUser().getId(),
                savedEntity.getTitle(),
                savedEntity.getContent()
        );
        return BaseResponse.success(response);
    }



    public BaseResponse<List<ReviewResponse>> getAllReviews(String token) {
        try{
            getUserFromToken(token);
        } catch (Exception e) {
            return BaseResponse.error(ResponseCode.FORBIDDEN, "권한이 없습니다.");
        }
        try {
            List<ReviewResponse> reviews = reviewRepository.findAll().stream()
                    .map(entity -> new ReviewResponse(
                            entity.getId(),
                            entity.getUser().getId(),
                            entity.getTitle(),
                            entity.getContent()
                    ))
                    .collect(Collectors.toList());
            return BaseResponse.success(reviews);
        } catch (Exception e) {
            return BaseResponse.error(ResponseCode.FORBIDDEN, "권한이 없습니다.");
        }

    }

    public BaseResponse<String> updateReview(Long id, ReviewDTO dto) {
        return BaseResponse.success("TODO");
    }
//
//    public BaseResponse<String> deleteReview(Long id,String token ) {
//        try{
//            reviewRepository.deleteById(id);
//            return BaseResponse.success("deleted");
//        } catch (Exception e) {
//            return BaseResponse.error(e.getMessage());
//        }
//    }


    private Optional<UserEntity> getUserFromToken(String token) {
        Long userId = TokenClient.getUserIdFromToken(token);
        return authRepository.findById(userId);
    }
}
