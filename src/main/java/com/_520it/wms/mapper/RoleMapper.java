package com._520it.wms.mapper;

import com._520it.wms.domain.Role;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

	void save(Role role);

	void delete(long id);

	void update(Role role);

	Role get(long id);

	List<Role> listAll();

	int queryForCount(QueryObject qo);

	List<Role> queryForList(QueryObject qo);

    void saveRelation(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	void deletePermissionRelation(Long roleId);

    void deleteMenuRelations(Long roleId);

	void saveMenuRelations(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
}
