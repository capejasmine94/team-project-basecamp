package com.bulmeong.basecamp.club.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.dto.ClubPostLikeDto;
import com.bulmeong.basecamp.club.dto.ClubRegionCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubVisitDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;


@Service
public class ClubService {

    @Autowired ClubSqlMapper clubSqlMapper;
    @Autowired UserSqlMapper userSqlMapper;

    // 소모임 개설하기

    public void createNewClub(ClubDto clubDto){
        clubSqlMapper.insertClubDto(clubDto);

        }

    // 소모임 개설조건 (연령)
    public void insertClubJoinCondition(ClubJoinConditionDto clubJoinConditionDto){
        clubSqlMapper.insertClubJoinCondition(clubJoinConditionDto);
    }


    //  소모임 게시판 글 작성하기

    public void writeClubPost(ClubPostDto clubPostDto, List<ImageDto>imageDtolist){
        
        clubSqlMapper.insertClubPostDto(clubPostDto);

        for(ImageDto imageDto : imageDtolist){
            int id = clubPostDto.getId();
            ClubPostImageDto clubPostImageDto = new ClubPostImageDto();
            clubPostImageDto.setPost_id(id);
            clubPostImageDto.setPost_img_location(imageDto.getLocation());
            clubSqlMapper.insertClubPostImage(clubPostImageDto);
        }

    }

    //  소모임 게시판 댓글 수정하기
    // public void updateComment(ClubPostCommentDto clubPostCommentDto){
    //     clubSqlMapper.updateComment(clubPostCommentDto);
    // }

    //  소모임 게시판 댓글 삭제하기
    // public void deleteComment(int id){
        // clubSqlMapper.deleteComment(id);
    // }



    // 소모임 회원가입
    public void joinClub(ClubMemberDto clubMemberDto){
        clubSqlMapper.insertClubMemberDto(clubMemberDto);
    }

    // 소모임 가입된 멤버 리스트 뽑기
    public List<Map<String, Object>> findClubMemerDataList(int id){
        List<ClubMemberDto> clubMemberDtoList = clubSqlMapper.selectClubMemberDtoList(id);
        List<Map<String, Object>> clubMemberDataList = new ArrayList<>();

        for(ClubMemberDto clubMemberDto : clubMemberDtoList){
            UserDto userDto = clubSqlMapper.selectUserDtoById(clubMemberDto.getUser_id());

            Map<String, Object> joinMemberMap = new HashMap<>();
            joinMemberMap.put("clubMemberDto", clubMemberDto);
            joinMemberMap.put("userDto", userDto);

            clubMemberDataList.add(joinMemberMap);
        }
            

        return clubMemberDataList;
    }


    // 관심사 카테고리
    public List<ClubCategoryDto> findClubCategory(){
        List<ClubCategoryDto> clubCategoryDtoList = clubSqlMapper.selectClubCategory();

        return clubCategoryDtoList;
    }


    // 지역 카테고리
    public List<ClubRegionCategoryDto> findRegionCategory(){

        List<ClubRegionCategoryDto> regionCategoryDtoList = clubSqlMapper.selectRegionCategory();

        return regionCategoryDtoList;
    }

    public ClubRegionCategoryDto findRegionCategoryDtoById(int id){
        ClubRegionCategoryDto clubRegionCategoryDto = clubSqlMapper.selectRegionCategoryDtoById(id);

        
        return clubRegionCategoryDto;
    }

    // 소모임 게시글 카테고리
    public List<ClubPostCategoryDto> findPostCategory(){
        List<ClubPostCategoryDto> postCategoryDtoList = clubSqlMapper.selectPostCategoryDto();
        
        return postCategoryDtoList;
    }

 

    // 모든 소모임 목록 뽑기
    public List<ClubDto> findClubDtoList(){

        List<ClubDto> clubDtoList = clubSqlMapper.selectClubList();
        return clubDtoList;
    }

