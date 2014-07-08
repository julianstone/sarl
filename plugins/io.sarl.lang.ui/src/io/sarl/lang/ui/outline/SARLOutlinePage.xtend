/*
 * $Id$
 *
 * SARL is an general-purpose agent programming language.
 * More details on http://www.sarl.io
 *
 * Copyright (C) 2014 Sebastian RODRIGUEZ, Nicolas GAUD, Stéphane GALLAND.
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
package io.sarl.lang.ui.outline

import java.util.List
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage

/**
 * Customize the outline page.
 * The outline page is expanding the nodes at the startup.
 */
public class SARLOutlinePage extends OutlinePage {

	protected override List<IOutlineNode> getInitiallyExpandedNodes() {
		var IOutlineNode rootNode = getTreeProvider().createRoot(getXtextDocument());
		var List<IOutlineNode> result = newArrayList(rootNode);
		
		for(IOutlineNode firstLevelNode: rootNode.getChildren()) {
			if(firstLevelNode instanceof EStructuralFeatureNode) { 
				result.add(firstLevelNode)
			}
		} 
		
		return result;
	}
	
}