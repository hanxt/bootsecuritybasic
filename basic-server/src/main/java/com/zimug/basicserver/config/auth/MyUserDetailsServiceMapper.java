package com.zimug.basicserver.config.auth;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MyUserDetailsServiceMapper {

    //根据userID查询用户信息
    @Select("SELECT username,password,enabled\n" +
            "FROM sys_user u\n" +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    MyUserDetails findByUserName(@Param("userId") String userId);

    //根据userID查询用户角色列表
    @Select("SELECT role_code\n" +
            "FROM sys_role r\n" +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN sys_user u ON u.id = ur.user_id\n" +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    List<String> findRoleByUserName(@Param("userId") String userId);


    //根据用户角色查询用户权限
    @Select({
      "<script>",
         "SELECT url " ,
         "FROM sys_menu m " ,
         "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id " ,
         "LEFT JOIN sys_role r ON r.id = rm.role_id ",
         "WHERE r.role_code IN ",
         "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>",
            "#{roleCode}",
         "</foreach>",
      "</script>"
    })
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);

}