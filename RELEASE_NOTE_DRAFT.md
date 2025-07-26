13.0.0
====================

This is the 13.0.0 major release.

This release focused on migrating the development baseline from Java 11 to Java 17, and updating our builds for p2 site to publish directly to maven central.

The Eclipse Collections team gives a huge thank you to everyone who participated in this release.

# New Functionality
-----------------
* Added default toArray/toImmutableList/Set/Bag implementations to LazyIterable.
* Implemented MapIterable.collectKeysUnique(). Fixes #409.
* Implemented MutableOrderedMap.toImmutable() and add ImmutableOrderedMapAdapter.
* Implemented OrderedMapAdapter.flipUniqueValues().
* Implemented Stack.getLast().
* Implemented Stack.distinct().
* Added feature to reverse Interval ranges.
* Added withOccurrences and withoutOccurrences to MutableBag.
* Implemented RichIterable.reduceBy(). Fixes #134.
* Added toStack to Ordered Primitive Iterable.
* Implemented Comparators.fromPredicate(), to create a Comparator from a Predicate2 that can answer isBefore.
* Implemented boxing wrappers for primitive sets. #1408.
* Implemented boxing wrappers for primitive lists. #1408.
* Exposed trimToSize() in HashBag implementations.
* Exposed trimToSize in all primitive maps and sets.
* Added removeIf to primitiveObject maps.
* Changed OrderedIterable.indexOf() to have a default implementation.
* Added missing empty with comparator methods. Fixes #1328.


# Optimizations
----------------------
* Optimized forEach method on Map views to not delegate to an iterator.
* Optimized Map.replaceAll() to not delegate to iterator.
* Optimized forEach method on Map views to not delegate to an iterator.
* Optimized putAll() method.
* Optimized ImmutableArrayList.takeWhile() and dropWhile() for small lists. Fixes #1640.
* Optimized clear() method of sub-lists. Fixes #1625.
* Optimized MutableList.subList().
* Optimized equals for primitive Bags using allSatisfyKeyValue on primitivePrimitive Maps.
* Added allSatisfyKeyValue method to Object/Primitive Maps to optimize HashBag equals method.
* Overrided Map.merge() default method for correctness and efficiency. Partially addresses #500.
* Optimized withAll on MutableXSetFactory and Immutable equivalent. Fixes #1374.
* Overrided Java 8 default method Map.forEach.
* Optimized any/all/noneSatisfy on UnifiedMapWithHashingStrategy. Fixes #1342.
* Optimized withAll for primitive bag factories. Fixes #1372.


# Tech Debt Reduction
---------------------
* Added more files to .idea/.gitignore.
* Used interfaces instead of implementations where possible without breaking backward compatibility.
* Formatted yaml files using Spotless.
* Synced .idea/compiler.xml.
* Fixed MapIterate.forEachKeyValue() to throw NullPointerException on null Map.
* Added missing @override annotations.
* Added suppress warnings to RichIterable for ClassReferencesSubclass.
* Fixed static analysis violations.
* Synced IntelliJ project files.
* Makeed UnifiedMapWithHashingStrategy more similar to UnifiedMap by implementing removeIf() and the detect*() methods.
* Added Feature #1568 - [OSGi] Opting in to Service Loader Mediator.
* Set .idea to linguist-generated=false in .gitattributes so that it shows up in code review diffs.
* Updated Checkstyle AvoidStaticImport to allow JUnit 5 static imports.
* Fixed IntelliJ code style settings.
* Configured CheckStyle check IllegalInstantiation to match the style of upstream configuration.
* Configured CheckStyle check HiddenField to match the style of upstream configuration.
* Turned on CheckStyle check RightCurly for additional tokens.
* Turned on CheckStyle check EmptyBlock for additional tokens.
* Turned on CheckStyle check AnnotationLocation for additional tokens.
* Ran rewrite.activeRecipes=org.openrewrite.java.RemoveUnusedImports.
* Ran org.openrewrite.java.UseStaticImport: methodPattern: org.junit.Assert *(..).
* Extracted interface MapTestCase above MutableMapIterableTestCase.
* Replaced new Long() with Long.valueOf().
* Updated JMH Benchmarks and library dependencies.
* Simplified Iterate forEach and sort by calling Java 8 default methods.
* Fixed overflow issues in LongInterval. Fixes #1717.
* Fixed OrderedMapAdapter.groupByUniqueKey().
* Fixed NullPointerException in IterableTestCase.assertEquals.
* Fixed generics in map factories.
* Fixed return types of aggregateBy overrides.
* Added default overrides for getFirst and getLast in MutableList and MutableSortedSet to Fixes #1460.
* Fixed a type in mutation.yml. Closes #1440.
* Fixed a bug in addAllAtIndex method. Closes #1433.
* Turned on additional IntelliJ inspections and fix violations (Trivial else). #1323.
* Turned on additional IntelliJ inspections and fix violations (Trivial If). #1323.
* Turned on additional IntelliJ inspections and fix violations (Commented out code). #1323.
* Turned on additional IntelliJ inspections and fix violations (Method is identical to its super method). #1323.
* Added missing overrides in multi-reader interfaces.
* Refactored distinct to use select.
* Refactored FastList to use new InternalArrayIterate primitive collect methods. Fixes #1350.


