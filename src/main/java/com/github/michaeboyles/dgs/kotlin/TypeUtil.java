package com.github.michaeboyles.dgs.kotlin;

import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.ClassNames;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.TypeName;
import com.squareup.kotlinpoet.TypeNames;
import graphql.language.Type;

import java.util.List;

import static java.util.Collections.emptyList;

class TypeUtil {
    public static TypeName convertType(Type<?> type) {
        return convertType(type, false);
    }

    private static TypeName convertType(Type<?> type, boolean parentIsNonNull) {
        if (type instanceof graphql.language.TypeName) {
            graphql.language.TypeName t = (graphql.language.TypeName) type;
            return getTypeForName(t.getName()).copy(!parentIsNonNull, emptyList());
        }
        else if (type instanceof graphql.language.ListType) {
            graphql.language.ListType listType = (graphql.language.ListType) type;
            return ParameterizedTypeName.get(
                ClassName.bestGuess(List.class.getName()), convertType(listType.getType(), false)
            );
        }
        else if (type instanceof graphql.language.NonNullType) {
            graphql.language.NonNullType nonNullType = (graphql.language.NonNullType) type;
            return convertType(nonNullType.getType(), true);
        }
        throw new RuntimeException("Unsupported type " + type.getClass());
    }

    private static TypeName getTypeForName(String name) {
        TypeName typeName = null;
        switch (name) {
            case "Int":
                typeName = TypeNames.get(int.class);
                break;
            case "Float":
                typeName = TypeNames.get(double.class);
                break;
            case "Boolean":
                typeName = TypeNames.get(boolean.class);
                break;
            case "String":
            case "ID":
                typeName = ClassNames.get(String.class);
        }
        if (typeName != null) {
            return typeName;
        }
        return ClassName.bestGuess("org.example." + name); // TODO not hardcoded
    }
}
