package com.mw.dance.service;

import com.mw.dance.dto.UmsCompanyRegisterParam;
import com.mw.dance.model.UmsCompany;

/**
 * @author wxmylife
 */
public interface UmsCompanyService {

  UmsCompany register(UmsCompanyRegisterParam umsRegisterParam);

}