    // 가입한 소모임 목록 뽑기 (by user_id)
    public List<ClubDto> findJoinClubDtoList(int user_id){
        List<ClubDto> clubDtoList = new ArrayList<>();

        List<ClubMemberDto> joinClubDtoList =  clubSqlMapper.selectJoinClubList(user_id);
        for(ClubMemberDto joinClubDto  : joinClubDtoList){
           int clubPk = joinClubDto.getClub_id();
           ClubDto clubDto = clubSqlMapper.selectClubDtoById(clubPk);

           clubDtoList.add(clubDto);
        }

        return clubDtoList;
    }

    // 북마크한 소모임 목록 뽑기
    public List<Map<String, Object>> getBookmarkedClubDtoList(int id){
        List<Map<String, Object>> bookmarkedClubDataList = new ArrayList<>();
        List<ClubDto> bookmarkedClubDtoList = clubSqlMapper.selectBookmarkedClubDtoList(id);
        
        for(ClubDto bookmarkedClubDto : bookmarkedClubDtoList){
            int clubPk = bookmarkedClubDto.getId();
            int regionId = bookmarkedClubDto.getRegion_id();
        
            ClubRegionCategoryDto clubRegionCategoryDto = clubSqlMapper.selectRegionCategoryDtoById(regionId);
            int totalClubMember = clubSqlMapper.countTotalClubMember(clubPk);

            Map<String, Object> bookmarkedClubMap = new HashMap<>();
            bookmarkedClubMap.put("bookmarkedClubDto", bookmarkedClubDto);
            bookmarkedClubMap.put("clubRegionCategoryDto", clubRegionCategoryDto);
            bookmarkedClubMap.put("totalClubMember", totalClubMember);

            bookmarkedClubDataList.add(bookmarkedClubMap);
        }

        return bookmarkedClubDataList;
    }

       //  카테고리 이름 가져오기
    //    public String findPostCategoryName(int id){
    //     String categoryName = clubSqlMapper.selectPostCategoryName(id);

    //      return categoryName;
    // }

    // 각 소모임 게시글 목록 뽑기 (by club_id)
    public  List<Map<String, Object>> getClubPostDtoList(int club_id){
        List<Map<String, Object>>postDetailList = new ArrayList<>();
        List<ClubPostDto> clubPostDtoList = clubSqlMapper.selectClubPostDtoList(club_id);
       
        for(ClubPostDto clubPostDto : clubPostDtoList){
            int userPk = clubPostDto.getUser_id();
            UserDto userDto = clubSqlMapper.selectUserDtoById(userPk);
            ClubPostCategoryDto clubPostCategoryDto = clubSqlMapper.selectPostCategoryDtoById(clubPostDto.getCategory_id());
            int totalComment = clubSqlMapper.countTotalComment(clubPostDto.getId());
            int totalPostLike = clubSqlMapper.countTotalPostLike(clubPostDto.getId());

            Map<String, Object> postDetailMap = new HashMap<>();
            postDetailMap.put("clubPostDto", clubPostDto);
            postDetailMap.put("userDto", userDto);
            postDetailMap.put("clubPostCategoryDto", clubPostCategoryDto);
            postDetailMap.put("totalComment", totalComment);
            postDetailMap.put("totalPostLike", totalPostLike);
            

            postDetailList.add(postDetailMap);
        }

        return postDetailList;
    }

    // 상세 게시글 내용 가져오기 (by id)
    public Map<String, Object> getClubPostData(int id){
        ClubPostDto clubPostDto = clubSqlMapper.selectPostDtoById(id);
        UserDto userDto = clubSqlMapper.selectUserDtoById(clubPostDto.getUser_id());
        int totalComment = clubSqlMapper.countTotalComment(id);

        Map<String, Object> map = new HashMap<>();
        map.put("clubPostDto", clubPostDto);
        map.put("userDto", userDto);
        map.put("totalComment", totalComment);

        return map ;
    }



