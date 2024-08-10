package com.bulmeong.basecamp.club.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulmeong.basecamp.club.dto.ClubBookmarkDto;
import com.bulmeong.basecamp.club.dto.ClubCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMeetingDto;
import com.bulmeong.basecamp.club.dto.ClubMeetingMemberDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubNestedCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentLikeDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostLikeDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.dto.ClubRegionCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubVisitDto;
import com.bulmeong.basecamp.user.dto.UserDto;


@Mapper
public interface ClubSqlMapper {
    public void insertClubDto(ClubDto clubDto);
    public void insertClubJoinCondition(ClubJoinConditionDto clubJoinConditionDto);
    public void insertClubPostDto(ClubPostDto clubPostsDto);
    public void insertClubPostImage(ClubPostImageDto clubPostImageDto);
    public void insertClubMemberDto(ClubMemberDto clubMemberDto);
    public List<ClubCategoryDto> selectClubCategory();
    public List<ClubRegionCategoryDto> selectRegionCategory();
    public List<ClubDto> selectClubList();
    public List<ClubMemberDto> selectJoinClubList(int user_id);
    public List<ClubPostDto> selectClubPostDtoList(int club_id);
    public UserDto selectUserDtoById(int id);
    public ClubMemberDto selectClubMemberDto(@Param("club_id")int club_id, @Param("user_id")int user_id);
    public List<ClubMemberDto> selectClubMemberDtoList(int id);
    public ClubPostCategoryDto selectPostCategoryDtoById(int id);
    public List<ClubPostCategoryDto> selectPostCategoryDto();
    public String selectPostCategoryName(int id);
    public String selectClubCategoryName(int id);
    public List<ClubPostImageDto> selectPostImageDtoList();
    public List<ClubPostImageDto> selectPostImageDtoListById(int id);
    public ClubPostDto selectPostDtoById(int id);
    public List<ClubPostDto> selectAllPosts();

    // 인기글 사진
    public List<ClubPostImageDto> selectPostImageList(int post_id);

    public void insertClubPostCommentDto(ClubPostCommentDto clubPostCommentDto);
    // public void updateComment(ClubPostCommentDto clubPostCommentDto);
    // public void deleteComment(int id);
    public int countTotalComment(int id); 
    
    // 댓글 좋아요
    public int confirmCommentLike(ClubPostCommentLikeDto clubPostCommentLikeDto); 
    public void insertCommentLike(ClubPostCommentLikeDto clubPostCommentLikeDto); 
    public void deleteCommentLike(ClubPostCommentLikeDto clubPostCommentLikeDto); 

    // 대댓글 작성
    public void insertNestedCommentDto(ClubNestedCommentDto clubNestedCommentDto);
    public List<ClubNestedCommentDto> selectNestedCommentDtoList(int comment_id);



    public List<ClubPostCommentDto> selectPostCommentDto(int post_id);
    public ClubDto selectClubDtoById(int id);
    public ClubRegionCategoryDto selectRegionCategoryDtoById(int id);
    
    
    // 소모임 북마크
    public int confirmBookmark(ClubBookmarkDto clubBookmarkDto);
    public void deleteBookmark(ClubBookmarkDto clubBookmarkDto);
    public void insertBookmark(ClubBookmarkDto clubBookmarkDto);
    public int countTotalBookmark(int id);

    // 게시글 스크랩
    public int confirmPostLike(ClubPostLikeDto clubPostLikeDto);
    public void deletePostLike(ClubPostLikeDto clubPostLikeDto);
    public void insertPostLike(ClubPostLikeDto clubPostLikeDto);
    public int countTotalPostLike(int id);


    public void increaseReadCount(int id);
    public int totalReadCount(int id);

    public int countTotalClubMember(int id);
    public int checkClubMembership(ClubMemberDto clubMemberDto);

    public List<ClubPostImageDto> selectPostImageDtoByPostId(int id);
    public List<ClubDto> selectBookmarkedClubDtoList(int id);
    
    //  정기모임
    public void insertClubMeetingDto(ClubMeetingDto clubMeetingDto);
    public void insertClubMeetingMemberDto(ClubMeetingMemberDto clubMeetingMemberDto);
    public ClubMeetingMemberDto selectMeetingMember(@Param("meeting_id") int meeting_id, @Param("user_id")int user_id);
    public void deleteMeetingMember(@Param("meeting_id") int meeting_id, @Param("user_id") int user_id);
    public String calculateDdaysForMeetings(@Param("meeting_id") int meeting_id);


    // 홈화면에 정모 정보 출력
    public List<ClubMeetingDto> selectClubMeetingDtoList(int id);
    public int countTotalMeetingMember(int id);
    public String findClubCategoryNameById(int id);

    // 정모 개수 집계
    public int countTotalMeeting(int id);

    // 소모임 정원 확인
    public int confirmCapacity(int id);

    // 소모임 가입조건 확인
    public ClubJoinConditionDto selectClubJoinCondition(int id);

    // 소모임 관리 페이지
    public int yesterdayPostCount(int id);
    public int yesterdayNewMembers(int id);
    
    // 소모임 방문자수 집계
    public int yesterdayVisitCount(int id);
    public int todayVisitCount(int id);

    // 소모임 권한수정
    public void roleUpdate(ClubMemberDto clubMemberDto);

    // 방문자수 쿼리
    public void increaseVisitCount(ClubVisitDto clubVisitDto);
    public ClubVisitDto selectTodayVisit(ClubVisitDto clubVisitDto);
    // 오늘 총방문자수
    public int selectTodayVisitCount(int club_id);
}


