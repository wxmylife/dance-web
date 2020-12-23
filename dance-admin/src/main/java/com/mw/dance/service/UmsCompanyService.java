package com.mw.dance.service;

import com.mw.dance.dto.UmsCompanyRegisterParam;
import com.mw.dance.model.UmsCompany;
import java.util.List;

/**
 * @author wxmylife
 */
public interface UmsCompanyService {

  UmsCompany register(UmsCompanyRegisterParam umsRegisterParam);

  /**
   * 审核查询
   */
  List<UmsCompany> list(Integer pageSize, Integer pageNum);

  /**
   * 修改公司审核状态
   *
   * @param id 公司 id
   * @param status 状态
   * @return 操作结果
   */
  int updateCompanyStatus(Long id, int status);

}