    // 이미지
        public List<ClubPostImageDto> getPostImageDtoList(){
            List<ClubPostImageDto>clubPostImageDtoList = clubSqlMapper.selectPostImageDtoList();
            // 반복문 돌려서 뺴온 아이디로 또 필요한 데이터 찾기
            return clubPostImageDtoList;
        }

        public List<ClubPostImageDto> getPostImageDtoListById(int id){
            List<ClubPostImageDto> clubPostImageDtoList = clubSqlMapper.selectPostImageDtoListById(id);

            return clubPostImageDtoList;
        }

    
    //
        public UserDto selectUserDtoById(int id){
            UserDto userDto = clubSqlMapper.selectUserDtoById(id);

            return userDto;
        }

    //  게시글 댓글 작성
        public void writeClubPostComment(ClubPostCommentDto clubPostCommentDto){
            clubSqlMapper.insertClubPostCommentDto(clubPostCommentDto);

        }

    // 게시글 대댓글 작성
        public void writeNestedComment(ClubNestedCommentDto clubNestedCommentDto){
            clubSqlMapper.insertNestedCommentDto(clubNestedCommentDto);
        }

    //  게시글 댓글 출력
        public List<Map<String, Object>> getPostCommentDetailList(int id){
            List<Map<String, Object>> commentDetailList = new ArrayList<>();
            List<ClubPostCommentDto> clubPostCommentDtoList = clubSqlMapper.selectPostCommentDto(id);

            for(ClubPostCommentDto clubPostCommentDto : clubPostCommentDtoList){
               UserDto userDto = clubSqlMapper.selectUserDtoById(clubPostCommentDto.getUser_id());
               ClubPostCommentLikeDto clubPostCommentLikeDto = new ClubPostCommentLikeDto();
               clubPostCommentLikeDto.setComment_id(clubPostCommentDto.getId());
               clubPostCommentLikeDto.setUser_id(userDto.getId());

                List<ClubNestedCommentDto> nestedCommentDtoList = clubSqlMapper.selectNestedCommentDtoList(clubPostCommentDto.getId());

                List<Map<String, Object>> nestedCommentDetailList = new ArrayList<>();
                for (ClubNestedCommentDto nestedCommentDto : nestedCommentDtoList) {
                    UserDto userDtoForNestedComment = clubSqlMapper.selectUserDtoById(nestedCommentDto.getUser_id());
                    Map<String, Object> nestedMap = new HashMap<>();
                    nestedMap.put("userDtoForNestedComment", userDtoForNestedComment);
                    nestedMap.put("nestedCommentDto", nestedCommentDto);
                    nestedCommentDetailList.add(nestedMap);
                }

                int confirmCommentLike = clubSqlMapper.confirmCommentLike(clubPostCommentLikeDto);

                Map<String, Object> map = new HashMap<>();
                map.put("isLiked", confirmCommentLike != 0);
                map.put("clubPostCommentDto", clubPostCommentDto);
                map.put("userDto", userDto);
                map.put("nestedCommentDetailList", nestedCommentDetailList);
        
                commentDetailList.add(map);
            }
            return commentDetailList;
        }    

        // 게시글 댓글 좋아요 확인
        public int confirmCommentLike(ClubPostCommentLikeDto clubPostCommentLikeDto){
            int confirmCommentLike = clubSqlMapper.confirmCommentLike(clubPostCommentLikeDto);
            return confirmCommentLike;
        }

        // 게시글 댓글 좋아요 insert
        public void insertCommentLike(ClubPostCommentLikeDto clubPostCommentLikeDto){
            clubSqlMapper.insertCommentLike(clubPostCommentLikeDto);
        }

        // 게시글 댓글 좋아요 delete
        public void deleteCommentLike(ClubPostCommentLikeDto clubPostCommentLikeDto){
            clubSqlMapper.deleteCommentLike(clubPostCommentLikeDto);
        }


