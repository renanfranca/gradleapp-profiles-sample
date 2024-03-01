# Package types

This application comes with two package level annotations:

* `SharedKernel` used to mark packages containing classes shared between multiple contexts;
* `BusinessContext` used to mark packages containing classes to answer a specific business need. Classes in this package can't be used in another package.

To mark a package, you have to add a `package-info.java` file at the package root with:

```java
@tech.jhipster.gradleapp.SharedKernel
package tech.jhipster.gradleapp;
```

or:

```java
@tech.jhipster.gradleapp.BusinessContext
package tech.jhipster.gradleapp;
```
