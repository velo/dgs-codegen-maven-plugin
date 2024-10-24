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

import static com.github.michaeboyles.dgs.FileUtil.getCanonicalFile;
import static com.github.michaeboyles.dgs.LanguageUtil.isProbablyKotlin;
import com.netflix.graphql.dgs.codegen.CodeGen;

import com.netflix.graphql.dgs.codegen.CodeGenConfig;
import com.netflix.graphql.dgs.codegen.Language;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@SuppressWarnings({ "FieldMayBeFinal", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection", "unused" })
public class GenerateMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.build.sourceDirectory}/../resources/schema")
    private File[] schemaPaths = {};

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/annotations/")
    private File outputDir;

    @Parameter(defaultValue = "${project.build.directory}/generated-examples")
    private File examplesOutputDir;

    @Parameter(required = true)
    private String packageName;

    @Parameter(defaultValue = "client")
    private String subPackageNameClient;

    @Parameter(defaultValue = "datafetchers")
    private String subPackageNameDatafetchers;

    @Parameter(defaultValue = "types")
    private String subPackageNameTypes;

    @Parameter(defaultValue = "docs")
    private String subPackageNameDocs;

    @Parameter
    private String language = isProbablyKotlin() ? "KOTLIN" : "JAVA";

    @Parameter
    private boolean generateBoxedTypes = false;
    @Parameter
    private boolean generateIsGetterForPrimitiveBooleanFields = false;

    @Parameter
    private boolean generateClient = false;

    @Parameter
    private boolean generateClientV2 = false;

    @Parameter
    private boolean generateInterfaces = false;

    @Parameter
    private boolean generateKotlinNullableClasses = false;

    @Parameter
    private boolean generateKotlinClosureProjections = false;

    @Parameter
    private Map<String, String> typeMapping = new HashMap<>();

    @Parameter
    private Set<String> includeQueries = new HashSet<>();

    @Parameter
    private Set<String> includeMutations = new HashSet<>();

    @Parameter
    private Set<String> includeSubscriptions = new HashSet<>();

    @Parameter
    private boolean skipEntityQueries = false;

    @Parameter
    private boolean shortProjectionNames = false;

    @Parameter
    private boolean generateDataTypes = true;

    @Parameter
    private boolean omitNullInputFields = false;

    @Parameter
    private int maxProjectionDepth = 10;

    @Parameter
    private boolean kotlinAllFieldsOptional = false;

    @Parameter
    private boolean snakeCaseConstantNames = false;

    @Parameter
    private boolean generateInterfaceSetters = true;

    @Parameter
    private boolean generateInterfaceMethodsForInterfaceFields = false;

    @Parameter
    private boolean generateDocs = false;

    @Parameter(defaultValue = "${project.build.directory}/generated-docs")
    private File generatedDocsFolder;

    @Parameter
    private Map<String, String> includeImports = new HashMap<>();

    @Parameter
    private Map<String, Map<String, String>> includeEnumImports = new HashMap<>();
    @Parameter
    private Map<String, Map<String, String>> includeClassImports = new HashMap<>();

    @Parameter
    private boolean generateCustomAnnotations = false;

    @Parameter
    private boolean javaGenerateAllConstructor = true;

    @Parameter
    private boolean implementSerializable = false;

    @Parameter
    private boolean addGeneratedAnnotation = false;

    @Parameter
    private boolean disableDatesInGeneratedAnnotation = false;

    @Parameter
    private boolean addDeprecatedAnnotation = false;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Override
    public void execute() {
        Set<File> schemaPaths = FileUtil.getSchemas(this.schemaPaths, getLog());

        CodeGenConfig config = new CodeGenConfig(
                /* schemas */ Collections.emptySet(),
                schemaPaths,
                Collections.emptyList(),
                getCanonicalFile(outputDir).toPath(),
                getCanonicalFile(examplesOutputDir).toPath(),
                /* writeToFiles */ true,
                packageName,
                subPackageNameClient,
                subPackageNameDatafetchers,
                subPackageNameTypes,
                subPackageNameDocs,
                Language.valueOf(language.toUpperCase()),
                generateBoxedTypes,
                generateIsGetterForPrimitiveBooleanFields,
                generateClient,
                generateClientV2,
                generateInterfaces,
                generateKotlinNullableClasses,
                generateKotlinClosureProjections,
                typeMapping,
                new HashSet<>(includeQueries),
                new HashSet<>(includeMutations),
                new HashSet<>(includeSubscriptions),
                skipEntityQueries,
                shortProjectionNames,
                generateDataTypes,
                omitNullInputFields,
                maxProjectionDepth,
                kotlinAllFieldsOptional,
                snakeCaseConstantNames,
                generateInterfaceSetters,
                generateInterfaceMethodsForInterfaceFields,
                generateDocs,
                getCanonicalFile(generatedDocsFolder).toPath(),
                includeImports,
                includeEnumImports,
                includeClassImports,
                generateCustomAnnotations,
                javaGenerateAllConstructor,
                implementSerializable,
                addGeneratedAnnotation,
                disableDatesInGeneratedAnnotation,
                addDeprecatedAnnotation);

        getLog().info("Codegen config: " + config);

        new CodeGen(config).generate();
        project.addCompileSourceRoot(getCanonicalFile(outputDir).getAbsolutePath());
    }

}