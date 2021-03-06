/*
 * Copyright 2014 Sebastian RODRIGUEZ, Nicolas GAUD, Stéphane GALLAND.
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
package io.sarl.lang.tests.bugs;

import com.google.inject.Inject;

import io.sarl.lang.SARLInjectorProvider;
import io.sarl.lang.sarl.Agent;
import io.sarl.lang.sarl.Attribute;
import io.sarl.lang.sarl.SarlScript;

import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmPrimitiveType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.TypesFactory;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.XNumberLiteral;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper.Result;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import io.sarl.tests.api.AbstractSarlTest;

/**
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@RunWith(XtextRunner.class)
@InjectWith(SARLInjectorProvider.class)
@SuppressWarnings("all")
public class Bug92 extends AbstractSarlTest {
	@Inject
	private ParseHelper<SarlScript> parser;

	@Inject
	private ValidationTestHelper validator;

	@Inject
	private CompilationTestHelper compiler;

	protected static void assertInstance(Class<?> expected, Object actual) {
		if (actual != null && !expected.isInstance(actual)) {
			fail("Unexpected type of object. The object must be a " + expected.getName() + " but it is: " + actual);
		}
	}

	protected static JvmType createType(Class<?> t) {
		JvmGenericType result = TypesFactory.eINSTANCE.createJvmGenericType();
		result.setSimpleName(t.getSimpleName());
		result.setPackageName(t.getPackage().getName());
		return result;
	}

	protected static JvmType createPrimitiveType(Class<?> t) {
		JvmPrimitiveType result = TypesFactory.eINSTANCE.createJvmPrimitiveType();
		result.setSimpleName(t.getName());
		return result;
	}

	@Test
	public void attributeDeclarationSyntax_inferredDouble() throws Exception {
		SarlScript mas = this.parser.parse(multilineString(
				"agent A1 {",
				"  var myDouble = 0d",
				"}"
				));
		this.validator.assertNoErrors(mas);
		assertEquals(1, mas.getElements().size());
		assertInstance(Agent.class, mas.getElements().get(0));
		Agent ag = (Agent) mas.getElements().get(0);
		assertEquals(1, ag.getFeatures().size());
		assertInstance(Attribute.class, ag.getFeatures().get(0));
		Attribute attr = (Attribute) ag.getFeatures().get(0);
		assertEquals("myDouble", attr.getName());
		assertTrue(attr.isWriteable());
		assertNull(attr.getType());
		assertInstance(XNumberLiteral.class, attr.getInitialValue());
		XNumberLiteral literal = (XNumberLiteral) attr.getInitialValue();
		assertEquals("0d", literal.getValue());
	}

	@Test
	public void attributeDeclarationSyntax_Double() throws Exception {
		SarlScript mas = this.parser.parse(multilineString(
				"agent A1 {",
				"  var myDouble : Double = 0d",
				"}"
				));
		this.validator.assertNoErrors(mas);
		assertEquals(1, mas.getElements().size());
		assertInstance(Agent.class, mas.getElements().get(0));
		Agent ag = (Agent) mas.getElements().get(0);
		assertEquals(1, ag.getFeatures().size());
		assertInstance(Attribute.class, ag.getFeatures().get(0));
		Attribute attr = (Attribute) ag.getFeatures().get(0);
		assertEquals("myDouble", attr.getName());
		assertTrue(attr.isWriteable());
		assertInstance(JvmParameterizedTypeReference.class, attr.getType());
		JvmParameterizedTypeReference type = (JvmParameterizedTypeReference) attr.getType();
		assertEquals(createType(Double.class).getQualifiedName(), type.getType().getQualifiedName());
		assertInstance(XNumberLiteral.class, attr.getInitialValue());
		XNumberLiteral literal = (XNumberLiteral) attr.getInitialValue();
		assertEquals("0d", literal.getValue());
	}

	@Test
	public void attributeDeclarationSyntax_double() throws Exception {
		SarlScript mas = this.parser.parse(multilineString(
				"agent A1 {",
				"  var myDouble : double = 0d",
				"}"
				));
		this.validator.assertNoErrors(mas);
		assertEquals(1, mas.getElements().size());
		assertEquals(1, mas.getElements().size());
		assertInstance(Agent.class, mas.getElements().get(0));
		Agent ag = (Agent) mas.getElements().get(0);
		assertEquals(1, ag.getFeatures().size());
		assertInstance(Attribute.class, ag.getFeatures().get(0));
		Attribute attr = (Attribute) ag.getFeatures().get(0);
		assertEquals("myDouble", attr.getName());
		assertTrue(attr.isWriteable());
		assertInstance(JvmParameterizedTypeReference.class, attr.getType());
		JvmParameterizedTypeReference type = (JvmParameterizedTypeReference) attr.getType();
		assertEquals(createPrimitiveType(double.class).getQualifiedName(), type.getType().getQualifiedName());
		assertInstance(XNumberLiteral.class, attr.getInitialValue());
		XNumberLiteral literal = (XNumberLiteral) attr.getInitialValue();
		assertEquals("0d", literal.getValue());
	}

	@Test
	public void attributeDeclarationCompiler_inferredDouble() throws Exception {
		this.compiler.assertCompilesTo(
				multilineString(
						"agent A1 {",
						"  var myDouble = 0d",
						"}"
						),
				multilineString(
						"import io.sarl.lang.annotation.Generated;",
						"import io.sarl.lang.core.Agent;",
						"import java.util.UUID;",
						"",
						"@SuppressWarnings(\"all\")",
						"public class A1 extends Agent {",
						"  protected double myDouble = 0d;",
						"  ",
						"  /**",
						"   * Construct an agent.",
						"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
						"   */",
						"  @Generated",
						"  public A1(final UUID parentID) {",
						"    super(parentID, null);",
						"  }",
						"  ",
						"  /**",
						"   * Construct an agent.",
						"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
						"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
						"   */",
						"  @Generated",
						"  public A1(final UUID parentID, final UUID agentID) {",
						"    super(parentID, agentID);",
						"  }",
						"}",
						""));
	}

	@Test
	public void attributeDeclarationCompiler_Double() throws Exception {
		this.compiler.assertCompilesTo(
				multilineString(
						"agent A1 {",
						"  var myDouble : Double = 0d",
						"}"
						),
				multilineString(
						"import io.sarl.lang.annotation.Generated;",
						"import io.sarl.lang.core.Agent;",
						"import java.util.UUID;",
						"",
						"@SuppressWarnings(\"all\")",
						"public class A1 extends Agent {",
						"  protected Double myDouble = Double.valueOf(0d);",
						"  ",
						"  /**",
						"   * Construct an agent.",
						"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
						"   */",
						"  @Generated",
						"  public A1(final UUID parentID) {",
						"    super(parentID, null);",
						"  }",
						"  ",
						"  /**",
						"   * Construct an agent.",
						"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
						"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
						"   */",
						"  @Generated",
						"  public A1(final UUID parentID, final UUID agentID) {",
						"    super(parentID, agentID);",
						"  }",
						"}",
						""));
	}

	@Test
	public void attributeDeclarationCompiler_double() throws Exception {
		this.compiler.assertCompilesTo(
				multilineString(
						"agent A1 {",
						"  var myDouble : double = 0d",
						"}"
						),
				multilineString(
						"import io.sarl.lang.annotation.Generated;",
						"import io.sarl.lang.core.Agent;",
						"import java.util.UUID;",
						"",
						"@SuppressWarnings(\"all\")",
						"public class A1 extends Agent {",
						"  protected double myDouble = 0d;",
						"  ",
						"  /**",
						"   * Construct an agent.",
						"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
						"   */",
						"  @Generated",
						"  public A1(final UUID parentID) {",
						"    super(parentID, null);",
						"  }",
						"  ",
						"  /**",
						"   * Construct an agent.",
						"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
						"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
						"   */",
						"  @Generated",
						"  public A1(final UUID parentID, final UUID agentID) {",
						"    super(parentID, agentID);",
						"  }",
						"}",
						""));
	}

	@Test
	public void originialCode_withDoubleType() throws Exception {
		final String source = multilineString(
				"capacity ComputeEnergyCapacity {",
				"  def getEnergy(currentTime : Double, deltaTime : Double, wantedEnergy : Double) : Double",
				"  def setVoltage(currentVoltage : Double)",
				"}",
				"agent EntityAgent {",
				"}",
				"agent DeviceAgent extends EntityAgent {",
				"  uses ComputeEnergyCapacity",
				"  requires ComputeEnergyCapacity",
				"  var busTime : Double = 0d",
				"  var wantedIntensity : Double",
				"}",
				"");
		final String expected1 = multilineString(
				"import io.sarl.lang.core.Capacity;",
				"",
				"@SuppressWarnings(\"all\")",
				"public interface ComputeEnergyCapacity extends Capacity {",
				"  public abstract Double getEnergy(final Double currentTime, final Double deltaTime, final Double wantedEnergy);",
				"  ",
				"  public abstract void setVoltage(final Double currentVoltage);",
				"}",
				"");
		final String expected2 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.annotation.ImportedCapacityFeature;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class DeviceAgent extends EntityAgent {",
				"  protected Double busTime = Double.valueOf(0d);",
				"  ",
				"  protected Double wantedIntensity;",
				"  ",
				"  /**",
				"   * See the capacity {@link ComputeEnergyCapacity#getEnergy(java.lang.Double,java.lang.Double,java.lang.Double)}.",
				"   * ",
				"   * @see ComputeEnergyCapacity#getEnergy(java.lang.Double,java.lang.Double,java.lang.Double)",
				"   */",
				"  @Generated",
				"  @ImportedCapacityFeature(ComputeEnergyCapacity.class)",
				"  protected Double getEnergy(final Double currentTime, final Double deltaTime, final Double wantedEnergy) {",
				"    return getSkill(ComputeEnergyCapacity.class).getEnergy(currentTime, deltaTime, wantedEnergy);",
				"  }",
				"  ",
				"  /**",
				"   * See the capacity {@link ComputeEnergyCapacity#setVoltage(java.lang.Double)}.",
				"   * ",
				"   * @see ComputeEnergyCapacity#setVoltage(java.lang.Double)",
				"   */",
				"  @Generated",
				"  @ImportedCapacityFeature(ComputeEnergyCapacity.class)",
				"  protected void setVoltage(final Double currentVoltage) {",
				"    getSkill(ComputeEnergyCapacity.class).setVoltage(currentVoltage);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public DeviceAgent(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public DeviceAgent(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				"");
		final String expected3 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class EntityAgent extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public EntityAgent(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public EntityAgent(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				"");

		this.compiler.compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			@Override
			public void accept(Result r) {
				assertEquals(expected1,r.getGeneratedCode("ComputeEnergyCapacity"));
				assertEquals(expected2,r.getGeneratedCode("DeviceAgent"));
				assertEquals(expected3,r.getGeneratedCode("EntityAgent"));
			}
		});
	}

	@Test
	public void originialCode_withoutDoubleType() throws Exception {
		final String source = multilineString(
				"capacity ComputeEnergyCapacity {",
				"  def getEnergy(currentTime : Double, deltaTime : Double, wantedEnergy : Double) : Double",
				"  def setVoltage(currentVoltage : Double)",
				"}",
				"agent EntityAgent {",
				"}",
				"agent DeviceAgent extends EntityAgent {",
				"  uses ComputeEnergyCapacity",
				"  requires ComputeEnergyCapacity",
				"  var busTime = 0d",
				"  var wantedIntensity : Double",
				"}"
				);
		final String expected1 = multilineString(
				"import io.sarl.lang.core.Capacity;",
				"",
				"@SuppressWarnings(\"all\")",
				"public interface ComputeEnergyCapacity extends Capacity {",
				"  public abstract Double getEnergy(final Double currentTime, final Double deltaTime, final Double wantedEnergy);",
				"  ",
				"  public abstract void setVoltage(final Double currentVoltage);",
				"}",
				"");
		final String expected2 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.annotation.ImportedCapacityFeature;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class DeviceAgent extends EntityAgent {",
				"  protected double busTime = 0d;",
				"  ",
				"  protected Double wantedIntensity;",
				"  ",
				"  /**",
				"   * See the capacity {@link ComputeEnergyCapacity#getEnergy(java.lang.Double,java.lang.Double,java.lang.Double)}.",
				"   * ",
				"   * @see ComputeEnergyCapacity#getEnergy(java.lang.Double,java.lang.Double,java.lang.Double)",
				"   */",
				"  @Generated",
				"  @ImportedCapacityFeature(ComputeEnergyCapacity.class)",
				"  protected Double getEnergy(final Double currentTime, final Double deltaTime, final Double wantedEnergy) {",
				"    return getSkill(ComputeEnergyCapacity.class).getEnergy(currentTime, deltaTime, wantedEnergy);",
				"  }",
				"  ",
				"  /**",
				"   * See the capacity {@link ComputeEnergyCapacity#setVoltage(java.lang.Double)}.",
				"   * ",
				"   * @see ComputeEnergyCapacity#setVoltage(java.lang.Double)",
				"   */",
				"  @Generated",
				"  @ImportedCapacityFeature(ComputeEnergyCapacity.class)",
				"  protected void setVoltage(final Double currentVoltage) {",
				"    getSkill(ComputeEnergyCapacity.class).setVoltage(currentVoltage);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public DeviceAgent(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public DeviceAgent(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				"");
		final String expected3 = multilineString(
				"import io.sarl.lang.annotation.Generated;",
				"import io.sarl.lang.core.Agent;",
				"import java.util.UUID;",
				"",
				"@SuppressWarnings(\"all\")",
				"public class EntityAgent extends Agent {",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   */",
				"  @Generated",
				"  public EntityAgent(final UUID parentID) {",
				"    super(parentID, null);",
				"  }",
				"  ",
				"  /**",
				"   * Construct an agent.",
				"   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.",
				"   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.",
				"   */",
				"  @Generated",
				"  public EntityAgent(final UUID parentID, final UUID agentID) {",
				"    super(parentID, agentID);",
				"  }",
				"}",
				"");

		this.compiler.compile(source, new IAcceptor<CompilationTestHelper.Result>() {
			@Override
			public void accept(Result r) {
				assertEquals(expected1,r.getGeneratedCode("ComputeEnergyCapacity"));
				assertEquals(expected2,r.getGeneratedCode("DeviceAgent"));
				assertEquals(expected3,r.getGeneratedCode("EntityAgent"));
			}
		});
	}

}