    // 소모임 홈 소모임 정보 카드
        public Map<String, Object>  clubDetail(int id){
            ClubDto clubDto = clubSqlMapper.selectClubDtoById(id);
            ClubRegionCategoryDto clubRegionCategoryDto = clubSqlMapper.selectRegionCategoryDtoById(clubDto.getRegion_id());
            int totalClubMember = clubSqlMapper.countTotalClubMember(id);
            UserDto userDto = clubSqlMapper.selectUserDtoById(clubDto.getUser_id());

            Map<String, Object> map = new HashMap<>();
            map.put("clubDto", clubDto);
            map.put("clubRegionCategoryDto", clubRegionCategoryDto);
            map.put("totalClubMember", totalClubMember);
            map.put("userDto", userDto);


            return map;
        }


        //  소모임 북마크 여부 확인
        public int confirmBookmark(ClubBookmarkDto clubBookmarkDto){
            int confirmBookmark  = clubSqlMapper.confirmBookmark(clubBookmarkDto);
            return confirmBookmark;
        }

        //  소모임 북마크 삽입
        public void insertBookmark(ClubBookmarkDto clubBookmarkDto){
            clubSqlMapper.insertBookmark(clubBookmarkDto);
        }

        //  소모임 북마크 삭제
        public void delteBookmarkDto(ClubBookmarkDto clubBookmarkDto){
            clubSqlMapper.deleteBookmark(clubBookmarkDto);
        }

        // 소모임 북마크 개수 집계
        public int countTotalBookmark(int id){
            int totalBookmark = clubSqlMapper.countTotalBookmark(id);

            return totalBookmark;
        }


        // 게시글 조회수 증가
        public void increaseReadCount(int id){
            clubSqlMapper.increaseReadCount(id);
        }

        //  게시글 조회수 집계
        public int totalReadCount(int id){
           int totalReadCount = clubSqlMapper.totalReadCount(id);

            return totalReadCount;
        }

        // 게시글 스크랩 유무 확인
        public int confirmPostLike(ClubPostLikeDto clubPostLikeDto){
            int confirmPostLike = clubSqlMapper.confirmPostLike(clubPostLikeDto);
            
            return confirmPostLike;
        }

        // 게시글 스크랩 삽입
        public void insertPostLike(ClubPostLikeDto clubPostLikeDto){
            clubSqlMapper.insertPostLike(clubPostLikeDto);
        }

        // 게시글 스크랩 삭제
        public void deletePostLike(ClubPostLikeDto clubPostLikeDto){
            clubSqlMapper.deletePostLike(clubPostLikeDto);
        }

        // 게시글 스크랩 개수 집계
        public int countTotalPostLike(int id){
            int totalPostLike = clubSqlMapper.countTotalPostLike(id);

            return totalPostLike;
        }

        // 소모임 가입 여부 확인
        public int checkClubMembership(ClubMemberDto clubMemberDto){
            int isMemberInClub = clubSqlMapper.checkClubMembership(clubMemberDto);

            return isMemberInClub;
        }

        // 앨범
        public List<ClubPostImageDto> selectPostImageDtoByPostId(int id){
            List<ClubPostImageDto> postImageDtoList = clubSqlMapper.selectPostImageDtoByPostId(id);

            return postImageDtoList;
        }

        // 정모 DTO insert
        public void insertClubMeetingDto(ClubMeetingDto clubMeetingDto){
            clubSqlMapper.insertClubMeetingDto(clubMeetingDto);

        }

        // Pk로 ClubDto 찾기
        public ClubDto selectClubDtoById(int id){
            ClubDto clubDto = clubSqlMapper.selectClubDtoById(id);

            return clubDto;
        }

        // 정모 회원 DTO insert
        public void joinMeeting(ClubMeetingMemberDto clubMeetingMemberDto){
            clubSqlMapper.insertClubMeetingMemberDto(clubMeetingMemberDto);

        }

