package life.majang.community.community.Mapper;
import life.majang.community.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified,creator,comment_count, view_count, like_count, tag)" +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);
    @Select("select*from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("select*from question where creator=#{accountId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("accountId")Integer accountId,@Param("offset")Integer offset,@Param("size")Integer size);
    @Select("select count(1) from question where creator=#{accountId}")
    Integer countByUserId(@Param("accountId")Integer accountId);
    @Select("Select*from question where id=#{id}")
    Question getById(@Param("id") Integer id);
    @Update("update question set title=#{title},description=#{description},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified} " +
            "where id=#{id}")
    List<Question> update(Question question);
    @Update("update question set title=#{title},description=#{description},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified},view_count=#{viewCount}+1" +
            " where id=#{id}")
    void updateViewCount(Question updateQuestion);
}
