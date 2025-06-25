package com.dgsw.javasuhangminilet.review.controlller;

import com.dgsw.javasuhangminilet.review.dto.*;
import com.dgsw.javasuhangminilet.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class reviewController {
    ReviewService reviewService;

    @PostMapping("/review")
    public ResponseDTO addReview(@RequestBody @Valid ReviewDTO dto) {
        log.info("review ID: " + dto.getId());
        log.info("User ID: " + dto.getUserId());
        log.info("review title: " + dto.getTitle());
        log.info("review content: " + dto.getContent());
        boolean created = reviewService.addReview(dto);
        return new ResponseDTO(created ? "created" : "failed");
    }

    @GetMapping("/reviews")
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PatchMapping("/update/{id}")
    public ResponseDTO updateReview(@PathVariable Long id, @RequestBody ReviewDTO dto) {
        boolean updated = reviewService.updateReview(id, dto);
        return new ResponseDTO(updated ? "updated" : "failed");
    }
}