        // 정모 회원 DTO Delete
        public void declineMeeting(int meeting_id, int user_id){
            clubSqlMapper.deleteMeetingMember(meeting_id, user_id);
        }

        // 홈화면 정모 정보 출력하기
        public List<Map<String, Object>> selectClubMeetingDtoList(int id, int user_id){
            List<ClubMeetingDto> clubMeetingDtoList = clubSqlMapper.selectClubMeetingDtoList(id);
            List<Map<String, Object>> meetingDataList = new ArrayList<>();
            for(ClubMeetingDto clubMeetingDto : clubMeetingDtoList){
                int meeting_id = clubMeetingDto.getId();
                int totalJoinMember = clubSqlMapper.countTotalMeetingMember(meeting_id);
                Map<String, Object> meetingDataMap = new HashMap<>();

                ClubMeetingMemberDto clubMeetingMemberDto = clubSqlMapper.selectMeetingMember(meeting_id, user_id);
                String dDay = clubSqlMapper.calculateDdaysForMeetings(meeting_id);
                
                meetingDataMap.put("clubMeetingDto", clubMeetingDto);
                meetingDataMap.put("totalJoinMember", totalJoinMember);
                meetingDataMap.put("clubMeetingMemberDto",clubMeetingMemberDto);
                meetingDataMap.put("dDay", dDay);

                meetingDataList.add(meetingDataMap);
            }
            return meetingDataList;
        }



        // 정모 개수 집계하기
        public int countTotalMeeting(int id){
            int totalMeeting = clubSqlMapper.countTotalMeeting(id);

            return totalMeeting;
        }

        // 소모임 정원 확인
        public int confirmCapacity(int id){
            int capacity = clubSqlMapper.confirmCapacity(id);

            return capacity;
        }

        // 소모임 가입한 회원수 확인
        public int countTotalClubMember(int id){
            int totalMember = clubSqlMapper.countTotalClubMember(id);

            return totalMember;
        }

        // 소모임 가입조건 확인
        public boolean confirmUserCondition(int id, UserDto userDto){
            ClubJoinConditionDto clubJoinConditionDto = clubSqlMapper.selectClubJoinCondition(id);

            String gender = clubJoinConditionDto.getGender();
            int start_year = clubJoinConditionDto.getStart_year();
            int end_year = clubJoinConditionDto.getEnd_year();

            String genderOfUser = userDto.getGender();

            // 출생연도 추출
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(userDto.getBirth());
            int birthYearOfUser = calendar.get(Calendar.YEAR);

            // 조건확인
            boolean isGenderMatch = gender.equals("all") || gender.equals(genderOfUser);
            boolean isYearMatch = birthYearOfUser >= start_year && birthYearOfUser <= end_year;
;
            if(isGenderMatch && isYearMatch){
                return true;
            }else{
                return false;
            }
            
        }

        // 소모임 어제 게시글 수 집계
        public int countYesterdayPost(int id){
            int yesterdayNewPosts = clubSqlMapper.yesterdayPostCount(id);

            return yesterdayNewPosts;
        }

        // 소모임 어제 가입자수 집계
        public int countYesterdayNewMembers(int id){
            int yesterdayNewMembers = clubSqlMapper.yesterdayNewMembers(id);

            return yesterdayNewMembers;
        }

        // 소모임 어제 방문자수 집계
        public int countYesterdayVisit(int id){
           int yesterdayVisitCount = clubSqlMapper.yesterdayVisitCount(id);
           return yesterdayVisitCount;
        }

        // 소모임 오늘 방문자수 집계
        public int countTodayVisit(int id){
            int countTodayVisit = clubSqlMapper.todayVisitCount(id);
            return countTodayVisit;
        }

        //  소모임 권한수정
        public void roleUpdate(ClubMemberDto clubMemberDto){
            clubSqlMapper.roleUpdate(clubMemberDto);
        }

