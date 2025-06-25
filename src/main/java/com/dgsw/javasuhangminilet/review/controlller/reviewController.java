package com.dgsw.javasuhangminilet.review.controlller;

import com.dgsw.javasuhangminilet.review.dto.*;
import com.dgsw.javasuhangminilet.review.service.ReviewService;
import com.dgsw.javasuhangminilet.util.BaseResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class reviewController {
    ReviewService reviewService;

    @PostMapping("/review")
    public BaseResponse<String> addReview(@RequestBody @Valid ReviewDTO dto) {
        log.info("review ID: " + dto.getId());
        log.info("User ID: " + dto.getUserId());
        log.info("review title: " + dto.getTitle());
        log.info("review content: " + dto.getContent());
        return reviewService.addReview(dto);
    }

    @GetMapping("/reviews")
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PatchMapping("/update/{id}")
    public BaseResponse<String> updateReview(@PathVariable Long id, @RequestBody ReviewDTO dto) {
        return reviewService.updateReview(id, dto);
    }
}