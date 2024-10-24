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

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtil {
    public static Set<File> getSchemas(File[] paths, Log log) {
        Set<File> schemaPaths = Arrays.stream(paths)
                .map(FileUtil::getCanonicalFile)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (File schemaPath : schemaPaths) {
            if (!schemaPath.exists()) {
                log.warn("Schema location " + schemaPath + " does not exist");
            }
        }
        return schemaPaths;
    }

    public static File getCanonicalFile(File file) {
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
