package life.majang.community.community.Mapper;

import life.majang.community.community.Dto.QuestionDTO;
import life.majang.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified,creator,comment_count, view_count, like_count, tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtMdoified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);
    @Select("select*from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("select*from question where creator=#{accountId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("accountId")Integer accountId,@Param("offset")Integer offset,@Param("size")Integer size);
    @Select("select count(1) from question where creator=#{accountId}")
    Integer countByUserId(@Param("accountId")Integer accountId);

    Question getById(Integer id);
}
