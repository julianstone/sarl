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
package io.sarl.lang.ui;

import io.sarl.lang.ui.bugfixes.XtextBugFixes438191And438233;
import io.sarl.lang.ui.highlighting.SARLHighlightingCalculator;
import io.sarl.lang.ui.outline.SARLBehaviorUnitOutlineFilter;
import io.sarl.lang.ui.outline.SARLFieldOutlineFilter;
import io.sarl.lang.ui.outline.SARLOperationOutlineFilter;
import io.sarl.lang.ui.outline.SARLOutlineNodeComparator;
import io.sarl.lang.ui.outline.SARLOutlinePage;
import io.sarl.lang.ui.validation.SARLUIValidator;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineFilterAndSorter.IComparator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SARLUiModule extends AbstractSARLUiModule {

	/** Construct an injection module for the UI of SARL.
	 *
	 * @param plugin - the Eclipse plugin.
	 */
	public SARLUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		assert (super.bindISemanticHighlightingCalculator().isAssignableFrom(SARLHighlightingCalculator.class))
			: "The class SARLHighlightingCalculator does not extend the " //$NON-NLS-1$
				+ "class provided by default by Xbase"; //$NON-NLS-1$
		//
		return SARLHighlightingCalculator.class;
	}

	@Override
	@org.eclipse.xtext.service.SingletonBinding(eager = true)
	public Class<? extends org.eclipse.xtext.xbase.ui.validation.XbaseUIValidator> bindXbaseUIValidator() {
		assert (super.bindXbaseUIValidator().isAssignableFrom(SARLUIValidator.class))
			: "The class SARLUIValidator does not extend the class " //$NON-NLS-1$
				+ "provided by default by Xbase"; //$NON-NLS-1$
		//
		return SARLUIValidator.class;
	}

	@Override
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return SARLOutlinePage.class;
	}

	@Override
	public Class<? extends IComparator> bindOutlineFilterAndSorter$IComparator() {
		return SARLOutlineNodeComparator.class;
	}

	/** Configure the contribution to the filtering operations.
	 *
	 * @param binder - the Google binder.
	 */
	@SuppressWarnings("static-method")
	public void configureFilterOperationsContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(
				Names.named("SARLFieldOutlineFilter")) //$NON-NLS-1$
				.to(SARLFieldOutlineFilter.class);
		binder.bind(IOutlineContribution.class).annotatedWith(
				Names.named("SARLOperationOutlineFilter")) //$NON-NLS-1$
				.to(SARLOperationOutlineFilter.class);
		binder.bind(IOutlineContribution.class).annotatedWith(
				Names.named("SARLBehaviorUnitOutlineFilter")) //$NON-NLS-1$
				.to(SARLBehaviorUnitOutlineFilter.class);
	}

	@Override
	public Class<? extends org.eclipse.xtext.common.types.xtext.ui.ITypesProposalProvider> bindITypesProposalProvider() {
		return XtextBugFixes438191And438233.class;
	}

}
