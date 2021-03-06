/**
 * Copyright 2005-2017 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kew.engine.node;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;

/**
 * A Node type which can be used to dynamically generate a route path for a document.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public interface DynamicNode extends Node {

	/**
	 * Invoked when the engine first encounters this DynamicNode.  Should return a {@link DynamicResult} containing 
	 * the first {@link RouteNodeInstance} in the dynamic process.
	 */
	public DynamicResult transitioningInto(RouteContext context, RouteNodeInstance process, RouteHelper helper) throws Exception;
	
	/**
	 * Invoked everytime a node in the dynamic process completes.  Should return a {@link DynamicResult} with the 
	 * {@link RouteNodeInstance} (or instances) of the next node in the dynamic graph.  If this returns no next 
	 * node then the dynamic process will be considered completed.
	 */
	public DynamicResult transitioningOutOf(RouteContext context, RouteHelper helper) throws Exception;
	
}
