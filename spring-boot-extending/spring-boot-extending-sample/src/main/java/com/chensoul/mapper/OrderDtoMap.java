/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chensoul.mapper;

import org.modelmapper.PropertyMap;

import org.springframework.stereotype.Component;

@Component
public class OrderDtoMap extends PropertyMap<Order, OrderDto> {

	@Override
	protected void configure() {
		map().setBillingStreet(this.source.getBillingAddress().getStreet());
		map().setBillingCity("REDACTED");
		// Uncomment this to cause failure: map().setBillingCity("Will break");
	}

}
