package com.bulmeong.basecamp.club.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bulmeong.basecamp.club.dto.ClubBookmarkDto;
import com.bulmeong.basecamp.club.dto.ClubDto;
import com.bulmeong.basecamp.club.dto.ClubJoinConditionDto;
import com.bulmeong.basecamp.club.dto.ClubMemberDto;
import com.bulmeong.basecamp.club.dto.ClubPostCategoryDto;
import com.bulmeong.basecamp.club.dto.ClubPostCommentDto;
import com.bulmeong.basecamp.club.dto.ClubPostDto;
import com.bulmeong.basecamp.club.dto.ClubPostImageDto;
import com.bulmeong.basecamp.club.dto.ClubRegionCategoryDto;
import com.bulmeong.basecamp.club.mapper.ClubSqlMapper;
import com.bulmeong.basecamp.common.dto.ImageDto;
import com.bulmeong.basecamp.common.util.ImageUtil;
import com.bulmeong.basecamp.user.dto.UserDto;
import com.bulmeong.basecamp.user.mapper.UserSqlMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClubService {

    @Autowired ClubSqlMapper clubSqlMapper;
    @Autowired UserSqlMapper userSqlMapper;

    // 소모임 개설하기

    public void createNewClub(ClubDto clubDto, ClubJoinConditionDto clubJoinConditionDto){
        clubSqlMapper.insertClubDto(clubDto);

        int club_id = clubDto.getId();
        // ClubJoinConditionDto clubJoinConditionDtoIncludeClubId  = new ClubJoinConditionDto();
        clubJoinConditionDto.setClub_id(club_id);
        
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

    // 소모임 회원가입
    public void joinClub(ClubMemberDto clubMemberDto){
        clubSqlMapper.insertClubMemberDto(clubMemberDto);
    }

    // 지역 카테고리
    public List<ClubRegionCategoryDto> findRegionCategory(){

        List<ClubRegionCategoryDto> regionCategroyDtoList = clubSqlMapper.selectRegionCategory();

        return regionCategroyDtoList;
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

        List<ClubDto> joinClubDtoList =  clubSqlMapper.selectJoinClubList(user_id);
        return joinClubDtoList;
    }

    // 각 소모임 게시글 목록 뽑기 (by club_id)
    public  List<Map<String, Object>> getClubPostDtoList(int club_id){
        List<Map<String, Object>>postDetailList = new ArrayList<>();
        List<ClubPostDto> clubPostDtoList = clubSqlMapper.selectClubPostDtoList(club_id);
       
        for(ClubPostDto clubPostDto : clubPostDtoList){
            int userPk = clubPostDto.getUser_id();
            UserDto userDto = clubSqlMapper.selectUserDtoById(userPk);
            ClubPostCategoryDto clubPostCategoryDto = clubSqlMapper.selectPostCategoryDtoById(clubPostDto.getCategory_id());

            Map<String, Object> postDetailMap = new HashMap<>();
            postDetailMap.put("clubPostDto", clubPostDto);
            postDetailMap.put("userDto", userDto);
            postDetailMap.put("clubPostCategoryDto", clubPostCategoryDto);

            postDetailList.add(postDetailMap);
        }

        return postDetailList;
    }

    // 상세 게시글 내용 가져오기 (by id)
    public Map<String, Object> getClubPostData(int id){
        ClubPostDto clubPostDto = clubSqlMapper.selectPostDtoById(id);
        UserDto userDto = clubSqlMapper.selectUserDtoById(clubPostDto.getUser_id());

        Map<String, Object> map = new HashMap<>();
        map.put("clubPostDto", clubPostDto);
        map.put("userDto", userDto);

        return map ;
    }



    // 앨범 이미지
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

    //  게시글 댓글 
        public void writeClubPostComment(ClubPostCommentDto clubPostCommentDto){
            clubSqlMapper.insertClubPostCommentDto(clubPostCommentDto);

        }

    //  게시글 댓글 출력
        public List<Map<String, Object>> getPostCommentDetailList(int id){
            List<Map<String, Object>> commentDetailList = new ArrayList<>();
            List<ClubPostCommentDto> clubPostCommentDtoList = clubSqlMapper.selectPostCommentDto(id);
            for(ClubPostCommentDto clubPostCommentDto : clubPostCommentDtoList){
               UserDto userDto = clubSqlMapper.selectUserDtoById(clubPostCommentDto.getUser_id());

               Map<String, Object> map = new HashMap<>();
               map.put("clubPostCommentDto", clubPostCommentDto);
               map.put("userDto", userDto);
            
               commentDetailList.add(map);
            }
            return commentDetailList;
        }    

    // 소모임 홈 소모임 정보 카드
        public Map<String, Object>  clubDetail(int id){
            ClubDto clubDto = clubSqlMapper.selectClubDtoById(id);
            ClubRegionCategoryDto clubRegionCategoryDto = clubSqlMapper.selectRegionCategoryDtoById(clubDto.getRegion_id());

            Map<String, Object> map = new HashMap<>();
            map.put("clubDto", clubDto);
            map.put("clubRegionCategoryDto", clubRegionCategoryDto);

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

        // 소모임 북마크 집계
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
}
