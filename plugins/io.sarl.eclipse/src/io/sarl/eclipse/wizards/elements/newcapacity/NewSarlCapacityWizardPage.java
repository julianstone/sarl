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
package io.sarl.eclipse.wizards.elements.newcapacity;

import io.sarl.eclipse.SARLConfig;
import io.sarl.eclipse.SARLEclipsePlugin;
import io.sarl.eclipse.wizards.elements.AbstractNewSarlElementWizardPage;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.genmodel.GeneratedCode;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page for creating a new SARL behavior.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class NewSarlCapacityWizardPage extends AbstractNewSarlElementWizardPage {

	/**
	 */
	public NewSarlCapacityWizardPage() {
		super(INTERFACE_TYPE, Messages.NewSarlCapacity_0);
		setTitle(Messages.NewSarlCapacity_0);
		setDescription(Messages.NewSarlCapacityWizardPage_0);
		setImageDescriptor(SARLEclipsePlugin.getDefault().getImageDescriptor(SARLConfig.NEW_CAPACITY_WIZARD_DIALOG_IMAGE));
	}

	@Override
	public void createPageControls(Composite parent) {
		createSuperClassControls(parent, COLUMNS);
	}

	@Override
	protected void doStatusUpdate() {
		IStatus[] status = new IStatus[] {
				this.fContainerStatus,
				this.fPackageStatus,
				this.fTypeNameStatus,
				this.fSuperClassStatus,
		};
		updateStatus(status);
	}

	@Override
	protected void getTypeContent(Resource ecoreResource, String typeComment) throws CoreException {
		GeneratedCode code = this.sarlGenerator.createScript(ecoreResource, getPackageFragment().getElementName());
		io.sarl.lang.sarl.Capacity capacity = this.sarlGenerator.createCapacity(code, getTypeName(), getSuperClass());
		this.sarlGenerator.attachComment(code, capacity, typeComment);
		code.finalizeScript();
	}

	@Override
	protected String getExistingElementErrorMessage() {
		return Messages.NewSarlCapacityWizardPage_1;
	}

	@Override
	protected String getInvalidSubtypeErrorMessage() {
		return Messages.NewSarlCapacityWizardPage_2;
	}

	@Override
	protected IType getRootSuperType() throws JavaModelException {
		return getJavaProject().findType(Capacity.class.getName());
	}

}
