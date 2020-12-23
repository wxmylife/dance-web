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
   * 审核插叙
   */
  List<UmsCompany> list(Integer pageSize, Integer pageNum);

}
