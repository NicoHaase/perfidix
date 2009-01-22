/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     David Saff (saff@mit.edu) - initial API and implementation
 *             (bug 102632: [JUnit] Support for JUnit 4.)
 *******************************************************************************/

package org.perfidix.perclipse.launcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;

import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class PerfidixLaunchDescription {
	
  /** Attributes to match in order to find an existing configuration. */
  public static final String[] ATTRIBUTES_THAT_MUST_MATCH = new String[] {
			IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
			PerfidixLaunchConfiguration.LAUNCH_CONTAINER_ATTR,
			IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
			PerfidixLaunchConfiguration.BENCH_NAME_ATTR};

	public static final String EMPTY= ""; //$NON-NLS-1$

	private static final String DEFAULT_VALUE= ""; //$NON-NLS-1$

	private Map<String, String> fAttributes= new HashMap<String, String>();

	private final IJavaElement fElement;

	private final String fName;

	public PerfidixLaunchDescription(IJavaElement element, String name) {
		fElement= element;
		fName= name;
		setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, getProjectName());
	}

	public void copyAttributesInto(ILaunchConfigurationWorkingCopy wc) {
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                    getProjectName());

		Set definedAttributes= getDefinedAttributes();
		for (Iterator iter= definedAttributes.iterator(); iter.hasNext();) {
			Entry attribute= (Entry) iter.next();
			wc.setAttribute((String) attribute.getKey(), (String) attribute.getValue());
		}
	}

	public boolean equals(Object arg0) {
		PerfidixLaunchDescription desc = (PerfidixLaunchDescription) arg0;
		return areEqual(desc.fElement, fElement) && areEqual(desc.fName, fName)
				&& areEqual(desc.fAttributes, fAttributes);
	}

	public String getAttribute(String attr) {
		if (fAttributes.containsKey(attr))
			return (String) fAttributes.get(attr);
		return DEFAULT_VALUE;
	}

	public String getContainer() {
		return getAttribute(PerfidixLaunchConfiguration.LAUNCH_CONTAINER_ATTR);
	}

	public Set getDefinedAttributes() {
		return fAttributes.entrySet();
	}

	public IJavaElement getElement() {
		return fElement;
	}

	public String getName() {
		return fName;
	}

	public PerfidixLaunchDescription setContainer(String handleIdentifier) {
		return setAttribute(PerfidixLaunchConfiguration.LAUNCH_CONTAINER_ATTR, handleIdentifier);
	}

	public PerfidixLaunchDescription setMainType(String mainType) {
		return setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, mainType);
	}

	public PerfidixLaunchDescription setTestName(String testName) {
		return setAttribute(PerfidixLaunchConfiguration.BENCH_NAME_ATTR, testName);
	}

	public String toString() {
		return "PerfidixLaunchDescription(" + fName + ")"; //$NON-NLS-1$//$NON-NLS-2$
	}

	protected String getProjectName() {
		IJavaProject project = getProject();
		return project == null ? null : project.getElementName();
	}

	boolean attributesMatch(ILaunchConfiguration config) throws CoreException {
		for (int i = 0; i < ATTRIBUTES_THAT_MUST_MATCH.length; i++) {
			if (! configurationMatches(ATTRIBUTES_THAT_MUST_MATCH[i], config)) {
				return false;
			}
		}
		return true;
	}

	boolean configurationMatches(final String attributeName, ILaunchConfiguration config) throws CoreException {
		return config.getAttribute(attributeName, EMPTY).equals(getAttribute(attributeName));
	}

	void setMainType(IType type) {
		setMainType(type.getFullyQualifiedName());
	}

	private boolean areEqual(Object thing, Object otherThing) {
		if (thing == null)
			return otherThing == null;
		return thing.equals(otherThing);
	}

	public IJavaProject getProject() {
		return fElement == null ? null : fElement.getJavaProject();
	}

	private PerfidixLaunchDescription setAttribute(String attr, String value) {
		fAttributes.put(attr, value);
		return this;
	}
}
