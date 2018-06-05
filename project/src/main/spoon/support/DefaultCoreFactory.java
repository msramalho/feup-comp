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
package spoon.support;

import spoon.reflect.code.*;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.cu.position.BodyHolderSourcePosition;
import spoon.reflect.cu.position.DeclarationSourcePosition;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.SubFactory;
import spoon.reflect.reference.*;
import spoon.support.reflect.code.*;
import spoon.support.reflect.cu.CompilationUnitImpl;
import spoon.support.reflect.cu.position.BodyHolderSourcePositionImpl;
import spoon.support.reflect.cu.position.DeclarationSourcePositionImpl;
import spoon.support.reflect.cu.position.SourcePositionImpl;
import spoon.support.reflect.declaration.*;
import spoon.support.reflect.reference.*;
import spoon.support.visitor.equals.CloneHelper;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * This class implements a default core factory for Spoon's meta-model. This
 * implementation is done with regular Java classes (POJOs).
 */
public class DefaultCoreFactory extends SubFactory implements CoreFactory, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DefaultCoreFactory() {
		super(null);
	}

	public <T extends CtElement> T clone(T object) {
		return CloneHelper.INSTANCE.clone(object);
	}

	public <A extends Annotation> CtAnnotation<A> createAnnotation() {
		CtAnnotation<A> e = new CtAnnotationImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T extends Annotation> CtAnnotationType<T> createAnnotationType() {
		CtAnnotationType<T> e = new CtAnnotationTypeImpl<>();
		e.setFactory(getMainFactory());
		e.setParent(getMainFactory().Package().getRootPackage());
		return e;
	}

	public CtAnonymousExecutable createAnonymousExecutable() {
		CtAnonymousExecutable e = new CtAnonymousExecutableImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtArrayRead<T> createArrayRead() {
		CtArrayRead<T> e = new CtArrayReadImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtArrayWrite<T> createArrayWrite() {
		CtArrayWrite<T> e = new CtArrayWriteImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtArrayTypeReference<T> createArrayTypeReference() {
		CtArrayTypeReference<T> e = new CtArrayTypeReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtAssert<T> createAssert() {
		CtAssert<T> e = new CtAssertImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T, A extends T> CtAssignment<T, A> createAssignment() {
		CtAssignment<T, A> e = new CtAssignmentImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtBinaryOperator<T> createBinaryOperator() {
		CtBinaryOperator<T> e = new CtBinaryOperatorImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <R> CtBlock<R> createBlock() {
		CtBlock<R> e = new CtBlockImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtBreak createBreak() {
		CtBreak e = new CtBreakImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public <S> CtCase<S> createCase() {
		CtCase<S> e = new CtCaseImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtCatch createCatch() {
		CtCatch e = new CtCatchImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtClass<T> createClass() {
		CtClass<T> e = new CtClassImpl<>();
		e.setFactory(getMainFactory());
		e.setParent(getMainFactory().Package().getRootPackage());
		return e;
	}

	@Override
	public CtTypeParameter createTypeParameter() {
		CtTypeParameter e = new CtTypeParameterImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtConditional<T> createConditional() {
		CtConditional<T> e = new CtConditionalImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtConstructor<T> createConstructor() {
		CtConstructor<T> e = new CtConstructorImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtContinue createContinue() {
		CtContinue e = new CtContinueImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtDo createDo() {
		CtDo e = new CtDoImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T extends Enum<?>> CtEnum<T> createEnum() {
		CtEnum<T> e = new CtEnumImpl<>();
		e.setFactory(getMainFactory());
		e.setParent(getMainFactory().Package().getRootPackage());
		return e;
	}

	public <T> CtExecutableReference<T> createExecutableReference() {
		CtExecutableReference<T> e = new CtExecutableReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtField<T> createField() {
		CtField<T> e = new CtFieldImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtEnumValue<T> createEnumValue() {
		CtEnumValue<T> e = new CtEnumValueImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtFieldRead<T> createFieldRead() {
		CtFieldRead<T> e = new CtFieldReadImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtFieldWrite<T> createFieldWrite() {
		CtFieldWrite<T> e = new CtFieldWriteImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtAnnotationFieldAccess<T> createAnnotationFieldAccess() {
		CtAnnotationFieldAccess<T> e = new CtAnnotationFieldAccessImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtUnboundVariableReference<T> createUnboundVariableReference() {
		CtUnboundVariableReference e = new CtUnboundVariableReferenceImpl<T>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtFieldReference<T> createFieldReference() {
		CtFieldReference<T> e = new CtFieldReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtFor createFor() {
		CtFor e = new CtForImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtForEach createForEach() {
		CtForEach e = new CtForEachImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtIf createIf() {
		CtIf e = new CtIfImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtInterface<T> createInterface() {
		CtInterface<T> e = new CtInterfaceImpl<>();
		e.setFactory(getMainFactory());
		e.setParent(getMainFactory().Package().getRootPackage());
		return e;
	}

	public <T> CtInvocation<T> createInvocation() {
		CtInvocation<T> e = new CtInvocationImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtLiteral<T> createLiteral() {
		CtLiteral<T> e = new CtLiteralImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtLocalVariable<T> createLocalVariable() {
		CtLocalVariable<T> e = new CtLocalVariableImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtLocalVariableReference<T> createLocalVariableReference() {
		CtLocalVariableReference<T> e = new CtLocalVariableReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtCatchVariable<T> createCatchVariable() {
		CtCatchVariable<T> e = new CtCatchVariableImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtCatchVariableReference<T> createCatchVariableReference() {
		CtCatchVariableReference<T> e = new CtCatchVariableReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtMethod<T> createMethod() {
		CtMethod<T> e = new CtMethodImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtAnnotationMethod<T> createAnnotationMethod() {
		CtAnnotationMethod<T> e = new CtAnnotationMethodImpl<T>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtNewArray<T> createNewArray() {
		CtNewArray<T> e = new CtNewArrayImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtConstructorCall<T> createConstructorCall() {
		CtConstructorCall<T> e = new CtConstructorCallImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtNewClass<T> createNewClass() {
		CtNewClass<T> e = new CtNewClassImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtLambda<T> createLambda() {
		CtLambda<T> e = new CtLambdaImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T, E extends CtExpression<?>> CtExecutableReferenceExpression<T, E> createExecutableReferenceExpression() {
		CtExecutableReferenceExpression<T, E> e = new CtExecutableReferenceExpressionImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T, A extends T> CtOperatorAssignment<T, A> createOperatorAssignment() {
		CtOperatorAssignment<T, A> e = new CtOperatorAssignmentImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtPackage createPackage() {
		CtPackage e = new CtPackageImpl();
		e.setFactory(getMainFactory());
		e.setParent(getMainFactory().Package().getRootPackage());
		return e;
	}

	public CtPackageReference createPackageReference() {
		CtPackageReference e = new CtPackageReferenceImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtParameter<T> createParameter() {
		CtParameter<T> e = new CtParameterImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtParameterReference<T> createParameterReference() {
		CtParameterReference<T> e = new CtParameterReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <R> CtReturn<R> createReturn() {
		CtReturn<R> e = new CtReturnImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <R> CtStatementList createStatementList() {
		CtStatementList e = new CtStatementListImpl<R>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <S> CtSwitch<S> createSwitch() {
		CtSwitch<S> e = new CtSwitchImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtSynchronized createSynchronized() {
		CtSynchronized e = new CtSynchronizedImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtThrow createThrow() {
		CtThrow e = new CtThrowImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtTry createTry() {
		CtTry e = new CtTryImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public CtTryWithResource createTryWithResource() {
		CtTryWithResource e = new CtTryWithResourceImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtTypeParameterReference createTypeParameterReference() {
		CtTypeParameterReference e = new CtTypeParameterReferenceImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public CtWildcardReference createWildcardReference() {
		CtWildcardReference e = new CtWildcardReferenceImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtIntersectionTypeReference<T> createIntersectionTypeReference() {
		CtIntersectionTypeReference<T> e = new CtIntersectionTypeReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtTypeReference<T> createTypeReference() {
		CtTypeReference<T> e = new CtTypeReferenceImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public <T> CtTypeAccess<T> createTypeAccess() {
		CtTypeAccess<T> e = new CtTypeAccessImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtUnaryOperator<T> createUnaryOperator() {
		CtUnaryOperator<T> e = new CtUnaryOperatorImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtVariableRead<T> createVariableRead() {
		CtVariableRead<T> e = new CtVariableReadImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtVariableWrite<T> createVariableWrite() {
		CtVariableWrite<T> e = new CtVariableWriteImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtCodeSnippetExpression<T> createCodeSnippetExpression() {
		CtCodeSnippetExpression<T> e = new CtCodeSnippetExpressionImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtCodeSnippetStatement createCodeSnippetStatement() {
		CtCodeSnippetStatement e = new CtCodeSnippetStatementImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtComment createComment() {
		CtComment e = new CtCommentImpl();
		e.setFactory(getMainFactory());
		e.setCommentType(CtComment.CommentType.BLOCK);
		e.setContent("");
		return e;
	}

	@Override
	public CtJavaDoc createJavaDoc() {
		CtJavaDoc e = new CtJavaDocImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	@Override
	public CtJavaDocTag createJavaDocTag() {
		CtJavaDocTag e = new CtJavaDocTagImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtWhile createWhile() {
		CtWhile e = new CtWhileImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtImport createImport() {
		CtImport e = new CtImportImpl();
		e.setFactory(getMainFactory());
		return e;
	}

	public Factory getMainFactory() {
		return factory;
	}

	public void setMainFactory(Factory mainFactory) {
		this.factory = mainFactory;
	}

	@Override
	public SourcePosition createSourcePosition(CompilationUnit compilationUnit, int startSource, int end, int[] lineSeparatorPositions) {
		return new SourcePositionImpl(compilationUnit, startSource, end, lineSeparatorPositions);
	}

	@Override
	public SourcePosition createPartialSourcePosition(CompilationUnit compilationUnit) {
		return ((CompilationUnitImpl) compilationUnit).getOrCreatePartialSourcePosition();
	}

	@Override
	public DeclarationSourcePosition createDeclarationSourcePosition(CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int[] lineSeparatorPositions) {
		return new DeclarationSourcePositionImpl(compilationUnit, startSource, end, modifierStart, modifierEnd, declarationStart, declarationEnd, lineSeparatorPositions);
	}

	@Override
	public BodyHolderSourcePosition createBodyHolderSourcePosition(CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int bodyStart, int bodyEnd, int[] lineSeparatorPositions) {
		return new BodyHolderSourcePositionImpl(compilationUnit,
				startSource, end,
				modifierStart, modifierEnd,
				declarationStart, declarationEnd,
				bodyStart, bodyEnd,
				lineSeparatorPositions);
	}

	public CompilationUnit createCompilationUnit() {
		CompilationUnit cu = new CompilationUnitImpl();
		cu.setFactory(getMainFactory());
		return cu;
	}

	public <T> CtThisAccess<T> createThisAccess() {
		CtThisAccess<T> e = new CtThisAccessImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public <T> CtSuperAccess<T> createSuperAccess() {
		CtSuperAccess<T> e = new CtSuperAccessImpl<>();
		e.setFactory(getMainFactory());
		return e;
	}

	public CtElement create(Class<? extends CtElement> klass) {
		if (klass.equals(spoon.reflect.code.CtAnnotationFieldAccess.class)) {
			return createAnnotationFieldAccess();
		}
		if (klass.equals(spoon.reflect.code.CtArrayRead.class)) {
			return createArrayRead();
		}
		if (klass.equals(spoon.reflect.code.CtArrayWrite.class)) {
			return createArrayWrite();
		}
		if (klass.equals(spoon.reflect.code.CtAssert.class)) {
			return createAssert();
		}
		if (klass.equals(spoon.reflect.code.CtAssignment.class)) {
			return createAssignment();
		}
		if (klass.equals(spoon.reflect.code.CtBinaryOperator.class)) {
			return createBinaryOperator();
		}
		if (klass.equals(spoon.reflect.code.CtBlock.class)) {
			return createBlock();
		}
		if (klass.equals(spoon.reflect.code.CtBreak.class)) {
			return createBreak();
		}
		if (klass.equals(spoon.reflect.code.CtCase.class)) {
			return createCase();
		}
		if (klass.equals(spoon.reflect.code.CtCatch.class)) {
			return createCatch();
		}
		if (klass.equals(spoon.reflect.code.CtCatchVariable.class)) {
			return createCatchVariable();
		}
		if (klass.equals(spoon.reflect.code.CtCodeSnippetExpression.class)) {
			return createCodeSnippetExpression();
		}
		if (klass.equals(spoon.reflect.code.CtCodeSnippetStatement.class)) {
			return createCodeSnippetStatement();
		}
		if (klass.equals(spoon.reflect.code.CtComment.class)) {
			return createComment();
		}
		if (klass.equals(spoon.reflect.code.CtJavaDoc.class)) {
			return createJavaDoc();
		}
		if (klass.equals(spoon.reflect.code.CtJavaDocTag.class)) {
			return createJavaDocTag();
		}
		if (klass.equals(spoon.reflect.code.CtConditional.class)) {
			return createConditional();
		}
		if (klass.equals(spoon.reflect.code.CtConstructorCall.class)) {
			return createConstructorCall();
		}
		if (klass.equals(spoon.reflect.code.CtContinue.class)) {
			return createContinue();
		}
		if (klass.equals(spoon.reflect.code.CtDo.class)) {
			return createDo();
		}
		if (klass.equals(spoon.reflect.code.CtExecutableReferenceExpression.class)) {
			return createExecutableReferenceExpression();
		}
		if (klass.equals(spoon.reflect.code.CtFieldRead.class)) {
			return createFieldRead();
		}
		if (klass.equals(spoon.reflect.code.CtFieldWrite.class)) {
			return createFieldWrite();
		}
		if (klass.equals(spoon.reflect.code.CtForEach.class)) {
			return createForEach();
		}
		if (klass.equals(spoon.reflect.code.CtFor.class)) {
			return createFor();
		}
		if (klass.equals(spoon.reflect.code.CtIf.class)) {
			return createIf();
		}
		if (klass.equals(spoon.reflect.code.CtInvocation.class)) {
			return createInvocation();
		}
		if (klass.equals(spoon.reflect.code.CtLambda.class)) {
			return createLambda();
		}
		if (klass.equals(spoon.reflect.code.CtLiteral.class)) {
			return createLiteral();
		}
		if (klass.equals(spoon.reflect.code.CtLocalVariable.class)) {
			return createLocalVariable();
		}
		if (klass.equals(spoon.reflect.code.CtNewArray.class)) {
			return createNewArray();
		}
		if (klass.equals(spoon.reflect.code.CtNewClass.class)) {
			return createNewClass();
		}
		if (klass.equals(spoon.reflect.code.CtOperatorAssignment.class)) {
			return createOperatorAssignment();
		}
		if (klass.equals(spoon.reflect.code.CtReturn.class)) {
			return createReturn();
		}
		if (klass.equals(spoon.reflect.code.CtStatementList.class)) {
			return createStatementList();
		}
		if (klass.equals(spoon.reflect.code.CtSuperAccess.class)) {
			return createSuperAccess();
		}
		if (klass.equals(spoon.reflect.code.CtSwitch.class)) {
			return createSwitch();
		}
		if (klass.equals(spoon.reflect.code.CtSynchronized.class)) {
			return createSynchronized();
		}
		if (klass.equals(spoon.reflect.code.CtThisAccess.class)) {
			return createThisAccess();
		}
		if (klass.equals(spoon.reflect.code.CtThrow.class)) {
			return createThrow();
		}
		if (klass.equals(spoon.reflect.code.CtTry.class)) {
			return createTry();
		}
		if (klass.equals(spoon.reflect.code.CtTryWithResource.class)) {
			return createTryWithResource();
		}
		if (klass.equals(spoon.reflect.code.CtTypeAccess.class)) {
			return createTypeAccess();
		}
		if (klass.equals(spoon.reflect.code.CtUnaryOperator.class)) {
			return createUnaryOperator();
		}
		if (klass.equals(spoon.reflect.code.CtVariableRead.class)) {
			return createVariableRead();
		}
		if (klass.equals(spoon.reflect.code.CtVariableWrite.class)) {
			return createVariableWrite();
		}
		if (klass.equals(spoon.reflect.code.CtWhile.class)) {
			return createWhile();
		}
		if (klass.equals(spoon.reflect.declaration.CtAnnotation.class)) {
			return createAnnotation();
		}
		if (klass.equals(spoon.reflect.declaration.CtAnnotationMethod.class)) {
			return createAnnotationMethod();
		}
		if (klass.equals(spoon.reflect.declaration.CtAnnotationType.class)) {
			return createAnnotationType();
		}
		if (klass.equals(spoon.reflect.declaration.CtAnonymousExecutable.class)) {
			return createAnonymousExecutable();
		}
		if (klass.equals(spoon.reflect.declaration.CtClass.class)) {
			return createClass();
		}
		if (klass.equals(spoon.reflect.declaration.CtConstructor.class)) {
			return createConstructor();
		}
		if (klass.equals(spoon.reflect.declaration.CtEnum.class)) {
			return createEnum();
		}
		if (klass.equals(spoon.reflect.declaration.CtEnumValue.class)) {
			return createEnumValue();
		}
		if (klass.equals(spoon.reflect.declaration.CtField.class)) {
			return createField();
		}
		if (klass.equals(spoon.reflect.declaration.CtInterface.class)) {
			return createInterface();
		}
		if (klass.equals(spoon.reflect.declaration.CtMethod.class)) {
			return createMethod();
		}
		if (klass.equals(spoon.reflect.declaration.CtPackage.class)) {
			return createPackage();
		}
		if (klass.equals(spoon.reflect.declaration.CtParameter.class)) {
			return createParameter();
		}
		if (klass.equals(spoon.reflect.declaration.CtTypeParameter.class)) {
			return createTypeParameter();
		}
		if (klass.equals(spoon.reflect.reference.CtArrayTypeReference.class)) {
			return createArrayTypeReference();
		}
		if (klass.equals(spoon.reflect.reference.CtCatchVariableReference.class)) {
			return createCatchVariableReference();
		}
		if (klass.equals(spoon.reflect.reference.CtExecutableReference.class)) {
			return createExecutableReference();
		}
		if (klass.equals(spoon.reflect.reference.CtFieldReference.class)) {
			return createFieldReference();
		}
		if (klass.equals(spoon.reflect.reference.CtIntersectionTypeReference.class)) {
			return createIntersectionTypeReference();
		}
		if (klass.equals(spoon.reflect.reference.CtLocalVariableReference.class)) {
			return createLocalVariableReference();
		}
		if (klass.equals(spoon.reflect.reference.CtPackageReference.class)) {
			return createPackageReference();
		}
		if (klass.equals(spoon.reflect.reference.CtParameterReference.class)) {
			return createParameterReference();
		}
		if (klass.equals(spoon.reflect.reference.CtTypeParameterReference.class)) {
			return createTypeParameterReference();
		}
		if (klass.equals(spoon.reflect.reference.CtTypeReference.class)) {
			return createTypeReference();
		}
		if (klass.equals(spoon.reflect.reference.CtUnboundVariableReference.class)) {
			return createUnboundVariableReference();
		}
		if (klass.equals(spoon.reflect.reference.CtWildcardReference.class)) {
			return createWildcardReference();
		}
		if (klass.equals(spoon.reflect.declaration.CtImport.class)) {
			return createImport();
		}
		if (klass.equals(spoon.reflect.reference.CtModuleReference.class)) {
			return createModuleReference();
		}
		if (klass.equals(spoon.reflect.declaration.CtModule.class)) {
			return createModule();
		}
		if (klass.equals(spoon.reflect.declaration.CtModuleRequirement.class)) {
			return createModuleRequirement();
		}
		if (klass.equals(spoon.reflect.declaration.CtPackageExport.class)) {
			return createPackageExport();
		}
		if (klass.equals(spoon.reflect.declaration.CtProvidedService.class)) {
			return createProvidedService();
		}
		if (klass.equals(spoon.reflect.declaration.CtUsedService.class)) {
			return createUsedService();
		}
		throw new IllegalArgumentException("not instantiable by CoreFactory(): " + klass);
	}

	@Override
	public CtTypeReference createWildcardStaticTypeMemberReference() {
		CtTypeReference result = new CtWildcardStaticTypeMemberReferenceImpl();
		result.setFactory(getMainFactory());
		return result;
	}
	public CtModule createModule() {
		CtModule module = new CtModuleImpl();
		module.setFactory(getMainFactory());
		this.getMainFactory().Module().getUnnamedModule().addModule(module);
		return module;
	}

	@Override
	public CtModuleReference createModuleReference() {
		CtModuleReference moduleReference = new CtModuleReferenceImpl();
		moduleReference.setFactory(getMainFactory());
		return moduleReference;
	}

	@Override
	public CtModuleRequirement createModuleRequirement() {
		CtModuleRequirement moduleRequirement = new CtModuleRequirementImpl();
		moduleRequirement.setFactory(getMainFactory());
		return moduleRequirement;
	}

	@Override
	public CtPackageExport createPackageExport() {
		CtPackageExport moduleExport = new CtPackageExportImpl();
		moduleExport.setFactory(getMainFactory());
		return moduleExport;
	}

	@Override
	public CtProvidedService createProvidedService() {
		CtProvidedService moduleProvidedService = new CtProvidedServiceImpl();
		moduleProvidedService.setFactory(getMainFactory());
		return moduleProvidedService;
	}

	@Override
	public CtUsedService createUsedService() {
		CtUsedService ctUsedService = new CtUsedServiceImpl();
		ctUsedService.setFactory(getMainFactory());
		return ctUsedService;
	}

}
