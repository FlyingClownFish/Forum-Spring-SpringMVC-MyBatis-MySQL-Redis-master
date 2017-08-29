package com.fc.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "/store", description = "商店")
@Controller
@RequestMapping("/store")
public class UsersController {
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据用户名获取用户对象", httpMethod = "GET", notes = "根据用户名获取用户对象")
	public String getUserByName(
			@ApiParam(required = true, name = "name", value = "用户名") @PathVariable String name)
			throws Exception {
		return "success";
	}

	@RequestMapping(value = "/{storeid}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取商店信息", notes = "通过商店id获取商店信息")
	public String getStore(String storeid) {
		return "success";
	}

	@ApiOperation(value = "获取商店信息", notes = "通过商店name获取商店信息")
	@ResponseBody
	@RequestMapping(value = "/{storename}", method = RequestMethod.POST)
	public String getStore2(String storeid) {
		return "success";
	}
}