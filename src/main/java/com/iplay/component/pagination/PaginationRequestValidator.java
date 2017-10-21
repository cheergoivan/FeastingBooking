package com.iplay.component.pagination;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

public class PaginationRequestValidator {
	
	private static final String ERROR_INVALID_PAGE = "Parameter page must be larger than or equal zero!";
	
	private static final String ERROR_INVALID_PAGE_SIZE = "Parameter size must be larger than zero!";
	
	private static final String ERROR_ATTRIBUTE_NOT_SORTABLE = "The attribute %s is not sortable!";
	
	public static PaginationRequestValidationResult validatePageable(Pageable pageable, Class<?> target){
		if(pageable.getPageNumber()<0){
			return PaginationRequestValidationResult.fail(ERROR_INVALID_PAGE);
		}
		if(pageable.getPageSize()<=0){
			return PaginationRequestValidationResult.fail(ERROR_INVALID_PAGE_SIZE);
		}
		Set<String> sortableAtrrs = new HashSet<>();
		Field[] fields = target.getDeclaredFields();
		for(Field f:fields){
			if(f.isAnnotationPresent(PaginationSortable.class)){
				sortableAtrrs.add(f.getName());
			}
		}
		Iterator<Order> iter = pageable.getSort().iterator();
		while(iter.hasNext()){
			Order o = iter.next();
			if(!sortableAtrrs.contains(o.getProperty())){
				return  PaginationRequestValidationResult.fail(String.format(ERROR_ATTRIBUTE_NOT_SORTABLE, o.getProperty()));
			}
		}
		return PaginationRequestValidationResult.succeed();
	}
	
}
