package com.iplay.web.order;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.component.dateFormat.FeastBookingDateFormatter;
import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.dto.ApiResponse;
import com.iplay.dto.ApiResponseMessage;
import com.iplay.dto.order.OrderDTO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.service.exception.InvalidRequestParametersException;
import com.iplay.service.order.OrderService;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.order.PostPaymentVO;
import com.iplay.vo.order.PostReservationVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FeastBookingDateFormatter dateFormatter;
	
	@ApiOperation(notes = "用户新建一个咨询单，返回包含订单id的ApiResponse。如果ApiResponse为false则表示该推介人用户不存在。", value = "")
	@PostMapping
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public ApiResponse<?> postOrder(@Valid @ApiParam("咨询单实体") @RequestBody PostReservationVO vo,
			@AuthenticationPrincipal UserContext context) {
		SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		int rs = orderService.addReservation(authenticatedUser, vo);
		if (rs == -1)
			return ApiResponse.createFailApiResponse(ApiResponseMessage.RECOMMENDER_NOT_FOUND);
		return ApiResponse.createSuccessApiResponse(rs);
	}

	@ApiOperation(notes = "用户在订单处于咨询状态时修改经理人，返回ApiResponse。如果ApiResponse为false则表示该经理人用户不存在。"
			+ "返回404表示此订单不存在。返回403表示此时订单不处于咨询状态或者订单所有者与token代表的用户不相符。", value = "")
	@PutMapping("/{id}/manager")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public ApiResponse<String> putManager(@ApiParam("订单id") @PathVariable int id,
			@ApiParam("经理人用户名") @RequestParam String username, @AuthenticationPrincipal UserContext context) {
		SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		boolean rs = orderService.fillManager(authenticatedUser, id, username);
		if (rs)
			return ApiResponse.SUCCESSFUL_RESPONSE_WITHOUT_MESSAGE;
		return ApiResponse.createFailApiResponse(ApiResponseMessage.MANAGER_NOT_FOUND);
	}
	
	@ApiOperation(notes = "用户在订单处于咨询状态时修改酒席日期，返回Boolean。日期格式：yyyy.MM.dd"
			+ "返回404表示此订单不存在。返回403表示此时订单不处于咨询状态或者订单所有者与token代表的用户不相符。", value = "")
	@PutMapping("/{id}/feasting_date")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public boolean putFeastingDate(@ApiParam("订单id") @PathVariable int id,
			@ApiParam("确定的酒席日期") @RequestParam String value, @AuthenticationPrincipal UserContext context) {
		SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		try {
			if(value.indexOf("-")==-1) {
				dateFormatter.parseToDateWithDefaultDateFormat(value);
			}else {
				for(String date: value.split("-")) {
					dateFormatter.parseToDateWithDefaultDateFormat(date);
				}
			}
		} catch (ParseException e) {
			throw new InvalidRequestParametersException("The date format must be "+dateFormatter.getDefaultDateFormat()+"!");
		}
		return orderService.fillFeastingDate(authenticatedUser, id, value);
	}

	@ApiOperation(notes = "管理员修改订单状态，返回修改后的OrderStatus。无参数表示使订单进入下一个状态（状态集合：CANCELED, CONSULTING, RESERVED, FEASTED, CASHBACK, TO_BE_REVIEWD, DONE;）。"
			+ "有参数value=CANCELED表示取消订单。返回404表示此订单不存在。返回403表示当前状态已是最后一个状态，因此操作失败。返回400表示参数传递错误", value = "")
	@PutMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public OrderStatus moveToNextStatus(@ApiParam("订单id") @PathVariable int id,
			@ApiParam("可选参数，暂时值只能为CANCELED")@RequestParam(value = "value", required = false) OrderStatus.ModifiableOrderStatus status) {
		if (status != null) {
			return orderService.updateStatus(id, status.toOrderStatus());
		}
		return orderService.moveToNextStatus(id);
	}

	@ApiOperation(notes = "用户上传合同照片，返回boolean。返回404表示此订单不存在。返回403表示此订单不处于商议/看场地状态或者订单所有者与token代表的用户不相符。", value = "")
	@PostMapping("/{id}/contract")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public boolean uploadContract(@ApiParam("订单id") @PathVariable int id, @ApiParam("File[] files") PostFilesVO vo,
			@AuthenticationPrincipal UserContext context) {
		SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		return orderService.uploadContract(authenticatedUser, id, vo);
	}

	@ApiOperation(notes = "用户上传支付凭证，返回boolean。返回404表示此订单不存在。返回403表示此订单不处于已预订状态或者订单所有者与token代表的用户不相符。", value = "")
	@PostMapping("/{id}/payment")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public boolean uploadPayment(@ApiParam("订单id") @PathVariable int id, 
			@ApiParam("payment对象，包含两个属性：boolean amountPaid，File[] files")PostPaymentVO postPaymentVO,
			@AuthenticationPrincipal UserContext context) {
		SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		return orderService.fillPayment(authenticatedUser, id, postPaymentVO);
	}
	
	@ApiOperation(notes = "用户根據訂單ID獲得訂單詳細信息，詳細信息不包括合同文件和支付憑證文件", value = "")
	@PostMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
	public OrderDTO findById(@ApiParam("订单id") @PathVariable int id,
			@AuthenticationPrincipal UserContext context) {
		//SimplifiedUser authenticatedUser = new SimplifiedUser(context.getUserId(), context.getUsername());
		return null;
	}
	
	
	

}
