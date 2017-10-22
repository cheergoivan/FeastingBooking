package com.iplay.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.dto.order.SimplifiedOrderDTO;
import com.iplay.service.order.OrderService;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.order.OrderStatusVO;
import com.iplay.web.configuration.PaginationConfig;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/user")
public class AuthenticatedUserController {
	
	@Autowired
	private OrderService orderService;
	
	@ApiOperation(notes = "用户获得所有与自己相关的订单,包含一个status参数, status有两个可选项：UNFINISHED, FINISHED。例子："
			+ "/api/user/orders?status=UNFINISHED&page=0。默认每页返回10条数据（也可以通过size指定每页的数据条数），"
			+ "按照orderTime降序排列。具体Order对象所有的属性请参考GET::/api/order/{id}", value = "")
	@GetMapping("/orders")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public Page<SimplifiedOrderDTO> postOrder(@ApiParam("订单状态") @RequestParam("status") OrderStatusVO vo, 
			@ApiParam("分页参数，可选。例子：page=0&size=10&sort=orderTime,desc。每次请求时添加page参数即可，"
					+ "默认情况下size=10, sort=orderTime,desc.")@PageableDefault(value = PaginationConfig.OORDERS_PER_PAGE_FOR_ORDINARY_USER, sort = { "orderTime" }, 
			direction = Sort.Direction.DESC) Pageable pageable, 
			@AuthenticationPrincipal UserContext context){
		SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		return orderService.listOrders(authenticatedUser, vo, pageable);
	}
	
}
