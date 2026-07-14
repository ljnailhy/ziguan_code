package com.jk.asset.utils.limit;

import com.google.common.collect.Lists;
import com.jk.common.model.Result;
import com.jk.infrastructure.enums.DataAuthorityEnum;
import com.jk.infrastructure.model.dto.SysDataAuthorityDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.infrastructure.model.dto.SysUserOrgDTO;
import com.jk.infrastructure.model.request.page.SysUserOrgPageRequest;
import com.jk.infrastructure.model.request.sys.authority.SysDataAuthorityPageRequest;
import com.jk.infrastructure.model.request.sys.authority.SysDataAuthorityRequest;
import com.jk.infrastructure.model.request.sys.role.SysRoleUpdateRequest;
import com.jk.service.client.SysDataAuthorityClient;
import com.jk.service.client.SysOrgClient;
import com.jk.service.client.SysUserOrgClient;
import com.jk.service.utils.PlatformUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户权限工具类
 *
 * @author WangShuai
 * @since 2024/3/28 18:26
 **/
@Slf4j
@Component
public class AssetUserLimitsUtils {

  @Autowired
  private PlatformUserUtils platformUserUtils;
  @Autowired
  protected SysOrgClient orgClient;
  @Autowired
  protected SysDataAuthorityClient sysDataAuthorityClient;
  @Autowired
  protected SysUserOrgClient sysUserOrgClient;

  public Set<Long> getUserLimitsOrg(){

    Set<Long> visibleOrg = new HashSet<>();

    SysUserDTO currentUser = platformUserUtils.getCurrentUser();
    //  判断如果没有角色返回负一
    if (ObjectUtils.isEmpty(currentUser.getRoleList())) {
      visibleOrg.add(-1L);
      return visibleOrg;
    }
    List<SysRoleUpdateRequest> roleList = currentUser.getRoleList();
    //  判断角色权限是否存在查看全部数据
    boolean isAll = roleList
        .stream()
        .anyMatch(item -> DataAuthorityEnum.ALL.equals(item.getDataAuthority()));

    //  如果看全部返回null
    if (isAll){
      return visibleOrg;
    }

    //  只找选中的部门，下面的部门不查询
    for (SysRoleUpdateRequest sysRoleUpdateRequest : roleList) {
      if (DataAuthorityEnum.DEPT.equals(sysRoleUpdateRequest.getDataAuthority())) {
        visibleOrg.add(currentUser.getDeptId());
        //  部门数据查询该部门下面的全部人员
        List<Long> orgIds = Lists.newArrayList();
        orgIds.add(currentUser.getDeptId());
        List<Long> orgUserIds = getOrgUserIds(orgIds);
        if (!ObjectUtils.isEmpty(orgUserIds)) {
          visibleOrg.addAll(orgUserIds);
        }
      }
      if (DataAuthorityEnum.CUSTOM.equals(sysRoleUpdateRequest.getDataAuthority())) {
        SysDataAuthorityPageRequest sysDataAuthorityPageRequest = new SysDataAuthorityPageRequest();
        sysDataAuthorityPageRequest.setDoType("ROLE");
        sysDataAuthorityPageRequest.setDoId(sysRoleUpdateRequest.getId());
        Result<List<SysDataAuthorityDTO>> dataAuthorityClientAll = sysDataAuthorityClient.findAll(1, 10000, sysDataAuthorityPageRequest);
        if (ObjectUtils.isEmpty(dataAuthorityClientAll) || ObjectUtils.isEmpty(dataAuthorityClientAll.getData())) {
          visibleOrg = new HashSet<>();
          visibleOrg.add((long) -1);
          return visibleOrg;
        }
        List<Long> org = dataAuthorityClientAll.getData()
            .stream()
            .filter(item -> "DEPT".equals(item.getDoType()))
            .map(SysDataAuthorityRequest::getDoId)
            .collect(Collectors.toList());
        visibleOrg.addAll(org);

        //  部门数据查询该部门下面的全部人员
        List<Long> orgUserIds = getOrgUserIds(org);
        if (!ObjectUtils.isEmpty(orgUserIds)) {
          visibleOrg.addAll(orgUserIds);
        }
      }
      if (DataAuthorityEnum.USER.equals(sysRoleUpdateRequest.getDataAuthority())) {
        visibleOrg.add(currentUser.getId());
      }
    }
    return visibleOrg;
  }

  /**
   * 根据部门id查询该部门下面的人员
   * @param orgIds  orgIds
   * @return java.util.List<java.lang.Long>
   * @author wangshuai
   * @since 2024/8/1 18:04
   **/
  public List<Long> getOrgUserIds(List<Long> orgIds){

    SysUserOrgPageRequest sysUserOrgPageRequest = new SysUserOrgPageRequest();
    sysUserOrgPageRequest.setOrgIds(orgIds);

    Result<List<SysUserOrgDTO>> sysUserOrgClientAll = sysUserOrgClient.findAll(1, 10000, sysUserOrgPageRequest);

    if (sysUserOrgClientAll.succeedWithData()){
      return sysUserOrgClientAll.getData().stream().map(item -> item.getUserId()).collect(Collectors.toList());
    }
    return Lists.newArrayList();
  }

}
