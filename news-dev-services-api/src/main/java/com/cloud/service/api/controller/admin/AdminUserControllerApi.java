package com.cloud.service.api.controller.admin;

import com.cloud.bo.AdminLoginBO;
import com.cloud.bo.NewAdminBO;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 99141
 */
@Api(value = "管理员admin的维护",tags = {"管理员admin的维护"})
@RequestMapping("/adminMng")
public interface AdminUserControllerApi {

    @ApiOperation(value = "hello方法的接口", notes = "hello方法的接口", httpMethod = "POST")
    @PostMapping("/adminLogin")
    GraceJSONResult adminLogin(@RequestBody AdminLoginBO adminLoginBo,
                                      HttpServletRequest request,
                                      HttpServletResponse response) ;

    @ApiOperation(value = "判断用户名是否存在的接口", notes = "判断用户名是否存在的接口", httpMethod = "POST")
    @PostMapping("/adminIsExist")
    GraceJSONResult adminIsExist(@RequestBody AdminLoginBO adminLoginBo) ;

    @ApiOperation(value = "添加admin的接口", notes = "添加admin的接口", httpMethod = "POST")
    @PostMapping("/addNewAdmin")
    GraceJSONResult addNewAdmin(@RequestBody NewAdminBO newAdminBO,
                                        HttpServletRequest request,
                                        HttpServletResponse response) ;
    @ApiOperation(value = "查询admin列表", notes = "查询admin列表", httpMethod = "POST")
    @PostMapping("/getAdminList")
    GraceJSONResult getAdminList(
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页查询每一页显示的条数", required = false)
            @RequestParam Integer pageSize);

    @ApiOperation(value = "admin账号注销接口", notes = "dmin账号注销接口", httpMethod = "POST")
    @PostMapping("/adminLogout")
    GraceJSONResult adminLogout(@RequestParam String adminId,
                                HttpServletRequest request,
                                HttpServletResponse response) ;
}
