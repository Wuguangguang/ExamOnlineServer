package com.volcano.examonlineserv.mapper;

import com.volcano.examonlineserv.bean.ArticleInfo;
import com.volcano.examonlineserv.bean.ArticleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ArticleInfoMapper {
    long countByExample(ArticleInfoExample example);

    int deleteByExample(ArticleInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    List<ArticleInfo> selectByExample(ArticleInfoExample example);

    ArticleInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleInfo record, @Param("example") ArticleInfoExample example);

    int updateByExample(@Param("record") ArticleInfo record, @Param("example") ArticleInfoExample example);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    @Select("SELECT * FROM articleinfo")
    List<ArticleInfo> getArticles();

    @Select("SELECT * FROM articleinfo ORDER BY (commentNums + zanNums) DESC")
    List<ArticleInfo> getHotArticles();

    @Select("SELECT * FROM articleinfo " +
            "WHERE description LIKE #{content,jdbcType=VARCHAR} OR title LIKE #{content,jdbcType=VARCHAR}")
    List<ArticleInfo> searchArticles(@Param("content") String content);

    @Update("UPDATE articleinfo " +
            "SET commentNums = commentNums + 1 " +
            "WHERE id = #{id,jdbcType=INTEGER}")
    void increaseCommentNums(@Param("id") Integer id);
}