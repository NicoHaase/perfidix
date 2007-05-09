package org.perfidix.perclipse.util;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class BenchFinder {



	public void findTestsInContainer(Object[] elements, Set result, IProgressMonitor pm) {
		try {
			for (int i= 0; i < elements.length; i++) {
				Object container= BenchSearchEngine.computeScope(elements[i]);
				if (container instanceof IJavaProject) {
					IJavaProject project= (IJavaProject) container;
					findTestsInProject(project, result);
				} else if (container instanceof IPackageFragmentRoot) {
					IPackageFragmentRoot root= (IPackageFragmentRoot) container;
					findTestsInPackageFragmentRoot(root, result);
				} else if (container instanceof IPackageFragment) {
					IPackageFragment fragment= (IPackageFragment) container;
					findTestsInPackageFragment(fragment, result);
				} else if (container instanceof ICompilationUnit) {
					ICompilationUnit cu= (ICompilationUnit) container;
					findTestsInCompilationUnit(cu, result);
				} else if (container instanceof IType) {
					IType type= (IType) container;
					findTestsInType(type, result);
				}
			}			
		} catch (JavaModelException e) {
			// do nothing
		}
	}

	private void findTestsInProject(IJavaProject project, Set result) throws JavaModelException {
		IPackageFragmentRoot[] roots= project.getPackageFragmentRoots();
		for (int i= 0; i < roots.length; i++) {
			IPackageFragmentRoot root= roots[i];
			findTestsInPackageFragmentRoot(root, result);
		}
	}

	private void findTestsInPackageFragmentRoot(IPackageFragmentRoot root, Set result) throws JavaModelException {
		IJavaElement[] children= root.getChildren();
		for (int j= 0; j < children.length; j++) {
			IPackageFragment fragment= (IPackageFragment) children[j];
			findTestsInPackageFragment(fragment, result);
		}
	}

	private void findTestsInPackageFragment(IPackageFragment fragment, Set result) throws JavaModelException {
		ICompilationUnit[] compilationUnits= fragment.getCompilationUnits();
		for (int k= 0; k < compilationUnits.length; k++) {
			ICompilationUnit unit= compilationUnits[k];
			findTestsInCompilationUnit(unit, result);
		}
	}

	private void findTestsInCompilationUnit(ICompilationUnit unit, Set result) throws JavaModelException {
		IType[] types= unit.getAllTypes();
		for (int l= 0; l < types.length; l++) {
			IType type= types[l];
			findTestsInType(type, result);
		}
	}

	private void findTestsInType(IType type, Set result) throws JavaModelException {
		if (isTest(type))
			result.add(type);
	}

	public boolean isTest(IType type) throws JavaModelException {
		//TODO Test for type
		return false;
	}
	
}
