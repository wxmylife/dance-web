package com.mw.dance.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.mw.dance.mapper.UmsResourceMapper;
import com.mw.dance.model.UmsResource;
import com.mw.dance.model.UmsResourceExample;
import com.mw.dance.service.UmsAdminCacheService;
import com.mw.dance.service.UmsResourceService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台资源管理 Service 实现类
 *
 * @author wxmylife
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

  @Autowired
  private UmsResourceMapper resourceMapper;

  @Autowired
  private UmsAdminCacheService adminCacheService;

  @Override public int create(UmsResource umsResource) {
    umsResource.setCreateTime(new Date());
    return resourceMapper.insert(umsResource);
  }

  @Override public int update(Long id, UmsResource umsResource) {
    umsResource.setId(id);
    int count = resourceMapper.updateByPrimaryKeySelective(umsResource);
    //
    return count;
  }

  @Override public UmsResource getItem(Long id) {
    return resourceMapper.selectByPrimaryKey(id);
  }

  @Override public int delete(Long id) {
    int count = resourceMapper.deleteByPrimaryKey(id);
    // adminCacheService.
    return count;
  }

  @Override public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
    PageHelper.startPage(pageNum, pageSize);
    UmsResourceExample example = new UmsResourceExample();
    UmsResourceExample.Criteria criteria = example.createCriteria();
    if (categoryId != null) {
      criteria.andCategoryIdEqualTo(categoryId);
    }
    if (StrUtil.isNotEmpty(nameKeyword)) {
      criteria.andNameLike('%' + nameKeyword + '%');
    }
    if (StrUtil.isNotEmpty(urlKeyword)) {
      criteria.andUrlLike('%' + urlKeyword + '%');
    }
    return resourceMapper.selectByExample(example);
  }

  @Override public List<UmsResource> listAll() {
    return resourceMapper.selectByExample(new UmsResourceExample());
  }
}
