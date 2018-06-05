/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor;

import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.*;

import java.lang.annotation.Annotation;

/**
 * This interface defines the visitor for the Spoon metamodel, as defined in
 * {@link spoon.reflect.declaration}, {@link spoon.reflect.code}, and
 * {@link spoon.reflect.reference}. It declares a visit method for each
 * element of the AST.
 */
public interface CtVisitor {
	/**
	 * Visits an annotation.
	 */
	<A extends Annotation> void visitCtAnnotation(CtAnnotation<A> annotation);

	/**
	 * Visits a code snippet expression.
	 */
	<T> void visitCtCodeSnippetExpression(CtCodeSnippetExpression<T> expression);

	/**
	 * Visits a code snippet statement.
	 */
	void visitCtCodeSnippetStatement(CtCodeSnippetStatement statement);

	/**
	 * Visits an annotation type declaration.
	 */
	<A extends Annotation> void visitCtAnnotationType(CtAnnotationType<A> annotationType);

	/**
	 * Visits an anonymous executable.
	 */
	void visitCtAnonymousExecutable(CtAnonymousExecutable anonymousExec);

	/**
	 * Visits an array read access.
	 */
	<T> void visitCtArrayRead(CtArrayRead<T> arrayRead);

	/**
	 * Visits an array write access.
	 */
	<T> void visitCtArrayWrite(CtArrayWrite<T> arrayWrite);

	/**
	 * Visits a reference to an array type.
	 */
	<T> void visitCtArrayTypeReference(CtArrayTypeReference<T> reference);

	/**
	 * Visits an assert.
	 */
	<T> void visitCtAssert(CtAssert<T> asserted);

	/**
	 * Visits an assignment.
	 */
	<T, A extends T> void visitCtAssignment(CtAssignment<T, A> assignement);

	/**
	 * Visits a binary operator.
	 */
	<T> void visitCtBinaryOperator(CtBinaryOperator<T> operator);

	/**
	 * Visits a block of code.
	 */
	<R> void visitCtBlock(CtBlock<R> block);

	/**
	 * Visits a <code>break</code> statement.
	 */
	void visitCtBreak(CtBreak breakStatement);

	/**
	 * Visits a <code>case</code> clause.
	 */
	<S> void visitCtCase(CtCase<S> caseStatement);

	/**
	 * Visits a <code>catch</code> clause.
	 */
	void visitCtCatch(CtCatch catchBlock);

	/**
	 * Visits a class declaration.
	 */
	<T> void visitCtClass(CtClass<T> ctClass);

	/**
	 * Visits a type parameter declaration.
	 */
	void visitCtTypeParameter(CtTypeParameter typeParameter);

	/**
	 * Visits a conditional expression
	 */
	<T> void visitCtConditional(CtConditional<T> conditional);

	/**
	 * Visits a constructor declaration.
	 */
	<T> void visitCtConstructor(CtConstructor<T> c);

	/**
	 * Visits a <code>continue</code> statement.
	 */
	void visitCtContinue(CtContinue continueStatement);

	/**
	 * Visits a <code>do</code> loop.
	 */
	void visitCtDo(CtDo doLoop);

	/**
	 * Visits an enumeration declaration.
	 */
	<T extends Enum<?>> void visitCtEnum(CtEnum<T> ctEnum);

	/**
	 * Visits a reference to an executable.
	 */
	<T> void visitCtExecutableReference(CtExecutableReference<T> reference);

	/**
	 * Visits a field declaration.
	 */
	<T> void visitCtField(CtField<T> f);

	/**
	 * Visits an enum value declaration.
	 */
	<T> void visitCtEnumValue(CtEnumValue<T> enumValue);

	/**
	 * Visits a this access.
	 */
	<T> void visitCtThisAccess(CtThisAccess<T> thisAccess);

	/**
	 * Visits a reference to a field.
	 */
	<T> void visitCtFieldReference(CtFieldReference<T> reference);

	/**
	 * Visits a reference to an unbound field
	 */
	<T> void visitCtUnboundVariableReference(CtUnboundVariableReference<T> reference);

	/**
	 * Visits a <code>for</code> loop.
	 */
	void visitCtFor(CtFor forLoop);

	/**
	 * Visits an enhanced <code>for</code> loop.
	 */
	void visitCtForEach(CtForEach foreach);

	/**
	 * Visits an <code>if</code> statement.
	 */
	void visitCtIf(CtIf ifElement);

	/**
	 * Visits an interface declaration.
	 */
	<T> void visitCtInterface(CtInterface<T> intrface);

	/**
	 * Visits an executable invocation.
	 */
	<T> void visitCtInvocation(CtInvocation<T> invocation);

	/**
	 * Visits a literal expression.
	 */
	<T> void visitCtLiteral(CtLiteral<T> literal);

	/**
	 * Visits a local variable declaration.
	 */
	<T> void visitCtLocalVariable(CtLocalVariable<T> localVariable);

	/**
	 * Visits a reference to a local variable.
	 */
	<T> void visitCtLocalVariableReference(CtLocalVariableReference<T> reference);

	/**
	 * Visits a catch variable declaration.
	 */
	<T> void visitCtCatchVariable(CtCatchVariable<T> catchVariable);

	/**
	 * Visits a reference to a catch variable.
	 */
	<T> void visitCtCatchVariableReference(CtCatchVariableReference<T> reference);

