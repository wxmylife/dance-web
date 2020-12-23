package com.mw.dance.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mw.dance.common.exception.Asserts;
import com.mw.dance.dto.UmsCompanyRegisterParam;
import com.mw.dance.mapper.UmsCompanyMapper;
import com.mw.dance.model.UmsCompany;
import com.mw.dance.model.UmsCompanyExample;
import com.mw.dance.service.UmsCompanyService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxmylife
 */
@Service
public class UmsCompanyServiceImpl implements UmsCompanyService {

  @Autowired
  private UmsCompanyMapper companyMapper;

  @Override public UmsCompany register(UmsCompanyRegisterParam umsRegisterParam) {
    // 查询该用户是否申请过
    UmsCompanyExample example = new UmsCompanyExample();
    example.createCriteria().andManagerIdEqualTo(umsRegisterParam.getAdminId());
    List<UmsCompany> umsCompanies = companyMapper.selectByExample(example);
    if (CollUtil.isNotEmpty(umsCompanies)) {
      Asserts.fail("该用户已经申请过了");
    }
    // 注册公司
    UmsCompany umsCompany = new UmsCompany();
    umsCompany.setTelephone(umsRegisterParam.getTelephone());
    umsCompany.setCreateTime(new Date());
    umsCompany.setAddress(umsRegisterParam.getAddress());
    umsCompany.setProvince(umsRegisterParam.getProvince());
    umsCompany.setCity(umsRegisterParam.getCity());
    umsCompany.setDistrict(umsRegisterParam.getDistrict());
    umsCompany.setManagerId(umsRegisterParam.getAdminId());
    umsCompany.setBusinessLicense(umsRegisterParam.getBusinessLicense());
    umsCompany.setBusinessLicenseNumber(umsRegisterParam.getBusinessLicenseNumber());
    umsCompany.setStatus(0);
    umsCompany.setLegal(umsRegisterParam.getLegalName());
    umsCompany.setName(umsRegisterParam.getName());
    companyMapper.insert(umsCompany);
    return umsCompany;
  }
}
