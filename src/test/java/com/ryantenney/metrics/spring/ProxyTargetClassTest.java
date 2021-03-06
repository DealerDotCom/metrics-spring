/*
 * Copyright 2012 Ryan W Tenney (http://ryan.10e.us)
 *            and Martello Technologies (http://martellotech.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ryantenney.metrics.spring;

import com.yammer.metrics.annotation.Timed;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Purpose of test:
 * Tests `proxy-target-class` option in `<metrics:annotation-driven />`
 */
public class ProxyTargetClassTest {

	@Test(expected = BeanCreationException.class)
	public void negativeContextLoadingTest() {
		new ClassPathXmlApplicationContext("classpath:proxy-target-class-disabled.xml");
	}

	@Test
	public void positiveContextLoadingTest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:proxy-target-class-enabled.xml");
		Assert.assertNotNull("Expected to be able to get ProxyTargetClass by class.", ctx.getBean(ProxyTargetClass.class));
		Assert.assertNotNull("Expected to be able to get ProxyTargetClass from AutowiredCollaborator.", ctx.getBean(AutowiredCollaborator.class).getDependency());
	}


	/**
	 * Empty interface to trick Spring.
	 */
	public interface UselessInterface {}


	public static class ProxyTargetClass implements UselessInterface {

		@Timed
		public void timed() {}

	}


	public static class AutowiredCollaborator {

		@Autowired
		private ProxyTargetClass dependency;

		public ProxyTargetClass getDependency() {
			return dependency;
		}

	}


}
