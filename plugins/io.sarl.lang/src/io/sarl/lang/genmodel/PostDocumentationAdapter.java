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
package io.sarl.lang.genmodel;


import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtext.xbase.compiler.DocumentationAdapter;

/** Postfix documentation for an Ecore element.
 *
 * The prefix document is supported by {@link DocumentationAdapter}.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @see DocumentationAdapter
 */
public class PostDocumentationAdapter extends AdapterImpl {

	private String documentation;

	/** Replies the documentation.
	 *
	 * @return the documentation.
	 */
	public String getDocumentation() {
		return this.documentation;
	}

	/** Change the documentation.
	 *
	 * @param documentation - the comment.
	 */
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == PostDocumentationAdapter.class;
	}

}
