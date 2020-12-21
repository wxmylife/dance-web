package com.mw.dance.service.impl;

import com.mw.dance.mapper.UmsResourceMapper;
import com.mw.dance.model.UmsResource;
import com.mw.dance.service.UmsAdminCacheService;
import com.mw.dance.service.UmsResourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台资源管理 Service 实现类
 * @author wxmylife
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

  @Autowired
  private UmsResourceMapper resourceMapper;

  @Autowired
  private UmsAdminCacheService adminCacheService;

  @Override public int create(UmsResource umsResource) {
    return 0;
  }

  @Override public int update(Long id, UmsResource umsResource) {
    return 0;
  }

  @Override public UmsResource getItem(Long id) {
    return null;
  }

  @Override public int delete(Long id) {
    return 0;
  }

  @Override public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
    return null;
  }

  @Override public List<UmsResource> listAll() {
    return null;
  }
}