        // 소모임 회원 1명의 UserDto, ClubMemberDto
        public Map<String, Object> findClubMemberData(int club_id, int user_id){
           UserDto userDto = clubSqlMapper.selectUserDtoById(user_id);
           ClubMemberDto clubMemberDto = clubSqlMapper.selectClubMemberDto(club_id, user_id);

           Map<String, Object> memberDataMap = new HashMap<>();
           memberDataMap.put("userDto", userDto);
           memberDataMap.put("clubMemberDto", clubMemberDto);

           return memberDataMap;
        }

        // 방문자 증가 쿼리
        public void increaseVisitCount(ClubVisitDto clubVisitDto){
            ClubVisitDto todayClubVisitDto = clubSqlMapper.selectTodayVisit(clubVisitDto);
            if(todayClubVisitDto == null){
                clubSqlMapper.increaseVisitCount(clubVisitDto);
            }else{
                return;
            }
        }

        // 방문자 조회
        public int selectTodayVisitCount(int club_id){
          return clubSqlMapper.selectTodayVisitCount(club_id);
        }

        // 소모임 메인 인기글
        public List<Map<String,Object>> getHotPosts(){
           List<Map<String,Object>> hotPostsList = new ArrayList<>();
           
           List<ClubPostDto> clubPostDtoList = clubSqlMapper.selectAllPosts();
            for(ClubPostDto clubPostDto : clubPostDtoList){
                Map<String, Object> mapForHotPosts = new HashMap<>();
                int postPk = clubPostDto.getId();
                List<ClubPostImageDto> clubPostImageList = clubSqlMapper.selectPostImageList(postPk);
                String postCategory = clubSqlMapper.selectPostCategoryName(clubPostDto.getCategory_id());
                int userPk = clubPostDto.getUser_id();
                UserDto userDto = clubSqlMapper.selectUserDtoById(userPk);
                ClubDto clubDto = clubSqlMapper.selectClubDtoById(clubPostDto.getClub_id());
                String clubCategory = clubSqlMapper.selectClubCategoryName(clubDto.getCategory_id());

                mapForHotPosts.put("clubPostDto", clubPostDto);
                mapForHotPosts.put("clubPostImageList", clubPostImageList);
                mapForHotPosts.put("postCategory", postCategory);
                mapForHotPosts.put("userDto", userDto);
                mapForHotPosts.put("clubDto", clubDto);
                mapForHotPosts.put("clubCategory", clubCategory);

                hotPostsList.add(mapForHotPosts);

            }
            
                return hotPostsList;
        }

        // 다가오는 정모 일정
        public List<Map<String, Object>> getUpcomingMeetingData(String meeting_data){
           List<ClubMeetingDto> upcomingMeetingDataList = clubSqlMapper.selectUpcomingMeetigDataList(meeting_data);
           List<Map<String, Object>> upcomingMeetingList = new ArrayList<>();

        for(ClubMeetingDto upcomingMeetingDto : upcomingMeetingDataList){
            Map<String, Object> upcomingMeetingMap = new HashMap<>();
            int meetingPk = upcomingMeetingDto.getId();
            int meetingMemberCount = clubSqlMapper.selectMeetingMemberCount(meetingPk);
            ClubDto clubDto = clubSqlMapper.selectClubDtoById(upcomingMeetingDto.getClub_id());
            ClubRegionCategoryDto clubRegionCategoryDto = clubSqlMapper.selectRegionCategoryDtoById(clubDto.getCategory_id());

            upcomingMeetingMap.put("upcomingMeetingDto", upcomingMeetingDto);
            upcomingMeetingMap.put("meetingMemberCount", meetingMemberCount);
            upcomingMeetingMap.put("clubDto", clubDto);
            upcomingMeetingMap.put("clubRegionCategoryDto", clubRegionCategoryDto);

            upcomingMeetingList.add(upcomingMeetingMap);

           }
          
           return upcomingMeetingList;

        }


    }