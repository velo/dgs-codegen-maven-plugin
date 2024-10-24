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
package com.github.michaelboyles.dgs.it;

import org.junit.Test;

import com.github.michaeboyles.dgs.generated.DgsConstants;
import com.github.michaeboyles.dgs.generated.sub.IPerson;
import com.github.michaeboyles.dgs.generated.sub.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeneratedTest {
    @Test
    public void constantName() {
        assertEquals("Person", DgsConstants.PERSON.TYPE_NAME);
    }

    @Test
    public void builder() {
        IPerson person = Person.newBuilder()
                .name("Michael")
                .age(28)
                .likesDogs(true)
                .build();

        assertEquals("Michael", person.getName());
        assertEquals(Integer.valueOf(28), person.getAge());
        assertTrue(person.getLikesDogs());
    }
}
