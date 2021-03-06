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
package io.sarl.maven.compiler;

import java.text.MessageFormat;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/** Cleaning mojo for compiling SARL.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @goal clean
 * @phase clean
 * @requiresDependencyResolution compile
 */
public class CleanMojo extends AbstractSarlMojo {

	@Override
	public void executeMojo() throws MojoExecutionException, MojoFailureException {
		String cleanerGroupId = MavenHelper.getConfig("cleaner.groupId"); //$NON-NLS-1$
		String cleanerArtifactId = MavenHelper.getConfig("cleaner.artifactId"); //$NON-NLS-1$
		String cleanerVersion = this.mavenHelper.getPluginDependencyVersion(
				cleanerGroupId, cleanerArtifactId, "clean"); //$NON-NLS-1$
		String cleanerMojo = MavenHelper.getConfig("cleaner.mojo"); //$NON-NLS-1$
		executeMojo(
				cleanerGroupId, cleanerArtifactId, cleanerVersion, cleanerMojo,
				MessageFormat.format(
						MavenHelper.getConfig("cleaner.configuration"), //$NON-NLS-1$
						getOutput().getAbsolutePath(),
						getTestOutput().getAbsolutePath()));
	}

}
