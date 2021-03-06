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
package io.sarl.util;

import java.io.Serializable;
import java.security.acl.Permission;

import io.sarl.lang.core.SpaceSpecification;

/**
 * Specification of an {@link RestrictedAccessEventSpace} where agents are register and unregister themselves
 * according to a access restriction.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface RestrictedAccessEventSpaceSpecification extends SpaceSpecification<RestrictedAccessEventSpace> {

	/** Defines the permission to access to a {@link RestrictedAccessEventSpace space}.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	public static class RegistrationPermission implements Permission, Serializable {

		private static final long serialVersionUID = 7533477485005863476L;

		/**
		 */
		public RegistrationPermission() {
			//
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			return toString().equals(obj.toString());
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public String toString() {
			return "io.sarl.util.RestrictedAccessEventSpace.register"; //$NON-NLS-1$
		}

	}

}
