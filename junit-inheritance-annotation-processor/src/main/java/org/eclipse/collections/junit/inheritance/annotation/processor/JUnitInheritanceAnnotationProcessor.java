/*
 * Copyright (c) 2024 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.junit.inheritance.annotation.processor;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import org.junit.jupiter.api.Test;

@AutoService(Processor.class)
@SupportedAnnotationTypes("org.junit.jupiter.api.Test")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JUnitInheritanceAnnotationProcessor
        extends AbstractProcessor
{
    private Types typeUtils;
    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        if (annotations.isEmpty())
        {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(Test.class))
        {
            if (element.getKind() == ElementKind.METHOD)
            {
                this.checkSubclassMethods((ExecutableElement) element, roundEnv);
            }
        }
        return true;
    }

    private void checkSubclassMethods(ExecutableElement testMethod, RoundEnvironment roundEnv)
    {
        TypeElement containingClass = (TypeElement) testMethod.getEnclosingElement();

        for (TypeMirror subtype : this.getAllKnownSubtypes(containingClass.asType(), roundEnv))
        {
            TypeElement subtypeElement = (TypeElement) this.typeUtils.asElement(subtype);

            for (Element subclassElement : this.elementUtils.getAllMembers(subtypeElement))
            {
                if (subclassElement.getKind() == ElementKind.METHOD)
                {
                    ExecutableElement subclassMethod = (ExecutableElement) subclassElement;
                    if (this.elementUtils.overrides(subclassMethod, testMethod, subtypeElement)
                            && !this.hasTestAnnotation(subclassMethod))
                    {
                        this.messager.printMessage(
                                Diagnostic.Kind.ERROR,
                                "Method '"
                                        + subclassMethod.getSimpleName()
                                        + "' in class '"
                                        +
                                        subtypeElement.getQualifiedName()
                                        + "' must have @Test annotation because it overrides a method with @Test annotation.",
                                subclassMethod);
                    }
                }
            }
        }
    }

    private Set<TypeMirror> getAllKnownSubtypes(TypeMirror type, RoundEnvironment roundEnv)
    {
        Set<TypeMirror> subtypes = new HashSet<>();
        for (Element element : roundEnv.getRootElements())
        {
            if (element instanceof TypeElement)
            {
                TypeElement typeElement = (TypeElement) element;
                if (this.typeUtils.isAssignable(typeElement.asType(), type) && !this.typeUtils.isSameType(
                        typeElement.asType(),
                        type))
                {
                    subtypes.add(typeElement.asType());
                }
            }
        }
        return subtypes;
    }

    private boolean hasTestAnnotation(ExecutableElement method)
    {
        return method.getAnnotation(Test.class) != null;
    }
}
