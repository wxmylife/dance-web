package com.mw.dance.mapper;

import com.mw.dance.model.UmsCompany;
import com.mw.dance.model.UmsCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsCompanyMapper {
    long countByExample(UmsCompanyExample example);

    int deleteByExample(UmsCompanyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsCompany record);

    int insertSelective(UmsCompany record);

    List<UmsCompany> selectByExample(UmsCompanyExample example);

    UmsCompany selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsCompany record, @Param("example") UmsCompanyExample example);

    int updateByExample(@Param("record") UmsCompany record, @Param("example") UmsCompanyExample example);

    int updateByPrimaryKeySelective(UmsCompany record);

    int updateByPrimaryKey(UmsCompany record);
}