/*
 * Copyright (C) 2024 Marvin Herman Froeder (marvin@marvinformatics.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.michaeboyles.dgs.java;

import com.github.michaeboyles.dgs.Packages;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import graphql.language.Type;

import java.util.List;

class TypeUtil {
    public static TypeName convertType(Packages packages, Type<?> type) {
        return convertType(packages, type, false, 0);
    }

    private static TypeName convertType(Packages packages, Type<?> type, boolean parentIsNonNull, int depth) {
        if (type instanceof graphql.language.TypeName) {
            graphql.language.TypeName t = (graphql.language.TypeName) type;
            return getTypeForName(packages, t.getName(), !(depth == 1 && parentIsNonNull));
        } else if (type instanceof graphql.language.ListType) {
            graphql.language.ListType listType = (graphql.language.ListType) type;
            return ParameterizedTypeName.get(
                    ClassName.get(List.class), convertType(packages, listType.getType(), false, depth + 1));
        } else if (type instanceof graphql.language.NonNullType) {
            graphql.language.NonNullType nonNullType = (graphql.language.NonNullType) type;
            return convertType(packages, nonNullType.getType(), true, depth + 1);
        }
        throw new RuntimeException("Unsupported type " + type.getClass());
    }

    private static TypeName getTypeForName(Packages packages, String name, boolean box) {
        TypeName typeName = null;
        switch (name) {
        case "Int":
            typeName = TypeName.get(int.class);
            break;
        case "Float":
            typeName = TypeName.get(double.class);
            break;
        case "Boolean":
            typeName = TypeName.get(boolean.class);
            break;
        case "String":
        case "ID":
            typeName = ClassName.get(String.class);
        }
        if (typeName != null) {
            return box ? typeName.box() : typeName;
        }
        return ClassName.get(packages.typesPackage(), name);
    }
}
