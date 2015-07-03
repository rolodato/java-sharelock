# java-sharelock

A JVM client for [Sharelock](https://sharelock.io).

```java
// Public instance at https://sharelock.io
Sharelock sharelock = new Sharelock();

// Custom instance
Sharelock mySharelock = new Sharelock("https://example.com/sharelock/");

sharelock.create("single recipient", "alice@example.com");

sharelock.create("multiple recipients", "bob@example.com", "carol@example.com");

List<String> recipients = ...;
sharelock.create("list", recipients);
```
