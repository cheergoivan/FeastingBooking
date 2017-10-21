package com.iplay.component.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark those field as PaginationSortable. PaginationSortable field
 * means the field can represent as parameters in pagination request. For example: 
 * users?page =1&size=20&sort=firstname&sort=lastname,desc
 * 
 * Field firstname and lastname in Entity UserDO must be marked as PaginationSortable. Otherwise, 
 * PaginationRequestValidator will throw BadRequestException.
 *  
 * @author Ivan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface  PaginationSortable {
	
}