	/**
	 * Visits a method declaration.
	 */
	<T> void visitCtMethod(CtMethod<T> m);

	/**
	 * Visits an annotation method declaration.
	 */
	<T> void visitCtAnnotationMethod(CtAnnotationMethod<T> annotationMethod);

	/**
	 * Visits an array construction.
	 */
	<T> void visitCtNewArray(CtNewArray<T> newArray);

	/**
	 * Visits a call to a constructor.
	 */
	<T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall);

	/**
	 * Visits an anonymous class construction.
	 */
	<T> void visitCtNewClass(CtNewClass<T> newClass);

	/**
	 * Visits an anonymous method construction.
	 */
	<T> void visitCtLambda(CtLambda<T> lambda);

	/**
	 * Visits a reference to an executable.
	 */
	<T, E extends CtExpression<?>> void visitCtExecutableReferenceExpression(
			CtExecutableReferenceExpression<T, E> expression);

	/**
	 * Visits an operator assignment.
	 */
	<T, A extends T> void visitCtOperatorAssignment(
			CtOperatorAssignment<T, A> assignment);

	/**
	 * Visits a package declaration.
	 */
	void visitCtPackage(CtPackage ctPackage);

	/**
	 * Visits a reference to a package.
	 */
	void visitCtPackageReference(CtPackageReference reference);

	/**
	 * Visits a parameter declaration.
	 */
	<T> void visitCtParameter(CtParameter<T> parameter);

	/**
	 * Visits a reference to a parameter.
	 */
	<T> void visitCtParameterReference(CtParameterReference<T> reference);

	/**
	 * Visits a <code>return</code> statement.
	 */
	<R> void visitCtReturn(CtReturn<R> returnStatement);

	/**
	 * Visits a statement list.
	 */
	<R> void visitCtStatementList(CtStatementList statements);

	/**
	 * Visits a <code>switch</code> statement.
	 */
	<S> void visitCtSwitch(CtSwitch<S> switchStatement);

	/**
	 * Visits a <code>synchronized</code> modifier.
	 */
	void visitCtSynchronized(CtSynchronized synchro);

	/**
	 * Visits a <code>throw</code> statement.
	 */
	void visitCtThrow(CtThrow throwStatement);

	/**
	 * Visits a <code>try</code> statement.
	 */
	void visitCtTry(CtTry tryBlock);

	/**
	 * Visits a <code>try</code> with resource statement.
	 */
	void visitCtTryWithResource(CtTryWithResource tryWithResource);

	/**
	 * Visits a reference to a type parameter.
	 */
	void visitCtTypeParameterReference(CtTypeParameterReference ref);

	/**
	 * Visits a reference to a wildcard.
	 */
	void visitCtWildcardReference(CtWildcardReference wildcardReference);

	/**
	 * Visits an intersection type.
	 */
	<T> void visitCtIntersectionTypeReference(CtIntersectionTypeReference<T> reference);

	/**
	 * Visits a reference to a type.
	 */
	<T> void visitCtTypeReference(CtTypeReference<T> reference);

	/**
	 * Visits a type access.
	 */
	<T> void visitCtTypeAccess(CtTypeAccess<T> typeAccess);

	/**
	 * Visits a unary operator.
	 */
	<T> void visitCtUnaryOperator(CtUnaryOperator<T> operator);

	/**
	 * Visits a variable read access.
	 */
	<T> void visitCtVariableRead(CtVariableRead<T> variableRead);

	/**
	 * Visits a variable write access.
	 */
	<T> void visitCtVariableWrite(CtVariableWrite<T> variableWrite);

	/**
	 * Visits a <code>while</code> loop.
	 */
	void visitCtWhile(CtWhile whileLoop);

	/**
	 * Visits a field of an annotation.
	 */
	<T> void visitCtAnnotationFieldAccess(CtAnnotationFieldAccess<T> annotationFieldAccess);

	/**
	 * Visits a field read access.
	 */
	<T> void visitCtFieldRead(CtFieldRead<T> fieldRead);

	/**
	 * Visits a field write access.
	 */
	<T> void visitCtFieldWrite(CtFieldWrite<T> fieldWrite);

	/**
	 * Visits an access to a super invocation.
	 */
	<T> void visitCtSuperAccess(CtSuperAccess<T> f);

	/**
	 * Visits a comment
	 */
	void visitCtComment(CtComment comment);

	/**
	 * Visits a javadoc comment
	 */
	void visitCtJavaDoc(CtJavaDoc comment);

	/**
	 * Visits a javadoc tag
	 */
	void visitCtJavaDocTag(CtJavaDocTag docTag);

	/**
	 * Visits an import declaration
	 */
	void visitCtImport(CtImport ctImport);

	/**
	 * Visits a module declaration
	 */
	void visitCtModule(CtModule module);

	/**
	 * Visits a module reference
	 */
	void visitCtModuleReference(CtModuleReference moduleReference);

	/**
	 * Visits a package export in a module declaration.
	 */
	void visitCtPackageExport(CtPackageExport moduleExport);

	/**
	 * Visits a "require" directive in a module declaration.
	 */
	void visitCtModuleRequirement(CtModuleRequirement moduleRequirement);

	/**
	 * Visits a "provides" directive in a module declaration.
	 */
	void visitCtProvidedService(CtProvidedService moduleProvidedService);

	/**
	 * Visits a "uses" directive in a module declaration.
	 */
	void visitCtUsedService(CtUsedService usedService);
}
