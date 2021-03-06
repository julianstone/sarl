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
package io.sarl.lang.ui.outline;

import io.sarl.lang.sarl.SarlPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;

import com.google.inject.Inject;

/**
 * Customize the filter/sorter of the outline.
 *
 * This filter permits to hide/show the behavior units.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SARLBehaviorUnitOutlineFilter extends AbstractFilterOutlineContribution {

	/** Key in the preference storage for outline components.
	 */
	public static final String PREFERENCE_KEY = "io.sarl.lang.ui.outline.filterBehaviorUnits"; //$NON-NLS-1$

	@Inject private IImageDescriptorHelper imageHelper;

	/** Replies if the given type is a behavior unit.
	 *
	 * @param type - the type to test.
	 * @return <code>true</code> if the given type is for a behavior unit; <code>false</code> otherwise.
	 */
	protected static boolean isBehaviorUnit(EClass type) {
		return type == SarlPackage.Literals.BEHAVIOR_UNIT;
	}

	@Override
	protected boolean apply(IOutlineNode node) {
		if (node instanceof EObjectNode) {
			return !isBehaviorUnit(((EObjectNode) node).getEClass());
		}
		if (node instanceof EStructuralFeatureNode) {
			return !isBehaviorUnit(((EStructuralFeatureNode) node).getEStructuralFeature().eClass());
		}
		return true;
	}

	@Override
	protected void configureAction(Action action) {
		action.setText(Messages.SARLBehaviorUnitOutlineFilter_0);
		action.setDescription(Messages.SARLBehaviorUnitOutlineFilter_0);
		action.setToolTipText(Messages.SARLBehaviorUnitOutlineFilter_0);
		action.setImageDescriptor(this.imageHelper.getImageDescriptor("hide_behavior_units.png")); //$NON-NLS-1$
	}

	@Override
	public String getPreferenceKey() {
		return PREFERENCE_KEY;
	}

}