# Documentation Changes
----------------------
* Added "Eclipse Collections Categorically" book to "Learn Eclipse Collections" section of README.
* Improved structural search templates, mostly for collection factories and assertions.
* Clarified Java version compatibility in README.
* Added method categories with emojis in RichIterable using region/endregion comments.
* Added method categories with emojis in RichIterable javadoc summary.
* Fixed incorrect Javadoc for SortedSetIterable.intersect() and SetIterable.intersect().
* Fixed typo in 3-Code_Blocks.adoc.
* Fixed bold markup typos in the reference guide.
* Fixed typo in Primitive set doc.
* Fixed Java version in CONTRIBUTING.MD
* Added NOTICE.md file.
* Added Javadoc to ConcurrentMutableMap.merge().
* Updated README.md compatibility table and add blog links.
* Fixed some Javadoc errors.
* Fixed all language links, their order, old website links and http links.
* Added missing Javadoc for Iterate.getOnly().
* Removed prompt from code blocks in CONTRIBUTING.MD.
* Removed a typo in ImmutableSet Javadoc.
* Removed anonymous inner class examples from RichIterable and Iterate JavaDoc.
* Updated 2-Collection_Containers.adoc file for adding IntInterval Documentation.
* Updated 2-Collection_Containers.adoc file for Primitive sets documentation.
* Fixed dead links in CONTRIBUTING.MD


# Build Changes
-----------------
* Upgraded the minimum Java version to Java 17.
* Build a maven p2 site from the reactor content. Fixes #1411, #288
* Upgrade bnd plugin to 7.1.0.
* Publish p2 artifacts to maven central. Fixes #294 . Example [here](https://central.sonatype.com/artifact/org.eclipse.collections/p2-site)

# Note
-------
_We have taken all the measures to ensure all features are captured in the release notes.
However, release notes compilation is manual, so it is possible that a commit might be missed.
For a comprehensive list of commits please go through the commit log._

Acquiring Eclipse Collections
-----------------------------

### Maven

```xml
<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections-api</artifactId>
    <version>13.0.0</version>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections</artifactId>
    <version>13.0.0</version>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections-testutils</artifactId>
    <version>13.0.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>p2-site</artifactId>
    <version>13.0.0</version>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections-forkjoin</artifactId>
    <version>13.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'org.eclipse.collections:eclipse-collections-api:13.0.0'
implementation 'org.eclipse.collections:eclipse-collections:13.0.0'
testImplementation 'org.eclipse.collections:eclipse-collections-testutils:13.0.0'
implementation 'org.eclipse.collections:p2-site:13.0.0'
implementation 'org.eclipse.collections:eclipse-collections-forkjoin:13.0.0'
```

### Ivy

```xml
<dependency org="org.eclipse.collections" name="eclipse-collections-api" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="eclipse-collections" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="eclipse-collections-testutils" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="p2-site" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="eclipse-collections-forkjoin" rev="13.0.0"/>
```
<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections-api</artifactId>
    <version>13.0.0</version>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections</artifactId>
    <version>13.0.0</version>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections-testutils</artifactId>
    <version>13.0.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>p2-site</artifactId>
    <version>13.0.0</version>
</dependency>

<dependency>
    <groupId>org.eclipse.collections</groupId>
    <artifactId>eclipse-collections-forkjoin</artifactId>
    <version>13.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'org.eclipse.collections:eclipse-collections-api:13.0.0'
implementation 'org.eclipse.collections:eclipse-collections:13.0.0'
testImplementation 'org.eclipse.collections:eclipse-collections-testutils:13.0.0'
implementation 'org.eclipse.collections:p2-site:13.0.0'
implementation 'org.eclipse.collections:eclipse-collections-forkjoin:13.0.0'
```

### Ivy

```xml
<dependency org="org.eclipse.collections" name="eclipse-collections-api" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="eclipse-collections" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="eclipse-collections-testutils" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="p2-site" rev="13.0.0" />
<dependency org="org.eclipse.collections" name="eclipse-collections-forkjoin" rev="13.0.0"/>
```
