package com.mw.dance.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.mw.dance.common.exception.Asserts;
import com.mw.dance.dto.UmsCompanyRegisterParam;
import com.mw.dance.mapper.UmsCompanyMapper;
import com.mw.dance.model.UmsAdmin;
import com.mw.dance.model.UmsCompany;
import com.mw.dance.model.UmsCompanyExample;
import com.mw.dance.service.UmsAdminCacheService;
import com.mw.dance.service.UmsAdminService;
import com.mw.dance.service.UmsCompanyService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author wxmylife
 */
@Service
public class UmsCompanyServiceImpl implements UmsCompanyService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UmsCompanyServiceImpl.class);

  @Autowired
  private UmsCompanyMapper companyMapper;

  @Autowired
  private UmsAdminService adminService;

  @Autowired
  private UmsAdminCacheService adminCacheService;

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

  @Override public List<UmsCompany> list(Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    return companyMapper.selectByExample(new UmsCompanyExample());
  }

  @Override public int updateCompanyStatus(Long id, int status) {
    String telephone = SecurityContextHolder.getContext().getAuthentication().getName();
    UmsAdmin umsAdmin = adminCacheService.getAdmin(telephone);

    UmsCompanyExample example = new UmsCompanyExample();
    example.createCriteria().andIdEqualTo(id);
    List<UmsCompany> companyList = companyMapper.selectByExample(example);
    if (CollUtil.isEmpty(companyList)) {
      Asserts.fail("未找到该申请记录");
    }
    UmsCompany company = companyList.get(0);
    company.setStatus(status);
    company.setExecutorId(umsAdmin.getId());
    company.setUpdateTime(new Date());
    return companyMapper.updateByPrimaryKeySelective(company);
  }
}
