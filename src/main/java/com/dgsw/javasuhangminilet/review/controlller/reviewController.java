package com.dgsw.javasuhangminilet.review.controlller;

import com.dgsw.javasuhangminilet.review.dto.*;
import com.dgsw.javasuhangminilet.review.dto.request.AddReviewRequest;
import com.dgsw.javasuhangminilet.review.dto.response.ReviewResponse;
import com.dgsw.javasuhangminilet.review.service.ReviewService;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public BaseResponse<ReviewResponse> addReview(@RequestBody @Valid AddReviewRequest dto,@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String token) {
        log.info("review ID: " + dto.title());
        log.info("User ID: " + dto.content());
        return reviewService.addReview(new ReviewDTO(token, dto.title(), dto.content()));
    }

    @GetMapping("/reviews")
    public BaseResponse<List<ReviewResponse>> getAllReviews(@Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String token) {
        return reviewService.getAllReviews(token);
    }

    @PatchMapping("/update/{id}")
    public BaseResponse<String> updateReview(@PathVariable Long id, @RequestBody ReviewDTO dto) {
        return reviewService.updateReview(id, dto);
    }
//
//    @DeleteMapping("/update/{id}")
//    public BaseResponse<String> deleteReview(@PathVariable Long id, @RequestHeader("Authorization") String token) {
//        return reviewService.deleteReview(id, token);
//    }
}