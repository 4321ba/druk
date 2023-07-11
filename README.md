A tesztelőprogram repository-ja eredetileg [Stellaway-nél](https://github.com/Stellaway/DrukTest) található, a könnyebb fordítás érdekében bemásoltam ide az `src/test` mappába is.

# Fordítás

`cd src`

## Prototípus program

`dir`

*látszik drukmakor mappa*

`javac drukmakor/*.java`

`jar cfm druk.jar MANIFEST.MF drukmakor/*.class`

*amennyiben a jar nem szerepel a pathban, a `jar` helyett `<jdk elérési útja>/bin/jar.exe` a megadandó*

## Tesztelő program

A tesztelő helyes működéséhez szükség van a fentebb leírtak alapján a tesztelendő program fordítására (`druk.jar`-ra az aktuális mappában).

`dir`

*látszik a test, inp, exp, result directoryk, illetve a Manifest_test.mf fájl is*

`javac test/*.java`

`jar cfm test.jar Manifest_test.mf test/*.class`

# Futtatás

## Prototípus program

`java -jar druk.jar`

Innentől lehet beírni a parancsokat: például `add ci`, `add me ci1`. A dokumentációban, minden mással együtt, a parancsnyelv részletes dokumentációja is megtalálható.

## Tesztelő program

`java -jar test.jar xxx`

Ahol xxx egy teszteset száma; vagy semmi, ha minden tesztet futtatni akarunk.

A futtatás eredménye időbélyeggel ellátva a results mappában lesz látható, ahogy azt a
program a standard kimeneten jelzi is.

# Megjegyzés a tesztelő cross-platformságához

Linuxon történő teszteléshez kicsit módosítani kell a `test/Test.java` fájlt a következőképpen:

A 183. sorban a

```java
Process p = rt.exec(new String[]{"cmd.exe", "/c", "java", "-jar", drukFileName, "<", testInput, ">", testOutput});
```

sort le kell cserélni

```java
Process p = rt.exec(new String[]{"/bin/sh", "-c", "java -jar " + drukFileName + " < " + testInput + " > " + testOutput});
```
-ra.

A druk.jar lecserélhető a grafikus változatra is, ennek a teszteléséhez viszont ugyanabba a sorba be kell írni, hogy gui nélkül indítsa a programot:
```java
Process p = rt.exec(new String[]{"/bin/sh", "-c", "java -jar " + drukFileName + " nogui < " + testInput + " > " + testOutput});
```

Ez beírható a prototípus teszteléséhez használt tesztelőbe is, mivel a prototípus nem nézi a parancssori argumentumokat.

Ezek alapján módosítottam az itt található `Test.java`-t, így az a grafikus és proto program tesztelésére is alkalmas kell legyen, mind linux, mind windows környezetben. Más környezetben viszont további módosításra lehet szükség.
