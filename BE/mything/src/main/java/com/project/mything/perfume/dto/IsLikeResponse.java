package com.project.mything.perfume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.mything.perfume.dto
 * fileName       : IsLikeResponse
 * author         : hagnoykmik
 * date           : 2023-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-03-28        hagnoykmik       최초 생성
 */
@AllArgsConstructor
@Data
@Builder
public class IsLikeResponse {
    private String likeImageList;
    private Boolean isLike;
    private Long likeCnt;

    public static IsLikeResponse create(String likeImageList, Boolean isLike, Long likeCnt) {
        IsLikeResponse isLikeResponse = IsLikeResponse.builder()
                .likeImageList(likeImageList)
                .isLike(isLike)
                .likeCnt(likeCnt)
                .build();
        return isLikeResponse;
    }
}
