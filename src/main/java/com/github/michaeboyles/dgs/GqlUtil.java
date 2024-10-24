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
package com.github.michaeboyles.dgs;

import graphql.language.Document;
import graphql.language.ObjectTypeDefinition;

import java.util.Optional;

public class GqlUtil {
    public static Optional<ObjectTypeDefinition> getQuery(Document document) {
        return document.getDefinitionsOfType(ObjectTypeDefinition.class)
                .stream()
                .filter(def -> def.getName().equals("Query"))
                .findAny();
    }

    public static Optional<ObjectTypeDefinition> getMutation(Document document) {
        return document.getDefinitionsOfType(ObjectTypeDefinition.class)
                .stream()
                .filter(def -> def.getName().equals("Mutation"))
                .findAny();
    }

    public static Optional<ObjectTypeDefinition> getSubscription(Document document) {
        return document.getDefinitionsOfType(ObjectTypeDefinition.class)
                .stream()
                .filter(def -> def.getName().equals("Subscription"))
                .findAny();
    }
}
